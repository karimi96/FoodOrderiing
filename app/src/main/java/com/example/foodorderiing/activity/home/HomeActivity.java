package com.example.foodorderiing.activity.home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.example.foodorderiing.R;
import com.example.foodorderiing.activity.customer.CustomerActivity;
import com.example.foodorderiing.activity.grouping.GroupingActivity;
import com.example.foodorderiing.activity.ordering.OrderingActivity;
import com.example.foodorderiing.activity.product.ProductActivity;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    CardView cardView_product;
    CardView cardView_customer;
    CardView cardView_grouping;
    CardView cardView_waiting;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

       create_chart();


        cardView_product = findViewById(R.id.cardView_product);
        cardView_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this,ProductActivity.class);
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in , android.R.anim.fade_out);

            }
        });

        cardView_customer = findViewById(R.id.cardview_customer);
        cardView_customer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, CustomerActivity.class);
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in , android.R.anim.fade_out);


            }
        });

        cardView_grouping = findViewById(R.id.cardView_grouping);
        cardView_grouping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, GroupingActivity.class);
                startActivity(intent);
                overridePendingTransition(android.R.anim.slide_in_left , android.R.anim.slide_out_right);

            }
        });

        cardView_waiting = findViewById(R.id.cardview_waiting);
        cardView_waiting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, OrderingActivity.class);
                startActivity(intent);
            }
        });




    }



    public void create_chart(){

        BarChart bar_chart = findViewById(R.id.chart_bar);
        ArrayList<BarEntry> visitor = new ArrayList<>();
        visitor.add(new BarEntry(1,100));
        visitor.add(new BarEntry(2,200));
        visitor.add(new BarEntry(3,250));
        visitor.add(new BarEntry(4,50));
        visitor.add(new BarEntry(5,800));
        visitor.add(new BarEntry(6,600));
        visitor.add(new BarEntry(7,100));
        visitor.add(new BarEntry(8,700));
        visitor.add(new BarEntry(9,300));
        visitor.add(new BarEntry(10,500));
        visitor.add(new BarEntry(11,700));
        visitor.add(new BarEntry(12,300));
        visitor.add(new BarEntry(13,500));
        visitor.add(new BarEntry(14,100));
        visitor.add(new BarEntry(15,200));
        visitor.add(new BarEntry(15,250));
        visitor.add(new BarEntry(16,50));
        visitor.add(new BarEntry(17,800));
        visitor.add(new BarEntry(18,600));
        visitor.add(new BarEntry(19,100));
        visitor.add(new BarEntry(20,700));
        visitor.add(new BarEntry(21,300));
        visitor.add(new BarEntry(22,500));
        visitor.add(new BarEntry(23,700));
        visitor.add(new BarEntry(24,300));
        visitor.add(new BarEntry(25,500));
        visitor.add(new BarEntry(26,100));
        visitor.add(new BarEntry(27,200));
        visitor.add(new BarEntry(28,250));
        visitor.add(new BarEntry(29,50));
        visitor.add(new BarEntry(30,800));


        BarDataSet barDataSet = new BarDataSet(visitor,"Visitors");
        barDataSet.setColors(Color.rgb(241,92,65));
        barDataSet.setValueTextColor(Color.BLACK);
//        barDataSet.setValueTextSize(16f);
        BarData barData = new BarData(barDataSet);

        bar_chart.setFitBars(true);
        bar_chart.setData(barData);
//        bar_chart.getDescription().setText("Bar chart example");
        bar_chart.animateY(1000);


    }
}