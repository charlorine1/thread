package com.usst.cas;

public class Test01 {
    public  static Object xml=new Object();

    public static void main(String[] args) {

        Thread t = new Thread();
        t.start();
        try {
            t.join();  //主线程内，里面相当于当前线程wait
        } catch (InterruptedException e) {
            e.printStackTrace();
        }



    }

}
