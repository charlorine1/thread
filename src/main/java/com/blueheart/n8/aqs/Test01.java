package com.blueheart.n8.aqs;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedLongSynchronizer;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Test01 {
    public static void main(String[] args) {
      //  AbstractQueuedSynchronizer
        ReentrantLock lock = new ReentrantLock();
        new Thread(()->{
            lock.lock();
            try {
                try {
                    System.out.println("第一个线程获取了锁，开始睡眠10seconds");
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }finally {
                lock.unlock();
            }
        }).start();

        //第二个线程
/*        Thread t2 = new Thread(() -> {
          //  lock.lock();   打不断，t2还会继续等待，知道其他线程释放了锁
            try {
                lock.lockInterruptibly();
            } catch (InterruptedException e) {
                e.printStackTrace();
                System.out.println("等待中被打断");
                return;
            }
            try {
                System.out.println("第二个线程获取了锁");
            } finally {
                lock.unlock();
            }
        });
        t2.start();*/

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //第三个线程
        new Thread(()->{
           // boolean b = lock.tryLock();//尝试一次，要么获取要么结束
            boolean b =false ;
            try {
                 b = lock.tryLock(4000, TimeUnit.MILLISECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (!b){
                System.out.println("获取失败，立刻返回");
                return;      //如果该处没有return的话，会往下执行，此时会进行锁的释放，但是因为释放是把state从1->0, sync.release(1); 因为该锁的owner是t1，
                                    // 所以1=1，则要去判断当前线程是否是owner，不是的话直接报错 ，Exception in thread "Thread-1" java.lang.IllegalMonitorStateException
            }
            try {
                System.out.println("t3 获取到线程");
            }finally {
                lock.unlock();
            }
        }).start();

        //t2.interrupt();

    }

}
