package com.usst.dapter2.a2to1.a233Nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class TimeClinetHandle  implements  Runnable{
    private String host;
    private int port;
    private Selector selector;
    private SocketChannel channel;
    private volatile boolean stop;

    public TimeClinetHandle(String host, int port){
        this.host = host == null? "127.0.0.1" : host;
        this.port = port ;
        try {
             selector =selector.open();
             channel = SocketChannel.open();
             channel.configureBlocking(false);

        }catch (IOException e){
            e.printStackTrace();
            System.exit(-1);
        }
    }




    @Override
    public void run() {
        try {
            doConnection();
        }catch (IOException e){
            e.printStackTrace();
            System.exit(-1);
        }

        while (!stop){
            try {
                selector.select(1000);
                //判断是否有就绪的channel
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> it = selectionKeys.iterator();
                SelectionKey key = null;

                while (it.hasNext()){
                      key = it.next();
                      it.remove();
                      try {
                           handleInput(key);
                      }catch (Exception e){
                          if(key != null){
                              key.cancel();
                              if(key.channel() != null){
                                  key.channel().close();
                              }
                          }
                      }
                }

            }catch (Exception e){
                e.printStackTrace();
                System.exit(1);
            }
        }
        if(selector != null){
            try {
                selector.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    private void doConnection() throws IOException{
        //判断改端口是否连接成功
         if(channel.connect(new InetSocketAddress(host, port))){
              channel.register(selector, SelectionKey.OP_READ);
              doWrite(channel);
         }else {
             //SelectionKey.OP_CONNECT 是监听连接操作的
             channel.register(selector,SelectionKey.OP_CONNECT);
         }

    }

    private void handleInput(SelectionKey key) throws IOException{
        if(key.isValid()){
            //判断是否连接成功
           SocketChannel sc = (SocketChannel) key.channel();
           if(key.isConnectable()){
               if(sc.finishConnect()){
                  sc.register(selector,SelectionKey.OP_READ);
                  doWrite(sc);
               }else {
                   System.exit(1);   //连接失败，进程退出
               }
               try {
                   Thread.sleep(1000);
               } catch (InterruptedException e) {
                   e.printStackTrace();
               }
               sc.register(selector,SelectionKey.OP_WRITE);
               if(key.isReadable()){
                   ByteBuffer readBuffer = ByteBuffer.allocate(1024);
                   int readBytes = sc.read(readBuffer);
                   if(readBytes > 0){
                       readBuffer.flip();
                      // ByteBuffer[] bytes = new ByteBuffer[readBuffer.remaining()];
                       byte[] bytes = new byte[readBuffer.remaining()];
                       readBuffer.get(bytes);
                       String body = new String(bytes,"UTF-8");
                       System.out.println("NOW IS :" + body);
                       this.stop = true;
                   }else if(readBytes < 0 ) {
                       //对端链路关闭
                       key.cancel();
                       sc.close();
                   }else {
                       ;   //读到0 字节 忽略
                   }
               }
           }
        }
    }

    private void doWrite(SocketChannel sc) throws IOException {
        byte [] bytes = "QUERY TIME ORDER".getBytes();
        //writeBuffer 为客户端的缓冲区
        ByteBuffer writeBuffer = ByteBuffer.allocate(bytes.length);
       // int write = sc.write(writeBuffer);
        writeBuffer.put(bytes);
        writeBuffer.flip();

        sc.write(writeBuffer);
        if(!writeBuffer.hasRemaining()){
             System.out.println("send order 2 server succeed ");
        }

    }
}
