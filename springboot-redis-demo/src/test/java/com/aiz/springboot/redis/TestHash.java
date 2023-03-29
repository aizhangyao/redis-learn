package com.aiz.springboot.redis;

import com.aiz.springboot.redis.pojo.Address;
import com.aiz.springboot.redis.pojo.User;
import com.aiz.springboot.redis.utils.RedisUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName TestHash
 * @Description TODO
 * @Author ZhangYao
 * @Date Create in 15:55 2022/12/1
 * @Version 1.0
 */
@SpringBootTest
public class TestHash {
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
        // redisTemplate 操作不同的数据类型，api和我们的指令是一样的
        // opsForValue 操作字符串 类似String
        // opsForList 操作List 类似List
        // opsForSet
        // opsForHash
        // opsForZSet
        // opsForGeo
        // opsForHyperLogLog

        // 除了基本的操作，我们常用的方法都可以直接通过redisTemplate操作，比如事务和基本的CURD

        // 获取redis的连接对象
        RedisConnection connection = redisTemplate.getConnectionFactory().getConnection();
        connection.flushDb();

        User user = new User();
        user.setName("黑心虎");
        user.setAge(101);
        Address address = new Address();
        address.setCity("Shanghai");
        user.setAddress(address);
//        user.setAddress(null);
        System.out.println(JSONObject.toJSONString(user));
        Map<String, Object> cartHeadMap = JSON.parseObject(JSONObject.toJSONString(user)).getInnerMap();
        redisTemplate.opsForHash().putAll("o2om:cart:cache:0:{9343757782272}:APP:{default}", cartHeadMap);



//        redisTemplate.opsForValue().set("name-key-1", "zhangyao");
//        System.out.println(redisTemplate.opsForValue().get("name-key-1"));
//
//        redisTemplate.opsForValue().set("name-key-2", "夜不收");
//        System.out.println(redisTemplate.opsForValue().get("name-key-2"));

    }

    @Test
    void test() {
        String userBuyRecordKey = "o2om:order:buy:0:{9343757782272}:APP";
        Map<String, Object> boughtMap = new HashMap<>();
        boughtMap.put("ALL-001", 1);
        boughtMap.put("ALL-002", 2);
        boughtMap.put("ALL-003", 3);
        boughtMap.put("ALL-004", 4);
        redisTemplate.opsForHash().putAll(userBuyRecordKey, boughtMap);


        Map<String, Object> boughtMap1 = new HashMap<>();
        boughtMap1.put("ALL-001", 0);
        boughtMap1.put("ALL-003", 3);
        boughtMap1.put("ALL-004", 4);
        redisTemplate.opsForHash().putAll(userBuyRecordKey, boughtMap1);

        // Object o = redisTemplate.opsForHash().get(userBuyRecordKey, "ALL-001");
        List spuList = new ArrayList();
        spuList.add("ALL-001");
        spuList.add("ALL-005");
        spuList.add("ALL-001");
        List list = redisTemplate.opsForHash().multiGet(userBuyRecordKey, spuList);
        System.out.println(list);

        Map entries = redisTemplate.opsForHash().entries(userBuyRecordKey);
        System.out.println(JSONObject.toJSONString(entries));
        //redisTemplate.opsForHash().putAll(userBuyRecordKey, boughtMap);
    }

}
