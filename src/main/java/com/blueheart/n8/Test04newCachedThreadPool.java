package com.blueheart.n8;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class Test04newCachedThreadPool {
    static Logger log = LoggerFactory.getLogger(Test03ThreadPoolStatus.class);
    volatile static AtomicInteger  i  = new AtomicInteger(100);
    public static void main(String[] args) {


        ExecutorService executor = Executors.newCachedThreadPool();
        executor.execute(()->{
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.info("Test04newCachedThreadPool ==={}",i.getAndIncrement());
        });

        executor.execute(()->{
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.info("Test04newCachedThreadPool ==={}",i.getAndIncrement());
        });

        executor.execute(()->{
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.info("Test04newCachedThreadPool ==={}",i.getAndIncrement());
        });


        try {
            Thread.sleep(10000);
            log.info("10秒三个线程已经结束,重复利用");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        executor.execute(()->{
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.info("Test04newCachedThreadPool ==={}",i.getAndIncrement());
        });



        try {
            Thread.sleep(70000);
            log.info("70秒三个线程已经结束,创建第四个线程");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        executor.execute(()->{
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.info("Test04newCachedThreadPool ==={}",i.getAndIncrement());
        });


    }
}
