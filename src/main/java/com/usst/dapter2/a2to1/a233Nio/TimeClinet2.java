package com.usst.dapter2.a2to1.a233Nio;

public class TimeClinet2 {
    public static void main(String[] args) {
        int port = 8080;
        if(args != null && args.length >0){
            try {
                port = Integer.parseInt(args[0]);
            }catch (NumberFormatException e){
                e.printStackTrace();
            }
        }
        new Thread(new TimeClinetHandle("127.0.0.1",port)).start();
    }
}
