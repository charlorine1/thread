package com.blueheart.n8.reentrantlock;

import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.ReentrantLock;

public class Demo1 {

    public static void main(String[] args) {

      //  addWaiter(AbstractQueuedSynchronizer.Node.EXCLUSIVE);
        ReentrantLock lock = new ReentrantLock();


      new Thread(() -> {
          System.out.println("name : " + Thread.currentThread().getName());
          System.out.println("name : " + Thread.currentThread().getId());
          try {
              Thread.sleep(1000000);
          } catch (InterruptedException e) {
              e.printStackTrace();
          }
      }).start();

    }


}
