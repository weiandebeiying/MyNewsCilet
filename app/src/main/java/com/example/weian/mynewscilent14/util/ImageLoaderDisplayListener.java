package com.example.weian.mynewscilent14.util;

import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by ZhangYang on 2017/6/30.
 * 图片加载监听事件
 */

public class ImageLoaderDisplayListener extends SimpleImageLoadingListener {
    public static List<String> displayedImages = Collections.synchronizedList(new LinkedList<String>());

    @Override
    public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
        if (loadedImage != null){
            ImageView imageView = (ImageView) view;
            boolean firstDisplay = !displayedImages.contains(imageUri);
            if (firstDisplay)
            {
                //设置隐藏动画500ms
                FadeInBitmapDisplayer.animate(imageView,500);
                //将图片Uri添加到集合中
                displayedImages.add(imageUri);
            }
        }
    }
}
