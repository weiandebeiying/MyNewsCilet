package com.example.weian.mynewscilent14.url;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by weian on 2017/6/19.
 */

public class MyUrlMap {
    private Map<String,String> url;
    private Map<String,String> videoUrl;
    public Map getVideoMap(){
        videoUrl = new HashMap<>();
        videoUrl.put("热点","V9LG4B3A0");
        videoUrl.put("娱乐","V9LG4CHOR");
        videoUrl.put("搞笑","V9LG4E6VR");
        videoUrl.put("精品","00850FRB");
        return videoUrl;
    }
    public Map getMap(){
        url = new HashMap<>();
        url.put("头条","T1348647909107");
        url.put("足球","T1399700447917");
        url.put("娱乐","T1348648517839");
        url.put("体育","T1348649079062");
        url.put("财经","T1348648756099");
        url.put("科技","T1348649580692");
        url.put("电影","T1348648650048");
        url.put("汽车","T1348654060988");
        url.put("笑话","T1350383429665");
        url.put("游戏","T1348654151579");
        url.put("时尚","T1348650593803");
        url.put("情感","T1348650839000");
        url.put("精选","T1370583240249");
        url.put("电台","T1379038288239");
        url.put("nba","T1348649145984");
        url.put("数码","T1348649776727");
        url.put("移动","T1351233117091");
        url.put("彩票","T1356600029035");
        url.put("教育","T1348654225495");
        url.put("论坛","T1349837670307");
        url.put("旅游","T1348654204705");
        url.put("手机","T1348649654285");
        url.put("博客","T1349837698345");
        url.put("社会","T1348648037603");
        url.put("家居","T1348654105308");
        url.put("暴雪游戏","T1397016069906");
        url.put("CBA","T1348649475931");
        url.put("消息","T1371543208049");
        url.put("军事","T1348648141035");
        return url;
    }
}
