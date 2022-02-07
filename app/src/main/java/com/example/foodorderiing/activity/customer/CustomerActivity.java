package com.example.foodorderiing.activity.customer;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodorderiing.R;
import com.example.foodorderiing.adapter.CustomerAdapter;
import com.example.foodorderiing.database.DatabaseHelper;
import com.example.foodorderiing.database.dao.CustomerDao;
import com.example.foodorderiing.design.RecyclerTouchListener;
import com.example.foodorderiing.helper.App;
import com.example.foodorderiing.helper.Tools;
import com.example.foodorderiing.model.Customer;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

import java.util.ArrayList;


public class CustomerActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private FloatingActionButton fab, fab_gotoEnd;
    private CustomerAdapter adapter;
    private DatabaseHelper db;
    private CustomerDao dao;
    private boolean for_order = false;
    private TextView noCustomer;
    private RecyclerTouchListener touchListener;
    private Customer customerr;
    private int poss;
    private static final int CALL_PERMISSION_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);

        if (getIntent() != null) for_order = getIntent().getBooleanExtra("for_order", false);

        set_toolBar();
        initID();
        set_fab();
        set_fab_gotoEnd();
        hide_fab();
        show_fab_gotoEnd();
        initDataBase();
        set_recyclerView();
        setSwipe();
        setReverseRecycler();
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (adapter != null) {
            if (dao.getCustomerList().size() != 0) {
                noCustomer.setVisibility(View.GONE);
                adapter.addList(dao.getCustomerList());
            } else noCustomer.setVisibility(View.VISIBLE);
        }
    }

    private void initDataBase() {
        db = App.getDatabase();
        dao = db.customerDao();
    }


    private void initID() {
        recyclerView = findViewById(R.id.recycler_customer);
        fab = findViewById(R.id.floating_customer);
        noCustomer = findViewById(R.id.noCustomer);
        fab_gotoEnd = findViewById(R.id.fab_go_to_end);
    }


    public void set_toolBar() {
        toolbar = findViewById(R.id.toolbar_customer);
        toolbar.setTitle("");
        toolbar.setTitleTextColor(getResources().getColor(R.color.white_text));
        setSupportActionBar(toolbar);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_customer, menu);
        MenuItem item = menu.findItem(R.id.searchCustomer);
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setBackground(getResources().getDrawable(R.drawable.ripple_all));

        TextView searchText = (TextView) searchView.findViewById(R.id.search_src_text);
        Typeface myCustomFont = Typeface.createFromAsset(getAssets(), "font/iran_sans.ttf");
        searchText.setTypeface(myCustomFont);
        searchText.setTextSize(14);

        // for underline
        View v = searchView.findViewById(androidx.appcompat.R.id.search_plate);
        v.setBackgroundColor(Color.parseColor("#ef4224"));

        // for remove icon hint
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
                adapter.getFilter().filter(newText);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }


    private void set_recyclerView() {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new CustomerAdapter(new ArrayList<>(), this, (customer, pos, name) -> {
            customerr = customer;
            poss = pos;
            if (for_order) {
                Intent returnIntent = new Intent();
                returnIntent.putExtra("json_customer", new Gson().toJson(customer));
                setResult(Activity.RESULT_OK, returnIntent);
                finish();
            } else adapter.showButtonSheet(pos, name, customer.customer_id);
        });
        recyclerView.setAdapter(adapter);
    }


    private void hide_fab() {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0) fab.hide();
                else fab.show();
                super.onScrolled(recyclerView, dx, dy);
            }
        });
    }


    private void show_fab_gotoEnd() {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0) fab_gotoEnd.show();
                else fab_gotoEnd.hide();
                super.onScrolled(recyclerView, dx, dy);
            }
        });
    }


    private void set_fab() {
        fab.setOnClickListener(v -> {
            startActivity(new Intent(CustomerActivity.this, AddNewCustomerActivity.class));
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        });
    }


    private void set_fab_gotoEnd() {
        fab_gotoEnd.setOnClickListener(v -> {
            final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
//            linearLayoutManager.setStackFromEnd(true);
            linearLayoutManager.setReverseLayout(true);
            recyclerView.setLayoutManager(linearLayoutManager);
        });
    }


    private void setReverseRecycler() {
        Tools.setReverseRecycler(this, recyclerView);
    }


    private void layoutAnimation(RecyclerView recyclerView) {
      Tools.layoutAnimationRecycler(recyclerView);
    }


    private void setSwipe() {
        touchListener = new RecyclerTouchListener(this, recyclerView);
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
                .setSwipeable(R.id.card_FG, R.id.linear_BG, (viewID, position) -> {
                    switch (viewID) {
                        case R.id.lottie_phone:
                            if (checkCallPermition()) {
                                String phonnumber = dao.getCustomerList().get(position).phone;
                                Intent call = new Intent(Intent.ACTION_VIEW);
                                call.setData(Uri.parse("tel:" + phonnumber));
                                startActivity(call);
                            }
                            break;
                    }
                });
        recyclerView.addOnItemTouchListener(touchListener);
    }


    public Boolean checkCallPermition() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(CustomerActivity.this, new String[]{Manifest.permission.CALL_PHONE}, 100);
        } else {
            return true;
        }
        return false;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                   Toast.makeText(getApplicationContext(), "Storage Permission Granted", Toast.LENGTH_SHORT).show();
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage(" دسترسی به مجوزها ");
                builder.setPositiveButton("برو به تنظیمات", (dialog, which) -> {
                    Toast.makeText(getApplicationContext(), "dd", Toast.LENGTH_SHORT).show();
                    dialog.cancel();
                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    Uri uri = Uri.fromParts("package", getPackageName(), null);
                    intent.setData(uri);
                    startActivityForResult(intent, 100);

                });
                builder.setNegativeButton("بستن", (dialog, which) -> {
                    dialog.cancel();
//                        finish();
                });
                builder.show();

            }
        }
    }


}