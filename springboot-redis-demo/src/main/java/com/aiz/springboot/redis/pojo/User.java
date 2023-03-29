package com.aiz.springboot.redis.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * @ClassName User
 * @Description User实体类
 * @Author ZhangYao
 * @Date Create in 09:40 2022/8/17
 * @Version 1.0
 */
@Component
@AllArgsConstructor
@NoArgsConstructor
@Data
public class User implements Serializable {
    private String name;
    private int age;
    private Address address;

    public User(String name, int age) {
        this.name = name;
        this.age = age;
    }
}
