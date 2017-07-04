package com.example.administrator.spms.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.spms.R;
import com.example.administrator.spms.base.Base64ToBitmap;
import com.example.administrator.spms.base.BaseFragment;
import com.example.administrator.spms.base.CustomProgressDialog;
import com.example.administrator.spms.base.Loading;
import com.example.administrator.spms.base.LoadingDialog;
import com.example.administrator.spms.base.NetWorking;
import com.example.administrator.spms.base.Post;
import com.example.administrator.spms.model.GetStationBaseInfoBean;
import com.example.administrator.spms.model.GetStationWeatherInfoBean;
import com.example.administrator.spms.refresh.PullToRefreshLayout;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

/**
 * Created by Administrator on 2016/10/26.
 * 气象信息
 */


@ContentView(R.layout.fragment_power_two)
public class PowerTwoFragment extends BaseFragment {
    private Handler handler8;
    @ViewInject(R.id.tv_tag_power_two)
    private TextView tv_title;
    @ViewInject(R.id.tv_weather)
    private TextView tv_weather;
    // "今日累计辐射量"
    @ViewInject(R.id.tv_power_two_tag)
    private TextView tv_str;
    @ViewInject(R.id.tv_tv_power_two_info)
    private TextView tv_power_info;
    @ViewInject(R.id.tv_num_power_two)
    private TextView tv_num;
    @ViewInject(R.id.tv_tag0_power_two)
    private TextView tv;
    // "本月累计辐射量"
    @ViewInject(R.id.tv_power_two_tag1)
    private TextView tv_str1;
    @ViewInject(R.id.tv_num_power_two1)
    private TextView tv_num1;
    @ViewInject(R.id.tv_tag_power)
    private TextView tv1;
    // "本年累计辐射量"
    @ViewInject(R.id.textView6)
    private TextView tv_str2;
    @ViewInject(R.id.tv_num_power_two2)
    private TextView tv_num2;
    @ViewInject(R.id.tv_tag2_power)
    private TextView tv2;

    //  "本月峰值日照小时数"
    @ViewInject(R.id.tv_power_two_tag3)
    private TextView tv_str3;
    @ViewInject(R.id.textView2)
    private TextView tv_num3;
    @ViewInject(R.id.tv_tag3_power)
    private TextView tv3;

    //   "本年峰值日照小时数"
    @ViewInject(R.id.textView)
    private TextView tv_str4;
    @ViewInject(R.id.textView3)
    private TextView tv_num4;
    @ViewInject(R.id.tv_tag4_power)
    private TextView tv4;

    //   "环境温度"
    @ViewInject(R.id.tv_power_two_tag4)
    private TextView tv_str5;
    @ViewInject(R.id.textView4)
    private TextView tv_num5;
    @ViewInject(R.id.tv_tag5_power)
    private TextView tv5;

    //    "组件温度"
    @ViewInject(R.id.tv_power_two_tag5)
    private TextView tv_str6;
    @ViewInject(R.id.tv_num_power_two5)
    private TextView tv_num6;
    @ViewInject(R.id.tv_tag6_power)
    private TextView tv6;
    private LoadingDialog dialog;
    //天气图标
    @ViewInject(R.id.iv_sun)
    private ImageView iv_icon;
    //状态
    @ViewInject(R.id.iv_state)
    private ImageView iv_state;
    //stationID
    private String ID, ID1, ip;
    //气象站状态文字
    @ViewInject(R.id.tv_tv_power_two_info)
    private TextView tv_weather_text;
    //图片
    @ViewInject(R.id.iv_power_two_tag)
    private ImageView iv_one;
    //图片
    @ViewInject(R.id.iv_power_two_tag2)
    private ImageView iv_two;
    //图片
    @ViewInject(R.id.iv_power_two_tag3)
    private ImageView iv_three;
    //图片
    @ViewInject(R.id.iv_power_two_tag4)
    private ImageView iv_four;
    //图片
    @ViewInject(R.id.iv_power_two_tag5)
    private ImageView iv_five;
    //图片
    @ViewInject(R.id.iv_power_two_tag6)
    private ImageView iv_six;
    //图片
    @ViewInject(R.id.iv_power_two_tag7)
    private ImageView iv_seven;

    private String language;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dialog = new LoadingDialog(getActivity(), Loading.getText(getActivity()));
        //显示Dialog
        dialog.show();

        SharedPreferences sp = getActivity().getSharedPreferences("Set", getActivity().MODE_APPEND);
        ID = sp.getString("SessionID", "");
        ID1 = sp.getString("StationID", "");

        SharedPreferences sp1 = getActivity().getSharedPreferences("IP", getActivity().MODE_APPEND);
        ip = sp1.getString("Ip", "");

