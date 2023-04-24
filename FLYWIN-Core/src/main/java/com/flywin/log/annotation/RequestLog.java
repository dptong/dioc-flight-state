package com.flywin.log.annotation;

import java.lang.annotation.*;

/**
 * @ClassName: RequestLog
 * @Description: 请求日志记录注解
 * @Author: System
 * @Date: 2021-3-19 14:45:30
 * @Version: 1.0
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequestLog {

    /**
     * 操作名
     *
     * @return String
     */
    String name() default "";

    /**
     * 操作描述
     *
     * @return String
     */
    String description() default "";

    /**
     * 是否记录返回结果
     *
     * @return boolean
     */
    boolean recordResponse() default true;
}
