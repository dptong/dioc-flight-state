/**
 * <li>文件名：LocaleConfig.java
 * <li>说明：
 * <li>创建人： 曾明辉
 * <li>创建日期：2018年12月12日
 * <li>修改人：
 * <li>修改日期：
 */
package com.flywin.core.web.config;

import com.flywin.core.web.interceptor.MessageInterpolator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

import java.util.Locale;

/**
 *
 * @Description: TODO
 * @author: 曾明辉
 * @date: 2019年8月14日
 */
@Configuration
public class LocaleConfig implements WebMvcConfigurer {

    /**
     *
     * @Title localeResolver
     * @Description TODO
     * @return
     * @return LocaleResolver
     * @author 曾明辉
     * @date: 2019年8月14日
     */
    @Bean
    public LocaleResolver localeResolver() {
        AcceptHeaderLocaleResolver localeResolver = new AcceptHeaderLocaleResolver();

        localeResolver.setDefaultLocale(Locale.CHINA); // 默认简体中文，请求头Accept-Language：zh-cn、en-us

        return localeResolver;
    }

    /**
     *
     * @Title getValidator
     * @Description TODO
     * @return
     * @author 曾明辉
     * @date: 2019年8月14日
     */
    @Bean
    @Override
    public Validator getValidator() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setDefaultEncoding("UTF-8");
        messageSource.setBasenames("i18n/ValidationMessages");

        LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();
        validator.setValidationMessageSource(messageSource);

        MessageInterpolator messageInterpolator = new MessageInterpolator(messageSource);
        validator.setMessageInterpolator(messageInterpolator);

        return validator;
    }

}
