/**
 * <li>文件名：RandomUtils.java
 * <li>说明：
 * <li>创建人： 曾明辉
 * <li>创建日期：2018年11月19日
 * <li>修改人：
 * <li>修改日期：
 */
package com.flywin.utils;

import java.util.Random;

/**
 * @Description: TODO
 * @author: 曾明辉
 * @date: 2019年8月15日
 */
public final class RandomStringUtils {

    /**
     * 所有的数字字符
     */
    public static final String ALLCHAR = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    /**
     * 字符
     */
    public static final String LETTERCHAR = "abcdefghijkllmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    /**
     * 数字
     */
    public static final String NUMBERCHAR = "0123456789";

    /**
     * @Title:
     * @Description:
     */
    private RandomStringUtils() {

    }

    private static Random random = new Random();

    /**
     * @param length 随机字符串长度
     * @return String
     * @Title generateString
     * @Description 返回一个定长的随机字符串(只包含大小写字母 、 数字)
     * @author 曾明辉
     * @date: 2019年8月15日
     */
    public static String generateString(int length) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            sb.append(ALLCHAR.charAt(random.nextInt(ALLCHAR.length())));
        }
        return sb.toString();
    }

    /**
     * @param length 随机字符串长度
     * @return String
     * @Title generateLetterString
     * @Description 返回一个定长的随机纯字母字符串(只包含大小写字母)
     * @author 曾明辉
     * @date: 2019年8月15日
     */
    public static String generateLetterString(int length) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            sb.append(LETTERCHAR.charAt(random.nextInt(LETTERCHAR.length())));
        }
        return sb.toString();
    }

    /**
     * @param length 字符串长度
     * @return String
     * @Title generateZeroString
     * @Description 生成一个定长的纯0字符串
     * @author 曾明辉
     * @date: 2019年8月15日
     */
    public static String generateZeroString(int length) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            sb.append('0');
        }
        return sb.toString();
    }

    /**
     * @param num       数字
     * @param fixdlenth 固定长度
     * @return String
     * @Title toFixdLengthString
     * @Description 根据数字生成一个定长的字符串，长度不够前面补0
     * @author 曾明辉
     * @date: 2019年8月15日
     */
    public static String toFixdLengthString(long num, int fixdlenth) {
        StringBuffer sb = new StringBuffer();
        String strNum = String.valueOf(num);
        if (fixdlenth - strNum.length() >= 0) {
            sb.append(generateZeroString(fixdlenth - strNum.length()));
        } else {
            throw new RuntimeException("将数字" + num + "转化为长度为" + fixdlenth + "的字符串发生异常！");
        }
        sb.append(strNum);
        return sb.toString();
    }


}
