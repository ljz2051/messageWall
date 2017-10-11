package com.springapp.mvc.service.impl;

import com.springapp.mvc.commons.JedisPoolBuilder;
import com.springapp.mvc.service.IJedisService;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class JedisServiceImpl implements IJedisService{

    public void setAttrInJedis(String sessionId, Map<String, String> userInfo, int expireTime) throws Exception{
        JedisPool jedisPool = JedisPoolBuilder.getInstance().getJedisPool();
        Jedis jedis = null;
        try{
            jedis = jedisPool.getResource();
            jedis.hmset(sessionId, userInfo);
            System.out.println("userInfo:" + userInfo + "  expiretime: " + expireTime);
            jedis.expire(sessionId, expireTime);
            System.out.println("jedishmset:" + jedis.hgetAll(sessionId));
        } finally {
            if(jedis != null){
                jedis.close();
            }
        }
    }

    public Map<String, String> getAttrFromJedis(String sessionId) throws Exception{
        JedisPool jedisPool = JedisPoolBuilder.getInstance().getJedisPool();
        Jedis jedis = null;
        try{
            jedis = jedisPool.getResource();
            Map<String, String> infoMap = new HashMap<String, String>();
            infoMap = jedis.hgetAll(sessionId);
            return infoMap;
        } finally {
            if(jedis != null){
                jedis.close();
            }
        }
    }

    public static void main(String[] args){
        JedisPool jedisPool = JedisPoolBuilder.getInstance().getJedisPool();
        Jedis jedis = jedisPool.getResource();
        Map<String, String> map = new HashMap<String, String>();
        map.put("a","abc");
        jedis.hmset("m",map);
        Map<String,String> a = jedis.hgetAll("m");
        System.out.println(a.get("a"));
    }
}
