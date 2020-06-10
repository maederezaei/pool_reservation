package org.project.poolreservation.models;

public class TimeOfDay {
    private String time;
    private int gender;
    private int capacity;
    private int off;
    private boolean show=true;

    public TimeOfDay(String time, int gender, int capacity, int off) {
        this.time = time;
        this.gender = gender;
        this.capacity = capacity;
        this.off = off;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getOff() {
        return off;
    }

    public void setOff(int off) {
        this.off = off;
    }

    public boolean isShow() {
        return show;
    }

    public void setShow(boolean show) {
        this.show = show;
    }
}
