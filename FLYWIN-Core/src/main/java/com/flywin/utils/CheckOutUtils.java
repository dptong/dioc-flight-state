package com.flywin.utils;

import com.flywin.enums.CheckOutEnum;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 校验工具类
 */
public class CheckOutUtils {

    /**
     * @param value: 手机号
     * @Title checkOutCellPhone
     * @Description:校验手机号
     * @Author: Programmer
     * @Date: 2020/5/27
     * @return: boolean
     **/
    public static boolean checkOutCellPhone(String value) {
        // 手机号校验规则
        String mate = CheckOutEnum.CHECK_OUT_CELL_PHONE.getValue();
        Pattern p = Pattern.compile(mate);
        // 校验手机号
        Matcher m = p.matcher(value);
        return m.matches();
    }

    /**
     * @param value:
     * @Title checkOutPhone
     * @Description:校验电话号码
     * @Author: Programmer
     * @Date: 2020/5/27
     * @return: boolean
     **/
    public static boolean checkOutPhone(String value) {
        // 电话校验规则
        String mate = CheckOutEnum.CHECK_OUT_PHONE.getValue();
        Pattern p = Pattern.compile(mate);
        // 校验电话
        Matcher m = p.matcher(value);
        return m.matches();
    }

    /**
     * @param value:
     * @Title checkOutEmail
     * @Description:校验邮箱
     * @Author: Programmer
     * @Date: 2020/5/27
     * @return: boolean
     **/
    public static boolean checkOutEmail(String value) {
        // 邮箱校验规则
        String mate = CheckOutEnum.CHECK_OUT_EMAIL.getValue();
        Pattern p = Pattern.compile(mate);
        // 校验邮箱
        Matcher m = p.matcher(value);
        return m.matches();
    }

    /**
     * @param value:
     * @Title checkOutContactNumber
     * @Description: 联系电话的校验
     * @Author: Programmer
     * @Date: 2020/5/29
     * @return: boolean
     **/
    public static boolean checkOutContactNumber(String value) {
        // 联系号码校验规则
        String mate = CheckOutEnum.CHECK_OUT_CONTACT_NUMBER.getValue();
        Pattern p = Pattern.compile(mate);
        // 校验联系号码
        Matcher m = p.matcher(value);
        return m.find();
    }

    /**
     * @param value:
     * @Title checkOutEmail
     * @Description:校验中文
     * @Author: Programmer
     * @Date: 2020/5/27
     * @return: boolean
     **/
    public static boolean checkOutChinese(String value) {
        // 中文校验规则
        String mate = CheckOutEnum.CHECK_OUT_CHINESE.getValue();
        Pattern p = Pattern.compile(mate);
        // 校验中文
        Matcher m = p.matcher(value);
        return m.find();
    }

}
