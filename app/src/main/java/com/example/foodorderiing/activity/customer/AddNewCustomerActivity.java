package com.example.foodorderiing.activity.customer;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.foodorderiing.R;
import com.example.foodorderiing.database.DatabaseHelper;
import com.example.foodorderiing.database.dao.CustomerDao;
import com.example.foodorderiing.helper.App;
import com.example.foodorderiing.helper.Tools;
import com.example.foodorderiing.model.Customer;
import com.google.gson.Gson;


public class AddNewCustomerActivity extends AppCompatActivity {
    private EditText name ,phone ,address ;
    private TextView save ,cancel;
    private DatabaseHelper db;
    private CustomerDao dao;
    private Customer c;
    private RelativeLayout mainBack;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_add_new_customer);

        initDataBase();
        init();
        hideKayBord();

        if (getIntent().getExtras() != null){
            String getCustomer = getIntent().getStringExtra("customer");
            c = new Gson().fromJson(getCustomer, Customer.class);
            name.setText(c.name);
            phone.setText(c.phone);
            address.setText(c.address); }

        actionSave();
        actionCancel();
    }


    private void initDataBase(){
        db = App.getDatabase();
        dao = db.customerDao();
    }


    private void init(){
        name = findViewById(R.id.et_get_name_customer);
        phone = findViewById(R.id.et_get_phone_customer);
        address = findViewById(R.id.et_get_address_customer);
        save = findViewById(R.id.tv_save_customer);
        cancel = findViewById(R.id.tv_cancel_customer);
    }


    private void initGradiant(){
        AnimationDrawable animationDrawable = (AnimationDrawable) mainBack.getBackground();
        animationDrawable.setEnterFadeDuration(2500);
        animationDrawable.setExitFadeDuration(5000);
        animationDrawable.start();
    }


    private void actionSave(){
        save.setOnClickListener(v -> {
                String cName = name.getText().toString();
                String cPhone = phone.getText().toString();
                String cAddress = address.getText().toString();

                if(c == null){
                    if(TextUtils.isEmpty(cName) || TextUtils.isEmpty(cPhone) || TextUtils.isEmpty(cAddress)){
                        Toast.makeText(getApplicationContext(), "فیلد مورد نظر را پرکنید!!!", Toast.LENGTH_SHORT).show();

                    }else if(cPhone.length() != 11) {
                        Toast.makeText(getApplicationContext(), "شماره تلفن باید 11 کاراکترباشد", Toast.LENGTH_SHORT).show();

                    }else {
                        dao.insertCustomer(new Customer(cName,cPhone,cAddress));
                        Toast.makeText(getApplicationContext(), cName + " با موفقیت به لیست اضافه شد ", Toast.LENGTH_LONG).show();
                        finish();
                    }

                }else {
                    c.name = cName;
                    c.phone = cPhone;
                    c.address = cAddress;
                    Log.e("qqqq", "onClick: update customer =" + c.customer_id );
                    dao.updateCustomer(c);
                    finish();
                }
                overridePendingTransition(android.R.anim.fade_in , android.R.anim.fade_out);
        });
    }


    private void actionCancel(){
        cancel.setOnClickListener(v -> {
                finish();
                overridePendingTransition(android.R.anim.fade_in , android.R.anim.fade_out);
        });
    }


    private void hideKayBord(){
        Tools.hideKeyBord(name, this);
        Tools.hideKeyBord(phone, this);
        Tools.hideKeyBord(address, this);
        }
}