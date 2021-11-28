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
import com.example.foodorderiing.database.dao.RecordOrderDao;
import com.example.foodorderiing.database.dao.OrderDetailDao;
import com.example.foodorderiing.database.dao.ProductDao;
import com.example.foodorderiing.helper.Tools;
import com.example.foodorderiing.model.Customer;
import com.example.foodorderiing.model.Order;
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

    ProductDao dao_product;
    RecordOrderDao dao_order;
    OrderDetailDao dao_detail ;

    TextView record_order,add_order;
    View box_customer;
    private SlidrInterface slidr;
    TextView name_customer;
    LottieAnimationView lottie;
    OrderDetail orderDetail ;
    OrderDetailDao orderDetailDao;
    List<Product> list1 ;
    TextView number_order;
    TextView total ;
    TextView save_order ;

    Customer customer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ordering);

        slidr = Slidr.attach(this);
        db = DatabaseHelper.getInstance(getApplicationContext());
        dao_product = db.productDao();
        dao_order = db.orderDao();
        dao_detail = db.orderDetailDao();

        initID();
        initBoxCustomer();
        initBoxProduct();
        initLottie();

        list1 = new ArrayList<>();
        ordringAdapter = new OrdringAdapter(list1, this, new OrdringAdapter.Listener() {
            @Override
            public void onAdded(int pos) {
                list1.get(pos).amount = list1.get(pos).amount + 1;
                ordringAdapter.notifyItemChanged(pos);
                initCounter();
            }

            @Override
            public void onRemove(int pos) {
                if (list1.get(pos).amount > 0){
                    list1.get(pos).amount = list1.get(pos).amount - 1;
                    ordringAdapter.notifyItemChanged(pos);
                }else {
                    list1.remove(pos);
                    ordringAdapter.notifyDataSetChanged();
                }
                initCounter();
            }
        });
        recyclerView.setAdapter(ordringAdapter);
        recyclerView.setHasFixedSize(true);

        initSaveOrder();


    }

    @Override
    protected void onActivityResult( int requestCode, int resultCode , @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK){
            switch (requestCode){

                case 100:
                    String json_customer = data.getExtras().getString("json_customer");
                    customer = new Gson().fromJson(json_customer,Customer.class);
                    name_customer.setText(customer.name);
                    break;

                case 200:
                    String json_product = data.getExtras().getString("json_product");

                    Product product = new Gson().fromJson(json_product,Product.class);

                    list1.add(product);
                    ordringAdapter.notifyDataSetChanged();
                    initCounter();
                    break;
            }
        }
    }

    private void initID(){
        add_order = findViewById(R.id.add_order);
        box_customer = findViewById(R.id.box_customer);
        name_customer = findViewById(R.id.name);
        lottie = findViewById(R.id.lottie);
        total = findViewById(R.id.tv_total);
        save_order = findViewById(R.id.save_order);
        number_order = findViewById(R.id.text_number_of_order);
        recyclerView = findViewById(R.id.recycler_ordering);
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

    private void initCounter(){
        number_order.setText(list1.size()+"");
        total.setText(getTotalPrice()+"");
    }

    private Integer getTotalPrice(){
        int p = 0;
        for (int i = 0; i < list1.size(); i++) {
            p = p + (Tools.convertToPrice(list1.get(i).price) * list1.get(i).amount);
        }
        return p;
    }

    private void initLottie(){
        lottie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lottie.setRepeatCount(0);
                lottie.playAnimation();
                Toast.makeText(OrderingActivity.this, " welcome ", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initSaveOrder(){
        save_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dao_order.insertOrder(new Order(customer.name , "1" , customer.customer_id , 1 , total.getText()+"" , "با تمام مخلفات " ));

                for (int i = 0; i < list1.size(); i++) {
                    dao_detail.insertOrderDetail(new OrderDetail(list1.get(i).name , list1.get(i).category , list1.get(i).price ,
                                                Tools.convertToPrice(number_order.getText().toString()) , dao_order.getOrderList().get(i).code ));

                    Toast.makeText(OrderingActivity.this, "save data", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
    }

}