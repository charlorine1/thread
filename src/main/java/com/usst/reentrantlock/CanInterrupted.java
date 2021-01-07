package com.usst.reentrantlock;


import java.util.concurrent.locks.ReentrantLock;

/**
 * 如果被打断线程正在 sleep，wait，join 会导致被打断的线程抛出 InterruptedException，并清除打断标记 ；
 * 如果打断的正在运行的线程，则会设置 打断标记 ；
 * park 的线程被打断，也会设置 打断标记，park相当于wait/notify
 */
public class CanInterrupted {

    public static void main(String[] args) {
        ReentrantLock lock = new ReentrantLock();

        Thread t1 = new Thread() {
            @Override
            public void run() {
                System.out.println("去获取锁");
                try {
                    lock.lockInterruptibly();   //去获取锁
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    System.out.println("锁等待过程被打断");
                    return;
                }
                try {
                    System.out.println("临界区获取锁对象成功");
                }finally {
                    lock.unlock();
                }
            }
        };

        //必须要由主线程来使用锁
        System.out.println("主线程获取了锁");
        lock.lock();
        t1.start();
        try {
            try {
                Thread.sleep(1000);
                t1.interrupt();
                System.out.println("执行了打断");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }finally {
            lock.unlock();
        }





    }

}
