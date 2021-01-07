package com.usst.park;


import java.util.concurrent.locks.LockSupport;

/**
 * park & unpark 可以先 unpark，而 wait & notify 不能先 notify
 */
public class ParkDemo1 {
    public static void main(String[] args) {
        Thread t1 = new Thread() {
            @Override
            public void run() {
                //2、t1线程进行park
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("park start..");
                LockSupport.park();
                //3、第一步的unpark唤醒，如果不起作用不会打印resume..,起作用则打印resume..
                System.out.println("resume...");
            }
        };
        t1.start();
        System.out.println("unpark..");
        LockSupport.unpark(t1);   //1、开始第一步就唤醒

    }
}
