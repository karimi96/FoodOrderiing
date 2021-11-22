package com.example.foodorderiing.activity.ordering;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodorderiing.R;
import com.example.foodorderiing.activity.customer.CustomerActivity;
import com.example.foodorderiing.activity.orderDetail.OrderingDetail;
import com.example.foodorderiing.activity.product.ProductActivity;
import com.example.foodorderiing.adapter.OrdringAdapter;
import com.example.foodorderiing.database.DatabaseHelper;
import com.example.foodorderiing.database.dao.ProductDao;
import com.example.foodorderiing.model.Customer;
import com.example.foodorderiing.model.Product;
import com.google.gson.Gson;

public class OrderingActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    OrdringAdapter ordringAdapter;
    DatabaseHelper db;
    ProductDao dao;
    TextView record_order,add_order;
    View box_customer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ordering);




        db = DatabaseHelper.getInstance(getApplicationContext());
        dao = db.productDao();
        recyclerView = findViewById(R.id.recycler_ordering);
        recyclerView.setHasFixedSize(true);
        ordringAdapter = new OrdringAdapter(dao.getProductList(),this);
        recyclerView.setAdapter(ordringAdapter);

        record_order = findViewById(R.id.record_order);
        record_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OrderingActivity.this, OrderingDetail.class);
                startActivity(intent);
            }
        });

        initID();
        initBoxCustomer();
        initBoxProduct();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK){
            switch (requestCode){
                case 100:
                    String json_customer = data.getExtras().getString("json_customer");
                    Customer customer = new Gson().fromJson(json_customer,Customer.class);
                    Toast.makeText(this, ""+customer.name, Toast.LENGTH_SHORT).show();
                    break;
                case 200:
                    String json_product = data.getExtras().getString("json_product");
                    Product product = new Gson().fromJson(json_product,Product.class);
                    Toast.makeText(this, ""+product.name, Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }

    private void initID(){
        add_order = findViewById(R.id.add_order);
        box_customer = findViewById(R.id.box_customer);
    }


    private void initBoxCustomer(){
        box_customer.setOnClickListener(v ->{
            Intent intent = new Intent(this, CustomerActivity.class);
            intent.putExtra("for_order", true);
           startActivityForResult(intent,100);
        });
    }

    private void initBoxProduct(){
        add_order.setOnClickListener(v ->{
            Intent intent = new Intent(this, ProductActivity.class);
            intent.putExtra("for_order", true);
            startActivityForResult(intent,200);
        });
    }
}