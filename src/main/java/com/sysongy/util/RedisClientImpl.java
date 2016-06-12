package com.sysongy.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import redis.clients.jedis.JedisPoolConfig;

import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2016/5/26.
 */
public class RedisClientImpl implements RedisClientInterface{

    private static Logger logger = LoggerFactory.getLogger(RedisClientImpl.class);

    private JedisConnectionFactory connectionFactory;

    private RedisTemplate<String, Object> redisTemplate;

    public JedisConnectionFactory getConnectionFactory() {
        return connectionFactory;
    }

    public void setConnectionFactory(JedisConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public RedisTemplate<String, Object> getRedisTemplate() {
        return redisTemplate;
    }

    public void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void addToCache(String key, Object obj, int expireTime){
        ValueOperations<String, Object> valueops = redisTemplate.opsForValue();
        valueops.set(key, obj);
        if(expireTime > 0){
            redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
        }
        logger.info("----------Put the data in redis successful: key=" + key + "object=" + obj.toString() + "----------");
    }

    public Object getFromCache(String key){
        ValueOperations<String, Object> valueops = redisTemplate.opsForValue();
        logger.info("----------Get the data from redis successful: key=" + key + "----------");
        return valueops.get(key);
    }

    public void deleteFromCache(String key){
        logger.info("----------Delete the data from redis successful.----------");
        redisTemplate.delete(key);
    }
}
