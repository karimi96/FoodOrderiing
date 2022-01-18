package com.example.foodorderiing.helper;

import android.widget.Toast;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;

import ir.hamsaa.persiandatepicker.util.PersianCalendar;

public class Tools {
    public static Integer convertToPrice(String value){
        return Integer.valueOf(value.replace(".","")
                .replace(",","")
                .replace(" ",""));
    }

    public static String getForamtPrice(String price){
        Double number = Double.valueOf(price);
        DecimalFormat dec = new DecimalFormat("###,###,###");
        dec.setMinimumFractionDigits(0);
        return dec.format(number);
    }

    public static Date getDate(int dayAgo){
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.DAY_OF_YEAR,dayAgo);
        return c.getTime();
    }


    public static String getCurrentDate(){
        PersianCalendar now = new PersianCalendar();
        String currentDate = now.getPersianShortDate();
        return currentDate;
    }

    public static String getSevenDayAgo(int dayAgo){
        PersianCalendar now = new PersianCalendar();
        now.add(PersianCalendar.DAY_OF_YEAR , dayAgo);
        String currentDate = now.getPersianShortDate();
        return currentDate;
    }

    public static String getDayName(){
        PersianCalendar now = new PersianCalendar();
        String currentDate = now.getPersianWeekDayName();
        return currentDate;
    }


    public static String dayAgo(){
        PersianCalendar now = new PersianCalendar();
        String dayName = now.getPersianWeekDayName();
        String date = "";

        switch (dayName){
            case "شنبه" :
                date = getCurrentDate();
                break;
            case "یکشنبه" :
                date = getSevenDayAgo(-1);
                break;
            case "دوشنبه" :
                date = getSevenDayAgo(-2);
                break;
            case "سه شنبه" :
                date = getSevenDayAgo(-3);
                break;
            case "چهارشنبه" :
                date = getSevenDayAgo(-4);
                break;
            case "پنج شنبه" :
                date = getSevenDayAgo(-5);
                break;
            case "جمعه" :
                date = getSevenDayAgo(-6);
                break;
            default:
//                throw new IllegalStateException("Unexpected value: " + dayName);
        }
        return date ;
    }


}
