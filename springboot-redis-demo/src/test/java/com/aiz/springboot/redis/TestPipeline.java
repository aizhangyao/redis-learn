package com.aiz.springboot.redis;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.List;

/**
 * @author ZhangYao
 * @version 1.0
 * @className TestPipeline
 * @description Redis管道技术
 * @date Create in 00:08 2023/6/27
 */

@SpringBootTest
public class TestPipeline {
    @Autowired
    @Qualifier("redisTemplate")
    private RedisTemplate redisTemplate;

    @Test
    void contextLoads() {
        List<Object> list = redisTemplate.executePipelined(new RedisCallback<Object>() {
            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                // 1.打开 pipeline
                connection.openPipeline();
                // 2.执行批量操作
                for (int i = 0; i < 10000; i++) {
                    String key = "pipeline:test:key_" + i;
                    String value = "value_" + i;
                    connection.set(key.getBytes(), value.getBytes());
                }
                // 3.结果返回：这里返回null
                return null;
            }
        });
        // 4.redisTemplate将最终结果汇总在最外层的list中

        // 5.查看管道批量操作返回的结果
        for (Object item : list) {
            System.out.println(item);
        }
    }

    @Test
    void delete() {
        for (int i = 0; i < 10000; i++) {
            String key = "pipeline:test:key_" + i;
            redisTemplate.delete(key);
        }
    }


}
