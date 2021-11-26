package com.example.foodorderiing.activity.ordering;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.foodorderiing.R;
import com.example.foodorderiing.activity.customer.CustomerActivity;
import com.example.foodorderiing.activity.product.ProductActivity;
import com.example.foodorderiing.adapter.OrdringAdapter;
import com.example.foodorderiing.database.DatabaseHelper;
import com.example.foodorderiing.database.dao.OrderDetailDao;
import com.example.foodorderiing.database.dao.ProductDao;
import com.example.foodorderiing.model.Customer;
import com.example.foodorderiing.model.OrderDetail;
import com.example.foodorderiing.model.Product;
import com.google.gson.Gson;
import com.r0adkll.slidr.Slidr;
import com.r0adkll.slidr.model.SlidrInterface;

import java.util.ArrayList;
import java.util.List;

public class OrderingActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    OrdringAdapter ordringAdapter;
    DatabaseHelper db;
    ProductDao dao;
    TextView record_order,add_order;
    View box_customer;
    private SlidrInterface slidr;
    TextView name_customer;
    LottieAnimationView lottie;
    OrderDetail orderDetail ;
    OrderDetailDao orderDetailDao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ordering);

        slidr = Slidr.attach(this);
        db = DatabaseHelper.getInstance(getApplicationContext());
        dao = db.productDao();

        initID();
        initBoxCustomer();
        initBoxProduct();
        lottie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lottie.setRepeatCount(0);
                lottie.playAnimation();
                Toast.makeText(OrderingActivity.this, " welcome ", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    protected void onActivityResult( int requestCode, int resultCode , @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK){
            switch (requestCode){

                case 100:
                    String json_customer = data.getExtras().getString("json_customer");
                    Customer customer = new Gson().fromJson(json_customer,Customer.class);
                    name_customer.setText(customer.name);
//                    Toast.makeText(this, ""+customer.name, Toast.LENGTH_SHORT).show();
                    break;

                case 200:
                    String json_product = data.getExtras().getString("json_product");
                    Product product = new Gson().fromJson(json_product,Product.class);
                    recyclerView = findViewById(R.id.recycler_ordering);
                    recyclerView.setHasFixedSize(true);
                    orderDetailDao = db.orderDetailDao();
//                  orderDetailDao.insertOrderDetail(new OrderDetail( product.name , product.category , product.price , 8 , "1" ));
                    ordringAdapter = new OrdringAdapter(dao.getProductList(),this);
                    recyclerView.setAdapter(ordringAdapter);
                    break;
            }
        }
    }

    private void initID(){
        add_order = findViewById(R.id.add_order);
        box_customer = findViewById(R.id.box_customer);
        name_customer = findViewById(R.id.name);
        lottie = findViewById(R.id.lottie);
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


    private void setRecycler(){

    }


}