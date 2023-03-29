package jedis;

import redis.clients.jedis.Jedis;

/**
 * @ClassName TestPing
 * @Description 测试Redis Ping命令
 * @Author ZhangYao
 * @Date Create in 19:28 2022/8/16
 * @Version 1.0
 */
public class TestPing {
    public static void main(String[] args) {
        // 1、new Jedis对象
        //Jedis jedis = new Jedis("127.0.0.1",6379);
        Jedis jedis = new Jedis("81.68.199.252",6379);
        
        jedis.auth("redis");
        // Jedis 所用的命令就是我们之前学习的所有指令
        System.out.println(jedis.ping());
    }
}
