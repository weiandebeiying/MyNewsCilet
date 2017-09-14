package com.example.weian.mynewscilent14.util;

import android.graphics.Bitmap;
import android.util.Log;
import android.util.LruCache;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by ZhangYang on 2017/6/21.
 * 负责图片的缓存
 */

public class BitmapCache {
    private LruCache<String,Bitmap> mCatch;
    private ExecutorService es;
    public BitmapCache()
    {
        //设定线程池的最大线程数
        es = Executors.newFixedThreadPool(5);
        //设定缓存最大内存大小为当前应用程序的最大内存的1/8
        int maxSize = (int) (Runtime.getRuntime().maxMemory()/8);
        mCatch = new LruCache<String,Bitmap>(maxSize){
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getRowBytes()*value.getHeight();
            }
            //一旦缓存超出了最大内存，我们就要回收之前的数据来释放内存
            @Override
            protected void entryRemoved(boolean evicted, String key, Bitmap oldValue, Bitmap newValue) {
                super.entryRemoved(evicted, key, oldValue, newValue);
                if (evicted && oldValue!=null)
                {
                    //回收之前(使用频率较低)的数据
                    oldValue.recycle();
                }
            }
        };
    }
    //从缓存中获取图片
    private Bitmap getBitmapFromLruCatch(String imgUrl)
    {
        return mCatch.get(imgUrl);
    }
    //将请求到的网络图片加入到内存缓存中
    private  void putBitmap(String url,Bitmap bitmap)
    {
        Log.e("cache", url+";"+bitmap );
        mCatch.put(url,bitmap);
    }
    //从本地获取图片
    private Bitmap getBitmapFromLocal(String imgUrl)
    {
        Bitmap bitmap = BitMapUtil.getBitmap(imgUrl);
        if (bitmap!=null)
        {
            //将Bitmap加入到LruCatch缓存
            putBitmap(imgUrl,bitmap);
        }
        return bitmap;
    }
    //从网络获取图片
    private Bitmap getBitmapFromNetwork(String imgUrl)
    {
        try {
            URL url = new URL(imgUrl);
            URLConnection conn = url.openConnection();
            conn.setReadTimeout(6000);
            conn.setConnectTimeout(6000);
            InputStream is = conn.getInputStream();
            //保存图片到本地
            BitMapUtil.saveBitMap(is,imgUrl);
            //从本地获取图片对象
            Bitmap bitmap = BitMapUtil.getBitmap(imgUrl);
            Log.e("imageLoader", "getBitmapFromNetwork: "+imgUrl );
            //关闭输入流
            is.close();
            return bitmap;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    //给ImageView添加图片内容
    public void displayImg(String imgUrl, ImageView imageView)
    {
        imageView.setTag(imgUrl);
        //先从LruCatch缓存中获取Bitmap
        Bitmap bitmap = getBitmapFromLruCatch(imgUrl);
        if (bitmap!=null)
        {
            if (imgUrl.equals(imageView.getTag())){
                imageView.setImageBitmap(bitmap);
                Log.e("imageLoader", "LruCache: "+imgUrl );
            }
            return;
        }
        /**
         * 如果LruCatch缓存中不存在地址对应的Bitmap对象，那么我们就用线程池执行一个线程任务，来加载图片。
         */
        getBitmapRunnable runnable = new getBitmapRunnable(imgUrl,imageView);
        es.execute(runnable);
    }
    //从本地和网络中获取图片
    class getBitmapRunnable implements Runnable
    {
        private String imgsrc;
        private ImageView imageView;

        public getBitmapRunnable(String imgsrc, ImageView imageView) {
            this.imgsrc = imgsrc;
            this.imageView = imageView;
        }

        @Override
        public void run() {
            //从本地获取Bitmap对象
            Bitmap bitmap = getBitmapFromLocal(imgsrc);
            if (bitmap!=null)
            {
               if (imgsrc.equals(imageView.getTag())){
                   //更新ImageView
                   showBitmapOnUiThread(imageView,bitmap);
                   Log.e("imageLoader", "local: "+imgsrc );
               }
                return;
            }
            //从网络获取Bitmap对象
            bitmap = getBitmapFromNetwork(imgsrc);
            if (bitmap!=null)
            {
                if (imgsrc.equals(imageView.getTag())){
                    //更新ImageView
                    showBitmapOnUiThread(imageView,bitmap);
                }
            }
        }
    }
    //更新ImageView
    private void showBitmapOnUiThread(final ImageView imageView, final Bitmap bitmap)
    {
        imageView.post(new Runnable() {
            @Override
            public void run() {
                imageView.setImageBitmap(bitmap);
            }
        });
    }
}
