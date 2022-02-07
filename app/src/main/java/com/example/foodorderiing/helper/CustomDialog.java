package com.example.foodorderiing.helper;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import com.example.foodorderiing.R;

public final class CustomDialog {

    public static void showDialog(Activity activity,int layout,DrawerLayout drawer){
        final android.app.Dialog dialog_guid = new android.app.Dialog(activity);
        dialog_guid.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog_guid.setContentView(layout);

        TextView close_guid = dialog_guid.findViewById(R.id.close_dialog_guid);
        drawer.closeDrawer(GravityCompat.END);
        dialog_guid.show();
        dialog_guid.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog_guid.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        close_guid.setOnClickListener(v1 -> {
            dialog_guid.dismiss();
        });
    }

}
