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
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.weian.mynewscilent14.R;
import com.example.weian.mynewscilent14.adapter.VideoAdapter;
import com.example.weian.mynewscilent14.bean.Video;
import com.example.weian.mynewscilent14.httputil.Http;
import com.example.weian.mynewscilent14.url.MyUrlMap;
import com.example.weian.mynewscilent14.url.Url;
import com.example.weian.mynewscilent14.view.SwipeRefreshView;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by weian on 2017/7/9.
 */

public class MyVideoFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, SwipeRefreshView.OnLoadListener{
    private ListView listView;
    //对应频道
    private String channelName;
    //频道ID集合
    private Map<String, String> url;
    //上拉加载和下拉刷新控件
    private SwipeRefreshView refreshView;
    private int index;
    private List<Video> videos;
    private Gson gson;
    private VideoAdapter adapter;
    private boolean isFirst = true;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.my_fragment_layout, container, false);
        listView = (ListView) view.findViewById(R.id.news_list_view);
        refreshView = (SwipeRefreshView) view.findViewById(R.id.swipe_refresh_layout);
        gson = new Gson();
        refreshView.setOnRefreshListener(this);
        refreshView.setOnLoadListener(this);
        Log.e("ssss", channelName+"-------------------onCreateView: ");
        return view;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        url = new MyUrlMap().getVideoMap();
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
    }
    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            public void run() {
                videos = new ArrayList<>();
                initVideoData();
                //加载完数据后设置为不刷新状态，将下拉进度条收起
                refreshView.setRefreshing(false);
            }
        }, 0);
    }

    @Override
    public void onLoad() {
        if (index<30){
            index+=10;
            new Handler().postDelayed(new Runnable() {
                public void run() {
                    initVideoData();
                    //加载完成后设置为不加载状态，将加载进度收起来
                    refreshView.setLoading(false);
                }
            },500);
        }
        else {
            Toast.makeText(getContext(),"已经到底了!",Toast.LENGTH_SHORT).show();
            refreshView.setLoading(false);
        }

    }
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser){
            url = new MyUrlMap().getVideoMap();
            Bundle bundle = getArguments();
            index = 0;
            channelName = bundle != null ? bundle.getString("text") : "失败！";
            isFirst = true;
            onRefresh();
        }
    }

    private void initVideoData() {
        RequestQueue mQueue = Volley.newRequestQueue(getContext());
        JsonObjectRequest jor = new JsonObjectRequest("http://c.3g.163.com/nc/video/list/"+url.get(channelName)+"/n/"+index+"-10.html", null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        if (isFirst){
                            videos = new ArrayList<>();
                        }
                        try {
                            JSONArray jr = response.getJSONArray(url.get(channelName));
                            for (int i = 0;i<jr.length();i++){
                                videos.add(gson.fromJson(jr.getJSONObject(i).toString(),Video.class));
                            }
                            if (isFirst){
                                adapter = new VideoAdapter(getContext(),videos);
                                listView.setAdapter(adapter);
                                isFirst = false;
                                Log.e("asdasdasda", videos.toString() );
                            }else {
                                adapter.notifyDataSetChanged();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        mQueue.add(jor);
    }

}
