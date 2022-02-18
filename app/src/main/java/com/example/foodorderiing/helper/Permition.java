package com.example.foodorderiing.helper;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Settings;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class Permition extends AppCompatActivity {
    //    public static String CALL_PHONE =  ContextCompat.checkSelfPermission(context, permission[0]) == PackageManager.PERMISSION_DENIED
    public int code;
    public Context context;
    public Activity activity;

//    public Permition(int code, Context context, Activity activity) {
//        this.code = code;
//        this.context = context;
//        this.activity = activity;
//    }

//    public Permition(int contentLayoutId, int code, Context context, Activity activity) {
//        super(contentLayoutId);
//        this.code = code;
//        this.context = context;
//        this.activity = activity;
//    }

    public Boolean checkPermission() {
        String[] permission = {Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CALL_PHONE};
        int requestCode = 0;
        int requestCode_call = 0;
        if(code == 100 ){
            requestCode = 100;

        }else if(code == 200){
            requestCode_call = 200;
        }
        if (ContextCompat.checkSelfPermission(context, permission[0]) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(activity, new String[] { permission[0] }, requestCode);
        }else if (ContextCompat.checkSelfPermission(context, permission[1]) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(activity, new String[]{permission[1]}, requestCode);
        }else if (ContextCompat.checkSelfPermission(context, permission[2]) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(activity, new String[]{permission[2]}, requestCode_call);
        }
        else {
            return true;
        }
        return false;

    }


    public Boolean checkCallPermition(Context context, Activity activity){
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.CALL_PHONE}, 200);
        }else {
            return true;
        }
        return false;
    }



    public Boolean checkStoregPermition(Context context, Activity activity) {
        String[] permission = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};

        if (ContextCompat.checkSelfPermission(context, permission[0]) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(activity, new String[]{permission[0]}, 100);
        } else if (ContextCompat.checkSelfPermission(context, permission[1]) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(activity, new String[]{permission[1]}, 100);
        }
        else {
            return true;
        }
        return false;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == code ) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED){
//                Toast.makeText(this, "Storage Permission Granted", Toast.LENGTH_SHORT).show();

            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage(" دسترسی به مجوزها ");
                builder.setPositiveButton("برو به تنظیمات", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri = Uri.fromParts("package", getPackageName(), null);
                        intent.setData(uri);
                        startActivityForResult(intent, code);
                    }
                });
                builder.setNegativeButton("بستن", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
//                        finish();
                    }
                });
                builder.show();
            }
        }
    }







}
