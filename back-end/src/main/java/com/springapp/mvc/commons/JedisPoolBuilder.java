package com.springapp.mvc.commons;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class JedisPoolBuilder {
    private JedisPool jedisPool;
    private static final String host = "127.0.0.1";
    private static final int port = 6379;
    private static JedisPoolBuilder jedisPoolBuilder = null;
    private JedisPoolBuilder(){
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxIdle(8);

        this.jedisPool = new JedisPool(config, host, port);
    }

    public static synchronized JedisPoolBuilder getInstance(){
        if(jedisPoolBuilder == null){
            jedisPoolBuilder = new JedisPoolBuilder();
        }
        return jedisPoolBuilder;
    }

    public JedisPool getJedisPool(){
        return this.jedisPool;
    }

}
