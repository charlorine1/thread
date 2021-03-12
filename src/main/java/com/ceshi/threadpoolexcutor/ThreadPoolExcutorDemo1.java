package com.ceshi.threadpoolexcutor;

import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;


/***
 * 1、出现异常的线程3会在创建的时候processWorkerExit() ->   workers.remove(w);     移除   ,但是后面肯定会把线程创建到核心线程数，但是因为是累加，
 *  所以以后不会出现出现过异常的工作线程，会往上累加
 *                                                   【thread-name:测试线程5,执行方式:executor4】
                                                    【thread-name:测试线程6,执行方式:executo5】



 *2、猜想：1、2、3、4、5   其中3发生了异常，此时会把创建的线程3 抛弃    workers.remove(w);，并且如果在workcount <corePoolSize的情况，会创建一个新的Worker，但是该worker的task=null，   addWorker(null, false);
 * 但是worker的start已经在开始轮询了
 *
 *  方案：查看workers集合的线程Woker数(Worker的属性有Thread，创建woker的时候会调用传入的工厂创建线程，其实就是给线程name命名)，remove 3 的同同时是否有4存在(但是4不会去执行4任务，他是异常的时候加入的)
 *             且corePoolSize  >=5 ,不然直接加入LinkedBlockingDequeue队列中
                    创建线程：        Worker(Runnable firstTask) {
                                                     setState(-1); // inhibit interrupts until runWorker
                                                     this.firstTask = firstTask;
                                                     this.thread = getThreadFactory().newThread(this);
                                                }

                                    protected String nextThreadName() {
                                         return getThreadNamePrefix() + this.threadCount.incrementAndGet();
 }
   测试结果：猜想完全正确，见图
 *
 *
 */

public class ThreadPoolExcutorDemo1 {

    public static void main(String[] args) throws Exception {

        ThreadPoolTaskExecutor executor = ThreadPoolExcutorDemo1.buildThreadPoolTaskExecutor();

            /*
        executor.execute(()->{
            try {
                TimeUnit.SECONDS.sleep(1000);
                sayHi("executor1");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        });

        executor.execute(()->{
            try {
                TimeUnit.SECONDS.sleep(1000);
                sayHi("executor2");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        });
;
        executor.execute(()->{ sayHi("executor3"); });
        executor.execute(()->{ sayHi("executor4"); });
        executor.execute(()->{ sayHi("executor5"); });
        executor.execute(()->{ sayHi("executor6"); });
        executor.execute(()->{ sayHi("executor7"); });
        executor.execute(()->{ sayHi("executor8"); });
        executor.execute(()->{ sayHi("executor9"); });
        executor.execute(()->{ sayHi("executor10"); });
        executor.execute(()->{ sayHi("executor11"); });
        executor.execute(()->{ sayHi("executor12"); });
        executor.execute(()->{ sayHi("executor13"); });
        executor.execute(()->{ sayHi("executor14"); });
        executor.execute(()->{ sayHi("executor15"); });

*/

       executor.execute(()->{ sayHi("executor1"); });
        executor.execute(()->{ sayHi("executor2"); });
       // executor.execute(()->{ sayHi("executor3"); });
        executor.execute(()->{ sayHi("execute-exception3"); });
        executor.execute(()->{ sayHi("executor4"); });
        executor.execute(()->{ sayHi("executor5"); });
     /*   executor.execute(()->{ sayHi("executor6"); });
        executor.execute(()->{ sayHi("executor7"); });
        executor.execute(()->{ sayHi("executor8"); });

        executor.execute(()->{ sayHi("executor10"); });
        executor.execute(()->{ sayHi("executor11"); });
        executor.execute(()->{ sayHi("executor12"); });
        executor.execute(()->{ sayHi("executor13"); });
        executor.execute(()->{ sayHi("executor14"); });
        executor.execute(()->{ sayHi("executor15"); });*/
    }



    public static void sayHi(String name){
        String printStr = "【thread-name:" + Thread.currentThread().getName() + ",执行方式:" + name+"】";
        if("execute-exception3".equals(name)){
            throw new RuntimeException(printStr + ",我异常啦!哈哈哈!");
        }else {
            System.out.println(printStr);
        }
    }



    private static ThreadPoolTaskExecutor buildThreadPoolTaskExecutor() {
        ThreadPoolTaskExecutor executorService = new ThreadPoolTaskExecutor();
        executorService.setThreadNamePrefix("测试线程");
        executorService.setCorePoolSize(5);
        executorService.setMaxPoolSize(10);
        executorService.setQueueCapacity(100);
        executorService.setKeepAliveSeconds(30);
     //   executorService.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());     //CallerRunsPolicy
    //   executorService.setRejectedExecutionHandler(new ThreadPoolExecutor.DiscardOldestPolicy());    //    e.getQueue().poll();  e.execute(r);
      //  executorService.setRejectedExecutionHandler(new ThreadPoolExecutor.DiscardPolicy());
       executorService.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());
        executorService.initialize();
        return executorService;
    }

}
