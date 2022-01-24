package com.example.foodorderiing.helper;

import android.graphics.Typeface;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.SearchView;

import com.example.foodorderiing.R;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
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

    public static String getDayAgo(int dayAgo){
        PersianCalendar now = new PersianCalendar();
        now.add(PersianCalendar.DAY_OF_YEAR , dayAgo);
        String currentDate = now.getPersianShortDate();
        return currentDate;
    }

    public static String getDayName(){
        PersianCalendar now = new PersianCalendar();
        String dayName = now.getPersianWeekDayName();
        return dayName;
    }

    public static String getMonthName(){
        PersianCalendar now = new PersianCalendar();
        String monthName = now.getPersianMonthName();
        return monthName;
    }


    public static String getSevenDayAgo(){
        PersianCalendar now = new PersianCalendar();
        String dayName = now.getPersianWeekDayName();
        String date = "";

        switch (dayName){
            case "شنبه" :
                date = getCurrentDate();
                break;
            case "یکشنبه" :
                date = getDayAgo(-1);
                break;
            case "دوشنبه" :
                date = getDayAgo(-2);
                break;
            case "سه شنبه" :
                date = getDayAgo(-3);
                break;
            case "چهارشنبه" :
                date = getDayAgo(-4);
                break;
            case "پنج شنبه" :
                date = getDayAgo(-5);
                break;
            case "جمعه" :
                date = getDayAgo(-6);
                break;
            default:
//                throw new IllegalStateException("Unexpected value: " + dayName);
        }
        return date ;
    }



    public static String getThirtyDaysAgo(){
        PersianCalendar now = new PersianCalendar();
        int day = now.getPersianDay();
        int thirtyAgo = (day - ((day * 2)-1)) ;
        String date = getDayAgo(thirtyAgo);
        return date;
    }


}
