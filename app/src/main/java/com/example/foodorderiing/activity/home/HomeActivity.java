package com.example.foodorderiing.activity.home;

import android.content.Intent;
import android.graphics.Color;
import android.icu.text.DecimalFormat;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.foodorderiing.R;
import com.example.foodorderiing.activity.customer.CustomerActivity;
import com.example.foodorderiing.activity.grouping.GroupingActivity;
import com.example.foodorderiing.activity.order.OrderActivity;
import com.example.foodorderiing.activity.product.ProductActivity;
import com.example.foodorderiing.activity.listOrder.ListOrder;
import com.example.foodorderiing.database.DatabaseHelper;
import com.example.foodorderiing.database.dao.CustomerDao;
import com.example.foodorderiing.database.dao.GroupingDao;
import com.example.foodorderiing.database.dao.OrderDao;
import com.example.foodorderiing.database.dao.ProductDao;
import com.example.foodorderiing.helper.Tools;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import saman.zamani.persiandate.PersianDate;
import saman.zamani.persiandate.PersianDateFormat;

public class HomeActivity extends AppCompatActivity {
    private CardView cardView_product ,cardView_customer ,cardView_grouping ;
    private TextView title ,num_product ,num_customer ,num_grouping;
    private DatabaseHelper db;
    private ProductDao dao_p;
    private GroupingDao dao_g;
    private CustomerDao dao_c;
    private OrderDao dao_order;
    private ImageView img_ordering;
    private TextView recordOrder ,month ,week ,day ;
    private String daily;


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initDataBase();
        initID();
        toolbar();
        initGetOrder();
        initListOrder();
        gOToProduct();
        goToCustomer();
        goToGrouping();
        setDateMiladi_daily();
        setDateShamsi_daily();
        setDateShamsi_weekly();

    }


    private void initDataBase(){
        db = DatabaseHelper.getInstance(getApplicationContext());
        dao_p = db.productDao();
        dao_c = db.customerDao();
        dao_g = db.groupingDao();
        dao_order = db.orderDao();
    }


    private void initID(){
        cardView_grouping = findViewById(R.id.cardView_grouping);
        cardView_customer = findViewById(R.id.cardview_customer);
        cardView_product = findViewById(R.id.cardView_product);
        num_product = findViewById(R.id.number_of_product);
        num_customer = findViewById(R.id.number_of_customer);
        num_grouping = findViewById(R.id.number_of_grouping);
        recordOrder = findViewById(R.id.text_record_order);
        month = findViewById(R.id.month);
        week = findViewById(R.id.week);
        day = findViewById(R.id.day);
    }


    public void toolbar() {
        title = findViewById(R.id.title_home);
        title.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        title.setSelected(true);
    }


    public void create_chart(int price) {
        BarChart bar_chart = findViewById(R.id.chart_bar);
        ArrayList<BarEntry> visitor = new ArrayList<>();
        for (int i = 0; i < 1; i++) {
            visitor.add(new BarEntry(i, Tools.convertToPrice(String.valueOf(price))));
        }

        BarDataSet barDataSet = new BarDataSet(visitor, "");
        barDataSet.setColors(Color.rgb(241, 92, 65));
        barDataSet.setValueTextSize(0f);
        BarData barData = new BarData(barDataSet);
        bar_chart.setFitBars(true);
        bar_chart.setData(barData);
        bar_chart.getDescription().setText("");
        bar_chart.animateY(1000);
    }


    private void initGetOrder() {
        img_ordering = findViewById(R.id.img_shoping);
        img_ordering.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, OrderActivity.class));
            }
        });
    }


    private void gOToProduct(){
        cardView_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, ProductActivity.class);
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });
    }


    private void goToCustomer(){
        cardView_customer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, CustomerActivity.class);
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });
    }


    private void goToGrouping(){
        cardView_grouping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, GroupingActivity.class);
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });
    }


    private void initListOrder(){
        recordOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, ListOrder.class);
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });
    }


    private void setDateMiladi_daily(){
        Calendar c = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        c.add(Calendar.DAY_OF_WEEK, +10);
        long result = c.getTimeInMillis();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String datetime = dateFormat.format(result);
        month.setText(datetime);
    }


    private void setDateShamsi_daily(){
        PersianDate b = new PersianDate();
        PersianDateFormat dateFormattt = new PersianDateFormat(" Y/m/d ");
        daily = dateFormattt.format(b);
    }


    private void setDateShamsi_weekly(){
        PersianDate a = new PersianDate();
        PersianDateFormat dateFormatt = new PersianDateFormat(" Y/m/d ");
        a = new PersianDate(a.getTime()- 604800000L );
        String datetimee = dateFormatt.format(a);
        month.setText(datetimee);
    }


    @Override
    protected void onResume() {
        super.onResume();
        num_product.setText(Integer.toString(dao_p.getProductList().size()));
        num_customer.setText(Integer.toString(dao_c.getCustomerList().size()));
        num_grouping.setText(Integer.toString(dao_g.getGroupingList().size()));

        /* daily price */
        List<String> cost = new ArrayList<>();
        cost.addAll(dao_order.alldate(daily));
        int j = 0 ;
        for (int i = 0; i < dao_order.getOrderList().size() ; i++) {
            String dd = cost.get(i);
            j = j + Tools.convertToPrice(dd);
        }
        day.setText(Tools.getForamtPrice(String.valueOf(j)));
        create_chart(j);
    }


}