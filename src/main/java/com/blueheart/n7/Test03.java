package com.blueheart.n7;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Thread.sleep;

public class Test03 {
    public static void main(String[] args) {
        new Thread(()->{
            List<Person> personList = new ArrayList<>();
            System.out.println("111111");
            int i = 0;
           while (true){
               try {
                   i=i+1;
                   personList.add(new Person("YCX"+i,i));
                   Thread.sleep(1);
               } catch (InterruptedException e) {
                   e.printStackTrace();
               }
           }
            //System.out.println("222222");

        },"t22").start();

        DateTimeFormatter date = DateTimeFormatter.ofPattern("yyyy-mm-dd");

        System.out.println("33333");

    }
}


class  Person{
    private String name;
    private int age;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}