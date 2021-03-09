package com.usst.park;


import java.util.concurrent.locks.LockSupport;

/**
 * 要测试的内容：
 *    线程有打断标记，会不会被park住
 *
 *           test1测试结果  ，两次返回的是true，  isInterrupted()   判断是否被打断， 不会清除 打断标记
 *           test2测试结果，第一次true，第二次false，interrupted() static 判断当前线程是否被打断 会清除打断标记
 *              test3测试：打断标记未true不能park住
 *
 *
 *
 */
public class ParkAndInterruptedDemo {

    public static void main(String[] args) throws Exception {

        //test1();
        //  test2();
         test3();

    }

    private static void test3() throws InterruptedException {
        Thread t1 = new Thread(() -> {
            System.out.println("调用park。。");
            LockSupport.park();
            System.out.println("park失效，unpark。。");
            //System.out.println("打断状态为：" + Thread.interrupted());   //interrupted 会清除打断标记,还能park住
            System.out.println("打断状态为：" + Thread.currentThread().isInterrupted());   //isInterrupted 不会清除打断标记,不能park住
            LockSupport.park();
            System.out.println("park失效，unpark。。");


        });

        t1.start();

        Thread.sleep(1000);  //1秒后打断t1
        t1.interrupt();
    }

    private static void test2() throws InterruptedException {
        Thread t1 = new Thread(() -> {
            System.out.println("调用park。。");
            LockSupport.park();
            System.out.println("park失效，unpark。。");
            System.out.println("打断状态为：" + Thread.interrupted());
            System.out.println("打断状态为：" + Thread.currentThread().isInterrupted());
        });

        t1.start();

        Thread.sleep(1000);  //1秒后打断t1
        t1.interrupt();
    }

    private static void test1() throws InterruptedException {
        Thread t1 = new Thread(() -> {
            System.out.println("调用park。。");
            LockSupport.park();
            System.out.println("park失效，unpark。。");
            System.out.println("打断状态为：" + Thread.currentThread().isInterrupted());
            System.out.println("打断状态为：" + Thread.currentThread().isInterrupted());
        });

        t1.start();

        Thread.sleep(1000);  //1秒后打断t1
        t1.interrupt();
        LockSupport.unpark(t1);
    }

}
