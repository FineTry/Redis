package cn.itcast.test;

import org.junit.Test;
import redis.clients.jedis.Jedis;

import java.util.UUID;

/**
 * 3. list

 ​           多用于秒杀  --> 去模拟一个queue 队列
 * 实现一个queue队列
 */
public class RedisList {

    @Test
    public void product() {
        Jedis jedis = new Jedis("127.0.0.1", 6379);
        //模拟一个生产者队列
        while (true) {

            try {
                Thread.sleep(500);
                String uuid = UUID.randomUUID().toString();
                jedis.lpush("task-queue", uuid);
                System.out.println("生产了" + uuid);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }


    @Test
    public void consumer() {
        Jedis jedis = new Jedis("127.0.0.1", 6379);
        while (true) {
            long m = 500l + (long) (Math.random() * 500);
            try {
                Thread.sleep(m);
                String task = jedis.rpop("task-queue");
                //模拟一个随机失败的执行
                if (task!=null && !"".equals(task)){
                    jedis.lpush("tem-queue", task);
                    int num = (int) (Math.random()*13);
                    if (num % 7 == 0) {
                        //执行失败,把tem-queue的数据返回给
                        jedis.rpoplpush("tem-queue","task-queue");
                        System.out.println("执行失败...."+task);
                    } else {
                        //执行成功
                        jedis.rpop("tem-queue");
                        System.out.println("执行成功"+task);
                    }
                }else {
                    System.out.println("执行完了"+task);
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

    }

}
