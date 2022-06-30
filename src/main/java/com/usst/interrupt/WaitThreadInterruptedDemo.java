package com.usst.interrupt;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author yang
 * @title: WaitThreadInterruptedDemo
 * @projectName thread
 * @description: TODO
 * @date 2022/6/2919:14
 */

@Slf4j
public class WaitThreadInterruptedDemo {

    static ReentrantLock lock = new ReentrantLock();

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            lock.lock();
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
                log.info("当前线程被打断");
            }

            lock.unlock();
        });

        Thread t2 = new Thread(() -> {
            lock.lock();
            lock.unlock();
            log.info("当前线程释放锁");
        });

        t1.start();
        t2.start();

        Thread.sleep(100);
        t1.interrupt();


        while (true){

        }

    }


}
