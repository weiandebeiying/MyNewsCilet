package com.example.weian.mynewscilent14.application;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import com.example.weian.mynewscilent14.bean.Channel;
import com.example.weian.mynewscilent14.bean.Collect;
import com.mob.MobApplication;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;
import java.util.List;

import db.MyDBHelper;


/**
 * Created by weian on 2017/6/23.
 * 全局变量
 */

public class MyApplication extends MobApplication {

    //频道集合
    private List<Channel> channel = new ArrayList<>();
    //收藏集合
    private List<Collect> collect = new ArrayList<>();
    //天气所在地
    private String city = "成都";

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    /**
     * 获取收藏
     * @return
     */
    public List<Collect> getCollect() {
        return collect;
    }

    /**
     * 设置收藏
     * @param collect
     */
    public void setCollect(List<Collect> collect) {
        this.collect = collect;
    }

    /**
     * 获取频道
     * @return
     */
    public List<Channel> getChannel() {
        return channel;
    }

    /**
     * 设置频道
     * @param channel
     */
    public void setChannel(List<Channel> channel) {
        this.channel = channel;
    }
 
    @Override
    public void onCreate() {
        super.onCreate();
        ImageLoaderConfiguration configuration =new ImageLoaderConfiguration.Builder(this).writeDebugLogs().build();
        ImageLoader.getInstance().init(configuration);

        //从数据库中获取频道和收藏集合
        MyDBHelper helper = new MyDBHelper(this);
        helper.getWritableDatabase();
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from channel",null);
        while(cursor.moveToNext()){
            String name = cursor.getString(cursor.getColumnIndex("name"));
            Channel c = new Channel(name);
            channel.add(c);
        }
        //从数据库中获取收藏的新闻对象
        Cursor cursor1 = db.rawQuery("select * from collect",null);
        while(cursor1.moveToNext()){
            String imgurl = cursor1.getString(cursor1.getColumnIndex("imgurl"));
            String title = cursor1.getString(cursor1.getColumnIndex("title"));
            String source = cursor1.getString(cursor1.getColumnIndex("source"));
            String url = cursor1.getString(cursor1.getColumnIndex("url"));
            Collect co = new Collect(imgurl,title,source,url);
            collect.add(co);
        }
        cursor.close();
        cursor1.close();
        db.close();
    }


}
