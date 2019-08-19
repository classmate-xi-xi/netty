package com.example.demo.GongPeng;

import redis.clients.jedis.Jedis;

import java.util.Random;

public class xxxxx {
    private static Jedis jedis;

    public static void setJedis(Jedis jedis) {
        Random random = new Random();
        Integer i = random.nextInt();
        System.out.println("xxxxx");
        jedis.set("aa","xx");

    }

    public void xxx(){
        jedis =new Jedis("192.168.0.40",6379);
        setJedis(jedis);
        System.out.println("xxxxx");
    }

    public static void main(String[] args) {
        new xxxxx().xxx();
        for (long i=0;i<1000000;i++) {
            System.out.println(jedis.get(String.valueOf(i)));
        }
    }

}
