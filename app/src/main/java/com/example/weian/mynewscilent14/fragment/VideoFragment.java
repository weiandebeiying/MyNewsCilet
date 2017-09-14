package com.example.weian.mynewscilent14.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.weian.mynewscilent14.R;
import com.example.weian.mynewscilent14.adapter.MyFragmentPagerAdapter;
import com.example.weian.mynewscilent14.url.MyUrlMap;
import com.nostra13.universalimageloader.utils.L;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;

/**
 * Created by weian on 2017/7/7.
 */

public class VideoFragment extends Fragment {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private List<String> title;
    private List<Fragment> fragments;
    private MyFragmentPagerAdapter adapter;
    private Map<String,String> videoMap;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.video_fragment,container,false);
        tabLayout = (TabLayout) view.findViewById(R.id.video_tablayout);
        viewPager = (ViewPager) view.findViewById(R.id.video_viewpager);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
    }

    private void init() {
        videoMap = new MyUrlMap().getVideoMap();
        title = new ArrayList<>();
        title.add("精品");
        title.add("热点");
        title.add("搞笑");
        title.add("娱乐");
        fragments = new ArrayList<>();
        for (int i = 0;i<title.size();i++){
            Bundle bundle = new Bundle();
            bundle.putString("text",title.get(i));
            MyVideoFragment fragment = new MyVideoFragment();
            fragment.setArguments(bundle);
            fragments.add(fragment);
        }
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        for (int i = 0; i < title.size(); i++) {
            tabLayout.addTab(tabLayout.newTab().setText(title.get(i)));
        }
        adapter = new MyFragmentPagerAdapter(getChildFragmentManager(), title, fragments);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }
}
