package com.example.foodorderiing.activity.home;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
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
import com.example.foodorderiing.model.Order;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;


public class HomeActivity extends AppCompatActivity {
    private CardView cardView_product ,cardView_customer ,cardView_grouping ,cardView_ListOrder,waiting;
    private TextView title ,num_product ,num_customer ,num_grouping;
    private DatabaseHelper db;
    private ProductDao dao_p;
    private GroupingDao dao_g;
    private CustomerDao dao_c;
    private OrderDao dao_order;
    private ImageView img_ordering;
    private TextView numOrder ,month ,week ,day ;
    private String date;
    private Order orderModel;
    private ListOrder listOrder;
    String karimi;


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
//        setDateShamsi_daily();
//        setDateShamsi_weekly();
//        date = setDateShamsi_daily();
//        create_chart();
        showBarChart();

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
        month = findViewById(R.id.month);
        week = findViewById(R.id.week);
        day = findViewById(R.id.day);
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




    public void create_chart() {
        BarChart bar_chart = findViewById(R.id.chart_bar);
        ArrayList<BarEntry> visitor = new ArrayList<>();

                   visitor.add(new BarEntry(1,59000));
                   visitor.add(new BarEntry(2,5900));
                   visitor.add(new BarEntry(3,30000));
                   visitor.add(new BarEntry(4,5000));

//                SELECT date_time  FROM dsr_data
//                WHERE date_time
//                between DATEADD(DAY,-30,GETDATE()) and  GETDATE();

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


    private void initNumberOrder(){
//        ListOrder listOrder = new ListOrder();
//        if (orderModel.numOrder != null){
//            int count = orderModel.numOrder;
//            numOrder.setText(String.valueOf(count));
//        }else {
//            numOrder.setText("0");
//        }
    }



    @Override
    protected void onResume() {
        super.onResume();
        num_product.setText(Integer.toString(dao_p.getProductList().size()));
        num_customer.setText(Integer.toString(dao_c.getCustomerList().size()));
        num_grouping.setText(Integer.toString(dao_g.getGroupingList().size()));

    }


    private void showBarChart(){
        BarChart bar_chart = findViewById(R.id.chart_bar);
        ArrayList<Double> valueList = new ArrayList<Double>();
        ArrayList<BarEntry> entries = new ArrayList<>();
        String title = "Title";

        //input data
//        for(int i = 0; i < 6; i++){
//            valueList.add(i * 100.1);
//        }

        valueList.add(45000 / 0.1000);
        valueList.add(40000 / 0.1000);
        valueList.add(120000 / 0.1000);
        valueList.add(51000 / 0.1000);
        valueList.add(99000 / 0.1000);
        valueList.add(66000 / 0.1000);


        //fit the data into a bar
        for (int i = 0; i < valueList.size(); i++) {
            BarEntry barEntry = new BarEntry(i, valueList.get(i).floatValue());
            entries.add(barEntry);
        }

        BarDataSet barDataSet = new BarDataSet(entries, "");
//        barDataSet.setValueTextSize(12f);


        //Changing the color of the bar
        barDataSet.setColor(Color.parseColor("#304567"));
        //Setting the size of the form in the legend
        barDataSet.setFormSize(15f);
        //showing the value of the bar, default true if not set
        barDataSet.setDrawValues(false);
        //setting the text size of the value of the bar
        barDataSet.setValueTextSize(12f);


        //hiding the grey background of the chart, default false if not set
        bar_chart.setDrawGridBackground(false);
        //remove the bar shadow, default false if not set
        bar_chart.setDrawBarShadow(false);
        //remove border of the chart, default false if not set
        bar_chart.setDrawBorders(false);

        //remove the description label text located at the lower right corner
        Description description = new Description();
        description.setEnabled(false);
        bar_chart.setDescription(description);

        //setting animation for y-axis, the bar will pop up from 0 to its value within the time we set
        bar_chart.animateY(1000);
        //setting animation for x-axis, the bar will pop up separately within the time we set
        bar_chart.animateX(1000);



        XAxis xAxis = bar_chart.getXAxis();
        //change the position of x-axis to the bottom
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        //set the horizontal distance of the grid line
        xAxis.setGranularity(1f);
        //hiding the x-axis line, default true if not set
        xAxis.setDrawAxisLine(false);
        //hiding the vertical grid lines, default true if not set
        xAxis.setDrawGridLines(false);
        YAxis leftAxis = bar_chart.getAxisLeft();
        //hiding the left y-axis line, default true if not set
        leftAxis.setDrawAxisLine(false);

        YAxis rightAxis = bar_chart.getAxisRight();
        //hiding the right y-axis line, default true if not set
        rightAxis.setDrawAxisLine(false);

        Legend legend = bar_chart.getLegend();
        //setting the shape of the legend form to line, default square shape
        legend.setForm(Legend.LegendForm.LINE);
        //setting the text size of the legend
        legend.setTextSize(11f);
        //setting the alignment of legend toward the chart
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        //setting the stacking direction of legend
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        //setting the location of legend outside the chart, default false if not set
        legend.setDrawInside(false);

        BarData data = new BarData(barDataSet);
        bar_chart.setData(data);
        bar_chart.invalidate();
    }




    private void setDateMiladi_daily(){
        Calendar c = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        c.add(Calendar.DAY_OF_WEEK, +10);
        long result = c.getTimeInMillis();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String datetime = dateFormat.format(result);
//        month.setText(datetime);
    }


//    private String setDateShamsi_daily(){
//        PersianDate b = new PersianDate();
////        karimi =  b.dayName();
////        karimi = b.dayName();
//        PersianDateFormat dateFormattt = new PersianDateFormat(" Y/m/d ");
////        date = dateFormattt.format(b);
//        return dateFormattt.format(b);
//    }


//    private void setDateShamsi_weekly(){
//        PersianDate a = new PersianDate();
//        PersianDateFormat dateFormatt = new PersianDateFormat(" Y/m/d ");
//        a = new PersianDate(a.getTime()- 604800000L );
//        String datetimee = dateFormatt.format(a);
////        month.setText(datetimee);
//    }


    private int getTotalOneDay(String daily){
        List<String> cost = new ArrayList<>();
        cost.addAll(dao_order.allTotal(daily));
        int j = 0 ;
        for (int i = 0; i < dao_order.getOrderList().size() ; i++) {
            String dd = cost.get(i);
            j = j + Tools.convertToPrice(dd);
        }
        day.setText(Tools.getForamtPrice(String.valueOf(j)));
//        create_chart(j);
        return j;
    }



}