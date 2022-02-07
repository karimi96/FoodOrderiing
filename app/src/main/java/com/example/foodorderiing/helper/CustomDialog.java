package com.example.foodorderiing.helper;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.foodorderiing.R;

public final class CustomDialog {


    public static void showDialog(Activity activity, int layout, DrawerLayout drawer, Context context){
        final android.app.Dialog dialog_guid = new android.app.Dialog(activity);
        dialog_guid.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog_guid.setContentView(layout);

        setTypeface(dialog_guid , context);

        TextView close_guid = dialog_guid.findViewById(R.id.close_dialog_guid);
        drawer.closeDrawer(GravityCompat.END);
        dialog_guid.show();
        dialog_guid.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog_guid.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        close_guid.setOnClickListener(v1 -> {
            dialog_guid.dismiss();
        });
    }


    public static void setTypeface(Dialog dialog, Context context){
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

}
