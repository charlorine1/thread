package com.usst.httpDemo.t1;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerSocketDemo {

    public static void main(String[] args) throws IOException{

        ServerSocket serverSocket = new ServerSocket(18888);
        Socket accept = null;
        while (true){
            accept = serverSocket.accept();
            InputStream inputStream = accept.getInputStream();
            OutputStream outputStream = accept.getOutputStream();

            byte[] bytes = new byte[1024];
           while ((inputStream.read(bytes)) != -1){
               System.out.println(new String(bytes,"UTF-8"));
           }
            byte[] bytes1 = "我已经收到了".getBytes();
            outputStream.write(bytes1);
            outputStream.flush();
            outputStream.close();
            inputStream.close();

        }



    }
}
