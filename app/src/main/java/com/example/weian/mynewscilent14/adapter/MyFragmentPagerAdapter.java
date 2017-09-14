package com.example.weian.mynewscilent14.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * 作者：胡晓强
 * 2017.06.10
 * view pager适配器
 */

public class MyFragmentPagerAdapter extends FragmentStatePagerAdapter {
    //fragment列表
    private List<Fragment> fragments;
    //tab名列表
    private List<String> tabs;

    public MyFragmentPagerAdapter(FragmentManager fm, List<String> tabs, List<Fragment> fragments) {
        super(fm);
        this.tabs = tabs;
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
    //用来显示Tab上的名字

    @Override
    public CharSequence getPageTitle(int position) {
        return tabs.get(position);
    }
}
