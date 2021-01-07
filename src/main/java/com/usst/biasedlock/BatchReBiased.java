package com.usst.biasedlock;

import org.openjdk.jol.info.ClassLayout;

import java.util.Vector;

/**
 * 批量重偏向
 * 如果对象虽然被多个线程（本例子就2个线程）访问，但没有竞争，这时偏向了线程 T1 的对象仍有机会重新偏向 T2，重偏向会重置对象
     的 Thread ID
     当撤销偏向锁阈值超过 20 次后，jvm 会这样觉得，我是不是偏向错了呢，于是会在给这些对象加锁时重新偏向至加锁线程
     线程T2 才是主角
  取消偏向延迟-XX:BiasedLockingStartupDelay=0      不然第一个对象的第一次状态不是可偏向的。


  代码运行结果：
     线程1、第一次打印为可偏向：00000000 00000000 00000000 00000000 00000000 00000000 00000000 00000101
           第二次打印为有偏向的线程id：
                            00000000 00000000 00000000 00000000 00011110 11100010 01100000 00000101
           第三次虽然锁已经结束，但是还是偏向之前的线程，所以和上面打印一样
                            00000000 00000000 00000000 00000000 00011110 11100010 01100000 00000101
     线程2、前面19个对象，已经升级到轻量级锁了。
               第一次打印        00000000 00000000 00000000 00000000  00011110 11100010 01100000 00000101
               第二次打印        00000000 00000000 00000000 00000000  00100000 01010010 11110101 00010000    已经升级为轻量级锁了
               第三次打印        00000000 00000000 00000000 00000000  00000000 00000000 00000000 00000001    已经变成普通对象，不能进行偏向优化了
           第20个对象开始，开始批量重偏向
               第一次打印偏向t1   00000000 00000000 00000000 00000000  00011110 11100010 01100000 00000101
               第二次打印偏向t2   00000000 00000000 00000000 00000000  00011111 10101111 00110001 00000101   开始偏向t2了，没有升级为轻量级锁
               第三次打印        00000000 00000000 00000000 00000000  00011111 10101111 00110001 00000101   开始偏向t2了，没有升级为轻量级锁
 */
public class BatchReBiased {

    public static void main(String[] args) throws Exception{
        Vector<Person> people = new Vector<>();
        new Thread(){
            @Override
            public void run() {

                for(int i = 0 ;i< 30; i++){
                    Person person = new Person();
                    people.add(person);
                    System.out.println("i ==========" + i + ClassLayout.parseInstance(person).toPrintable());
                    synchronized (person){
                        System.out.println("i ==========" + i + ClassLayout.parseInstance(person).toPrintable());
                    }
                    System.out.println("i ==========" + i + ClassLayout.parseInstance(person).toPrintable());

                }
                synchronized (people){
                    people.notify();
                }

            }
        }.start();

       // Thread.sleep(10000);
        //线程run里面使用synchronized (person1) ，就是给person1 对象加锁，该线程不释放，没有其他线程能拿到该锁进入房间。
        // 其他线程要拿到改把锁就是获得进入房间的使用权(cpu的试用权)
        new Thread("T2"){
            @Override
            public void run() {
                synchronized (people){
                    try {
                        people.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
               for (int i = 0;i< people.size(); i++){
                   Person person1 = people.get(i);

                   System.out.println("i ==========" + i);
                   System.out.println(ClassLayout.parseInstance(person1).toPrintable());
                   synchronized (person1){
                       System.out.println("i ==========" + i);
                       System.out.println(ClassLayout.parseInstance(person1).toPrintable());
                   }
                   System.out.println("i ==========" + i);
                   System.out.println(ClassLayout.parseInstance(person1).toPrintable());
               }
            }
        }.start();
    }

}

class Person{

}