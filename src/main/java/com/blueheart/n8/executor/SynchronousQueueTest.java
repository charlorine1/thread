package com.blueheart.n8.executor;


import java.util.concurrent.SynchronousQueue;

/**
 *
 * 内部是使用了 SynchronousQueue 队列，它没有容量，没有线程来取是放不进去的（一手交钱、一手交货）
 * 1、创建一个线程放货,如果没有线程来取货，则下一次放货会处于阻塞状态，只有上一个货物被取走后才会往下走
 * 2、创建一个线程来取货，
 *
 */
public class SynchronousQueueTest {

    public static void main(String[] args)  throws Exception{
        SynchronousQueue<String> s = new SynchronousQueue<>();
        // 1、创建一个线程放货,如果没有线程来取货，则下一次放货会处于阻塞状态，只有上一个货物被取走后才会往下走
        new Thread(()->{
            try {
                s.put("1");
                System.out.println("存值1");
                s.put("2");
                System.out.println("存值2");
                s.put("3");
                System.out.println("存值3");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();


        Thread.sleep(1000);

        // 2、创建一个线程来取货，
        new Thread(()->{
            try {
                System.out.println("取值：" + s.take());
                System.out.println("取值：" + s.take());
                System.out.println("取值：" + s.take());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

    }


}
