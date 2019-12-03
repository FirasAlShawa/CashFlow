package com.firasshawa.cashflow.DataModels;

public class Main {
    String key ;
    int total;

    public Main() {
    }

    public Main(String key, int total) {
        this.key = key;
        this.total = total;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "Main{" +
                "key='" + key + '\'' +
                ", total=" + total +
                '}';
    }
}
