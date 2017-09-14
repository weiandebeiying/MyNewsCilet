package com.example.weian.mynewscilent14.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.weian.mynewscilent14.R;
import com.example.weian.mynewscilent14.adapter.WeatherAdapter;
import com.example.weian.mynewscilent14.application.MyApplication;
import com.example.weian.mynewscilent14.weather.TheWeather;
import com.google.gson.Gson;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by weian on 2017/7/7.
 */

public class WeatherFragment extends Fragment {
    private ListView listView;
    private List<TheWeather> weathers;
    private TextView textView;
    private WeatherAdapter adapter;
    private MyApplication app;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.weather_fragment,container,false);
        listView = (ListView) view.findViewById(R.id.weather_listview);
        textView = (TextView) view.findViewById(R.id.city_name);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        app = (MyApplication) getActivity().getApplication();
        registerBoradcastReceiver();
        init();
    }

    private void init() {
        weathers = new ArrayList<>();
        RequestQueue mQueue = Volley.newRequestQueue(getContext());
        final JsonObjectRequest jor = new JsonObjectRequest("http://wthrcdn.etouch.cn/weather_mini?city="+app.getCity(), null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("GetWeather", response.toString());
                        try {
                            Gson gson = new Gson();
                            JSONObject jsonObject = response.getJSONObject("data");
                            Log.e("GetWeather", jsonObject.toString() );
                            //获取城市
                            String city = jsonObject.getString("city");
                            Log.e("GetWeather",city );
                            //获取空气质量
                            String aqi = jsonObject.getString("aqi");
                            Log.e("GetWeather",aqi );
                            JSONArray ja = jsonObject.getJSONArray("forecast");
                            Log.e("GetWeather",ja.toString() );
                            Log.e("GetWeather",ja.length()+"");
                            for (int i =0;i<ja.length();i++){
                                weathers.add(gson.fromJson(ja.getJSONObject(i).toString(),TheWeather.class));
                            }
                            adapter = new WeatherAdapter(getContext(),weathers);
                            listView.setAdapter(adapter);
                            textView.setText(city);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        mQueue.add(jor);
        Log.e("asdadasd", weathers.toString());

    }
    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver(){
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if(action.equals("com.weian.city")){
                init();
            }
        }

    };

    public void registerBoradcastReceiver(){
        IntentFilter myIntentFilter = new IntentFilter();
        myIntentFilter.addAction("com.weian.city");
        //注册广播        
        getActivity().registerReceiver(mBroadcastReceiver, myIntentFilter);
    }
}
