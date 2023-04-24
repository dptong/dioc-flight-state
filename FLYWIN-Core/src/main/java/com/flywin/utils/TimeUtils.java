/**
 * <li>文件名：TimeUtils.java
 * <li>说明：
 * <li>创建人： 曾明辉
 * <li>创建日期：2018年11月9日
 * <li>修改人：
 * <li>修改日期：
 */
package com.flywin.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @Description: TODO
 * @author: 曾明辉
 * @date: 2019年8月15日
 */
public final class TimeUtils {
    /**
     * 日志
     */
    private static final Logger logger = LoggerFactory.getLogger(TimeUtils.class);

    /**
     * @Title:
     * @Description:
     */
    private TimeUtils() {

    }

    /**
     * @return Date
     * @Title getNowTime
     * @Description TODO
     * @author 曾明辉
     * @date: 2019年8月15日
     */
    public static Date getNowTime() {
        return new Date();
    }

    /**
     * @return Date
     * @Title getNowDate
     * @Description TODO
     * @author 曾明辉
     * @date: 2019年8月15日
     */
    public static Date getNowDate() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);

        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        calendar.set(year, month, day, 0, 0, 0);
        return calendar.getTime();
    }

    /**
     * @param date 日期
     * @return String
     * @Title dateToStr
     * @Description 日期转换为"yyyy-MM-dd" 日期格式字符串
     * @author 曾明辉
     * @date: 2019年8月15日
     */
    public static String dateToStr(Date date) {
        return dateToStr(date, "yyyy-MM-dd");
    }

    /**
     * @param time 时间
     * @return String
     * @Title timeToStr
     * @Description 时间转换为"yyyy-MM-dd HH:mm:ss" 时间格式字符串
     * @author 曾明辉
     * @date: 2019年8月15日
     */
    public static String timeToStr(Date time) {
        return dateToStr(time, "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * @param date   时间
     * @param format 格式字符串
     * @return String
     * @Title dateToStr
     * @Description 时间转换为指定格式字符串
     * @author 曾明辉
     * @date: 2019年8月15日
     */
    public static String dateToStr(Date date, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }

    /**
     * @param strDate 日期字符串
     * @return Date
     * @Title strToDate
     * @Description "yyyy-MM-dd"格式日期字符串转为Date
     * @author 曾明辉
     * @date: 2019年8月15日
     */
    public static Date strToDate(String strDate) {
        return strToDate(strDate, "yyyy-MM-dd");
    }

    /**
     *
     * <li>方法名：strToTime
     * <li>@param strTime
     * <li>@return
     * <li>返回类型：Date
     * <li>说明：
     * <li>创建人：曾明辉
     * <li>创建日期：2018年11月9日
     * <li>修改人：
     * <li>修改日期：
     */
    /**
     * @param strTime 时间字符串
     * @return Date
     * @Title strToDate
     * @Description "yyyy-MM-dd HH:mm:ss"格式日期字符串转为Date
     * @author 曾明辉
     * @date: 2019年8月15日
     */
    public static Date strToTime(String strTime) {
        return strToDate(strTime, "yyyy-MM-dd HH:mm:ss");
    }

    /**
     *
     * <li>方法名：strToDate
     * <li>@param strDate
     * <li>@param format
     * <li>@return
     * <li>返回类型：Date
     * <li>说明：
     * <li>创建人：曾明辉
     * <li>创建日期：2018年11月9日
     * <li>修改人：
     * <li>修改日期：
     */
    /**
     * @param strDate 日期时间字符串
     * @param format  格式化
     * @return Date
     * @Title strToDate
     * @Description TODO
     * @author 曾明辉
     * @date: 2019年8月15日
     */
    public static Date strToDate(String strDate, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        try {
            return sdf.parse(strDate);
        } catch (ParseException e) {
            logger.error(e.getMessage(), e);

        }
        return null;
    }

    /**
     * @param date 时间
     * @return Date
     * @Title getBeginTimeOfDay
     * @Description 获取当日开始时间
     * @author 曾明辉
     * @date: 2019年10月17日
     */
    public static Date getBeginTimeOfDay(Date date) {
        if (date != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String dateStr = sdf.format(date);
            try {
                date = sdf.parse(dateStr);
                Calendar c = Calendar.getInstance();
                c.setTime(date);
                return c.getTime();
            } catch (ParseException e) {
                logger.error(e.getMessage(), e);

            }
        }
        return null;
    }

    /**
     * @param date 时间
     * @return Date
     * @Title getEndTimeOfDay
     * @Description 获取当日结束时间
     * @author 曾明辉
     * @date: 2019年10月17日
     */
    public static Date getEndTimeOfDay(Date date) {
        if (date != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String dateStr = sdf.format(date);
            try {
                date = sdf.parse(dateStr);
                Calendar c = Calendar.getInstance();
                c.setTime(date);
                c.add(Calendar.DAY_OF_YEAR, 1);
                c.add(Calendar.SECOND, -1);
                return c.getTime();
            } catch (ParseException e) {
                logger.error(e.getMessage(), e);

            }
        }
        return null;
    }

    /**
     * @param args 参数
     * @Title main
     * @Description TODO
     * @author 曾明辉
     * @date: 2019年8月15日
     */
    public static void main(String[] args) {
        System.out.println(TimeUtils.getNowDate().before(TimeUtils.getNowDate()));

        System.out.println(TimeUtils.getBeginTimeOfDay(new Date()));
        System.out.println(TimeUtils.getEndTimeOfDay(new Date()));
    }


    /**
     * 指定日期加上天数后的日期
     *
     * @param num  增加的天数
     * @param date 指定的时间
     * @return Date
     * @throws ParseException
     */
    public static Date plusDay(int num, Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, num);
        return cal.getTime();
    }

    /**
     * @param date 日期
     * @return java.util.Date
     * @Description 获取一年的开始时间
     * @Author wumin
     * @Date 2020-03-26 10:25
     */
    public static Date getYearStartTime(Date date) {
        Calendar calendar = Calendar.getInstance();
        if (date != null) {
            calendar.setTime(date);
        }
        calendar.add(Calendar.YEAR, 0);
        calendar.add(Calendar.DATE, 0);
        calendar.add(Calendar.MONTH, 0);
        calendar.set(Calendar.DAY_OF_YEAR, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * @param date 日期
     * @return java.util.Date
     * @Description 获取一年的开始时间
     * @Author wumin
     * @Date 2020-03-26 10:25
     */
    public static Date getYearEndTime(Date date) {
        Calendar calendar = Calendar.getInstance();
        if (date != null) {
            calendar.setTime(date);
        }
        int year = calendar.get(Calendar.YEAR);
        calendar.clear();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        calendar.roll(Calendar.DAY_OF_YEAR, -1);
        return calendar.getTime();
    }

}
