package com.example.foodorderiing.activity.product;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import com.example.foodorderiing.R;
import com.example.foodorderiing.database.DatabaseHelper;
import com.example.foodorderiing.database.dao.GroupingDao;
import com.example.foodorderiing.database.dao.ProductDao;
import com.example.foodorderiing.design.NumberTextWatcherForThousand;
import com.example.foodorderiing.model.Product;
import com.google.gson.Gson;

public class AddNewProductActivity extends AppCompatActivity {
    private EditText et_name ,et_price ;
    private AutoCompleteTextView autoTextView_grouing;
    private ArrayAdapter<String> adapter_autocomplete;
    private TextView tv_save , tv_cancel;
    private VideoView videoView;
    private DatabaseHelper db;
    private ProductDao dao_product;
    private GroupingDao dao_grouping;
    private Product p = null;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_add_new_product);

        initID();
        initDataBase();
        hideKeyBord();
        set_VideoView();

        if (getIntent().getExtras() != null){
            String getNameProduct = getIntent().getStringExtra("product");
            p = new Gson().fromJson(getNameProduct,Product.class);
            et_name.setText(p.name);
            autoTextView_grouing.setText(p.category);
            et_price.setText(p.price);
        }

        et_price.addTextChangedListener(new NumberTextWatcherForThousand(et_price));
        initOutoTextView();
        actionSave();
        actionCancel();
    }


    private void initDataBase(){
        db = DatabaseHelper.getInstance(getApplicationContext());
        dao_product = db.productDao();
        dao_grouping = db.groupingDao();
    }


    private void initID(){
        tv_save = findViewById(R.id.tv_save_product);
        et_name = findViewById(R.id.et_get_name_product);
        et_price = findViewById(R.id.et_get_price_product);
        autoTextView_grouing  = findViewById(R.id.autoComplete_tv);
        tv_cancel = findViewById(R.id.tv_cancel_product);

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


    private void hideKeyBord(){
        et_name.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken() , 0);
                }

            }
        });

        et_price.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken() , 0);
                }

            }
        });

        autoTextView_grouing.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken() , 0);
                }

            }
        });
    }


    private void initOutoTextView(){
        adapter_autocomplete = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_dropdown_item_1line,dao_grouping.getname());
        autoTextView_grouing.setAdapter(adapter_autocomplete);
        autoTextView_grouing.setThreshold(0);
        autoTextView_grouing.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                parent.getItemAtPosition(position)
            }
        });

//        autoCompleteTextView.setOnFocusChangeListener((v, hasFocus) -> {
//            if (hasFocus) autoCompleteTextView.showDropDown();
//        });
//
//        autoCompleteTextView.setOnClickListener(v -> {
//            autoCompleteTextView.showDropDown();
//        });
    }



    private void actionSave(){
        tv_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nameProduct = et_name.getText().toString();
                String categoryProduct = autoTextView_grouing.getText().toString();
                String priceProduct = et_price.getText().toString();

                if (p == null){
                    if(TextUtils.isEmpty(nameProduct) || TextUtils.isEmpty(categoryProduct) ||TextUtils.isEmpty(priceProduct)){
                        Toast.makeText(getApplicationContext(), "فیلد مورد نظر را پرکنید!!!", Toast.LENGTH_SHORT).show();

                    }else {
                        if(dao_product.getOneName(nameProduct) != null){
                            Toast.makeText(AddNewProductActivity.this, " این محصول وجود دارد ", Toast.LENGTH_SHORT).show();

                        }else if(dao_grouping.getOneName(categoryProduct) == null){
                            Toast.makeText(AddNewProductActivity.this,  " دسته بندی "+ categoryProduct + " وجود ندارد ", Toast.LENGTH_SHORT).show();

                        }else {
                            dao_product.insertProduct(new Product(nameProduct,categoryProduct, priceProduct));
                            Toast.makeText(getApplicationContext(), nameProduct + " با موفقیت به لیست اضافه شد ", Toast.LENGTH_LONG).show();
                            finish();

                        }
                    }
                }else {
                    p.name = nameProduct;
                    p.category = categoryProduct;
                    p.price = priceProduct ;
                    Log.e("qqqq", "onClick: update product=" + p.product_id );
                    dao_product.updateProduct(p);
                    Toast.makeText(getApplicationContext()," با موفقیت تغییر کرد " , Toast.LENGTH_LONG).show();
                    finish();
                }
                overridePendingTransition(android.R.anim.fade_in , android.R.anim.fade_out);


            }
        });
    }


    private void actionCancel(){
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(android.R.anim.fade_in , android.R.anim.fade_out);
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
        super.onDestroy();
    }
}