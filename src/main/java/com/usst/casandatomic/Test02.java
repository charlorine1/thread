package com.usst.casandatomic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicIntegerArray;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class Test02 {
    public static void main(String[] args) {
//        demo(
//                ()->new int[10],
//                (arrary)->arrary.length,
//                (arrary,index)->arrary[index]++,
//                (arrary)-> System.out.println(Arrays.toString(arrary))
//        );
        demo(
                ()->new AtomicIntegerArray(10),
                (arrary)->arrary.length(),
                (arrary,index)->arrary.getAndIncrement(index),
                (arrary)-> System.out.println(arrary)
        );
    }
    private static <T> void demo(
            Supplier<T> arraySupplier,
            Function<T, Integer> lengthFun,
            BiConsumer<T, Integer> putConsumer,
            Consumer<T> printConsumer ) {
        List<Thread> ts = new ArrayList<>();
        T array = arraySupplier.get();
        int length = lengthFun.apply(array);
        System.out.println(array);
        for (int i = 0; i < length; i++) {
            // 每个线程对数组作 10000 次操作
            ts.add(new Thread(() -> {
                for (int j = 0; j < 10000; j++) {
                 // synchronized ("qq"){
                     //一个线程循环1000次，各有1000次给下标位0，1，2，3，4，5，6，7，8，9的数组值加一
                    //一共10个线程，就10*1000=10000
                      putConsumer.accept(array, j%length);
                //  }
                }
            }));
        }
        ts.forEach(t -> t.start()); // 启动所有线程
        ts.forEach(t -> {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }); // 等所有线程结束
        printConsumer.accept(array);
    }

}
