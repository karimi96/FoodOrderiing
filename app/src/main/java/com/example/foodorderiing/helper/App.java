package com.example.foodorderiing.helper;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.example.foodorderiing.database.DatabaseHelper;

public class App extends Application {
    public static Context context;
    public static DatabaseHelper db;

    @Override
    public void onCreate() {
        super.onCreate();
//        context = getApplicationContext();
        context = this;
        db = DatabaseHelper.getInstance(context);
        Log.e("qqqqapp", "onCreate: app" );

    }

    public static DatabaseHelper getDatabase(){
        if (!db.isOpen() || db == null){

            db = DatabaseHelper.getInstance(context);
        }
        return db;
    }

}
