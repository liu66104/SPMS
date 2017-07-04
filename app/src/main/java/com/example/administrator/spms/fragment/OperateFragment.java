package com.example.administrator.spms.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.spms.R;
import com.example.administrator.spms.activity.AboutActivity;
import com.example.administrator.spms.activity.InfoActivity;
import com.example.administrator.spms.activity.LoginActivity;
import com.example.administrator.spms.activity.LoginOneActivity;
import com.example.administrator.spms.activity.LoginTwoActivity;
import com.example.administrator.spms.base.BaseFragment;
import com.example.administrator.spms.base.CustomProgressDialog;
import com.example.administrator.spms.base.Loading;
import com.example.administrator.spms.base.LoadingDialog;
import com.example.administrator.spms.base.NetWorking;
import com.example.administrator.spms.base.Post;
import com.example.administrator.spms.model.GetGroupOperationInfo;
import com.example.administrator.spms.refresh.PullToRefreshLayout;
import com.example.administrator.spms.refresh.PullableScrollView;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

/**
 * Created by Administrator on 2016/10/24.
 * 运营界面
 */

@ContentView(R.layout.fragment_operate)
public class OperateFragment extends BaseFragment {
    private PopupWindow mPopupwinow = null;
    private View view_pop;
    @ViewInject(R.id.iv_set_operate)
    private ImageView iv_point;
    //标题
    @ViewInject(R.id.tv_operate_title)
    private TextView tv_title;
    private Handler handler8;
    //今日发电量
    @ViewInject(R.id.tv_tag)
    private TextView tv_tag;
    @ViewInject(R.id.tv_tag_str)
    private TextView tv_tag_str;
    @ViewInject(R.id.tv_kwh)
    private TextView tv_kwh;
    //年发电量
    @ViewInject(R.id.tv_tag2)
    private TextView tv_tag2;
    @ViewInject(R.id.tv_tag2_str)
    private TextView tv_tag2_str;
    @ViewInject(R.id.tv_kwh2)
    private TextView tv_kmh2;
    //年发电量目标
    @ViewInject(R.id.tv_tag10)
    private TextView tv_tag10;
    @ViewInject(R.id.tv_anim10)
    private TextView tv_tag10_str;
    @ViewInject(R.id.tv_kwh10)
    private TextView tv_kmh10;


    //去年发电量完成率
    @ViewInject(R.id.tv_tag6)
    private TextView tv_tag6;
    @ViewInject(R.id.tv_kwh6)
    private TextView tv_kwh6;
    @ViewInject(R.id.tv_tag6_str)
    private TextView tv_tag6_str;
    //运行电站数量
    @ViewInject(R.id.tv_tag3)
    private TextView tv_tag3;
    @ViewInject(R.id.tv_kwh3)
    private TextView tv_kwh3;
    @ViewInject(R.id.tv_tag3_str)
    private TextView tv_tag3_str;
    //运行电站容量
    @ViewInject(R.id.tv_tag7)
    private TextView tv_tag7;
    @ViewInject(R.id.textView7)
    private TextView tv_tag7_str;
    @ViewInject(R.id.tv_kwh7)
    private TextView tv_kwh7;
    //业主累计收益
    @ViewInject(R.id.tv_tag4)
    private TextView tv_tag4;
    @ViewInject(R.id.tv_kwh4)
    private TextView tv_kwh4;
    @ViewInject(R.id.tv_tag4_str)
    private TextView tv_tag4_str;
    //累计减排量
    @ViewInject(R.id.tv_tag5)
    private TextView tv_tag5;

    @ViewInject(R.id.tv_tag5_1)
    private TextView tv_tag5_1;
    @ViewInject(R.id.tv_tag5_2)
    private TextView tv_tag5_2;
    @ViewInject(R.id.tv_tag5_3)
    private TextView tv_tag5_3;

    @ViewInject(R.id.tv_kwh5)
    private TextView tv_kwh5;
    @ViewInject(R.id.tv_tag5_str)
    private TextView tv_tag5_str;
    private LoadingDialog dialog;
    //Title的文字
    private String title;
    @ViewInject(R.id.tv_operate_title_main)
    private TextView title_main;

    @ViewInject(R.id.ll_op)
    private LinearLayout ll_op;
    private String level;
    private String language;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dialog = new LoadingDialog(getActivity(), Loading.getText(getActivity()));
        //显示Dialog
        dialog.show();


