package com.firasshawa.cashflow.DataModels;

public class Log {
    public String key;
    public String user;
    public String time;
    public String date;
    public int oldCurrent;
    public int newCurrent;
    public int value;
    public String category;
    public String desc;
    public String type;

    public Log() {
    }


    public Log(String key, String user, String time, String date, int oldCurrent, int newCurrent, int value, String category, String desc, String type) {
        this.key = key;
        this.user = user;
        this.time = time;
        this.date = date;
        this.oldCurrent = oldCurrent;
        this.newCurrent = newCurrent;
        this.value = value;
        this.category = category;
        this.desc = desc;
        this.type = type;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getOldCurrent() {
        return oldCurrent;
    }

    public void setOldCurrent(int oldCurrent) {
        this.oldCurrent = oldCurrent;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int getNewCurrent() {
        return this.newCurrent;
    }

    public void setNewCurrent(int newCurrent) {
        this.newCurrent = newCurrent;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Log{" +
                "key='" + key + '\'' +
                ", user='" + user + '\'' +
                ", time='" + time + '\'' +
                ", date='" + date + '\'' +
                ", oldCurrent=" + oldCurrent +
                ", newCurrent=" + newCurrent +
                ", value=" + value +
                ", category='" + category + '\'' +
                ", desc='" + desc + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
