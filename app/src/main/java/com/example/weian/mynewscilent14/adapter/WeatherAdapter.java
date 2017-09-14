package com.example.weian.mynewscilent14.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.weian.mynewscilent14.R;
import com.example.weian.mynewscilent14.weather.TheWeather;

import java.util.List;

/**
 * Created by weian on 2017/7/9.
 */

public class WeatherAdapter extends BaseAdapter {
    private List<TheWeather> weathers;
    private LayoutInflater inflater;

    public WeatherAdapter(Context context,List<TheWeather> weathers) {
        this.weathers = weathers;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return weathers.size();
    }

    @Override
    public Object getItem(int position) {
        return weathers.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    class ViewHolder{
        private TextView time,weather,temperature;
    }
    ViewHolder holder = null;
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView==null)
        {
            convertView = inflater.inflate(R.layout.weather_item,null);
            holder = new ViewHolder();
            holder.temperature = (TextView) convertView.findViewById(R.id.weather_temperature);
            holder.time = (TextView) convertView.findViewById(R.id.weather_time);
            holder.weather = (TextView) convertView.findViewById(R.id.weather_weather);
            convertView.setTag(holder);
        }else
        {
            holder = (ViewHolder) convertView.getTag();
        }
        TheWeather weather = weathers.get(position);
        holder.weather.setText(weather.getType());
        holder.time.setText(weather.getDate());
        holder.temperature.setText(weather.getHigh()+"/"+weather.getLow());
        return convertView;
    }
}
