package com.blueheart.n8.executor;

import java.util.concurrent.*;


/***
 *
 *   Future<String> submit=   executor.submit() 异步回调，不管线程耗时多少都会等到结果然后然后打印出来
 *
 *
 */

public class FutrueDemo {

    public static void main(String[] args) {

        ExecutorService executor = Executors.newFixedThreadPool(3);
        Future<String> submit = executor.submit(new Callable<String>() {
            @Override
            public String call() throws Exception {
                Thread.sleep(10000);
                return "121";
            }
        });
        try {
            System.out.println(submit.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

}
