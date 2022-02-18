package com.example.foodorderiing.helper;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.foodorderiing.R;

public final class CustomDialog {
    static ImageView email;
    static ImageView telegram;
    static ImageView instagram;


    public static void showDialogGuid(Activity activity, int layout, DrawerLayout drawer, Context context) {
        final android.app.Dialog dialog_guid = new android.app.Dialog(activity);
        dialog_guid.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog_guid.setContentView(layout);

        setTypeface(dialog_guid, context);

        TextView close_guid = dialog_guid.findViewById(R.id.close_dialog_guid);
        drawer.closeDrawer(GravityCompat.END);
        dialog_guid.show();
        dialog_guid.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog_guid.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        close_guid.setOnClickListener(v1 -> {
            dialog_guid.dismiss();
        });
    }

    public static void showDialogAboutUs(Activity activity, int layout, DrawerLayout drawer, Context context) {
        final android.app.Dialog dialog_guid = new android.app.Dialog(activity);
        dialog_guid.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog_guid.setContentView(layout);

        setTypeface(dialog_guid, context);
        setContactWithUs(dialog_guid, context);

        TextView close_guid = dialog_guid.findViewById(R.id.close_dialog_guid);
        drawer.closeDrawer(GravityCompat.END);
        dialog_guid.show();
        dialog_guid.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog_guid.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        close_guid.setOnClickListener(v1 -> {
            dialog_guid.dismiss();
        });
    }


    public static void setTypeface(Dialog dialog, Context context) {
        TextView text = dialog.findViewById(R.id.text11);
        TextView text1 = dialog.findViewById(R.id.text22);
        TextView text2 = dialog.findViewById(R.id.text33);
        TextView text3 = dialog.findViewById(R.id.text44);

        TextView tt = (TextView) text.findViewById(R.id.search_src_text);
        TextView tt2 = (TextView) text1.findViewById(R.id.search_src_text);
        TextView tt3 = (TextView) text2.findViewById(R.id.search_src_text);
        TextView tt4 = (TextView) text3.findViewById(R.id.search_src_text);
        Typeface myCustomFont = Typeface.createFromAsset(context.getAssets(), "font/iran_sans.ttf");
        text.setTypeface(myCustomFont);
        text1.setTypeface(myCustomFont);
        text2.setTypeface(myCustomFont);
        text3.setTypeface(myCustomFont);
    }

    public static void setContactWithUs(Dialog dialog, Context context) {
        email = dialog.findViewById(R.id.email);
        telegram = dialog.findViewById(R.id.telegram);
        instagram = dialog.findViewById(R.id.instagram);
        email.setOnClickListener(v -> {
            String[] TO = {"roghayek92@gmail.com"};
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setData(Uri.parse("n.alibagheri1998@gmail.com"));
            intent.putExtra(Intent.EXTRA_EMAIL, TO);
            intent.setType("message/rfc822");
            context.startActivity(Intent.createChooser(intent, "Select email"));

        });

        telegram.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://t.me/Semicolon_Developers"));
            context.startActivity(intent);
        });

        instagram.setOnClickListener(v -> {
            Uri uri = Uri.parse("http://instagram.com/_u/semicolon_developers");
            Intent likeIng = new Intent(Intent.ACTION_VIEW, uri);

            likeIng.setPackage("com.instagram.android");

            try {
                context.startActivity(likeIng);
            } catch (ActivityNotFoundException e) {

            }
        });
    }


    public static void setTypeFaceAlertDialog(AlertDialog dialog, Context context){
        TextView textView = (TextView) dialog.findViewById(android.R.id.message);
//        TextView alertTitle = (TextView) dialog.findViewById(android.R.id.alertTitle);
//        TextView alertTitle1 = (TextView) dialog.findViewById(androidx.appcompat.R.id.alertTitle);
        Button button1 = (Button) dialog.findViewById(android.R.id.button1);
        Button button2 = (Button) dialog.findViewById(android.R.id.button2);

        Typeface face = Typeface.createFromAsset(context.getAssets(), "font/iran_sans.ttf");
        textView.setTypeface(face);
        textView.setTextSize(12);

//        alertTitle1.setTypeface(face);
        button1.setTypeface(face);
        button1.setTextSize(12);
        button2.setTypeface(face);
        button2.setTextSize(12);
    }

}
