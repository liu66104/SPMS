package com.example.administrator.spms.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.spms.R;
import com.example.administrator.spms.adapter.GridViewAdapter;
import com.example.administrator.spms.base.Base64ToBitmap;
import com.example.administrator.spms.base.BaseFragment;
import com.example.administrator.spms.base.CustomProgressDialog;
import com.example.administrator.spms.base.Loading;
import com.example.administrator.spms.base.LoadingDialog;
import com.example.administrator.spms.base.MyBitmap;
import com.example.administrator.spms.base.NetWorking;
import com.example.administrator.spms.base.Post;
import com.example.administrator.spms.model.GetStationBaseInfoBean;
import com.example.administrator.spms.model.GetStationListBean;
import com.example.administrator.spms.refresh.PullToRefreshLayout;
import com.facebook.common.logging.LoggingDelegate;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/10/26.
 * 电站概况
 */

@ContentView(R.layout.fragment_power_one)
public class PowerOneFragment extends BaseFragment {
    @ViewInject(R.id.tv_name_power_one)
    private TextView tv_title;

    @ViewInject(R.id.iv_power_one)
    private ImageView iv;
    //装机容量
    @ViewInject(R.id.tv_power_one_tag)
    private TextView tv_tag_str;
    @ViewInject(R.id.tv_tag0_power)
    private TextView tv_tag;
    @ViewInject(R.id.tv_num_power)
    private TextView tv_num;
    //今日已发电量
    @ViewInject(R.id.tv_power_one_tag2)
    private TextView tv_tag2_str;
    @ViewInject(R.id.tv_tag_power)
    private TextView tv_tag2;
    @ViewInject(R.id.tv_num2_power)
    private TextView tv_num2;
    //累计已发电量
    @ViewInject(R.id.tv_power_one_tag3)
    private TextView tv_tag3_str;
    @ViewInject(R.id.tv_tag2_power)
    private TextView tv_tag3;
    @ViewInject(R.id.tv_num3_power)
    private TextView tv_num3;
    //业主上月收益
    @ViewInject(R.id.tv_power_one_tag4)
    private TextView tv_tag4_str;
    @ViewInject(R.id.tv_tag3_power)
    private TextView tv_tag4;
    @ViewInject(R.id.textView2)
    private TextView tv_num4;
    //业主累计收益
    @ViewInject(R.id.textView)
    private TextView tv_tag5_str;
    @ViewInject(R.id.tv_tag4_power)
    private TextView tv_tag5;
    @ViewInject(R.id.textView3)
    private TextView tv_num5;
    //累计碳减排量
    @ViewInject(R.id.tv_power_one_tag6)
    private TextView tv_tag6_str;

    @ViewInject(R.id.tv_power_one_tag6_1)
    private TextView tv_tag6_str_1;

    @ViewInject(R.id.tv_power_one_tag6_2)
    private TextView tv_tag6_str_2;

    @ViewInject(R.id.tv_power_one_tag6_3)
    private TextView tv_tag6_str_3;


    @ViewInject(R.id.tv_num6_power)
    private TextView tv_tag6;
    @ViewInject(R.id.tv_tag5_power)
    private TextView tv_num6;

    @ViewInject(R.id.tv_power_one_title)
    private TextView tv_power_title;
    private LoadingDialog dialog;
    //图片
    @ViewInject(R.id.iv_power_one_tag)
    private ImageView iv_one;
    //图片
    @ViewInject(R.id.iv_power_one_tag2)
    private ImageView iv_two;
    //图片
    @ViewInject(R.id.iv_power_one_tag3)
    private ImageView iv_three;
    //图片
    @ViewInject(R.id.iv_power_one_tag4)
    private ImageView iv_four;
    //图片
    @ViewInject(R.id.iv_power_one_tag5)
    private ImageView iv_five;
    //图片
    @ViewInject(R.id.iv_power_one_tag6)
    private ImageView iv_six;

    private String language;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dialog = new LoadingDialog(getActivity(), Loading.getText(getActivity()));
        //显示Dialog
        dialog.show();

        SharedPreferences sp3 = getActivity().getSharedPreferences("language", getActivity().MODE_APPEND);
        language = sp3.getString("language", "");

        if (NetWorking.isNetworkAvailable(getActivity())){
            new MyThread().start();
        }else {
            dialog.close();
            if (language.equals("zh_cn")){
                Toast.makeText(getActivity(), "当前网络不可用", Toast.LENGTH_SHORT).show();

            }else {
                Toast.makeText(getActivity(), "The current network is not available", Toast.LENGTH_SHORT).show();
            }
        }


