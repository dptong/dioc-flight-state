/**
 * <li>文件名：TokenUtils.java
 * <li>说明：
 * <li>创建人： 曾明辉
 * <li>创建日期：2018年11月16日
 * <li>修改人：
 * <li>修改日期：
 */
package com.flywin.utils;

import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

/**
 *
 * @Description: TODO
 * @author: 曾明辉
 * @date: 2019年8月15日
 */
public final class TokenUtils {

    /**
     * Token名称
     */
    public static final String TOKEN_NAME = "Authorization";

    /**
     *
     * @Title:
     * @Description:
     */
    private TokenUtils() {

    }

    /**
     *
     * @Title newToken
     * @Description TODO
     * @return
     * @return String
     * @author 曾明辉
     * @date: 2019年8月15日
     */
    public static String newToken() {
        String token = UUID.randomUUID().toString().replaceAll("-", "");

        return token;
    }

    /**
     *
     * @Title getRequestToken
     * @Description TODO
     * @param request
     *            请求
     * @return
     * @return String
     * @author 曾明辉
     * @date: 2019年8月15日
     */
    public static String getRequestToken(HttpServletRequest request) {
        if (request == null) {
            return null;
        }
        String token = request.getHeader(TOKEN_NAME);
        if (StringUtils.isEmpty(token)) { // 没有登录
            return null;
        } else {
            token = token.replace("Bearer ", "");
            return token;
        }
    }
}
