package com.usst.guarded.guarded1;


/**
 * 实现的原理就是wait和notify，没有response的话就进入wait，让其他线程把值写入的同时使用notify来唤醒
 *
 *
 */
public class MainDemo {
    public static void main(String[] args) {
        //创建一个guardedObject
         GuardedeObject guardedeObject = new GuardedeObject();
        new Thread("thead1"){
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName()+"开始do someThing");
                //do someThing ,用睡眠5秒代替
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                guardedeObject.complete(5000);
            }
        }.start();

        //主线程继续执行,正常情况上面线程等待，主线程一直会执行，但是里面使用了停等保护模式。主线程阻塞不唤醒之前该线程（主线程）都是
        //阻塞情况，在waitSet里面，上面执行complete唤醒后才会继续执行该线程（这里是组箱）
        System.out.println("主线程开始执行guardedeObject.get()");
        Object response = guardedeObject.get();
        System.out.println(response.toString());

    }


}
