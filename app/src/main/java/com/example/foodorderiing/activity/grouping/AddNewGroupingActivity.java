package com.example.foodorderiing.activity.grouping;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;
import com.example.foodorderiing.R;
import com.example.foodorderiing.database.DatabaseHelper;
import com.example.foodorderiing.database.dao.GroupingDao;
import com.example.foodorderiing.model.Grouping;
import com.example.foodorderiing.model.Product;
import com.google.gson.Gson;

import java.util.Arrays;
import java.util.List;

public class AddNewGroupingActivity extends AppCompatActivity {
    EditText editText_category;
    DatabaseHelper db;
    GroupingDao dao_grouping;
    TextView tv_save , tv_cancel;
    String name;
    Grouping g;
    String old_name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_add_new_grouping);

        db = DatabaseHelper.getInstance(getApplicationContext());
        dao_grouping = db.groupingDao();
        editText_category = findViewById(R.id.et_get_category_grouping);

        if(getIntent().getExtras() != null){
            String getGrouping =getIntent().getStringExtra("grouping");
            g = new Gson().fromJson(getGrouping, Grouping.class);
            old_name = g.name;
            editText_category.setText(g.name); }

        click_save();
        click_cancel();
        hideInputType();

    }


    public void click_save(){
        tv_save = findViewById(R.id.tv_save_group);
        tv_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                name = editText_category.getText().toString();

                if(g == null){

                        if(TextUtils.isEmpty(name)){

                            Toast.makeText(AddNewGroupingActivity.this, "فیلد مورد نظر را پرکنید!!!", Toast.LENGTH_LONG).show();
    //
                        }else if(dao_grouping.getOneName(name) != null){
                            Toast.makeText(AddNewGroupingActivity.this, "این نام وجود دارد", Toast.LENGTH_LONG).show();

                        } else {
                            dao_grouping.insertGrouping(new Grouping(name));
                            Toast.makeText(getApplicationContext(), name + " با موفقیت به لیست اضافه شد ", Toast.LENGTH_LONG).show();
                            finish();

                        }
                }else {
                    g.name = name;
                    dao_grouping.updateGrouping(g);
                    Toast.makeText(getApplicationContext(),  old_name + " با موفقیت به " + name + " تغییر کرد", Toast.LENGTH_LONG).show();
                    finish();
                }

                overridePendingTransition(android.R.anim.fade_in , android.R.anim.fade_out);

            }
        });

    }


    public void click_cancel(){
        tv_cancel = findViewById(R.id.tv_cancel_group);
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(android.R.anim.fade_in , android.R.anim.fade_out);

            }
        });
    }


    public void check_array(){
        for (int i = 0; i < dao_grouping.getGroupingList().size(); i++) {
            if (name != dao_grouping.getGroupingList().get(i).name) {
                
            }else {
                Toast.makeText(this, "repetity", Toast.LENGTH_SHORT).show();
            }

        }
    }

    private void hideInputType(){
        editText_category.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken() , 0);
                }

            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
    }


    @Override
    protected void onPause() {
        super.onPause();
    }


    @Override
    protected void onDestroy() {
//        if (db != null) db.close();
        super.onDestroy();
    }

}