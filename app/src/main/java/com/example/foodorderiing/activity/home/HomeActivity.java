package com.example.foodorderiing.activity.home;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.airbnb.lottie.LottieAnimationView;
import com.example.foodorderiing.R;
import com.example.foodorderiing.activity.customer.CustomerActivity;
import com.example.foodorderiing.activity.grouping.GroupingActivity;
import com.example.foodorderiing.activity.listOrder.ListOrder;
import com.example.foodorderiing.activity.order.OrderActivity;
import com.example.foodorderiing.activity.product.ProductActivity;
import com.example.foodorderiing.activity.setting.SettingActivity;
import com.example.foodorderiing.database.DatabaseHelper;
import com.example.foodorderiing.database.dao.CustomerDao;
import com.example.foodorderiing.database.dao.GroupingDao;
import com.example.foodorderiing.database.dao.OrderDao;
import com.example.foodorderiing.database.dao.ProductDao;
import com.example.foodorderiing.helper.App;
import com.example.foodorderiing.helper.CustomDialog;
import com.example.foodorderiing.helper.Session;
import com.example.foodorderiing.helper.Tools;
import com.example.foodorderiing.model.ChartModel;
import com.example.foodorderiing.model.Order;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;
import java.util.List;


public class HomeActivity extends AppCompatActivity {

    private ArrayList<ChartModel> chartModels;

    private CardView cardView_product, cardView_customer, cardView_grouping, cardView_ListOrder, waiting;
    private TextView num_product, num_customer, num_grouping;
    private DatabaseHelper db;
    private ProductDao dao_p;
    private GroupingDao dao_g;
    private CustomerDao dao_c;
    private OrderDao dao_order;
    private ImageView img_ordering, img_menu;
    private TextView numOrder, monthlyTotal, weeklyTotal, dailyTotal, dayName, monthName, alltotal, titleHome, titleDrawer;
    private String date;
    private ListOrder listOrder;
    private DrawerLayout drawer;
    private LinearLayout setting_drawer, aboutUs_drawer, guid_drawer, exit_drawer;
    private LottieAnimationView lottie_chart;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initDataBase();
        initID();
        initGetOrder();
        initListOrder();
        gOToProduct();
        goToCustomer();
        goToGrouping();
        setNavigationDrawer();
        setDrawer();
    }


    @Override
    protected void onResume() {
        super.onResume();
        setNumberOf_Product_Customer_Grouping();
        numberListOrder();
        setTotalDaily();
        setTotalWeekly();
        setTotalMonthly();
        setAllTotal();
        setName();
        populateChart();
        create_chart22();
        setNameTitle();
        setNavigationDrawer();
    }


    private void initDataBase() {
        db = App.getDatabase();
        dao_p = db.productDao();
        dao_c = db.customerDao();
        dao_g = db.groupingDao();
        dao_order = db.orderDao();
    }


    private void initID() {
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
        alltotal = findViewById(R.id.allTotal);
        titleHome = findViewById(R.id.title_home);
        titleDrawer = findViewById(R.id.title_myDrawer);
        img_menu = findViewById(R.id.img_menu);
        drawer = findViewById(R.id.myDrawer);
        setting_drawer = findViewById(R.id.setting_nav);
        aboutUs_drawer = findViewById(R.id.about_nav);
        guid_drawer = findViewById(R.id.guid_nav);
        exit_drawer = findViewById(R.id.exist_nav);
        lottie_chart = findViewById(R.id.lottie_chart);
    }


    private void setNumberOf_Product_Customer_Grouping() {
        if (dao_p.getProductList().size() == 0) num_product.setText("");
        else num_product.setText(Integer.toString(dao_p.getProductList().size()));

        if (dao_c.getCustomerList().size() == 0) num_customer.setText("");
        else num_customer.setText(Integer.toString(dao_c.getCustomerList().size()));

        if (dao_g.getGroupingList().size() == 0) num_grouping.setText("");
        else num_grouping.setText(Integer.toString(dao_g.getGroupingList().size()));
    }


    private void setNameTitle() {
        if (Session.getInstance().getString("name") != null) {
            titleHome.setText("غذای سرای " + Session.getInstance().getString("name"));
            titleDrawer.setText("غذای سرای " + Session.getInstance().getString("name"));
        }
    }


    private void setNavigationDrawer() {
        img_menu.setOnClickListener(v -> {
            drawer.openDrawer(GravityCompat.END);
        });
    }


    private void initGetOrder() {
        img_ordering.setOnClickListener(v -> {
            startActivity(new Intent(HomeActivity.this, OrderActivity.class));
        });
    }


    private void gOToProduct() {
        cardView_product.setOnClickListener(v -> {
            startActivity(new Intent(HomeActivity.this, ProductActivity.class));
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        });
    }


    private void goToCustomer() {
        cardView_customer.setOnClickListener(v -> {
            startActivity(new Intent(HomeActivity.this, CustomerActivity.class));
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        });
    }


    private void goToGrouping() {
        cardView_grouping.setOnClickListener(v -> {
            startActivity(new Intent(HomeActivity.this, GroupingActivity.class));
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        });
    }


    private void initListOrder() {
        cardView_ListOrder.setOnClickListener(v -> {
            startActivity(new Intent(HomeActivity.this, ListOrder.class));
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        });
    }

