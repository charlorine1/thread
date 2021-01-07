package com.usst.dapter3.netty.simple;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class TimeServer {
    private  final  static int PORT = 10888 ;

    //主方法
    public static void main(String[] args) throws Exception{

         new TimeServer().bind(PORT);


    }

    public void bind(int port) throws Exception{
        //配置服务器的NIO线程数目
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workGroup = new NioEventLoopGroup(8);

        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 1024)
                    .childHandler(new ChildChannelHandler());
            //绑定端口，同步等待连接
            ChannelFuture f = b.bind(port);

            //等待服务器监听端口关闭
            f.channel().closeFuture().sync();

        }finally {
            //优雅退出，释放线程池
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }

    private class  ChildChannelHandler extends ChannelInitializer<SocketChannel>{
        @Override
        protected void initChannel(SocketChannel ch) throws Exception {
            ch.pipeline().addLast(new TimeServerHandler());
        }
    }

}
