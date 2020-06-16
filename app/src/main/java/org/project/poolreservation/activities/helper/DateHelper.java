package org.project.poolreservation.activities.helper;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;
import org.project.poolreservation.R;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import ir.hamsaa.persiandatepicker.Listener;
import ir.hamsaa.persiandatepicker.PersianDatePickerDialog;
import ir.hamsaa.persiandatepicker.util.PersianCalendar;


public class DateHelper {

    private String TAG = this.getClass().getName();

    public interface DateHelperPresenter {
        void onDateSelected(String datePersian, String dateAD, int requestCode);
    }

    private DateHelperPresenter dateHelperPresenter;

    public void setDateHelperPresenter(DateHelperPresenter dateHelperPresenter) {
        this.dateHelperPresenter = dateHelperPresenter;
    }

    public static String getTodayDate() {
        String date = "", time = "";
        PersianCalendar persianCalendar = new PersianCalendar();
        date = persianCalendar.getPersianYear() + "-" + getWithZeroDate(persianCalendar.getPersianMonth()) + "-" + getWithZeroDate(persianCalendar.getPersianDay());
        Date d = new Date();
        time = getWithZeroDate(d.getHours()) + ":" + getWithZeroDate(d.getMinutes());

        return date + " " + time;
    }


    public static String getTodayDateAD() {
        String date = "", time = "";
        Date d = new Date();
        date = d.getYear() + 1900 + "-" + getWithZeroDate(d.getMonth() + 1) + "-" + getWithZeroDate(d.getDate());
        time = getWithZeroDate(d.getHours()) + ":" + getWithZeroDate(d.getMinutes()) + ":" + getWithZeroDate(d.getSeconds());

        return date + " " + time;
    }

    public String getDateADByJalali(String date) {
        Log.d(TAG, "getDateADByJalali: " + date);
        String dateAD = "";
        try {
            int y = Integer.parseInt(date.substring(0, 4));
            int m = Integer.parseInt(date.substring(5, 7)) - 1;
            int d = Integer.parseInt(date.substring(8, 10));

            PersianCalendar persianCalendar = new PersianCalendar();
            persianCalendar.setPersianDate(y, m, d);

            Date date1 = persianCalendar.getTime();

            dateAD = (1900 + date1.getYear()) + "-" + getWithZeroDate(date1.getMonth() + 1) + "-" + getWithZeroDate(date1.getDate());

            dateAD += " " + date.substring(11);

            Log.d(TAG, "getDateADByJalali: " + dateAD);


        } catch (Exception e) {
            e.printStackTrace();
        }
        return dateAD;

    }

    public String getDateJalaliByAD(String date, boolean withoutSeconds) {
        Log.d(TAG, "getDateJalaliByAD: " + date);
        String dateAD = "";
        try {
            int y = Integer.parseInt(date.substring(0, 4));
            int m = Integer.parseInt(date.substring(5, 7)) - 1;
            int d = Integer.parseInt(date.substring(8, 10));

//            String hour = date.substring(11, 13);
//            String min = date.substring(14, 16);
//            String sec = date.substring(17, 19);

            PersianCalendar persianCalendar = new PersianCalendar();
            persianCalendar.set(y, m, d);

            dateAD = (persianCalendar.getPersianYear()) + "-" + getWithZeroDate(persianCalendar.getPersianMonth()) + "-" + getWithZeroDate(persianCalendar.getPersianDay());
            dateAD += " " + date.substring(11, date.length() - 3);

            Log.d(TAG, "getDateJalaliByAD: " + dateAD);


        } catch (Exception e) {
            e.printStackTrace();
        }
        return dateAD;

    }


    private static String getWithZeroDate(int i) {
        if (i < 10) {
            return "0" + i;
        } else {
            return i + "";
        }
    }

    private static String getWithFormatTimeStamp(int y, int m, int d) {
        return (y + 1900) + "-" + getWithZeroDate(m + 1) + "-" + getWithZeroDate(d);
    }


