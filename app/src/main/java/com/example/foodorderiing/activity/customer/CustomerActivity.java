package com.example.foodorderiing.activity.customer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

import com.example.foodorderiing.R;
import com.example.foodorderiing.adapter.CustomerAdapter;
import com.example.foodorderiing.database.DatabaseHelper;
import com.example.foodorderiing.database.dao.CustomerDao;
import com.example.foodorderiing.model.Customer;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

import java.util.ArrayList;

public class CustomerActivity extends AppCompatActivity {

    RecyclerView recyclerView ;
    FloatingActionButton fab;
    CustomerAdapter adapter;
    DatabaseHelper db;
    CustomerDao dao;

    private boolean for_order = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);

        for_order = getIntent().getExtras().getBoolean("for_order",false);

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
        adapter = new CustomerAdapter(new ArrayList<>(), this, new CustomerAdapter.Listener() {
            @Override
            public void onClickListener(Customer customer) {
                if (for_order){
                    Intent returnIntent = new Intent();
                    returnIntent.putExtra("json_customer",new Gson().toJson(customer));
                    setResult(Activity.RESULT_OK,returnIntent);
                    finish();
                }
            }
        });
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
                overridePendingTransition(android.R.anim.fade_in , android.R.anim.fade_out);

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

    private void layoutAnimation(RecyclerView recyclerView){
        Context context = recyclerView.getContext();
        LayoutAnimationController layoutAnimationController = AnimationUtils.loadLayoutAnimation(context,R.anim.layout_fall_down);

        recyclerView.setLayoutAnimation(layoutAnimationController);
        recyclerView.getAdapter().notifyDataSetChanged();
        recyclerView.scheduleLayoutAnimation();
    }

}