package com.example.foodorderiing.activity.customer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodorderiing.R;
import com.example.foodorderiing.activity.grouping.AddNewGroupingActivity;
import com.example.foodorderiing.adapter.CustomerAdapter;
import com.example.foodorderiing.database.DatabaseHelper;
import com.example.foodorderiing.database.dao.CustomerDao;
import com.example.foodorderiing.model.Customer;
import com.example.foodorderiing.model.Grouping;

public class AddNewCustomerActivity extends AppCompatActivity {

    EditText name ;
    EditText phone ;
    EditText address ;
    TextView save;
    TextView cancel;
    DatabaseHelper db;
    CustomerDao dao;
    CustomerAdapter customerAdapter;
    RecyclerView.ViewHolder viewHolder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_customer);

        init();
        db = DatabaseHelper.getInstance(getApplicationContext());
        dao = db.customerDao();
//        int positionn = viewHolder.getAdapterPosition();



        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String cName = name.getText().toString();
                String cPhone = phone.getText().toString();
                String cAddress = address.getText().toString();

                if(TextUtils.isEmpty(cName) || TextUtils.isEmpty(cPhone) || TextUtils.isEmpty(cAddress)){

                    Toast.makeText(getApplicationContext(), "فیلد مورد نظر را پرکنید!!!", Toast.LENGTH_SHORT).show();

                }else {
                    Customer customer = new Customer(cName , cPhone , cAddress);
                    dao.insertCustomer(customer);
                    finish();
//                    customerAdapter.notifyItemChanged(positionn);

                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void init(){
        name = findViewById(R.id.et_get_name_customer);
        phone = findViewById(R.id.et_get_phone_customer);
        address = findViewById(R.id.et_get_address_customer);
        save = findViewById(R.id.tv_save_customer);
        cancel = findViewById(R.id.tv_cancel_customer); }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(db != null) db.close(); }

}