        //上拉加载和下拉刷新的监听
        ((PullToRefreshLayout) getActivity().findViewById(R.id.refresh_power_one))
                .setOnRefreshListener(new MyListener());

    }

    private class MyThread extends Thread {
        @Override
        public void run() {
            SharedPreferences sp = getActivity().getSharedPreferences("Set", getActivity().MODE_APPEND);
            String ID = sp.getString("SessionID", "");
            String ID1 = sp.getString("StationID", "");
            SharedPreferences sp1 = getActivity().getSharedPreferences("IP", getActivity().MODE_APPEND);
            String ip = sp1.getString("Ip", "");
            Log.d("112233", ID);

            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("Cmd", "GetStationBaseInfo");
                jsonObject.put("SessionID", ID);
                jsonObject.put("Dev", "app");
                jsonObject.put("StationID", ID1);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            String sr = Post.sendPost(
                    "http://" + ip + "/SPMSWcfService/SPMSMainHandler.ashx", jsonObject);
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
                GetStationBaseInfoBean bean = gson.fromJson(sr, GetStationBaseInfoBean.class);
                dialog.close();


                try {
                    if (bean.getRet().equals("Success")) {
                        tv_title.setText(bean.getPageTitle() + "");
                        if (bean.getBaseInfo().getStationImg() ==null ||bean.getBaseInfo().getStationImg().equals("")||bean.getBaseInfo().getStationImg().equals(null)){

                        }else {
                            iv.setImageBitmap(MyBitmap.zoomImage(Base64ToBitmap.stringtoBitmap(bean.getBaseInfo().getStationImg()), 800, 600));
                        }

                        tv_tag.setText(bean.getBaseInfo().getStationCapacityUnit() + "");
                        tv_tag_str.setText(bean.getBaseInfo().getStationCapacityStr() + "");
                        tv_num.setText(bean.getBaseInfo().getStationCapacity() + "");
                        tv_tag2.setText(bean.getBaseInfo().getDayPowerGenerationUnit() + "");
                        tv_tag2_str.setText(bean.getBaseInfo().getDayPowerGenerationStr() + "");
                        tv_num2.setText(bean.getBaseInfo().getDayPowerGeneration() + "");
                        tv_tag3.setText(bean.getBaseInfo().getTotalPowerGenerationUnit() + "");
                        tv_tag3_str.setText(bean.getBaseInfo().getTotalPowerGenerationStr() + "");
                        tv_num3.setText(bean.getBaseInfo().getTotalPowerGeneration() + "");
                        tv_tag4.setText(bean.getBaseInfo().getLastMonthBenefitUnit() + "");
                        tv_tag4_str.setText(bean.getBaseInfo().getLastMonthBenefitStr() + "");
                        tv_num4.setText(bean.getBaseInfo().getLastMonthBenefit() + "");
                        tv_tag5.setText(bean.getBaseInfo().getTotalBenefitUnit() + "");
                        tv_tag5_str.setText(bean.getBaseInfo().getTotalBenefitStr() + "");
                        tv_num5.setText(bean.getBaseInfo().getTotalBenefit() + "");


                        if (language.equals("zh_cn")){
                            tv_tag6_str.setText(bean.getBaseInfo().getEmissionsReductionStr());
                            tv_tag6_str_1.setVisibility(View.INVISIBLE);
                            tv_tag6_str_2.setVisibility(View.INVISIBLE);
                            tv_tag6_str_3.setVisibility(View.INVISIBLE);
                        }else {
                            tv_tag6_str.setVisibility(View.INVISIBLE);
                            String GET = bean.getBaseInfo().getEmissionsReductionStr();
                            tv_tag6_str_1.setText(GET.substring(0,2));
                            tv_tag6_str_2.setText("2"+"");
                            tv_tag6_str_3.setText(GET.substring(3));
                        }

                        tv_tag6.setText(bean.getBaseInfo().getEmissionsReduction());
                        tv_num6.setText(bean.getBaseInfo().getEmissionsReductionUnit());
                        tv_power_title.setText(bean.getBaseInfo().getStationSummary());
                        iv_one.setVisibility(View.VISIBLE);
                        iv_two.setVisibility(View.VISIBLE);
                        iv_three.setVisibility(View.VISIBLE);
                        iv_four.setVisibility(View.VISIBLE);
                        iv_five.setVisibility(View.VISIBLE);
                        iv_six.setVisibility(View.VISIBLE);


                    } else {
//                        if (bean.getErrCode().equals("1")){
//                            if (language.equals("zh_cn")){
//                                Toast.makeText(getActivity(), "您的登录已失效，请重新登录", Toast.LENGTH_SHORT).show();
//
//                            }else {
//                                Toast.makeText(getActivity(), "Your login has expired, please log in again", Toast.LENGTH_SHORT).show();
//                            }
//                        }else {
//                            if (language.equals("zh_cn")){
//                                Toast.makeText(getActivity(), "连接服务器失败", Toast.LENGTH_SHORT).show();
//
//                            }else {
//                                Toast.makeText(getActivity(), "Connection server failed ", Toast.LENGTH_SHORT).show();
//                            }
//                        }
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

}