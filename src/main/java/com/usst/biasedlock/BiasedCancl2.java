package com.usst.biasedlock;


import org.openjdk.jol.info.ClassLayout;

/**
 * 其他线程给该对象加锁(不能再同一时刻)
 * 第一次打印：00000000 00000000 00000000 00000000 00000000 00000000 00000000 00000101   可偏向状态
 * 第二、三、四次打印：
 *           00000000 00000000 00000000 00000000 00011110 11110101 01101000 00000101  可偏向，并且偏向了t1线程。
 * 第五次打印：00000000 00000000 00000000 00000000 00100000 01011011 11110110 00000000  轻量级 ，且后面是轻量级锁记录
 * 第六次打印：00000000 00000000 00000000 00000000 00000000 00000000 00000000 00000001  不可偏向状态
 *
 *
 * */
public class BiasedCancl2 {
    static  Thread t1 , t2;

    public static void main(String[] args) throws Exception{

        Cat3 cat3 = new Cat3();
        t1 = new Thread("t1"){
            @Override
            public void run() {
                System.out.println(ClassLayout.parseInstance(cat3).toPrintable());
                synchronized (cat3){
                    System.out.println(ClassLayout.parseInstance(cat3).toPrintable());
                }
                System.out.println(ClassLayout.parseInstance(cat3).toPrintable());
                synchronized (BiasedCancl2.class){
                    BiasedCancl2.class.notify();
                }
            }
        };
        t1.start();

        Thread.sleep(5000);

        t2 = new Thread("t2"){
            @Override
            public void run() {
                //先让 t2 开启，然后让他等待
                synchronized (BiasedCancl2.class){
                    try {
                        BiasedCancl2.class.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println(ClassLayout.parseInstance(cat3).toPrintable());
                synchronized (cat3){
                    System.out.println(ClassLayout.parseInstance(cat3).toPrintable());
                }
                System.out.println(ClassLayout.parseInstance(cat3).toPrintable());
            }
        };
        t2.start();
    }
}


class Cat3{

}