package com.example.foodorderiing.helper;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.foodorderiing.R;

public abstract class Test {
    public static LinearLayout edit;
    private static LinearLayout delete;
    private static Dialog dialog_sheet;

    public  void getTest(Context context, int layout, int edite,int deletee,int namee, String name){
        dialog_sheet = new Dialog(context);
        dialog_sheet.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog_sheet.setContentView(layout);

        edit = dialog_sheet.findViewById(edite);
        delete = dialog_sheet.findViewById(deletee);
        TextView title = dialog_sheet.findViewById(namee);
        title.setText(name);
        dialog_sheet.show();
        dialog_sheet.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog_sheet.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog_sheet.getWindow().getAttributes().windowAnimations = R.style.DialogAnimationSheet;
        dialog_sheet.getWindow().setGravity(Gravity.BOTTOM);

    }

    public void onClickEdit(){
        edit.setOnClickListener(v -> {

        });
    }

    public void onClickDelete(){
        delete.setOnClickListener(v -> {

        });
    }

    public interface onClick{
        void editClick();
        void delete();
    }

}
