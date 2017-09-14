package com.example.weian.mynewscilent14.adapter;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.example.weian.mynewscilent14.R;
import com.example.weian.mynewscilent14.activity.NewActivity;
import com.example.weian.mynewscilent14.bean.Collect;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import db.MyDBHelper;

/**
 * Created by weian on 2017/7/5.
 */

public class CollectAdapter extends BaseAdapter {
    private List<Collect> collects;
    private LayoutInflater inflater;
    private MyDBHelper helper;
    public CollectAdapter(Context context, List<Collect> collects) {
        this.collects = collects;
        inflater = LayoutInflater.from(context);
        helper = new MyDBHelper(context);
        helper.getWritableDatabase();
    }

    @Override
    public int getCount() {
        return collects.size();
    }

    @Override
    public Object getItem(int position) {
        return collects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    class ViewHolder{
        private ImageView img;
        private TextView title,content;
        private LinearLayout linearLayout;
    }
    ViewHolder holder = null;
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView==null)
        {
            convertView = inflater.inflate(R.layout.list_view_layout,null);
            holder = new ViewHolder();
            holder.title = (TextView) convertView.findViewById(R.id.title);
            holder.content = (TextView) convertView.findViewById(R.id.url_3w);
            holder.img = (ImageView) convertView.findViewById(R.id.img);
            holder.linearLayout = (LinearLayout) convertView.findViewById(R.id.list_view_ll);


            convertView.setTag(holder);
        }else
        {
            holder = (ViewHolder) convertView.getTag();
        }
        final Collect collect = collects.get(position);
        final String url = collect.getUrl();
        holder.title.setText(collect.getTitle());
        holder.content.setText(collect.getSource());
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(inflater.getContext(), NewActivity.class);
                intent.putExtra("url",url);
                intent.putExtra("imgurl",collect.getImgurl());
                intent.putExtra("title",collect.getTitle());
                intent.putExtra("source",collect.getSource());
                inflater.getContext().startActivity(intent);
            }
        });
        holder.linearLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                SQLiteDatabase db = helper.getWritableDatabase();
                int position = 0;
                for (int i= 0;i<collects.size();i++){
                    if (collects.get(i).getTitle().equals(collect.getTitle())){
                        position = i;
                        break;
                    }
                }
                collects.remove(position);
                db.execSQL("delete from collect where title = '"+collect.getTitle()+"'");
                Toast.makeText(inflater.getContext(),collect.getTitle(),Toast.LENGTH_SHORT).show();
                notifyDataSetChanged();
                return false;
            }
        });
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.mipmap.nonews)
                .showImageOnFail(R.drawable.ic_stub)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();
        ImageLoader.getInstance().displayImage(collect.getImgurl(),holder.img,options);
        return convertView;
    }
}
