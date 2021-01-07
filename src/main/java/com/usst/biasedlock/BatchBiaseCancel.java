package com.usst.biasedlock;

import org.openjdk.jol.info.ClassLayout;

import java.util.Vector;
import java.util.concurrent.locks.LockSupport;


/**
 * 1、第一步 开始全部偏向于t1 线程
 * 2、第二步 0~18 个对象偏向撤销、变成轻量级锁    19~38后面的重新批量重偏向于t2线程,  第一、二步是BatchRebiased.java中演示的步骤
 * 3、第三步 0~18 加轻量级锁，19~38后面的偏向撤销（以后全部都偏向撤销了，不会重偏向于t3了），变成轻量级锁，不会偏向于t3了
 * 4、打印阈值40的时候，jvm任务该类竞争太激烈了，不允许加偏向锁了，直接使用轻量级锁
 *
 * */
public class BatchBiaseCancel {
    static Thread t1,t2,t3;

    public static void main(String[] args) throws InterruptedException{
         test4();

    }


    private static void test4() throws InterruptedException {
        Vector<Dog> list = new Vector<>();
        int loopNumber = 42;
        t1 = new Thread(() -> {
            for (int i = 0; i < loopNumber; i++) {
                Dog d = new Dog();
                list.add(d);
                synchronized (d) {
                    System.out.println("i=============" + i);
                    System.out.println(ClassLayout.parseInstance(d).toPrintable());
                }
            }
            LockSupport.unpark(t2);
        }, "t1");
        t1.start();
        System.out.println("#################111111111111#################");
        t2 = new Thread(() -> {
            LockSupport.park();
            System.out.println("===============> ");
            for (int i = 0; i < loopNumber; i++) {
                Dog d = list.get(i);
                System.out.println("i=============" + i);
                System.out.println(ClassLayout.parseInstance(d).toPrintable());
                synchronized (d) {
                    System.out.println(ClassLayout.parseInstance(d).toPrintable());
                }
                System.out.println(ClassLayout.parseInstance(d).toPrintable());
            }
            LockSupport.unpark(t3);
        }, "t2");
        t2.start();
        System.out.println("#################2222222222222222#################");
        t3 = new Thread(() -> {
            LockSupport.park();
            System.out.println("===============> ");
            for (int i = 0; i < loopNumber; i++) {
                Dog d = list.get(i);
                System.out.println("i=============" + i);
                System.out.println(ClassLayout.parseInstance(d).toPrintable());
                synchronized (d) {
                    System.out.println(ClassLayout.parseInstance(d).toPrintable());
                }
                System.out.println(ClassLayout.parseInstance(d).toPrintable());
            }
        }, "t3");
        t3.start();
        System.out.println("#################333333333#################");
        t3.join();
        System.out.println("--------------------------------------------------");
        System.out.println(ClassLayout.parseInstance(new Dog()).toPrintable());
    }


}

class Dog{

}
