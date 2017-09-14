package com.example.weian.mynewscilent14;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.weian.mynewscilent14.activity.ChoiceCityActivity;
import com.example.weian.mynewscilent14.application.MyApplication;
import com.example.weian.mynewscilent14.fragment.CollectFragment;
import com.example.weian.mynewscilent14.fragment.HomeFragment;
import com.example.weian.mynewscilent14.fragment.VideoFragment;
import com.example.weian.mynewscilent14.fragment.WeatherFragment;
import com.example.weian.mynewscilent14.weather.TheWeather;
import com.example.weian.mynewscilent14.weather.TodayWeather;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.sharesdk.onekeyshare.OnekeyShare;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private LinearLayout homeImg,videoImg,weatherImgLa,collectImg,menuLayout;
    private TextView homeTV,videoTV,weatherTV,collentTV;
    private ImageView homeImage,videoImage,weatherImage,collectImage;
    private Fragment collectF,homeF,videoF,weatherF;
    private Toolbar toolbar;
    private FragmentManager fm;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private String homeTitle = "首页";
    private TodayWeather todayWeather;
    private static List<TheWeather> theWeathers;
    private TextView cityTV,temperatureTV,dataTV,aqiTV;
    private ImageView weatherImg;
    private LinearLayout exit,setting;
    private MyApplication app;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        registerBoradcastReceiver();
        init();
    }

    private void init() {
        app = (MyApplication) getApplication();
        exit = (LinearLayout) findViewById(R.id.exit);
        setting = (LinearLayout) findViewById(R.id.setting);
        menuLayout = (LinearLayout) findViewById(R.id.menu_layout);
        weatherImg = (ImageView) findViewById(R.id.weather_img);
        dataTV = (TextView) findViewById(R.id.date);
        temperatureTV = (TextView) findViewById(R.id.temperature);
        cityTV = (TextView) findViewById(R.id.city);
        aqiTV = (TextView) findViewById(R.id.aqi);
        weatherGetJson();
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        homeTV = (TextView) findViewById(R.id.home_tv);
        videoTV = (TextView) findViewById(R.id.video_tv);
        weatherTV = (TextView) findViewById(R.id.weather_tv);
        collentTV = (TextView) findViewById(R.id.collecct_tv);
        homeImage = (ImageView) findViewById(R.id.home_img);
        videoImage = (ImageView) findViewById(R.id.video_img);
        weatherImage = (ImageView) findViewById(R.id.weather_img_mian);
        collectImage = (ImageView) findViewById(R.id.collecct_img);
        homeImg = (LinearLayout) findViewById(R.id.home_page);
        videoImg = (LinearLayout) findViewById(R.id.video);
        weatherImgLa = (LinearLayout) findViewById(R.id.weather_layout);
        collectImg = (LinearLayout) findViewById(R.id.my_collect);
        //-------------------------------------------------------------------

        fm = getSupportFragmentManager();
        collectF = new CollectFragment();
        homeF = new HomeFragment();
        videoF = new VideoFragment();
        weatherF = new WeatherFragment();
        fm.beginTransaction().add(R.id.dl_container,collectF).commit();
        fm.beginTransaction().add(R.id.dl_container,videoF).commit();
        fm.beginTransaction().add(R.id.dl_container,weatherF).commit();
        fm.beginTransaction().add(R.id.dl_container,homeF).commit();
        fm.beginTransaction().show(homeF).hide(weatherF).hide(collectF).hide(videoF).commit();
        toolbar = (Toolbar) findViewById(R.id.toolBar);
        toolbar.setTitle("首页");
        setSupportActionBar(toolbar);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, 0, 0);
        drawerToggle.syncState();
        drawerLayout.addDrawerListener(drawerToggle);
        drawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                View mContent = drawerLayout.getChildAt(0);
                View mMenu = drawerView;
                mContent.setPivotX(0);
                mContent.setPivotY(mContent.getHeight() / 2);
                mContent.setTranslationX(mMenu.getWidth() * slideOffset);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                toolbar.setTitle("菜单");
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                toolbar.setTitle(homeTitle);
            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });
        //----------------------------------------------------------------
        homeImg.setOnClickListener(this);
        videoImg.setOnClickListener(this);
        weatherImgLa.setOnClickListener(this);
        collectImg.setOnClickListener(this);
        exit.setOnClickListener(this);
        setting.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == homeImg){
            fm.beginTransaction().show(homeF).hide(weatherF).hide(collectF).hide(videoF).commit();
            homeImage.setImageDrawable(getResources().getDrawable(R.drawable.home_red));
            videoImage.setImageDrawable(getResources().getDrawable(R.drawable.video_player));
            weatherImage.setImageDrawable(getResources().getDrawable(R.drawable.sun1));
            collectImage.setImageDrawable(getResources().getDrawable(R.drawable.like));
            homeTV.setTextColor(getResources().getColor(R.color.red));
            videoTV.setTextColor(getResources().getColor(R.color.black));
            weatherTV.setTextColor(getResources().getColor(R.color.black));
            collentTV.setTextColor(getResources().getColor(R.color.black));
            toolbar.setTitle("首页");
            homeTitle = "首页";
        }else if (v == videoImg){
            fm.beginTransaction().show(videoF).hide(weatherF).hide(collectF).hide(homeF).commit();
            homeImage.setImageDrawable(getResources().getDrawable(R.drawable.home));
            videoImage.setImageDrawable(getResources().getDrawable(R.drawable.video_player_red));
            weatherImage.setImageDrawable(getResources().getDrawable(R.drawable.sun1));
            collectImage.setImageDrawable(getResources().getDrawable(R.drawable.like));
            homeTV.setTextColor(getResources().getColor(R.color.black));
            videoTV.setTextColor(getResources().getColor(R.color.red));
            weatherTV.setTextColor(getResources().getColor(R.color.black));
            collentTV.setTextColor(getResources().getColor(R.color.black));
            toolbar.setTitle("视频");
            homeTitle = "视频";
        }else if (v == weatherImgLa){
            fm.beginTransaction().show(weatherF).hide(homeF).hide(collectF).hide(videoF).commit();
            homeImage.setImageDrawable(getResources().getDrawable(R.drawable.home));
            videoImage.setImageDrawable(getResources().getDrawable(R.drawable.video_player));
            weatherImage.setImageDrawable(getResources().getDrawable(R.drawable.sun_red));
            collectImage.setImageDrawable(getResources().getDrawable(R.drawable.like));
            homeTV.setTextColor(getResources().getColor(R.color.black));
            videoTV.setTextColor(getResources().getColor(R.color.black));
            weatherTV.setTextColor(getResources().getColor(R.color.red));
            collentTV.setTextColor(getResources().getColor(R.color.black));
            toolbar.setTitle("天气");
            homeTitle = "天气";
        }else if (v == collectImg){
            fm.beginTransaction().show(collectF).hide(weatherF).hide(homeF).hide(videoF).commit();
            homeImage.setImageDrawable(getResources().getDrawable(R.drawable.home));
            videoImage.setImageDrawable(getResources().getDrawable(R.drawable.video_player));
            weatherImage.setImageDrawable(getResources().getDrawable(R.drawable.sun1));
            collectImage.setImageDrawable(getResources().getDrawable(R.drawable.like_red));
            homeTV.setTextColor(getResources().getColor(R.color.black));
            videoTV.setTextColor(getResources().getColor(R.color.black));
            weatherTV.setTextColor(getResources().getColor(R.color.black));
            collentTV.setTextColor(getResources().getColor(R.color.red));
            toolbar.setTitle("收藏");
            homeTitle = "收藏";
        }else if (v == setting){
            startActivity(new Intent(this, ChoiceCityActivity.class));
        }else if (v == exit){
            finish();
            System.exit(0);
        }
    }
    //-----------------------------------------------------------------------------------------
    public void weatherGetJson(){
        theWeathers = new ArrayList<>();
        RequestQueue mQueue = Volley.newRequestQueue(this);
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
                                theWeathers.add(gson.fromJson(ja.getJSONObject(i).toString(),TheWeather.class));
                            }
                            Log.e("GetWeather",theWeathers.get(0).toString() );
                            todayWeather = new TodayWeather(city+"/"+theWeathers.get(0).getType(),"空气质量:"+aqi,
                                    (theWeathers.get(0).getHigh()+"/"+theWeathers.get(0).getLow()).replace("高温","").replace("低温",""),
                                    theWeathers.get(0).getDate());
                            Log.e("GetWeather",theWeathers.toString() );
                            Log.e("GetWeather",todayWeather.toString());
                            cityTV.setText(todayWeather.getCity());
                            temperatureTV.setText(todayWeather.getTemperature());
                            aqiTV.setText(todayWeather.getAqi());
                            dataTV.setText(todayWeather.getTime());
                            switch (theWeathers.get(0).getType()){
                                case "晴":
                                    weatherImg.setImageDrawable(getResources().getDrawable(R.drawable.weather_q));
                                    menuLayout.setBackgroundColor(getResources().getColor(R.color.warmbule));
                                    break;
                                case "小雨":
                                    weatherImg.setImageDrawable(getResources().getDrawable(R.drawable.weather_xy));
                                    menuLayout.setBackgroundColor(getResources().getColor(R.color.warmbule));
                                    break;
                                case "阴":
                                    weatherImg.setImageDrawable(getResources().getDrawable(R.drawable.weather_y));
                                    menuLayout.setBackgroundColor(getResources().getColor(R.color.coldbule));
                                    break;
                                case "多云":
                                    weatherImg.setImageDrawable(getResources().getDrawable(R.drawable.weather_dy));
                                    menuLayout.setBackgroundColor(getResources().getColor(R.color.warmyello));
                                    break;
//                                case "小雨":
//                                    weatherImg.setImageDrawable(getResources().getDrawable(R.drawable.weather_q));
//                                    menuLayout.setBackgroundColor(getResources().getColor(R.color.warmyello));
//                                    break;
//                                case "小雨":
//                                    weatherImg.setImageDrawable(getResources().getDrawable(R.drawable.weather_q));
//                                    menuLayout.setBackgroundColor(getResources().getColor(R.color.warmyello));
//                                    break;
//                                case "小雨":
//                                    weatherImg.setImageDrawable(getResources().getDrawable(R.drawable.weather_q));
//                                    menuLayout.setBackgroundColor(getResources().getColor(R.color.warmyello));
//                                    break;
//                                case "小雨":
//                                    weatherImg.setImageDrawable(getResources().getDrawable(R.drawable.weather_q));
//                                    menuLayout.setBackgroundColor(getResources().getColor(R.color.warmyello));
//                                    break;
//                                case "小雨":
//                                    weatherImg.setImageDrawable(getResources().getDrawable(R.drawable.weather_q));
//                                    menuLayout.setBackgroundColor(getResources().getColor(R.color.warmyello));
//                                    break;
//                                case "小雨":
//                                    weatherImg.setImageDrawable(getResources().getDrawable(R.drawable.weather_q));
//                                    menuLayout.setBackgroundColor(getResources().getColor(R.color.warmyello));
//                                    break;
                            }
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
    }
    //--------------使用onKeyDown()干掉他--------------

    //记录用户首次点击返回键的时间
    private long firstTime=0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK && event.getAction()==KeyEvent.ACTION_DOWN){
            if (System.currentTimeMillis()-firstTime>2000){
                Toast.makeText(MainActivity.this,"再按一次退出程序--->onKeyDown",Toast.LENGTH_SHORT).show();
                firstTime=System.currentTimeMillis();
            }else{
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver(){
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if(action.equals("com.weian.city")){
                weatherGetJson();
            }
        }

    };

    public void registerBoradcastReceiver(){
        IntentFilter myIntentFilter = new IntentFilter();
        myIntentFilter.addAction("com.weian.city");
        //注册广播
        registerReceiver(mBroadcastReceiver, myIntentFilter);
    }
}
