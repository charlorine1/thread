


    public ThreadPoolExecutor(int corePoolSize,
                              int maximumPoolSize,
                              long keepAliveTime,
                              TimeUnit unit,
                              BlockingQueue<Runnable> workQueue,
                              ThreadFactory threadFactory,
                              RejectedExecutionHandler handler) {
因为ThreadPoolExecutor 的构造方法有很多的入参，造成初学者很难学会，所以JDK封装了一个工厂方法，用来创建同情况的线程池，
只需要传入个别的参数，里面的参数都是JDK自己处理好的，易于初学者上手

1、 Executors.newFixedThreadPool(3); 只需要传入核心线程数就可以创建出一个线程池（不需要出入ThreadPoolExecutor 的七个参数）
2、spring 封装了一个ThreadPoolTaskExecutor()