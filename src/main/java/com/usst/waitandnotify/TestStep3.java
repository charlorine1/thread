package com.usst.waitandnotify;


/**
 * wait和notify使用最后姿势，解决虚假唤醒情况，使用while来代替if，虚假唤醒会使线程不干活，因为if只有一次机会
 * 虚假唤醒会让小南直接不干活了
 *
 *
 *
 *
 * 小南来干活，没烟先歇一会儿
 小女来干活，没外卖先歇一会儿
 小女唤醒接着干活，外卖到了没：true
 外卖到了小女开始干活
 小南来干活，没烟先歇一会儿
 */
public class TestStep3 {
    private static final Object room = new Object();    //锁对象----
    private static boolean hasSiggaret = false;  // 没烟
    private static boolean hasCheckout = false;  //外卖到了么

    public static void main(String[] args) throws Exception{
        new Thread("小南"){
            @Override
            public void run() {
                // 获得房间的锁，在房间内操作数据
                synchronized (room){
                    while (!hasSiggaret){
                        System.out.println("小南来干活，没烟先歇一会儿");
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
                    while(!hasCheckout){
                        System.out.println("小女来干活，没外卖先歇一会儿" );
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
            room.notifyAll();
        }
    }
}
