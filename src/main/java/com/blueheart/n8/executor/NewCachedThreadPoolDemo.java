package com.blueheart.n8.executor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 *
 *内部是使用了 SynchronousQueue 队列，它没有容量，没有线程来取是放不进去的（一手交钱、一手交货）
 *
 *
 */

public class NewCachedThreadPoolDemo {

    public static void main(String[] args) {

        ExecutorService executor= Executors.newCachedThreadPool();
        //创建一个线程放货,如果没有线程来取货，则下一次放货会处于阻塞状态，只有上一个货物被取走后才会往下走
        new Thread() {
            @Override
            public void run() {

            }
        };






    }

}
