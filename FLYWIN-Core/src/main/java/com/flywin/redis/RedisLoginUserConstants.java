package com.flywin.redis;

/**
 * @ClassName: RedisLoginUserConstants
 * @Description: RedisLoginUserConstants
 * @Author: 冉小刚
 * @Date: 2021-9-2 10:16:03
 * @Version: 2.0
 */
public final class RedisLoginUserConstants {

    /**
     * PC端登录过期秒数：4小时
     */
    public static final Integer PC_REDIS_LOGIN_USER_TIMEOUT = 60 * 30 * 8;

    /**
     * APP端登录过期秒数：6个月
     */
    public static final Integer APP_REDIS_LOGIN_USER_TIMEOUT = 6 * 30 * 24 * 60 * 60;

}
