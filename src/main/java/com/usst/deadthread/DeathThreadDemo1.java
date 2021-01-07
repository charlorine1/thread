package com.usst.deadthread;

public class DeathThreadDemo1 {

    public static Object resourceA = new Object();
    public static Object resourceB = new Object();

    public static void main(String[] args) {

        DeadLockExample deadLockExample = new DeadLockExample();
        Runnable runnableA = new Runnable() {
            @Override
            public void run() {
                synchronized(resourceA) {
                    System.out.printf(
                            "[INFO]: %s get resourceA" + System.lineSeparator(),
                            Thread.currentThread().getName()
                    );
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.printf(
                            "[INFO]: %s trying to get resourceB" + System.lineSeparator(),
                            Thread.currentThread().getName()
                    );
                    synchronized(resourceB) {
                        System.out.printf(
                                "[INFO]: %s get resourceB" + System.lineSeparator(),
                                Thread.currentThread().getName()
                        );
                    }
                    System.out.printf(
                            "[INFO]: %s has done" + System.lineSeparator(),
                            Thread.currentThread().getName()
                    );
                }
            }
        };
        Runnable runnableB = new Runnable() {
            @Override
            public void run() {
                synchronized(resourceB) {
                    System.out.printf(
                            "[INFO]: %s get resourceB" + System.lineSeparator(),
                            Thread.currentThread().getName()
                    );
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.printf(
                            "[INFO]: %s trying to get resourceA" + System.lineSeparator(),
                            Thread.currentThread().getName()
                    );
                    synchronized(resourceA) {
                        System.out.printf(
                                "[INFO]: %s get resourceA" + System.lineSeparator(),
                                Thread.currentThread().getName()
                        );
                    }
                    System.out.printf(
                            "[INFO]: %s has done" + System.lineSeparator(),
                            Thread.currentThread().getName()
                    );
                }
            }
        };
        new Thread(runnableA).start();
        new Thread(runnableB).start();

    }

}

class DeadLockExample{
    public Object resourceA;
    public Object resourceB;
}
