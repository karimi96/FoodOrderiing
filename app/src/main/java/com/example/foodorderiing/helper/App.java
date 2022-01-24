package com.example.foodorderiing.helper;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.mohamadamin.persianmaterialdatetimepicker.utils.PersianCalendar;
import com.mohamadamin.persianmaterialdatetimepicker.utils.PersianCalendarUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class App extends Application {
    public static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();

//        Log.e("qqqqapp", "onCreate now: " +PersianCalendarUtils.julianToPersian(new Date().getTime()));
//        Log.e("qqqqapp", "onCreate: 7 day ago: " +PersianCalendarUtils.julianToPersian(Tools.getDate(-7).getTime()) );



        Log.e("qqqqapp", "onCreate now: " + Tools.getCurrentDate());
//        Log.e("qqqqapp", "onCreate: 7 day ago: " + Tools.getSevenDayAgo(-7) );
        Log.e("qqqqapp", "onCreate: 7 day ago : " + Tools.getSevenDayAgo() );
        Log.e("qqqqapp", "onCreate: thirty days ago : " + Tools.getThirtyDaysAgo() );
        Log.e("qqqqapp", "onCreate: thirty days ago : " + Tools.getMonthName());


        List<Integer> ff = new ArrayList<>();
        ff.add(0 , 10);
        ff.add(1 , 20);
        ff.add(2 , 30);
        ff.add(3 , 40);

        for (int i = 0; i < ff.size(); ) {
        Log.e("aaa", "onCreate: test " + ff.get(i) );
            i+=2;
        }

    }
}
