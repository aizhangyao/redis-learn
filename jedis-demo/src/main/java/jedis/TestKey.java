package jedis;

import redis.clients.jedis.Jedis;

import java.util.Set;

/**
 * @ClassName TestKey
 * @Description 测试Redis Key命令
 * @Author ZhangYao
 * @Date Create in 23:16 2022/8/16
 * @Version 1.0
 */
public class TestKey {
    public static void main(String[] args) {
        Jedis jedis = new Jedis("127.0.0.1", 6379);
        jedis.auth("redis");

        System.out.println("清空数据:" + jedis.flushDB());
        System.out.println("判断某个键是否存在:" + jedis.exists("username"));
        System.out.println("新增<'username','夜不收'>的键值对:" + jedis.set("username", "夜不收"));
        System.out.println("新增<'password','123456'>的键值对:" + jedis.set("password", "123456"));

        Set<String> keys = jedis.keys("*");
        System.out.println("系统中所有键如下:" + keys);

        System.out.println("删除键password:" + jedis.del("password"));
        System.out.println("判断键password是否存在:" + jedis.exists("password"));
        System.out.println("查看键username所存储的类型:" + jedis.type("username"));

        System.out.println("随机返回key空间中一个:" + jedis.randomKey());
        System.out.println("重命名key:" + jedis.rename("username", "name"));
        System.out.println("取出改后的name:" + jedis.get("name"));

        System.out.println("按索引查询:" + jedis.select(0));
        System.out.println("删除当前选择数据库中的所有key:" + jedis.flushDB());
        System.out.println("返回当前数据库中Key的数目:" + jedis.dbSize());
        System.out.println("删除所有数据库中的所有key:" + jedis.flushAll());
    }
}
