/**
 * <li>文件名：LoginInterceptor.java
 * <li>说明：
 * <li>创建人： 曾明辉
 * <li>创建日期：2018年11月9日
 * <li>修改人：
 * <li>修改日期：
 */
package com.flywin.core.web.interceptor;

import com.flywin.core.dto.SysLoginUser;
import com.flywin.redis.RedisLoginUserManager;
import com.flywin.utils.TokenUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.security.auth.message.AuthException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <li>类型名称：
 * <li>说明：
 * <li>创建人： 曾明辉
 * <li>创建日期：2018年11月9日
 * <li>修改人：
 * <li>修改日期：
 */
@Slf4j
public class AccessTokenInterceptor extends HandlerInterceptorAdapter {

    /**
     * default value
     */
    @Value("${sys.login.redirect.url:}")
    private String redirectUrl;

    /**
     * 用户缓存
     */
    @Autowired
    private RedisLoginUserManager redisLoginUserManager;


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

        String token = TokenUtils.getRequestToken(request);

        if (StringUtils.isEmpty(token)) { // 没有登录
            /**
             * 非ajax(异步)请求，并且有配置则保存当前访问链接
             */
            if (request.getHeader("x-requested-with") == null && !StringUtils.isEmpty(this.redirectUrl)) {
                if (this.redirectUrl.startsWith("/")) {
                    response.sendRedirect(request.getContextPath() + this.redirectUrl);
                } else {
                    response.sendRedirect(this.redirectUrl);
                }
                return false;
            }
            // else { // ajax请求
            // throw new
            // AuthException(i18nMessage.getStringMessage("auth.fail.unlogged.authorization"));
            // }
        } else { // 已登录过

            SysLoginUser loginUser = this.redisLoginUserManager.getLoginUserByToken(token);
            if (loginUser == null) {
                throw new AuthException("auth.fail.logon.has.expired");
            } else {
                // 验证是否允许多点登陆 0:不允许,1:允许
                Long allowMultlogin = loginUser.getAllowMultlogin();
                if (allowMultlogin == null || allowMultlogin != 1L) {
                    if (!token.equals(loginUser.getToken())) { // token不一致
                        throw new AuthException("auth.fail.logged.at.other");
                    }
                }

                this.redisLoginUserManager.refreshLoginUser(loginUser);

            }
        }

        log.info("uri:" + request.getRequestURI().toString() + " token valid");

        return super.preHandle(request, response, handler);
    }

}
