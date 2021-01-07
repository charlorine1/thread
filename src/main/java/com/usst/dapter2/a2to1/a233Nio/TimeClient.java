package com.usst.dapter2.a2to1.a233Nio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class TimeClient {

    public static void main(String[] args) {
        int port = 18888;
        if(args != null && args.length>0) {
            try {
                port = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        Socket socket = null;
        BufferedReader in = null;
        PrintWriter out = null;
        try {
            socket = new Socket("localhost",port);
            InputStreamReader inputStreamReader = new InputStreamReader(socket.getInputStream());
            in = new BufferedReader(inputStreamReader);
            out = new PrintWriter(socket.getOutputStream(),true);
            String xml = "hello world";
           for (int i = 0 ; i< 1000 ; i++){
               out.println(xml);
           }
            System.out.println("send order 2 server succeed");
            String resp = in.readLine();
            System.out.println("NOW IS : " + resp);
        }catch (Exception e){

        }finally {
            if(out != null){
                out.close();
                out = null;
            }

            if(in != null){
                try {
                    in.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
                in = null;
            }

            if(socket != null){
                try {
                    socket.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
                socket = null;
            }
        }
    }

}
