package com.example.foodorderiing.activity.grouping;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.foodorderiing.R;
import com.example.foodorderiing.database.DatabaseHelper;
import com.example.foodorderiing.database.dao.GroupingDao;
import com.example.foodorderiing.model.Grouping;
import com.google.gson.Gson;


public class AddNewGroupingActivity extends AppCompatActivity {
    private EditText editText_category;
    private DatabaseHelper db;
    private GroupingDao dao_grouping;
    private TextView tv_save , tv_cancel;
    private String name ,old_name;
    private Grouping g;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_add_new_grouping);

       initDataBase();
       initID();
       editText_category = findViewById(R.id.et_get_category_grouping);

       if(getIntent().getExtras() != null){
            String getGrouping =getIntent().getStringExtra("grouping");
            g = new Gson().fromJson(getGrouping, Grouping.class);
            old_name = g.name;
            editText_category.setText(g.name); }

        click_save();
        click_cancel();
        hideKeyBord();

    }


    private void initDataBase(){
        db = DatabaseHelper.getInstance(getApplicationContext());
        dao_grouping = db.groupingDao();
    }

    private void initID(){
        tv_save = findViewById(R.id.tv_save_group);
        tv_cancel = findViewById(R.id.tv_cancel_group);

    }


    public void click_save(){
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
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(android.R.anim.fade_in , android.R.anim.fade_out);
            }
        });
    }


    private void hideKeyBord(){
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