    public void getDateTimeDialog(final Context context, final int requestCode) {

        final Map<String, String> result = new HashMap<>();
        PersianDatePickerDialog picker2 = new PersianDatePickerDialog(context)
                .setPositiveButtonString("تایید")
                .setNegativeButton("بیخیال")
                .setActionTextColor(context.getResources().getColor(R.color.colorAccent))
                .setListener(new Listener() {
                    @Override
                    public void onDateSelected(PersianCalendar persianCalendar) {

                        final String date = persianCalendar.getPersianYear() + "-" + getWithZeroDate(persianCalendar.getPersianMonth()) + "-" + getWithZeroDate(persianCalendar.getPersianDay());
                        final Date dateAD = persianCalendar.getTime();
                       /* TimePickerDialog timePickerDialog = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
                         //   @Override
                            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                                String time = getWithZeroDate(i) + ":" + getWithZeroDate(i1) + ":00";
                                result.put("date_persian", date + " " );
                                result.put("date_ad", getWithFormatTimeStamp(dateAD.getYear(), dateAD.getMonth(), dateAD.getDate()) + " " + time);


                                if (dateAD.getTime() < new Date().getTime()) {
                                    Toast.makeText(context, "تاریخ انتخاب شده نباید مربوط به گذشته باشد.", Toast.LENGTH_SHORT).show();
                                } else {
                                    dateHelperPresenter.onDateSelected(date + " " , getWithFormatTimeStamp(dateAD.getYear(), dateAD.getMonth(), dateAD.getDate()) + " " + time, requestCode);
                                }
                            }
                        }, new Date().getHours(), new Date().getMinutes(), true);

                        timePickerDialog.show();*/
                        result.put("date_persian", date + " " );
                        result.put("date_ad", getWithFormatTimeStamp(dateAD.getYear(), dateAD.getMonth(), dateAD.getDate()) + " ");


                        if (dateAD.getTime() < new Date().getTime()) {
                            Toast.makeText(context, "تاریخ انتخاب شده نباید مربوط به گذشته باشد.", Toast.LENGTH_SHORT).show();
                        } else {
                            dateHelperPresenter.onDateSelected(date + " " , getWithFormatTimeStamp(dateAD.getYear(), dateAD.getMonth(), dateAD.getDate()) + " " , requestCode);
                        }

                    }

                    @Override
                    public void onDisimised() {

                    }

                });


        picker2.show();

    }

    public static Integer diffDateDays(String dateAD) {
        Integer days = null;
        try {
            Calendar calendar = Calendar.getInstance();
            int y = Integer.parseInt(dateAD.substring(0, 4));
            int m = Integer.parseInt(dateAD.substring(5, 7)) - 1;
            int d = Integer.parseInt(dateAD.substring(8, 10));
            calendar.set(y, m, d);
            Calendar calendarToday = Calendar.getInstance();
            long timeDef = (8 * 60 * 60 * 1000) - ((60 * 1000) + (50 * 1000));
            long diff = calendarToday.getTimeInMillis() - ((calendar.getTimeInMillis() - timeDef));
            days = Math.round(diff / (1000 * 60 * 60 * 24));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return days;
    }


    public static DateClass diffDateDays(int dateFirst, int dateSecond) {
        DateClass dateClass = new DateClass();
        try {

            int diff = dateSecond - dateFirst;
            int days = diff / (60 * 60 * 24);
            int hours = diff - (days * ((60 * 60 * 24)));
            int minutes = (diff / (60));
            int seconds = diff;

            dateClass.setDays(days);
            dateClass.setHours(hours);
            dateClass.setMinutes(minutes);
            dateClass.setSeconds(seconds);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return dateClass;
    }
    public static class DateClass{

        public int days;
        public int hours;
        public int minutes;
        public int seconds;


        public void setDays(int days) {
            this.days = days;
        }

        public void setHours(int hours) {
            this.hours = hours;
        }

        public void setMinutes(int minutes) {
            this.minutes = minutes;
        }
        public void setSeconds(int seconds) {
            this.seconds = seconds;
        }
    }


}
