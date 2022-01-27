package com.example.foodorderiing.activity.login;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.foodorderiing.R;
import com.example.foodorderiing.activity.File.FileActivity;
import com.example.foodorderiing.activity.home.HomeActivity;
import com.example.foodorderiing.database.DatabaseHelper;
import com.example.foodorderiing.database.dao.UserDao;
import com.example.foodorderiing.design.BlureImage;
import com.example.foodorderiing.helper.Session;
import com.example.foodorderiing.model.User;
import com.google.android.material.bottomsheet.BottomSheetDialog;


public class LoginActivity extends AppCompatActivity {
    private static final int STORAGE_PERMISSION_CODE = 101;
    public static final int CALL_PERMISSION_CODE = 100;

    private ImageView img_back;
    private CheckBox checkBox;
    private TextView tv_login , tv_NewAccount;
    private EditText et_name,et_password;
    private DatabaseHelper db;
    private UserDao dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);

        initID();
        initDataBase();
        blurBg();
        createNewAcount();
        hideKeyBoad();
        initLogin();
        setCheckBox();
    }


    private void initDataBase(){
        db = DatabaseHelper.getInstance(this);
        dao = db.userDao();
    }


    private void blurBg(){
        Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.food_honey);
        Bitmap bit_kebab = BlureImage.blur18(getApplicationContext(),bm,18f);
        img_back.setImageBitmap(bit_kebab);
    }


    private void initID(){
        img_back = findViewById(R.id.img_background);
        et_password = findViewById(R.id.et_pass);
        et_name = findViewById(R.id.et_name);
        tv_login = findViewById(R.id.tv_login);
        tv_NewAccount = findViewById(R.id.tv_newAcount);
        checkBox = findViewById(R.id.checkBox_loging);
    }


    private void setCheckBox(){
        checkBox.setChecked(false);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    String name = et_name.getText().toString();
                    String phone = et_password.getText().toString();
                    if(TextUtils.isEmpty(name) || TextUtils.isEmpty(phone)){
                        Toast.makeText(getApplicationContext(), "فیلد ها را پرکنید", Toast.LENGTH_SHORT).show();
                        checkBox.setChecked(false);
                    }else {
                        Boolean isCheck = checkBox.isChecked();
                        Session.getInstance().putExtra("name",name);
                        Session.getInstance().putExtra("phone",phone);
                        Session.getInstance().putExtra("isChecked" ,isCheck);
                    }
                }else if (!isChecked){
                    Session.getInstance().clearExtras();
                }
            }
        });

        if(Session.getInstance().getString("name") != null || Session.getInstance().getString("phone") != null ){
            et_name.setText(Session.getInstance().getString("name"));
            et_password.setText(Session.getInstance().getString("phone"));
            checkBox.setChecked(Session.getInstance().getBoolean("isChecked"));
        }
    }


    private void hideKeyBoad(){
        et_name.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken() , 0);
                }
            }
        });

        et_password.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken() , 0);
                }
            }
        });
    }


    private void createNewAcount(){
        tv_NewAccount.setOnClickListener(v -> {
            BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
            bottomSheetDialog.setContentView(R.layout.bottomsheet_sign_up);

            EditText name = (EditText)bottomSheetDialog.findViewById(R.id.et_name_signup);
            EditText pass = (EditText)bottomSheetDialog.findViewById(R.id.et_pass_signup);
            Button btn_signup = (Button)bottomSheetDialog.findViewById(R.id.sign_up);
            btn_signup.setOnClickListener(v1 -> {
                String getName = name.getText().toString();
                String getPass = pass.getText().toString();

                if(TextUtils.isEmpty(getName) || TextUtils.isEmpty(getPass)){
                    Toast.makeText(getApplicationContext(), "لطفا فیلدها را پر کنید", Toast.LENGTH_SHORT).show();
                }else {
                    dao.insertUser(new User(getName,getPass));
                    Toast.makeText(getApplicationContext(), "با موفقیت ثبت شد", Toast.LENGTH_SHORT).show();
                    bottomSheetDialog.dismiss();
                }
            });
            bottomSheetDialog.show();
        });
    }


    private void initLogin(){
        tv_login.setOnClickListener(v -> {
            String name = et_name.getText().toString();
            String pass = et_password.getText().toString();
            if(TextUtils.isEmpty(name) || TextUtils.isEmpty(pass)){
                Toast.makeText(getApplicationContext(), "لطفا فیلدها را پر کنید", Toast.LENGTH_SHORT).show();

            }else if(dao.getUser(name , pass) == null ){
                Toast.makeText(getApplicationContext(), "این کاربر وجود ندارد", Toast.LENGTH_SHORT).show();

            }else {
                if(checkPermission()){
                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                    startActivity(intent);
                    overridePendingTransition(android.R.anim.fade_in , android.R.anim.fade_out);
                    finish();
                }
            }
        });
    }




    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == STORAGE_PERMISSION_CODE ) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Storage Permission Granted", Toast.LENGTH_SHORT).show();

            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("desc_need_permission");
                builder.setPositiveButton("ok_permission", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri = Uri.fromParts("package", getPackageName(), null);
                        intent.setData(uri);
                        startActivityForResult(intent, STORAGE_PERMISSION_CODE);
                    }
                });
                builder.setNegativeButton("exit_app", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        finish();
                    }
                });
                builder.show();
            }
        }
    }

    public Boolean checkPermission() {
        String[] permission = {Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CALL_PHONE};
        int requestCode = STORAGE_PERMISSION_CODE;
        int requestCode_call = CALL_PERMISSION_CODE;
        if (ContextCompat.checkSelfPermission(this, permission[0]) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this, new String[] { permission[0] }, requestCode);
        }else if (ContextCompat.checkSelfPermission(this, permission[1]) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this, new String[]{permission[1]}, requestCode);
        }else if (ContextCompat.checkSelfPermission(this, permission[2]) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this, new String[]{permission[2]}, requestCode_call);
        }
        else {
            return true;
        }
        return false;
    }

}