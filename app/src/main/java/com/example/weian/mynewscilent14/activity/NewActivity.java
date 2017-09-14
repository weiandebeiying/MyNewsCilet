package com.example.weian.mynewscilent14.activity;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.weian.mynewscilent14.R;
import com.example.weian.mynewscilent14.application.MyApplication;
import com.example.weian.mynewscilent14.bean.Collect;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cn.sharesdk.onekeyshare.OnekeyShare;
import db.MyDBHelper;

/**
 * Created by weian on 2017/7/4.
 * 新闻详情界面
 */

public class NewActivity extends Activity {
    private LinearLayout linearLayout;
    private ImageView img, collect,share;
    private MyApplication app;
    private List<Collect> collects;
    private MyDBHelper helper;
    private List<String> news;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.web_avtivity_layout);
        init();
    }
//初始话控件
    private void init() {
        Intent intent = getIntent();
        final String url = intent.getStringExtra("url");
        final String title = intent.getStringExtra("title");
        final String source = intent.getStringExtra("source");
        final String imgurl = intent.getStringExtra("imgurl");
        helper = new MyDBHelper(this);
        helper.getWritableDatabase();
        app = (MyApplication) getApplication();
        collects = app.getCollect();
        img = (ImageView) findViewById(R.id.back);
        collect = (ImageView) findViewById(R.id.collect);
        if (!isCollect(title)){
            Glide.with(NewActivity.this).load(R.drawable.like).into(collect);
        }else {
            Glide.with(NewActivity.this).load(R.drawable.like1).into(collect);
        }
        share  = (ImageView) findViewById(R.id.share);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (url!=null){
                    showShare(title,url,news);
                }else {
                    Toast.makeText(NewActivity.this,"网页无效",Toast.LENGTH_SHORT).show();
                }
            }
        });
        //收藏监听
        collect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isCollect(title)){
                    Glide.with(NewActivity.this).load(R.drawable.like1).into(collect);
                    collects.add(new Collect(imgurl,title,source,url));
                    SQLiteDatabase db = helper.getWritableDatabase();
                    db.execSQL("insert into collect(imgurl,title,source,url)" +
                            " values('" +imgurl+
                            "','" +title+
                            "','" +source+
                            "','" +url+
                            "')");
                    Toast.makeText(NewActivity.this,"收藏成功",Toast.LENGTH_SHORT).show();
                }else {
                    Glide.with(NewActivity.this).load(R.drawable.like).into(collect);
                    int position = 0;
                    for (int i= 0;i<collects.size();i++){
                        if (collects.get(i).getTitle().equals(title)){
                            position = i;
                            break;
                        }
                    }
                    collects.remove(position);
                    SQLiteDatabase db = helper.getWritableDatabase();
                    db.execSQL("delete from collect where title = '"+title+"'");
                    Toast.makeText(NewActivity.this,"取消收藏成功"+title,Toast.LENGTH_SHORT).show();
                }

            }

        });
        linearLayout = (LinearLayout) findViewById(R.id.news_layout);
        if (url != null) {
            //解析新闻内容
            final Handler mHandler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
                    news = (List<String>) msg.obj;
                    Log.e("aasdasd", news.toString());
                    TextView title = new TextView(NewActivity.this);
                    title.setText(news.get(0));
                    title.setTextColor(Color.BLACK);
                    Log.e("asaf", "handleMessage: " + news.get(0));
                    title.setTextSize(30);
                    title.setGravity(Gravity.CENTER);
                    linearLayout.addView(title);
                    TextView time = new TextView(NewActivity.this);
                    time.setText(news.get(1));
                    time.setTextColor(Color.BLACK);
                    time.setTextSize(15);
                    time.setGravity(Gravity.RIGHT);
                    linearLayout.addView(time);
                    for (int i = 2; i < news.size(); i++) {
                        if (!news.get(i).isEmpty() && news.get(i).contains("http://")) {
                            ImageView img = new ImageView(NewActivity.this);
                            final String img_url = news.get(i);
                            img.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent1 = new Intent(NewActivity.this, ImageActivity.class);
                                    intent1.putExtra("url", img_url);
                                    startActivity(intent1);
                                }
                            });
                            img.setLayoutParams(new LinearLayout.LayoutParams(
                                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                            Glide.with(NewActivity.this).load(img_url).into(img);
                            linearLayout.addView(img);
                        } else if (!news.get(i).isEmpty() && !news.get(i).contains("http://")) {
                            TextView tv = new TextView(NewActivity.this);
                            tv.setText(news.get(i));
                            tv.setTextSize(18);
                            tv.setTextColor(Color.BLACK);
                            linearLayout.addView(tv);
                        }
                        TextView tv1 = new TextView(NewActivity.this);
                        tv1.setText("       ");
                        tv1.setTextSize(10);
                        tv1.setTextColor(Color.BLACK);
                        linearLayout.addView(tv1);
                    }
                }
            };
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        List<String> newMsg = new ArrayList<String>();
                        Document document = Jsoup.connect(url).get();
                        //---------title----------
                        String title = document.getElementsByTag("h1").text();
                        newMsg.add(title);
                        //---------时间-------------
                        if (document.getElementsByClass("post_time_source").text().length() > 0) {
                            String time = document.getElementsByClass("post_time_source").text();
                            newMsg.add(time);
                        } else if (document.getElementsByClass("article-author").text().length() > 0) {
                            String time = document.getElementsByClass("article-author").text();
                            time = time + " " + document.getElementsByClass("article-timestamp").text();
                            newMsg.add(time);
                        } else if (document.getElementsByClass("info").text().length() > 0) {
                            String time = document.getElementsByClass("info").text();
//                        time = time+" "+document.getElementsByClass("article-timestamp").text();
                            newMsg.add(time);
                        }
                        if (document.getElementsByClass("post_text").size() > 0) {
                            Elements mainElements = document.getElementsByClass("post_text");
                            Log.e("sss", "run: ---------------------------------");
                            for (int i = 0; i < mainElements.size(); i++) {
                                Elements newElements = mainElements.get(i).getElementsByTag("p");
                                for (int j = 0; j < newElements.size(); j++) {
                                    if (newElements.get(j).text().isEmpty()) {
                                        String img = newElements.get(j).getElementsByTag("img").attr("src");
                                        img = img.split("\\?")[0];
                                        newMsg.add(img);
                                    } else {
                                        String newWorld = newElements.get(j).text();
                                        newMsg.add(newWorld);
                                        Log.e("title", newWorld);
                                    }
                                }
                            }
                        } else if (document.getElementsByClass("clearfix").size() > 0) {
                            Elements mainElements = document.getElementsByClass("clearfix");
                            Log.e("asfasf", mainElements.toString());
                            Log.e("sss", "run: ---------------------------------");
                            for (int i = 0; i < mainElements.size(); i++) {
                                Elements newElements = mainElements.get(i).getElementsByTag("p");
                                for (int j = 0; j < newElements.size(); j++) {
                                    if (newElements.get(j).text().isEmpty()) {
                                        String imgUrl = newElements.get(j).getElementsByTag("img").attr("src");
                                        imgUrl = imgUrl.split("\\?")[0];
                                        int num = 0;
                                        for (int q = 0; q < newMsg.size(); q++) {
                                            if (!imgUrl.equals(newMsg.get(q))) {
                                                num++;
                                            }
                                        }
                                        if (num == newMsg.size()) {
                                            newMsg.add(imgUrl);
                                        }

                                    } else {
                                        String newWorld = newElements.get(j).text();
                                        int num = 0;
                                        for (int q = 0; q < newMsg.size(); q++) {
                                            if (!newWorld.equals(newMsg.get(q))) {
                                                num++;
                                            }
                                        }
                                        if (num == newMsg.size()) {
                                            newMsg.add(newWorld);
                                        }
                                        Log.e("title", newWorld);
                                    }
                                }
                            }
                        }
                        Log.e("title", newMsg.toString());
                        Message mag = new Message();
                        mag.obj = newMsg;
                        mHandler.sendMessage(mag);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        } else {
            ImageView imageView = new ImageView(this);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            Glide.with(this).load(R.mipmap.error404).into(imageView);
            linearLayout.addView(imageView);
        }
    }
    //判断是否已被收藏
    private boolean isCollect(String title) {
        for (int i = 0;i<collects.size();i++){
            if (collects.get(i).getTitle().equals(title)){
                return true;
            }
        }
        return false;
    }
    //第三方分享
    private void showShare(String title,String url,List<String> news) {
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间等使用
        oks.setTitle("每日新闻");
        // titleUrl是标题的网络链接，QQ和QQ空间等使用
        oks.setTitleUrl(url);
        // text是分享文本，所有平台都需要这个字段
        oks.setText(getWorld(news));
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl(url);
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("来自新闻客户端");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite(getString(R.string.app_name));
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl(url);
        // 启动分享GUI
        oks.show(this);
    }
    private String getWorld(List<String> news){
        StringBuffer sb = new StringBuffer();
        for (int i = 0;i<news.size();i++){
            if (!news.get(i).contains("http")){
                sb.append(news.get(i));
            }
        }
        return sb.toString();
    }
}
