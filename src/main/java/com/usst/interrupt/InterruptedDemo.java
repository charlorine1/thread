package com.usst.interrupt;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author yang
 * @title: InterruptedDemo
 * @projectName thread
 * @description: entryLock 的锁打断
 * @date 2022/6/2712:41
 */
@Slf4j
public class InterruptedDemo {

    static final ReentrantLock lock = new ReentrantLock();


    public static void main(String[] args) {


        new Thread(() -> {
            try {
                // lock.lock();
                try {
                    lock.lockInterruptibly();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                while (true) {
                    try {
                        TimeUnit.SECONDS.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    log.info("获得锁");
                }
            } finally {
                lock.unlock();
            }
        }, "t1").start();


        new Thread(() -> {
            try {
                lock.lock();
                while (true) {
                    try {
                        TimeUnit.SECONDS.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    log.info("获得锁");
                }

            } finally {
                lock.unlock();
            }
        }, "t1").start();


    }


}
