package com.example.foodorderiing.activity.product;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.VideoView;

import com.example.foodorderiing.R;

public class AddNewProductActivity extends AppCompatActivity {

    VideoView videoView;
    Spinner spinner;
    ArrayAdapter<String> adapter;
    String[] sp = {"فست فود","سالاد","چلوها"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_product);


//        videoView = findViewById(R.id.vedio);
//        String path = "android.resource://com.example.foodorderiing/"+R.raw.haberger2;
//        Uri u = Uri.parse(path);
//        videoView.setVideoURI(u);
//        videoView.start();
//
//        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
//            @Override
//            public void onPrepared(MediaPlayer mp) {
//                mp.setLooping(true);
//            }
//        });


        spinner = findViewById(R.id.spinner);
        adapter = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,sp);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }













//    @Override
//    protected void onResume() {
//        videoView.resume();
//        super.onResume();
//    }
//
//    @Override
//    protected void onPause() {
//        videoView.suspend();
//        super.onPause();
//    }
//
//
//    @Override
//    protected void onDestroy() {
//        videoView.stopPlayback();
//        super.onDestroy();
//    }
}