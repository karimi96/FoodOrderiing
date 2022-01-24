package com.example.foodorderiing.activity.home;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.foodorderiing.R;
import com.example.foodorderiing.activity.customer.CustomerActivity;
import com.example.foodorderiing.activity.grouping.GroupingActivity;
import com.example.foodorderiing.activity.listOrder.ListOrder;
import com.example.foodorderiing.activity.order.OrderActivity;
import com.example.foodorderiing.activity.product.ProductActivity;
import com.example.foodorderiing.database.DatabaseHelper;
import com.example.foodorderiing.database.dao.CustomerDao;
import com.example.foodorderiing.database.dao.GroupingDao;
import com.example.foodorderiing.database.dao.OrderDao;
import com.example.foodorderiing.database.dao.ProductDao;
import com.example.foodorderiing.helper.Tools;
import com.example.foodorderiing.model.ChartModel;
import com.example.foodorderiing.model.Order;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import ir.hamsaa.persiandatepicker.util.PersianCalendar;


public class HomeActivity extends AppCompatActivity {

    private ArrayList<ChartModel> chartModels;

    private CardView cardView_product ,cardView_customer ,cardView_grouping ,cardView_ListOrder,waiting;
    private TextView title ,num_product ,num_customer ,num_grouping;
    private DatabaseHelper db;
    private ProductDao dao_p;
    private GroupingDao dao_g;
    private CustomerDao dao_c;
    private OrderDao dao_order;
    private ImageView img_ordering;
    private TextView numOrder ,monthlyTotal ,weeklyTotal , dailyTotal, dayName, monthName, titleHome;
    private String date;
    private ListOrder listOrder;

    private  List<String> d;
    private  List<Integer> t;


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
//        showBarChart();
        populateChart();
        create_chart22();
    }

    @Override
    protected void onResume() {
        super.onResume();
        num_product.setText(Integer.toString(dao_p.getProductList().size()));
        num_customer.setText(Integer.toString(dao_c.getCustomerList().size()));
        num_grouping.setText(Integer.toString(dao_g.getGroupingList().size()));
        numberListOrder();
        getTotalDaily();
        getTotalWeekly();
        getTotalMonthly();
        setName();
//        test();
//        dd();
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
        cardView_ListOrder = findViewById(R.id.cardview_listOrder);
        waiting = findViewById(R.id.waiting);
        num_product = findViewById(R.id.number_of_product);
        num_customer = findViewById(R.id.number_of_customer);
        num_grouping = findViewById(R.id.number_of_grouping);
        numOrder = findViewById(R.id.text_num_order);
        img_ordering = findViewById(R.id.img_shoping);
        monthlyTotal = findViewById(R.id.month);
        weeklyTotal = findViewById(R.id.week);
        dailyTotal = findViewById(R.id.day);
        dayName = findViewById(R.id.dayName);
        monthName = findViewById(R.id.monthName);
        titleHome = findViewById(R.id.title_home);
    }


    public void toolbar() {
        title = findViewById(R.id.title_home);
        title.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        title.setSelected(true);
    }


    private void initGetOrder() {
        img_ordering.setOnClickListener(v -> {
                startActivity(new Intent(HomeActivity.this, OrderActivity.class));
        });
    }


    private void gOToProduct(){
        cardView_product.setOnClickListener(v -> {
                startActivity(new Intent(HomeActivity.this, ProductActivity.class));
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        });
    }


    private void goToCustomer(){
        cardView_customer.setOnClickListener(v ->  {
                startActivity(new Intent(HomeActivity.this, CustomerActivity.class));
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        });
    }


    private void goToGrouping(){
        cardView_grouping.setOnClickListener(v -> {
                startActivity(new Intent(HomeActivity.this, GroupingActivity.class));
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        });
    }


    private void initListOrder(){
        cardView_ListOrder.setOnClickListener(v -> {
                startActivity(new Intent(HomeActivity.this, ListOrder.class));
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        });
    }




