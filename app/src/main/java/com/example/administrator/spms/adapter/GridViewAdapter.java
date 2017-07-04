package com.example.administrator.spms.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;
import com.example.administrator.spms.R;
import com.example.administrator.spms.base.Base64ToBitmap;
import com.example.administrator.spms.base.CommonAdapter;
import com.example.administrator.spms.base.MyBitmap;
import com.example.administrator.spms.base.ViewHolder;
import com.example.administrator.spms.model.GetStationListBean;
import java.util.List;

/**
 * Created by Administrator on 2016/10/31.
 */

public class GridViewAdapter extends CommonAdapter<GetStationListBean.StationsBean> {
    private Context context;
    private Handler handler;

    public GridViewAdapter(Context context, List<GetStationListBean.StationsBean> data, int layoutId,Handler handler) {
        super(context, data, layoutId);
        this.context = context;
        this.handler = handler;
    }

    @Override
    public void convert(ViewHolder holder, final GetStationListBean.StationsBean s) {

        holder.setText(R.id.tv_name_power, s.getStationName());
//        holder.setImageBitmap(R.id.iv_pic_power, Base64ToBitmap.stringtoBitmap(s.getStationImg()));
//        ImageView imageView = holder.getView(R.id.iv_pic_power);


//        myBitmapUtils.display(imageView, s.getStationImg(), R.mipmap.ic_launcher);
        if (s.getStationImg()== null || s.getStationImg().equals("")||s.getStationImg().equals(null)){


        }else {


            holder.setImageBitmap(R.id.iv_pic_power, MyBitmap.zoomImage(Base64ToBitmap.stringtoBitmap(s.getStationImg()), 500, 250));
        }





        if (s.getStationAlarm().equals("0")) {
            holder.setImageMipmap(R.id.iv_point_station_item, R.drawable.text_shape_green1);
        } else {
            holder.setImageMipmap(R.id.iv_point_station_item, R.drawable.text_shape_red1);

        }

        //只有红点才能点击跳转到报警页
        holder.setOnClickListener(R.id.iv_point_station_item, new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (s.getStationAlarm().equals("0")) {

                } else {
                    sendBroadcast();
                    Intent intent2 = new Intent("MainActivity.broadcast.alert");
                    intent2.putExtra("se", s.getStationID());
                    intent2.putExtra("id", "");
                    intent2.putExtra("device","all");
                    LocalBroadcastManager.getInstance(context).sendBroadcast(intent2);
                }


            }
        });

       holder.setOnClickListener(R.id.tv_name_power, new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               handler.sendMessage(handler.obtainMessage(6, s.getStationID()));
           }
       });

        holder.setOnClickListener(R.id.iv_pic_power, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handler.sendMessage(handler.obtainMessage(6, s.getStationID()));
            }
        });
    }


    //发送广播的方法
    public void sendBroadcast() {
        Intent intent1 = new Intent("MainActivity.broadcast");
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent1);

    }

}
