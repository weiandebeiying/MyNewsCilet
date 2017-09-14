package com.example.weian.mynewscilent14.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;


import com.example.weian.mynewscilent14.R;
import com.example.weian.mynewscilent14.bean.News;
import com.example.weian.mynewscilent14.httputil.Http;
import com.example.weian.mynewscilent14.url.MyUrlMap;
import com.example.weian.mynewscilent14.url.Url;
import com.example.weian.mynewscilent14.view.SwipeRefreshView;

import java.util.List;
import java.util.Map;

/**
 * Created by weian on 2017/6/23.
 */

public class MyFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, SwipeRefreshView.OnLoadListener {
    public ListView listView;
    //新闻集合
    private List<News> news;
    //对应频道
    private String channelName;
    //频道ID集合
    private Map<String, String> url;
    //全局application
//    private MyApplication app;
    //上拉加载和下拉刷新控件
    private SwipeRefreshView refreshView;
    private int index;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.my_fragment_layout, container, false);
        listView = (ListView) view.findViewById(R.id.news_list_view);
        refreshView = (SwipeRefreshView) view.findViewById(R.id.swipe_refresh_layout);

        refreshView.setOnRefreshListener(this);
        refreshView.setOnLoadListener(this);
        Log.e("ssss", channelName+"-------------------onCreateView: ");
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        url = new MyUrlMap().getMap();
        Bundle bundle = getArguments();
        index = 0;
        channelName = bundle != null ? bundle.getString("text") : "失败！";
        super.onCreate(savedInstanceState);
        Log.e("ssss", channelName+"-------------------onCreate: ");

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.e("ssss", channelName+"-------------------onActivityCreated: ");
        initData();
    }

    private void initData() {
        String URL = Url.TopUrl + url.get(channelName) + "/" + 0 + Url.endUrl;
        Http.pullVolleyGetJson(getActivity(),listView,URL,url.get(channelName));

    }

    @Override
    public void onRefresh() {
        final String URL = Url.TopUrl + url.get(channelName) + "/" + 0 + Url.endUrl;
        new Handler().postDelayed(new Runnable() {
            public void run() {
                Http.pullVolleyGetJson(getActivity(), listView,URL,url.get(channelName));
                //加载完数据后设置为不刷新状态，将下拉进度条收起
                refreshView.setRefreshing(false);
            }
        }, 0);
    }

    @Override
    public void onLoad() {
        index+=20;
        final String URL = Url.TopUrl + url.get(channelName) + "/" + index + Url.endUrl;
        new Handler().postDelayed(new Runnable() {
            public void run() {
                Http.pushVolleyGetJson(getActivity(),URL,url.get(channelName));
                //加载完成后设置为不加载状态，将加载进度收起来
                refreshView.setLoading(false);
            }
        },1000);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser){
            url = new MyUrlMap().getMap();
            Bundle bundle = getArguments();
            index = 0;
            channelName = bundle != null ? bundle.getString("text") : "失败！";
            onRefresh();
        }
    }
}