//    private void test(){
//        d = new ArrayList<>();
//        t = new ArrayList<>();
//        d.addAll(dao_order.getAllDate());
//        for (int i = 0; i < dao_order.getAllDate().size(); i++) {
//            int a = get(d.get(i));
//            t.add(i,a);
//        }
//    }


//    private int get(String dataaaa){
//        List<String> totall = new ArrayList<>();
//        int j = 0 ;
//        try {
//            totall.addAll(dao_order.dailyTotal(dataaaa));
//            for (int i = 0; i < dao_order.getOrderList().size() ; i++) {
//                String t = totall.get(i);
//                j = j + Tools.convertToPrice(t);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return j;
//    }


//    private void dd(){
//        d = new ArrayList<>();
//        t = new ArrayList<>();
//
//        String a;
//        int cc;
//
//        List<Order> x = new ArrayList<>();
//        x.addAll( dao_order.getOrderList());
//        for (int i = 0; i < dao_order.getOrderList().size(); i++) {
//            a = x.get(i).date;
//            cc = Tools.convertToPrice(x.get(i).total);
//            d.add(i,a);
//            for (int j = 0; j < dao_order.getOrderList().size(); j++) {
//                if(a == dao_order.getOrderList().get(j+1).date){
//                    cc = cc + Tools.convertToPrice( dao_order.getOrderList().get(j+1).total);
//                }
//                t.add(j,cc);
//            }
//
//        }
//
//    }


//    public void create_chart() {
//        BarChart bar_chart = findViewById(R.id.chart_bar);
//
////        List<String> xDate = new ArrayList<>();
////        for (int i = 0; i < dao_order.getAllDate().size(); i++) {
////            xDate.add(dao_order.getAllDate().get(i));
////        }
////
////        List<String> yDate = new ArrayList<>();
////        for (int i = 0; i < dao_order.getAllTotal().size(); i++) {
////            yDate.add(dao_order.getAllTotal().get(i));
////        }
//
//
////        ArrayList<BarEntry> yVals = new ArrayList<>();
////        for (int i = 0; i < yDate.size(); i++) {
////            yVals.add(new BarEntry( Integer.valueOf(yDate.get(i)) ,i));
////        }
//////
////        ArrayList<String> xVals = new ArrayList<>();
////        for (int i = 0; i < xDate.size(); i++) {
////            xVals.add(xDate.get(i));
////        }
//
//
//
//        ArrayList<BarEntry> yVals = new ArrayList<>();
//        for (int i = 0; i < t.size(); i++) {
//            yVals.add(new BarEntry( t.get(i) ,i));
//        }
////
//        ArrayList<String> xVals = new ArrayList<>();
//        for (int i = 0; i < d.size(); i++) {
//            xVals.add(d.get(i));
//        }
//

