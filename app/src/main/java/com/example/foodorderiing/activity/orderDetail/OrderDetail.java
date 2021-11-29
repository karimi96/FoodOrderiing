package com.example.foodorderiing.activity.orderDetail;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.foodorderiing.R;
import com.example.foodorderiing.adapter.OrderDetailAdapter;
import com.example.foodorderiing.database.DatabaseHelper;
import com.example.foodorderiing.database.dao.OrderDao;
import com.example.foodorderiing.database.dao.OrderDetailDao;

public class OrderDetail extends AppCompatActivity {

    private RecyclerView recycler;
    private DatabaseHelper db ;
    private OrderDetailDao dao_orderDetail;
    private OrderDao dao_order;
    private OrderDetailAdapter adapter ;
    private String code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);


        if(getIntent().getExtras() != null){
            code = getIntent().getStringExtra("code");
        }


        initDataBase();
        initID();
        initRecycler();

    }

    private void initDataBase(){
        db = DatabaseHelper.getInstance(getApplicationContext());
        dao_orderDetail = db.orderDetailDao();
        dao_order = db.orderDao();
    }

    private void initID(){
        recycler = findViewById(R.id.recycler_detail);

    }

    private void initRecycler(){
        recycler.setHasFixedSize(true);
        adapter = new OrderDetailAdapter( dao_orderDetail.getSpecificOrder(code), this );
        recycler.setAdapter(adapter);
    }


}