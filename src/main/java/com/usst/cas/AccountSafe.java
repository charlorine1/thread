package com.usst.cas;

import java.util.concurrent.atomic.AtomicInteger;

public class AccountSafe implements Account {
    private AtomicInteger balance;

    @Override
    public Integer getBalance() {
        return balance.get();
    }

    @Override
    public void withdraw(Integer amount) {
        while (true){
            int pre = balance.get();
            int next=pre-amount;
            if(balance.compareAndSet(pre,next)){
                break;
            }
        }
    }

    public AccountSafe(AtomicInteger balance) {
        this.balance = balance;
    }
}
