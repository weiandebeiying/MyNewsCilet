package com.example.weian.mynewscilent14.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.weian.mynewscilent14.R;
import com.example.weian.mynewscilent14.bean.Video;
import com.example.weian.mynewscilent14.view.CircleImageView;


import java.util.ArrayList;
import java.util.List;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

/**
 * Created by weian on 2017/7/7.
 */

public class VideoAdapter extends BaseAdapter {
    private List<Video> videos;
    private LayoutInflater inflater;

    public VideoAdapter(Context context, List<Video> videos) {
//        this.videos =  shourOne(videos);
        this.videos = videos;
        inflater = LayoutInflater.from(context);
    }

//    private List<Video> shourOne(List<Video> videos) {
//        List<Video> list = new ArrayList<>();
//        for (int i =0 ;i<videos.size();i++){
//            int q = videos.size()-i-1;
//            for (int j = i;j<videos.size();j++){
//                if (!videos.get(i).getMp4_url().equals(videos.get(j).getMp4_url())){
//                    q--;
//                }
//            }
//            if (q==0){
//                list.add(videos.get(i));
//            }
//        }
//        return list;
//    }

    @Override
    public int getCount() {
        return videos.size();
    }

    @Override
    public Object getItem(int position) {
        return videos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    class ViewHolder{
        private CircleImageView circleImageView;
        private JCVideoPlayerStandard player;
        private TextView tname,ptime;
    }
    ViewHolder holder = null;
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView==null)
        {
            Log.e("asdasdasda", "||||||"+videos.toString() );
            convertView = inflater.inflate(R.layout.video_item,null);
            holder = new ViewHolder();
            holder.circleImageView = (CircleImageView) convertView.findViewById(R.id.topic_icons);
            holder.player = (JCVideoPlayerStandard) convertView.findViewById(R.id.player_list_video);
            holder.ptime = (TextView) convertView.findViewById(R.id.ptime);
            holder.tname = (TextView) convertView.findViewById(R.id.tname);
            convertView.setTag(holder);
        }else
        {
            holder = (ViewHolder) convertView.getTag();
        }
        Video video = videos.get(position);
        boolean setUp = holder.player.setUp(video.getMp4_url(), JCVideoPlayer.SCREEN_LAYOUT_LIST, video.getTitle());
        if (setUp)
        {
            Glide.with(inflater.getContext()).load(video.getCover())
                    .into(holder.player.thumbImageView);
        }
        Glide.with(inflater.getContext()).load(video.getTopicImg()).into(holder.circleImageView);
        holder.tname.setText(video.getTopicName());
        holder.ptime.setText(video.getPtime());
        return convertView;
    }
}
