package com.flywin.aspect;

import com.flywin.annotation.AvoidRepeatableCommit;
import com.flywin.core.web.response.ResponseUtils;
import com.flywin.redis.RedisConstants;
import com.flywin.utils.IpUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName: AvoidRepeatableCommitAspect
 * @Description: 重复提交APO
 * @Author: System
 * @Date: 2021-3-19 14:45:30
 * @Version: 1.0
 */
@Slf4j
@Aspect
@Component
public class AvoidRepeatableCommitAspect {

    /**
     * redis
     */
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 注释切入点
     */
    @Pointcut("@annotation(com.flywin.annotation.AvoidRepeatableCommit)")
    public void annotationPointcut() {

    }

    /**
     * @param joinPoint 连接点
     */
    @Before("annotationPointcut()")
    public void beforePointcut(JoinPoint joinPoint) {
        // 此处进入到方法前  可以实现一些业务逻辑
    }

    /**
     * 切入时执行
     *
     * @param joinPoint 连接点
     * @return Object
     * @throws Throwable 异常
     */
    @Around("annotationPointcut()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {

        // 获取ip
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String ip = IpUtils.getIpAddr(request);

        // 获取注解
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        //目标类、方法
        String className = method.getDeclaringClass().getName();
        String name = method.getName();

        String ipKey = String.format("%s#%s", className, name);

        int hashCode = ipKey.hashCode();

        String key = RedisConstants.REDIS_ROOT + String.format("%s_%d", ip, hashCode);

        // 记录日志
        log.info("ipKey={},hashCode={},key={}", ipKey, hashCode, key);

        AvoidRepeatableCommit avoidRepeatableCommit = method.getAnnotation(AvoidRepeatableCommit.class);
        long timeout = avoidRepeatableCommit.timeout();
        if (timeout < 0) {
            timeout = 5 * RedisConstants.SECOND;
        }
        String value = (String) redisTemplate.opsForValue().get(key);
        if (StringUtils.isNotBlank(value)) {
            return ResponseUtils.fail("请勿重复提交！");
        }
        redisTemplate.opsForValue().set(key, UUID.randomUUID().toString(), timeout, TimeUnit.MILLISECONDS);

        //执行方法
        return joinPoint.proceed();
    }

    /**
     * @param joinPoint 连接点
     */
    @AfterReturning("annotationPointcut()")
    public void doAfterReturning(JoinPoint joinPoint) {
        // 在切入点return内容之后切入内容（可以用来对处理返回值做一些加工处理）
    }

}
