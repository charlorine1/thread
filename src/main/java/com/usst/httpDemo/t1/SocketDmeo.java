package com.usst.httpDemo.t1;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class SocketDmeo {

    public static void main(String[] args) {
        Socket socket = null;
        try {
            socket = new Socket("localhost",18888);
            OutputStream outputStream = socket.getOutputStream();
            byte[] bytes = "hello world".getBytes();
            outputStream.write(bytes);
            outputStream.flush();
            socket.shutdownOutput();

            InputStream inputStream = socket.getInputStream();
            byte[] b = new byte[1024];
            String s = "";

            while ((inputStream.read(b) != -1)){
                s = new String(b,"UTF-8");
            }
            System.out.println(s);

            inputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }





    }

}
