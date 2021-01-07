package com.usst.dapter2.a2to1.a213Bio;

import java.io.*;
import java.net.Socket;
import java.util.Date;

public class TimeServerHandler implements Runnable {
    private Socket socket ;

    public  TimeServerHandler(Socket socket){
        this.socket = socket;
    }

    @Override
    public void run() {
        BufferedReader reader = null;
        PrintWriter writer = null;

        try {
            InputStream inputStream = this.socket.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            reader = new BufferedReader(inputStreamReader);
            writer = new PrintWriter(this.socket.getOutputStream(),true);
            String currentTime = null;
            String body = null ;
            while (true){
                System.out.println("连接后没有数据会阻塞在该处。。。。。。。。。1111111111");
                body = reader.readLine();
               // System.out.println("长度+++++   ==="+ inputStreamReader.read());
                System.out.println("连接后没有数据会阻塞在该处。。。。。。。。。2222222222");
                if(body == null)
                    break;
                System.out.println("this time server receive order :" + body);
                currentTime = "QUERY TIME ORDER".equalsIgnoreCase(body)? new Date(
                        System.currentTimeMillis()).toString(): "BAD ORDER";
              //  writer.print(currentTime);
                writer.println(currentTime);
            }
        }catch (Exception e){
            if(reader != null){
                try {
                    reader.close();
                }catch (IOException e1){
                    e1.printStackTrace();
                }
            }
            if(writer != null){
                writer.close();
                writer = null;
            }
            if(this.socket != null){
                try{
                    this.socket.close();
                }catch (IOException e1){
                    e1.printStackTrace();
                }
                this.socket = null;
            }
        }
    }
}
