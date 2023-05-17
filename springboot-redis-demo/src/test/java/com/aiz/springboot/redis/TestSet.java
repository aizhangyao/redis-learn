package com.aiz.springboot.redis;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @author ZhangYao
 * @version 1.0
 * @className TestSet
 * @description TestSet
 * @date Create in 15:54 2023/5/17
 */
@SpringBootTest
public class TestSet {
    @Autowired
    @Qualifier("redisTemplate")
    private RedisTemplate redisTemplate;

    @Test
    void contextLoads() {

    }
}
