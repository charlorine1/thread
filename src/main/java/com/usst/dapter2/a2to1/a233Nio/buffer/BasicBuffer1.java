package com.usst.dapter2.a2to1.a233Nio.buffer;

import io.netty.util.CharsetUtil;

import java.nio.ByteBuffer;

public class BasicBuffer1 {
    public static void main(String[] args) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(100);
        byte[] bytes1 = "我是hahaha".getBytes();
        byteBuffer.put(bytes1);
        byteBuffer.flip();
        byte[] bytes2 = new byte[100];
        int i =0;
        while (byteBuffer.hasRemaining()){
            byte b = byteBuffer.get();
            bytes2[++i]=b;
        }
        System.out.println(new String(bytes2,CharsetUtil.UTF_8));

    }
}
