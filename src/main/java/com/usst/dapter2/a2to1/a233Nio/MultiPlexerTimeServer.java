package com.usst.dapter2.a2to1.a233Nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

public class MultiPlexerTimeServer implements Runnable{
    private Selector selector;

    private ServerSocketChannel serverChannel;

    private volatile boolean stop;

    private volatile Integer number = 0;

    /**
     * 初始化多路复用器，绑定监听端口
     * 传入一个端口号：就会有一个Selector来监听
     */
    public  MultiPlexerTimeServer(int port){
        try {
            selector = Selector.open();
            serverChannel = ServerSocketChannel.open();
            //设置客户端链路为非阻塞状态
            serverChannel.configureBlocking(false);
            serverChannel.socket().bind(new InetSocketAddress(port),1024);

            //ServerSocketChannel注册一个selector,如果有客户端产生读和写的操作，ServerSocketChannel就会生成socketChannel
            serverChannel.register(selector, SelectionKey.OP_ACCEPT);
            System.out.println("the time server is start in port :" + port);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    public void stop(){
        this.stop= true;
    }

    @Override
    public void run() {
           while (!stop){
               try {
                   selector.select(1000);
                   //selector.keys() 返回该选择器上所有注册的selectionKey
                   //selector.selectedKeys(); 只返回有事件发生的selectionKey
                   Set<SelectionKey> selectionKeys = selector.selectedKeys();
                   Iterator<SelectionKey> it = selectionKeys.iterator();
                   SelectionKey key = null;
                   while (it.hasNext()){
                       System.out.println("客户端连接个数：" + number++);
                        key = it.next();
                        it.remove();
                       System.out.println("channel的toString 为：" + key.channel().toString());
                        try {
                            handleInput(key);
                        }catch (IOException e){
                            if(key != null){
                                key.cancel();
/*                                if(key.channel() != null){
                                    key.channel().close();
                                }*/
                                if (key.isValid() && key.isReadable()){
                                    key.cancel();
                                    if (key.channel() != null){
                                        key.channel().close();
                                    }
                                }
                            }
                        }
                   }
               } catch (Throwable t) {
                   t.printStackTrace();
               }
           }

           //多路复用关闭后，所有注册在上面的Channel和Pipe都会被自动去注册并且关闭，所以不需要重复释放资源
           if(selector != null){
               try {
                   selector.close();
               } catch (IOException e) {
                   e.printStackTrace();
               }
           }
    }

    private void handleInput(SelectionKey key) throws IOException{
        if(key.isValid()){
            if(key.isAcceptable()){
                //accept the new connection
                 ServerSocketChannel ssc = (ServerSocketChannel) key.channel();
                SocketChannel sc = ssc.accept();
                sc.configureBlocking(false);
                sc.register(selector,SelectionKey.OP_READ);
            }
            //isReadable      //当前事件为读就绪
            if(key.isReadable()){
                //read data
                SocketChannel sc = (SocketChannel) key.channel();
                ByteBuffer readBuffer = ByteBuffer.allocate(1024);
                int readBytes = sc.read(readBuffer);
                if(readBytes >0){
                    readBuffer.flip();
                    byte[] bytes = new byte[readBuffer.remaining()];
                    readBuffer.get(bytes);
                    String body = new String(bytes,"UTF-8");
                    System.out.println("The time server recevie order :" + body);
                    String currentTime =
                    "QUERY TIME ORDER".equalsIgnoreCase(body)?new Date(System.currentTimeMillis()).toString(): "BAD ORDER";
                    doWrite(sc, currentTime);
                }else if(readBytes <0){
                    //对接链路关闭
                    key.channel();
                    sc.close();
                }else
                   ; //读到0字节忽略
            }
        }
    }

    private void doWrite(SocketChannel channel, String response) throws IOException{
        if(response != null && response.trim().length() >0){
            byte[] bytes = response.getBytes();
            ByteBuffer writeBuffer = ByteBuffer.allocate(bytes.length);
            writeBuffer.put(bytes);
           // writeBuffer.flip();
            System.out.println("server 端发送内容：" + response);
            channel.write(writeBuffer);
            if(!writeBuffer.hasRemaining()){
                System.out.println("server send succeed :"+ response);
            }
        }
    }
}
