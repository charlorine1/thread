package com.usst.threadl;



public class Test1 {


    /**
     *
     *1、实现接口:是去执行runnbale的run方法
     *
     *2、基础类是重写类里面的run方法，就是重写了父类runnable的run 方法，子类实现了方法就不会去执行父类了
     *
     *
     * */
    public static void main(String[] args) {

        Runnable r = () -> {
            System.out.println("this is Runnable  running...");

        };

        Thread t = new Thread(r){

            @Override
            public void run() {
                System.out.println("this is thread running...");
            }
        };
        t.start();

    }

    public void t1(){
        Thread t = new Thread(){

            @Override
            public void run(){

            }
        };
    }

    public static int a(){
        int a=1,b=2;
        int c = a+ b;
        return c;
    }
}
