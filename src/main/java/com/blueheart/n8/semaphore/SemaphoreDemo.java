package com.blueheart.n8.semaphore;

import java.util.concurrent.Semaphore;

public class SemaphoreDemo {

    public static void main(String[] args) throws InterruptedException {

        Semaphore semaphore = new Semaphore(3);
        for (int i = 0; i < 10; i++) {
            semaphore.acquire();
            new Thread(() -> {
                try {
                    System.out.println("thread:" + Thread.currentThread().getName() + "begin");
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    semaphore.release();
                }
                System.out.println("thread:" + Thread.currentThread().getName() + "end");

            }).start();
        }
    }
}

/*
        thread:Thread-1begin
        thread:Thread-0begin
        thread:Thread-2begin
        thread:Thread-1end
        thread:Thread-0end
        thread:Thread-2end
        thread:Thread-3begin
        thread:Thread-4begin
        thread:Thread-5begin
        thread:Thread-5end
*/
