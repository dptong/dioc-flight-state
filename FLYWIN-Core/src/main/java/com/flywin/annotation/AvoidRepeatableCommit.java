package com.flywin.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @ClassName: AvoidRepeatableCommit
 * @Description: 避免重复提交
 * @Author: System
 * @Date: 2021-3-19 14:45:30
 * @Version: 1.0
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AvoidRepeatableCommit {

    /**
     * 指定时间内不可重复提交,单位毫秒
     */
    long timeout() default 5000;

}
