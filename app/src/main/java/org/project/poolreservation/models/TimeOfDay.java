package org.project.poolreservation.models;

public class TimeOfDay {
    private String time;
    private String gender;
    private int capacity;
    private int off;
    private String price;
    private String id;
    private boolean show=true;

    public TimeOfDay(String id,String price,String time, String gender, int capacity) {
        this.price=price;
        this.id=id;
        this.time = time;
        this.gender = gender;
        this.capacity = capacity;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
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
