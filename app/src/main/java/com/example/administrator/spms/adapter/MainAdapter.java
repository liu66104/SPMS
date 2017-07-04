package com.example.administrator.spms.adapter;

import android.content.Context;

import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.administrator.spms.R;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/10/24.
 * MainActivity的适配器
 */

public class MainAdapter extends FragmentPagerAdapter {
    private ArrayList<Fragment> data;
    private Context context;
    private List<String> titles;
    private Handler handler;

    private int IconImg[] = {
            R.drawable.operate_selector,
            R.drawable.powerstation_selector,
            R.drawable.equipment_selector,
            R.drawable.alert_selector};

//    String title1 = "运营";
//    String title2 = "电站";
//    String title3 = "设备";
//    String title4 = "报警";
//
//    String[] titles = {title1, title2, title3, title4};


    public MainAdapter(FragmentManager fm, ArrayList<Fragment> data, List<String> titles, Context context) {
        super(fm);
        this.data = data;
        this.context = context;
        this.titles = titles;

        Log.e("wwww", String.valueOf(data.size()));
    }


    @Override
    public Fragment getItem(int position) {
        return data.get(position);
    }

    @Override
    public int getCount() {
        return data.size();

    }

    public View getTabView(final int position) {
        View v = LayoutInflater.from(context).inflate(R.layout.custom_tab, null);
        TextView tv = (TextView) v.findViewById(R.id.tv_custom);
        tv.setText(titles.get(position));
        ImageView img = (ImageView) v.findViewById(R.id.tab_ima);
        img.setImageResource(IconImg[position]);
//        RelativeLayout rl = (RelativeLayout) v.findViewById(R.id.rl_main);
//        rl.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (position == 0){
//                    handler.sendEmptyMessage(0);
//                }else if (position == 1){
//                    handler.sendEmptyMessage(1);
//                }else if (position == 2){
//                    handler.sendEmptyMessage(2);
//                }else {
//                    handler.sendEmptyMessage(3);
//                }
//
//            }
//        });
        return v;
    }

}
