package com.example.foodorderiing.activity.grouping;

import androidx.appcompat.app.AppCompatActivity;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;
import com.example.foodorderiing.R;
import com.example.foodorderiing.database.DatabaseHelper;
import com.example.foodorderiing.database.dao.GroupingDao;
import com.example.foodorderiing.model.Grouping;

public class AddNewGroupingActivity extends AppCompatActivity {
    EditText editText_category;
    DatabaseHelper db;
    GroupingDao dao_grouping;
    VideoView videoView;
    TextView tv_save , tv_cancel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_grouping);

        build_videoView();
        db = DatabaseHelper.getInstance(getApplicationContext());
        dao_grouping = db.groupingDao();
        editText_category = findViewById(R.id.et_get_category_grouping);

        click_save();
        click_cancel();

    }


    public void build_videoView(){
        videoView = findViewById(R.id.vedio_grouping);
        String path = "android.resource://com.example.foodorderiing/"+R.raw.lemen;
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

    public void click_save(){
        tv_save = findViewById(R.id.tv_save_group);
        tv_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
// اگه تکراری بود چک شود
                String name = editText_category.getText().toString();

                if(TextUtils.isEmpty(name)){

                    Toast.makeText(AddNewGroupingActivity.this, "فیلد مورد نظر را پرکنید!!!", Toast.LENGTH_SHORT).show();

                }else {
                    Grouping grouping = new Grouping(name);
                    dao_grouping.insertGrouping(grouping);
                    db.close();
                    finish();
                }

            }
        });
    }


    public void click_cancel(){
        tv_cancel = findViewById(R.id.tv_cancel_group);
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
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