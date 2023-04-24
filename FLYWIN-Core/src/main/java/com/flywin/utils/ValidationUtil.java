package com.flywin.utils;

import com.flywin.core.web.interceptor.MessageInterpolator;
import org.springframework.context.support.ResourceBundleMessageSource;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.xml.bind.ValidationException;
import java.util.Set;

public class ValidationUtil {
    /**
     * 校验器
     */
    private static Validator validator;


    static {
//        ValidatorFactory vf = Validation.buildDefaultValidatorFactory();
//        validator = vf.getValidator();
        // 配置国际化
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setDefaultEncoding("UTF-8");
        messageSource.setBasenames("i18n/ValidationMessages");
        MessageInterpolator messageInterpolator = new MessageInterpolator(messageSource);
        ValidatorFactory vf = Validation.byDefaultProvider().configure().messageInterpolator(messageInterpolator)
                .buildValidatorFactory();
        validator = vf.getValidator();
    }


    /**
     * @param t   将要校验的对象
     * @param <T> 对象泛型
     * @throws ValidationException
     * @throws ValidationException
     * @throws ValidationException void
     * @throws
     * @Description: 校验方法
     */
    public static <T> void validate(T t) throws ValidationException {
        Set<ConstraintViolation<T>> set = validator.validate(t);
        if (set.size() > 0) {
            StringBuilder validateError = new StringBuilder();
            for (ConstraintViolation<T> val : set) {
                validateError.append(val.getMessage() + ";");
            }
            throw new ValidationException(validateError.toString());
        }
    }

    /**
     * @param t   将要校验的对象
     * @param <T> 对象泛型
     * @return java.lang.String
     * @Description 获取验证失败后的数据
     * @Author wumin
     * @Date 2020-02-17 17:06
     */
    public static <T> String getValidate(T t) {
        if (t == null) {
            return null;
        }
        Set<ConstraintViolation<T>> set = validator.validate(t);
        if (set.size() > 0) {
            StringBuilder validateError = new StringBuilder();
            for (ConstraintViolation<T> val : set) {
                if (validateError.length() > 0) {
                    validateError.append(";");
                }
                validateError.append(val.getMessage());
            }
            return validateError.toString();
        }

        return null;
    }
}
