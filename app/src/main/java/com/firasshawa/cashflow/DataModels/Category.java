package com.firasshawa.cashflow.DataModels;

public class Category {
    public String name;
    public String key;

    public Category() {
    }

    public Category(String name, String key) {
        this.name = name;
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public String toString() {
        return "Category{" +
                "name='" + name + '\'' +
                ", key='" + key + '\'' +
                '}';
    }
}
