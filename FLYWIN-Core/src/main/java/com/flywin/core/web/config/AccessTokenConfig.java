/**
 * <li>文件名：MvcConfig.java
 * <li>说明：
 * <li>创建人： 曾明辉
 * <li>创建日期：2018年11月5日
 * <li>修改人：
 * <li>修改日期：
 */
package com.flywin.core.web.config;

import com.flywin.core.web.interceptor.AccessTokenInterceptor;
import com.flywin.core.web.interceptor.CorsInterceptor;
import com.flywin.core.web.interceptor.FeignTokenRequestInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 *
 * <li>类型名称：
 * <li>说明：
 * <li>创建人： 曾明辉
 * <li>创建日期：2018年11月15日
 * <li>修改人：
 * <li>修改日期：
 */
@Configuration
public class AccessTokenConfig implements WebMvcConfigurer {

    /**
     * 访问控制排除路径
     */
    @Value("${access.token.exclude.path.patterns:/,/error, /actuator/**, /js/**,/css/**,/image/**}")
    private String[] excludePathPatterns; // {"/","/error", "/actuator/**", "/js/**","/css/**","/image/**"};

    // .excludePathPatterns("/","/login","/phoneLogin","/error","/getTransEncryptKey","/validateCode/**",
    // "/qrCode/getQRCode","/qrCode/showQRCodeImg/**","/qrCodeLoginCheck",
    // "/actuator/**", "/js/**","/css/**","/image/**");

    /**
     *
     * @Title getAccessTokenInterceptor
     * @Description TODO
     * @return
     * @return AccessTokenInterceptor
     * @author 曾明辉
     * @date: 2019年8月14日
     */
    @Bean
    AccessTokenInterceptor getAccessTokenInterceptor() {
        return new AccessTokenInterceptor();
    }

    /**
     *
     * @Title getCorsInterceptor
     * @Description TODO
     * @return
     * @return CorsInterceptor
     * @author 曾明辉
     * @date: 2019年8月14日
     */
    @Bean
    CorsInterceptor getCorsInterceptor() {
        return new CorsInterceptor();
    }

    /**
     *
     * @Title getFeignTokenRequestInterceptor
     * @Description TODO
     * @return
     * @return FeignTokenRequestInterceptor
     * @author 曾明辉
     * @date: 2019年8月14日
     */
    @Bean
    FeignTokenRequestInterceptor getFeignTokenRequestInterceptor() {
        return new FeignTokenRequestInterceptor();
    }

    /**
     *
     * @Title addInterceptors
     * @Description TODO
     * @param registry
     * @author 曾明辉
     * @date: 2019年8月14日
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(getAccessTokenInterceptor()).addPathPatterns("/**")
                .excludePathPatterns(excludePathPatterns);

        registry.addInterceptor(getCorsInterceptor()).addPathPatterns("/**");
    }

}
