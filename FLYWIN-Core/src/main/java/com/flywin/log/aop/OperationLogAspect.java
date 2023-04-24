package com.flywin.log.aop;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.flywin.core.dto.SysLoginUser;
import com.flywin.core.exception.BusinessException;
import com.flywin.log.annotation.RequestLog;
import com.flywin.log.entity.OperationLog;
import com.flywin.redis.RedisLoginUserManager;
import com.flywin.utils.TokenUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.HashMap;

/**
 * @Description 操作日志AOP
 * @Author wumin
 * @Date 2020-03-25 17:34
 * @Version 1.0
 */
@Aspect
@Component
public class OperationLogAspect {

    private static final Log log = LogFactory.getLog(OperationLogAspect.class);

    /**
     * 操作日志队列
     */
    private static final String QUEUE_OPERATION_LOG = "queue_operation_log";
    /**
     * 请求
     */
    @Value("${spring.application.name}")
    private String application;
    /**
     * 消息模板
     */
    private RabbitTemplate rabbitTemplate;
    /**
     * redis登陆控制
     */
    private RedisLoginUserManager redisLoginUserManager;

    /**
     * @param rabbitTemplate 消息模板
     */
    @Autowired
    public void setRabbitTemplate(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    /**
     * @param redisLoginUserManager 消息模板
     */
    @Autowired
    public void setRedisLoginUserManager(RedisLoginUserManager redisLoginUserManager) {
        this.redisLoginUserManager = redisLoginUserManager;
    }

    /**
     * log
     */
    @Pointcut("@annotation(com.flywin.log.annotation.RequestLog)")
    public void log() {
        //切入点
    }

    /**
     * @param proceedingJoinPoint 环绕通知
     * @return Object 超类
     * @throws BusinessException 异常
     */
    @Around("log()")
    public Object doAroundAdvice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes)
                RequestContextHolder.getRequestAttributes()).getRequest();

        ParameterNameDiscoverer pnd = new DefaultParameterNameDiscoverer();
        MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();
        Method method = signature.getMethod();

        // 参数名
        String[] parameterNames = pnd.getParameterNames(method);
        // 参数值
        Object[] args = proceedingJoinPoint.getArgs();
        // 通过map封装参数和参数值
        HashMap<String, Object> parameterMap = new HashMap();
        for (int i = 0; i < parameterNames.length; i++) {
            if (args[i] instanceof ServletResponse) {
                continue;
            }
            if (args[i] instanceof ServletRequest) {
                continue;
            }
            parameterMap.put(parameterNames[i], args[i]);
        }

        // 获取注解
        RequestLog requestLog = method.getAnnotation(RequestLog.class);
        String name = requestLog.name();
        String description = requestLog.description();
        boolean recordResponse = requestLog.recordResponse();

        ObjectMapper mapper = new ObjectMapper();
        String requestJson = null;
        String responseJson = null;
        try {
            requestJson = mapper.writeValueAsString(parameterMap);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage(), e);
        }

        // 封装日志对象
        OperationLog operationLog = new OperationLog();
        operationLog.setApplication(application);
        operationLog.setName(name);
        operationLog.setDescription(description);
        operationLog.setRequestJson(requestJson);

        Object resultValue;
        try {
            resultValue = proceedingJoinPoint.proceed();

            if (recordResponse) {
                try {
                    responseJson = mapper.writeValueAsString(resultValue);
                } catch (JsonProcessingException e) {
                    log.error("json序列化失败");
                }
            }
            operationLog.setResponseJson(responseJson);
            // 发送消息
            sendLog(operationLog, request);
        } catch (Throwable throwable) {
            responseJson = throwable.getMessage();
            operationLog.setResponseJson(responseJson);
            // 发送消息
            sendLog(operationLog, request);
            log.error(responseJson, throwable);
            throw throwable.fillInStackTrace();
        }

        return resultValue;
    }

    /**
     * @param operationLog 操作日志
     * @param request      请求
     * @return void
     * @Description 发送消息
     * @Author wumin
     * @Date 2020-03-25 19:35
     */
    private void sendLog(OperationLog operationLog, HttpServletRequest request) {
        // 获取登录人
        SysLoginUser loginUser = getLoginUser(request);

        operationLog.setIp(getIp(request));
        operationLog.setPath(request.getRequestURI());
        if (loginUser != null) {
            operationLog.setCreatorId(loginUser.getId());
            operationLog.setCreator(loginUser.getName());
            // 判断是否是管理员
            if (loginUser.isOrgAdmin() || loginUser.isSuperAdmin()) {
                operationLog.setIsAdmin(1);
            } else {
                operationLog.setIsAdmin(0);
            }
        }

        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
        rabbitTemplate.convertAndSend(QUEUE_OPERATION_LOG, operationLog);
    }

    /**
     * @param request 请求
     * @return com.flywin.core.dto.SysLoginUser
     * @Description 获取登录人信息
     * @Author wumin
     * @Date 2020-03-25 19:36
     */
    private SysLoginUser getLoginUser(HttpServletRequest request) {

        String token = TokenUtils.getRequestToken(request);

        return redisLoginUserManager.getLoginUserByToken(token);
    }

    /**
     * @param request 请求
     * @return java.lang.String
     * @Description 获取IP地址
     * @Author wumin
     * @Date 2020-03-26 12:02
     */
    public static String getIp(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return "0:0:0:0:0:0:0:1".equals(ip) ? "127.0.0.1" : ip;
    }
}
