package com.blueheart.n8;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Test02Pool {
    static Logger log = LoggerFactory.getLogger(Test02Pool.class);
    public static void main(String[] args) {
        //核心线程数等于最大线程数
        Executor executor  = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 10000000; i++) {
            int j =i;
            executor.execute(()->{
                log.info(":j=" +j);
            });
        }
    }
}
