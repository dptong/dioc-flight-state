package com.flywin.utils;


import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ObjectUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @ClassName: WebUtils
 * @Description: Web工具类
 * @Author: System
 * @Date: 2021-3-23 10:31:01
 * @Version: 1.0
 */
@Slf4j
public class WebUtils {

    /**
     * 获取HttpServletRequest
     *
     * @return HttpServletRequest
     */
    public static HttpServletRequest getRequest() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (ObjectUtils.isEmpty(requestAttributes)) {
            return null;
        } else {
            return ((ServletRequestAttributes) requestAttributes).getRequest();
        }
    }
}
