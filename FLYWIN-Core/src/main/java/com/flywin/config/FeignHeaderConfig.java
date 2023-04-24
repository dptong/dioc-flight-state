package com.flywin.config;

import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

/**
 * @Description 解决Feign Header丢失
 * @Author wumin
 * @Date 2020-10-30 8:47
 * @Version 1.0
 */
@Configuration
public class FeignHeaderConfig {

    @Bean("requestInterceptor")
    public RequestInterceptor requestInterceptor() {

        return template -> {
            // 1、RequestContextHolder 拿到当前的请求
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if (attributes != null) {
                // 原始请求 页面发起的老请求
                HttpServletRequest request = attributes.getRequest();
                if (request != null) {
                    Enumeration<String> headerNames = request.getHeaderNames();
                    while (headerNames.hasMoreElements()) {
                        String headerName = headerNames.nextElement();
                        // 给feign生成的请求设置请求头authorization
                        template.header(headerName, request.getHeader(headerName));
                    }

                }
            }
        };
    }
}
