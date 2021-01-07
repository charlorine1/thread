package com.usst.threadl;

public class Test4 {
    static  int counnter = 0;

    public static void main(String[] args) throws  Exception{
        Thread t1 = new Thread("t1") {
            @Override
            public void run() {

                synchronized (Test4.class){
                    for (int i = 0; i < 100000; i++) {
                        System.out.println("counnter进行增加="+ counnter++);
                    }
                }

            }
        };
        Thread t2 = new Thread("t2") {
            @Override
            public void run() {
                synchronized (Test4.class){
                    for (int i = 0; i < 100000; i++) {
                        System.out.println("counnter进行减少="+ counnter--);
                    }
                }
            }
        };
        t1.start();
        t2.start();
        t1.join();
        t2.join();
       // Thread.sleep(5000);
        System.out.println(counnter);
    }
}
