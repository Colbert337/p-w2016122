package com.sysongy.util;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/5/26.
 */
public interface RedisClientInterface{

    /**
     * 调用该方法将对象缓存至Redis缓存
     * @param key
     * @param obj
     * @param expireTime 如果需要设置超时时间，则给一个正值，单位为秒
     */
    void addToCache(String key, Object obj, int expireTime);

    /**
     *调用该方法从Redis缓存获取数据
     * @param key
     * @return
     */
    Object getFromCache(String key);

    /**
     *调用该方法将对象删除Redis缓存
     * @param key
     * @return
     */
    void deleteFromCache(String key);

}
