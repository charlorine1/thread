package com.usst.dapter2.a2to1.a213Bio;

import java.io.*;
import java.net.Socket;

public class TimeClient {

    public static void main(String[] args) {
          for(;;){
              int port = 9999;
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
                  out.println("QUERY TIME ORDER");

/*                String xml = "QUERY TIME  ORDER11111";
                OutputStream os = socket.getOutputStream();
                os.write(xml.getBytes(),0,xml.length());*/


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


}
