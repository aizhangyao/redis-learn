package com.aiz.springboot.redis;

import com.aiz.springboot.redis.pojo.Address;
import com.aiz.springboot.redis.pojo.User;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.scripting.ScriptSource;
import org.springframework.scripting.support.ResourceScriptSource;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author ZhangYao
 * @version 1.0
 * @className TestLuaScripts
 * @description 测试lua脚本
 * @date Create in 20:11 2023/5/12
 */
@SpringBootTest
public class TestLuaScripts {

    @Autowired
    @Qualifier("redisTemplate")
    private RedisTemplate redisTemplate;

    @Test
    void testLuaScripts1() {
        DefaultRedisScript<Boolean> redisScript = new DefaultRedisScript<>();
        ScriptSource scriptSource = new ResourceScriptSource(
                new ClassPathResource("META-INF/scripts/check_and_set.lua"));
        redisScript.setResultType(Boolean.class);
        redisScript.setScriptSource(scriptSource);

        // 调用
        List<String> keys = new ArrayList<>();
        keys.add("stock");
        System.out.println(redisTemplate.execute(redisScript,
                new StringRedisSerializer(), new StringRedisSerializer(), keys, "4", "5"));
    }

    @Test
    void testLuaScripts2() {
        DefaultRedisScript<Boolean> redisScript = new DefaultRedisScript<>();
        ScriptSource scriptSource = new ResourceScriptSource(
                new ClassPathResource("META-INF/scripts/for_list.lua"));
        redisScript.setResultType(Boolean.class);
        redisScript.setScriptSource(scriptSource);

        // 调用
        List<String> keys = new ArrayList<>();
        keys.add("stock");
        System.out.println(redisTemplate.execute(redisScript,
                new StringRedisSerializer(), new StringRedisSerializer(), keys, "4", "5"));
    }

    // 插入List<String>
    @Test
    void testLuaScripts3() {
        // load redis lua script
        DefaultRedisScript<Boolean> redisScript = new DefaultRedisScript<>();
        ScriptSource scriptSource = new ResourceScriptSource(
                new ClassPathResource("META-INF/scripts/insert_names.lua"));
        redisScript.setResultType(Boolean.class);
        redisScript.setScriptSource(scriptSource);

        // preparation data
        ArrayList<String> names = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            String name = "name" + i;
            names.add(name);
        }
        // insert into redis (Transaction)
        System.out.println(redisTemplate.execute(redisScript, names));
    }

    // 插入List<User>
    @Test
    void testLuaScripts4() {
        // load redis lua script
        DefaultRedisScript<Boolean> redisScript = new DefaultRedisScript<>();
        ScriptSource scriptSource = new ResourceScriptSource(
                new ClassPathResource("META-INF/scripts/insert_user_objects.lua"));
        redisScript.setResultType(Boolean.class);
        redisScript.setScriptSource(scriptSource);

        // preparation data
        ArrayList<User> userList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            String name = "name" + i;
            User user = new User(name, i, new Address());
            userList.add(user);
        }
        ArrayList<String> userStrList = new ArrayList<>();
        for (User user : userList) {
            userStrList.add(JSONObject.toJSONString(user));
        }

        // insert into redis (Transaction)
        System.out.println(redisTemplate.execute(redisScript, userStrList));
    }
}
