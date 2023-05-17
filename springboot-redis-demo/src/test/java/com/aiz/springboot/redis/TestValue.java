package com.aiz.springboot.redis;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @author ZhangYao
 * @version 1.0
 * @className TestValue
 * @description 测试string类型
 * @date Create in 15:55 2023/5/17
 */
public class TestValue {
    @Autowired
    @Qualifier("redisTemplate")
    private RedisTemplate redisTemplate;

    @Test
    void contextLoads() {

    }
}
