package com.ceshi.threadpoolexcutor;

public class CtlTest {
    private static final int COUNT_BITS = Integer.SIZE - 3;
    private static final int CAPACITY   = (1 << COUNT_BITS) - 1;
    private static  int i =-536870912;
    public static void main(String[] args) {
        System.out.println("线程池的状态： "+ runStateOf(i));
        System.out.println("现场池工作现场数："+ workerCountOf(i));

    }


    private static int runStateOf(int c)     { return c & ~CAPACITY; }
    private static int workerCountOf(int c)  { return c & CAPACITY; }
    private static int ctlOf(int rs, int wc) { return rs | wc; }

}
