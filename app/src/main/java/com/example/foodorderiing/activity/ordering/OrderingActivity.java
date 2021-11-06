package com.example.foodorderiing.activity.ordering;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.foodorderiing.R;

public class OrderingActivity extends AppCompatActivity {
    ImageView increase;
    ImageView dicrease;
    TextView num_Ordring;
    int num = 0 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ordering);

        increase = findViewById(R.id.img_inCrease);
        dicrease = findViewById(R.id.img_diCrease);
        num_Ordring = findViewById(R.id.tv_number_of_order);
        num_Ordring.setText("0");

        increase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                num++;
                num_Ordring.setText(num);
            }
        });

        dicrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });

    }
}