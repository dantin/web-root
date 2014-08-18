package com.demo2do.core.cache;

/**
 * Cache Accessor
 *
 * @author David
 */
public interface CacheAccessor {

    /**
     * whether cache contains key
     *
     * @param key the input key
     * @return result
     */
    public boolean contains(String key);

    /**
     * get key from cache
     *
     * @param key the input key
     * @return the result
     */
    public Object evaluate(String key);
}
