package com.firasshawa.cashflow.DataModels;

public class User {
    String key;
    String name;
    int current;
    int oldCurrent;
    String lastLog;

    public User() {
    }

    public User(String key, String name, int current, int oldCurrent, String lastLog) {
        this.key = key;
        this.name = name;
        this.current = current;
        this.oldCurrent = oldCurrent;
        this.lastLog = lastLog;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCurrent() {
        return current;
    }

    public void setCurrent(int current) {
        this.current = current;
    }

    public int getOldCurrent() {
        return oldCurrent;
    }

    public void setOldCurrent(int oldCurrent) {
        this.oldCurrent = oldCurrent;
    }

    public String getLastLog() {
        return lastLog;
    }

    public void setLastLog(String lastLog) {
        this.lastLog = lastLog;
    }

    @Override
    public String toString() {
        return "User{" +
                "key='" + key + '\'' +
                ", name='" + name + '\'' +
                ", current=" + current +
                ", oldCurrent=" + oldCurrent +
                ", lastLog='" + lastLog + '\'' +
                '}';
    }
}
