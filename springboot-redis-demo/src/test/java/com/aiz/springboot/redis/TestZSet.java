package com.aiz.springboot.redis;

import com.aiz.springboot.redis.utils.RedisUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;

import java.util.Objects;
import java.util.Optional;
import java.util.Set;

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
        redisTemplate.getConnectionFactory().getConnection();
        for (int i = 0; i < 100; i++) {
            redisTemplate.opsForZSet().add("zset-key", "aaaa" + i, 4000 + i);
        }
        Long size = redisTemplate.opsForZSet().count("zset-key", 0, System.currentTimeMillis());
        System.out.println(size);
    }

    @Test
    void queryByPage() {
        Integer pageNo = 1;
        Integer pageSize = 20;

        Long offset = (long) (pageNo - 1) * pageSize;
        Long count = Long.valueOf(pageSize);
        Set<String> set = redisTemplate.opsForZSet().rangeByScore("zset-key", 0, Integer.MAX_VALUE, offset, count);
        for (String s : set) {
            System.out.println(s);
        }
    }

    @Test
    void f(){

        Set<ZSetOperations.TypedTuple<String>> tuples = redisTemplate.opsForZSet().reverseRangeWithScores("zset-key", 0, 0);

        Optional<ZSetOperations.TypedTuple<String>> first = tuples.stream().findFirst();
        if (first.isPresent()) {
            System.out.println(first.get().getScore());
        }
    }

}
