package com.usst.waitandnotify;


/**
 * sleep和wait最大的区别就是
 * 1、sleep()不会释放对象锁，wait会释放锁对象  进入到等待队列waitset()此时会释放对象锁
 * 2、notify()会随机唤醒waitSet里面的一个线程，如果唤醒的是没被期待的那个线程，就叫做虚假唤醒，则使用notifyAll()
 *
 */
public class TestStep1 {
    static final Object room = new Object();    //锁对象----
    static boolean hasSiggaret = false;  // 没烟

    public static void main(String[] args) throws Exception{
       new Thread("小南"){
           @Override
           public void run() {
               // 获得房间的锁，在房间内操作数据
              synchronized (room){
                  System.out.println("小南来干活，有没有烟：" + hasSiggaret);
                  if(!hasSiggaret){
                      try {
                          //锁对象会关联一个monitor，room.wait()就是把该线程放到monitor 的waitSet()里面
                          //此时锁有没有释放呢？没释放则其他线程不能进入到monitor，变成monitor的owner。
                          //如果是sleep的话不会释放对象锁，不会变成owner，其他线程会一直等待中
                         room.wait();
                         // Thread.sleep(6000);
                      } catch (InterruptedException e) {
                          e.printStackTrace();
                      }
                  }
                  System.out.println("小南来干活，有没有烟："+ hasSiggaret);
                  if(hasSiggaret){
                      System.out.println("小南开始干活");
                  }
              }
           }
       }.start();

       //还有5个线程需要使用该房间
       for (int i= 0; i<5; i++){
           new Thread("其他人"+i){
               @Override
               public void run() {
                   synchronized (room) {
                       System.out.println(Thread.currentThread().getName() + "开始干活");
                   }
               }
           }.start();
       }

       //先让t1 执行后再送烟
       Thread.sleep(1000);
       hasSiggaret = true;
       synchronized (room){
           // 执行wait 和notify都需要到房间里面去才能操作，就是先要变成monitor的owner(就是在线程内加锁，使该线程变成monitor)，才有资格执行notify(),
           // 不然报错Exception in thread "main" java.lang.IllegalMonitorStateException
           room.notify();
       }

    }
}

