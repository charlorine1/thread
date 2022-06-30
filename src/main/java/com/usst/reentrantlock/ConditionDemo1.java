package com.usst.reentrantlock;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author yang
 * @title: ConditionDemo1
 * @projectName thread
 * @description: TODO
 * @date 2022/6/2711:09
 */
@Slf4j
public class ConditionDemo1 {

    final static ReentrantLock lock = new ReentrantLock();

    // 默认没烟
    private static boolean hasCigerite = false;

    private static final Condition notCigerite = lock.newCondition();

    public static void main(String[] args) throws InterruptedException {

        new Thread(() -> {
            try {
                lock.lock();
                try {
                    //没烟 等待
                    if (!hasCigerite) {
                        log.info("小明没烟，不干活");
                        notCigerite.await();
                    }
                    log.info("获取到烟，开始干活");

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } finally {
                lock.unlock();
            }
        }, "小明").start();


        TimeUnit.SECONDS.sleep(1);

        new Thread(() -> {
            try {
                lock.lock();
                if (!hasCigerite) {
                    log.info("没烟开始送烟,让阻塞唤醒");
                    hasCigerite = true;
                    notCigerite.signal();   // 必须在阻塞的时候才能唤醒，且要在锁内部，不然会报错  ： IllegalMonitorStateException
                }
            } finally {
                lock.unlock();
            }

        }, "快递员").start();


    }

}
