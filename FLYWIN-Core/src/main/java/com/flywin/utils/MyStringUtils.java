/**
 *
 */
package com.flywin.utils;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * @author
 */
public class MyStringUtils {
    /**
     * @param obj 超类
     * @return Boolean
     */
    public static Boolean isBlank(Object obj) {
        if (obj == null) { // 是null 则立即返回true
            return true;
        }

        // 如果是字符串
        if (obj instanceof String) {
            return "".equals(obj) ? true : false;
        }

        // 集合判断为空
        if (obj instanceof Collection) {
            return ((Collection) obj).size() > 0 ? false : true;
        }

        return false;
    }

    /**
     * @param params 校验的参数列表
     * @return boolean
     * @Title isBlank
     * @Description TODO 校验多个对象是否为空,任何一个为空则返回true
     * @author 唐飞宏
     * @date: 2019年10月30日
     */
    public static boolean isBlank(Object... params) {
        if (params == null || params.length < 1) {
            return true;
        }
        for (Object obj : params) {
            if (isBlank(obj)) {
                return true;
            }
        }
        return false;
    }

    /**
     * @param source 超类
     * @return String[]
     */
    public static String[] getNullPropertyNames(Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<String>();
        for (java.beans.PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) {
                emptyNames.add(pd.getName());
            }
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }

    /**
     * @param src    文件
     * @param target 目标文件
     */
    public static void copyPropertiesIgnoreNull(Object src, Object target) {
        BeanUtils.copyProperties(src, target, getNullPropertyNames(src));
    }
}
