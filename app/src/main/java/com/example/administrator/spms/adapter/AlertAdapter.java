package com.example.administrator.spms.adapter;

import android.content.Context;
import android.graphics.Color;
import android.widget.LinearLayout;

import com.example.administrator.spms.R;
import com.example.administrator.spms.base.CommonAdapter;
import com.example.administrator.spms.base.ViewHolder;
import com.example.administrator.spms.model.AlertBean;

import java.util.List;

/**
 * Created by Administrator on 2016/11/17.
 */

public class AlertAdapter extends CommonAdapter<AlertBean.RealTimeAlarmsInfoBean> {

    public AlertAdapter(Context context, List<AlertBean.RealTimeAlarmsInfoBean> data, int layoutId) {
        super(context, data, layoutId);
    }

    @Override
    public void convert(ViewHolder holder, AlertBean.RealTimeAlarmsInfoBean realTimeAlarmsInfoBean) {
        LinearLayout linearLayout = holder.getView(R.id.ll_alert);
        //布局颜色
        if (holder.getPosition() % 2 == 0) {
            linearLayout.setBackgroundColor(Color.parseColor("#9eb9d2"));
        } else {
            linearLayout.setBackgroundColor(Color.parseColor("#dadbdf"));
        }
        //判断字体颜色
        //红
        if (realTimeAlarmsInfoBean.getAckStatus().equals("1")) {
            holder.setTextColor(R.id.tv_alert_time, Color.parseColor("#ff0000"));
            holder.setTextColor(R.id.tv_alert_station, Color.parseColor("#ff0000"));
            holder.setTextColor(R.id.tv_alert_equipment, Color.parseColor("#ff0000"));
            holder.setTextColor(R.id.tv_alert_tagName, Color.parseColor("#ff0000"));
            holder.setTextColor(R.id.tv_alert_desc, Color.parseColor("#ff0000"));
            holder.setBackGround(R.id.v_alert,Color.WHITE);
            holder.setBackGround(R.id.v_alert2,Color.WHITE);

        }//绿
        else if (realTimeAlarmsInfoBean.getAckStatus().equals("0")){
            holder.setTextColor(R.id.tv_alert_time, Color.parseColor("#118210"));
            holder.setTextColor(R.id.tv_alert_station, Color.parseColor("#118210"));
            holder.setTextColor(R.id.tv_alert_equipment, Color.parseColor("#118210"));
            holder.setTextColor(R.id.tv_alert_tagName, Color.parseColor("#118210"));
            holder.setTextColor(R.id.tv_alert_desc, Color.parseColor("#118210"));
            holder.setBackGround(R.id.v_alert,Color.RED);
            holder.setBackGround(R.id.v_alert2,Color.RED);
        }
        //白
        else {
            holder.setTextColor(R.id.tv_alert_time, Color.parseColor("#ffffff"));
            holder.setTextColor(R.id.tv_alert_station, Color.parseColor("#ffffff"));
            holder.setTextColor(R.id.tv_alert_equipment, Color.parseColor("#ffffff"));
            holder.setTextColor(R.id.tv_alert_tagName, Color.parseColor("#ffffff"));
            holder.setTextColor(R.id.tv_alert_desc, Color.parseColor("#ffffff"));
            holder.setBackGround(R.id.v_alert,Color.GREEN);
            holder.setBackGround(R.id.v_alert2,Color.GREEN);
        }

        //添加textview的内容
        holder.setText(R.id.tv_alert_time, realTimeAlarmsInfoBean.getAlarmDateTime()+"");
        holder.setText(R.id.tv_alert_station, realTimeAlarmsInfoBean.getStationName()+"");
        holder.setText(R.id.tv_alert_equipment, realTimeAlarmsInfoBean.getDeviceName()+"");
        holder.setText(R.id.tv_alert_tagName, realTimeAlarmsInfoBean.getAlarmTagName()+"");
        holder.setText(R.id.tv_alert_desc, realTimeAlarmsInfoBean.getAlarmDesc()+"");

    }
}
