package cn.itcast.test;

import org.junit.Test;
import redis.clients.jedis.Jedis;

import java.util.Set;

/**
 * 4.  set

 ​         多适用于微博 -->  求共同好友
 */
public class RedisSet {


    @Test
    public void setTest(){
        Jedis jedis = new Jedis("127.0.0.1",6379);
        jedis.sadd("redis1","张三","李四","王五");
        jedis.sadd("redis2","张三","李狗蛋","王大强");

        Set<String> sdiff = jedis.sdiff("redis1", "redis2");
        for (String s : sdiff) {
            System.out.println(s);
        }
        System.out.println("--------------------------");
        Set<String> sunion = jedis.sunion("redis1", "redis2");
        for (String s : sunion) {
            System.out.println(s);
        }
        System.out.println("--------------------------");
        Set<String> sinter = jedis.sinter("redis1", "redis2");
        for (String s : sinter) {
            System.out.println(s);
        }
    }
}
