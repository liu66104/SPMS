package com.example.administrator.spms.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.spms.R;
import com.example.administrator.spms.activity.LoginActivity;
import com.example.administrator.spms.adapter.ViewPagerAdapterDay;
import com.example.administrator.spms.adapter.ViewPagerAdapterMonth;
import com.example.administrator.spms.adapter.ViewPagerAdapterYear;
import com.example.administrator.spms.base.Base64ToBitmap;
import com.example.administrator.spms.base.BaseFragment;
import com.example.administrator.spms.base.Loading;
import com.example.administrator.spms.base.LoadingDialog;
import com.example.administrator.spms.base.NetWorking;
import com.example.administrator.spms.base.Post;
import com.example.administrator.spms.model.GetStationStatisticsBean;
import com.example.administrator.spms.model.GetStationWeatherInfoBean;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.example.administrator.spms.R.mipmap.date;

/**
 * Created by Administrator on 2016/10/26.
 * 发电统计
 */

@ContentView(R.layout.fragment_power_three)
public class PowerThreeFragment extends BaseFragment {
    private ViewPagerAdapterDay adapterDay;
    private ViewPagerAdapterMonth adapterMoth;
    private ViewPagerAdapterYear adapterYear;
    private int nowDay;
    private int nowMonth;
    private String dayTime;
    private boolean day;
    private boolean day1;
    private boolean month;
    private boolean month1;
    private boolean year;
    private boolean year1;
    private String yesterday;
    private int yesterMonth;
    private int yesterYear;
    private int getYear;
    private List<View> data, data2, data3;
    private View view_day;
    private View view_month;
    private View view_year;
    @ViewInject(R.id.vp_power_day)
    private ViewPager vp_day;
    //控制day传一次
    private int a = 0;
    private int b = 0;
    private int c = 0;
    @ViewInject(R.id.vp_power_month)
    private ViewPager vp_month;

    @ViewInject(R.id.vp_power_year)
    private ViewPager vp_year;
    //day的提示图片
    @ViewInject(R.id.iv_day_left)
    private ImageView iv_day_left;
    //month的提示图片
    @ViewInject(R.id.iv_month_left)
    private ImageView iv_month_left;
    //year的提示图片
    @ViewInject(R.id.iv_year_left)
    private ImageView iv_year_left;

    private LoadingDialog dialog;
    private int pos;

    //标题
    @ViewInject(R.id.tv_power_title_three)
    private TextView tv_power_title_three;
    private String language;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Date date = new Date(System.currentTimeMillis());
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        pos = 0;
        day1 = true;
        dayTime = format.format(date);
        yesterday = dayTime;
        Calendar calendar = Calendar.getInstance();
        yesterMonth = calendar.get(Calendar.MONTH) + 1;
        yesterYear = calendar.get(Calendar.YEAR);
        nowDay = calendar.get(Calendar.MONTH) + 1;
        nowMonth = calendar.get(Calendar.YEAR);
        getYear = nowMonth = calendar.get(Calendar.YEAR);

        SharedPreferences sp3 = getActivity().getSharedPreferences("language", getActivity().MODE_APPEND);
        language = sp3.getString("language", "");

        dialog = new LoadingDialog(getActivity(), Loading.getText(getActivity()));
        //显示Dialog
        dialog.show();

        if (NetWorking.isNetworkAvailable(getActivity())) {
            new MyThread1().start();
        } else {
            dialog.close();
        }
//        new MyThread2().start();
//        new MyThread3().start();
        //触摸监听是否显示滑动提示
        vp_day.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        iv_day_left.setVisibility(View.VISIBLE);
                        break;

