package com.example.foodorderiing.activity.product;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
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
import com.example.foodorderiing.adapter.GroupInProductAdapter;
import com.example.foodorderiing.adapter.ProductAdapter;
import com.example.foodorderiing.database.DatabaseHelper;
import com.example.foodorderiing.database.dao.GroupingDao;
import com.example.foodorderiing.database.dao.ProductDao;
import com.example.foodorderiing.helper.App;
import com.example.foodorderiing.helper.Tools;
import com.example.foodorderiing.model.Grouping;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

import java.util.ArrayList;


public class ProductActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private RecyclerView recyclerView_product;
    private RecyclerView recyclerView_category;
    private FloatingActionButton fab;
    private ProductAdapter adapter_pro = null;
    private GroupInProductAdapter adapter_gro;
    private DatabaseHelper db;
    private ProductDao dao_product;
    private GroupingDao dao_grouping;
    private Boolean for_order = false;
    private TextView noProduct;
    private String nameGrouping;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        if (getIntent().getExtras() != null) {
            for_order = getIntent().getBooleanExtra("for_order", false);

        }
        if (getIntent().getStringExtra("groupingToproduct") != null) {
            String for_grouping = getIntent().getStringExtra("groupingToproduct");
            Grouping grouping = new Gson().fromJson(for_grouping, Grouping.class);
            nameGrouping = grouping.name;
        }

        initDataBase();
        initID();
        set_toolBar();
        set_recycler_category();
        set_recycler_product();
        click_fab();
        hide_fab();
        setReverseRecycler();
    }


    @Override
    protected void onResume() {
        super.onResume();
        initListProduct();
        set_recycler_category();
    }


    private void initID() {
        recyclerView_category = findViewById(R.id.recycler_grouping_product_page);
        recyclerView_product = findViewById(R.id.recycler_product);
        fab = findViewById(R.id.fab_product);
        noProduct = findViewById(R.id.noProduct);
    }


    private void initDataBase() {
        db = App.getDatabase();
        dao_grouping = db.groupingDao();
        dao_product = db.productDao();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_product, menu);
        MenuItem item = menu.findItem(R.id.searchProduct);
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
        searchEdit.setHint("جست و جو کنید...");

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


    public void set_toolBar() {
        toolbar = findViewById(R.id.toolbar_product);
        toolbar.setTitle("");
        toolbar.setTitleTextColor(getResources().getColor(R.color.white_text));
        setSupportActionBar(toolbar);
    }


    public void set_recycler_category() {
        ArrayList<Grouping> groupingArrayList = new ArrayList<>();
        groupingArrayList.add(0, new Grouping("همه محصولات ", ""));
        groupingArrayList.addAll(dao_grouping.getGroupingList());

        recyclerView_category.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView_category.setLayoutManager(layoutManager);
        adapter_gro = new GroupInProductAdapter(groupingArrayList, this, nameGrouping, new GroupInProductAdapter.Listener() {
            @Override
            public void onClick(int pos, Grouping g) {
                Log.e("asd", "product: get position " + pos);
                if (pos == 0) {
                    nameGrouping = null;
                } else {
                    nameGrouping = g.name;
                }
                initListProduct();
            }
        });
        recyclerView_category.setAdapter(adapter_gro);
    }


    private void initListProduct() {
        Log.e("qqqq", "initListProduct: " + nameGrouping);
        if (adapter_pro != null) {
            if (dao_product.getProductList().size() >= 0) {
                recyclerView_category.setVisibility(View.VISIBLE);

                if (nameGrouping == null || nameGrouping.isEmpty())
                    adapter_pro.addList(dao_product.getProductList());
                else {
//                    if(dao_product.get_product_by_category(nameGrouping).size() == 0) noProduct.setVisibility(View.VISIBLE);
                    adapter_pro.addList(dao_product.get_product_by_category(nameGrouping));
                }

            } else {
                recyclerView_category.setVisibility(View.GONE);
                noProduct.setVisibility(View.VISIBLE);
            }
        }
    }


    public void set_recycler_product() {
        recyclerView_product.setHasFixedSize(true);
        adapter_pro = new ProductAdapter(new ArrayList<>(), ProductActivity.this, (product, pos, name) -> {
            if (for_order) {
                for_order = getIntent().getBooleanExtra("for_order", false);
                Intent returnIntent = new Intent();
                returnIntent.putExtra("json_product", new Gson().toJson(product));
                setResult(Activity.RESULT_OK, returnIntent);
                finish();
            } else adapter_pro.showButtonSheet(pos, name);

        });
        recyclerView_product.setAdapter(adapter_pro);
    }


    private void setReverseRecycler() {
        Tools.setReverseRecycler(this, recyclerView_product);
    }


    public void click_fab() {
        fab.setOnClickListener(v -> {
            startActivity(new Intent(ProductActivity.this, AddNewProductActivity.class));
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        });
    }


    public void hide_fab() {
        recyclerView_product.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0) fab.hide();
                else fab.show();
                super.onScrolled(recyclerView, dx, dy);
            }
        });
    }


    private void layoutAnimation(RecyclerView recyclerView) {
        Context context = recyclerView.getContext();
        LayoutAnimationController layoutAnimationController = AnimationUtils.loadLayoutAnimation(context, R.anim.layout_slide_right);
        recyclerView.setLayoutAnimation(layoutAnimationController);
        recyclerView.getAdapter().notifyDataSetChanged();
        recyclerView.scheduleLayoutAnimation();
    }


}