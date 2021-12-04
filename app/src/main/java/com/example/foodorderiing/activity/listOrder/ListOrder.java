package com.example.foodorderiing.activity.listOrder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.foodorderiing.R;
import com.example.foodorderiing.adapter.ListOrderAdapter;
import com.example.foodorderiing.database.DatabaseHelper;
import com.example.foodorderiing.database.dao.OrderDao;

public class ListOrder extends AppCompatActivity {
    private RecyclerView recyclerView ;
    private ListOrderAdapter adapter ;
    private DatabaseHelper db ;
    private OrderDao dao ;
    private TextView noListOrder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_order);

        initDataBase();
        initID();
        initRecycler();
    }

    private void initDataBase(){
        db = DatabaseHelper.getInstance(getApplicationContext());
        dao = db.orderDao() ;
    }

    private void initID (){
        recyclerView = findViewById(R.id.recycler_recordOrdring);
        noListOrder = findViewById(R.id.noListOrder);
    }

    private void initRecycler(){
        recyclerView.setHasFixedSize(true);
        adapter = new ListOrderAdapter(this, dao.getOrderList());
        recyclerView.setAdapter(adapter);

    }

    @Override
    protected void onResume() {
        super.onResume();
        if(dao.getOrderList().size() != 0){
            noListOrder.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }
    }
}