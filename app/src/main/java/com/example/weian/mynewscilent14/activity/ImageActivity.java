package com.example.weian.mynewscilent14.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.weian.mynewscilent14.R;

/**
 * Created by weian on 2017/7/4.
 * 图片显示界面
 */

public class ImageActivity extends AppCompatActivity {
    private ImageView imageView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_activity);
        init();
    }

    private void init() {
        imageView = (ImageView) findViewById(R.id.activity_image);
        Intent intent = getIntent();
        String img_url = intent.getStringExtra("url");
        Glide.with(this).load(img_url).into(imageView);
        //点击关闭
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
