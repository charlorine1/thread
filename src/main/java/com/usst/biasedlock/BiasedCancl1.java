package com.usst.biasedlock;

import org.openjdk.jol.info.ClassLayout;


/**
 * hashcode = 1528637575
 * 第一次输出：00000000 00000000 00000000 01011011 00011101 00101000 10000111 00000001   不可偏向状态：2进制值为1528637575
 * 第二次输出：00000000 00000000 00000000 00000000 00011111 11110100 11101110 11110000   轻量级锁
 * 第三次输出：00000000 00000000 00000000 01011011 00011101 00101000 10000111 00000001   不可偏向状态：2进制值为1528637575
 *
 * */
public class BiasedCancl1 {

    public static void main(String[] args) {

        Cat2 cat2 = new Cat2();
        System.out.println(cat2.hashCode());
        Thread t1 = new Thread() {
            @Override
            public void run() {
                System.out.println(ClassLayout.parseInstance(cat2).toPrintable());
                synchronized (cat2) {
                    System.out.println(ClassLayout.parseInstance(cat2).toPrintable());
                }
                System.out.println(ClassLayout.parseInstance(cat2).toPrintable());
            }
        };
        t1.start();
    }
}

class Cat2{

}
