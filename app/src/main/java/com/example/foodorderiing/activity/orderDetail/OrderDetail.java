package com.example.foodorderiing.activity.orderDetail;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.foodorderiing.R;
import com.example.foodorderiing.adapter.OrderDetailAdapter;
import com.example.foodorderiing.database.DatabaseHelper;
import com.example.foodorderiing.database.dao.CustomerDao;
import com.example.foodorderiing.database.dao.OrderDao;
import com.example.foodorderiing.database.dao.OrderDetailDao;
import com.example.foodorderiing.model.Customer;

public class OrderDetail extends AppCompatActivity {

    private RecyclerView recycler;
    private DatabaseHelper db ;
    private OrderDetailDao dao_orderDetail;
    private OrderDao dao_order;
    private CustomerDao dao_customer;
    private OrderDetailAdapter adapter ;
    private String code , customerName , total_detail;
    private int customerID;
    private TextView name , phone , total;
    private ImageView back;
    private Customer customer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);


        if(getIntent().getExtras() != null){
            code = getIntent().getStringExtra("code");
            customerID = getIntent().getIntExtra("customerid" ,0 );
            customerName = getIntent().getStringExtra("name");
            total_detail = getIntent().getStringExtra("total");
        }


        initDataBase();
        initID();
        initRecycler();
        setBoxCustomer();
        setTotal();

    }

    private void initDataBase(){
        db = DatabaseHelper.getInstance(getApplicationContext());
        dao_orderDetail = db.orderDetailDao();
        dao_order = db.orderDao();
        dao_customer = db.customerDao();
    }

    private void initID(){
        recycler = findViewById(R.id.recycler_detail);
        name = findViewById(R.id.customer_detail);
        phone = findViewById(R.id.phone_detail);
        back = findViewById(R.id.back_detail);
        total = findViewById(R.id.total_detail);
    }

    private void initRecycler(){
        recycler.setHasFixedSize(true);
        adapter = new OrderDetailAdapter( dao_orderDetail.getSpecificOrder(code), this );
        recycler.setAdapter(adapter);

    }

    private void setBoxCustomer(){
        name.setText(customerName);
        phone.setText(dao_customer.getID(customerID).phone);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void setTotal(){
        total.setText(total_detail);

    }

}