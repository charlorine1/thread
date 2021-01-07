package com.usst.threadl;

import java.util.concurrent.locks.LockSupport;



/**
 * 1、park可以用来停止线程，interrupt可以用来打断停止的线程
 * 2、但是park停止被打断后，打断标记变为true，此时不能够再使用park进行停止线程
 * 3、但是可以在判断是否打断的方法里面，使用interrupted（） 会清除标记的方法,就在第二个park停止，不会执行最后一行
 *    System.out.println("unpark...");
 *
 * */
public class ParkDemo {

    public static void main(String[] args) {

        test1();
    }


    public static  void test1(){

       Thread t1 =  new Thread(){

            @Override
            public void run() {
                System.out.println("park...");

                LockSupport.park();
                System.out.println("unpark...");
                System.out.println("打断状态：" +  Thread.currentThread().isInterrupted());
              //  System.out.println("打断状态：" +  Thread.interrupted());

                LockSupport.park();
                System.out.println("unpark...");


            }
        };

       t1.start();

        try {
            Thread.sleep(1000);
            t1.interrupt();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }

}
