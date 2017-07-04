package com.example.administrator.spms.base;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewGroup;

/**
 * Created by Administrator on 2016/12/13.
 */

public class MyViewPager extends ViewPager {
    private float xPosition, yPosition;// 手指触摸点的x轴坐标


    public MyViewPager(Context context) {
        super(context);
    }

    public MyViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    /**
     * 02. * @return 表示不进行事件的处理
     * 03. *
     * 04.
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        // TODO Auto-generated method stub

        // 先保存手指按下的x轴的坐标
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            xPosition = ev.getX();
            yPosition = ev.getY();
        }



        if (ev.getAction() == MotionEvent.ACTION_MOVE) {
            /*
             计算手指移动时的坐标跟按下的坐标之间的绝对值，如果超过给定的值，
             就认为viewpager需要滚动。通过调节distance的大小，可以改变滑动
             灵敏度
              */
            Log.e("ssdxzc",Math.abs(ev.getX() - xPosition)+"");
            Log.e("ssdxzc",Math.abs(ev.getY() - yPosition)+"");
            if (Math.abs(ev.getX() - xPosition) > Math.abs(ev.getY() - yPosition)){
                return true;

            }else {
                return false;
            }

        }
        return super.onInterceptTouchEvent(ev);
    }


}
