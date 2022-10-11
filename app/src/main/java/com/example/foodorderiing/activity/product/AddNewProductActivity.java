package com.example.foodorderiing.activity.product;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.foodorderiing.R;
import com.example.foodorderiing.database.DatabaseHelper;
import com.example.foodorderiing.database.dao.GroupingDao;
import com.example.foodorderiing.database.dao.ProductDao;
import com.example.foodorderiing.design.NumberTextWatcherForThousand;
import com.example.foodorderiing.helper.App;
import com.example.foodorderiing.helper.Tools;
import com.example.foodorderiing.model.Grouping;
import com.example.foodorderiing.model.Product;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;

public class AddNewProductActivity extends AppCompatActivity {
    private EditText et_name, et_price;
    private ImageView img_show, img;
    private AutoCompleteTextView autoTextView_grouing;
    private ArrayAdapter<String> adapter_autocomplete;
    private TextView tv_save, tv_cancel;
    private DatabaseHelper db;
    private ProductDao dao_product;
    private GroupingDao dao_grouping;
    private Product p = null;
    private FloatingActionButton fab;
    private String save;
    private String TIMEMILLISECOND = String.valueOf(System.currentTimeMillis());


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_add_new_product);

        initID();
        initPhoto();
        initDataBase();
        hideKeyBord();

        if (getIntent().getExtras() != null) {
            String getNameProduct = getIntent().getStringExtra("product");
            p = new Gson().fromJson(getNameProduct, Product.class);
            et_name.setText(p.name);
            autoTextView_grouing.setText(p.category);
            et_price.setText(p.price);
            img_show.setImageURI(Uri.parse(p.picture));
            img.setVisibility(View.GONE);
        }

        et_price.addTextChangedListener(new NumberTextWatcherForThousand(et_price));
        initAutoTextView();
        actionSave();
        actionCancel();
    }


    private void initDataBase() {
        db = App.getDatabase();
        dao_product = db.productDao();
        dao_grouping = db.groupingDao();
    }


    private void initID() {
        tv_save = findViewById(R.id.tv_save_product);
        et_name = findViewById(R.id.et_get_name_product);
        et_price = findViewById(R.id.et_get_price_product);
        autoTextView_grouing = findViewById(R.id.autoComplete_tv);
        tv_cancel = findViewById(R.id.tv_cancel_product);
        fab = findViewById(R.id.fab_add_p);
        img_show = findViewById(R.id.image_show_p);
        img = findViewById(R.id.pic);
    }


    private void initPhoto() {
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
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                save = Tools.saveFile(Tools.getBytes(resultUri), new File(Environment.getExternalStorageDirectory() + "/FoodOrdering/image"), TIMEMILLISECOND + ".jpg");
//                save = Tools.saveFile(Tools.getBytes(resultUri), new File(Environment.getExternalStorageDirectory() + "/DCIM/Foods"), TIMEMILLISECOND + ".jpg");
                img_show.setImageURI(resultUri);
                img.setVisibility(View.GONE);
                Log.e("qqqqfile", "onActivityResult: " + save);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }


    private void hideKeyBord() {
        Tools.hideKeyBord(et_name, this);
        Tools.hideKeyBord(et_price, this);
        Tools.hideKeyBord(autoTextView_grouing, this);
    }


    private void initAutoTextView() {
        adapter_autocomplete = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_dropdown_item_1line, dao_grouping.getname());
        autoTextView_grouing.setAdapter(adapter_autocomplete);
        autoTextView_grouing.setThreshold(0);
        autoTextView_grouing.setOnItemClickListener((parent, view, position, id) -> {
//                parent.getItemAtPosition(position)
        });

        autoTextView_grouing.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) autoTextView_grouing.showDropDown();
        });

        autoTextView_grouing.setOnClickListener(v -> {
            autoTextView_grouing.showDropDown();
        });
    }


    private void actionSave() {
        tv_save.setOnClickListener(v -> {
            String nameProduct = et_name.getText().toString();
            String categoryProduct = autoTextView_grouing.getText().toString();
            String priceProduct = et_price.getText().toString();

            if (p == null) {
                if (img_show.getDrawable() == null) {
                    Toast.makeText(getApplicationContext(), "لطفا یک عکس انتخاب کنید!!!", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(nameProduct) || TextUtils.isEmpty(categoryProduct) || TextUtils.isEmpty(priceProduct)) {
                    Toast.makeText(getApplicationContext(), "فیلد مورد نظر را پرکنید!!!", Toast.LENGTH_SHORT).show();

                } else {
                    if (dao_product.getOneName(nameProduct) != null) {
                        Toast.makeText(AddNewProductActivity.this, " این محصول وجود دارد ", Toast.LENGTH_SHORT).show();

                    } else if (dao_grouping.getOneName(categoryProduct) == null) {
                        dao_grouping.insertGrouping(new Grouping(categoryProduct, ""));

                    } else {
                        dao_product.insertProduct(new Product(nameProduct, categoryProduct, priceProduct, save));
                        Toast.makeText(getApplicationContext(), nameProduct + " با موفقیت به لیست اضافه شد ", Toast.LENGTH_LONG).show();
                        finish();
                    }
                }
            } else {
                p.name = nameProduct;
                p.category = categoryProduct;
                p.price = priceProduct;
                if(save == null) p.picture = p.picture;
                else  p.picture = save;
                Log.e("qqqq", "onClick: update product=" + p.product_id);
                dao_product.updateProduct(p);
                Toast.makeText(getApplicationContext(), " با موفقیت تغییر کرد ", Toast.LENGTH_LONG).show();
                finish();
            }
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        });
    }


    private void actionCancel() {
        tv_cancel.setOnClickListener(v -> {
            finish();
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        });
    }


    public Boolean checkStoregPermition() {
        String[] permission = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
        if (ContextCompat.checkSelfPermission(this, permission[0]) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(AddNewProductActivity.this, new String[]{permission[0]}, 200);
        } else if (ContextCompat.checkSelfPermission(this, permission[1]) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(AddNewProductActivity.this, new String[]{permission[1]}, 200);
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