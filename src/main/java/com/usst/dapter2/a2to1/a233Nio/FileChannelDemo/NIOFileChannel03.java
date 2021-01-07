package com.usst.dapter2.a2to1.a233Nio.FileChannelDemo;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/***
 * 通过channel把文件拷贝到另外一个地方
 *
 * 注意：1、使用同一个buffer的时候，一定要注意flip()
 *       2、使用同一个buffer多次读写的时候，一定要clear，把基础数据进行清0，因为buffer的position = limit的时候读取的长度是0，永远不能进入-1的判断
 *              一直处于死循环
 *
 */
public class NIOFileChannel03 {
    public static void main(String[] args)  throws IOException{

        //创建输入channel
        FileInputStream fileInputStream = new FileInputStream("1.txt");
        FileChannel channel01 = fileInputStream.getChannel();

        //创建输出channel
        FileOutputStream fileOutputStream = new FileOutputStream("2.txt");
        FileChannel channel02 = fileOutputStream.getChannel();

        //创建读写的buffer
        ByteBuffer byteBuffer = ByteBuffer.allocate(512);
        while (true){
            //把channel01的数据读入到byteBuffer中
            int read = channel01.read(byteBuffer);
            System.out.println("read的长度为：" + read);
            if(read == -1){
                break;
            }
            //像buffer中写数据后如果进行读数据，一定要使用flip
            byteBuffer.flip();
            channel02.write(byteBuffer);
        }

    }
}
