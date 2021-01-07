package com.blueheart.n8;

public class Demo {
    private static final int COUNT_BITS = Integer.SIZE - 3;
    public static void main(String[] args) {
        System.out.println(COUNT_BITS);
        //10000000
        System.out.println( (1 << COUNT_BITS) );



    }
}
