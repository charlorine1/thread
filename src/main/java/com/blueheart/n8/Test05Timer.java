package com.blueheart.n8;

import javafx.concurrent.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Timer;
import java.util.TimerTask;

public class Test05Timer {
    static Logger log = LoggerFactory.getLogger(Test03ThreadPoolStatus.class);
    public static void main(String[] args) {

        Timer timer = new Timer();

        //创建2个任务
        TimerTask timerTask1 = new TimerTask() {
            @Override
            public void run() {
                log.info("第一个任务");

                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        TimerTask timerTask2 = new TimerTask() {
            @Override
            public void run() {
                log.info("第二个任务");

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        timer.schedule(timerTask1,2000);
        timer.schedule(timerTask2,1000);

    }
}
