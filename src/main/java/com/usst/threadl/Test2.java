package com.usst.threadl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;


public class Test2 {

    public static void main(String[] args) throws Exception{

        //创建一个线程f，该f配合Callable 得到返回的值
        FutureTask<Integer> f = new FutureTask<>(new Callable<Integer>() {

            @Override
            public Integer call() throws Exception {
               // System.out.println("子线程 hello world");
                Log log = LogFactory.getLog(Test2.class);
                log.info("子线程 hello world");

                Thread.sleep(2000);
                return 1000;
            }
        });

        //FutureTask也是实现了runnable接口，所以也是一个任务，可以放到thread里面
        Thread thread = new Thread(f);
        thread.start();
        int i = 5+5;
        System.out.println("f 返回的值是" + f.get());
        System.out.println(i);

    }
}
