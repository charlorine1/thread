package com.usst.lomda;

public class Test1 {
    public static void main(String[] args) throws Exception{

/*        new Thread(){
            @Override
            public void run() {
                System.out.println("thead .....");
            }
        }.start();*/
        //new Thread(){() =>System.out.println("thead .....")}.start();

        Runnable r1 = () -> {
          //coding
        };


        Comparable c = new Comparable() {
            @Override
            public int compareTo(Object o) {
                return 0;
            }
        };
    }
}
