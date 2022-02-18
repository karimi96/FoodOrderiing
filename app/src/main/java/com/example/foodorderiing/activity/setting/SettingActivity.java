package com.example.foodorderiing.activity.setting;

import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.foodorderiing.R;
import com.example.foodorderiing.database.DatabaseHelper;
import com.example.foodorderiing.database.dao.UserDao;
import com.example.foodorderiing.helper.App;
import com.google.android.material.bottomsheet.BottomSheetDialog;

public class SettingActivity extends AppCompatActivity {
    private LinearLayout linearName, linearPass;
    private DatabaseHelper db;
    private UserDao userDao;
    private TextView defultName;
    private EditText name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        setDataBase();
        initID();
        setDefultname();
        setLinearPass();
//        setLinearName();

    }

    @Override
    protected void onResume() {
        super.onResume();
        setLinearName();
//        if(userDao.getUserName() != null) name.setText(userDao.getUserName());
    }

    private void setDataBase(){
        db = App.getDatabase();
        userDao = db.userDao();
    }


    private void initID(){
        linearName = findViewById(R.id.linear_name_sitting);
        linearPass = findViewById(R.id.linear_pass_sitting);
        defultName = findViewById(R.id.tv_name_setting);
    }

    private void setDefultname(){
        defultName.setText(userDao.getUserName());
    }

    private void setLinearName(){
        linearName.setOnClickListener(v -> {
            BottomSheetDialog btn = new BottomSheetDialog(this);
            btn.setContentView(R.layout.bottom_sheet_name_setting);
            name = (EditText) btn.findViewById(R.id.et_name_setting);
            TextView save = (TextView) btn.findViewById(R.id.tv_saveName_setting);
            TextView cancel = (TextView) btn.findViewById(R.id.tv_cancelName_setting);

            name.setText(userDao.getUserName());
            save.setOnClickListener(v1 -> {
                String nameUser = name.getText().toString();
                userDao.updateUserName(nameUser);
                Toast.makeText(getApplicationContext(), "با موفقیت تغییر کرد", Toast.LENGTH_SHORT).show();
                btn.dismiss();
            });

            cancel.setOnClickListener(v1 -> {
                btn.dismiss();
            });
            btn.show();
        });
    }

    private void setLinearPass(){
        linearPass.setOnClickListener(v -> {
            BottomSheetDialog btn = new BottomSheetDialog(this);
            btn.setContentView(R.layout.bottom_sheet_pass_setting);
            EditText oldPass = (EditText) btn.findViewById(R.id.et_oldPass_setting);
            EditText newPass = (EditText) btn.findViewById(R.id.et_newPass_setting);
            EditText confirmPass = (EditText) btn.findViewById(R.id.et_confirmPass_setting);
            TextView save = (TextView) btn.findViewById(R.id.tv_saveName_setting);
            TextView cancel = (TextView) btn.findViewById(R.id.tv_cancelName_setting);

            save.setOnClickListener(v1 -> {
                if(oldPass.getText().toString().equals(userDao.getUserPass())){
                    if(newPass.getText().toString().equals(confirmPass.getText().toString())){
                        userDao.updateUserPass(newPass.getText().toString());
                        Toast.makeText(getApplicationContext(), "با موفقیت تغییر کرد", Toast.LENGTH_SHORT).show();
                        btn.dismiss();
                    }else Toast.makeText(getApplicationContext(), "پسورد مطابقت ندارد", Toast.LENGTH_SHORT).show();

                }else {
                    Toast.makeText(getApplicationContext(), "پسورد وارد شده اشتباه است", Toast.LENGTH_SHORT).show();
                }
            });

            cancel.setOnClickListener(v1 -> {
                btn.dismiss();
            });
            btn.show();

        });


    }



}