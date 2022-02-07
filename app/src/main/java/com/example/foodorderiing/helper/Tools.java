package com.example.foodorderiing.helper;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodorderiing.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
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
        return getDayAgo(thirtyAgo);
    }


    public static byte[] getBytes(Uri uri)  {
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();

//Bitmap bi= null;

        try {
//            bi.compress(Bitmap.CompressFormat.JPEG, 100, byteBuffer);
//            Log.d("TAG", "Width :" + bi.getWidth() + " Height :" + bi.getHeight());

            FileInputStream file = new FileInputStream(uri.getPath());
            int bufferSize = 1024;
            byte[] buffer = new byte[bufferSize];

            int len = 0;
            while ((len = file.read(buffer)) != -1) {
                byteBuffer.write(buffer, 0, len);
            }
        }catch (Exception exception){
            exception.printStackTrace();
        }
        return byteBuffer.toByteArray();
    }


    public static String saveFile(byte[] data,File DESTINY_DIR, String fileName) {
        Bitmap bitmap = null;
        if (!DESTINY_DIR.exists()) DESTINY_DIR.mkdirs();

        File mainPicture = new File(DESTINY_DIR, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(mainPicture);
//            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
//            Log.d("TAG", "Width :" + bitmap.getWidth() + " Height :" + bitmap.getHeight());

            fos.write(data);
            fos.close();
            return mainPicture.getPath();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

//
//    private Bitmap decodeFile(File f) {
//        Bitmap b = null;
//
//        //Decode image size
//        BitmapFactory.Options o = new BitmapFactory.Options();
//        o.inJustDecodeBounds = true;
//
//        FileInputStream fis = null;
//        try {
//            fis = new FileInputStream(f);
//            BitmapFactory.decodeStream(fis, null, o);
//            fis.close();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        int IMAGE_MAX_SIZE = 1024;
//        int scale = 1;
//        if (o.outHeight > IMAGE_MAX_SIZE || o.outWidth > IMAGE_MAX_SIZE) {
//            scale = (int) Math.pow(2, (int) Math.ceil(Math.log(IMAGE_MAX_SIZE /
//                    (double) Math.max(o.outHeight, o.outWidth)) / Math.log(0.5)));
//        }
//
//        //Decode with inSampleSize
//        BitmapFactory.Options o2 = new BitmapFactory.Options();
//        o2.inSampleSize = scale;
//        try {
//            fis = new FileInputStream(f);
//            b = BitmapFactory.decodeStream(fis, null, o2);
//            fis.close();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        Log.d("TAG", "Width :" + b.getWidth() + " Height :" + b.getHeight());
//
//        destFile = new File(file, "img_"
//                + dateFormatter.format(new Date()).toString() + ".png");
//        try {
//            FileOutputStream out = new FileOutputStream(destFile);
//            b.compress(Bitmap.CompressFormat.PNG, 100, out);
//            File file = new File(String.valueOf(b));
//            out.flush();
//            out.close();
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return b;
//    }
//
//
//    private void j(){
//    }



    public static void setReverseRecycler(Context context, RecyclerView recyclerView){
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setStackFromEnd(true);
        linearLayoutManager.setReverseLayout(true);
        recyclerView.setLayoutManager(linearLayoutManager);
    }


    public static void hideKeyBord(EditText editText, Context context) {
        editText.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
            }
        });
    }


    public static void layoutAnimationRecycler(RecyclerView recyclerView) {
        Context context = recyclerView.getContext();
        LayoutAnimationController layoutAnimationController = AnimationUtils.loadLayoutAnimation(context, R.anim.layout_fall_down);
        recyclerView.setLayoutAnimation(layoutAnimationController);
        recyclerView.getAdapter().notifyDataSetChanged();
        recyclerView.scheduleLayoutAnimation();
    }


}