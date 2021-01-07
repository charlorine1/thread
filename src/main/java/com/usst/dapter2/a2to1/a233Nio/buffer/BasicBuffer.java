package com.usst.dapter2.a2to1.a233Nio.buffer;

import java.nio.IntBuffer;

public class BasicBuffer {

    public static void main(String[] args) {
        //举例说明buffer的使用
        //创建一个intBuffer。长度为5个int
        IntBuffer intBuffer = IntBuffer.allocate(5);
        for (int i = 0 ; i< 3 ; i++){
            intBuffer.put(i * 2);
        }

        //buffer转换，从放数据变成读数据
        intBuffer.flip();


        while (intBuffer.hasRemaining()){
            //remaining 方法是看buffer里面的剩余值的长度
            System.out.println("remaining = "+intBuffer.remaining());
            System.out.println(intBuffer.get());
        }


    }
}
