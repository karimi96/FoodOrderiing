package com.example.foodorderiing.activity.customer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodorderiing.R;
import com.example.foodorderiing.adapter.CustomerAdapter;
import com.example.foodorderiing.database.DatabaseHelper;
import com.example.foodorderiing.database.dao.CustomerDao;
import com.example.foodorderiing.model.Customer;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.r0adkll.slidr.Slidr;
import com.r0adkll.slidr.model.SlidrInterface;

import java.util.ArrayList;
import java.util.List;

public class CustomerActivity extends AppCompatActivity {

    RecyclerView recyclerView ;
    FloatingActionButton fab;
    CustomerAdapter adapter;
    DatabaseHelper db;
    CustomerDao dao;
    private SlidrInterface slidr ;

    private boolean for_order = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);

        slidr = Slidr.attach(this);
        if (getIntent() != null){
            for_order = getIntent().getBooleanExtra("for_order",false);
        }

        init();
        set_fab();
        hide_fab();

        db = DatabaseHelper.getInstance(getApplicationContext());
        dao = db.customerDao();
        set_recyclerView();

    }

    @Override
    protected void onResume() {
        super.onResume();
        if( adapter!= null){
            adapter.addList(dao.getCustomerList());
        }
    }


    public void set_recyclerView(){
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new CustomerAdapter(new ArrayList<>(), this, new CustomerAdapter.Listener() {
            @Override
            public void onClickListener(Customer customer , List<Customer> list , int pos) {
                if(for_order){
                    Intent returnIntent = new Intent();
                    returnIntent.putExtra("json_customer", new Gson().toJson(customer));
                    setResult(Activity.RESULT_OK, returnIntent);
                    finish();
                }else {
//                    Toast.makeText(CustomerActivity.this, "dddd", Toast.LENGTH_SHORT).show();
                    showDialogSheet( pos , list.get(pos).name , list , customer);
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
        if(db != null) db.close(); }


    private void layoutAnimation(RecyclerView recyclerView){
        Context context = recyclerView.getContext();
        LayoutAnimationController layoutAnimationController = AnimationUtils.loadLayoutAnimation(context,R.anim.layout_fall_down);

        recyclerView.setLayoutAnimation(layoutAnimationController);
        recyclerView.getAdapter().notifyDataSetChanged();
        recyclerView.scheduleLayoutAnimation(); }




    private void showDialogSheet(int pos , String name , List<Customer> list , Customer customer){
        final Dialog dialog_sheet = new Dialog(this);
        dialog_sheet.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog_sheet.setContentView(R.layout.bottom_sheet_customer);

        LinearLayout edit = dialog_sheet.findViewById(R.id.linear_edit_c);
        LinearLayout delete = dialog_sheet.findViewById(R.id.linear_delete_c);
        TextView title = dialog_sheet.findViewById(R.id.name_sheet_c);
        title.setText(name);

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AddNewCustomerActivity.class);
                intent.putExtra("customer",new Gson().toJson(list.get(pos)));
                startActivity(intent);
                dialog_sheet.dismiss();
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//
                new AlertDialog.Builder(getApplicationContext() , R.font.iran_sans )
                        .setTitle("حذف")
                        .setMessage("ایا مایلید این مورد را حذف کنید؟")
                        .setPositiveButton("بله", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

//                                db = DatabaseHelper.getInstance(getApplicationContext());
//                                dao = db.customerDao();
                                dao.deleteCustomer(customer);
                                list.remove(pos);
                                adapter.notifyItemRemoved(pos);
                                adapter.notifyItemRangeChanged(pos,list.size());
                                adapter.notifyDataSetChanged();
                                dialog_sheet.dismiss();
                                Toast.makeText(getApplicationContext() , "با موفقیت حذف شد 😉 ", Toast.LENGTH_LONG).show();

                            }
                        })
                        .setNegativeButton("خیر", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                dialog_sheet.dismiss();
                            }
                        })
                        .create()
                        .show();
            }
        });

        dialog_sheet.show();
        dialog_sheet.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog_sheet.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog_sheet.getWindow().getAttributes().windowAnimations = R.style.DialogAnimationSheet;
        dialog_sheet.getWindow().setGravity(Gravity.BOTTOM);

    }


}