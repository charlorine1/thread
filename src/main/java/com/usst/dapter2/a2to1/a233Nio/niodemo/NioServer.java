package com.usst.dapter2.a2to1.a233Nio.niodemo;

import io.netty.buffer.ByteBuf;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

public class NioServer {

    public static void main(String[] args) throws Exception{

        ServerSocketChannel socketChannel = ServerSocketChannel.open();
        Selector selector = Selector.open();
        socketChannel.socket().bind(new InetSocketAddress(19999),1024);
        socketChannel.configureBlocking(false);
        socketChannel.register(selector, SelectionKey.OP_ACCEPT);

        while (true){
           // System.out.println(System.currentTimeMillis());
            int select = selector.select(1000);
           // System.out.println(System.currentTimeMillis());
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectionKeys.iterator();

            while (iterator.hasNext()){
                SelectionKey key = iterator.next();
                if(key.isAcceptable()){
                    ServerSocketChannel channel = (ServerSocketChannel) key.channel();
                    SocketChannel socketChannel1 = channel.accept();
                    socketChannel1.configureBlocking(false);
                    socketChannel1.register(selector,SelectionKey.OP_READ, ByteBuffer.allocate(1024));
                }
                if(key.isReadable()){
                    SocketChannel socketChannel1 = (SocketChannel) key.channel();
                    ByteBuffer buffer  = ByteBuffer.allocate(1024);
                   // ByteBuffer buffer = (ByteBuffer) key.attachment();
                    socketChannel1.read(buffer);
                    System.out.println(new String(buffer.array()));
                }
             iterator.remove();
            }





        }







    }

}
