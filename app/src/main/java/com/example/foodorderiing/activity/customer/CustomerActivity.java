package com.example.foodorderiing.activity.customer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.foodorderiing.R;
import com.example.foodorderiing.activity.grouping.AddNewGroupingActivity;
import com.example.foodorderiing.activity.grouping.GroupingActivity;
import com.example.foodorderiing.adapter.CustomerAdapter;
import com.example.foodorderiing.database.DatabaseHelper;
import com.example.foodorderiing.database.dao.CustomerDao;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class CustomerActivity extends AppCompatActivity {

    RecyclerView recyclerView ;
    FloatingActionButton fab;
    CustomerAdapter adapter;
    DatabaseHelper db;
    CustomerDao dao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);

        init();
        set_fab();
        hide_fab();

        db = DatabaseHelper.getInstance(getApplicationContext());
        dao = db.customerDao();
        set_recyclerView();

    }


    public void set_recyclerView(){
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new CustomerAdapter( new ArrayList<>(), this);
        recyclerView.setAdapter(adapter);
    }


    public void init(){
        recyclerView = findViewById(R.id.recycler_customer);
        fab = findViewById(R.id.floating_customer);
    }


    public void hide_fab(){
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if(dy >0 ){
                    fab.hide();
                }else {
                    fab.show();
                }
                super.onScrolled(recyclerView, dx, dy);
            }
        });
    }


    public void set_fab(){
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CustomerActivity.this, AddNewCustomerActivity.class);
                startActivity(intent);
            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(db != null) db.close();
    }


    @Override
    protected void onResume() {
        super.onResume();
        if( adapter!= null){
            adapter.addList(dao.getCustomerList());
        }
    }

}