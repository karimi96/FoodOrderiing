package com.example.foodorderiing.activity.orderDetail;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.foodorderiing.R;
import com.example.foodorderiing.adapter.OrdringDetailAdapter;
import com.example.foodorderiing.database.DatabaseHelper;
import com.example.foodorderiing.database.dao.OrderDao;
import com.example.foodorderiing.database.dao.OrderDetailDao;

public class OrderDetaill extends AppCompatActivity {

    private RecyclerView recycler;
    private DatabaseHelper db ;
    private OrderDetailDao dao_orderDetail;
    private OrderDao dao_order;
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
        dao_orderDetail = db.orderDetailDao();
        dao_order = db.orderDao();
    }

    private void initID(){
        recycler = findViewById(R.id.recycler_detail);

    }

    private void initRecycler(){
        recycler.setHasFixedSize(true);
//        String code = dao_order.getOrderList().
        adapter = new OrdringDetailAdapter(dao_orderDetail.getSpecificOrder("1") , this );
        recycler.setAdapter(adapter);
    }


}