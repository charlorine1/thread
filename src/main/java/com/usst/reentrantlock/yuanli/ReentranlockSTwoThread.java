package com.usst.reentrantlock.yuanli;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author yang
 * @title: ReentranlockSTwoThread
 * @projectName thread
 * @description: TODO
 * @date 2022/6/2919:45
 */
@Slf4j
public class ReentranlockSTwoThread {

    static ReentrantLock lock = new ReentrantLock();

    public static void main(String[] args) {

        new Thread(() -> {
            lock.lock();
            try {
                log.info("获取到锁");
                TimeUnit.SECONDS.sleep(6000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"t1").start();


        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new Thread(() -> {
            log.info("尝试获取锁");
            lock.lock();


        },"t2").start();


        log.info("single thread");

    }
}
