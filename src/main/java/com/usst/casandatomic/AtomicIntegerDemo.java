package com.usst.casandatomic;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.IntUnaryOperator;

public class AtomicIntegerDemo {

    public static void main(String[] args) {
        AtomicInteger i = new AtomicInteger(100);
        // 获取并自增（i = 0, 结果 i = 1, 返回 0），类似于 i++
        System.out.println(i.getAndIncrement());    //100
        //获取并自增（i = 0, 结果 i = 1, 返回 0），类似于 ++i
        System.out.println(i.incrementAndGet());   //102
        int i1 = i.updateAndGet(operand -> 100 * operand);
        System.out.println(i1);


    }
}
