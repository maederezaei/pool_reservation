package org.project.poolreservation.models;

import java.util.List;

public class DayOfWeek {
    private String day;
    private String date;
    //private List<TimeOfDay>timeOfDays=null;

    public DayOfWeek(String day, String date) {
        this.day = day;
        this.date = date;
        //this.timeOfDays = timeOfDays;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

  /*  public List<TimeOfDay> getTimeOfDays() {
        return timeOfDays;
    }

    public void setTimeOfDays(List<TimeOfDay> timeOfDays) {
        this.timeOfDays = timeOfDays;
    }*/
}
