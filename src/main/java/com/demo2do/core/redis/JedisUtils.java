package com.demo2do.core.redis;

import redis.clients.jedis.Jedis;

/**
 * Jedis utility
 *
 * @author David
 */
public class JedisUtils {

    private static final String OK_CODE = "OK";

    private static final String OK_MULTI_CODE = "+OK";

    /**
     * 判断 是 OK 或 +OK.
     */
    public static boolean isStatusOk(String status) {
        return (status != null) && (OK_CODE.equals(status) || OK_MULTI_CODE.equals(status));
    }

    /**
     * 退出然后关闭Jedis连接。如果Jedis为null则无动作。
     */
    public static void closeJedis(Jedis jedis) {
        if ((jedis != null) && jedis.isConnected()) {
            try {
                try {
                    jedis.quit();
                } catch (Exception e) {
                }
                jedis.disconnect();
            } catch (Exception e) {
            }
        }
    }

}
