package com.usst.threadl;

public class SallTicketDmeo {

    public static void main(String[] args) {
        SallTicketDmeo sa = new SallTicketDmeo();
        synchronized (sa){
            sa.aa();
        }

    }

    private  void aa() {
        synchronized (this){
            System.out.println("----");
        }
    }
}

class Windows {
    private int count;

    public Windows(int count) {
        this.count = count;
    }

    //获得票的数量
    public int getCount() {
        return count;
    }



    //进行购票

}
