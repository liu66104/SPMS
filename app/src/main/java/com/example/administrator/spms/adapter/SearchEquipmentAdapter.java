package com.example.administrator.spms.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;

import com.example.administrator.spms.R;
import com.example.administrator.spms.base.CommonAdapter;
import com.example.administrator.spms.base.ViewHolder;
import com.example.administrator.spms.model.GetDeviceStatusListBean;
import com.example.administrator.spms.model.SearchBean;

import java.util.List;

/**
 * Created by Administrator on 2016/11/14.
 */

public class SearchEquipmentAdapter extends CommonAdapter<GetDeviceStatusListBean.DeviceListBean>{
    private Context context;
    public SearchEquipmentAdapter(Context context, List<GetDeviceStatusListBean.DeviceListBean> data, int layoutId) {
        super(context, data, layoutId);
        this.context = context;
    }

    @Override
    public void convert(ViewHolder holder, final GetDeviceStatusListBean.DeviceListBean bean) {
        holder.setText(R.id.tv_item_name, bean.getStationName());
        holder.setText(R.id.tv_item_num,bean.getTotalStr());
        holder.setText(R.id.tv_total,bean.getTotal());
        holder.setText(R.id.tv_item_normal,bean.getNormalStr());
        holder.setText(R.id.tv_normal_num,bean.getNormal());
        holder.setText(R.id.tv_item_fault,bean.getFaultStr());
        holder.setText(R.id.tv_item_fault_num,bean.getFault());

        holder.setOnClickListener(R.id.rl_search_equipment, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendBroadcast();
                Intent intent2 = new Intent("MainActivity.broadcast.alert");
                intent2.putExtra("se", bean.getStationID());
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
