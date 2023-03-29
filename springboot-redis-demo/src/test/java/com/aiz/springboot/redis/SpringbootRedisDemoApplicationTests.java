package com.aiz.springboot.redis;

import com.aiz.springboot.redis.pojo.User;
import com.aiz.springboot.redis.utils.RedisUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.scripting.ScriptSource;
import org.springframework.scripting.support.ResourceScriptSource;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class SpringbootRedisDemoApplicationTests {

    @Autowired
    @Qualifier("redisTemplate")
    private RedisTemplate redisTemplate;

    @Autowired
    private RedisUtil redisUtil;

    @Test
    public void testRedisUtil() {
        redisUtil.set("redis-util-key", "redis-util-value");
        System.out.println(redisUtil.get("redis-util-key"));
    }

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

        redisTemplate.opsForValue().set("name-key-1", "zhangyao");
        System.out.println(redisTemplate.opsForValue().get("name-key-1"));

        redisTemplate.opsForValue().set("name-key-2", "夜不收");
        System.out.println(redisTemplate.opsForValue().get("name-key-2"));
    }

    /**
     * 测试序列化
     */
    @Test
    public void test() throws JsonProcessingException {
        // 真实的开发一般使用json来传递对象
        User user = new User("夜不收", 26);

        // 这里直接设置对象，会报错，需要先序列化
        redisTemplate.opsForValue().set("user", user); //User(name=夜不收, age=26)

        String jsonUser = new ObjectMapper().writeValueAsString(user);
        redisTemplate.opsForValue().set("user", jsonUser); //{"name":"夜不收","age":26}
        Object userGet = redisTemplate.opsForValue().get("user");
        System.out.println(userGet);
    }


    @Test
    void testLuaScripts() {
        DefaultRedisScript<Boolean> redisScript = new DefaultRedisScript<>();
        ScriptSource scriptSource = new ResourceScriptSource(
                new ClassPathResource("META-INF/scripts/checkandset.lua"));
        redisScript.setResultType(Boolean.class);
        redisScript.setScriptSource(scriptSource);

        // 调用
        List<String> keys = new ArrayList<>();
        keys.add("stock");
        System.out.println(redisTemplate.execute(redisScript,
                new StringRedisSerializer(), new StringRedisSerializer(), keys, "4", "5"));
    }
}
