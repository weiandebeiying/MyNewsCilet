package com.example.weian.mynewscilent14.util;

import android.util.Log;


import com.example.weian.mynewscilent14.bean.News;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by weian on 2017/6/20.
 */

public class JSONParse {

    public static List<News> jsonParse(String id, JSONObject jsonObject){
        Gson gson = new Gson();
        List<News> news = new ArrayList<>();
        try {
            JSONArray ja = jsonObject.getJSONArray(id);
            for (int i = 0;i<ja.length();i++){
                news.add(gson.fromJson(ja.getJSONObject(i).toString(),News.class));
            }
            Log.e("sss", "jsonParse----news: "+news);
            return news;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
