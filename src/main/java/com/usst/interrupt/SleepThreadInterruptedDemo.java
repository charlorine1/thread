package com.usst.interrupt;

/**
 * @author yang
 * @title: SleepThreadInterruptedDemo
 * @projectName thread
 * @description: TODO
 * @date 2022/6/2919:06
 */


import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 *
 * sleep 的线程进行 打断
 *
 */


@Slf4j
public class SleepThreadInterruptedDemo {


    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
                log.info("线程睡眠被打断");
            }


        }, "线程1");
        t1.start();

        log.info("t1线程的打断标记为：{}",t1.isInterrupted());
        Thread.sleep(100);

        t1.interrupt();
        log.info("t1线程的打断标记为：{}",t1.isInterrupted());
        log.info("t1线程的打断标记为：{}",t1.isInterrupted());
    }


}
