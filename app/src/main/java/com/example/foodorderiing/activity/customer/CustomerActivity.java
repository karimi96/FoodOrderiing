package com.example.foodorderiing.activity.customer;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.foodorderiing.R;
import com.example.foodorderiing.adapter.CustomerAdapter;
import com.example.foodorderiing.database.DatabaseHelper;
import com.example.foodorderiing.database.dao.CustomerDao;
import com.example.foodorderiing.design.RecyclerTouchListener;
import com.example.foodorderiing.model.Customer;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

import java.util.ArrayList;


public class CustomerActivity extends AppCompatActivity {
    private RecyclerView recyclerView ;
    private FloatingActionButton fab;
    private CustomerAdapter adapter;
    private DatabaseHelper db;
    private CustomerDao dao;
    private boolean for_order = false;
    private TextView noCustomer ;
    private RecyclerTouchListener touchListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);

        if (getIntent() != null){
            for_order = getIntent().getBooleanExtra("for_order",false);
        }

        initID();
        set_fab();
        hide_fab();
        initDataBase();
        set_recyclerView();
        setSwipe();
        setText();
    }


    @Override
    protected void onResume() {
        super.onResume();
        setText();
        if( adapter!= null){
            adapter.addList(dao.getCustomerList());
        }

    }


    private void setText(){
        if(dao.getCustomerList().size() < 1 ){
            noCustomer.setVisibility(View.VISIBLE);
//            recyclerView_product.setVisibility(View.GONE);
        }else {
            noCustomer.setVisibility(View.GONE);
        }
    }


    private void initDataBase(){
        db = DatabaseHelper.getInstance(getApplicationContext());
        dao = db.customerDao();
    }


    private void set_recyclerView(){
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
            adapter = new CustomerAdapter(new ArrayList<>(), this, new CustomerAdapter.Listener() {
                @Override
                public void onClickListener(Customer customer , int pos , String name) {
                    if(for_order){
                        Intent returnIntent = new Intent();
                        returnIntent.putExtra("json_customer", new Gson().toJson(customer));
                        setResult(Activity.RESULT_OK, returnIntent);
                        finish();
                    }else {
                        adapter.showDialogSheet(  pos , name );
                    }
                }
            });
            recyclerView.setAdapter(adapter);

        }


    private void initID(){
        recyclerView = findViewById(R.id.recycler_customer);
        fab = findViewById(R.id.floating_customer);
        noCustomer = findViewById(R.id.noCustomer);
    }


    private void hide_fab(){
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


    private void set_fab(){
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
        if(db != null) db.close(); }


    private void layoutAnimation(RecyclerView recyclerView){
        Context context = recyclerView.getContext();
        LayoutAnimationController layoutAnimationController = AnimationUtils.loadLayoutAnimation(context,R.anim.layout_fall_down);
        recyclerView.setLayoutAnimation(layoutAnimationController);
        recyclerView.getAdapter().notifyDataSetChanged();
        recyclerView.scheduleLayoutAnimation(); }


        private void setSwipe(){

            touchListener = new RecyclerTouchListener(this,recyclerView);
            touchListener
//                    .setClickable(new RecyclerTouchListener.OnRowClickListener() {
//                        @Override
//                        public void onRowClicked(int position) {
////                            Toast.makeText(getApplicationContext(),taskList.get(position).getName(), Toast.LENGTH_SHORT).show();
//                            Toast.makeText(getApplicationContext(),"hello", Toast.LENGTH_SHORT).show();
//                        }
//
//                        @Override
//                        public void onIndependentViewClicked(int independentViewID, int position) {
//
//                        }
//                    })
                    .setSwipeOptionViews(R.id.lottie_phone)
                    .setSwipeable(R.id.card_FG, R.id.linear_BG, new RecyclerTouchListener.OnSwipeOptionsClickListener() {
                        @Override
                        public void onSwipeOptionClicked(int viewID, int position) {

                            switch (viewID){
                                case R.id.lottie_phone:
                                    String phonnumber = "0000000000";
                                    Intent call = new Intent(Intent.ACTION_DIAL);
                                    call.setData(Uri.parse("tel:" + phonnumber));
                                    startActivity(call);
                                    break;


                            }
                        }
                    });
            recyclerView.addOnItemTouchListener(touchListener);

        }



}