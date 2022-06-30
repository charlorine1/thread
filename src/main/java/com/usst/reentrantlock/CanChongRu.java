package com.usst.reentrantlock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


/**
 *可重入是指一个线程获取了某一把锁，那么他就是该把锁的拥有者，因此有权再次获得改把锁
 * 不可重入是指第二次去获取改把锁的时候，会被获取失败，被锁挡住
 */
public class CanChongRu {
    static ReentrantLock lock = new ReentrantLock();
    public static void main(String[] args) {
         m1();
    }
    public static void m1(){
        lock.lock();  //给该线程进行加锁，相当于synchronized("lock"),如果该线程获取成功了就进入到下面，如果阻塞的话则阻塞在该处
        try {
            System.out.println("m1 do something...");
            m2();
        }finally {
            lock.unlock();  // 相当于synchronized("lock") 大括号最后一个结束
        }
    }

    public static void m2(){
        lock.lock();
        try {
            System.out.println("m2  do something...");
        }finally {
            lock.unlock();
        }
    }
}
