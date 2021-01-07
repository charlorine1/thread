package com.usst.dapter2.a2to1.a233Nio;

import java.io.IOException;

public class TimeServer {
    public static void main(String[] args) throws IOException{
        int port = 18080;
        if(args != null && args.length >0){
            try {
                port = Integer.parseInt(args[0]);
            }catch (NumberFormatException e){
                e.printStackTrace();
            }
        }
        MultiPlexerTimeServer timeServer = new MultiPlexerTimeServer(port);
        new Thread(timeServer,"NIO-MultiolexerTimeServer-001").start();


    }

}
