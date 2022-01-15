package com.example.foodorderiing.helper;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.mohamadamin.persianmaterialdatetimepicker.utils.PersianCalendar;
import com.mohamadamin.persianmaterialdatetimepicker.utils.PersianCalendarUtils;

import java.util.Date;

public class App extends Application {
    public static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();

        Log.e("qqqqapp", "onCreate now: " +PersianCalendarUtils.julianToPersian(new Date().getTime()));
        Log.e("qqqqapp", "onCreate: 7 day ago: " +PersianCalendarUtils.julianToPersian(Tools.getDate(-7).getTime()) );


    }
}
