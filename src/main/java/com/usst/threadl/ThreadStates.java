package com.usst.threadl;

public class ThreadStates {

    public static void main(String[] args) {

        //NEW
        Thread t1 = new Thread("t1") {

            @Override
            public void run() {
                System.out.println("t1 is running ...");
            }
        };


        Thread t2 = new Thread("t2") {

            @Override
            public void run() {
                while (true) {

                }
            }
        };

        t2.start();

        //TERMINATED
        Thread t3 = new Thread("t3") {

            @Override
            public void run() {
                System.out.println("t3 is running");
            }
        };
        t3.start();


        //timed_waiting
        Thread t4 = new Thread("t4") {
            @Override
            public void run() {
                //一直睡着，不会把这个锁释放调（一个锁可以锁一个方法，也可以锁多个方法？）
                synchronized (ThreadStates.class) {
                    try {
                        Thread.sleep(1000000000);    //一个有时现的等待timed_waiting
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        t4.start();


        //waiting
        Thread t5 = new Thread("t5") {
            @Override
            public void run() {
                try {

                    //在A线程中调用了B线程的join()方法时,表示只有当B线程执行完毕时,A线程才能继续执行
                    t2.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        t5.start();


        Thread t6 = new Thread("t6") {
            @Override
            public void run() {

                synchronized (ThreadStates.class) {   //拿不到锁就出现blocked
                    try {
                        Thread.sleep(1010000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        t6.start();

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("t1 state = "+ t1.getState());
        System.out.println("t2 state = "+ t2.getState());
        System.out.println("t3 state = "+ t3.getState());
        System.out.println("t4 state = "+ t4.getState());
        System.out.println("t5 state = "+ t5.getState());
        System.out.println("t6 state = "+ t6.getState());
    }

}
