/**
 * <li>文件名：RsponseInterceptor.java
 * <li>说明：
 * <li>创建人： 曾明辉
 * <li>创建日期：2018年12月3日
 * <li>修改人：
 * <li>修改日期：
 */
package com.flywin.core.web.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @Description: TODO
 * @author: 曾明辉
 * @date: 2019年8月14日
 */
@Slf4j
public class CorsInterceptor extends HandlerInterceptorAdapter {

    /**
     * 访问控制
     */
    @Value("${access-control-allow-origin:*}")
    private String accessControlAllowOrigin;

    /**
     *
     * @Title preHandle
     * @Description TODO
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     * @author 曾明辉
     * @date: 2019年8月14日
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        log.info("uri:" + request.getRequestURI().toString());

        response.addHeader("Access-Control-Allow-Origin", accessControlAllowOrigin); // 解决跨域
        response.setHeader("Access-Control-Allow-Headers", Constants.ACCESS_ALLOW_HEADERS);
        response.setHeader("Access-Control-Allow-Methods", Constants.ACCESS_ALLOW_METHODS);

        return super.preHandle(request, response, handler);
    }

}
