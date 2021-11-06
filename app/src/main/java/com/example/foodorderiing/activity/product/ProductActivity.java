package com.example.foodorderiing.activity.product;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import com.example.foodorderiing.R;
import com.example.foodorderiing.adapter.ProductAdapter;
import com.example.foodorderiing.database.DatabaseHelper;
import com.example.foodorderiing.database.dao.ProductDao;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;


public class ProductActivity extends AppCompatActivity {

    Toolbar toolbar;
    RecyclerView recyclerView;
    FloatingActionButton fab;
    ProductAdapter adapter;
    DatabaseHelper db;
    ProductDao dao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

       set_toolBar();
       set_recyclerView();

        db = DatabaseHelper.getInstance(getApplicationContext());
        dao = db.productDao();
        click_fab();
        hide_fab();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search , menu);
        MenuItem item = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setMaxWidth(Integer.MAX_VALUE);
//        searchView.setQueryHint("جستوجو کنید...");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                    adapter.getFilter().filter(newText);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    protected void onResume() {
        super.onResume();
        if(adapter != null){
            adapter.addList(dao.getProductList());

        }
    }

    public void set_toolBar(){
        toolbar = findViewById(R.id.toolbar_search);
        toolbar.setTitle("محصولات");
        toolbar.setTitleTextColor(getResources().getColor(R.color.white_text));
        setSupportActionBar(toolbar);
    }

    public void set_recyclerView(){
        recyclerView = findViewById(R.id.recycler_product);
        recyclerView.setHasFixedSize(true);
        adapter = new ProductAdapter(new ArrayList<>(), this );
        recyclerView.setAdapter(adapter);
//        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, Collections.singletonList(dao.getProductList().get(0).name));
        adapter.notifyDataSetChanged();

    }

    public void click_fab(){
        fab = findViewById(R.id.fab_product);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ProductActivity.this,AddNewProductActivity.class);
                startActivity(intent);
            }
        });

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
}

