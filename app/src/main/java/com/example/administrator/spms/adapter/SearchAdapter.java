package com.example.administrator.spms.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;

import com.example.administrator.spms.R;
import com.example.administrator.spms.base.Base64ToBitmap;
import com.example.administrator.spms.base.CommonAdapter;
import com.example.administrator.spms.base.MyBitmap;
import com.example.administrator.spms.base.ViewHolder;
import com.example.administrator.spms.model.GetStationListBean;
import com.example.administrator.spms.model.SearchBean;

import java.util.List;

/**
 * Created by Administrator on 2016/6/15.
 */
public class SearchAdapter extends CommonAdapter<GetStationListBean.StationsBean> {
    private Context context;

    public SearchAdapter(Context context, List<GetStationListBean.StationsBean> data, int layoutId) {
        super(context, data, layoutId);
        this.context = context;
    }

    @Override
    public void convert(ViewHolder holder, final GetStationListBean.StationsBean bean) {
        holder.setText(R.id.tv_search_name, bean.getStationName());
        holder.setImageBitmap(R.id.iv_search_power, MyBitmap.zoomImage(Base64ToBitmap.stringtoBitmap(bean.getStationImg()), 200, 100));

        if (bean.getStationAlarm().equals("0")) {
            holder.setImageMipmap(R.id.iv_search_point, R.drawable.text_shape_green);

        } else {
            holder.setImageMipmap(R.id.iv_search_point,R.drawable.text_shape_red);

        }

        holder.setOnClickListener(R.id.iv_search_point, new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (bean.getStationAlarm().equals("0")) {

                } else {
                    sendBroadcast();
                    Intent intent2 = new Intent("MainActivity.broadcast.alert");
                    intent2.putExtra("se", bean.getStationID());
                    intent2.putExtra("id", "");
                    intent2.putExtra("device","all");
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
