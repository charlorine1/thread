package com.usst.reentrantlock;


import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.ReentrantLock;


/**
 *
 * 该类：说明lock.lockInterruptibly() 如果在阻塞队列中的时候，是可以被打断的，不会死等下去
 */

/**
 * 如果被打断线程正在 sleep，wait，join 会导致被打断的线程抛出 InterruptedException，并清除打断标记 ；
 * 如果打断的正在运行的线程，则会设置 打断标记 ；(while(true))
 * park 的线程被打断，也会设置 打断标记，park相当于wait/notify
 * <p>
 * <p>
 * <p>
 * 打断标记 意思是： 使用是否被打断的时候为： 0
 */
@Slf4j
public class CanInterrupted {

    public static void main(String[] args) {
        ReentrantLock lock = new ReentrantLock();

        Thread t1 = new Thread() {
            @Override
            public void run() {
                log.info("去获取锁");
                try {
                    // 如果没竞争，和lock一样获得锁
                    //如果竞争进入阻塞队列，可以被其他线程用interrupt方法打断
                    lock.lockInterruptibly();   //去获取锁
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    log.info("锁等待过程被打断,没有获得到锁");
                    return;
                }
                try {
                    log.info("临界区获取锁对象成功");
                } finally {
                    lock.unlock();
                }
            }
        };

        //必须要由主线程来使用锁
        log.info("主线程获取了锁");
        lock.lock();
        t1.start();
        try {
            try {
                Thread.sleep(1000);
                t1.interrupt();
                log.info("执行了打断");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } finally {
            lock.unlock();
        }


    }

}
