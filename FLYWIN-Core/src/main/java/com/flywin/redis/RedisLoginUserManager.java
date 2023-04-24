package com.flywin.redis;

import com.flywin.core.dto.SysLoginAttribute;
import com.flywin.core.dto.SysLoginUser;
import com.flywin.utils.MyStringUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.text.MessageFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @ClassName: RedisLoginUserManager
 * @Description: 登录用户Redis管理器
 * @Author: 冉小刚
 * @Date: 2021-4-9 09:49:01
 * @Version: 2.0
 */
@Component
public class RedisLoginUserManager {

    /**
     * 日志
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(RedisLoginUserManager.class);

    /**
     * 登录用户信息：token(String) -> SysLoginUser(json)] 用户登录终端：userId(String) ->
     * List<SysLoginAttribute>(json)
     */
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 用于转换对象格式
     */
    private static final Gson GSON = new Gson();

    /**
     * 存储登录信息
     *
     * @param loginUser 用户本次登录信息
     */
    public void setLoginUser(SysLoginUser loginUser) {
        Assert.notNull(loginUser.getToken(), "设置Redis登录用户的token不能为null");
        Assert.notNull(loginUser.getId(), "设置Redis登录用户的id不能为null");

        // 存储登录终端信息：userId -> SysLoginAttribute
        SysLoginAttribute loginAttribute = RedisLoginUserManager.buildAttribute(loginUser);
        // 追加登录终端
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        List<SysLoginAttribute> loginAttributes = null;
        try {
            loginAttributes = GSON.fromJson(valueOperations.get(loginUser.getId()),
                    new TypeToken<List<SysLoginAttribute>>() {
                    }.getType());
        } catch (Exception e) {
            loginAttributes = new ArrayList<>();
        }
        if (CollectionUtils.isEmpty(loginAttributes)) {
            loginAttributes = new ArrayList<>();
        } else {
            // 移除已过期的登录终端
            Iterator<SysLoginAttribute> iterator = loginAttributes.iterator();
            while (iterator.hasNext()) {
                SysLoginAttribute otherEquip = iterator.next();
                ValueOperations<String, String> tmpOperations = redisTemplate.opsForValue();
                SysLoginUser otherLoginEquip = GSON.fromJson(tmpOperations.get(otherEquip.getToken()),
                        SysLoginUser.class);
                if (ObjectUtils.isEmpty(otherLoginEquip)) {
                    iterator.remove();
                    LOGGER.info(MessageFormat.format(
                            "RedisLoginUserManager has removed the expired loginAttribute, key:{0} ,value:{1}",
                            loginUser.getId(), loginAttribute));
                }
            }
        }
        // 登录终端信息用不过期
        loginAttributes.add(loginAttribute);
        redisTemplate.opsForValue().set(loginUser.getId(), GSON.toJson(loginAttributes));
        LOGGER.info(MessageFormat.format("RedisLoginUserManager append loginAttribute success, key:{0} ,value:{1}",
                loginUser.getId(), loginAttribute));

        // 过期时间：pc -> 4小时，app -> 6个月
        int timeout = Arrays.asList("1", "2").contains(loginUser.getEquipmentCode())
                ? RedisLoginUserConstants.APP_REDIS_LOGIN_USER_TIMEOUT
                : RedisLoginUserConstants.PC_REDIS_LOGIN_USER_TIMEOUT;
        // 存储登录用户信息：token -> SysLoginUser
        redisTemplate.opsForValue().set(loginUser.getToken(), GSON.toJson(loginUser),
                timeout, TimeUnit.SECONDS);
        LOGGER.info(MessageFormat.format("RedisLoginUserManager set loginUser success, key:{0} ,value:{1}",
                loginUser.getToken(), loginUser));

    }

    /**
     * 获取登录用户信息
     *
     * @param token token
     * @return SysLoginUser
     */
    public SysLoginUser getLoginUserByToken(String token) {
        if (MyStringUtils.isBlank(token)) {
            LOGGER.error("RedisLoginUserManager get loginUser failed, the token is null");
            return null;
        }
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        SysLoginUser loginUser = GSON.fromJson(valueOperations.get(token), SysLoginUser.class);
        LOGGER.debug(MessageFormat.format("RedisLoginUserManager get loginUser success, key:{0} ,value:{1}", token,
                loginUser));

        return loginUser;
    }

