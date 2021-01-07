package com.blueheart.n7;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


/**
 * @implSpec
This class is immutable and thread-safe.
 *
 */
public class Test02DateTimeFormatter {
    public static void main(String[] args) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                LocalDate date = dtf.parse("2020-12-25", LocalDate::from);
                System.out.println(date);
            }).start();
        }


    }
}
