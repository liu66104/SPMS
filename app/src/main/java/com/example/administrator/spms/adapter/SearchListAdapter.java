package com.example.administrator.spms.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.View;

import com.example.administrator.spms.R;
import com.example.administrator.spms.base.CommonAdapter;
import com.example.administrator.spms.base.ViewHolder;
import com.example.administrator.spms.model.GetDeviceAlarmListBean;
import com.example.administrator.spms.model.SearchBean;

import java.util.List;

/**
 * Created by Administrator on 2016/11/14.
 */

public class SearchListAdapter extends CommonAdapter<GetDeviceAlarmListBean.DeviceListBean>{
    private Context context;
    private String stationID;
    private Handler handler;
    public SearchListAdapter(Handler handler, Context context, List<GetDeviceAlarmListBean.DeviceListBean>  data, int layoutId) {
        super(context, data, layoutId);
        this.context = context;
        this.handler= handler;
    }

    @Override
    public void convert(final ViewHolder holder, final GetDeviceAlarmListBean.DeviceListBean searchBean) {
        holder.setText(R.id.tv_equipment_two, searchBean.getDeviceName());
        if (searchBean.getDeviceAlarm().equals("1")){
            holder.setImageMipmap(R.id.iv_equipment_two,R.drawable.text_shape_red);

        }else if (searchBean.getDeviceAlarm().equals("0")){
            holder.setImageMipmap(R.id.iv_equipment_two, R.drawable.text_shape_green);
        }

        holder.setOnClickListener(R.id.iv_equipment_two, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (searchBean.getDeviceAlarm().equals("1")){
                    handler.sendMessage(handler.obtainMessage(11,searchBean.getDeviceID()));

//                    sendBroadcast();
//                    Intent intent2 = new Intent("MainActivity.broadcast.alert");
//                    intent2.putExtra("se", stationID);
//                    intent2.putExtra("id", searchBean.getDeviceID());
//                    Log.d("wodajdsmzxcz", searchBean.getDeviceID()+"@@@"+ stationID);
//                    LocalBroadcastManager.getInstance(context).sendBroadcast(intent2);

                }else if (searchBean.getDeviceAlarm().equals("0")){

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
