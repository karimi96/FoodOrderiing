package com.example.foodorderiing.activity.customer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.foodorderiing.R;
import com.example.foodorderiing.adapter.CustomerAdapter;
import com.example.foodorderiing.database.DatabaseHelper;
import com.example.foodorderiing.database.dao.CustomerDao;
import com.example.foodorderiing.model.Customer;
import com.example.foodorderiing.model.Product;
import com.google.gson.Gson;


public class AddNewCustomerActivity extends AppCompatActivity {

    EditText name ;
    EditText phone ;
    EditText address ;
    TextView save;
    TextView cancel;
    DatabaseHelper db;
    CustomerDao dao;
    Customer c;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_add_new_customer);


        db = DatabaseHelper.getInstance(getApplicationContext());
        dao = db.customerDao();
        init();
        hideInputType();



        if (getIntent().getExtras() != null){
            String getCustomer = getIntent().getStringExtra("customer");
            c = new Gson().fromJson(getCustomer, Customer.class);
            name.setText(c.name);
            phone.setText(c.phone);
            address.setText(c.address); }


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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


            }

        });


        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(android.R.anim.fade_in , android.R.anim.fade_out);

            }
        });

    }



    private void init(){
        name = findViewById(R.id.et_get_name_customer);
        phone = findViewById(R.id.et_get_phone_customer);
        address = findViewById(R.id.et_get_address_customer);
        save = findViewById(R.id.tv_save_customer);
        cancel = findViewById(R.id.tv_cancel_customer); }


    private void hideInputType(){
            name.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (!hasFocus) {
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(v.getWindowToken() , 0);
                    }

                }
            });

            phone.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (!hasFocus) {
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(v.getWindowToken() , 0);
                    }

                }
            });

            address.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (!hasFocus) {
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(v.getWindowToken() , 0);
                    }

                }
            });
        }


    @Override
    protected void onDestroy() {
        super.onDestroy();
//        if(db != null) db.close();
        }

}