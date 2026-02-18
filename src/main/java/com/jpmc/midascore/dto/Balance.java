package com.jpmc.midascore.dto;

public class Balance {

    private float amount;

    public Balance(float amount) {
        this.amount = amount;
    }

    public float getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        return "Balance{amount=" + amount + '}';
    }
}
