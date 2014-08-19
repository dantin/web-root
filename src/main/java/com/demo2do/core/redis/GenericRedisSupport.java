package com.demo2do.core.redis;

import com.demo2do.core.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Value;
import redis.clients.jedis.JedisPool;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Generic Redis Support
 *
 * @author David
 */
public class GenericRedisSupport {

    private JedisTemplate jedisTemplate;

    @Value("#{redis['namespace']}")
    private String namespace;

    /**
     * @param jedisPool the jedisPool to set
     */
    public void setJedisPool(JedisPool jedisPool) {
        this.jedisTemplate = new JedisTemplate(jedisPool);
    }

    /**
     * add new string into set
     *
     * @param key     the key
     * @param members the members
     * @return result
     */
    public Boolean addMembersToSet(String key, String... members) {
        return jedisTemplate.sadd(namespace + ":" + key, members);
    }

    /**
     * add new object into set, every member needs to be serialized to string
     *
     * @param key     the key
     * @param members the members
     * @return result
     */
    public Boolean addMembersToSet(String key, Object... members) {
        String[] omembers = new String[members.length];
        for (int i = 0; i < members.length; i++) {
            omembers[i] = JsonUtils.toJsonString(members[i]);
        }
        return this.addMembersToSet(key, omembers);
    }

    /**
     * add new string into sorted set
     *
     * @param key    the key
     * @param member the member
     * @param score  the score
     * @return result
     */
    public Boolean addMemberToSortedSet(String key, String member, double score) {
        return jedisTemplate.zadd(namespace + ":" + key, member, score);
    }

    /**
     * add new object into sorted set
     *
     * @param key    the key
     * @param member the member
     * @param score  the score
     * @return result
     */
    public Boolean addMemberToSortedSet(String key, Object member, double score) {
        return jedisTemplate.zadd(namespace + ":" + key, JsonUtils.toJsonString(member), score);
    }

    /**
     * get all the members as a set of string according to a key
     *
     * @param key the key
     * @return result set
     */
    public Set<String> getMembersForSet(String key) {
        return jedisTemplate.smembers(namespace + ":" + key);
    }

    /**
     * get all the members as a set of object according to a key
     *
     * @param key   the key
     * @param clazz the class type
     * @return result type
     */
    public <T> Set<T> getMembersForSet(String key, Class<T> clazz) {
        Set<String> smembers = this.getMembersForSet(key);
        Set<T> omembers = new HashSet<T>(smembers.size());
        for (String member : smembers) {
            omembers.add(JsonUtils.parse(member, clazz));
        }
        return omembers;
    }

    /**
     * get all the members as a set of string according to a key and order asce
     *
     * @param key the key
     * @return result set
     */
    public Set<String> getMembersForSortedSet(String key) {
        return jedisTemplate.zrange(namespace + ":" + key, 0, -1);
    }

    /**
     * get all the members as a set of object according to a key and order asce
     *
     * @param key   the key
     * @param clazz the class type
     * @return result set
     */
    public <T> Set<T> getMembersForSortedSet(String key, Class<T> clazz) {
        Set<String> smembers = this.getMembersForSortedSet(key);
        Set<T> omembers = new LinkedHashSet<T>(smembers.size());
        for (String member : smembers) {
            omembers.add(JsonUtils.parse(member, clazz));
        }
        return omembers;
    }

    /**
     * get all the members as a set of string according to a key and order desc
     *
     * @param key the key
     * @return result set
     */
    public Set<String> getReverseMembersForSortedSet(String key) {
        return jedisTemplate.zrevrange(namespace + ":" + key, 0, -1);
    }

    /**
     * get all the members as a set of object according to a key and order desc
     *
     * @param key   the key
     * @param clazz the class type
     * @return result set
     */
    public <T> Set<T> getReverseMembersForSortedSet(String key, Class<T> clazz) {
        Set<String> smembers = this.getReverseMembersForSortedSet(key);
        Set<T> omembers = new LinkedHashSet<T>(smembers.size());
        for (String member : smembers) {
            omembers.add(JsonUtils.parse(member, clazz));
        }
        return omembers;
    }

    /**
     * get the size of set according to a key
     *
     * @param key the key
     * @return the size of set
     */
    public Long getSizeOfSet(String key) {
        return jedisTemplate.scard(namespace + ":" + key);
    }

    /**
     * get the size of sorted set according to a key
     *
     * @param key the key
     * @return the size of set
     */
    public Long getSizeOfSortedSet(String key) {
        return jedisTemplate.zcard(namespace + ":" + key);
    }

    /**
     * remove string from set according to key
     *
     * @param key     the key
     * @param members the members
     * @return result
     */
    public boolean removeMembersFromSet(String key, String... members) {
        return jedisTemplate.srem(namespace + ":" + key, members);
    }

    /**
     * remove object from set according to key
     *
     * @param key     the key
     * @param members the members
     * @return result
     */
    public boolean removeMembersFromSet(String key, Object... members) {
        String[] omembers = new String[members.length];
        for (int i = 0; i < members.length; i++) {
            omembers[i] = JsonUtils.toJsonString(members[i]);
        }
        return this.removeMembersFromSet(key, omembers);
    }

    /**
     * remove string from sorted set according to key
     *
     * @param key    the key
     * @param member the member
     * @return result
     */
    public boolean reomveMemberFromSortedSet(String key, String member) {
        return jedisTemplate.zrem(namespace + ":" + key, member);
    }

    /**
     * remove object from sorted set according to key
     *
     * @param key    the key
     * @param member the member
     * @return result
     */
    public boolean removeMemberFromSortedSet(String key, Object member) {
        return jedisTemplate.zrem(namespace + ":" + key, JsonUtils.toJsonString(member));
    }

    /**
     * expire key in give seconds time
     *
     * @param key     the key
     * @param seconds the expire time in seconds
     * @return result
     */
    public Long expire(String key, int seconds) {
        return jedisTemplate.expire(namespace + ":" + key, seconds);
    }

}