                    case MotionEvent.ACTION_UP:
                        iv_day_left.setVisibility(View.GONE);
                        break;
                }

                return false;
            }
        });

        //触摸监听是否显示滑动提示
        vp_month.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        iv_month_left.setVisibility(View.VISIBLE);
                        break;

                    case MotionEvent.ACTION_UP:
                        iv_month_left.setVisibility(View.GONE);
                        break;
                }

                return false;
            }
        });


        //触摸监听是否显示滑动提示
        vp_year.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        iv_year_left.setVisibility(View.VISIBLE);
                        break;

                    case MotionEvent.ACTION_UP:
                        iv_year_left.setVisibility(View.GONE);
                        break;
                }

                return false;
            }
        });


        /**
         *
         *
         * 日数据的监听
         */
        vp_day.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Log.d("3213213231", String.valueOf(position));
                if (day == true) {
                    day = false;
                    if (position == 1) {
                        dialog = new LoadingDialog(getActivity(), Loading.getText(getActivity()));
                        //显示Dialog
                        dialog.show();
                        handler.sendMessage(handler.obtainMessage(2, "1"));
                    } else if (position == 0) {
                        dialog = new LoadingDialog(getActivity(), Loading.getText(getActivity()));
                        //显示Dialog
                        dialog.show();
                        handler.sendMessage(handler.obtainMessage(2, "1"));
                    } else if (position == 2) {
                        if (yesterday.equals(dayTime)) {

                        } else {
                            dialog = new LoadingDialog(getActivity(), Loading.getText(getActivity()));
                            //显示Dialog
                            dialog.show();
                            handler.sendMessage(handler.obtainMessage(2, "2"));
                        }
                    }

                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (a == 0) {
                    a++;
                    day = true;
                    Log.d("32132132zxdxx", "day123");
                    day1 = true;
                }

            }
        });
