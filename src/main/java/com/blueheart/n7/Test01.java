package com.blueheart.n7;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * 下面的代码在运行时，由于 SimpleDateFormat 不是线程安全的
 * 所以在要加锁 ，这样虽能解决问题，但带来的是性能上的损失，并不算很好：
 */
public class Test01 {
    public static void main(String[] args) {
        //SimpleFormatter sp = new SimpleFormatter("yyyy-mm-dd");
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-mm-dd");
        for (int i = 0; i <10 ; i++) {
            new Thread(){
                @Override
                public void run() {
                    try {
                        synchronized (Test01.class){
                            Date date = sf.parse("2020-12-25");
                            System.out.println(date);
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }.start();
        }

    }
}