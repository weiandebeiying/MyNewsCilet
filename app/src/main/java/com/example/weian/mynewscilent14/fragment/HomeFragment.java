package com.example.weian.mynewscilent14.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.weian.mynewscilent14.R;
import com.example.weian.mynewscilent14.activity.AddActivity;
import com.example.weian.mynewscilent14.adapter.MyFragmentPagerAdapter;
import com.example.weian.mynewscilent14.application.MyApplication;
import com.example.weian.mynewscilent14.bean.Channel;

import java.util.ArrayList;
import java.util.List;

import db.MyDBHelper;

/**
 * Created by weian on 2017/7/7.
 */

public class HomeFragment extends Fragment {
    public static boolean isRefresh;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private LinearLayout linearLayout;
    private MyApplication app;
    private List<Fragment> fragments;
    private MyFragmentPagerAdapter adapter;
    private List<Channel> list;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment,container,false);
        tabLayout = (TabLayout) view.findViewById(R.id.tab_layout);
        viewPager = (ViewPager) view.findViewById(R.id.view_pager);
        linearLayout = (LinearLayout) view.findViewById(R.id.add_img);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddActivity.class);
                String[] arr = new String[list.size()];
                for (int i = 0; i < list.size(); i++) {
                    arr[i] = list.get(i).getName();
                    Log.e("asf", "onClick: " + arr[i]);
                }
                intent.putExtra("频道", arr);
                getActivity().startActivity(intent);
            }
        });

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
    }

    private void init() {
        app = (MyApplication) getActivity().getApplication();
        list = app.getChannel();
        fragments = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            Bundle bundle = new Bundle();
            bundle.putString("text", list.get(i).getName());
            Log.e("text", "init:==================== " + list.get(i));
            MyFragment myFragment = new MyFragment();
            myFragment.setArguments(bundle);
            fragments.add(myFragment);
        }
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        for (int i = 0; i < list.size(); i++) {
            tabLayout.addTab(tabLayout.newTab().setText(list.get(i).getName()));
        }
        List<String> list1 = changeList(list);
        adapter = new MyFragmentPagerAdapter(getChildFragmentManager(), list1, fragments);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }
    private List<String> changeList(List<Channel> list) {
        List<String> list1 = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            list1.add(list.get(i).getName());
        }
        return list1;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isRefresh){
            init();
            isRefresh = false;
        }
    }
}