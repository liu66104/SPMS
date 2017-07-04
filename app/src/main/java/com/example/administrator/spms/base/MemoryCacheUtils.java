package com.example.administrator.spms.base;

import android.graphics.Bitmap;

import java.lang.ref.SoftReference;
import java.util.HashMap;

/**
 * Created by Administrator on 2016/11/29.
 */

public class MemoryCacheUtils {

    //  private HashMap<String, Bitmap> hash;
    private HashMap<String, SoftReference<Bitmap>> hash;

    // 写内存缓存
    public void setMemoryCache(String url, Bitmap bitmap) {
//      if (hash == null) {
//          hash = new HashMap<String, Bitmap>();
//      }
//      hash.put(url, bitmap);
        if(hash == null){
            hash = new HashMap<String, SoftReference<Bitmap>>();
        }
        //使用软引用把Bitmap包装起来
        SoftReference<Bitmap> soft = new SoftReference<Bitmap>(bitmap);
        hash.put(url, soft);
    }

    // 读内存缓存
    public Bitmap getMemoryCache(String url) {
//      if (hash != null && hash.containsKey(url)) {
//          Bitmap bitmap = hash.get(url);
//          return bitmap;
//      }

        if(hash!=null && hash.containsKey(url)){
            SoftReference<Bitmap> soft = hash.get(url);
            Bitmap bitmap = soft.get();
            return bitmap;
        }
        return null;
    }



}
