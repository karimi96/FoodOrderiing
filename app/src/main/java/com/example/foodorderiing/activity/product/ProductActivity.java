package com.example.foodorderiing.activity.product;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodorderiing.R;
import com.example.foodorderiing.design.BlureImage;

public class ProductActivity extends AppCompatActivity {

    ImageView imageView , i2,i3,i4,i5 , img_menu;
    TextView tt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);


        img_menu = findViewById(R.id.img_menu);
        tt = findViewById(R.id.textView43);
        registerForContextMenu(tt);
//        tt.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(ProductActivity.this, "helo", Toast.LENGTH_SHORT).show();
//
//            }
//        });

        imageView = findViewById(R.id.img_ke);
        i2 = findViewById(R.id.img_fastfo);
        i3 = findViewById(R.id.img_fastfod4);
        i4 = findViewById(R.id.img_ke);
        i5 = findViewById(R.id.img_fastfod);
//        Bitmap bm = BitmapFactory.decodeResource(getResources(),R.drawable.food_orang );
//        Bitmap bit_kebab = BlureImage.blur18(getApplicationContext(),bm,10f);
//        imageView.setImageBitmap(bit_kebab);

//        Bitmap bmm = BitmapFactory.decodeResource(getResources(),R.drawable.food_honey );
//        Bitmap bit_kebabb = BlureImage.blur18(getApplicationContext(),bmm,10f);
//        i2.setImageBitmap(bit_kebabb);
//
//        Bitmap bm2 = BitmapFactory.decodeResource(getResources(),R.drawable.food_kebab );
//        Bitmap bit_kebab2 = BlureImage.blur18(getApplicationContext(),bm2,10f);
//        i3.setImageBitmap(bit_kebab2);
//
//        Bitmap bm3 = BitmapFactory.decodeResource(getResources(),R.drawable.food_egg );
//        Bitmap bit_kebab3 = BlureImage.blur18(getApplicationContext(),bm3,10f);
//        i4.setImageBitmap(bit_kebab3);
//
//        Bitmap bm4 = BitmapFactory.decodeResource(getResources(),R.drawable.food_toast );
//        Bitmap bit_kebab4 = BlureImage.blur18(getApplicationContext(),bm4,10f);
//        i5.setImageBitmap(bit_kebab4);




    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_product,menu);
//return true;
//    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.menu_product,menu);

    }
//
//    @Override
//    public boolean onContextItemSelected(@NonNull MenuItem item) {
//        return super.onContextItemSelected(item);
//    }

    //    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_product,menu);
//        return true;
//    }



}