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
import com.example.weian.mynewscilent14.application.MyApplication;

import java.util.List;

/**
 * Created by weian on 2017/7/11.
 */

public class CityListAdapter extends BaseAdapter {
    private List<String> list;
    private LayoutInflater inflater;
    private MyApplication app;

    public CityListAdapter(Context context, List<String> list) {
        this.list = list;
        inflater = LayoutInflater.from(context);
        app = (MyApplication) context.getApplicationContext();
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    class ViewHolder {
        private TextView textView;
    }
    ViewHolder holder = null;
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView==null)
        {
            convertView = inflater.inflate(R.layout.city_item,null);
            holder = new ViewHolder();
            holder.textView = (TextView) convertView.findViewById(R.id.city_tv);
            convertView.setTag(holder);
        }else
        {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.textView.setText(list.get(position));
        return convertView;
    }
}