        SharedPreferences sp = getActivity().getSharedPreferences("Set", getActivity().MODE_APPEND);

        SharedPreferences sp3 = getActivity().getSharedPreferences("language", getActivity().MODE_APPEND);
        language = sp3.getString("language", "");

        level = sp.getString("Level", "");

        SharedPreferences sp2 = getActivity().getSharedPreferences("title", getActivity().MODE_APPEND);
        title = sp2.getString("title", "");



        if (level.equals("3")) {
            ll_op.setVisibility(View.GONE);
            if (language.equals("zh_cn")){
                Toast.makeText(getActivity(), "您没有权限查看当前页面", Toast.LENGTH_SHORT).show();

            }else {
                Toast.makeText(getActivity(), "You do not have permission to view the current page ", Toast.LENGTH_SHORT).show();
            }

            dialog.close();
        } else {
            if (NetWorking.isNetworkAvailable(getActivity())) {
                new MyThread().start();
            } else {
                if (language.equals("zh_cn")){
                    Toast.makeText(getActivity(), "当前网络不可用", Toast.LENGTH_SHORT).show();

                }else {
                    Toast.makeText(getActivity(), "The current network is not available", Toast.LENGTH_SHORT).show();
                }
            }


            //上拉加载和下拉刷新的监听
            ((PullToRefreshLayout) getActivity().findViewById(R.id.refresh_view))
                    .setOnRefreshListener(new MyListener());


            //右上角弹出的设置窗口
            view_pop = LayoutInflater.from(getActivity()).inflate(
                    R.layout.pop_menu, null);

            TextView pop1 = (TextView) view_pop.findViewById(R.id.pop_textView1);
            pop1.setText(sp.getString("SUserMessage",""));
            TextView pop2 = (TextView) view_pop.findViewById(R.id.pop_textView2);
            pop2.setText(sp.getString("SSet",""));
            TextView pop3= (TextView) view_pop.findViewById(R.id.pop_textView3);
            pop3.setText(sp.getString("SAbout",""));
            //帐户信息
            LinearLayout ll1 = (LinearLayout) view_pop.findViewById(R.id.ll_pop1);
            ll1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), InfoActivity.class);
                    startActivity(intent);
                }
            });
            //登录
            LinearLayout ll2 = (LinearLayout) view_pop.findViewById(R.id.ll_pop2);
            ll2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), LoginOneActivity.class);
                    startActivity(intent);
                }
            });
            //关于
            LinearLayout ll3 = (LinearLayout) view_pop.findViewById(R.id.ll_pop3);
            ll3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), AboutActivity.class);
                    startActivity(intent);
                }
            });

            iv_point.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    if (mPopupwinow == null) {
                        mPopupwinow = new PopupWindow(view_pop,
                                ViewGroup.LayoutParams.WRAP_CONTENT,
                                ViewGroup.LayoutParams.WRAP_CONTENT, true);
                        //设置pop window的背景颜色
                        mPopupwinow.setBackgroundDrawable(new ColorDrawable(
                                0x00000000));
                    }
                    //设置pop window的位置
                    mPopupwinow.showAsDropDown(iv_point, 0, 0);
                }
            });
        }
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                String sr = (String) msg.obj;
                Gson gson = new Gson();
                GetGroupOperationInfo bean = gson.fromJson(sr, GetGroupOperationInfo.class);

                try {
                    if (bean.getRet().equals("Success")) {
                        title_main.setText(title);
                        tv_title.setText(bean.getPageTitle() + "");
                        tv_tag.setText(bean.getGroupOperationInfo().getDayPowerGenerationStr() + "");
                        tv_kwh.setText(bean.getGroupOperationInfo().getDayPowerGenerationUnit() + "");
                        tv_tag_str.setText(bean.getGroupOperationInfo().getDayPowerGeneration() + "");
                        tv_tag2.setText(bean.getGroupOperationInfo().getYearPowerGenerationStr() + "");
                        tv_tag2_str.setText(bean.getGroupOperationInfo().getYearPowerGeneration() + "");
                        tv_kmh2.setText(bean.getGroupOperationInfo().getYearPowerGenerationUnit() + "");
                        tv_tag6.setText(bean.getGroupOperationInfo().getLastYearCompletionRateStr() + "");
                        tv_kwh6.setText(bean.getGroupOperationInfo().getLastYearCompletionRateUnit() + "");
                        tv_tag6_str.setText(bean.getGroupOperationInfo().getLastYearCompletionRate() + "");
                        tv_tag3.setText(bean.getGroupOperationInfo().getWorkingStationNumStr() + "");
                        tv_kwh3.setText(bean.getGroupOperationInfo().getWorkingStationNumUnit() + "");
                        tv_tag3_str.setText(bean.getGroupOperationInfo().getWorkingStationNum() + "");
                        tv_tag7.setText(bean.getGroupOperationInfo().getAllStationCapacityStr() + "");
                        tv_tag7_str.setText(bean.getGroupOperationInfo().getAllStationCapacity() + "");
                        tv_kwh7.setText(bean.getGroupOperationInfo().getAllStationCapacityUnit() + "");
                        tv_tag4.setText(bean.getGroupOperationInfo().getTotalBenefitStr());
                        tv_kwh4.setText(bean.getGroupOperationInfo().getTotalBenefitUnit());
                        tv_tag4_str.setText(bean.getGroupOperationInfo().getTotalBenefit());
                        if (language.equals("zh_cn")){
                            tv_tag5.setText(bean.getGroupOperationInfo().getEmissionsReductionStr());
                            tv_tag5_1.setVisibility(View.INVISIBLE);
                            tv_tag5_2.setVisibility(View.INVISIBLE);
                            tv_tag5_3.setVisibility(View.INVISIBLE);
                        }else {
                            String GET = bean.getGroupOperationInfo().getEmissionsReductionStr();
                            tv_tag5_1.setText(GET.substring(0,2));
                            tv_tag5_2.setText("2"+"");
                            tv_tag5_3.setText(GET.substring(3));
                            tv_tag5.setVisibility(View.INVISIBLE);
                        }

                        tv_kwh5.setText(bean.getGroupOperationInfo().getEmissionsReductionUnit());
                        tv_tag5_str.setText(bean.getGroupOperationInfo().getEmissionsReduction());
                        tv_kmh10.setText(bean.getGroupOperationInfo().getYearObjectiveGenerationUnit());
                        tv_tag10.setText(bean.getGroupOperationInfo().getYearObjectiveGenerationStr());
                        tv_tag10_str.setText(bean.getGroupOperationInfo().getYearObjectiveGeneration());
                        dialog.close();
                    } else {
                        dialog.close();

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
            super.handleMessage(msg);
        }
    };

    private class MyThread extends Thread {
        @Override
        public void run() {
            // TODO Auto-generated method stub
            SharedPreferences sp = getActivity().getSharedPreferences("Set", getActivity().MODE_APPEND);
            String ID = sp.getString("SessionID", "");
            SharedPreferences sp1 = getActivity().getSharedPreferences("IP", getActivity().MODE_APPEND);
            String ip = sp1.getString("Ip", "");

            JSONObject object = new JSONObject();
            try {
                object.put("Cmd", "GetGroupOperationInfo");
                object.put("Dev", "app");
                object.put("SessionID", ID);
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            String sr = Post.sendPost(
                    "http://" + ip + "/SPMSWcfService/SPMSMainHandler.ashx", object);
            Log.d("coming", sr);
            handler.sendMessage(handler.obtainMessage(1, sr));
        }

    }


    public class MyListener implements PullToRefreshLayout.OnRefreshListener {

        @Override
        public void onRefresh(final PullToRefreshLayout pullToRefreshLayout) {
            // 下拉刷新操作
            new Handler() {
                @Override
                public void handleMessage(Message msg) {

                    if (NetWorking.isNetworkAvailable(getActivity())) {
                        new MyThread().start();
                    } else {
                        if (language.equals("zh_cn")){
                            Toast.makeText(getActivity(), "当前网络不可用", Toast.LENGTH_SHORT).show();

                        }else {
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

                    if (NetWorking.isNetworkAvailable(getActivity())) {
                        new MyThread().start();
                    } else {
                        if (language.equals("zh_cn")){
                            Toast.makeText(getActivity(), "当前网络不可用", Toast.LENGTH_SHORT).show();

                        }else {
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
        Intent intent1 = new Intent("com.main");
        LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent1);

    }

}
