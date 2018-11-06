package com.hiramexpress.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class RedisService {

    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    public RedisService(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    public void set(String key, String value, Long time) {
        stringRedisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
    }

    public String get(String key) {
        return stringRedisTemplate.opsForValue().get(key);
    }

    public String get(Integer userid) {
        return stringRedisTemplate.opsForValue().get(userid);
    }
}
