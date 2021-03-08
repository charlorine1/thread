package com.jdk8;

import java.util.function.Function;

public class Demo2 {

    public static void main(String[] args) {

        /**
         * 最原始的写法
         *
         */
/*        Function<Integer,String> retrunStr = new Function<Integer, String>() {
            @Override
            public String apply(Integer integer) {
                return String.valueOf(integer);
            }
        };
        String apply = retrunStr.apply(121);
        System.out.println(apply.equals("12"));*/


        /**
         * lambda 表达式
         *
         */
/*        Function<Integer,String> retrunStr = integer -> String.valueOf(integer);
        String apply = retrunStr.apply(12);
        System.out.println(apply.equals("12"));*/

        /**
         * lambda 表达式简化版本
         *
         */
        Function<Integer,String> retrunStr = String::valueOf;
        String apply = retrunStr.apply(12);
        System.out.println(apply.equals("12"));





    }

}