//        BarDataSet barDataSet = new BarDataSet(yVals, "");
////        barDataSet.setColors(Color.rgb(241, 92, 65));
//        barDataSet.setValueTextSize(0f);
//
//        BarData barData = new BarData(xVals ,barDataSet);
//
////        bar_chart.setFitBars(true);
//        bar_chart.setData(barData);
////        bar_chart.getDescription().setText("");
////        bar_chart.animateY(1000);
////
////        XAxis xAxis = bar_chart.getXAxis();
////        xAxis.setGranularity(1f);
////        xAxis.setDrawAxisLine(true);
////        xAxis.setDrawGridLines(false);
//    }



    private void populateChart(){
        Log.e("qqqqmain", "populateChart: started" );
        ArrayList<Order> list = new ArrayList<>(dao_order.getOrderList());
        chartModels = new ArrayList<>();

        Log.e("qqqqmain", "populateChart: "+list.size() + "-"+ chartModels.size() );

        for (int i = 0; i < list.size(); i++) {


            Log.e("qqqqmain2", "populateChart: for list is started \n"
            + list.get(i).date
            );

            if (chartModels.size() == 0){
                Log.e("qqqqmain", "populateChart: chartModels.size() == 0" );

                chartModels.add(new ChartModel(list.get(i).date,Tools.convertToPrice(list.get(i).total)));
            }else {
                Log.e("qqqqmain", "populateChart: chartModels.size() > 0" );

                for (int c = 0; c < chartModels.size(); c++) {
                    Log.e("qqqqmain2", "populateChart: for chart model is started\n"
                            + chartModels.get(c).getDate() );

                    if (list.get(i).date.equals(chartModels.get(c).getDate())){
                        chartModels.get(c).setTotal(chartModels.get(c).getTotal() + Tools.convertToPrice(list.get(i).total));
//                        return;
                    }else {
                        chartModels.add(new ChartModel(list.get(i).date,Tools.convertToPrice(list.get(i).total)));
                    }
                }
            }
        }
    }

    public void create_chart22() {
        BarChart bar_chart = findViewById(R.id.chart_bar);
        ArrayList<BarEntry> visitor = new ArrayList<>();

//        List<String> xDate = new ArrayList<>();
//        for (int i = 0; i < dao_order.getAllDate().size(); i++) {
//            xDate.add(dao_order.getAllDate().get(i));
//        }
//
//        List<String> yTotal = new ArrayList<>();
//        for (int i = 0; i < dao_order.getAllTotal().size(); i++) {
//            yTotal.add(dao_order.getAllTotal().get(i));
//        }
//
//        for (int i = 0; i < xDate.size(); i++) {
//            visitor.add(new BarEntry( i+1 , Tools.convertToPrice(yTotal.get(i)) ));
//        }

        for (int i = 0; i < chartModels.size(); i++) {
            visitor.add(new BarEntry( i, (int) chartModels.get(i).getTotal() ));

        }


        BarDataSet barDataSet = new BarDataSet(visitor, "");
        barDataSet.setColors(Color.rgb(241, 92, 65));
        barDataSet.setValueTextSize(0f);

        BarData barData = new BarData(barDataSet);
        bar_chart.setFitBars(true);
        bar_chart.setData(barData);
        bar_chart.getDescription().setText("");
        bar_chart.animateY(1000);

        XAxis xAxis = bar_chart.getXAxis();
        xAxis.setGranularity(1f);
        xAxis.setDrawAxisLine(true);
        xAxis.setDrawGridLines(false);
    }



    private void numberListOrder(){
        if (dao_order.getOrderList().size() >= 1){
            int count = dao_order.getOrderList().size();
            numOrder.setText("( " + count + " ) ");
        }else {
            numOrder.setText("");
        }
    }


    private int getTotalDaily(){
        List<String> total = new ArrayList<>();
        int j = 0 ;
            try {
                total.addAll(dao_order.dailyTotal(Tools.getCurrentDate()));
                for (int i = 0; i < dao_order.getOrderList().size() ; i++) {
                    String t = total.get(i);
                    j = j + Tools.convertToPrice(t);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        dailyTotal.setText(Tools.getForamtPrice(String.valueOf(j)));
        return j;
    }


    private void getTotalWeekly(){
        List<Order> total = new ArrayList<>();
        total.addAll(dao_order.getOrderListDate(Tools.getSevenDayAgo()));
        int j = 0 ;
        for (int i = 0; i < total.size() ; i++) {
            String t = total.get(i).total;
            j = j + Tools.convertToPrice(t);
        }
        weeklyTotal.setText(Tools.getForamtPrice(String.valueOf(j)));
    }


    private void getTotalMonthly(){
        List<Order> total = new ArrayList<>();
        total.addAll(dao_order.getOrderListDate(Tools.getThirtyDaysAgo()));
        int j = 0 ;
        for (int i = 0; i < total.size() ; i++) {
            String t = total.get(i).total;
            j = j + Tools.convertToPrice(t);
        }
        monthlyTotal.setText(Tools.getForamtPrice(String.valueOf(j)));
    }


    private void setName(){
        dayName.setText(" ( " + Tools.getDayName() + " ) ");
        monthName.setText(" ( " + Tools.getMonthName() + " ) ");
    }


}