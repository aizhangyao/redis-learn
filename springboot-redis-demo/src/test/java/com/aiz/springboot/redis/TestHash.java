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
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @ClassName TestHash
 * @Description 使用RedisTemplate测试TestHash
 * @Author ZhangYao
 * @Date Create in 15:55 2022/12/1
 * @Version 1.0
 */
@SpringBootTest
public class TestHash {
    @Autowired
    @Qualifier("redisTemplate")
    private RedisTemplate redisTemplate;

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
        for (int i = 1; i <= 100; i++) {
            boughtMap.put("ALL-" + i, i);
        }
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

    /**
     * 分页
     */
    @Test
    void page() {
        Integer pageNo = 2;
        Integer pageSize = 20;

        int startIndex = (pageNo - 1) * pageSize;
        int maxIndex = pageNo * pageSize;

        ScanOptions scanOptions = ScanOptions.scanOptions().count(1000).build();
        int count = 0;
        try (Cursor<Map.Entry<String, String>> cursor = redisTemplate.<String, String>opsForHash().scan("o2om:order:buy:0:{9343757782272}:APP", scanOptions)) {
            while (cursor.hasNext()) {
                Map.Entry<String, String> next = cursor.next();
                if (count < startIndex) {
                    count++;
                    continue;
                }
                if (count >= maxIndex) {
                    break;
                }
                System.out.println(next.getKey());
                count++;
            }
        } catch (Exception e) {

        }
    }

    @Test
    void queryHashKey() {
        String redisKey = "o2user:notice:0:seriesLaunch:first:LJL004:OikE9EeFceUUYNrWHbY3OuZffQgViDbllfo9Gr7WTCs";
        String hashKey = "新系列-L2";
        redisTemplate.opsForHash().get(redisKey, hashKey);
//        JSONObject obj = JSON.parseObject(v);
//        String categoryCode = obj.getString("categoryCode");
//        String secondKey = obj.getString("secondKey");
//        String contentId = obj.getString("contentId");
//        System.out.println(categoryCode + "___" + secondKey + "___" + contentId);
    }

}
