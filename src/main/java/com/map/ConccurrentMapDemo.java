package com.map;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @author yang
 * @title: ConccurrentMapDemo
 * @projectName thread
 * @description: concurrentHashMap 源码分析
 * @date 2022/6/420:07
 */

///////////////////////////////////////////////////////////////////////////
//
///////////////////////////////////////////////////////////////////////////
public class ConccurrentMapDemo {



    public static void main(String[] args) throws Exception {

//        Map<String, String> map = new ConcurrentHashMap<>(16);
//        map.put("a","A");
//        map.put("b","B");
//        map.put("c","C");
//        map.put("d","D");
//
//        System.out.println(map.get("c"));

/*        CompletableFuture<Integer> result1 = CompletableFuture.supplyAsync(new Supplier<Integer>() {
            @Override
            public Integer get() {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return 12;
            }
        });*/

            CompletableFuture<Integer> result1 = CompletableFuture.supplyAsync(new Supplier<Integer>() {
                @Override
                public Integer get() {
                    try {
                        Thread.sleep(10000);
                        System.out.println("发送任务给provider" + Thread.currentThread().getName());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return 12;
                }
            });
        CompletableFuture<String> result = result1.thenApply(num -> String.valueOf(num));
        System.out.println("程序 is begin" + Thread.currentThread().getName());

        //System.out.println(result.get().getClass()); System.out.println(result.get());
        System.out.println("程序 is end" + Thread.currentThread().getName());


        Thread.sleep(5000000);


    }





}
