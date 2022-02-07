package com.example.foodorderiing.activity.orderDetail;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodorderiing.R;
import com.example.foodorderiing.adapter.OrderDetailAdapter;
import com.example.foodorderiing.database.DatabaseHelper;
import com.example.foodorderiing.database.dao.CustomerDao;
import com.example.foodorderiing.database.dao.OrderDao;
import com.example.foodorderiing.database.dao.OrderDetailDao;
import com.example.foodorderiing.helper.App;
import com.example.foodorderiing.model.Order;
import com.google.gson.Gson;

public class OrderDetail extends AppCompatActivity {
    private RecyclerView recycler;
    private DatabaseHelper db;
    private OrderDetailDao dao_orderDetail;
    private OrderDao dao_order;
    private CustomerDao dao_customer;
    private OrderDetailAdapter adapter;
    private TextView name, phone, address, total;
    private Order order;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);

        if (getIntent().getExtras() != null) {
            String orderList = getIntent().getStringExtra("listOrder");
            order = new Gson().fromJson(orderList, Order.class);
        }

        initDataBase();
        initID();
        initRecycler();
        setBoxCustomer();
        setTotal();

    }

    private void initDataBase() {
        db = App.getDatabase();
        dao_orderDetail = db.orderDetailDao();
        dao_order = db.orderDao();
        dao_customer = db.customerDao();
    }

    private void initID() {
        recycler = findViewById(R.id.recycler_detail);
        name = findViewById(R.id.name_detail);
        phone = findViewById(R.id.phone_detail);
        address = findViewById(R.id.address_detail);
        total = findViewById(R.id.total_detail);
    }

    private void initRecycler() {
        recycler.setHasFixedSize(true);
        adapter = new OrderDetailAdapter(dao_orderDetail.getSpecificOrder(order.code), this);
        recycler.setAdapter(adapter);
    }

    private void setBoxCustomer() {
        name.setText(order.name);
        phone.setText(dao_customer.getID(order.customerID).phone);
        address.setText(dao_customer.getID(order.customerID).address);
    }

    private void setTotal() {
        total.setText(order.total);
    }

}