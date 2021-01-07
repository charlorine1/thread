package com.usst.dapter2.a2to1.a233Nio.FileChannelDemo;


import io.netty.buffer.ByteBuf;
import sun.nio.ch.FileChannelImpl;

import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 把内容输出到指定文件
 *     1、往文件输入就是从内存输出到文件，使用输出流。
 *     2、字节数组put放到buffer
 *     3、把buffer写到channel
 *
 */
public class NIOFileChannel01 {

    public static void main(String[] args) throws Exception{
       String str = "hello ,木木夕";

        FileOutputStream fileOutputStream = new FileOutputStream("E:\\111111111\\Nio\\FileChannel01.txt");

        FileChannel channel = fileOutputStream.getChannel();

        ByteBuffer buffer = ByteBuffer.allocate(1025);
        buffer.put(str.getBytes());

        //往缓冲区放后，读取缓冲区得内容需要反转
        buffer.flip();
        //从buffer写道channel,相对于管道
        channel.write(buffer);
        channel.close();
        fileOutputStream.close();

    }
}
