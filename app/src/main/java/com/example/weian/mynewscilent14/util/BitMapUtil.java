package com.example.weian.mynewscilent14.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by weian on 2017/6/21.
 */

public class BitMapUtil {
    //图片缓存路径
    private static final String CATCH_PATH = Environment.
            getExternalStorageDirectory().getPath()+"/image_catch/";
    //缓存图片
    public static void saveBitMap(InputStream is,String imgsrc){
        String filePath = CATCH_PATH+MD5(imgsrc);
        File file = new File(filePath);
        if (!file.getParentFile().exists()){
            file.getParentFile().mkdirs();
        }
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
            byte[] buff = new byte[1024*4];
            int len;
            while ((len = is.read(buff))!=-1){
                fos.write(buff,0,len);
            }
            is.close();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //获取本地图片
    public static Bitmap getBitmap(String imgsrc){
        String filePath = CATCH_PATH+MD5(imgsrc);
        File file = new File(filePath);
        if (file.exists()){
            Bitmap bitmap = BitmapFactory.decodeFile(filePath);
            if (bitmap!=null){
                return bitmap;
            }
        }
        return null;
    }
    //文件名加密（唯一且可用）
    private static String MD5(String imgsrc) {
        try {
            byte[] bytes = imgsrc.getBytes();
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(bytes);
            byte[] md = messageDigest.digest();
            StringBuffer sb = new StringBuffer();
            for (int i = 0;i<md.length;i++){
                int val = ((int)md[i])&0xff;
                if (val<16)
                    sb.append("0");
                    sb.append(Integer.toHexString(val));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
}
