package com.jdk8;

import java.util.ArrayList;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class ShuangMaoHaoDemo {

    public static void main(String[] args) {
        //消费者函数Consumer
        /*        Consumer<String> consumer = new Consumer<String>() {
                    @Override
                    public void accept(String s) {
                        System.out.println(s);
                    }
                };
                consumer.accept("1111111");*/


        //上面是最原始的写法
        //下面使用lambda表达式
        /*        Consumer<String> consumer = (s)->System.out.println(s);
                consumer.accept("1111111");*/


        //下面使用lambda表达式的同时再更简洁
        Consumer<String> consumer = System.out::println;
        consumer.accept("1111111");


        Stream<String> stream = new ArrayList<String>().stream();




    }
}
