package com.flywin.utils;

import com.flywin.enums.IntConstants;

import java.util.UUID;

/**
 * <li>类型名称：
 * <li>说明：
 * <li>创建人： 刘二奇
 * <li>创建日期：2019-7-25
 * <li>修改人：
 * <li>修改日期：
 */
public final class GuidUtils {

    /**
     * @Title:
     * @Description:
     */
    private GuidUtils() {

    }

    /**
     * @return String
     * @Title getUuid
     * @Description 用于产生文件名
     * @author 刘二奇
     * @date: 2019年8月19日
     */
    public static String getUuid() {
        /**
         * 获取uuid
         */
        String uuid = UUID.randomUUID().toString().replace("-", "");
        /**
         * 获取时间戳
         */
        long timeStamp = System.currentTimeMillis();
        /**
         * 截取uuid前19位
         */
        String uuidTime = uuid.substring(0, IntConstants.INT_19);
        /**
         * 将两个获取出来的值拼接在一起 返回
         */
        uuidTime += timeStamp;
        return uuidTime;
    }
}
