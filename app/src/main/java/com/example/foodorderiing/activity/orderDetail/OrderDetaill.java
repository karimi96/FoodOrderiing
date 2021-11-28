package com.example.foodorderiing.activity.orderDetail;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.foodorderiing.R;
import com.example.foodorderiing.adapter.OrdringDetailAdapter;
import com.example.foodorderiing.database.DatabaseHelper;
import com.example.foodorderiing.database.dao.OrderDetailDao;

public class OrderDetaill extends AppCompatActivity {

    private RecyclerView recycler;
    private DatabaseHelper db ;
    private OrderDetailDao dao;
    private OrdringDetailAdapter adapter ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);

        initDataBase();
        initID();
        initRecycler();

    }


    private void initDataBase(){
        db = DatabaseHelper.getInstance(getApplicationContext());
        dao = db.orderDetailDao();
    }

    private void initID(){
        recycler = findViewById(R.id.recycler_detail);

    }

    private void initRecycler(){
        recycler.setHasFixedSize(true);
        adapter = new OrdringDetailAdapter( this );
        recycler.setAdapter(adapter);
    }


}