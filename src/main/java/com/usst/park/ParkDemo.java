package com.usst.park;

import java.util.concurrent.locks.LockSupport;

/**
 * LockSupport.park();  暂定当前线程

 */
public class ParkDemo {
    public static void main(String[] args) throws Exception {
        Thread t1 = new Thread() {
            @Override
            public void run() {
                System.out.println("park start..");
                LockSupport.park();  //锁定当前线程
                System.out.println("resume..");
            }
        };
        t1.start();
        Thread.sleep(2000);
        System.out.println("unpark...");
        LockSupport.unpark(t1);   //唤醒上面的t1线程

        t1.interrupt();
        Thread.interrupted();


    }


}
