/**
 * <li>文件名：RedisTransEncryptManager.java
 * <li>说明：
 * <li>创建人： 曾明辉
 * <li>创建日期：2018年11月20日
 * <li>修改人：
 * <li>修改日期：
 */
package com.flywin.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * <li>类型名称：
 * <li>说明：
 * <li>创建人： 曾明辉
 * <li>创建日期：2018年11月20日
 * <li>修改人：
 * <li>修改日期：
 */
@Component
public class RedisTransEncryptManager {

    /**
     * redis操作
     */
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     *
     * @Title setTransEncryptKey
     * @Description TODO
     * @param transToken
     *            传输token
     * @param encryptKey
     *            秘钥
     * @author 曾明辉
     * @date: 2019年8月14日
     */
    public void setTransEncryptKey(String transToken, String encryptKey) {
        stringRedisTemplate.opsForValue().set(transToken, encryptKey, Constants.REDIS_TRANS_ENCRYPT_TIMEOUT,
                TimeUnit.SECONDS);
    }

    /**
     *
     * @Title getAndDelTransEncryptToken
     * @Description 传输Token仅使用一次, 获取并删除传输Token
     * @param transToken
     *            传输token
     * @return String
     * @author 曾明辉
     * @date: 2019年8月14日
     */
    public String getAndDelTransEncryptToken(String transToken) {
        String redisEncryptKey = stringRedisTemplate.opsForValue().get(transToken);
        if (redisEncryptKey != null) {
            stringRedisTemplate.delete(transToken);
        }
        return redisEncryptKey;
    }

}
