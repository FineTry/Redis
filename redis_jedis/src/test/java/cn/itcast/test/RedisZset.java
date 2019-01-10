package cn.itcast.test;

import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

import java.util.Set;

//5. zset  可排序的集合
//
//   ​     优化购物车  (猜猜你喜欢)
//
//   ​     排行榜  lol  英雄出场率排行榜、
public class RedisZset {

    //英雄排行榜
    @Test
    public void play(){
        Jedis jedis = new Jedis("127.0.0.1",6379);
        String[] hero={"盖伦","易大师","提莫","皮城女警","赵信","虚空先知"};

        while(true){
            int index = (int)(Math.random()*6);
            try {
                Thread.sleep(1000);
                //按照分数添加
                jedis.zincrby("rank_list",1,hero[index]);
                System.out.println("这个英雄---"+hero[index]+"---在一次对局中被使用");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    //英雄排行榜查看
    @Test
    public void show(){
        Jedis jedis = new Jedis("127.0.0.1",6379);
        int i = 1;
        while(true){
            try {
                Thread.sleep(2000);
                System.out.println("第"+ (i++) +"次查看排行榜");
                //根据分数排名,查看从max到min的名称-->前三名
                Set<Tuple> set = jedis.zrevrangeWithScores("rank_list", 0, 2);
                for (Tuple tuple : set) {
                    System.out.println(tuple.getElement()+"出现了"+tuple.getScore()+"次");
                }
                System.out.println("---------------------------");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
