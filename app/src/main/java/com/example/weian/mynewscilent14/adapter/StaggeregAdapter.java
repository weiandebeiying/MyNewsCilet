package com.example.weian.mynewscilent14.adapter;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.example.weian.mynewscilent14.R;
import com.example.weian.mynewscilent14.application.MyApplication;
import com.example.weian.mynewscilent14.bean.Channel;

import java.util.ArrayList;
import java.util.List;

import db.MyDBHelper;

/**
 * Created by ZhangYang on 2017/6/28.
 */

public class StaggeregAdapter extends RecyclerView.Adapter<StaggeregAdapter.MyViewHolder>{
    private List<String> mDatas;
    private LayoutInflater inflater;
    private List<Integer> mHeight;
    private OnItemClickListener onItemClickListener;
    private MyApplication app;
    private MyDBHelper helper;
    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }
    //声明接口，回调点击方法
    public interface OnItemClickListener{
        //点击
        void onItemClickListener(View view, int position);
        //长按
        void onItemLongClickListener(View view, int position);
    }
    public StaggeregAdapter(Context context, List<String> datas)
    {
        inflater = LayoutInflater.from(context);
        mDatas = datas;
        app = (MyApplication) context.getApplicationContext();
        helper = new MyDBHelper(context);
        helper.getWritableDatabase();
        //设置item高度
        mHeight = new ArrayList<>();
        for (int i = 0;i<mDatas.size();i++)
        {
            mHeight.add((int) (100+ Math.random()*300));
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(inflater.inflate(R.layout.item_staggered_home,parent,false));
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        //给Item设定一个随机的高度
        ViewGroup.LayoutParams lp = holder.tv.getLayoutParams();
//        lp.height = mHeight.get(position);

        holder.tv.setLayoutParams(lp);
        holder.tv.setText(mDatas.get(position));
        if (mDatas.get(position).equals("头条")){
            holder.tv.setTextColor(Color.RED);
        }
        //绑定监听
        //如果设置了回调，则设置点击监听
        if (onItemClickListener != null)
        {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = holder.getLayoutPosition();
                    onItemClickListener.onItemClickListener(holder.itemView,pos);
                }
            });

            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    int pos = holder.getLayoutPosition();
                    onItemClickListener.onItemLongClickListener(holder.itemView,pos);
                    return false;
                }
            });
        }
    }
    //添加数据
    public void addData(String s)
    {
        if (!s.equals("头条")){
            mDatas.add(mDatas.size(),s);
//        mHeight.add((int) (100+ Math.random()*300));
            //通知item更新
            notifyItemInserted(mDatas.size()-1);
        }
    }
    public void addData1(String s)
    {
        if (!s.equals("头条")){
            mDatas.add(mDatas.size(),s);
//        mHeight.add((int) (100+ Math.random()*300));
            //通知item更新
            notifyItemInserted(mDatas.size()-1);
            SQLiteDatabase db = helper.getWritableDatabase();
            Channel channel = new Channel(s);
            List<Channel> channels = app.getChannel();
            channels.add(channels.size(),channel);
            app.setChannel(channels);
            db.execSQL("insert into channel(name) values('"+s+"')");
        }
    }
    //删除数据
    public void removeData(int position)
    {
        mDatas.remove(position);
        //通知item更新
        notifyItemRemoved(position);
    }
    //删除数据
    public void removeData1(int position)
    {
        if (position!=0){
            SQLiteDatabase db = helper.getWritableDatabase();
            List<Channel> channels = app.getChannel();
            channels.remove(position);
            app.setChannel(channels);
            db.execSQL("delete from channel where name = '"+mDatas.get(position)+"'");
            mDatas.remove(position);
            //通知item更新
            notifyItemRemoved(position);
        }
    }
    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tv;
        public MyViewHolder(View itemView) {
            super(itemView);
            tv = (TextView) itemView.findViewById(R.id.id_num);
        }
    }
}
