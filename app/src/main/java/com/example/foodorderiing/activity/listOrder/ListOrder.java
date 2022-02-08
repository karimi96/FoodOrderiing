package com.example.foodorderiing.activity.listOrder;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodorderiing.R;
import com.example.foodorderiing.adapter.ListOrderAdapter;
import com.example.foodorderiing.database.DatabaseHelper;
import com.example.foodorderiing.database.dao.CustomerDao;
import com.example.foodorderiing.database.dao.OrderDao;
import com.example.foodorderiing.helper.App;
import com.example.foodorderiing.model.Order;

import java.util.ArrayList;
import java.util.List;


public class ListOrder extends AppCompatActivity {
    private RecyclerView recyclerView ;
    private ListOrderAdapter adapter ;
    private DatabaseHelper db ;
    private OrderDao dao ;
    private CustomerDao customerDao;
    private TextView noListOrder;
    private Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_order);

        initDataBase();
        initID();
        set_toolBar();
        initRecycler();
    }


    @Override
    protected void onResume() {
        super.onResume();
        List<Order> listorder = new ArrayList<>();
        if(listorder.size() == 0 || listorder.isEmpty())
            noListOrder.setVisibility(View.VISIBLE);
        else listorder.addAll(dao.getOrderList());
        noListOrder.setVisibility(View.GONE);
    }

    private void initDataBase(){
        db = App.getDatabase();
        dao = db.orderDao() ;
        customerDao= db.customerDao();
    }


    public void set_toolBar() {
        toolbar = findViewById(R.id.toolbar_listOrder);
        toolbar.setTitle("");
        toolbar.setTitleTextColor(getResources().getColor(R.color.white_text));
        setSupportActionBar(toolbar);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_listorder, menu);
        MenuItem item = menu.findItem(R.id.searchListOrder);
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



    private void initID (){
        recyclerView = findViewById(R.id.recycler_recordOrdring);
        noListOrder = findViewById(R.id.noListOrder);
        toolbar = findViewById(R.id.toolbar_listOrder);
    }


    private void initRecycler(){
        recyclerView.setHasFixedSize(true);
        adapter = new ListOrderAdapter(this, dao.getOrderListByDate());
        recyclerView.setAdapter(adapter);
    }
}