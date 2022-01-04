package com.example.foodorderiing.activity.listOrder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

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
    public  List<Order> listOrder;


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
        customerDao= db.customerDao();
    }


    private void initID (){
        recyclerView = findViewById(R.id.recycler_recordOrdring);
        noListOrder = findViewById(R.id.noListOrder);
    }


    private void initRecycler(){
        listOrder = new ArrayList<>();
        recyclerView.setHasFixedSize(true);
        for (int i = 0; i < dao.getOrderList().size(); i++) {
            if(customerDao.getID(dao.getOrderList().get(i).customerID) != null){
                listOrder.add(dao.getOrderList().get(i));
            }
        }
//        order = new Order();
//        order.numOrder = listOrder.size();
        adapter = new ListOrderAdapter(this, listOrder);
        recyclerView.setAdapter(adapter);
    }


    @Override
    protected void onResume() {
        super.onResume();
        if( listOrder.size() > 0){
            noListOrder.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }
    }
}