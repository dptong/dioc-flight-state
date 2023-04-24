/**
 * <li>文件名：BeanUtils.java
 * <li>说明：
 * <li>创建人： 曾明辉
 * <li>创建日期：2018年11月12日
 * <li>修改人：
 * <li>修改日期：
 */
package com.flywin.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * @Description: TODO
 * @author: 曾明辉
 * @date: 2019年8月15日
 */
public final class ReflectionUtils {
    /**
     * 日志
     */
    private static final Logger logger = LoggerFactory.getLogger(ReflectionUtils.class);

    /**
     * @Title:
     * @Description:
     */
    private ReflectionUtils() {

    }

    /**
     * @param entity    实体对象
     * @param fieldName 属性名
     * @return Object
     * @throws Exception 异常
     * @Title getFieldValue
     * @Description 递归获取以"."为分隔符的属性值
     * @author 曾明辉
     * @date: 2019年8月15日
     */
    public static Object getFieldValue(Object entity, String fieldName) throws Exception {
        if (entity == null) {
            return null;
        }

        int idx = fieldName.indexOf(".");
        if (idx > 0) { // 多级需要递归
            String subFieldName = fieldName.substring(0, idx);
            Object subObj = getSelfFieldValue(entity, subFieldName);

            String restFieldName = fieldName.substring(idx + 1);
            return getFieldValue(subObj, restFieldName);
        } else {
            return getSelfFieldValue(entity, fieldName);
        }
    }

    /**
     * @param entity    实体对象
     * @param fieldName 属性名
     * @return Object
     * @throws Exception 异常
     * @Title getSelfFieldValue
     * @Description 获取自身的属性值
     * @author 曾明辉
     * @date: 2019年8月15日
     */
    private static Object getSelfFieldValue(Object entity, String fieldName) throws Exception {
        Class<?> cls = entity.getClass();
        Field declaredField = getDeclaredField(entity, fieldName);
        if (declaredField == null) {
            throw new NoSuchFieldException(cls.getName() + "没有" + fieldName);
        }
        declaredField.setAccessible(true);
        PropertyDescriptor pd = new PropertyDescriptor(declaredField.getName(), cls);
        Method readMethod = pd.getReadMethod();

        return readMethod.invoke(entity);
    }


    /**
     * @param object    子类对象
     * @param fieldName 父类中的属性名
     * @return Field 父类中的属性对象
     * @Title getDeclaredField
     * @Description 循环向上转型, 获取对象的 DeclaredField
     * @author 曾明辉
     * @date: 2019年8月15日
     */
    public static Field getDeclaredField(Object object, String fieldName) {
        Field field = null;

        Class<?> clazz = object.getClass();

        for (; clazz != Object.class; clazz = clazz.getSuperclass()) {
            try {
                field = clazz.getDeclaredField(fieldName);
                return field;
            } catch (Exception e) {
                // 这里甚么都不要做！并且这里的异常必须这样写，不能抛出去。
                // 如果这里的异常打印或者往外抛，则就不会执行clazz = clazz.getSuperclass(),最后就不会进入到父类中了

            }
        }

        return null;
    }

    /**
     * @param cla       类
     * @param fieldName 父类中的属性名
     * @return Field 父类中的属性对象
     * @Title getDeclaredField
     * @Description 循环向上转型, 获取对象的 DeclaredField
     * @author 曾明辉
     * @date: 2019年8月15日
     */
    public static Field getDeclaredField(Class cla, String fieldName) {
        Field field = null;

        for (; cla != Object.class; cla = cla.getSuperclass()) {
            try {
                field = cla.getDeclaredField(fieldName);
                return field;
            } catch (Exception e) {
                // 这里甚么都不要做！并且这里的异常必须这样写，不能抛出去。
                // 如果这里的异常打印或者往外抛，则就不会执行clazz = clazz.getSuperclass(),最后就不会进入到父类中了

            }
        }

        return null;
    }

    /**
     * @param clazz 运行时类型标识
     * @return Class
     * @Title getSuperClassGenricType
     * @Description 获取泛型的具体类型
     * @author 陈嗣洪
     * @date: 2019年8月15日
     */
    public static Class getSuperClassGenricType(Class clazz) {
        return getSuperClassGenricType(clazz, 0);
    }

    /**
     * @param clazz 运行时类型标识
     * @param index 定位标识
     * @return Class
     * @Title getSuperClassGenricType
     * @Description 获取泛型的具体类型
     * @author 陈嗣洪
     * @date: 2019年8月15日
     */
    public static Class getSuperClassGenricType(Class clazz, int index) {
        Type genType = clazz.getGenericSuperclass();

        if (!(genType instanceof ParameterizedType)) {
            return Object.class;
        }

        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();

        if (index >= params.length || index < 0) {
            return Object.class;
        }

        if (!(params[index] instanceof Class)) {
            return Object.class;
        }

        return (Class) params[index];
    }


    /**
     * @param obj 超类
     * @return Class
     * @Title getAllSet
     * @Description 获取泛型的具体类型
     * @author 陈嗣洪
     * @date: 2019年8月15日
     */
    public static List<Set> getAllSet(Object obj) {
        Class clazz = obj.getClass();

        Field[] fields = clazz.getDeclaredFields();
        List<Set> myList = new ArrayList<Set>();

        if (fields != null && fields.length > 0) {
            for (Field myField : fields) {
                if (myField.getType() == Set.class) {
                    Object result = null;
                    try {
                        result = getSelfFieldValue(obj, myField.getName());
                    } catch (Exception e) {
                        logger.error(e.getMessage(), e);

                    }

                    if (result != null) {
                        Set mySet = (Set) result;
                        myList.add(mySet);
                    }
                }
            }
        }

        return myList;
    }

    /**
     * @param obj 超类
     * @return HashMap<String, Object>
     * @throws Exception 异常
     */
    public static HashMap<String, Object> objectToMapWithoutNull(Object obj) throws Exception {
        if (obj == null) {
            return null;
        }
        HashMap<String, Object> map = new HashMap<String, Object>();

        BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
        for (PropertyDescriptor property : propertyDescriptors) {
            String key = property.getName();
            if (key.compareToIgnoreCase("class") == 0) {
                continue;
            }
            Method getter = property.getReadMethod();
            Object value = getter != null ? getter.invoke(obj) : null;

            if (value != null) {
                map.put(key, value);
            }
        }

        return map;
    }

    /**
     * @param obj 超类
     * @return HashMap<String, Object>
     * @throws Exception 异常
     */
    public static HashMap<String, Object> objectToMap(Object obj) throws Exception {
        if (obj == null) {
            return null;
        }
        HashMap<String, Object> map = new HashMap<String, Object>();

        BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
        for (PropertyDescriptor property : propertyDescriptors) {
            String key = property.getName();
            if (key.compareToIgnoreCase("class") == 0) {
                continue;
            }
            Method getter = property.getReadMethod();
            Object value = getter != null ? getter.invoke(obj) : null;

            map.put(key, value);
        }

        return map;
    }

}
