package com.example.administrator.spms.demo;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.spms.R;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/3/16.
 */
public class PagerMeAdapter extends FragmentPagerAdapter{
    private ArrayList<Fragment> data;
    private Context context;


    public PagerMeAdapter(FragmentManager fm,ArrayList<Fragment> data,Context context) {
        super(fm);
        this.data = data;
        this.context = context;

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
        ImageView img = (ImageView) v.findViewById(R.id.tab_ima);
        img.setImageResource(R.mipmap.ic_launcher);
        return v;
    }

}
