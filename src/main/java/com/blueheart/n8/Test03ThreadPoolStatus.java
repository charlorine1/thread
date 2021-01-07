package com.blueheart.n8;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Test03ThreadPoolStatus {
    static Logger log = LoggerFactory.getLogger(Test03ThreadPoolStatus.class);
    public static void main(String[] args) {

      //ExecutorService pool =  Executors.newFixedThreadPool(3);
       ExecutorService pool = Executors.newSingleThreadExecutor();
       pool.execute(()->{
           try {
               Thread.sleep(600000);
           } catch (InterruptedException e) {
               e.printStackTrace();
           }
           log.info("添加任务号: i = "+ 1);
       });
        pool.execute(()->{
            log.info("添加任务号: i = "+ 2);
        });
        pool.execute(()->{
            log.info("添加任务号: i = "+ 3);
        });

        pool.execute(()->{
            log.info("添加任务号: i = "+ 4);
        });
        pool.execute(()->{
            log.info("添加任务号: i = "+ 5);
        });



/*        for (int i = 0; i < 10; i++) {
            int j = i;
            pool.execute(()->{
                log.info("添加任务号: i = "+ j);
            });
        }*/
     //   pool.shutdown();

    }
}
