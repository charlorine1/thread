package com.map;

import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * @author yang
 * @title: LinkBlockingMap
 * @projectName thread
 * @description: TODO
 * @date 2022/6/2619:12
 */
public class LinkBlockingMap {

    //static Queue<String> queue = new LinkedBlockingQueue();
    private  static final BlockingQueue<String> queue = new LinkedBlockingQueue<>();



    public static void main(String[] args) {

        new Thread(()->{
/*               try {
             while (true){

                    String take = queue.poll();
                    System.out.println(22);
                    if(take != null){
                        System.out.println(take);
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }*/

                while (true){

                    String take = queue.poll();
                    System.out.println(22);
                    if(take != null){
                        System.out.println(take);
                    }
                }

        }





        ).start();


        new Thread(()->{
            try {
                TimeUnit.SECONDS.sleep(10);
              for (int i = 0 ;i<10;i++){
                  queue.put("hello wold");
              }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

    }
    
    
    
    
}
