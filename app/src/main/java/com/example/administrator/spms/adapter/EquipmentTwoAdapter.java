package com.example.administrator.spms.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;

import com.example.administrator.spms.R;
import com.example.administrator.spms.base.CommonAdapter;
import com.example.administrator.spms.base.ViewHolder;
import com.example.administrator.spms.model.GetDeviceAlarmListBean;

import java.util.List;

/**
 * Created by Administrator on 2016/10/28.
 */

public class EquipmentTwoAdapter extends CommonAdapter<GetDeviceAlarmListBean.DeviceListBean>{
    private Context context;
    private String stationID;
    public EquipmentTwoAdapter(String stationID, Context context, List<GetDeviceAlarmListBean.DeviceListBean> data, int layoutId) {
        super(context, data, layoutId);
        this.context = context;
        this.stationID = stationID;
    }

    @Override
    public void convert(ViewHolder holder, final GetDeviceAlarmListBean.DeviceListBean s) {
        holder.setText(R.id.tv_equipment_two, s.getDeviceName());

        if (s.getDeviceAlarm().equals("0")){
            holder.setImageMipmap(R.id.iv_equipment_two, R.drawable.text_shape_green);

        }else{
            holder.setImageMipmap(R.id.iv_equipment_two,R.drawable.text_shape_red);
        }

        //只有红点才能点击跳转到报警页
        holder.setOnClickListener(R.id.iv_equipment_two, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (s.getDeviceAlarm().equals("0")){

                }else{
                    sendBroadcast();
                    Intent intent2 = new Intent("MainActivity.broadcast.alert");
                    intent2.putExtra("se", stationID);
                    intent2.putExtra("id", s.getDeviceID());
                    intent2.putExtra("device","device");
                    LocalBroadcastManager.getInstance(context).sendBroadcast(intent2);
                }

            }
        });

    }


    //发送广播的方法
    public void sendBroadcast() {
        Intent intent1 = new Intent("MainActivity.broadcast");
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent1);

    }
}
