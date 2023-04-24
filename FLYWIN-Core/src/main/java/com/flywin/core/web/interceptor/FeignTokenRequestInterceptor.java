/**
 * <li>文件名：FeignBasicAuthRequestInterceptor.java
 * <li>说明：
 * <li>创建人： 曾明辉
 * <li>创建日期：2018年11月15日
 * <li>修改人：
 * <li>修改日期：
 */
package com.flywin.core.web.interceptor;

import com.flywin.utils.TokenUtils;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @Description: TODO
 * @author: 曾明辉
 * @date: 2019年8月14日
 */
public class FeignTokenRequestInterceptor implements RequestInterceptor {
    /**
     * 日志
     */
    private static final Logger logger = LoggerFactory.getLogger(FeignTokenRequestInterceptor.class);

    /**
     * @param template
     * @Title apply
     * @Description TODO
     * @author 曾明辉
     * @date: 2019年8月14日
     */
    @Override
    public void apply(RequestTemplate template) {
        HttpServletRequest request = getHttpServletRequest();
        String token = TokenUtils.getRequestToken(request);

        if (token != null) {
            template.header(TokenUtils.TOKEN_NAME, token);
        }

    }

    /**
     * @return
     * @return HttpServletRequest
     * @Title getHttpServletRequest
     * @Description TODO
     * @author 曾明辉
     * @date: 2019年8月14日
     */
    private HttpServletRequest getHttpServletRequest() {
        try {
            RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
            if (requestAttributes != null) {
                return ((ServletRequestAttributes) requestAttributes).getRequest();
            } else {
                return null;
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    /**
     *
     * <li>方法名：getHeaders
     * <li>@param request
     * <li>@return
     * <li>返回类型：Map<String,String>
     * <li>说明：
     * <li>创建人：曾明辉
     * <li>创建日期：2018年11月15日
     * <li>修改人：
     * <li>修改日期：
     */
    // private Map<String, String> getHeaders(HttpServletRequest request) {
    // Map<String, String> map = new LinkedHashMap<>();
    // Enumeration<String> enumeration = request.getHeaderNames();
    // while (enumeration.hasMoreElements()) {
    // String key = enumeration.nextElement();
    // String value = request.getHeader(key);
    // map.put(key, value);
    // }
    // return map;
    // }
}
