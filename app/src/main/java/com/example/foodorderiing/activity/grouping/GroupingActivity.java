package com.example.foodorderiing.activity.grouping;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.foodorderiing.R;
import com.example.foodorderiing.adapter.GroupingAdapter;
import com.example.foodorderiing.database.DatabaseHelper;
import com.example.foodorderiing.database.dao.GroupingDao;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.r0adkll.slidr.Slidr;
import com.r0adkll.slidr.model.SlidrInterface;

import java.util.ArrayList;


public class GroupingActivity extends AppCompatActivity {
    private FloatingActionButton fab_grouping;
    private RecyclerView recyclerView_grouping;
    private GroupingAdapter groupingAdapter;
    private DatabaseHelper db;
    private GroupingDao dao_group;
    private SlidrInterface slidr ;
    private TextView noGrouping ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grouping);

        slidr = Slidr.attach(this);
        initID();
        set_recyclerView();
        set_fab();
        hide_fab();
    }

    private void initID(){
        recyclerView_grouping = findViewById(R.id.recycler_grouping);
        fab_grouping = findViewById(R.id.floating_add_grouping);
        noGrouping = findViewById(R.id.noGrouping);
    }


    public void set_recyclerView(){
        db = DatabaseHelper.getInstance(getApplicationContext());
        dao_group = db.groupingDao();
        recyclerView_grouping.setHasFixedSize(true);
        groupingAdapter = new GroupingAdapter(new ArrayList<>(),this);
        recyclerView_grouping.setAdapter(groupingAdapter);
    }


    public void set_fab(){
        fab_grouping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GroupingActivity.this,AddNewGroupingActivity.class);
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in , android.R.anim.fade_out);
            }
        });
    }


    public void hide_fab(){
        recyclerView_grouping.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if(dy >0 ){
                    fab_grouping.hide();
                }else {
                    fab_grouping.show();
                }
                super.onScrolled(recyclerView, dx, dy);
            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (db != null) db.close();
    }


    @Override
    protected void onResume() {
        super.onResume();
        if( groupingAdapter!= null){
            groupingAdapter.addList(dao_group.getGroupingList());
        }
        if(dao_group.getGroupingList().size() != 0 ){
            noGrouping.setVisibility(View.GONE);
            recyclerView_grouping.setVisibility(View.VISIBLE);
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