package com.example.foodorderiing.activity.grouping;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodorderiing.R;
import com.example.foodorderiing.adapter.GroupingAdapter;
import com.example.foodorderiing.database.DatabaseHelper;
import com.example.foodorderiing.database.dao.GroupingDao;
import com.example.foodorderiing.helper.App;
import com.example.foodorderiing.helper.Tools;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.r0adkll.slidr.Slidr;
import com.r0adkll.slidr.model.SlidrInterface;

import java.util.ArrayList;


public class GroupingActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private FloatingActionButton fab_grouping;
    private RecyclerView recyclerView_grouping;
    private GroupingAdapter groupingAdapter;
    private DatabaseHelper db;
    private GroupingDao dao_group;
    private SlidrInterface slider;
    private TextView noGrouping;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grouping);

        slider = Slidr.attach(this);
        initID();
        set_toolBar();
        set_recyclerView();
        set_fab();
        hide_fab();
        setReverseRecycler();
    }

    private void initID() {
        recyclerView_grouping = findViewById(R.id.recycler_grouping);
        fab_grouping = findViewById(R.id.floating_add_grouping);
        noGrouping = findViewById(R.id.noGrouping);
    }


    public void set_toolBar() {
        toolbar = findViewById(R.id.toolbar_grouping);
        toolbar.setTitle("");
        toolbar.setTitleTextColor(getResources().getColor(R.color.white_text));
        setSupportActionBar(toolbar);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_grouping, menu);
        MenuItem item = menu.findItem(R.id.searchGrouping);
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setBackground(getResources().getDrawable(R.drawable.ripple_all));

        TextView searchText = (TextView) searchView.findViewById(R.id.search_src_text);
        Typeface myCustomFont = Typeface.createFromAsset(getAssets(), "font/iran_sans.ttf");
        searchText.setTypeface(myCustomFont);
        searchText.setTextSize(14);

        View v = searchView.findViewById(androidx.appcompat.R.id.search_plate);
        v.setBackgroundColor(Color.parseColor("#ef4224"));

        EditText searchEdit = ((EditText) searchView.findViewById(androidx.appcompat.R.id.search_src_text));
        searchEdit.setTextColor(getResources().getColor(R.color.white_text));
        searchEdit.setHintTextColor(getResources().getColor(R.color.white_text));
        searchEdit.setHint("جست وجو کنید...");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                groupingAdapter.getFilter().filter(newText);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }


    public void set_recyclerView() {
        db = App.getDatabase();
        dao_group = db.groupingDao();
        recyclerView_grouping.setHasFixedSize(true);
        groupingAdapter = new GroupingAdapter(new ArrayList<>(), this);
        recyclerView_grouping.setAdapter(groupingAdapter);
    }


    private void setReverseRecycler() {
        Tools.setReverseRecycler(this,recyclerView_grouping);
    }


    public void set_fab() {
        fab_grouping.setOnClickListener(v -> {
                startActivity(new Intent(GroupingActivity.this, AddNewGroupingActivity.class));
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        });
    }


    public void hide_fab() {
        recyclerView_grouping.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0) fab_grouping.hide();
                 else fab_grouping.show();
                super.onScrolled(recyclerView, dx, dy);
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (groupingAdapter != null) {
            if (dao_group.getGroupingList().size() != 0 ) {groupingAdapter.addList(dao_group.getGroupingList());
            noGrouping.setVisibility(View.GONE);}
            else noGrouping.setVisibility(View.VISIBLE);
        }
    }


    private void layoutAnimation(RecyclerView recyclerView) {
        Context context = recyclerView.getContext();
        LayoutAnimationController layoutAnimationController = AnimationUtils.loadLayoutAnimation(context, R.anim.layout_fall_down);

        recyclerView.setLayoutAnimation(layoutAnimationController);
        recyclerView.getAdapter().notifyDataSetChanged();
        recyclerView.scheduleLayoutAnimation();
    }
}