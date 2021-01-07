package com.usst.biasedlock;


import org.openjdk.jol.info.ClassLayout;

public class BiasedTest {
    public static void main(String[] args) throws Exception{

        // Thread.sleep(5000);

        //偏向锁是默认是延迟的，不会在程序启动时立即生效，如果想避免延迟，
        //可以加 VM 参数 -XX:BiasedLockingStartupDelay=0 来禁用延迟
        Cat1 cat = new Cat1();
        System.out.println(cat.hashCode());
        System.out.println(ClassLayout.parseInstance(cat).toPrintable());

        /*        Cat cat = new Cat();
         *//*        synchronized (cat){
            System.out.println(ClassLayout.parseInstance(cat).toPrintable());
        }*//*
        System.out.println(ClassLayout.parseInstance(cat).toPrintable());
        System.out.println(cat.hashCode());




        Thread.sleep(10000);
        Cat cat1 = new Cat();

        System.out.println(ClassLayout.parseInstance(cat1).toPrintable());
        System.out.println(cat1.hashCode());*/

    }
}

class Cat1{

}

