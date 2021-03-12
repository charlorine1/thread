package com.ceshi.threadpoolexcutor;


/*
测试结果：
  只有报异常才会去执行finally 里面的代码，线程池threadpootExecutor 里面就有用到runWorker () 中
 */
public class TestFinally {

    public static void main(String[] args) {
        try {
            int i=10;
            while (true){
                System.out.println(840/(i--)  +"i ="+ i);

            }
        }finally {
            System.out.println("执行了finally");
        }

    }


}
