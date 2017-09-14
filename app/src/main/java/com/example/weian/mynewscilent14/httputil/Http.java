package com.example.weian.mynewscilent14.httputil;

import android.content.Context;
import android.util.Log;
import android.widget.ListView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.weian.mynewscilent14.adapter.MyListViewAdapter;
import com.example.weian.mynewscilent14.bean.News;
import com.example.weian.mynewscilent14.util.JSONParse;


import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by weian on 2017/6/20.
 */

public class Http {
//新闻的集合
    private static List<News> newList = new ArrayList<>();
    private static MyListViewAdapter adapter;

    /**
     * 下拉刷新获取JSON字符串
     *
     * @param context
     */
    public static void pullVolleyGetJson(final Context context, final ListView listView, String endUrl, final String id) {
        RequestQueue mQueue = Volley.newRequestQueue(context);
        JsonObjectRequest jor = new JsonObjectRequest(endUrl, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        //解析JSON
                        newList = JSONParse.jsonParse(id, response);
                        //给ListView添加适配器
                        adapter = new MyListViewAdapter(context,newList);
                        listView.setAdapter(adapter);
                        //Log.e("TAG",pullIndex+"<=====>"+response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("TAG", error + "");
            }
        });
        mQueue.add(jor);
    }

    /**
     * 上拉加载获取JSON字符串
     *
     * @param context
     */
    public static void pushVolleyGetJson(final Context context, String url, final String id) {
        RequestQueue mQueue = Volley.newRequestQueue(context);
        JsonObjectRequest jor = new JsonObjectRequest(url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        //判空
//                        if (list.size()==0)
//                        {
//                            Log.e("TAG", "nullIndex:"+index);
//                            index++;
//                            pushVolleyGetJson(context,listView);
//                        }else {
//                            index++;
//                                newList.addAll(list);
//                        }
                        newList.addAll(JSONParse.jsonParse(id,response));
                        Log.e("size", "====>" + newList.size());
                        //给ListView添加适配器
                        //MyListViewAdapter adapter = new MyListViewAdapter(newList,context);
                        //listView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                        //Log.e("TAG",index+"<=====>"+response);
                        Log.e("list", newList.toString());

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("TAG", error + "");
            }
        });
        mQueue.add(jor);
    }
}
