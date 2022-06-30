package com.usst;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @author yang
 * @title: SleepDemo
 * @projectName thread
 * @description: 测试sleep 会不会是否锁
 * @date 2022/6/2822:09
 */
@Slf4j
public class SleepDemo {
    static ReentrantLock lock = new ReentrantLock();

    public static void main(String[] args) {

        new Thread(() -> {

            try {
                lock.lock();




            } finally {
                lock.unlock();
            }


        }, "t1").start();

    }

}
