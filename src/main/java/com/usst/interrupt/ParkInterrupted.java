package com.usst.interrupt;

import java.util.concurrent.locks.LockSupport;

/**
 * @author yang
 * @title: ParkInterrupted
 * @projectName thread
 * @description: TODO
 * @date 2022/6/2921:20
 */
public class ParkInterrupted {

    public static void main(String[] args) {


        Thread t1 = new Thread(() -> {
            //interrupt()  会打断park 的线程

            LockSupport.park();
            System.out.println("park 下面");
        });
        t1.start();

        System.out.println("t1 线程打断 标记"+t1.isInterrupted());

        t1.interrupt();
        System.out.println("t1 线程打断 标记"+t1.isInterrupted());
        System.out.println("t1 线程打断 标记"+t1.isInterrupted());

/*
*    t1 线程打断 标记false
    t1 线程打断 标记true
    t1 线程打断 标记true
* */

    }


}
