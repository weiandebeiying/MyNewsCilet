package com.example.weian.mynewscilent14.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.weian.mynewscilent14.R;
import com.example.weian.mynewscilent14.adapter.CityListAdapter;
import com.example.weian.mynewscilent14.application.MyApplication;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by weian on 2017/7/9.
 * 所在城市选择
 */

public class ChoiceCityActivity extends AppCompatActivity{
    private static String ACTION_NAME = "com.weian.city";
    private List<String> cities;
    private ListView listView;
    private TextView textView;
    private MyApplication app;
    private CityListAdapter adapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choice_city);
        init();

    }

    private void init() {
        initData();
        listView = (ListView) findViewById(R.id.city_list);
        textView = (TextView) findViewById(R.id.present_city);
        app = (MyApplication) getApplication();
        adapter = new CityListAdapter(this,cities);
        textView.setText(app.getCity());
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                app.setCity(cities.get(position));
                Intent mIntent = new Intent(ACTION_NAME);
                mIntent.putExtra("city", app.getCity());

                //发送广播
                sendBroadcast(mIntent);
                finish();
            }
        });
    }

    private void initData() {
        cities = new ArrayList<>();
        cities.add("成都");
        cities.add("北京");
        cities.add("上海");
        cities.add("广州");
        cities.add("深圳");
        cities.add("珠海");
        cities.add("佛山");
        cities.add("南京");
        cities.add("苏州");
        cities.add("厦门");
        cities.add("长沙");
        cities.add("福州");
        cities.add("杭州");
        cities.add("武汉");
        cities.add("青岛");
        cities.add("西安");
        cities.add("太原");
        cities.add("石家庄");
        cities.add("沈阳");
        cities.add("重庆");
        cities.add("天津");
        cities.add("南宁");
    }
}
