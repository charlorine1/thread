package com.usst.cas;

import java.util.concurrent.atomic.AtomicInteger;

public class BalanceDemo {
    public static void main(String[] args) {
        Account.demo(new AccountSafe(new AtomicInteger(10000)));

    }
}
