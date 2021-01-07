package com.usst.volitale;

public class test1 {
     static  boolean run =true;

    public static void main(String[] args) throws InterruptedException {
        Thread t = new Thread(()->{
            while(run){
               // System.out.println();
                int i=1;
            }
        });
        t.start();
        Thread.sleep(1000);
        run = false; // 线程t不会如预想的停下来
    }
}
