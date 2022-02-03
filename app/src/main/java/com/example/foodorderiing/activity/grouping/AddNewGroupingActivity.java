package com.example.foodorderiing.activity.grouping;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.foodorderiing.Permition;
import com.example.foodorderiing.R;
import com.example.foodorderiing.activity.product.AddNewProductActivity;
import com.example.foodorderiing.database.DatabaseHelper;
import com.example.foodorderiing.database.dao.GroupingDao;
import com.example.foodorderiing.helper.Tools;
import com.example.foodorderiing.model.Grouping;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;


public class AddNewGroupingActivity extends AppCompatActivity {
    private static final int PICK_IMAGE = 100;
    private EditText editText_category;
    private ImageView imageView_show ,imageView_back;
    private FloatingActionButton fab;
    private DatabaseHelper db;
    private GroupingDao dao_grouping;
    private TextView tv_save , tv_cancel;
    private String name ,old_name, save;
    private Grouping g;
    private Uri uri;
    private String TIMEMILLISSECOND = String.valueOf(System.currentTimeMillis());


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
            editText_category.setText(g.name);
            imageView_show.setImageURI(Uri.parse(g.picture));
            imageView_back.setVisibility(View.GONE);
       }

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
//            Intent intent = new Intent();
//                intent.setType("image/*");
////                intent.setDataAndType(uri , "image/*");
//                intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
////                intent.setAction(Intent.ACTION_GET_CONTENT);
////                intent.setAction(Intent.ACTION_PICK);
//                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);

            Permition permition;
            permition = new Permition(100,getApplicationContext(), AddNewGroupingActivity.this);
            if(permition.checkPermission()) {
                CropImage.activity()
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .start(this);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                Log.e("iiii", "resultUri: " + resultUri );

                save = Tools.saveFile(Tools.getBytes(resultUri), new File(Environment.getExternalStorageDirectory() + "/DCIM/Foods"),TIMEMILLISSECOND + ".jpg");
//                save = Tools.saveFile(Tools.getBytes(resultUri), Environment.getExternalStorageDirectory(),"test.png");
                imageView_show.setImageURI(resultUri);
                imageView_back.setVisibility(View.GONE);
                Log.e("qqqqfile", "onActivityResult: " + save );
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
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
                            dao_grouping.insertGrouping(new Grouping(name, save));
                            Toast.makeText(getApplicationContext(), name + " با موفقیت به لیست اضافه شد ", Toast.LENGTH_LONG).show();
                            finish();
                        }
                }else {
                    g.name = name;
                    g.picture = save;
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