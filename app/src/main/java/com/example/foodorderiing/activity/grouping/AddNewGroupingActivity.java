package com.example.foodorderiing.activity.grouping;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.foodorderiing.R;
import com.example.foodorderiing.database.DatabaseHelper;
import com.example.foodorderiing.database.dao.GroupingDao;
import com.example.foodorderiing.model.Grouping;

public class AddNewGroupingActivity extends AppCompatActivity {
    EditText editText_name;
    DatabaseHelper db;
    GroupingDao dao;
    TextView tv_save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_grouping);

        db = DatabaseHelper.getInstance(getApplicationContext());
        dao = db.groupingDao();

        editText_name = findViewById(R.id.et_get_name_grouping);

        tv_save = findViewById(R.id.tv_save_group);
        tv_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editText_name.getText().toString();
                Grouping grouping = new Grouping(name);
                dao.insertGrouping(grouping);
                db.close();
                finish();
            }
        });


    }
}