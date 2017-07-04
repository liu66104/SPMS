package com.example.administrator.spms.base;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * Created by Administrator on 2016/11/29.
 */

public class LocalCacheUtils {


    private static final String LOCAL_CACHE_PATH = Environment
            .getExternalStorageDirectory().getAbsolutePath()+"/zhxa_cache";

    //写本地缓存
    public void setLocalCache(String url,Bitmap bitmap) {
        File dir = new File(LOCAL_CACHE_PATH);
        if(!dir.exists() || !dir.isDirectory()){
            dir.mkdirs();//创建文件夹
        }

        try {
            String fileName = MD5WS.getMD5(url);//采用MD5加密文件名
            File cacheFile = new File(dir, fileName);
            // 参1:图片格式;参2:压缩比例0-100; 参3:输出流
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, new FileOutputStream(cacheFile));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //读本地缓存
    public Bitmap getLocalCache(String url) {
        try {
            File cacheFile = new File(LOCAL_CACHE_PATH, MD5WS.getMD5(url));
            if(cacheFile.exists()){
                Bitmap bitmap = BitmapFactory.decodeStream(new FileInputStream(cacheFile));
                return bitmap;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}
