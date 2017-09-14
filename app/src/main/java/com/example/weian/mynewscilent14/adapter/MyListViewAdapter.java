package com.example.weian.mynewscilent14.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.example.weian.mynewscilent14.R;
import com.example.weian.mynewscilent14.activity.NewActivity;
import com.example.weian.mynewscilent14.bean.News;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by weian on 2017/6/20.
 */

public class MyListViewAdapter extends BaseAdapter {
    private List list;
    private LayoutInflater inflater;
    //item的类型
    private final int VIEWTYPE_TOP = 0;
    private final int VIEWTYPE_CONTENT = 1;
    private final int VIEWTYPE_MID = 2;
    private final int VIEWTYPE_COUNT = 3;
    public MyListViewAdapter(Context context, List list) {
        this.list = list;
        inflater = LayoutInflater.from(context);
    }
    //设置item类型
    @Override
    public int getItemViewType(int position) {
        News n  = (News) list.get(position);
        if (n.getHasHead()==1 && n.getHasImg() == 1)
        {
            return VIEWTYPE_TOP;
        }
        if (n.getImgType() == 1)
        {
            return  VIEWTYPE_MID;
        }
        return VIEWTYPE_CONTENT;
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
    class ViewHolder{
        private ImageView img;
        private TextView title,content;
        private RelativeLayout layout;
        private TextView time;
    }
    ViewHolder holder = null;
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView==null)
        {
//            convertView = inflater.inflate(R.layout.list_view_layout,null);
            holder = new ViewHolder();
//            holder.title = (TextView) convertView.findViewById(R.id.title);
//            holder.content = (TextView) convertView.findViewById(R.id.url_3w);
//            holder.img = (ImageView) convertView.findViewById(R.id.img);
//            holder.linearLayout = (LinearLayout) convertView.findViewById(R.id.list_view_ll);
            switch (getItemViewType(position))
            {
                case VIEWTYPE_CONTENT:
                    convertView = inflater.inflate(R.layout.item,parent,false);
                    holder.title = (TextView) convertView.findViewById(R.id.title);
                    holder.content = (TextView) convertView.findViewById(R.id.content);
                    holder.time = (TextView) convertView.findViewById(R.id.time);
                    holder.img = (ImageView) convertView.findViewById(R.id.icon);
                    holder.layout = (RelativeLayout) convertView.findViewById(R.id.item_layout);
                    convertView.setTag(holder);
                    break;
                case VIEWTYPE_TOP:
                    convertView = inflater.inflate(R.layout.item_big,parent,false);
                    holder.title = (TextView) convertView.findViewById(R.id.title_big);
                    holder.content = (TextView) convertView.findViewById(R.id.resourse_big);
                    holder.time = (TextView) convertView.findViewById(R.id.count_big);
                    holder.img = (ImageView) convertView.findViewById(R.id.image_big);
                    holder.layout = (RelativeLayout) convertView.findViewById(R.id.item_big_layout);
                    convertView.setTag(holder);
                    break;
                case VIEWTYPE_MID:
                    convertView = inflater.inflate(R.layout.item_mid,parent,false);
                    holder.title = (TextView) convertView.findViewById(R.id.title_mid);
                    holder.content = (TextView) convertView.findViewById(R.id.resourse_mid);
                    holder.time = (TextView) convertView.findViewById(R.id.count_mid);
                    holder.img = (ImageView) convertView.findViewById(R.id.image_mid);
                    holder.layout = (RelativeLayout) convertView.findViewById(R.id.item_mid_layout);
                    convertView.setTag(holder);
                    break;
            }

            convertView.setTag(holder);
        }else
        {
            holder = (ViewHolder) convertView.getTag();
        }
        Log.e("zzzzzzzzz", "onClick: ----------------"+position );
        Log.e("zzzzzzzzz", "onClick: ----------------"+list.size() );
        final News n = (News) list.get(position);
        final String url = n.getUrl_3w();
        holder.title.setText(n.getTitle());
        holder.content.setText(n.getSource());
        holder.time.setText(getNumber(n.getReplyCount()));
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(inflater.getContext(), NewActivity.class);
                intent.putExtra("url",url);
                intent.putExtra("imgurl",n.getImgsrc());
                intent.putExtra("title",n.getTitle());
                intent.putExtra("source",n.getSource());
                inflater.getContext().startActivity(intent);
            }
        });
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.mipmap.nonews)
                .showImageOnFail(R.drawable.ic_stub)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();
        ImageLoader.getInstance().displayImage(n.getImgsrc(),holder.img,options);
        return convertView;
    }
    /**
     * 换算跟帖数字
     */
    private String getNumber(String str)
    {
        int i = 0;
        if (str == null)
        {

        }else
        {
            i = Integer.parseInt(str);
            if (i>9999)
            {
                float f = i/10000;
                return  f+"万跟帖";
            }
        }
        return i+"跟帖";
    }
}
