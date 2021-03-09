package com.jdk8.foreach2remove;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Foreach2Remove {


    public static void main(String[] args) {

        List<String> lists = new ArrayList<>();
        lists.add("1");
        lists.add("2");
        System.out.println(lists);
/*        for (String item : lists){
            if("2".equals(item)){
                lists.remove("2");
            }
        }*/
/*        Iterator<String> iterator = lists.iterator();
        while (iterator.hasNext()){
            if("1".equals(iterator.next())){
                iterator.remove();
            }
        }*/
        lists.removeIf("1"::equals);
        System.out.println(lists);




    }

}
