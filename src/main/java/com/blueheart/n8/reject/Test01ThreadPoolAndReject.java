package com.blueheart.n8.reject;

import io.netty.util.CharsetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;


/**
 * 上个线程池是queue没有拒绝策略的，就是只要会一直等着，有任务来往deque里面放入值
 *
 */
public class Test01ThreadPoolAndReject {
    private final static Logger logger =LoggerFactory.getLogger(Test01ThreadPoolAndReject.class);

    public static void main(String[] args) {
        //创建线程池
        ThreadPool pool = new ThreadPool(2, 3000, TimeUnit.MILLISECONDS, 2
        ,(queue,task) ->{
            //死等,如果阻塞丢列不释放，一直等着
           // queue.put(task);
            //带超时等待  (未解决)
           // queue.offer(task);
            //System.out.println("queue 已经满了，task不做处理(不会加到queue里面)，直接丢弃");

            //抛出异常
            //throw new RuntimeException("任务执行失败 " + task);
            //调用者自己执行
            task.run();
        });
        //主线程提交任务,
       for (int i = 0; i < 5; i++) {
                int j = i;
                pool.execute(()->{
                    logger.debug("添加任务，i=" + j);
                    try {
                        //任务执行时候进行阻塞，这样可以模拟出2个线程正在执行2个任务，2个任务在阻塞队列，则第五个任务进行丢弃
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                });
            }






/*        try {
            ServerSocket socket = new ServerSocket(19999);
           while (true){
               Socket accept = socket.accept();
               threadPool.execute(new Task(accept));
           }
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }
}


//添加拒绝策略，实际上就是想表达队列和任务的关系，
// 1) 死等
// 2) 带超时等待
// 3) 让调用者放弃任务执行
// 4) 让调用者抛出异常
// 5) 让调用者自己执行任务
@FunctionalInterface     //拒绝策略
interface RejectPolicy<T>{
 void reject(BlockingQueue<T> queue,T task);
}

class  Task implements Runnable{
    private Socket socket;

    public Task(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        InputStream in = null;
        try {
            in = socket.getInputStream();
            byte [] bytes = new byte[1024];
            int i=0;
            while ((i=in.read(bytes)) != -1){
                System.out.println("socket 接收到数据:" + new String(bytes,0,i,CharsetUtil.UTF_8));
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                in.close();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

//自定义线程池
class ThreadPool{
    private final static Logger logger =LoggerFactory.getLogger(ThreadPool.class);
    //任务队列
    private BlockingQueue<Runnable> taskQueue;
    //线程集合
    private HashSet<Worker> workers = new HashSet();

    //核心线程数
    private int coreSize;

    //超时时间
    private long timeOut;
    private TimeUnit unit;
    
    //拒绝策略
    private RejectPolicy<Runnable> rejectPolicy;
    

    public void execute(Runnable r){
        synchronized (workers){
            //如果线程池里面的线程数小于则创建，等于或者大于则不进行创建,放到等待队列中，下面还会判断任务数和阻塞队列的阈值的关系
            if(workers.size()<coreSize){
                Worker worker = new Worker(r);
                logger.info("新增worker。。"+worker);
                workers.add(worker);
                //线程创建就开始start,具体线程池中线程怎么去取任务执行是再worker线程里面,具体要实现什么样子的内容是任务r自己带进去用的（函数式编程）
                //所以线程池里面的线程其实调用实现细节的还是传入的任务r的run()方法里面的内容
                worker.start();
            }else {
                //等于或者大于则不进行创建,放到等待队列中
                logger.info("开始向阻塞队列中加入任务："+r.hashCode());
              //  taskQueue.put(r);
                taskQueue.tryPut(rejectPolicy,r);
            }
        }
    }

    public ThreadPool(int coreSize, long timeOut, TimeUnit unit,int capcity, RejectPolicy<Runnable> rejectPolicy) {
        this.coreSize = coreSize;
        this.timeOut = timeOut;
        this.unit = unit;
        this.taskQueue=new BlockingQueue<>(capcity);
        this.rejectPolicy=rejectPolicy;
    }


    class Worker extends Thread{
        private final  Logger logger =LoggerFactory.getLogger(Worker.class);
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
           // while (task !=null || (task=taskQueue.poll(timeOut,unit))!=null){
                try {
                    logger.info("正在执行。。。"+task.hashCode());
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

        //生产者线程往队列放数据，带超时，如果超时还没放进去就失败
        public boolean offer(T task,long timeOut,TimeUnit unit){
          return  false;
        }

        public int size(){
            lock.lock();
            try {
                return deque.size();
            }finally {
                lock.unlock();
            }
        }

    //任务加入到阻塞队列，时候调用的拒绝策略,此时是往阻塞队列添加任务，
    //1)、如果阻塞队列没满，正常放入
    //2)、如果阻塞队列满了，就要利用阻塞队列来对多余的这个task做处理
    public void tryPut(RejectPolicy<T> rejectPolicy, T task) {
            lock.lock();
            try {
                if(deque.size()==capcity){
                    //通过函数式编程传入的方法，来进行对多余的这个task进行处理
                    rejectPolicy.reject(this,task);
                }else {
                    //成功加入到任务队列中
                    System.out.println("阻塞队列中成功加入任务");
                   deque.add(task);
                   emptyWaiteSet.signal();
                }
            }finally {
                lock.unlock();
            }
    }
}