package com.example.foodorderiing.activity.grouping;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.example.foodorderiing.R;
import com.example.foodorderiing.database.DatabaseHelper;
import com.example.foodorderiing.database.dao.GroupingDao;
import com.example.foodorderiing.model.Grouping;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

import java.io.File;


public class AddNewGroupingActivity extends AppCompatActivity {
    private static final int PICK_IMAGE = 100;
    private EditText editText_category;
    private ImageView imageView_show ,imageView_back;
    private FloatingActionButton fab;
    private DatabaseHelper db;
    private GroupingDao dao_grouping;
    private TextView tv_save , tv_cancel;
    private String name ,old_name;
    private Grouping g;
    private Uri uri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_add_new_grouping);

       initDataBase();
       initID();
       initImage();

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
        dao_grouping = db.groupingDao(); }


    private void initID(){
        tv_save = findViewById(R.id.tv_save_group);
        tv_cancel = findViewById(R.id.tv_cancel_group);
        imageView_show = findViewById(R.id.image_show_g);
        imageView_back = findViewById(R.id.image_back_g);
        fab = findViewById(R.id.fab_add_g);
        editText_category = findViewById(R.id.et_get_category_grouping); }


    private void initImage(){
        fab.setOnClickListener(v -> {
            Intent intent = new Intent();
                intent.setType("image/*");
//                intent.setDataAndType(uri , "image/*");
                intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
//                intent.setAction(Intent.ACTION_GET_CONTENT);
//                intent.setAction(Intent.ACTION_PICK);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if ( resultCode == RESULT_OK) {
            if (requestCode == PICK_IMAGE){
                try {
                    uri = data.getData();
                    imageView_show.setImageURI(uri);
                    imageView_back.setVisibility(View.GONE);
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "try again ... ", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }


    public void click_save(){
        tv_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                name = editText_category.getText().toString();
                if(g == null){
                        if(imageView_show.getDrawable() == null){
                            Toast.makeText(getApplicationContext(), "لطفا یک عکس انتخاب کنید!!!", Toast.LENGTH_SHORT).show();
                        }
                        else if(TextUtils.isEmpty(name)){
                            Toast.makeText(AddNewGroupingActivity.this, "فیلد مورد نظر را پرکنید!!!", Toast.LENGTH_LONG).show();

                        }else if(dao_grouping.getOneName(name) != null){
                            Toast.makeText(AddNewGroupingActivity.this, "این نام وجود دارد", Toast.LENGTH_LONG).show();

                        } else {
                            dao_grouping.insertGrouping(new Grouping(name , uri.toString()));
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

}