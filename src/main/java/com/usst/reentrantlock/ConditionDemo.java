package com.usst.reentrantlock;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


/**
 * 进行等待都是要先加锁的前提,不然会报错
 */
@Slf4j
public class ConditionDemo {
    private  static  boolean hasCigrette= false;
    private  static  boolean hasBreakfast= false;
   static  ReentrantLock lock = new ReentrantLock();
    static Condition waitCigaretteQueue = lock.newCondition();
    static Condition waitbreakfastQueue = lock.newCondition();

    public static void main(String[] args) {
        Thread t1 = new Thread("小南") {
            @Override
            public void run() {
                log.info("小南线程启动成功");
                lock.lock();               //类似与synchronized，是加在while的外面，
                while (!hasCigrette) {   //没烟就不让执行，进行等待
                    try {
                        log.info("小南没烟进行等待中....");
                        waitCigaretteQueue.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                try {
                    log.info("小南得到烟开始干活");
                } finally {
                    lock.unlock();
                }
            }
        };
        Thread t2 = new Thread("小女") {
            @Override
            public void run() {
                log.info("小女线程启动成功");
                lock.lock();               //类似与synchronized，是加在while的外面，
                while (!hasBreakfast) {   //没外卖就不让执行，进行等待
                    try {
                        log.info("小女没外卖进行等待中....");
                        waitbreakfastQueue.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                try {
                    log.info("小女拿到外卖开始干活");
                } finally {
                    lock.unlock();
                }
            }
        };

        t1.start();
        t2.start();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //1秒后开启送烟和外卖线程
        new Thread(){
            @Override
            public void run() {
                lock.lock();
                try {
                    //送烟
                    log.info("送烟开始...");
                    hasCigrette =true;
                    waitCigaretteQueue.signal();
                    log.info("送外卖开始。。");
                    hasBreakfast=true;
                    waitbreakfastQueue.signal();
                }finally {
                    lock.unlock();
                }
            }
        }.start();
    }


}
