package com.ceshi.threadpoolexcutor;


import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

//测试验证放到线程池中的任务，有一条出问题，其他的怎么办
public class ThreadPoolExcutorDemo {

    public static void main(String[] args) throws Exception {

        ThreadPoolTaskExecutor executor = ThreadPoolExcutorDemo.buildThreadPoolTaskExecutor();
        executor.execute(()->{
            //System.out.println("callerRunPolicy");
            sayHi("executor");
        });
        TimeUnit.MILLISECONDS.sleep(100);
        System.out.println("----------------");
        Future<?> execute = executor.submit(() -> {
            sayHi("execute");
        });
/*        try {
            execute.get();
        }catch (ExecutionException e){
            e.printStackTrace();
        }*/


    }



    public static void sayHi(String name){
        String printStr = "【thread-name:" + Thread.currentThread().getName() + ",执行方式:" + name+"】";
        System.out.println(printStr);
        int i =1/0;
      //  throw new RuntimeException(printStr + ",我异常啦!哈哈哈!");
    }



    private static ThreadPoolTaskExecutor buildThreadPoolTaskExecutor() {
        ThreadPoolTaskExecutor executorService = new ThreadPoolTaskExecutor();
        executorService.setThreadNamePrefix("测试--");
        executorService.setCorePoolSize(5);
        executorService.setMaxPoolSize(22);
        executorService.setQueueCapacity(1000);
        executorService.setKeepAliveSeconds(30);
        executorService.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executorService.initialize();
        return executorService;
    }


}
