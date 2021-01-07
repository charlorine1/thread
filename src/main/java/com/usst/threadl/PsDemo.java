package com.usst.threadl;

public class PsDemo {

    public static void main(String[] args) {

        new Thread("t1"){
            @Override
            public void run() {
                while (true){
                    System.out.println("t1 thread is running");
                }
            }
        }.start();


        new Thread("t2"){
            @Override
            public void run() {
                while (true){
                    System.out.println("t2 thread is running");
                }
            }
        }.start();
    }
}
