package com.aiz.springboot.redis.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * @ClassName Address
 * @Description Address实体类
 * @Author ZhangYao
 * @Date Create in 16:09 2022/12/1
 * @Version 1.0
 */@Component
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Address implements Serializable {
     String city;
}
