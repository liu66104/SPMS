package com.example.administrator.spms.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;

import com.example.administrator.spms.R;
import com.example.administrator.spms.base.CommonAdapter;
import com.example.administrator.spms.base.ViewHolder;
import com.example.administrator.spms.model.GetDeviceStatusListBean;
import java.util.List;

/**
 * Created by Administrator on 2016/10/27.
 */

public class EquipmentAdapter extends CommonAdapter<GetDeviceStatusListBean.DeviceListBean>{
    private Context context;

    public EquipmentAdapter(Context context, List<GetDeviceStatusListBean.DeviceListBean> data, int layoutId) {
        super(context, data, layoutId);
        this.context = context;
    }

    @Override
    public void convert(ViewHolder holder, final GetDeviceStatusListBean.DeviceListBean s) {
        holder.setText(R.id.tv_item_name, s.getStationName());
        holder.setText(R.id.tv_item_num,s.getTotalStr());
        holder.setText(R.id.tv_total,s.getTotal());
        holder.setText(R.id.tv_item_normal,s.getNormalStr());
        holder.setText(R.id.tv_normal_num,s.getNormal());
        holder.setText(R.id.tv_item_fault,s.getFaultStr());
        holder.setText(R.id.tv_item_fault_num,s.getFault());

        holder.setOnClickListener(R.id.rl_equipment, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendBroadcast();
                Intent intent2 = new Intent("MainActivity.broadcast.alert");
                intent2.putExtra("se", s.getStationID());
                intent2.putExtra("id", "");
                intent2.putExtra("device","device");
                LocalBroadcastManager.getInstance(context).sendBroadcast(intent2);
            }
        });

    }

    //发送广播的方法
    public void sendBroadcast() {
        Intent intent1 = new Intent("MainActivity.broadcast");
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent1);

    }

}
