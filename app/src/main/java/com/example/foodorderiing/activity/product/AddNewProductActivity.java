package com.example.foodorderiing.activity.product;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.foodorderiing.R;
import com.example.foodorderiing.activity.grouping.AddNewGroupingActivity;
import com.example.foodorderiing.adapter.ProductAdapter;
import com.example.foodorderiing.database.DatabaseHelper;
import com.example.foodorderiing.database.dao.GroupingDao;
import com.example.foodorderiing.database.dao.ProductDao;
import com.example.foodorderiing.model.Product;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AddNewProductActivity extends AppCompatActivity {

    EditText et_name ,et_price ;
    AutoCompleteTextView autoCompleteTextView;
    ArrayAdapter<String> adapter_autocomplete;
    TextView tv_save , tv_cancel;
    VideoView videoView;
    DatabaseHelper db;
    ProductDao dao_product;
    GroupingDao dao_grouping;
    Product p = null;
    String itemCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_add_new_product);



        db = DatabaseHelper.getInstance(getApplicationContext());
        dao_product = db.productDao();
        dao_grouping = db.groupingDao();

        et_name = findViewById(R.id.et_get_name_product);
        et_price = findViewById(R.id.et_get_price_product);
        tv_save = findViewById(R.id.tv_save_product);
        autoCompleteTextView  = findViewById(R.id.autoComplete_tv);
        adapter_autocomplete = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_dropdown_item_1line,dao_grouping.getname());
        autoCompleteTextView.setThreshold(1);
        autoCompleteTextView.setAdapter(adapter_autocomplete);
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                itemCategory = adapter_autocomplete.getItem(position);
            }
        });


        if (getIntent().getExtras() != null){
            String getNameProduct = getIntent().getStringExtra("product");
            p = new Gson().fromJson(getNameProduct,Product.class);
            et_name.setText(p.name);
            autoCompleteTextView.setText(p.category);
            et_price.setText(p.price); }


        set_VideoView();
//        set_Spinner();

        tv_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nameProduct = et_name.getText().toString();
                String categoryProduct = autoCompleteTextView.getText().toString();
                String priceProduct = et_price.getText().toString();


                if (p == null){
                    if(TextUtils.isEmpty(nameProduct) || TextUtils.isEmpty(categoryProduct) ||TextUtils.isEmpty(priceProduct)){
                        Toast.makeText(getApplicationContext(), "فیلد مورد نظر را پرکنید!!!", Toast.LENGTH_SHORT).show();
                    }else {
                        dao_product.insertProduct(new Product(nameProduct,categoryProduct, priceProduct));
                        Toast.makeText(getApplicationContext(), nameProduct + " با موفقیت به لیست اضافه شد ", Toast.LENGTH_LONG).show();
                        finish();

                    }
                }else {
                    p.name = nameProduct;
                    p.category = categoryProduct;
                    p.price = priceProduct;
                    Log.e("qqqq", "onClick: update product=" + p.id );
                    dao_product.updateProduct(p);
                    Toast.makeText(getApplicationContext()," با موفقیت تغییر کرد " , Toast.LENGTH_LONG).show();
                    finish();
                }
                overridePendingTransition(android.R.anim.fade_in , android.R.anim.fade_out);


            }
        });

        tv_cancel = findViewById(R.id.tv_cancel_product);
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(android.R.anim.fade_in , android.R.anim.fade_out);

            }
        });
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

//
//    public void set_Spinner(){
//        List<String> nf = new ArrayList<>();
//        dao_grouping = db.groupingDao();
//        for (int i = 0; i < dao_grouping.getGroupingList().size(); i++) {
//            nf = Collections.singletonList(dao_grouping.getGroupingList().get(i).name);
//        }


//        spinner = findViewById(R.id.spinner_product);
//        adapterSp = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,nf);
//        spinner.setAdapter(adapterSp);
//        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                String s = (String) parent.getItemAtPosition(position);
//                Log.e("qqqq", "onItemSelected: "+s );
//            }
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
//    }


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
        super.onDestroy();
    }
}