package com.example.foodorderiing.activity.listOrder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.foodorderiing.R;
import com.example.foodorderiing.adapter.ListOrderAdapter;
import com.example.foodorderiing.database.DatabaseHelper;
import com.example.foodorderiing.database.dao.OrderDao;

public class ListOrder extends AppCompatActivity {
    RecyclerView recyclerView ;
    ListOrder recordOrdring ;
    ListOrderAdapter adapter ;

    DatabaseHelper db ;
    OrderDao dao ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_order);

        initDataBase();
        initID();
        initRecycler();
    }

    private void initDataBase(){
        db = DatabaseHelper.getInstance(getApplicationContext());
        dao = db.orderDao() ;
    }

    private void initID (){
        recyclerView = findViewById(R.id.recycler_recordOrdring);
    }

    private void initRecycler(){
        recyclerView.setHasFixedSize(true);
        adapter = new ListOrderAdapter(this, dao.getOrderList());
        recyclerView.setAdapter(adapter);
//        recyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
//            @Override
//            public boolean onInterceptTouchEvent( RecyclerView rv,  MotionEvent e) {
//                Intent intent = new Intent(RecordOrdring.this , OrderDetail.class);
//                startActivity(intent);
//                return false;
//            }
//
//            @Override
//            public void onTouchEvent( RecyclerView rv, MotionEvent e) {
//
//            }
//
//            @Override
//            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
//
//            }
//        });



    }



}