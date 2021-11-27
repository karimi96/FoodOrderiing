package com.example.foodorderiing.activity.product;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ArrayAdapter;
import com.example.foodorderiing.R;
import com.example.foodorderiing.adapter.GroupInProductAdapter;
import com.example.foodorderiing.adapter.ProductAdapter;
import com.example.foodorderiing.database.DatabaseHelper;
import com.example.foodorderiing.database.dao.GroupingDao;
import com.example.foodorderiing.database.dao.ProductDao;
import com.example.foodorderiing.model.Product;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.r0adkll.slidr.Slidr;
import com.r0adkll.slidr.model.SlidrInterface;

import java.util.ArrayList;
import java.util.List;


public class ProductActivity extends AppCompatActivity {

    Toolbar toolbar;
    RecyclerView recyclerView_product;
    RecyclerView recyclerView_category;
    FloatingActionButton fab;
    ProductAdapter adapter_pro;
    GroupInProductAdapter adapter_gro;
    DatabaseHelper db;
    ProductDao dao_product;
    GroupingDao dao_grouping;
    GroupInProductAdapter groupInProductAdapter;

    private Boolean for_order = false ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        if(getIntent().getExtras() != null){
            for_order = getIntent().getBooleanExtra("for_order",false);
        }
        db = DatabaseHelper.getInstance(getApplicationContext());
        dao_grouping = db.groupingDao();
        dao_product = db.productDao();

       set_toolBar();
       set_recycler_category();
       set_recycler_product();

        click_fab();
        hide_fab();
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search , menu);
        MenuItem item = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setBackground(getResources().getDrawable(R.drawable.ripple_all));
        searchView.setIconified(false);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                    adapter_pro.getFilter().filter(newText);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    protected void onResume() {
        super.onResume();
        if(adapter_pro != null){
            adapter_pro.addList(dao_product.getProductList());
//            adapter_pro.addList(dao_product.get_product_by_category(groupInProductAdapter.category_grouping));
        }
    }


    public void set_toolBar(){
        toolbar = findViewById(R.id.toolbar_search);
        toolbar.setTitle("");
        toolbar.setTitleTextColor(getResources().getColor(R.color.white_text));
        setSupportActionBar(toolbar); }


    public void set_recycler_category(){
        recyclerView_category = findViewById(R.id.recycler_grouping_product_page);
        recyclerView_category.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        recyclerView_category.setLayoutManager(layoutManager);
        adapter_gro = new GroupInProductAdapter( dao_grouping.getGroupingList(),this );
        recyclerView_category.setAdapter(adapter_gro); }


    public void set_recycler_product(){
        recyclerView_product = findViewById(R.id.recycler_product);
        recyclerView_product.setHasFixedSize(true);
        adapter_pro = new ProductAdapter(new ArrayList<>(), this , new ProductAdapter.Listener() {
            @Override
            public void onClick(Product product , int pos , String name ) {
//                Intent returnIntent = new Intent();
//                returnIntent.putExtra("json_product",new Gson().toJson(product));
//                setResult(Activity.RESULT_OK,returnIntent);
//                finish();


                if(for_order){
                    for_order = getIntent().getBooleanExtra("for_order",false);
                    Intent returnIntent = new Intent();
                    returnIntent.putExtra("json_product", new Gson().toJson(product));
                    setResult(Activity.RESULT_OK, returnIntent);
                    finish();
                }else {
//                    Toast.makeText(CustomerActivity.this, "dddd", Toast.LENGTH_SHORT).show();
//                    showDialogSheet( pos , list.get(pos).name , list , customer);
                    adapter_pro.showDialogSheet(pos , name );
                }

            }
        });
        recyclerView_product.setAdapter(adapter_pro);
//        recyclerView_product.setAdapter(adapter_gro);
        adapter_pro.notifyDataSetChanged();
    }


    public void click_fab(){
        fab = findViewById(R.id.fab_product);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ProductActivity.this,AddNewProductActivity.class);
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in , android.R.anim.fade_out);

            }
        });

    }


    public void hide_fab(){
        recyclerView_product.addOnScrollListener(new RecyclerView.OnScrollListener() {
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


    private void layoutAnimation(RecyclerView recyclerView){
        Context context = recyclerView.getContext();
        LayoutAnimationController layoutAnimationController = AnimationUtils.loadLayoutAnimation(context,R.anim.layout_slide_right);

        recyclerView.setLayoutAnimation(layoutAnimationController);
        recyclerView.getAdapter().notifyDataSetChanged();
        recyclerView.scheduleLayoutAnimation();
    }

}

    // arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, Collections.singletonList(dao.getProductList().get(0).name));
