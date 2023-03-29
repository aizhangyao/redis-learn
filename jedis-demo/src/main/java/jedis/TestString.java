package jedis;

import redis.clients.jedis.Jedis;

import java.util.concurrent.TimeUnit;

/**
 * @ClassName TestString
 * @Description 测试Redis String类型
 * @Author ZhangYao
 * @Date Create in 23:12 2022/8/16
 * @Version 1.0
 */
public class TestString {
    public static void main(String[] args) {
        Jedis jedis = new Jedis("127.0.0.1", 6379);
        jedis.auth("redis");

        jedis.flushDB();
        System.out.println("===============添加数据===============");
        System.out.println(jedis.set("key1","value1"));
        System.out.println(jedis.set("key2","value2"));
        System.out.println(jedis.set("key3","value3"));

        System.out.println("删除键key2:"+ jedis.del("key2"));
        System.out.println("获取键key2:"+ jedis.get("key2"));
        System.out.println("修改键key1:"+ jedis.set("key1","value1Changed"));
        System.out.println("获取键key1:"+ jedis.get("key1"));

        System.out.println("在key3后拼接值："+ jedis.append("key3","End"));
        System.out.println("获取键key3:"+ jedis.get("key3"));

        System.out.println("增加多个键值对："+jedis.mset("key01","value01","key02","value02","key03","value03"));
        System.out.println("获取多个键值对："+jedis.mget("key01","key02","key03"));
        System.out.println("删除多个键值对："+jedis.del("key01","key02"));
        System.out.println("获取多个键值对："+jedis.mget("key01","key02","key03"));

        jedis.flushDB();
        System.out.println("===============新增键值对防止覆盖原先值===============");
        System.out.println(jedis.setnx("key1","value1"));
        System.out.println(jedis.setnx("key2","value2"));
        System.out.println(jedis.setnx("key2","value2-new"));
        System.out.println(jedis.get("key1"));
        System.out.println(jedis.get("key2"));

        System.out.println("===============新增键值对并设置有效时间===============");
        System.out.println(jedis.setex("key3",2,"value3"));
        System.out.println(jedis.get("key3"));
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(jedis.get("key3"));

        System.out.println("===============获取原值更新为新值===============");
        System.out.println(jedis.getSet("key2","key2GetSet"));
        System.out.println(jedis.get("key2"));
        System.out.println("获取key2的值的子串:"+jedis.getrange("key2",2,4));

    }
}
