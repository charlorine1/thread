package com.usst.waitandnotify;

public class TestStep2 {
    private static final Object room = new Object();    //锁对象----
    private static boolean hasSiggaret = false;  // 没烟
    private static boolean hasCheckout = false;  //外卖到了么

    public static void main(String[] args) throws Exception{
        new Thread("小南"){
            @Override
            public void run() {
                // 获得房间的锁，在房间内操作数据
                synchronized (room){
                    System.out.println("小南来干活，有没有烟：" + hasSiggaret);
                    if(!hasSiggaret){
                        try {
                            room.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.println("小南唤醒接着干活，有没有烟："+ hasSiggaret);
                    if(hasSiggaret){
                        System.out.println("有烟小南开始干活");
                    }else{
                        System.out.println("没有烟小南不干活");
                    }

                }
            }
        }.start();

        new Thread("小女"){
            @Override
            public void run() {
                // 获得房间的锁，在房间内操作数据
                synchronized (room){
                    System.out.println("小女来干活，外卖到了么：" + hasCheckout);
                    if(!hasCheckout){
                        try {
                            room.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.println("小女唤醒接着干活，外卖到了没："+ hasCheckout);
                    if(hasCheckout){
                        System.out.println("外卖到了小女开始干活");
                    }else {
                        System.out.println("外卖没到小女不干活");
                    }

                }
            }
        }.start();

        //先让t1 执行后再送烟
        Thread.sleep(1000);
       // hasSiggaret = true;
        hasCheckout = true ;
        synchronized (room){
            // 执行wait 和notify都需要该线程拿到锁到房间里面去才能操作，
            // 不然报错Exception in thread "main" java.lang.IllegalMonitorStateException
            //room.notify();
            room.notifyAll();
        }

    }
}
