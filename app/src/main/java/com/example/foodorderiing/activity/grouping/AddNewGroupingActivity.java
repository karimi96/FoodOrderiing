package com.example.foodorderiing.activity.grouping;

import android.Manifest;
import android.app.assist.AssistStructure;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewParent;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.foodorderiing.R;
import com.example.foodorderiing.database.DatabaseHelper;
import com.example.foodorderiing.database.dao.GroupingDao;
import com.example.foodorderiing.helper.App;
import com.example.foodorderiing.helper.Permition;
import com.example.foodorderiing.helper.Tools;
import com.example.foodorderiing.model.Grouping;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;


public class AddNewGroupingActivity extends AppCompatActivity {
    private EditText editText_category;
    private ImageView imageView_show, imageView_back;
    private FloatingActionButton fab;
    private DatabaseHelper db;
    private GroupingDao dao_grouping;
    private TextView tv_save, tv_cancel;
    private String name, old_name, save;
    private Grouping g;
    private String TIMEMILLISSECOND = String.valueOf(System.currentTimeMillis());


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_add_new_grouping);

        initDataBase();
        initID();
        initImage();

        if (getIntent().getExtras() != null) {
            String getGrouping = getIntent().getStringExtra("grouping");
            g = new Gson().fromJson(getGrouping, Grouping.class);
            old_name = g.name;
            editText_category.setText(g.name);
            try {
                imageView_show.setImageURI(Uri.parse(g.picture));
            } catch (Exception exception) {
                exception.printStackTrace();
            }
            imageView_back.setVisibility(View.GONE);
        }

        click_save();
        click_cancel();
        hideKeyBord();
    }


    private void initDataBase() {
        db = App.getDatabase();
        dao_grouping = db.groupingDao();
    }


    private void initID() {
        tv_save = findViewById(R.id.tv_save_group);
        tv_cancel = findViewById(R.id.tv_cancel_group);
        imageView_show = findViewById(R.id.image_show_g);
        imageView_back = findViewById(R.id.image_back_g);
        fab = findViewById(R.id.fab_add_g);
        editText_category = findViewById(R.id.et_get_category_grouping);
    }


    private void initImage() {
        fab.setOnClickListener(v -> {
            if (checkStoregPermition()) {
                CropImage.activity()
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .start(this);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
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
                Log.e("iiii", "resultUri: " + resultUri);
                save = Tools.saveFile(Tools.getBytes(resultUri), new File(Environment.getExternalStorageDirectory() + "/FoodOrdering/image"), TIMEMILLISSECOND + ".jpg");


//                File image = new File(Environment.getExternalStorageDirectory() + "/DCIM/Foods", TIMEMILLISSECOND + ".jpg");
//                BitmapFactory.Options bmOptions = new BitmapFactory.Options();
//                Bitmap bitmap = BitmapFactory.decodeFile(image.getAbsolutePath(),bmOptions);
//                bitmap = Bitmap.createScaledBitmap(bitmap, getWallpaperDesiredMinimumWidth(),getWallpaperDesiredMinimumHeight(),true);
//                imageView_show.setImageBitmap(bitmap);


                imageView_show.setImageURI(resultUri);
                imageView_back.setVisibility(View.GONE);
                Log.e("qqqqfile", "onActivityResult: " + save);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }


    public void click_save() {
        tv_save.setOnClickListener(v -> {
            name = editText_category.getText().toString();
            if (g == null) {
                if (imageView_show.getDrawable() == null) {
                    Toast.makeText(getApplicationContext(), "لطفا یک عکس انتخاب کنید!!!", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(name)) {
                    Toast.makeText(AddNewGroupingActivity.this, "فیلد مورد نظر را پرکنید!!!", Toast.LENGTH_LONG).show();

                } else if (dao_grouping.getOneName(name) != null) {
                    Toast.makeText(AddNewGroupingActivity.this, "این نام وجود دارد", Toast.LENGTH_LONG).show();

                } else {
                    dao_grouping.insertGrouping(new Grouping(name, save));
                    Toast.makeText(getApplicationContext(), name + " با موفقیت به لیست اضافه شد ", Toast.LENGTH_LONG).show();
                    finish();
                }
            } else {

                if(save == null){
//                if(imageView_show.getDrawable() == null){
                    g.picture = g.picture;
                } else if (imageView_show.getDrawable() == null){
                    g.picture = "";
                } else{
                    g.picture = save;
                }
                    g.name = name;
                    dao_grouping.updateGrouping(g);
                    Toast.makeText(getApplicationContext(), old_name + " با موفقیت به " + name + " تغییر کرد", Toast.LENGTH_LONG).show();
                    finish();
            }
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        });
    }


    public void click_cancel() {
        tv_cancel.setOnClickListener(v -> {
            finish();
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        });
    }


    private void hideKeyBord() {
        Tools.hideKeyBord(editText_category, this);
    }


    public Boolean checkStoregPermition() {
        String[] permission = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
        if (ContextCompat.checkSelfPermission(this, permission[0]) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(AddNewGroupingActivity.this, new String[]{permission[0]}, 200);
        } else if (ContextCompat.checkSelfPermission(this, permission[1]) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(AddNewGroupingActivity.this, new String[]{permission[1]}, 200);
        } else {
            return true;
        }
        return false;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 200) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                Toast.makeText(this, "Storage Permission Granted", Toast.LENGTH_SHORT).show();

            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage(" دسترسی به مجوزها ");
                builder.setPositiveButton("برو به تنظیمات", (dialog, which) -> {
                    dialog.cancel();
                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    Uri uri = Uri.fromParts("package", getPackageName(), null);
                    intent.setData(uri);
                    startActivityForResult(intent, 200);
                });
                builder.setNegativeButton("بستن", (dialog, which) -> {
                    dialog.cancel();
//                        finish();
                });
                builder.show();
            }
        }
    }


}