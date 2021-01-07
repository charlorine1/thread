package com.usst.guarded.guarded1;

public class GuardedeObject {
        private final Object lock= new Object();
        private Object response;

        //get方法
        public Object get(){
           synchronized (lock){
               while (response ==null){
                   //等待
                   try {
                       System.out.println(Thread.currentThread().getName()+"开始进入等待");
                       lock.wait();
                   } catch (InterruptedException e) {
                       e.printStackTrace();
                   }
               }
               System.out.println(Thread.currentThread().getName()+"在get已经被唤醒");
              return response;
           }
        }

        //put方法,该方法要加锁，不然无法唤醒上面wait线程，而且如果要在其他地方唤醒的话，就不符合面向对象了
        //别的地方只需要直接调用该方法，而不用做任何安全（加锁）的处理
        public void complete(Object  response){
            synchronized (lock){
                this.response=response;
                System.out.println(Thread.currentThread().getName()+"在complete方法中去唤醒");
                lock.notify();
            }
       }
}
