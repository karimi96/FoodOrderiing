package com.example.foodorderiing.helper;

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




}