        SharedPreferences sp3 = getActivity().getSharedPreferences("language", getActivity().MODE_APPEND);
        language = sp3.getString("language", "");
        if (NetWorking.isNetworkAvailable(getActivity())) {
            new MyThread().start();
        } else {
//            dialog.close();
            if (language.equals("zh_cn")) {
                Toast.makeText(getActivity(), "当前网络不可用", Toast.LENGTH_SHORT).show();

            } else {
                Toast.makeText(getActivity(), "The current network is not available", Toast.LENGTH_SHORT).show();
            }
        }


        //上拉加载和下拉刷新的监听
        ((PullToRefreshLayout) getActivity().findViewById(R.id.iv_refresh_two))
                .setOnRefreshListener(new MyListener());


    }

    private class MyThread extends Thread {
        @Override
        public void run() {

            Log.e("fdxqwe", ID1);

            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("Cmd", "GetStationWeatherInfo");
                jsonObject.put("SessionID", ID);
                jsonObject.put("Dev", "app");
                jsonObject.put("StationID", ID1);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            String sr = Post.sendPost(
                    "http://" + ip + "/SPMSWcfService/SPMSMainHandler.ashx", jsonObject);

            Log.e("qwnmkj", String.valueOf(jsonObject));
            Log.d("coming", sr);


            handler.sendMessage(handler.obtainMessage(1, sr));
        }
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                String sr = (String) msg.obj;
                Gson gson = new Gson();
                GetStationWeatherInfoBean bean = gson.fromJson(sr, GetStationWeatherInfoBean.class);
                dialog.close();

                try {
                    if (bean.getRet().equals("Success")) {
                        Log.e("wwwqqqeee", "ssssss");
                        tv_weather_text.setText("气象站状态");
                        tv_title.setText(bean.getPageTitle());
                        tv_power_info.setText(bean.getWeatherInfo().getWetherStationStatusStr());
                        tv_weather.setText(bean.getWeatherInfo().getWetherInfo());
                        tv.setText(bean.getWeatherInfo().getDayTotalRadiationUnit());
                        tv_str.setText(bean.getWeatherInfo().getDayTotalRadiationStr());
                        tv_num.setText(bean.getWeatherInfo().getDayTotalRadiation());
                        tv1.setText(bean.getWeatherInfo().getMonthTotalRadiationUnit());
                        tv_str1.setText(bean.getWeatherInfo().getMonthTotalRadiationStr());
                        tv_num1.setText(bean.getWeatherInfo().getMonthTotalRadiation());
                        tv2.setText(bean.getWeatherInfo().getYearTotalRadiationUnit());
                        tv_num2.setText(bean.getWeatherInfo().getYearTotalRadiation());
                        tv_str2.setText(bean.getWeatherInfo().getYearTotalRadiationStr());
                        tv3.setText(bean.getWeatherInfo().getMonthPeakSunshineHourUnit());
                        tv_num3.setText(bean.getWeatherInfo().getMonthPeakSunshineHour());
                        tv_str3.setText(bean.getWeatherInfo().getMonthPeakSunshineHourStr());
                        tv4.setText(bean.getWeatherInfo().getYearPeakSunshineHourUnit());
                        tv_num4.setText(bean.getWeatherInfo().getYearPeakSunshineHour());
                        tv_str4.setText(bean.getWeatherInfo().getYearPeakSunshineHourStr());
                        tv5.setText(bean.getWeatherInfo().getEnvironmentTemperatureUnit());
                        tv_num5.setText(bean.getWeatherInfo().getEnvironmentTemperature());
                        tv_str5.setText(bean.getWeatherInfo().getEnvironmentTemperatureStr());
                        tv6.setText(bean.getWeatherInfo().getModuleTemperatureUnit());
                        tv_num6.setText(bean.getWeatherInfo().getModuleTemperature());
                        tv_str6.setText(bean.getWeatherInfo().getModuleTemperatureStr());
                        iv_one.setVisibility(View.VISIBLE);
                        iv_two.setVisibility(View.VISIBLE);
                        iv_three.setVisibility(View.VISIBLE);
                        iv_four.setVisibility(View.VISIBLE);
                        iv_five.setVisibility(View.VISIBLE);
                        iv_six.setVisibility(View.VISIBLE);
                        iv_seven.setVisibility(View.VISIBLE);

                        //判断天气
                       String a = bean.getWeatherInfo().getWeatherCode();
                      //  String a ="default";
                        //气象站状态红色跳转到报警
                        String b = bean.getWeatherInfo().getWetherStationStatus();



                        if (a.equals("3") || a.equals("4") || a.equals("37") || a.equals("38") || a.equals("39") || a.equals("45") || a.equals("47")) {
                            iv_icon.setImageResource(R.mipmap.leizhenyu);
                        } else if (a.equals("9") || a.equals("11") || a.equals("12") || a.equals("18") || a.equals("40")) {
                            iv_icon.setImageResource(R.mipmap.xiaoyu);
                        } else if (a.equals("27") || a.equals("28") || a.equals("29") || a.equals("30") || a.equals("44")) {
                            iv_icon.setImageResource(R.mipmap.duoyun);
                        } else if (a.equals("31") || a.equals("32") || a.equals("33") || a.equals("34") || a.equals("36")) {
                            iv_icon.setImageResource(R.mipmap.qing);
                        } else if (a.equals("25") || a.equals("26")) {
                            iv_icon.setImageResource(R.mipmap.yin);
                        } else if (a.equals("20") || a.equals("21") || a.equals("22")) {
                            iv_icon.setImageResource(R.mipmap.wu);
                        } else if (a.equals("5")) {
                            iv_icon.setImageResource(R.mipmap.yujiaxue);
                        } else if (a.equals("8") || a.equals("10")) {
                            iv_icon.setImageResource(R.mipmap.dongyu);
                        } else if (a.equals("13") || a.equals("14") || a.equals("15") || a.equals("16") || a.equals("42") || a.equals("46")) {
                            iv_icon.setImageResource(R.mipmap.xue);
                        } else if (a.equals("41") || a.equals("43")) {
                            iv_icon.setImageResource(R.mipmap.daxue);
                        } else if (a.equals("6") || a.equals("7") || a.equals("17") || a.equals("35")) {
                            iv_icon.setImageResource(R.mipmap.bingbao);
                        } else if (a.equals("19")) {
                            iv_icon.setImageResource(R.mipmap.fenchen);
                        } else if (a.equals("23") || a.equals("24")) {
                            iv_icon.setImageResource(R.mipmap.feng);
                        } else if ((a==null)||(a.equals("")||(a.trim().length()==0)||(a.equals("null")))){
                            iv_icon.setImageResource(R.mipmap.moren);
                        }else {
                            iv_icon.setImageResource(R.mipmap.moren);
                        }


                        if (b.equals("0")) {
                            iv_state.setImageResource(R.drawable.text_shape_green);
                        }else if ((b==null)||(b.equals("")||(b.trim().length()==0))||(b.equals("null"))){
                            iv_state.setImageResource(R.drawable.text_shape_green);
                        } else {
                            iv_state.setImageResource(R.drawable.text_shape_red);
                            //监听跳转到警报页面
                            iv_state.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    sendBroadcast();
                                    Intent intent2 = new Intent("MainActivity.broadcast.alert");
                                    intent2.putExtra("se", ID1);
                                    intent2.putExtra("id", "");
                                    intent2.putExtra("device","all");
                                    LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent2);
                                }
                            });

                        }
                    } else {
                        dialog.close();
//                    Toast.makeText(getActivity(), "连接服务器失败", Toast.LENGTH_SHORT).show();
                        Log.e("wwwqqqeee", "eeeeee");
                    }
                } catch (Exception e) {

                }


            }
            super.handleMessage(msg);
        }
    };


    public class MyListener implements PullToRefreshLayout.OnRefreshListener {

        @Override
        public void onRefresh(final PullToRefreshLayout pullToRefreshLayout) {
            // 下拉刷新操作
            new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    dialog = new LoadingDialog(getActivity(), Loading.getText(getActivity()));
                    //显示Dialog
                    dialog.show();

                    if (NetWorking.isNetworkAvailable(getActivity())) {
                        new MyThread().start();
                    } else {
                        dialog.close();
                        if (language.equals("zh_cn")) {
                            Toast.makeText(getActivity(), "当前网络不可用", Toast.LENGTH_SHORT).show();

                        } else {
                            Toast.makeText(getActivity(), "The current network is not available", Toast.LENGTH_SHORT).show();
                        }
                    }

                    // 千万别忘了告诉控件刷新完毕了哦！
                    pullToRefreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
                }
            }.sendEmptyMessageDelayed(0, 1000);

        }

        @Override
        public void onLoadMore(final PullToRefreshLayout pullToRefreshLayout) {
            // 加载操作
            new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    dialog = new LoadingDialog(getActivity(), Loading.getText(getActivity()));
                    //显示Dialog
                    dialog.show();

                    if (NetWorking.isNetworkAvailable(getActivity())) {
                        new MyThread().start();
                    } else {
                        dialog.close();
                        if (language.equals("zh_cn")) {
                            Toast.makeText(getActivity(), "当前网络不可用", Toast.LENGTH_SHORT).show();

                        } else {
                            Toast.makeText(getActivity(), "The current network is not available", Toast.LENGTH_SHORT).show();
                        }
                    }
                    // 千万别忘了告诉控件加载完毕了哦！
                    pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
                }
            }.sendEmptyMessageDelayed(0, 1000);
        }

    }


    //发送广播的方法
    public void sendBroadcast() {
        Intent intent1 = new Intent("MainActivity.broadcast");
        LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent1);

    }


}