//
//    private void populateChart() {
//        Log.e("qqqqmain", "populateChart: started");
//        ArrayList<Order> list = new ArrayList<>(dao_order.getOrderList());
//        chartModels = new ArrayList<>();
//        //  chartModels.addAll(dao_chart.getListChart());
//
//        Log.e("qqqqmain", "populateChart: " + list.size() + "-" + chartModels.size());
//
//        for (int i = 0; i < list.size(); i++) {
//
//            Log.e("qqqqmain2", "populateChart: for list is started \n"
//                    + list.get(i).date);
//
//            if (chartModels.size() == 0) {
//                Log.e("qqqqmain", "populateChart: chartModels.size() == 0");
//
////                dao_chart.insertChart(new ChartModel(list.get(i).date, Tools.convertToPrice(list.get(i).total)));
//                chartModels.add(new ChartModel(list.get(i).date, Tools.convertToPrice(list.get(i).total)));
//                Log.e("qqqmain", "populateChart:  chartModels  " + chartModels.size() );
//                Log.e("qqqqmain", "populateChart: chartModels " + chartModels.get(i).date);
//            } else {
//                Log.e("qqqqmain", "populateChart: chartModels.size() > 0");
//
//                for (int c = 0; c < chartModels.size(); c++) {
//                    Log.e("qqqqmain2", "populateChart: for chart model is started \n "
//                            + chartModels.get(c).getDate());
//                    if (list.get(i).date.equals(chartModels.get(c).getDate())) {
//                        Log.e("qqqqmain", "populateChart: chart model" + list.get(i).date + "=" + chartModels.get(c).date);
//                        chartModels.get(c).setTotal(chartModels.get(c).getTotal() + Tools.convertToPrice(list.get(i).total));
//                    }
//                }
//                Log.e("qqqqmain", "populateChart: finish halghe "  );
//
//                chartModels.add(new ChartModel(list.get(i).date, Tools.convertToPrice(list.get(i).total)));
//            }
//        }
//    }



    private void populateChart() {
        Log.e("qqqqmain", "populateChart: started");
        ArrayList<Order> list = new ArrayList<>(dao_order.getOrderList());
        chartModels = new ArrayList<>();

        Log.e("qqqqmain", "populateChart: " + list.size() + "-" + chartModels.size());

        for (int i = 0; i < list.size(); i++) {

            Log.e("qqqqmain2", "populateChart: for list is started \n"
                    + list.get(i).date);

            if (chartModels.size() < 1) {
                Log.e("qqqqmain", "populateChart: chartModels.size() == 0");

//                dao_chart.insertChart(new ChartModel(list.get(i).date, Tools.convertToPrice(list.get(i).total)));
                chartModels.add(new ChartModel(list.get(0).date, Tools.convertToPrice(list.get(i).total)));

                Log.e("qqqmain", "populateChart:  chartModels  " + chartModels.size() );
                Log.e("qqqqmain", "populateChart: chartModels " + chartModels.get(i).date);

            } else {

                Log.e("qqqqmain", "populateChart: chartModels.size() > 0");

                for (int c = 0; c < chartModels.size(); c++) {
                    Log.e("qqqqmain2", "populateChart: for chart model is started \n "
                            + chartModels.get(c).getDate());
                    if (list.get(i).date.equals(chartModels.get(c).getDate())) {

                        chartModels.get(c).setTotal(chartModels.get(c).getTotal() + Tools.convertToPrice(list.get(i).total));
                    }else {
                        chartModels.add(new ChartModel(list.get(i).date, Tools.convertToPrice(list.get(i).total)));
                    }
                }
            }

        }
    }


    public void create_chart22() {

        BarChart bar_chart = findViewById(R.id.chart_bar);
        ArrayList<BarEntry> visitor = new ArrayList<>();

        if(dao_order.getOrderList().size() == 0) {
            lottie_chart.setVisibility(View.VISIBLE);
            bar_chart.setVisibility(View.GONE);
        }else {
            bar_chart.setVisibility(View.VISIBLE);
            lottie_chart.setVisibility(View.GONE);
        }


        for (int i = 0; i < chartModels.size(); i++) {
            visitor.add(new BarEntry(i + 1, (int) chartModels.get(i).getTotal()));
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


    private void numberListOrder() {
        if (dao_order.getOrderList().size() >= 1)
            numOrder.setText("( " + dao_order.getOrderList().size() + " ) ");
        else numOrder.setText("");
    }


    private int setTotalDaily() {
        List<String> total = new ArrayList<>(dao_order.dailyTotal(Tools.getCurrentDate()));
        int j = 0;
        try {
            for (int i = 0; i < dao_order.getOrderList().size(); i++) {
                String t = total.get(i);
                j = j + Tools.convertToPrice(t);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (j == 0) dailyTotal.setText("  _ ");
        else dailyTotal.setText(Tools.getForamtPrice(String.valueOf(j)));

        return j;
    }


    private void setTotalWeekly() {
        List<Order> total = new ArrayList<>(dao_order.getOrderListDate(Tools.getSevenDayAgo()));
        int j = 0;
        try {
            for (int i = 0; i < total.size(); i++) {
                String t = total.get(i).total;
                j = j + Tools.convertToPrice(t);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        if (j == 0) weeklyTotal.setText("  _ ");
        else weeklyTotal.setText(Tools.getForamtPrice(String.valueOf(j)));
    }


    private void setTotalMonthly() {
        List<Order> total = new ArrayList<>(dao_order.getOrderListDate(Tools.getThirtyDaysAgo()));
        int j = 0;
        try {
            for (int i = 0; i < total.size(); i++) {
                String t = total.get(i).total;
                j = j + Tools.convertToPrice(t);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        if (j == 0) monthlyTotal.setText("  _ ");
        else monthlyTotal.setText(Tools.getForamtPrice(String.valueOf(j)));
    }


    private void setName() {
        dayName.setText(" ( " + Tools.getDayName() + " ) ");
        monthName.setText(" ( " + Tools.getMonthName() + " ) ");
    }


    private void setAllTotal() {
        List<String> allTotal = new ArrayList<>(dao_order.getAllTotal());
        int t = 0;
        try {
            for (int i = 0; i < allTotal.size(); i++) {
                String at = allTotal.get(i);
                t = t + Tools.convertToPrice(at);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        if (t == 0) alltotal.setText("  _ ");
        else alltotal.setText(Tools.getForamtPrice(String.valueOf(t)));
    }


    private void setDrawer() {
        setting_drawer.setOnClickListener(v -> {
            startActivity(new Intent(HomeActivity.this, SettingActivity.class));
            drawer.closeDrawers();
        });

        guid_drawer.setOnClickListener(v -> {
            new CustomDialog().showDialogGuid(HomeActivity.this, R.layout.dialog_guid, drawer, this);
        });

        aboutUs_drawer.setOnClickListener(v -> {
            new CustomDialog().showDialogAboutUs(HomeActivity.this, R.layout.dialog_about_us, drawer, this);
        });

        exit_drawer.setOnClickListener(v -> {
            drawer.closeDrawers();
        });
    }


}