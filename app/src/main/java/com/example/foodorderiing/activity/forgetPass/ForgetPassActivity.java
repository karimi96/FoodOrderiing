package com.example.foodorderiing.activity.forgetPass;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.example.foodorderiing.R;

public class ForgetPassActivity extends AppCompatActivity {
    private LinearLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_pass);

        layout = findViewById(R.id.linear_forgetPass);
        layout.setOnClickListener(v -> {
            Intent call = new Intent(Intent.ACTION_VIEW,Uri.parse("tel:" + "09334659477"));
//            call.setData(Uri.parse("tel:" + "phonnumber"));
            startActivity(call);
        });
    }
}