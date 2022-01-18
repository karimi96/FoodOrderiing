package com.example.foodorderiing.activity.listOrder;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.foodorderiing.R;
import com.example.foodorderiing.adapter.ListOrderAdapter;
import com.example.foodorderiing.database.DatabaseHelper;
import com.example.foodorderiing.database.dao.CustomerDao;
import com.example.foodorderiing.database.dao.OrderDao;
import com.example.foodorderiing.model.Order;

import java.util.ArrayList;
import java.util.List;


public class ListOrder extends AppCompatActivity {
    private RecyclerView recyclerView ;
    private ListOrderAdapter adapter ;
    private DatabaseHelper db ;
    private OrderDao dao ;
    private CustomerDao customerDao;
    private TextView noListOrder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_order);

        initDataBase();
        initID();
        initRecycler();
        setReverseRecycler();
    }


    @Override
    protected void onResume() {
        super.onResume();
        List<Order> listorder = new ArrayList<>();
        listorder.addAll(dao.getOrderList());
        if(listorder.size() <= 0 || listorder.isEmpty()){
            noListOrder.setVisibility(View.VISIBLE);
        }
    }

    private void initDataBase(){
        db = DatabaseHelper.getInstance(getApplicationContext());
        dao = db.orderDao() ;
        customerDao= db.customerDao();
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

    private void setReverseRecycler(){
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);
        linearLayoutManager.setReverseLayout(true);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

}