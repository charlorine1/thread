package com.usst.threadl;


/**
 * 1、线程没有在睡眠的时候被打断，不会抛出InterruptedException 异常，只有在睡眠的时候打断才会抛出异常，
 *    且把打断标记重置，此时isInterrupted()的值是false
 * 2、isInterrupted() 判断不会重置打断标记，interrupt() 判断会，先返回true的打断标记，再清除打断标记再变成false
 *
 *
 *
 */

public class Test3 {

    public static void main(String[] args) throws InterruptedException {

        TwoPhseTermination termination = new TwoPhseTermination();
        termination.start();

        //主线程睡眠3.5秒后去打断监控monitor线程
        Thread.sleep(3500);

        termination.stop();


    }


}

//2阶段终止阶段
class TwoPhseTermination{

    //创建监控线程
    private Thread monitor;

    //TwoPhseTermination 开始
    public void start(){
        monitor =  new Thread(){

            @Override
            public void run() {
                while (true){

                    Thread current = Thread.currentThread();
                    if(current.isInterrupted()){
                        System.out.println("料理后事");
                        break;
                    }

                    try {
                        Thread.sleep(1000);
                        System.out.println("执行监控记录");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        //如果睡眠了被打断会进入到该异常，且会把标记清除，然后把打断标记置为false
                        //重新设置打断标记
                        current.interrupt();
                    }
                }

            }
        };

        monitor.start();
    }

    public void stop(){
        monitor.interrupt();
    }


}