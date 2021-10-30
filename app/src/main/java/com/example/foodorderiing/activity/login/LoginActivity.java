package com.example.foodorderiing.activity.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.foodorderiing.R;
import com.example.foodorderiing.activity.home.HomeActivity;
import com.example.foodorderiing.design.BlureImage;

public class LoginActivity extends AppCompatActivity {
    ImageView img_back;
    TextView tv_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        init();
        Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.food_honey);
        Bitmap bit_kebab = BlureImage.blur18(getApplicationContext(),bm,18f);
        img_back.setImageBitmap(bit_kebab);

        tv_login = findViewById(R.id.tv_login);
        tv_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                Intent intent = new Intent(LoginActivity.this,HomeActivity.class);
                startActivity(intent);
            }
        });




    }

    public void init(){
        img_back = findViewById(R.id.img_background);

    }

}