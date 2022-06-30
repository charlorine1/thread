package com.usst.reentrantlock.yuanli;


import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 单一线程获取 获取锁
 */
public class ReentranlockSingleThread {
    static ReentrantLock lock = new ReentrantLock();

    public static void main(String[] args) {

        new Thread(()->{
            lock.lock();
            try {
                System.out.println("获取到锁");
                TimeUnit.SECONDS.sleep(6000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


        }).start();

        System.out.println("single thread");


    }


}
