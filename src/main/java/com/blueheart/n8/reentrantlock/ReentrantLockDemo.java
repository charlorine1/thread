package com.blueheart.n8.reentrantlock;


import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 目的：一个线程已经获取锁，另外2个线程如何进行处理
 *
 *
 */
public class ReentrantLockDemo {

    public static void main(String[] args) {

        ReentrantLock lock = new ReentrantLock(true);

        new Thread(()->{
            lock.lock();
            try {
                System.out.println("线程t1获取锁");

              //  Thread.sleep(100000);
            }catch (Exception e){
                e.printStackTrace();
            }
            finally {
                lock.unlock();
            }
        },"t1").start();

        new Thread(()->{
            System.out.println("t1尝试获得锁");

            lock.lock();
            try {
                System.out.println("t1获得锁");
            }finally {
                lock.unlock();
            }
        }).start();






    }


}
