package com.usst.dapter3.netty.simple;


import io.netty.util.NettyRuntime;

//双cpu 是4核 ，一个cup2个核
//输出一共核数
public class Test {
    public static void main(String[] args) {

        System.out.println("该电脑的核数" + NettyRuntime.availableProcessors());
        //该电脑的核数8
    }
}
