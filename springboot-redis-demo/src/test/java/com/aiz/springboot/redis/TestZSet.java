package com.aiz.springboot.redis;

import com.aiz.springboot.redis.utils.RedisUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @author ZhangYao
 * @version 1.0
 * @className TestZSet
 * @description TestZSet
 * @date Create in 14:56 2023/3/29
 */
@SpringBootTest
public class TestZSet {
    @Autowired
    @Qualifier("redisTemplate")
    private RedisTemplate redisTemplate;

    @Autowired
    private RedisUtil redisUtil;

    /**
     * 测试基本操作
     */
    @Test
    void contextLoads() {
        System.out.println("testZSet");
        RedisConnection connection = redisTemplate.getConnectionFactory().getConnection();
        redisTemplate.opsForZSet().add("zset-key","tuples",4000);
    }


}
