package com.firasshawa.cashflow.DataModels;

public class Log {
    public String user;
    public String time;
    public String date;
    public int oldCurrent;
    public int value;
    public String category;

    public Log(String user, String time, String date, int oldCurrent, int value, String category) {
        this.user = user;
        this.time = time;
        this.date = date;
        this.oldCurrent = oldCurrent;
        this.value = value;
        this.category = category;
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

    @Override
    public String toString() {
        return "Log{" +
                "user='" + user + '\'' +
                ", time='" + time + '\'' +
                ", date='" + date + '\'' +
                ", oldCurrent=" + oldCurrent +
                ", value=" + value +
                ", category='" + category + '\'' +
                '}';
    }
}
