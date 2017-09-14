package com.example.weian.mynewscilent14.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import com.example.weian.mynewscilent14.MainActivity;
import com.example.weian.mynewscilent14.R;
import com.example.weian.mynewscilent14.adapter.GuidePagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by weian on 2017/7/10.
 * 引导界面
 */

public class GuideActivity extends Activity {
    private ViewPager viewPager;
    private List<View> views;
    private int[] images;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.guide_activity);
        initViews();
    }
    //初始化ViewPager
    private void initViews() {
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        images = new int[]{R.mipmap.guide_1,R.mipmap.guide_2,R.mipmap.guide_3};
        views = new ArrayList<>();
        for (int i = 0;i<images.length;i++)
        {
            ImageView imageView = new ImageView(this);
            if (i != images.length)
            {
                imageView.setImageResource(images[i]);
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            }
            views.add(imageView);
        }
        //创建适配器，初始化数据
        final GuidePagerAdapter adapter = new GuidePagerAdapter(views);
        viewPager.setAdapter(adapter);
        //添加监听
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                //如果ViewPager选中在最后一页，就跳转Activity
                if (position == adapter.getCount()-1)
                {
                    startActivity(new Intent(GuideActivity.this,MainActivity.class));
                    finish();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}
