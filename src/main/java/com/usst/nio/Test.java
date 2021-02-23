package com.usst.nio;

import java.io.File;
import java.io.FileInputStream;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class Test {
    public static void main(String[] args)  throws  Exception {
        File file= new File("F:\\nio.txt");
       // FileInputStream  stream = new FileInputStream(file);
        RandomAccessFile accessFile = new  RandomAccessFile("F:\\nio.txt","rw");
        FileChannel channel = accessFile.getChannel();
        byte[] bytes = "hello world 世界你好".getBytes();
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        ByteBuffer put = byteBuffer.put(bytes);
        byteBuffer.clear();
        int write = channel.write(byteBuffer);

        System.out.println(write);
        System.out.println("----------");
        System.out.println(bytes.length);


    }

}
