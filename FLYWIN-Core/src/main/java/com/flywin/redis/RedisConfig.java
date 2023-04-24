/**
 * @Title: RedisConfig.java
 * @Package com.fa.redis.config
 * @Description: TODO
 * @author: 曾明辉
 * @date 2020年4月17日
 */
package com.flywin.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializationContext;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description: Redis缓存自动管理，使用Cacheable注解生效。
 * @author: 吴双
 * @date: 2020年8月31日
 */
@Configuration
public class RedisConfig {

    @Autowired
    RedisTemplate redisTemplate;

    @Bean
    public CacheManager cacheManager() {
        // 生成一个默认配置，通过config对象即可对缓存进行自定义配置
        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig();
        // 设置缓存的默认过期时间，也是使用Duration设置
        config = config.entryTtl(Duration.ZERO)
                // 设置 key为string序列化
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(redisTemplate.getStringSerializer()))
                // 设置value为json序列化
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(redisTemplate.getValueSerializer()))
                // 不缓存空值
                .disableCachingNullValues();

        // 对每个命名空间应用不同的过期时间
        Map<String, RedisCacheConfiguration> configMap = new HashMap<>();
        configMap.put("5s", config.entryTtl(Duration.ofSeconds(5)));
        configMap.put("1m", config.entryTtl(Duration.ofMinutes(1)));
        configMap.put("5m", config.entryTtl(Duration.ofMinutes(5)));
        configMap.put("15m", config.entryTtl(Duration.ofMinutes(15)));
        configMap.put("30m", config.entryTtl(Duration.ofMinutes(30)));
        configMap.put("1h", config.entryTtl(Duration.ofHours(1)));
        configMap.put("2h", config.entryTtl(Duration.ofHours(2)));
        configMap.put("4h", config.entryTtl(Duration.ofHours(4)));
        configMap.put("1d", config.entryTtl(Duration.ofDays(1)));
        configMap.put("30d", config.entryTtl(Duration.ofDays(30)));

        // 使用自定义的缓存配置初始化一个cacheManager
        RedisCacheManager cacheManager = RedisCacheManager.builder(redisTemplate.getConnectionFactory())
                .cacheDefaults(config)
                // 一定要先调用该方法设置初始化的缓存名，再初始化相关的配置
                .initialCacheNames(configMap.keySet())
                .withInitialCacheConfigurations(configMap)
                .build();
        return cacheManager;
    }
}
