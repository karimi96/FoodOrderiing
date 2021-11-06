package com.example.foodorderiing.activity.grouping;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import com.example.foodorderiing.R;
import com.example.foodorderiing.adapter.GroupingAdapter;
import com.example.foodorderiing.database.DatabaseHelper;
import com.example.foodorderiing.database.dao.GroupingDao;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;


public class GroupingActivity extends AppCompatActivity {

    FloatingActionButton fab_grouping;
    RecyclerView recyclerView_grouping;
    GroupingAdapter groupingAdapter;
    DatabaseHelper db;
    GroupingDao dao_group;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grouping);

        recyclerView_grouping = findViewById(R.id.recycler_grouping);
        recyclerView_grouping.setHasFixedSize(true);
        db = DatabaseHelper.getInstance(getApplicationContext());
        dao_group = db.groupingDao();


        groupingAdapter = new GroupingAdapter(new ArrayList<>(),this);
        recyclerView_grouping.setAdapter(groupingAdapter);
//
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
////                List<Grouping> g = new ArrayList<>();
//////                g.add(new Grouping("test"));
//////                g.add(new Grouping("test"));
//////                g.add(new Grouping("test"));
//////                g.add(new Grouping("test"));
//////                groupingAdapter.addList(g);
//////                groupingAdapter.addList(db.groupingDao().getGroupingList());
//            }
//        });


        fab_grouping = findViewById(R.id.floating_add_grouping);
        fab_grouping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GroupingActivity.this,AddNewGroupingActivity.class);
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in , android.R.anim.fade_out);

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
    }

}