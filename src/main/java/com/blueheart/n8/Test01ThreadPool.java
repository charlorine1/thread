package com.blueheart.n8;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Test01ThreadPool {
    private final static Logger logger =LoggerFactory.getLogger(Test01ThreadPool.class);

    public static void main(String[] args) {
        //创建线程池
        ThreadPool threadPool = new ThreadPool(2, 1000, TimeUnit.MILLISECONDS, 10);
        //主线程提交任务
        for (int i = 0; i < 5; i++) {
            int j =i;
            threadPool.execute(()->{
                System.out.println("i = " + j +"当前线程"+ Thread.currentThread().getName());
            });
        }

    }
}

//自定义线程池
class ThreadPool{
    //任务队列
    private BlockingQueue<Runnable> taskQueue;
    //线程集合
    private HashSet<Worker> workers = new HashSet();

    //核心线程数
    private int coreSize;

    //超时时间
    private long timeOut;
    private TimeUnit unit;

    public void execute(Runnable r){
        synchronized (workers){
            //如果线程池里面的线程数小于则创建，等于或者大于则不进行创建,放到等待队列中
            if(workers.size()<coreSize){
                Worker worker = new Worker(r);
                System.out.println("新增worker。。"+worker);
                workers.add(worker);
                //线程创建就开始start,具体线程池中线程怎么去取任务执行是再worker线程里面,具体要实现什么样子的内容是任务r自己带进去用的（函数式编程）
                //所以线程池里面的线程其实调用实现细节的还是传入的任务r的run()方法里面的内容
                worker.start();
            }else {
                //等于或者大于则不进行创建,放到等待队列中
                System.out.println("线程池内线程数：" + workers.size() +";coreSize :" +coreSize +"加入任务队列"+r.hashCode());
                taskQueue.put(r);
            }
        }
    }

    public ThreadPool(int coreSize, long timeOut, TimeUnit unit,int capcity) {
        this.coreSize = coreSize;
        this.timeOut = timeOut;
        this.unit = unit;
        this.taskQueue=new BlockingQueue<>(capcity);
    }

    class Worker extends Thread{
        private Runnable task;

        public Worker(Runnable task) {
            this.task = task;
        }

        @Override
        public void run() {
            //worker线程执行任务。
            //1、task不为null，执行任务
            //2、当task为null的时候，再接着从任务队列中获取任务并执行
            while (task !=null || (task=taskQueue.take())!=null){
                try {
                    System.out.println("正在执行。。。"+task.hashCode());
                    task.run();
                }catch (Exception e){
                    e.printStackTrace();
                }finally {
                    task=null;
                }
            }
            synchronized (workers){
                System.out.println("worker 被移除：" + this);
                workers.remove(this);
            }
        }
    }

}



//阻塞队列，用于存放任务和线程取任务
class BlockingQueue<T>{
        //存放task
        private Deque<T> deque = new ArrayDeque<T>() ;

        //要给上面的队列进行加锁，所以要定义一个锁
        private ReentrantLock lock = new ReentrantLock();

        //生产者条件变量,满了的话就不再生产了，进行等待
        private Condition fullWaiteSet =lock.newCondition();

        //消费者条件变量
        private  Condition emptyWaiteSet = lock.newCondition();

        //任务队列的容量
        private int capcity;

        public BlockingQueue(int capcity) {
            this.capcity = capcity;
        }

        //带超时获取阻塞队列，就是获取不带不会一直阻塞下去，会返回null
        public T poll(long timeOut, TimeUnit unit){
                lock.lock();
                try {
                    long nanos = unit.toNanos(timeOut);
                    while (deque.isEmpty()){
                        try {
                            if ((nanos<0)) {
                                return null;
                            }
                            nanos = emptyWaiteSet.awaitNanos(nanos);  //传入5000，如果等待3000毫秒被唤醒,则该方法返回值位2000毫秒
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    T task=deque.removeFirst();
                    fullWaiteSet.signal();
                    return task;
                }finally {
                    lock.unlock();
                }
        }

        //消费线程取队列里面的任务
        public T take(){
            lock.lock();
            try {
                while (deque.isEmpty()){
                    try {
                        emptyWaiteSet.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                T task = deque.removeFirst();
                fullWaiteSet.signal();
                return task;
            }finally {
                lock.unlock();
            }
        }

        //生产者线程往队列里面put数据
        public void put(T task){
            lock.lock();
            try {
                while (capcity == deque.size()){  //阈值小于队列size,进入waite,等于也不行，等于也要等待
                    try {
                        fullWaiteSet.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                deque.push(task);
                emptyWaiteSet.signal();
            }finally {
                lock.unlock();
            }
        }

        public int size(){
            lock.lock();
            try {
                return deque.size();
            }finally {
                lock.unlock();
            }
        }



}