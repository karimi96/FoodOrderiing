package com.example.foodorderiing.activity.listOrder;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodorderiing.R;
import com.example.foodorderiing.adapter.ListOrderAdapter;
import com.example.foodorderiing.database.DatabaseHelper;
import com.example.foodorderiing.database.dao.CustomerDao;
import com.example.foodorderiing.database.dao.OrderDao;
import com.example.foodorderiing.helper.App;
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
    }


    @Override
    protected void onResume() {
        super.onResume();
        List<Order> listorder = new ArrayList<>();
        if(listorder.size() == 0 || listorder.isEmpty())
            noListOrder.setVisibility(View.VISIBLE);
        else listorder.addAll(dao.getOrderList());
        noListOrder.setVisibility(View.GONE);
    }

    private void initDataBase(){
        db = App.getDatabase();
        dao = db.orderDao() ;
        customerDao= db.customerDao();
    }


    private void initID (){
        recyclerView = findViewById(R.id.recycler_recordOrdring);
        noListOrder = findViewById(R.id.noListOrder);
    }


    private void initRecycler(){
        recyclerView.setHasFixedSize(true);
        adapter = new ListOrderAdapter(this, dao.getOrderListByDate());
        recyclerView.setAdapter(adapter);
    }
}