package pers.can.manage.util;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisUtilTest {


    public static void main(String[] args) {
        RedisUtil redisUtil = getRedisUtil();
        String key = "name";
        String value = "8";

        Long incrCount = redisUtil.incr(key);
        redisUtil.setExpire(key, 1000);
        System.out.println("incrCount的值为:" + incrCount);
    }

    private static RedisUtil getRedisUtil() {
        //端口
        int port = 6379;
        //连接地址
        String host = "127.0.0.1";
        //密码
        String password = "123456";
        //最大空闲连接数
        int maxIdle = 300;
        //最大连接数
        int maxTotal = 6000;
        //最小空闲连接数
        int minIdle = 100;
        //获取连接时的最大等待毫秒数
        int maxWaitMillis = 1000;
        //超时时间
        int timeoutSecond = 5000;
        //选择第一个数据库
        int database = 0;
        //逐出连接的最小空闲时间
        long minEvictableIdleTimeMillis = 30000L;
        //检查一次连接池中空闲的连接的间隔时间
        long timeBetweenEvictionRunsMillis = 30000L;

        JedisPoolConfig jdConfig = new JedisPoolConfig();
        jdConfig.setMaxTotal(maxTotal);
        jdConfig.setMaxIdle(maxIdle);
        jdConfig.setMaxWaitMillis(maxWaitMillis);

        // 在获取一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的
        jdConfig.setTestOnBorrow(true);
        jdConfig.setTestWhileIdle(true);

        // 默认1800000毫秒(30分钟)
        jdConfig.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);

        // 1800000毫秒(30分钟)检查一次连接池中空闲的连接
        jdConfig.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);

        JedisPool jedisPool = new JedisPool(jdConfig, host, port, timeoutSecond, password, database);
        RedisUtil redisUtil = new RedisUtil();
        redisUtil.setJedisPool(jedisPool);
        return redisUtil;
    }

}
