package com.example.administrator.spms.base;

import android.graphics.Bitmap;
import android.widget.ImageView;

/**
 * Created by Administrator on 2016/11/29.
 */

public class MyBitmapUtils {



    private NetCacheUtils mNetCacheUtil;//网络缓存工具类
    private LocalCacheUtils mLocalCacheUtil;//本地缓存工具类
    private MemoryCacheUtils mMemoryCacheUtil;//内存缓存工具类

    public MyBitmapUtils() {
        mMemoryCacheUtil = new MemoryCacheUtils();
        mLocalCacheUtil = new LocalCacheUtils();
        mNetCacheUtil = new NetCacheUtils(mLocalCacheUtil, mMemoryCacheUtil);
    }

    /**
     *
     * @param imageView
     *            要展示加载图片的ImageView
     * @param url
     *            加载图片的链接
     * @param resId
     *            默认图片的资源id
     */
    public void display(ImageView imageView, String url, int resId) {
        // 设置默认图片
        imageView.setImageResource(resId);

        Bitmap bitmap = null;
        // 0.先从内存加载，如果内存中有值
        bitmap = mMemoryCacheUtil.getMemoryCache(url);
        if (bitmap != null) {
            System.out.println("从内存中加载");
            imageView.setImageBitmap(bitmap);
            return;
        }

        // 1.先从本地加载，判断是否有本地缓存
        bitmap = mLocalCacheUtil.getLocalCache(url);
        if (bitmap != null) {
            System.out.println("从本地加载");
            imageView.setImageBitmap(bitmap);
            //写内存缓存
            mMemoryCacheUtil.setMemoryCache(url, bitmap);
            return;
        }

        // 2.从网络加载
        mNetCacheUtil.getBitmapFromNet(imageView, url);
        System.out.println("从网络加载");

    }


}
