package com.usst.dapter2.a2to1.a213Bio;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class TimeServer {

    public static void main(String[] args) {
        int port = 14444;
        if(args != null && args.length > 0){
            try {
                port = Integer.parseInt(args[0]);
            }catch (NumberFormatException e){

            }
        }
        ServerSocket server = null;

        try {
            server = new ServerSocket(port);
            System.out.println("Time server is start in port :"+ port);
            while (true){
                //没服务器连接的时候会阻塞在该处
                System.out.println("等待服务器连接................");
                Socket socket = server.accept();
                new Thread(new TimeServerHandler(socket)).start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(server != null){
                System.out.println("this time server is closed");
                try {
                    server.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                server = null;
            }
        }
    }
}