/**
 *
 *
 * 月数据的监听
 */

        vp_month.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                if (month == true) {
                    if (position == 1) {
                        dialog = new LoadingDialog(getActivity(), Loading.getText(getActivity()));
                        //显示Dialog
                        dialog.show();
                        handler.sendMessage(handler.obtainMessage(4, "1"));
                    } else if (position == 0) {
                        dialog = new LoadingDialog(getActivity(), Loading.getText(getActivity()));
                        //显示Dialog
                        dialog.show();
                        handler.sendMessage(handler.obtainMessage(4, "1"));
                    } else if (position == 2) {
                        if (yesterMonth == nowDay && yesterYear == nowMonth) {

                        } else {
                            dialog = new LoadingDialog(getActivity(), Loading.getText(getActivity()));
                            //显示Dialog
                            dialog.show();
                            handler.sendMessage(handler.obtainMessage(4, "2"));
                        }
                    }
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (b == 0) {
                    b++;
                    month = true;
                    month1 = true;
                }

            }
        });
        /**
         *
         *
         * 年数据的监听
         */

        vp_year.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                if (year == true) {
                    if (position == 1) {
                        dialog = new LoadingDialog(getActivity(), Loading.getText(getActivity()));
                        //显示Dialog
                        dialog.show();
                        handler.sendMessage(handler.obtainMessage(8, "1"));
                    } else if (position == 0) {
                        dialog = new LoadingDialog(getActivity(), Loading.getText(getActivity()));
                        //显示Dialog
                        dialog.show();
                        handler.sendMessage(handler.obtainMessage(8, "1"));
                    } else if (position == 2) {
                        if (getYear == nowMonth) {

                        } else {
                            dialog = new LoadingDialog(getActivity(), Loading.getText(getActivity()));
                            //显示Dialog
                            dialog.show();
                            handler.sendMessage(handler.obtainMessage(8, "2"));
                        }
                    }
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (c == 0) {
                    c++;
                    year = true;
                    year1 = true;
                }

            }
        });

    }

    //首次进入
    private class MyThread1 extends Thread {
        @Override
        public void run() {
            String s = getSpecifiedDayAfter(dayTime);
            SharedPreferences sp = getActivity().getSharedPreferences("Set", getActivity().MODE_APPEND);
            String ID = sp.getString("SessionID", "");
            String ID1 = sp.getString("StationID", "");
            SharedPreferences sp1 = getActivity().getSharedPreferences("IP", getActivity().MODE_APPEND);
            String ip = sp1.getString("Ip", "");
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("Cmd", "GetStationStatistics");
                jsonObject.put("SessionID", ID);
                jsonObject.put("Dev", "app");
                jsonObject.put("StationID", ID1);
                jsonObject.put("TimeInterval", "TimeInterval_Hour");
                jsonObject.put("BeginTime", dayTime + " " + "00:00:00");
                jsonObject.put("EndTime", s + " " + "23:59:59");
                jsonObject.put("PicWidth", "570");
                jsonObject.put("PicHeight", "300");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            String sr = Post.sendPost(
                    "http://" + ip + "/SPMSWcfService/SPMSMainHandler.ashx", jsonObject);
            Log.e("spmsssss", "http://" + ip + "/SPMSWcfService/SPMSMainHandler.ashx");
            Gson gson = new Gson();
            GetStationStatisticsBean bean = gson.fromJson(sr, GetStationStatisticsBean.class);
            if (bean.getRet().equals("Success")) {
                handler.sendMessage(handler.obtainMessage(1, sr));
            } else {
                handler.sendMessage(handler.obtainMessage(10, sr));
            }


            JSONObject jsonObject1 = new JSONObject();
            try {
                jsonObject1.put("Cmd", "GetStationStatistics");
                jsonObject1.put("SessionID", ID);
                jsonObject1.put("Dev", "app");
                jsonObject1.put("StationID", ID1);
                jsonObject1.put("TimeInterval", "TimeInterval_Day");
                jsonObject1.put("BeginTime", nowMonth + "-" + nowDay + "-01" + " " + "00:00:00");
                jsonObject1.put("EndTime", nowMonth + "-" + nowDay + "-" + new Date(nowMonth, nowDay, 0).getDate() + " " + "23:59:59");
                jsonObject1.put("PicWidth", "570");
                jsonObject1.put("PicHeight", "300");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            String sr1 = Post.sendPost(
                    "http://" + ip + "/SPMSWcfService/SPMSMainHandler.ashx", jsonObject1);
            GetStationStatisticsBean bean1 = gson.fromJson(sr1, GetStationStatisticsBean.class);
            if (bean1.getRet().equals("Success")) {
                handler.sendMessage(handler.obtainMessage(6, sr1));
            } else {
                handler.sendMessage(handler.obtainMessage(10, sr));
            }


            JSONObject jsonObject2 = new JSONObject();
            try {
                jsonObject2.put("Cmd", "GetStationStatistics");
                jsonObject2.put("SessionID", ID);
                jsonObject2.put("Dev", "app");
                jsonObject2.put("StationID", ID1);
                jsonObject2.put("TimeInterval", "TimeInterval_Month");
                jsonObject2.put("BeginTime", nowMonth + "-01-01" + " " + "00:00:00");
                jsonObject2.put("EndTime", nowMonth + "-12-31" + " " + "23:59:59");
                jsonObject2.put("PicWidth", "570");
                jsonObject2.put("PicHeight", "300");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            String sr2 = Post.sendPost(
                    "http://" + ip + "/SPMSWcfService/SPMSMainHandler.ashx", jsonObject2);
            GetStationStatisticsBean bean2 = gson.fromJson(sr1, GetStationStatisticsBean.class);
            if (bean2.getRet().equals("Success")) {
                handler.sendMessage(handler.obtainMessage(9, sr2));
            } else {
                handler.sendMessage(handler.obtainMessage(10, sr));
            }
        }
    }

    private class MyThread2 extends Thread {
        @Override
        public void run() {
            String s = getSpecifiedDayAfter(dayTime);
            SharedPreferences sp = getActivity().getSharedPreferences("Set", getActivity().MODE_APPEND);
            String ID = sp.getString("SessionID", "");
            String ID1 = sp.getString("StationID", "");
            SharedPreferences sp1 = getActivity().getSharedPreferences("IP", getActivity().MODE_APPEND);
            String ip = sp1.getString("Ip", "");
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("Cmd", "GetStationStatistics");
                jsonObject.put("SessionID", ID);
                jsonObject.put("Dev", "app");
                jsonObject.put("StationID", ID1);
                //"TimeInterval":"TimeInterval_15Min"\"BeginTime":"2016-10-31","EndTime":"2016-10-31","PicWidth":"400","PicHeight":"200"}
                jsonObject.put("TimeInterval", "TimeInterval_Day");
                jsonObject.put("BeginTime", nowMonth + "-" + nowDay + "-01" + " " + "00:00:00");
                jsonObject.put("EndTime", nowMonth + "-" + nowDay + "-" + new Date(nowMonth, nowDay, 0).getDate() + " " + "23:59:59");
                jsonObject.put("PicWidth", "570");
                jsonObject.put("PicHeight", "300");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            String sr2 = Post.sendPost(
                    "http://" + ip + "/SPMSWcfService/SPMSMainHandler.ashx", jsonObject);
            handler.sendMessage(handler.obtainMessage(9, sr2));


        }
    }

    private class MyThread3 extends Thread {
        @Override
        public void run() {

            SharedPreferences sp = getActivity().getSharedPreferences("Set", getActivity().MODE_APPEND);
            String ID = sp.getString("SessionID", "");
            String ID1 = sp.getString("StationID", "");
            SharedPreferences sp1 = getActivity().getSharedPreferences("IP", getActivity().MODE_APPEND);
            String ip = sp1.getString("Ip", "");
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("Cmd", "GetStationStatistics");
                jsonObject.put("SessionID", ID);
                jsonObject.put("Dev", "app");
                jsonObject.put("StationID", ID1);
                //"TimeInterval":"TimeInterval_15Min"\"BeginTime":"2016-10-31","EndTime":"2016-10-31","PicWidth":"400","PicHeight":"200"}
                jsonObject.put("TimeInterval", "TimeInterval_Month");
                jsonObject.put("BeginTime", nowMonth + "-01-01" + " " + "00:00:00");
                jsonObject.put("EndTime", nowMonth + "-12-31" + " " + "23:59:59");
                jsonObject.put("PicWidth", "570");
                jsonObject.put("PicHeight", "300");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            String sr = Post.sendPost(
                    "http://" + ip + "/SPMSWcfService/SPMSMainHandler.ashx", jsonObject);
            Log.d("comingwodejjjj", sr);
            if (sr.equals("") || sr == null) {
                handler.sendMessage(handler.obtainMessage(10, ""));
            } else {
                handler.sendMessage(handler.obtainMessage(9, sr));
            }
        }
    }

    /**
     * 日的拉数据
     */
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                dialog.close();
                String sr = (String) msg.obj;
                Gson gson = new Gson();
                GetStationStatisticsBean bean = gson.fromJson(sr, GetStationStatisticsBean.class);
                tv_power_title_three.setText(bean.getPageTitle());

                data = new ArrayList<>();

                try {
                    for (int i = 0; i < 3; i++) {
                        view_day = LayoutInflater.from(getActivity()).inflate(R.layout.item_image, null);
                        if (i == 2) {
                            ImageView iv = (ImageView) view_day.findViewById(R.id.iv_day);
                            iv.setImageBitmap(Base64ToBitmap.stringtoBitmap(bean.getStaisticsInfo().getData()));
                        }
                        data.add(view_day);
                    }
                }catch (Exception e){

                }
                adapterDay = new ViewPagerAdapterDay(getActivity());
                adapterDay.addData(data);
                vp_day.setAdapter(adapterDay);
                vp_day.setCurrentItem(2);
            }
            //滑动前一天
            if (msg.what == 2) {
                dialog.close();
                final String B = (String) msg.obj;
                if (B.equals("1")) {
                    yesterday = getSpecifiedDayBefore(yesterday);
                    Log.d("yesterasda", yesterday);
                } else if (B.equals("2")) {
                    yesterday = getSpecifiedDayAfter(yesterday);

                    Log.d("yesterasda1", yesterday);
                }
                if (yesterday != dayTime) {
                    day = false;
                } else {
                    day = true;
                }

                final String s = getSpecifiedDayAfter(yesterday);

                class MyThread extends Thread {
                    @Override
                    public void run() {
                        SharedPreferences sp = getActivity().getSharedPreferences("Set", getActivity().MODE_APPEND);
                        String ID = sp.getString("SessionID", "");
                        String ID1 = sp.getString("StationID", "");
                        SharedPreferences sp1 = getActivity().getSharedPreferences("IP", getActivity().MODE_APPEND);
                        String ip = sp1.getString("Ip", "");
                        JSONObject jsonObject = new JSONObject();
                        try {
                            jsonObject.put("Cmd", "GetStationStatistics");
                            jsonObject.put("SessionID", ID);
                            jsonObject.put("Dev", "app");
                            jsonObject.put("StationID", ID1);
                            //"TimeInterval":"TimeInterval_15Min"\"BeginTime":"2016-10-31","EndTime":"2016-10-31","PicWidth":"400","PicHeight":"200"}
                            jsonObject.put("TimeInterval", "TimeInterval_Hour");
                            jsonObject.put("BeginTime", yesterday + " " + "00:00:00");
                            jsonObject.put("EndTime", yesterday + " " + "23:59:59");
                            jsonObject.put("PicWidth", "570");
                            jsonObject.put("PicHeight", "300");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        String sr = Post.sendPost(
                                "http://" + ip + "/SPMSWcfService/SPMSMainHandler.ashx", jsonObject);

                        Log.d("coming", String.valueOf(jsonObject));

                        handler.sendMessage(handler.obtainMessage(3, sr));

                    }
                }

                new MyThread().start();


            }
            //前一天加载数据
            if (msg.what == 3) {

                dialog.close();
                String sr = (String) msg.obj;
                Gson gson = new Gson();
                GetStationStatisticsBean bean = gson.fromJson(sr, GetStationStatisticsBean.class);
                data = new ArrayList<>();
                for (int i = 0; i < 3; i++) {
                    view_day = LayoutInflater.from(getActivity()).inflate(R.layout.item_image, null);

                    if (yesterday.equals(dayTime)) {
                        if (i == 2) {
                            ImageView iv = (ImageView) view_day.findViewById(R.id.iv_day);
                            iv.setImageBitmap(Base64ToBitmap.stringtoBitmap(bean.getStaisticsInfo().getData()));
                        }
                    } else {
                        if (i == 1) {
                            ImageView iv = (ImageView) view_day.findViewById(R.id.iv_day);
                            iv.setImageBitmap(Base64ToBitmap.stringtoBitmap(bean.getStaisticsInfo().getData()));

                        }
                    }

                    data.add(view_day);
                }
                adapterDay = new ViewPagerAdapterDay(getActivity());
                adapterDay.addData(data);
                vp_day.setAdapter(adapterDay);
                if (yesterday.equals(dayTime)) {
                    vp_day.setCurrentItem(2);
                } else {
                    vp_day.setCurrentItem(1);
                }
                a = 0;
            }
            /**
             * 月的拉数据
             *
             *
             *
             */
            if (msg.what == 6) {
                dialog.close();
                String sr = (String) msg.obj;
                Gson gson = new Gson();
                GetStationStatisticsBean bean = gson.fromJson(sr, GetStationStatisticsBean.class);

                try {
                    if (bean.getRet().equals("Success")) {
                        data2 = new ArrayList<>();
                        for (int i = 0; i < 3; i++) {
                            view_month = LayoutInflater.from(getActivity()).inflate(R.layout.item_image3, null);
                            if (i == 2) {
                                ImageView iv = (ImageView) view_month.findViewById(R.id.iv_day);
                                iv.setImageBitmap(Base64ToBitmap.stringtoBitmap(bean.getStaisticsInfo().getData()));
                            }
                            data2.add(view_month);
                        }
                        adapterMoth = new ViewPagerAdapterMonth(getActivity());
                        adapterMoth.addData(data2);
                        vp_month.setAdapter(adapterMoth);
                        vp_month.setCurrentItem(2);
                    } else {
                        if (bean.getErrCode().equals("1")){
                            if (language.equals("zh_cn")){
                                Toast.makeText(getActivity(), "您的登录已失效，请重新登录", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getActivity(),LoginActivity.class);
                                startActivity(intent);
                                sendBroadcast();
                            }else {
                                Toast.makeText(getActivity(), "Your login has expired, please log in again", Toast.LENGTH_SHORT).show();
                            }
                        }else {
                            if (language.equals("zh_cn")){
                                Toast.makeText(getActivity(), "连接服务器失败", Toast.LENGTH_SHORT).show();

                            }else {
                                Toast.makeText(getActivity(), "Connection server failed ", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                } catch (Exception e) {

                }

            }
            if (msg.what == 4) {
                dialog.close();
                final String B = (String) msg.obj;
                if (B.equals("1")) {
                    if (yesterMonth == 1) {
                        yesterMonth = 12;
                        yesterYear = yesterYear - 1;
                    } else {
                        yesterMonth = yesterMonth - 1;
                    }

                } else if (B.equals("2")) {
                    if (yesterMonth == 12) {
                        yesterMonth = 1;
                        yesterYear = yesterYear + 1;
                    } else {
                        yesterMonth = yesterMonth + 1;
                    }
                }
                if (yesterMonth == nowDay && yesterYear == nowMonth) {
                    month = true;
                } else {
                    month = false;
                    Log.d("321", "321");
                }
                class MyThread extends Thread {
                    @Override
                    public void run() {
                        SharedPreferences sp = getActivity().getSharedPreferences("Set", getActivity().MODE_APPEND);
                        String ID = sp.getString("SessionID", "");
                        String ID1 = sp.getString("StationID", "");
                        SharedPreferences sp1 = getActivity().getSharedPreferences("IP", getActivity().MODE_APPEND);
                        String ip = sp1.getString("Ip", "");
                        JSONObject jsonObject = new JSONObject();
                        try {
                            jsonObject.put("Cmd", "GetStationStatistics");
                            jsonObject.put("SessionID", ID);
                            jsonObject.put("Dev", "app");
                            jsonObject.put("StationID", ID1);
                            //"TimeInterval":"TimeInterval_15Min"\"BeginTime":"2016-10-31","EndTime":"2016-10-31","PicWidth":"400","PicHeight":"200"}
                            jsonObject.put("TimeInterval", "TimeInterval_Day");
                            jsonObject.put("BeginTime", yesterYear + "-" + yesterMonth + "-01" + " " + "00:00:00");
                            jsonObject.put("EndTime", yesterYear + "-" + yesterMonth + "-" + new Date(yesterYear, yesterMonth, 0).getDate() + " " + "23:59:59");
                            jsonObject.put("PicWidth", "570");
                            jsonObject.put("PicHeight", "300");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        String sr = Post.sendPost(
                                "http://" + ip + "/SPMSWcfService/SPMSMainHandler.ashx", jsonObject);
                        Log.d("coming", sr);

                        Log.e("coming1233213213", yesterYear + "-" + yesterMonth + "-01");
                        Log.d("coming1233213213", yesterYear + "-" + yesterMonth + "-" + new Date(yesterYear, yesterMonth, 0).getDate());
                        handler.sendMessage(handler.obtainMessage(5, sr));

                    }
                }
                new MyThread().start();

            }
            if (msg.what == 5) {
                String sr = (String) msg.obj;
                Gson gson = new Gson();
                GetStationStatisticsBean bean = gson.fromJson(sr, GetStationStatisticsBean.class);

                try {
                    if (bean.getRet().equals("Success")) {
                        data2 = new ArrayList<>();
                        for (int i = 0; i < 3; i++) {
                            view_month = LayoutInflater.from(getActivity()).inflate(R.layout.item_image3, null);

                            if (yesterMonth == nowDay && yesterYear == nowMonth) {
                                if (i == 2) {
                                    ImageView iv = (ImageView) view_month.findViewById(R.id.iv_day);
                                    iv.setImageBitmap(Base64ToBitmap.stringtoBitmap(bean.getStaisticsInfo().getData()));
                                }
                            } else {
                                if (i == 1) {
                                    ImageView iv = (ImageView) view_month.findViewById(R.id.iv_day);
                                    iv.setImageBitmap(Base64ToBitmap.stringtoBitmap(bean.getStaisticsInfo().getData()));

                                }
                            }
                            data2.add(view_month);
                        }

                        adapterMoth = new ViewPagerAdapterMonth(getActivity());
                        adapterMoth.addData(data2);
                        vp_month.setAdapter(adapterMoth);
                        if (yesterMonth == nowDay && yesterYear == nowMonth) {
                            vp_month.setCurrentItem(2);
                        } else {
                            vp_month.setCurrentItem(1);
                        }
                        b = 0;
                    } else {
                        if (bean.getErrCode().equals("1")){
                            if (language.equals("zh_cn")){
                                Toast.makeText(getActivity(), "您的登录已失效，请重新登录", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getActivity(),LoginActivity.class);
                                startActivity(intent);
                                sendBroadcast();
                            }else {
                                Toast.makeText(getActivity(), "Your login has expired, please log in again", Toast.LENGTH_SHORT).show();
                            }
                        }else {
                            if (language.equals("zh_cn")){
                                Toast.makeText(getActivity(), "连接服务器失败", Toast.LENGTH_SHORT).show();

                            }else {
                                Toast.makeText(getActivity(), "Connection server failed ", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                } catch (Exception e) {

                }

            }
            /**
             * 年
             */
            if (msg.what == 9) {
                dialog.close();
                String sr = (String) msg.obj;
                Gson gson = new Gson();
                GetStationStatisticsBean bean = gson.fromJson(sr, GetStationStatisticsBean.class);

//                if (bean.getRet().equals("Succcess")) {
                data3 = new ArrayList<>();
                for (int i = 0; i < 3; i++) {
                    view_year = LayoutInflater.from(getActivity()).inflate(R.layout.item_image, null);
                    if (i == 2) {
                        ImageView iv = (ImageView) view_year.findViewById(R.id.iv_day);
                        iv.setImageBitmap(Base64ToBitmap.stringtoBitmap(bean.getStaisticsInfo().getData()));
                    }
                    data3.add(view_year);
                }
                adapterYear = new ViewPagerAdapterYear(getActivity());
                adapterYear.addData(data3);
                vp_year.setAdapter(adapterYear);
                vp_year.setCurrentItem(2);
//                }

//                else {
//                    Toast.makeText(getActivity(), "您的登录已失效，请重新登录", Toast.LENGTH_SHORT).show();
//                }

            }
            if (msg.what == 8) {
                dialog.close();
                final String B = (String) msg.obj;
                if (B.equals("1")) {
                    getYear = getYear - 1;
                } else if (B.equals("2")) {
                    getYear = getYear + 1;
                }
                if (getYear == nowMonth) {
                    year = true;
                } else {
                    year = false;
                }
                Log.d("yearyear", String.valueOf(getYear));
                class MyThread extends Thread {
                    @Override
                    public void run() {
                        SharedPreferences sp = getActivity().getSharedPreferences("Set", getActivity().MODE_APPEND);
                        String ID = sp.getString("SessionID", "");
                        String ID1 = sp.getString("StationID", "");
                        SharedPreferences sp1 = getActivity().getSharedPreferences("IP", getActivity().MODE_APPEND);
                        String ip = sp1.getString("Ip", "");
                        JSONObject jsonObject = new JSONObject();
                        try {
                            jsonObject.put("Cmd", "GetStationStatistics");
                            jsonObject.put("SessionID", ID);
                            jsonObject.put("Dev", "app");
                            jsonObject.put("StationID", ID1);
                            //"TimeInterval":"TimeInterval_15Min"\"BeginTime":"2016-10-31","EndTime":"2016-10-31","PicWidth":"400","PicHeight":"200"}
                            jsonObject.put("TimeInterval", "TimeInterval_Month");
                            jsonObject.put("BeginTime", getYear + "-" + "01" + "-01" + " " + "00:00:00");
                            jsonObject.put("EndTime", getYear + "-" + "12" + "-31" + " " + "23:59:59");
                            jsonObject.put("PicWidth", "570");
                            jsonObject.put("PicHeight", "300");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        String sr = Post.sendPost(
                                "http://" + ip + "/SPMSWcfService/SPMSMainHandler.ashx", jsonObject);
                        handler.sendMessage(handler.obtainMessage(7, sr));

                    }
                }
                new MyThread().start();

            }
            if (msg.what == 7) {

                String sr = (String) msg.obj;
                Gson gson = new Gson();
                GetStationStatisticsBean bean = gson.fromJson(sr, GetStationStatisticsBean.class);

//                if (bean.getRet().equals("Success")) {
                data3 = new ArrayList<>();
                for (int i = 0; i < 3; i++) {
                    view_year = LayoutInflater.from(getActivity()).inflate(R.layout.item_image, null);
                    if (getYear == nowMonth) {
                        if (i == 2) {
                            ImageView iv = (ImageView) view_year.findViewById(R.id.iv_day);
                            iv.setImageBitmap(Base64ToBitmap.stringtoBitmap(bean.getStaisticsInfo().getData()));
                        }
                    } else {
                        if (i == 1) {
                            ImageView iv = (ImageView) view_year.findViewById(R.id.iv_day);
                            iv.setImageBitmap(Base64ToBitmap.stringtoBitmap(bean.getStaisticsInfo().getData()));

                        }
                    }

                    data3.add(view_year);
                }
                adapterYear = new ViewPagerAdapterYear(getActivity());
                adapterYear.addData(data3);
                vp_year.setAdapter(adapterYear);
                if (getYear == nowMonth) {
                    vp_year.setCurrentItem(2);
                } else {
                    vp_year.setCurrentItem(1);
                }
                c = 0;
//                }
//                else {
//                    Toast.makeText(getActivity(), "您的登录已失效，请重新登录", Toast.LENGTH_SHORT).show();
//                }


            }
            if (msg.what == 10) {
                dialog.close();
                if (language.equals("zh_cn")) {
                    Toast.makeText(getActivity(), "当前网络不稳定", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(getActivity(), "The current network is not stable", Toast.LENGTH_SHORT).show();
                }

            }
            super.handleMessage(msg);
        }
    };


    /**
     * 获得指定日期的前一天
     *
     * @param specifiedDay
     * @return
     * @throws Exception
     */
    public static String getSpecifiedDayBefore(String specifiedDay) {
        Calendar c = Calendar.getInstance();
        Date date = null;
        try {
            date = new SimpleDateFormat("yy-MM-dd").parse(specifiedDay);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c.setTime(date);
        int day = c.get(Calendar.DATE);
        c.set(Calendar.DATE, day - 1);

        String dayBefore = new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
        return dayBefore;
    }
    /**
     * 月
     */

    /**
     * 获得指定日期的后一天
     *
     * @param specifiedDay
     * @return
     */
    public static String getSpecifiedDayAfter(String specifiedDay) {
        Calendar c = Calendar.getInstance();
        Date date = null;
        try {
            date = new SimpleDateFormat("yy-MM-dd").parse(specifiedDay);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c.setTime(date);
        int day = c.get(Calendar.DATE);
        c.set(Calendar.DATE, day + 1);

        String dayAfter = new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
        return dayAfter;
    }

    //发送广播的方法
    public void sendBroadcast() {
        Intent intent1 = new Intent("com.main");
        LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent1);

    }
}
