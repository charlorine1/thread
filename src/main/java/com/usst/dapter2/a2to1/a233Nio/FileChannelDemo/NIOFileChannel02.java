package com.usst.dapter2.a2to1.a233Nio.FileChannelDemo;


import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

//从文件里面写道内存，内存到控制台

//channel和buffer不是包含关系，而是连接关系，channel读入到buffer或者buffer写出到channel
public class NIOFileChannel02 {
    public static void main(String[] args) throws Exception{

        FileInputStream fileInputStream = new FileInputStream("E:\\111111111\\Nio\\FileChannel01.txt");

        FileChannel channel = fileInputStream.getChannel();

        ByteBuffer buffer = ByteBuffer.allocate(1024);
        //从chennel读到数据
        int read = channel.read(buffer);
        System.out.println("hello ,木木夕 的长度为" + "hello ,木木夕".getBytes().length);
        System.out.println("read=" + read);

        System.out.println(new String(buffer.array()));
        channel.close();

    }
}
