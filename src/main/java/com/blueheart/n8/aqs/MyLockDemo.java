package com.blueheart.n8.aqs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;


/**
 * 获取锁和释放锁伴随的变化
 * 1、state
 * 2、setExclusiveOwnerThread  当前线程的拥有者
 *
 *
 * 加锁和解锁都是在这两个方法上面做操作的，要重写abstractQueuedSynchroniezer 的tryAccquire 和trytrelease
 * 加锁： tryAcquire(int arg)     其中包括，一直等待获取锁、尝试一次、在一段时间内重复尝试    //比如这个方法都已经被AQS 封装好了，只要实现里面的tryAquire方法就好了
 * 解锁：tryRelease(int releases)
 *
 *
 *
 *
 * 出现的问题;
 *  unlock() 里面的syn.tryRelease(1);   //锁释放不了
 */
public class MyLockDemo {
    static Logger log = LoggerFactory.getLogger(MyLockDemo.class);
    public static void main(String[] args) {
        //开始测试
        MyLock lock = new MyLock();
        new Thread(()->{
            lock.lock();
            try {
                try {
                    log.info("加锁成功过,进行睡眠4秒");
                    Thread.sleep(4000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }finally {
                lock.unlock();
            }
        }).start();

        new Thread(()->{
            lock.lock();
            try {
                log.info("加锁成功过");
            }finally {
                lock.unlock();
            }
        }).start();
    }



}


/**
 * 20210129
 * 上锁、解锁是锁MyLock的功能
 * 获取state、存入队列由继承了abstractQueuedSychroniezer的内部类来维护
 *
 *
 */
//实现一个自定的锁
class  MyLock implements Lock{
    MySyn syn = new MySyn();

    //加锁， 尝试，不成功，进入等待队列
    @Override
    public void lock() {
           // syn.tryAcquire(1);     //执行一次获取锁操作,失败就不在进行获取操作，没有while(true) ，因为tryRelease  不涉及到阻塞的队列，加锁肯定要设计到阻塞队列，加锁失败的话要进入队列中等待，
         // 到时候进行再次获取(不同的方法处理不同，tryLock() 就只尝试一次，所以可以直接调用syn.tryAcquire(1); )
        syn.acquire(1);   // 获取锁 失败则会进入等待队列 ，里面还是tryAcquire()
    }

    // 可打断加锁  -》线程去加锁的时候，如果被阻塞了在阻塞队列，可以打断？(自己理解)   :: 对的 怎么打断 t2.interrupt();
    @Override
    public void lockInterruptibly() throws InterruptedException {
        syn.acquireInterruptibly(1);
    }


    //去尝试一次，要么获取成功要么获取失败
    @Override
    public boolean tryLock() {
        return syn.tryAcquire(1);
    }

    //去尝试获取锁，再时间段内会一直会去参与获取，相当于lock
    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return syn.tryAcquireNanos(1,unit.toNanos(time));    //比如这个方法都已经被AQS 封装好了，只要实现里面的tryAquire方法就好了
    }

    //释放锁 ,然后把状态和拥有者改变，不会有唤醒等待线程的步骤，因为加锁是在加锁线程一直轮询的( for (;;)) ,除了tryLock以为，tryLock()只是尝试一次加锁请求
    @Override
    public void unlock() {
      //  syn.tryRelease(1);   //锁释放不了 ，因为tryRelease  不涉及到阻塞的队列
        syn.release(1);
    }


    // 条件变量
    @Override
    public Condition newCondition() {
        return null;
    }

    /***
     *  MyLock（lock()） --->      Sycn(lock()多次尝试，lockInterruptibly() 尝试可打断,tryLock( timeout)，这些方法做一些操作后)     --- >AbstractQueuedSycnhronizer （tryAcquire()）
     *  MyLock (Unlock())  --->   Sycn(lUnock()   -->AbstractQueuedSycnhronizer  (tryRelease())
     *
     */
    class MySyn extends AbstractQueuedSynchronizer{

        //实质做获取锁操作(进行状态和设置拥有者属性) 传入的是1->去加锁， 要么成功，要么失败，该方法没有没有循环操作, 不涉及到阻塞队列
        @Override
        protected boolean tryAcquire(int arg) {
                //1、判断状态
            int state = getState();
            if(state==0){
               compareAndSetState(0,arg);    //要0 才能更新，因为在前面获取的同时可能马上就被其他线程修该了
                setExclusiveOwnerThread(Thread.currentThread());
                return true;
            }
            return false;   //未实现锁重入  ->接下来会在下面写
            //2、已加锁判断是否是自己(锁重入)
        }

        //实质性做释放锁操作(进行状态的修改和拥有者的释放)  0->解锁，要么释放成功，要么释放失败，没有循环操作，不涉及到阻塞队列
        @Override
        protected boolean  tryRelease(int acquires) {
          /*  int state = getState();
            if(state ==0){  //已经解锁
                setExclusiveOwnerThread(null);
                return true;
            }else {
                setState(arg);  // 设置为0释放锁
                setExclusiveOwnerThread(null);
                return true;
            }*/
          if(acquires==1){
            if(getState()==0){
                throw  new  IllegalMonitorStateException();  //本来就没有上锁，何来的解锁
            }
            setExclusiveOwnerThread(null);
            setState(0);
            return true;
          }
          return false;
        }

        public Condition newCondition() {
            return new ConditionObject();
        }

        @Override
        protected int tryAcquireShared(int arg) {
            return super.tryAcquireShared(arg);
        }

        @Override
        protected boolean tryReleaseShared(int arg) {
            return super.tryReleaseShared(arg);
        }


        //  Mutex：不可重入互斥锁，锁资源（state）只有两种状态：0：未被锁定；1：锁定。
        @Override
        protected boolean isHeldExclusively() {
            return getState() == 1;
        }
    }
}
