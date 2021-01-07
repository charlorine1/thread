package com.usst.biasedlock;


import org.openjdk.jol.info.ClassLayout;

public class Test {
    public static void main(String[] args) {

        //默认开启偏向锁
        Cat cat = new Cat();
        System.out.println(ClassLayout.parseInstance(cat).toPrintable());
        //00000000 00000000 00000000 00000000 00000000 00000000 00000000 00000101   偏向锁，没有锁的时候没有线程id
        new Thread("t1"){
            @Override
            public void run() {
                synchronized (cat){
                    System.out.println(ClassLayout.parseInstance(cat).toPrintable());
                    //00000000 00000000 00000000 00000000 00011111  00111100  01000000 00000101  偏向锁，已经第一次锁时候线程id
                }
            }
        }.start();

        System.out.println(ClassLayout.parseInstance(cat).toPrintable());
        //锁结束偏向不会撤销
        //00000000 00000000 00000000 00000000 00011111 00111100 01000000 00000101
    }
}

class Cat {

}
