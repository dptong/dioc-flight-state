package com.flywin.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.flywin.core.dto.SysLoginUser;
import com.flywin.core.message.I18nMessage;
import com.flywin.core.web.response.ResponseResult;
import com.flywin.core.web.response.ResponseUtils;
import com.flywin.redis.RedisLoginUserManager;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.flywin.filter.CodeConstants.TOKEN_NAME;

/**
 * Zuul过滤器
 */
@Slf4j
@Component
public class ZuulAuthFilter extends ZuulFilter {

    /**
     * 自定义排除路径
     */
    @Value(value = "${zuul.auth.exclude.path.patterns}")
    private String[] excludePathPatterns;

    /**
     * 国际化消息
     */
    @Autowired
    private I18nMessage i18nMessage;

    /**
     * 用户redis管理工具
     */
    @Autowired
    private RedisLoginUserManager redisLoginUserManager;


    /**
     * to classify a filter by type. Standard types in Zuul are "pre" for pre-routing filtering,
     * "route" for routing to an origin, "post" for post-routing filters, "error" for error handling.
     * We also support a "static" type for static responses see  StaticResponseFilter.
     * Any filterType made be created or added and run by calling FilterProcessor.runFilters(type)
     *
     * @return A String representing that type
     */
    @Override
    public String filterType() { // 前置过滤
        return FilterConstants.PRE_TYPE;
    }

    /**
     * filterOrder() must also be defined for a filter. Filters may have the same  filterOrder if precedence is not
     * important for a filter. filterOrders do not need to be sequential.
     *
     * @return the int order of a filter
     */
    @Override
    public int filterOrder() { // 优先级为0，数字越大，优先级越低
        return FilterConstants.PRE_DECORATION_FILTER_ORDER;
    }

    /**
     * a "true" return from this method means that the run() method should be invoked
     *
     * @return true if the run() method should be invoked. false will not invoke the run() method
     */
    @Override
    public boolean shouldFilter() { // 是否执行该过滤器，此处为true，说明需要过滤
        return true;
    }

    /**
     * if shouldFilter() is true, this method will be invoked. this method is the core method of a ZuulFilter
     *
     * @return Some arbitrary artifact may be returned. Current implementation ignores it.
     * @throws ZuulException if an error occurs during execution.
     */
    @Override
    public Object run() throws ZuulException { // 执行代码

        // 获取请求
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();

        // 自定义排除路径
        if (excludePathMatch(request)) { // 放行
            return null;
        } else {
            // 根据请求解析token
            String token = request.getHeader(TOKEN_NAME);
            if (StringUtils.isEmpty(token)) { // 未登录授权
                // 拒绝转发请求
                ctx.setSendZuulResponse(false);
                ctx.setResponseStatusCode(CodeConstants.INT_401);
                ResponseResult<Object> result = ResponseUtils.fail(CodeConstants.INT_401,
                        i18nMessage.getStringMessage("auth.fail.unlogged.authorization"));
                responseOutJson(ctx.getResponse(), result);

            } else { // 用户已经登录
                token = token.replace("Bearer ", "");
                SysLoginUser loginUser = redisLoginUserManager.getLoginUserByToken(token);
                if (StringUtils.isEmpty(loginUser)) { // 用户对象为空
                    // 拒绝转发请求
                    ctx.setSendZuulResponse(false);
                    ctx.setResponseStatusCode(CodeConstants.INT_401);
                    ResponseResult<Object> result = ResponseUtils.fail(CodeConstants.INT_401,
                            i18nMessage.getStringMessage("auth.fail.logon.has.expired"));
                    responseOutJson(ctx.getResponse(), result);
                } else { // 用户存在则重置token的过期时间
                    redisLoginUserManager.refreshLoginUser(loginUser);
                }
            }

            return null;
        }
    }

    /**
     * @param request 请求request
     * @return boolean
     * @Title excludePathMatch
     * @Description 路径是否排除登录权限
     * @author 曾明辉
     * @date: 2019年8月15日
     */
    private boolean excludePathMatch(HttpServletRequest request) {
        String path = request.getRequestURI();
        AntPathMatcher pathMatcher = new AntPathMatcher();
        for (String pattern : excludePathPatterns) {
            if (pathMatcher.match(pattern, path)) {
                return true;
            }
        }

        return false;
    }

    /**
     * 转化为json数据输出
     *
     * @param response 响应
     * @param result   数据体
     */
    private void responseOutJson(HttpServletResponse response, ResponseResult result) {
        ObjectMapper mapper = new ObjectMapper();

        try {
            String json = mapper.writeValueAsString(result);
            responseOutJson(response, json);
        } catch (JsonProcessingException e) {
            log.error("转换Json错误：", e);
        }
    }

    /**
     * 转化为json数据输出
     *
     * @param response 响应
     * @param json     数据体
     */
    private static void responseOutJson(HttpServletResponse response, String json) {
        try {
            response.setCharacterEncoding("UTF-8");
            response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
            response.getOutputStream().write(json.getBytes("UTF-8"));

            response.flushBuffer();

        } catch (IOException e) {
            log.error("response输出错误：", e);
        }
    }

}
