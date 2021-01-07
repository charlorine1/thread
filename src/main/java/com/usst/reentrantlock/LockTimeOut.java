package com.usst.reentrantlock;


import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 1、发现请求不到锁直接返回,用的是无参
 * 2、指定时间内，如果获得锁就执行临界区代码，不然就是知道时间超时
 *
 *
 */
public class LockTimeOut {
    public static void main(String[] args) {
        ReentrantLock lock= new ReentrantLock();

        Thread t1 = new Thread(()->{
            //获取锁
            System.out.println("启动....");
            try {
                if( !lock.tryLock(6, TimeUnit.SECONDS)){
                    System.out.println("立刻获取锁失败");
                    return;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                System.out.println("成功获取锁..");
            }finally {
                lock.unlock();
            }
        });

        lock.lock();
        try {
            System.out.println("主线程获取锁");
            t1.start();
            try {
                Thread.sleep(5000 );
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }finally {
            lock.unlock();
        }

    }



}
