package com.example.foodorderiing.activity.product;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.VideoView;

import com.example.foodorderiing.R;
import com.example.foodorderiing.adapter.ProductAdapter;
import com.example.foodorderiing.database.DatabaseHelper;
import com.example.foodorderiing.database.dao.GroupingDao;
import com.example.foodorderiing.database.dao.ProductDao;
import com.example.foodorderiing.model.Product;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AddNewProductActivity extends AppCompatActivity {

    public EditText et_name ,et_price ;
    TextView tv_save , tv_cancel;
    VideoView videoView;
    Spinner spinner;
    ArrayAdapter<String> adapterSp;
    String[] sp = {"فست فود","سالاد","چلوها"};
    Product product;
    DatabaseHelper db;
    ProductDao dao_product;
    GroupingDao dao_grouping;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_product);

        set_VideoView();
        db = DatabaseHelper.getInstance(getApplicationContext());
        dao_product = db.productDao();
        set_Spinner();

        et_name = findViewById(R.id.et_get_name_product);
        et_price = findViewById(R.id.et_get_price_product);

        tv_save = findViewById(R.id.tv_save_product);
        tv_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nameProduct = et_name.getText().toString();
                String priceProduct = et_price.getText().toString();

                product = new Product(nameProduct ,priceProduct);
                dao_product.insertProduct(product);

                finish();

            }
        });

        tv_cancel = findViewById(R.id.tv_cancel_product);
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Intent intent = getIntent();
        String getNameProduct = intent.getStringExtra("nameProduct");
        String getPriceProduct = intent.getStringExtra("priceProduct");
        et_name.setText(getNameProduct);
        et_price.setText(getPriceProduct);
//        dao_product.updateProduct(new Product(getNameProduct,getPriceProduct));
    }



    public void set_VideoView(){
        videoView = findViewById(R.id.vedio);
        String path = "android.resource://com.example.foodorderiing/"+R.raw.ffffffffff;
        Uri u = Uri.parse(path);
        videoView.setVideoURI(u);
        videoView.start();
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setLooping(true);
            }
        });
    }


    public void set_Spinner(){
        List<String> nf = new ArrayList<>();
        dao_grouping = db.groupingDao();
        for (int i = 0; i < dao_grouping.getGroupingList().size(); i++) {
            nf = Collections.singletonList(dao_grouping.getGroupingList().get(i).name);
        }


        spinner = findViewById(R.id.spinner_product);
        adapterSp = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,nf);
        spinner.setAdapter(adapterSp);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


        @Override
    protected void onResume() {
        videoView.resume();
        super.onResume();

        }

    @Override
    protected void onPause() {
        videoView.suspend();
        super.onPause();
    }


    @Override
    protected void onDestroy() {
        videoView.stopPlayback();
        if (db != null) db.close();
        super.onDestroy();
    }
}