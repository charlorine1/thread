package com.usst.java8;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Optional;

public class OptionalDemo {

    public static void main(String[] args) {
        String str = null;

        Optional<String> str1 = Optional.of(str);
        if(str1.isPresent()){
           // System.out.println("str is null");
            Log log = LogFactory.getLog(OptionalDemo.class);
            log.info("str is null");
        }
    }
}