    /**
     * 获取登录对象信息
     *
     * @param userId 用户Id
     * @return SysLoginUser
     */
    public List<SysLoginUser> getLoginUsersByUserId(String userId) {

        Assert.notNull(userId, "获取登录对象信息的userId不能为空");

        /** 获取登录终端信息 */
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        List<SysLoginAttribute> loginAttributes = GSON.fromJson(valueOperations.get(userId),
                new TypeToken<List<SysLoginAttribute>>() {
                }.getType());
        LOGGER.debug(MessageFormat.format("RedisLoginUserManager get loginAttribute success, key:{0} ,value:{1}", userId,
                loginAttributes));

        /** 获取SysLoginUser */
        List<SysLoginUser> loginUsers = loginAttributes.stream().map(item -> {
            ValueOperations<String, String> vo = redisTemplate.opsForValue();
            SysLoginUser loginUser = GSON.fromJson(vo.get(item.getToken()), SysLoginUser.class);
            LOGGER.debug(MessageFormat.format("RedisLoginUserManager get loginUser success, key:{0} ,value:{1}",
                    item.getToken(), loginUser));
            return loginUser;
        }).collect(Collectors.toList());
        return loginUsers;
    }

    /**
     * 获取登录终端信息
     *
     * @param userId 用户Id
     * @return List<SysLoginAttribute>
     */
    public List<SysLoginAttribute> getLoginAttributes(String userId) {

        Assert.notNull(userId, "获取登录终端信息的userId不能为空");

        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        List<SysLoginAttribute> loginAttributes = GSON.fromJson(valueOperations.get(userId),
                new TypeToken<List<SysLoginAttribute>>() {
                }.getType());
        LOGGER.debug(MessageFormat.format("RedisLoginUserManager get loginAttribute success, key:{0} ,value:{1}", userId,
                loginAttributes));

        return loginAttributes;
    }

    /**
     * refreshLoginUser
     *
     * @param loginUser 登录用户信息
     */
    public void refreshLoginUser(SysLoginUser loginUser) {
        // 过期时间
        Long expire = redisTemplate.getExpire(loginUser.getToken(), TimeUnit.SECONDS);
        if (expire == null || expire < Constants.REDIS_LOGIN_USER_EXPIRE) {

            // 过期时间：pc -> 4小时，app -> 6个月
            int timeout = Arrays.asList("1", "2").contains(loginUser.getEquipmentCode())
                    ? RedisLoginUserConstants.APP_REDIS_LOGIN_USER_TIMEOUT
                    : RedisLoginUserConstants.PC_REDIS_LOGIN_USER_TIMEOUT;
            // 刷新登录用户信息
            Boolean expireLoginUser = redisTemplate.expire(loginUser.getToken(), timeout,
                    TimeUnit.SECONDS);
            LOGGER.info(MessageFormat.format("RedisLoginUserManager refresh loginUser {0}, key:{1} ,expire time:{2}s",
                    expireLoginUser, loginUser.getToken(), timeout));
        }
    }

    /**
     * 注销登录
     *
     * @param token token
     */
    public void delete(String token) {
        /** 删除该token -> SysLoginUser */
        LOGGER.info(MessageFormat.format("RedisLoginUserManager delete loginUser start, key:{0}", token));
        SysLoginUser loginUser = this.getLoginUserByToken(token);
        this.redisTemplate.delete(token);
        LOGGER.info(MessageFormat.format("RedisLoginUserManager delete loginUser success, key:{0}", token));

        /** 移除token对应的登录设备 */
        if (!ObjectUtils.isEmpty(loginUser)) {
            String userId = loginUser.getId();
            List<SysLoginAttribute> loginAttributes = this.getLoginAttributes(userId);
            if (!CollectionUtils.isEmpty(loginAttributes)) {
                if (loginAttributes.size() > 1) {
                    /** 该账户在多点登录，移除对应的节点并重置 */
                    loginAttributes.removeIf(item -> token.equals(item.getToken()));
                    LOGGER.info(MessageFormat.format(
                            "RedisLoginUserManager remove loginAttribute success, the loginAttribute's token is:{0}",
                            token));

                    redisTemplate.opsForValue().set(loginUser.getId(), GSON.toJson(loginAttributes));
                    LOGGER.info(MessageFormat.format(
                            "RedisLoginUserManager reset loginAttribute success, key:{0},value:{1}", userId,
                            loginAttributes));
                } else {
                    /** 该账户最多只在一个终端登录，删除该userId -> SysLoginAttribute */
                    this.redisTemplate.delete(userId);
                    LOGGER.info(MessageFormat.format("RedisLoginUserManager delete loginAttribute success, key:{0}",
                            userId));
                }
            }
        }
    }

    /**
     * 构造登录属性对象
     *
     * @param sysLoginUser 本地登录信息
     * @return SysLoginAttribute
     */
    private static SysLoginAttribute buildAttribute(SysLoginUser sysLoginUser) {
        SysLoginAttribute loginAttribute = new SysLoginAttribute();
        /** 账号 */
        loginAttribute.setAccount(sysLoginUser.getAccount());
        /** 终端设备 */
        loginAttribute.setEquipmentCode(sysLoginUser.getEquipmentCode());
        /** 登录时间 */
        loginAttribute.setLoginTime(new Date());
        /** token */
        loginAttribute.setToken(sysLoginUser.getToken());
        return loginAttribute;
    }

}
