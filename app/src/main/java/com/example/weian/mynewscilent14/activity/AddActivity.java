package com.example.weian.mynewscilent14.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.weian.mynewscilent14.MainActivity;
import com.example.weian.mynewscilent14.R;
import com.example.weian.mynewscilent14.adapter.StaggeregAdapter;
import com.example.weian.mynewscilent14.application.MyApplication;
import com.example.weian.mynewscilent14.fragment.HomeFragment;
import com.example.weian.mynewscilent14.url.MyUrlMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by weian on 2017/6/22.
 * 频道添加页面
 */

public class AddActivity extends Activity {
    private RecyclerView recyclerView,recyclerView2;
    private StaggeregAdapter adapter,adapter2;
    private List<String> list,list2;
    private ImageView back;
    private MyApplication app;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_activity_layout);
        initData();

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView2 = (RecyclerView) findViewById(R.id.recyclerView2);
        back = (ImageView) findViewById(R.id.back_img);
        adapter = new StaggeregAdapter(this,list);
        adapter2 = new StaggeregAdapter(this,list2);
        app = (MyApplication) getApplication();


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(4,StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView2.setLayoutManager(new StaggeredGridLayoutManager(4,StaggeredGridLayoutManager.VERTICAL));
        recyclerView2.setAdapter(adapter2);
        recyclerView2.setItemAnimator(new DefaultItemAnimator());
        init();
    }

    private void init() {
        adapter.setOnItemClickListener(new StaggeregAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(View view, int position) {
                adapter.removeData1(position);
                HomeFragment.isRefresh = true;
                TextView textView = (TextView) view.findViewById(R.id.id_num);
                adapter2.addData((String) textView.getText());
            }

            @Override
            public void onItemLongClickListener(View view, int position) {

            }
        });
        adapter2.setOnItemClickListener(new StaggeregAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(View view, int position) {
                adapter2.removeData(position);
                HomeFragment.isRefresh = true;
                TextView textView = (TextView) view.findViewById(R.id.id_num);
                adapter.addData1((String) textView.getText());
            }

            @Override
            public void onItemLongClickListener(View view, int position) {

            }
        });
    }

    private void initData() {
        list = new ArrayList<>();
        Intent intent = getIntent();
        String[] arr = intent.getStringArrayExtra("频道");
        for (int i = 0;i<arr.length;i++){
            list.add(arr[i]);
        }
        list2 = new ArrayList<>();
        MyUrlMap map = new MyUrlMap();
        Map<String,String> mMap = map.getMap();
        for (String s:mMap.keySet()) {
            int q = 0;
            for (int i = 0 ;i<list.size();i++){
                if (!s.equals(list.get(i))){
                    q++;
                }
            }
            if (q == list.size()){
                list2.add(s);
            }
        }
    }

}
