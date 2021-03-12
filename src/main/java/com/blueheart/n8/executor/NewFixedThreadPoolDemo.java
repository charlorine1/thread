package com.blueheart.n8.executor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/*

特点
核心线程数 == 最大线程数（没有救急线程被创建），因此也无需超时时间
阻塞队列是无界的，可以放任意数量的任务





 */

public class NewFixedThreadPoolDemo {


    public static void main(String[] args) {

       ExecutorService executor = Executors.newFixedThreadPool(3);


    }

}
