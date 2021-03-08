package com.blueheart.n8.interrupted;


/**
 *      interrupt()   打断线程
         如果被打断线程正在 sleep，wait，join 会导致被打断的线程抛出 InterruptedException，所以在sleep的时候要是有try  catch来进行捕捉异常，并清除打断标记 ；
         如果打断的正在运行的线程，则会设置 打断标记 ；park 的线程被打断，也会设置 打断标记
        打断标记可以用来判断线程是否被打断,有打断标记的话，interrupted() 返回的就是true，打断标记被清除的话，interrupted() 返回的就是false


         interrupted() static 判断当前线程是否被打断   会清除打断标记

 */

public class Demo1 {


    public static void main(String[] args) throws Exception {
        //1、打断正在sleep的线程
        Thread thread = new Thread() {
            @Override
            public void run() {
                    while (true){
                        System.out.println(11);
                    }
            }
        };
        thread.start();
        System.out.println("未被打断的时候，打断标记是：" + thread.isInterrupted());

        Thread.sleep(500);
        //在主线程调用线程thread ，并且打断
        thread.interrupt();

        System.out.println("已被打断的时候，打断标记是：" + thread.isInterrupted());


    }

}
