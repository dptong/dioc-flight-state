package com.flywin.core.web.interceptor;

import org.hibernate.validator.messageinterpolation.ResourceBundleMessageInterpolator;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.validation.beanvalidation.MessageSourceResourceBundleLocator;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * @Description 国际化处理
 * @Author wumin
 * @Date 2020-09-04 15:05
 * @Version 1.0
 */
public class MessageInterpolator extends ResourceBundleMessageInterpolator {

    /**
     * @Description 异常默认值
     * @Author wumin
     * @Date 2020-09-04 15:54
     * @param null
     * @return
     */
    private static final Map<String, String> VALIDATION_DEFAULT_MESSAGES = new HashMap<>();

    static {
        VALIDATION_DEFAULT_MESSAGES.put("AssertFalse", "validation.message.assertFalse");
        VALIDATION_DEFAULT_MESSAGES.put("AssertTrue", "validation.message.assertTrue");
        VALIDATION_DEFAULT_MESSAGES.put("DecimalMax", "validation.message.decimalMax");
        VALIDATION_DEFAULT_MESSAGES.put("DecimalMin", "validation.message.decimalMin");
        VALIDATION_DEFAULT_MESSAGES.put("Digits", "validation.message.digits");
        VALIDATION_DEFAULT_MESSAGES.put("Email", "validation.message.email");
        VALIDATION_DEFAULT_MESSAGES.put("Future", "validation.message.future");
        VALIDATION_DEFAULT_MESSAGES.put("FutureOrPresent", "validation.message.futureOrPresent");
        VALIDATION_DEFAULT_MESSAGES.put("Length", "validation.message.length");
        VALIDATION_DEFAULT_MESSAGES.put("Max", "validation.message.max");
        VALIDATION_DEFAULT_MESSAGES.put("Min", "validation.message.min");
        VALIDATION_DEFAULT_MESSAGES.put("Negative", "validation.message.negative");
        VALIDATION_DEFAULT_MESSAGES.put("NegativeOrZero", "validation.message.negativeOrZero");
        VALIDATION_DEFAULT_MESSAGES.put("NotBlank", "validation.message.notBlank");
        VALIDATION_DEFAULT_MESSAGES.put("NotEmpty", "validation.message.notEmpty");
        VALIDATION_DEFAULT_MESSAGES.put("NotNull", "validation.message.notNull");
        VALIDATION_DEFAULT_MESSAGES.put("Null", "validation.message.null");
        VALIDATION_DEFAULT_MESSAGES.put("Past", "validation.message.past");
        VALIDATION_DEFAULT_MESSAGES.put("PastOrPresent", "validation.message.pastOrPresent");
        VALIDATION_DEFAULT_MESSAGES.put("Pattern", "validation.message.pattern");
        VALIDATION_DEFAULT_MESSAGES.put("Positive", "validation.message.positive");
        VALIDATION_DEFAULT_MESSAGES.put("PositiveOrZero", "validation.message.positiveOrZero");
        VALIDATION_DEFAULT_MESSAGES.put("Size", "validation.message.size");
    }


    public MessageInterpolator(ResourceBundleMessageSource messageSource) {
        super(new MessageSourceResourceBundleLocator(messageSource));
    }

    @Override
    public String interpolate(String message, Context context) {
        Locale locale = LocaleContextHolder.getLocale();
        return this.interpolate(message, context, locale);
    }

    @Override
    public String interpolate(String message, Context context, Locale locale) {
        // 获取注解类型
        Annotation annotation = context.getConstraintDescriptor().getAnnotation();
        String annotationTypeName = annotation.annotationType().getSimpleName();

        // 根据注解类型获取自定义的消息Code
        String annotationDefaultMessageCode = VALIDATION_DEFAULT_MESSAGES.get(annotationTypeName);
        if (null != annotationDefaultMessageCode && !message.startsWith("{javax.validation.constraints")
                && !message.startsWith("{org.hibernate.validator.constraints")) {
            // 如果注解上指定的message不是默认的javax.validation或者org.hibernate.validator等开头的情况，
            // 则需要将自定义的消息Code拼装到原message的后面；
            message += "{" + annotationDefaultMessageCode + "}";
        }

        return super.interpolate(message, context, locale);
    }

}
