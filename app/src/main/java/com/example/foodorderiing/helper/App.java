package com.example.foodorderiing.helper;

import android.Manifest;
import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.mohamadamin.persianmaterialdatetimepicker.utils.PersianCalendar;
import com.mohamadamin.persianmaterialdatetimepicker.utils.PersianCalendarUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.security.auth.login.LoginException;

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
    }
}
