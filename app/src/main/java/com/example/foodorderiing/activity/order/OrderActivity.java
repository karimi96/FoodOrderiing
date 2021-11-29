package com.example.foodorderiing.activity.order;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.foodorderiing.R;
import com.example.foodorderiing.activity.customer.CustomerActivity;
import com.example.foodorderiing.activity.product.ProductActivity;
import com.example.foodorderiing.adapter.OrderAdapter;
import com.example.foodorderiing.database.DatabaseHelper;
import com.example.foodorderiing.database.dao.OrderDao;
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


public class OrderActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private OrderAdapter ordringAdapter;
    private DatabaseHelper db;
    private ProductDao dao_product;
    private OrderDao dao_order;
    private OrderDetailDao dao_detail ;
    private TextView add_order;
    private View box_customer;
    private SlidrInterface slidr;
    private TextView name_customer;
    private LottieAnimationView lottie;
    private List<Product> orderDetailList;
    private TextView number_order, total, save_order ;
    private Customer customer;
    private CardView card_number;
    private String CODE = String.valueOf(System.currentTimeMillis());


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ordering);

        slidr = Slidr.attach(this);
        initDataBase();
        initID();
        initBoxCustomer();
        initBoxProduct();
        initLottie();
        initRecycler();
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
                    insertToOrderList(product);
                    break;
            }
        }
    }


    private void insertToOrderList(Product product){
        for (int i = 0; i < orderDetailList.size(); i++) {
            if(orderDetailList.get(i).product_id == product.product_id){
                orderDetailList.get(i).amount = orderDetailList.get(i).amount + 1;
                ordringAdapter.notifyDataSetChanged();
                initCounter();
                return;
            }
        }
        orderDetailList.add(product);
        ordringAdapter.notifyDataSetChanged();
        initCounter();
    }


    private void initRecycler(){
        orderDetailList = new ArrayList<>();
        ordringAdapter = new OrderAdapter(orderDetailList, this, new OrderAdapter.Listener() {
            @Override
            public void onAdded(int pos) {
                orderDetailList.get(pos).amount = orderDetailList.get(pos).amount + 1;
                ordringAdapter.notifyItemChanged(pos);
                initCounter();
            }

            @Override
            public void onRemove(int pos) {
                if (orderDetailList.get(pos).amount > 1){
                    orderDetailList.get(pos).amount = orderDetailList.get(pos).amount - 1;
                    ordringAdapter.notifyItemChanged(pos);
                }else {
                    orderDetailList.remove(pos);
                    ordringAdapter.notifyDataSetChanged();

                }
                initCounter();
            }
        });
        recyclerView.setAdapter(ordringAdapter);
        recyclerView.setHasFixedSize(true);
    }

    private void initDataBase(){
        db = DatabaseHelper.getInstance(getApplicationContext());
        dao_product = db.productDao();
        dao_order = db.orderDao();
        dao_detail = db.orderDetailDao();
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
        card_number = findViewById(R.id.card_number);
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
        if(orderDetailList.size() > 0){
            card_number.setVisibility(View.VISIBLE);
            number_order.setText(orderDetailList.size()+"");

        }else {
            card_number.setVisibility(View.GONE);
        }
        total.setText(getTotalPrice()+"");
    }


    private Integer getTotalPrice(){
        int p = 0;
        for (int i = 0; i < orderDetailList.size(); i++) {
            p = p + (Tools.convertToPrice(orderDetailList.get(i).price) * orderDetailList.get(i).amount);
        }
        return p;
    }

    private void initLottie(){
        lottie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lottie.setRepeatCount(0);
                lottie.playAnimation();
                Toast.makeText(OrderActivity.this, " welcome ", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initSaveOrder(){
        save_order.setOnClickListener(view -> {
            dao_order.insertOrder(new Order(customer.name , CODE , customer.customer_id , 1 , total.getText()+"" , "با تمام مخلفات " ));

            for (int i = 0; i < orderDetailList.size(); i++) {
                dao_detail.insertOrderDetail(new OrderDetail(orderDetailList.get(i).name , orderDetailList.get(i).category , orderDetailList.get(i).price ,
                                    orderDetailList.get(i).amount ,CODE ));

                Toast.makeText(OrderActivity.this, "save data", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

}