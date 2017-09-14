package com.example.weian.mynewscilent14.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * 作者：胡晓强
 * 2017.06.10
 * 引导页面的适配器
 */

public class GuidePagerAdapter extends PagerAdapter{
    private List<View> views;
    public GuidePagerAdapter(List<View> views)
    {
        this.views = views;
    }
    @Override
    public int getCount() {
        return views.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = views.get(position);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        View view = views.get(position);
        container.removeView(view);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }
}
