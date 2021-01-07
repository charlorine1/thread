package com.usst.cas;

public class AccountUnsafe implements Account {
    private Integer balance;

    @Override
    public Integer getBalance() {
        return balance;
    }

    //取amount块钱
    @Override
    public void withdraw(Integer amount) {
        synchronized (AccountUnsafe.this){
            this.balance=balance-amount;
       }
    }

    public AccountUnsafe(Integer balance) {
        this.balance = balance;
    }
}
