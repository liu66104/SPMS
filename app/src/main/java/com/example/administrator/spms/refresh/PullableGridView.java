package com.example.administrator.spms.refresh;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.GridView;

public class PullableGridView extends GridView implements Pullable {

    public PullableGridView(Context context) {
        super(context);

    }

    public PullableGridView(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    public PullableGridView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean canPullDown() {
        if (getCount() == 0) {
            // û��item��ʱ��Ҳ��������ˢ��
            return true;
        } else if (getFirstVisiblePosition() == 0
                && getChildAt(0).getTop() >= 0) {
            // ����������
            return true;
        } else
            return false;
    }

    @Override
    public boolean canPullUp() {
        if (getCount() == 0) {
            // û��item��ʱ��Ҳ������������
            return true;
        } else if (getLastVisiblePosition() == (getCount() - 1)) {
            // �����ײ���
            if (getChildAt(getLastVisiblePosition() - getFirstVisiblePosition()) != null
                    && getChildAt(
                    getLastVisiblePosition()
                            - getFirstVisiblePosition()).getBottom() <= getMeasuredHeight())
                return true;
        }
        return false;
    }


    private float xPosition, yPosition;// 手指触摸点的x轴坐标
    private final float MINIMUM_DISTANCE = 3.0f;// 手指移动的最小距离
    private int distance;// 根据屏幕密度计算出来的，手指移动的最小距离

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        // 先保存手指按下的x轴的坐标
        if (ev.getAction() == MotionEvent.ACTION_DOWN){
            xPosition = ev.getX();
            yPosition = ev.getY();
        }


        if (ev.getAction() == MotionEvent.ACTION_MOVE) {
            /*
             计算手指移动时的坐标跟按下的坐标之间的绝对值，如果超过给定的值，
             就认为viewpager需要滚动。通过调节distance的大小，可以改变滑动
             灵敏度
              */

            Log.e("fdfdx", Math.abs(ev.getY() - yPosition)+"");
            Log.e("fdfdx", Math.abs(ev.getX() - xPosition)+"");

            if (Math.abs(ev.getY() - yPosition) > Math.abs(ev.getX() - xPosition))
                return true;
            else// 意思就是：touch事件已经被PeopleViewPager自己消费了，不会传递到子控件
                return false;
        }
        // 其他情况，依旧保持默认的处理方法
        return super.onInterceptTouchEvent(ev);
    }

}
