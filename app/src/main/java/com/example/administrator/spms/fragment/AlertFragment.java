package com.example.administrator.spms.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.spms.R;
import com.example.administrator.spms.activity.AboutActivity;
import com.example.administrator.spms.activity.InfoActivity;
import com.example.administrator.spms.activity.LoginActivity;
import com.example.administrator.spms.activity.LoginOneActivity;
import com.example.administrator.spms.activity.MainActivity;
import com.example.administrator.spms.adapter.AlertAdapter;
import com.example.administrator.spms.base.BaseFragment;
import com.example.administrator.spms.base.Loading;
import com.example.administrator.spms.base.LoadingDialog;
import com.example.administrator.spms.base.NetWorking;
import com.example.administrator.spms.base.Post;
import com.example.administrator.spms.model.AlertBean;
import com.example.administrator.spms.model.ButtonBean;
import com.example.administrator.spms.model.GetDeviceStatusListBean;
import com.example.administrator.spms.model.GetStationDeviceListBean;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

/**
 * Created by Administrator on 2016/10/24.
 * 报警界面
 */
@ContentView(R.layout.fragment_alarm)
public class AlertFragment extends BaseFragment implements View.OnClickListener {
    private String StationID = "";
    private String DeviceID = "";
    private String SSID = "";
    private String DDID = "";
    private ArrayAdapter<String> adapter;
    private ArrayAdapter<String> adapter1;
    private ArrayAdapter<String> adapter3;
    private ArrayAdapter<String> adapter0;
    private ArrayAdapter<String> adapter7, adapter8;
    @ViewInject(R.id.tv_title_alert)
    private TextView tv_title_alert;

    private String title_main;

    //显示报警列表的适配器
    private AlertAdapter alertAdapter;
    private Handler handler8;
    //菜单
    @ViewInject(R.id.iv_menu_alert)
    private ImageView iv_menu;
    private View view_pop;
    private PopupWindow mPopupwinow = null;

    @ViewInject(R.id.spinner1)
    private Spinner spinner;

    @ViewInject(R.id.spinner2)
    private Spinner spinner2;

    @ViewInject(R.id.spinner3)
    private Spinner spinner3;

    @ViewInject(R.id.tv_alert_title)
    private TextView tv_alert_title;

    @ViewInject(R.id.lv_alarm)
    private ListView lv;
    private LoadingDialog dialog;
    //时间日期
    @ViewInject(R.id.tv_alarm_1)
    private TextView tv1;
    @ViewInject(R.id.tv_alarm_1_1)
    private TextView tv1_1;
    //电站
    @ViewInject(R.id.tv_alarm_2)
    private TextView tv2;
    @ViewInject(R.id.tv_alarm_2_1)
    private TextView tv2_1;
    //设备
    @ViewInject(R.id.tv_alarm_3)
    private TextView tv3;
    @ViewInject(R.id.tv_alarm_3_1)
    private TextView tv3_1;
    //报警
    @ViewInject(R.id.tv_alarm_4)
    private TextView tv4;
    @ViewInject(R.id.tv_alarm_4_1)
    private TextView tv4_1;
    //确认
    @ViewInject(R.id.tv_alarm_5)
    private TextView tv5;
    @ViewInject(R.id.tv_alarm_5_1)
    private TextView tv5_1;
    @ViewInject(R.id.btn_alarm_find)
    private Button btn;
    //监听日期和时间
    @ViewInject(R.id.ll1)
    private RelativeLayout rl_ll1;
    @ViewInject(R.id.iv_ll1)
    private ImageView iv_ll1;
    private boolean l1 = true;
    //电站
    @ViewInject(R.id.ll2)
    private RelativeLayout rl_ll2;
    @ViewInject(R.id.iv_ll2)
    private ImageView iv_ll2;
    private boolean l2 = true;
    //设备
    @ViewInject(R.id.ll3)
    private RelativeLayout rl_ll3;
    @ViewInject(R.id.iv_ll3)
    private ImageView iv_ll3;
    private boolean l3 = true;

    //站点
    @ViewInject(R.id.ll4)
    private RelativeLayout rl_ll4;
    @ViewInject(R.id.iv_ll4)
    private ImageView iv_ll4;
    private boolean l4 = true;
    //描述
    @ViewInject(R.id.ll5)
    private RelativeLayout rl_ll5;
    @ViewInject(R.id.iv_ll5)
    private ImageView iv_ll5;
    private boolean l5 = true;

    //搜索的按钮
    @ViewInject(R.id.iv_search_alarm)
    private ImageView iv_search;

    //定义广播管理器，广播接收器 用来接收
    private LocalBroadcastManager broadcastManager, broadcastManager2;
    private IntentFilter intentFilter, intentFilter2;
    private BroadcastReceiver mReceiver, mReceiver2;
    private String getStationID = "";
    private String getDeviceID = "";
    private String ID, ip;
    //接收解析后的str
    private String sr5, sr3, sr4, sr6, sr7;
    private int num = 0;

    //搜索绑数据
    @ViewInject(R.id.lv_alarms)
    private ListView lvs;
    private LoadingDialog dialogs;
    //时间日期
    @ViewInject(R.id.tv_alarm_1s)
    private TextView tv1s;
    @ViewInject(R.id.tv_alarm_1_1s)
    private TextView tv1_1s;
    //电站
    @ViewInject(R.id.tv_alarm_2s)
    private TextView tv2s;
    @ViewInject(R.id.tv_alarm_2_1s)
    private TextView tv2_1s;
    //设备
    @ViewInject(R.id.tv_alarm_3s)
    private TextView tv3s;
    @ViewInject(R.id.tv_alarm_3_1s)
    private TextView tv3_1s;
    //报警
    @ViewInject(R.id.tv_alarm_4s)
    private TextView tv4s;
    @ViewInject(R.id.tv_alarm_4_1s)
    private TextView tv4_1s;
    //确认
    @ViewInject(R.id.tv_alarm_5s)
    private TextView tv5s;
    @ViewInject(R.id.tv_alarm_5_1s)
    private TextView tv5_1s;

    //监听日期和时间
    @ViewInject(R.id.ll1s)
    private RelativeLayout rl_ll1s;
    @ViewInject(R.id.iv_ll1s)
    private ImageView iv_ll1s;
    private boolean l1s = true;
    //电站
    @ViewInject(R.id.ll2s)
    private RelativeLayout rl_ll2s;
    @ViewInject(R.id.iv_ll2s)
    private ImageView iv_ll2s;
    private boolean l2s = true;
    //设备
    @ViewInject(R.id.ll3s)
    private RelativeLayout rl_ll3s;
    @ViewInject(R.id.iv_ll3s)
    private ImageView iv_ll3s;
    private boolean l3s = true;

    //站点
    @ViewInject(R.id.ll4s)
    private RelativeLayout rl_ll4s;
    @ViewInject(R.id.iv_ll4s)
    private ImageView iv_ll4s;
    private boolean l4s = true;
    //描述
    @ViewInject(R.id.ll5s)
    private RelativeLayout rl_ll5s;
    @ViewInject(R.id.iv_ll5s)
    private ImageView iv_ll5s;
    private boolean l5s = true;
    private String Searchs;
    @ViewInject(R.id.search_btn_backs)
    private Button btn_searchs;
    @ViewInject(R.id.search_et_inputs)
    private EditText et_inputs;
    @ViewInject(R.id.iv_back_equipments)
    private ImageView iv_backs;


    //报警详细和报警搜索的布局
    @ViewInject(R.id.ll_alert1)
    private LinearLayout ll_alert1;
    @ViewInject(R.id.ll_alert2)
    private LinearLayout ll_alert2;

    @ViewInject(R.id.search_iv_deletes)
    private ImageView ivDelete;

    private String device = "";
    private String level;


    private String language;

    //spinner的Bean
    private GetStationDeviceListBean AlarmBean;

    private String spDevice;
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SharedPreferences sp = getActivity().getSharedPreferences("Set", getActivity().MODE_APPEND);
        ID = sp.getString("SessionID", "");
        SharedPreferences sp1 = getActivity().getSharedPreferences("IP", getActivity().MODE_APPEND);
        ip = sp1.getString("Ip", "");
        //取系统语言
        SharedPreferences sp3 = getActivity().getSharedPreferences("language", getActivity().MODE_APPEND);
        language = sp3.getString("language", "");
        //拿页面标题
        SharedPreferences sp4 = getActivity().getSharedPreferences("title", getActivity().MODE_APPEND);
        title_main = sp4.getString("title", "");
        tv_title_alert.setText(title_main);
        //获取用户等级
        SharedPreferences sp2 = getActivity().getSharedPreferences("Set", getActivity().MODE_APPEND);
        level = sp2.getString("Level", "");

        new MyThread33().start();


        if (level.equals("3")) {

            //查询的线程
            new MyThread22().start();

        } else {
            if (NetWorking.isNetworkAvailable(getActivity())) {
                //拉取列表数据
                new MyThread().start();

                //查询的线程 spinner里面的东西
                new MyThread2().start();
            } else {
                if (language.equals("zh_cn")) {
                    Toast.makeText(getActivity(), "当前网络不可用", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(getActivity(), "The current network is not available", Toast.LENGTH_SHORT).show();
                }
            }
        }
        dialog = new LoadingDialog(getActivity(), Loading.getText(getActivity()));
        //显示Dialog
        //  dialog.show();


        //右上角弹出的设置窗口
        view_pop = LayoutInflater.from(getActivity()).inflate(
                R.layout.pop_menu, null);
        TextView pop1 = (TextView) view_pop.findViewById(R.id.pop_textView1);
        pop1.setText(sp.getString("SUserMessage", ""));
        TextView pop2 = (TextView) view_pop.findViewById(R.id.pop_textView2);
        pop2.setText(sp.getString("SSet", ""));
        TextView pop3 = (TextView) view_pop.findViewById(R.id.pop_textView3);
        pop3.setText(sp.getString("SAbout", ""));



        //帐户信息pop
        LinearLayout ll1 = (LinearLayout) view_pop.findViewById(R.id.ll_pop1);
        ll1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), InfoActivity.class);
                startActivity(intent);
            }
        });
        //登录pop
        LinearLayout ll2 = (LinearLayout) view_pop.findViewById(R.id.ll_pop2);
        ll2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), LoginOneActivity.class);
                startActivity(intent);
            }
        });
        //关于pop
        LinearLayout ll3 = (LinearLayout) view_pop.findViewById(R.id.ll_pop3);
        ll3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AboutActivity.class);
                startActivity(intent);
            }
        });

        //放大镜
        iv_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent("com.search8");
                LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent2);
                iv_menu.setVisibility(GONE);
                iv_search.setVisibility(GONE);
                iv_backs.setVisibility(VISIBLE);
                ll_alert1.setVisibility(GONE);
                ll_alert2.setVisibility(VISIBLE);
                num = 1;
            }
        });


        iv_menu.setOnClickListener(new View.OnClickListener() {
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
                mPopupwinow.showAsDropDown(iv_menu, 0, 0);
            }
        });

       //实时报警页面时间日期 电站等 按钮
        rl_ll1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (l1) {
                    dialog = new LoadingDialog(getActivity(), Loading.getText(getActivity()));
                    dialog.show();
                    handler.sendMessage(handler.obtainMessage(2, "asc"));
                    tv1_1.setVisibility(VISIBLE);
                    tv1.setVisibility(GONE);
                    iv_ll1.setImageResource(R.mipmap.lanhui);
                    l1 = false;

                } else {
                    dialog = new LoadingDialog(getActivity(), Loading.getText(getActivity()));
                    dialog.show();
                    handler.sendMessage(handler.obtainMessage(2, "desc"));
                    tv1.setVisibility(VISIBLE);
                    tv1_1.setVisibility(GONE);
                    iv_ll1.setImageResource(R.mipmap.huilan);
                    l1 = true;
                }

            }
        });


        rl_ll2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (l2) {
                    dialog = new LoadingDialog(getActivity(), Loading.getText(getActivity()));
                    dialog.show();
                    handler.sendMessage(handler.obtainMessage(3, "asc"));
                    tv2_1.setVisibility(VISIBLE);
                    tv2.setVisibility(GONE);
                    iv_ll2.setImageResource(R.mipmap.lanhui);
                    l2 = false;
                } else {
                    dialog = new LoadingDialog(getActivity(), Loading.getText(getActivity()));
                    dialog.show();
                    handler.sendMessage(handler.obtainMessage(3, "desc"));
                    tv2.setVisibility(VISIBLE);
                    tv2_1.setVisibility(GONE);
                    iv_ll2.setImageResource(R.mipmap.huilan);
                    l2 = true;
                }

            }
        });


        rl_ll3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (l3) {
                    dialog = new LoadingDialog(getActivity(), Loading.getText(getActivity()));
                    dialog.show();
                    handler.sendMessage(handler.obtainMessage(4, "asc"));
                    tv3_1.setVisibility(VISIBLE);
                    tv3.setVisibility(GONE);
                    iv_ll3.setImageResource(R.mipmap.lanhui);
                    l3 = false;
                } else {
                    dialog = new LoadingDialog(getActivity(), Loading.getText(getActivity()));
                    dialog.show();
                    handler.sendMessage(handler.obtainMessage(4, "desc"));
                    tv3.setVisibility(VISIBLE);
                    tv3_1.setVisibility(GONE);
                    iv_ll3.setImageResource(R.mipmap.huilan);
                    l3 = true;
                }

            }
        });


        rl_ll4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (l4) {
                    dialog = new LoadingDialog(getActivity(), Loading.getText(getActivity()));
                    dialog.show();
                    handler.sendMessage(handler.obtainMessage(5, "asc"));
                    tv4_1.setVisibility(VISIBLE);
                    tv4.setVisibility(GONE);
                    iv_ll4.setImageResource(R.mipmap.lanhui);
                    l4 = false;
                } else {
                    dialog = new LoadingDialog(getActivity(), Loading.getText(getActivity()));
                    dialog.show();
                    handler.sendMessage(handler.obtainMessage(5, "desc"));
                    tv4.setVisibility(VISIBLE);
                    tv4_1.setVisibility(GONE);
                    iv_ll4.setImageResource(R.mipmap.huilan);
                    l4 = true;
                }

            }
        });


        rl_ll5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (l5) {
                    dialog = new LoadingDialog(getActivity(), Loading.getText(getActivity()));
                    dialog.show();
                    handler.sendMessage(handler.obtainMessage(7, "asc"));
                    tv5_1.setVisibility(VISIBLE);
                    tv5.setVisibility(GONE);
                    iv_ll5.setImageResource(R.mipmap.lanhui);
                    l5 = false;
                } else {
                    dialog = new LoadingDialog(getActivity(), Loading.getText(getActivity()));
                    dialog.show();
                    handler.sendMessage(handler.obtainMessage(7, "desc"));
                    tv5.setVisibility(VISIBLE);
                    tv5_1.setVisibility(GONE);
                    iv_ll5.setImageResource(R.mipmap.huilan);
                    l5 = true;
                }

            }
        });


        //查询按键
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = new LoadingDialog(getActivity(), Loading.getText(getActivity()));
                dialog.show();
                handler.sendMessage(handler.obtainMessage(8, ""));
            }
        });
        spDevice = "";

        //点红点进发过来的 广播
        broadcastManager = LocalBroadcastManager.getInstance(getActivity());
        intentFilter = new IntentFilter();
        intentFilter.addAction("MainActivity.broadcast.alert");
        // intentFilter.addAction("com.example.administrator.myapplication.main.main.home.broadcast");
        mReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                StationID = intent.getStringExtra("se");

                DeviceID = intent.getStringExtra("id");

                handler.sendMessage(handler.obtainMessage(8, ""));

                SSID = intent.getStringExtra("se");
                DDID = intent.getStringExtra("id");
                spDevice = intent.getStringExtra("device");

                if (spDevice.equals("")||spDevice.equals("all")){
                    device = "";
                }else {
                    device = "Dev_Inverter";
                }


                new MyThread2().start();
//                handler.handleMessage(handler.obtainMessage(6, "2"));
                Log.d("321saxaw", StationID + "@!" + DeviceID);
                Log.d("321saxaw1", SSID+ "@!" + DDID );
                iv_menu.setVisibility(VISIBLE);
                iv_search.setVisibility(VISIBLE);
                iv_backs.setVisibility(GONE);
                ll_alert1.setVisibility(VISIBLE);
                ll_alert2.setVisibility(GONE);
                et_inputs.setText("");
                lvs.setVisibility(GONE);

            }
        };
        broadcastManager.registerReceiver(mReceiver, intentFilter);


//        //点返回键的广播
        broadcastManager2 = LocalBroadcastManager.getInstance(getActivity());
        intentFilter2 = new IntentFilter();
        intentFilter2.addAction("com.search9");
        // intentFilter.addAction("com.example.administrator.myapplication.main.main.home.broadcast");
        mReceiver2 = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                iv_menu.setVisibility(VISIBLE);
                iv_search.setVisibility(VISIBLE);
                iv_backs.setVisibility(GONE);
                ll_alert1.setVisibility(VISIBLE);
                ll_alert2.setVisibility(GONE);
                et_inputs.setText("");
                lvs.setVisibility(GONE);
            }
        };
        broadcastManager2.registerReceiver(mReceiver2, intentFilter2);


        //搜索返回键的监听
        iv_backs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iv_menu.setVisibility(VISIBLE);
                iv_search.setVisibility(VISIBLE);
                iv_backs.setVisibility(GONE);
                ll_alert1.setVisibility(VISIBLE);
                ll_alert2.setVisibility(GONE);
                et_inputs.setText("");
                lvs.setVisibility(GONE);
                num = 0;
            }
        });

        //搜索页面的 5个按钮
        rl_ll1s.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (NetWorking.isNetworkAvailable(getActivity())) {
                    Searchs = et_inputs.getText().toString();
                    if (Searchs.trim().equals("") || Searchs.trim().equals("null")) {

                    } else {
                        if (l1s) {
                            dialogs = new LoadingDialog(getActivity(), Loading.getText(getActivity()));
                            dialogs.show();
                            handlers.sendMessage(handlers.obtainMessage(2, "asc"));
                            tv1_1s.setVisibility(VISIBLE);
                            tv1s.setVisibility(GONE);
                            iv_ll1s.setImageResource(R.mipmap.lanhui);
                            l1s = false;

                        } else {
                            dialogs = new LoadingDialog(getActivity(), Loading.getText(getActivity()));
                            dialogs.show();
                            handlers.sendMessage(handlers.obtainMessage(2, "desc"));
                            tv1s.setVisibility(VISIBLE);
                            tv1_1s.setVisibility(GONE);
                            iv_ll1s.setImageResource(R.mipmap.huilan);
                            l1s = true;
                        }
                    }


                } else {
                    if (language.equals("zh_cn")) {
                        Toast.makeText(getActivity(), "当前网络不可用", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(getActivity(), "The current network is not available", Toast.LENGTH_SHORT).show();
                    }
                }


            }
        });


        rl_ll2s.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (NetWorking.isNetworkAvailable(getActivity())) {
                    Searchs = et_inputs.getText().toString();
                    if (Searchs.trim().equals("") || Searchs.trim().equals("null")) {

                    } else {
                        if (l2s) {
                            dialogs = new LoadingDialog(getActivity(), Loading.getText(getActivity()));
                            dialogs.show();
                            handlers.sendMessage(handlers.obtainMessage(3, "asc"));
                            tv2_1s.setVisibility(VISIBLE);
                            tv2s.setVisibility(GONE);
                            iv_ll2s.setImageResource(R.mipmap.lanhui);
                            l2s = false;
                        } else {
                            dialogs = new LoadingDialog(getActivity(), Loading.getText(getActivity()));
                            dialogs.show();
                            handlers.sendMessage(handlers.obtainMessage(3, "desc"));
                            tv2s.setVisibility(VISIBLE);
                            tv2_1s.setVisibility(GONE);
                            iv_ll2s.setImageResource(R.mipmap.huilan);
                            l2s = true;
                        }
                    }

                } else {
                    if (language.equals("zh_cn")) {
                        Toast.makeText(getActivity(), "当前网络不可用", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(getActivity(), "The current network is not available", Toast.LENGTH_SHORT).show();
                    }
                }


            }
        });


        rl_ll3s.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (NetWorking.isNetworkAvailable(getActivity())) {
                    Searchs = et_inputs.getText().toString();
                    if (Searchs.trim().equals("") || Searchs.trim().equals("null")) {

                    } else {
                        if (l3s) {
                            dialogs = new LoadingDialog(getActivity(), Loading.getText(getActivity()));
                            dialogs.show();
                            handlers.sendMessage(handlers.obtainMessage(4, "asc"));
                            tv3_1s.setVisibility(VISIBLE);
                            tv3s.setVisibility(GONE);
                            iv_ll3s.setImageResource(R.mipmap.lanhui);
                            l3s = false;
                        } else {
                            dialogs = new LoadingDialog(getActivity(), Loading.getText(getActivity()));
                            dialogs.show();
                            handlers.sendMessage(handlers.obtainMessage(4, "desc"));
                            tv3s.setVisibility(VISIBLE);
                            tv3_1s.setVisibility(GONE);
                            iv_ll3s.setImageResource(R.mipmap.huilan);
                            l3s = true;
                        }
                    }


                } else {
                    if (language.equals("zh_cn")) {
                        Toast.makeText(getActivity(), "当前网络不可用", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(getActivity(), "The current network is not available", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });


        rl_ll4s.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (NetWorking.isNetworkAvailable(getActivity())) {
                    Searchs = et_inputs.getText().toString();
                    if (Searchs.trim().equals("") || Searchs.trim().equals("null")) {

                    } else {
                        if (l4s) {
                            dialogs = new LoadingDialog(getActivity(), Loading.getText(getActivity()));
                            dialogs.show();
                            handlers.sendMessage(handlers.obtainMessage(5, "asc"));
                            tv4_1s.setVisibility(VISIBLE);
                            tv4s.setVisibility(GONE);
                            iv_ll4s.setImageResource(R.mipmap.lanhui);
                            l4s = false;
                        } else {
                            dialogs = new LoadingDialog(getActivity(), Loading.getText(getActivity()));
                            dialogs.show();
                            handlers.sendMessage(handlers.obtainMessage(5, "desc"));
                            tv4s.setVisibility(VISIBLE);
                            tv4_1s.setVisibility(GONE);
                            iv_ll4s.setImageResource(R.mipmap.huilan);
                            l4s = true;
                        }
                    }

                } else {
                    if (language.equals("zh_cn")) {
                        Toast.makeText(getActivity(), "当前网络不可用", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(getActivity(), "The current network is not available", Toast.LENGTH_SHORT).show();
                    }
                }


            }
        });


        rl_ll5s.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (NetWorking.isNetworkAvailable(getActivity())) {
                    Searchs = et_inputs.getText().toString();
                    if (Searchs.trim().equals("") || Searchs.trim().equals("null")) {

                    } else {
                        if (l5s) {
                            dialogs = new LoadingDialog(getActivity(), Loading.getText(getActivity()));
                            dialogs.show();
                            handlers.sendMessage(handlers.obtainMessage(7, "asc"));
                            tv5_1s.setVisibility(VISIBLE);
                            tv5s.setVisibility(GONE);
                            iv_ll5s.setImageResource(R.mipmap.lanhui);
                            l5s = false;
                        } else {
                            dialogs = new LoadingDialog(getActivity(), Loading.getText(getActivity()));
                            dialogs.show();
                            handlers.sendMessage(handlers.obtainMessage(7, "desc"));
                            tv5s.setVisibility(VISIBLE);
                            tv5_1s.setVisibility(GONE);
                            iv_ll5s.setImageResource(R.mipmap.huilan);
                            l5s = true;
                        }
                    }

                } else {
                    if (language.equals("zh_cn")) {
                        Toast.makeText(getActivity(), "当前网络不可用", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(getActivity(), "The current network is not available", Toast.LENGTH_SHORT).show();
                    }
                }


            }
        });



        if (language.equals("zh_cn")) {
            et_inputs.setHint("请输入关键字");
            btn_searchs.setText("搜索");
        } else {
            et_inputs.setHint("Please enter a keyword");
            btn_searchs.setText("search");
        }


        //搜索页面的搜索按钮
        btn_searchs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Searchs = et_inputs.getText().toString();
                if (Searchs.trim().equals("") || Searchs.trim().equals("null")) {
                    lvs.setVisibility(GONE);
                } else {
                    if (NetWorking.isNetworkAvailable(getActivity())) {
                        dialogs = new LoadingDialog(getActivity(), Loading.getText(getActivity()));
                        dialogs.show();
                        handlers.sendMessage(handlers.obtainMessage(2, "asc"));
                        lvs.setVisibility(VISIBLE);
                    } else {
                        if (language.equals("zh_cn")) {
                            Toast.makeText(getActivity(), "当前网络不可用", Toast.LENGTH_SHORT).show();

                        } else {
                            Toast.makeText(getActivity(), "The current network is not available", Toast.LENGTH_SHORT).show();
                        }
                    }

                }

            }
        });

        et_inputs.addTextChangedListener(new EditChangedListener());
        et_inputs.setOnClickListener(this);
        //点击键盘搜索按钮
        et_inputs.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                Searchs = et_inputs.getText().toString();
                if (Searchs.equals("") || Searchs.equals("null")) {



                } else {

                    if (NetWorking.isNetworkAvailable(getActivity())) {
                        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                            Searchs = et_inputs.getText().toString();
                            dialogs = new LoadingDialog(getActivity(), Loading.getText(getActivity()));
                            dialogs.show();
                            handlers.sendMessage(handlers.obtainMessage(2, "asc"));
                            lvs.setVisibility(VISIBLE);
                        }
                    } else {
                        if (language.equals("zh_cn")) {
                            Toast.makeText(getActivity(), "当前网络不可用", Toast.LENGTH_SHORT).show();

                        } else {
                            Toast.makeText(getActivity(), "The current network is not available", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                return true;
            }
        });


        ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_inputs.setText("");
                ivDelete.setVisibility(GONE);
            }
        });

    }

    @Override
    public void onClick(View v) {

    }

    private class EditChangedListener implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            if (!"".equals(charSequence.toString())) {
                ivDelete.setVisibility(VISIBLE);

            } else {
                ivDelete.setVisibility(GONE);

            }

        }

        @Override
        public void afterTextChanged(Editable editable) {
        }
    }

    /**
     * //搜索用的
     */

    Handler handlers = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {

                if (language.equals("zh_cn")) {
                    Toast.makeText(getActivity(), "完成搜索", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(getActivity(), "Complete search", Toast.LENGTH_SHORT).show();
                }

                String sr = (String) msg.obj;
                Gson gson = new Gson();
                AlertBean bean = gson.fromJson(sr, AlertBean.class);
                Log.d("xzxjzcnzsw", String.valueOf(bean));
                if (bean.getRet().equals("Success")) {

                    AlertAdapter adapter = new AlertAdapter(getActivity(), bean.getRealTimeAlarmsInfo(), R.layout.item_alert);
                    lvs.setAdapter(adapter);
                    dialogs.close();
                } else {
                    dialogs.close();
                    if (bean.getErrCode().equals("1")) {
                        if (language.equals("zh_cn")) {
                            Toast.makeText(getActivity(), "您的登录已失效，请重新登录", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getActivity(),LoginActivity.class);
                            startActivity(intent);
                            sendBroadcast();
                        } else {
                            Toast.makeText(getActivity(), "Your login has expired, please log in again", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        if (language.equals("zh_cn")) {
                            Toast.makeText(getActivity(), "连接服务器失败", Toast.LENGTH_SHORT).show();

                        } else {
                            Toast.makeText(getActivity(), "Connection server failed ", Toast.LENGTH_SHORT).show();
                        }
                    }
                }


            }
            if (msg.what == 2) {
                final String sr = (String) msg.obj;
                class MyThread extends Thread {
                    @Override
                    public void run() {

                        SharedPreferences sp = getActivity().getSharedPreferences("Set", getActivity().MODE_APPEND);
                        String ID = sp.getString("SessionID", "");
                        SharedPreferences sp1 = getActivity().getSharedPreferences("IP", getActivity().MODE_APPEND);
                        String ip = sp1.getString("Ip", "");
                        JSONObject object = new JSONObject();
                        try {
                            object.put("Cmd", "GetRealTimeAlarmsInfo");
                            object.put("Dev", "app");
                            object.put("SessionID", ID);
                            object.put("StationID", "");
                            object.put("DeviceType", "");
                            object.put("DeviceID", "");
                            object.put("SearchStr", Searchs);
                            object.put("SortField", "AlarmDateTime");
                            object.put("SortType", sr);

                        } catch (JSONException e) {

                            e.printStackTrace();
                        }
                        String sr = Post.sendPost(
                                "http://" + ip + "/SPMSWcfService/SPMSMainHandler.ashx", object);
                        handlers.sendMessage(handlers.obtainMessage(1, sr));
                    }
                }
                new MyThread().start();
            }
            if (msg.what == 3) {
                final String sr = (String) msg.obj;
                class MyThread extends Thread {
                    @Override
                    public void run() {

                        SharedPreferences sp = getActivity().getSharedPreferences("Set", getActivity().MODE_APPEND);
                        String ID = sp.getString("SessionID", "");
                        SharedPreferences sp1 = getActivity().getSharedPreferences("IP", getActivity().MODE_APPEND);
                        String ip = sp1.getString("Ip", "");
                        JSONObject object = new JSONObject();
                        try {
                            object.put("Cmd", "GetRealTimeAlarmsInfo");
                            object.put("Dev", "app");
                            object.put("SessionID", ID);
                            object.put("StationID", "");
                            object.put("DeviceType", "");
                            object.put("DeviceID", "");
                            object.put("SearchStr", Searchs);
                            object.put("SortField", "StationName");
                            object.put("SortType", sr);

                        } catch (JSONException e) {

                            e.printStackTrace();
                        }
                        String sr = Post.sendPost(
                                "http://" + ip + "/SPMSWcfService/SPMSMainHandler.ashx", object);
                        handlers.sendMessage(handlers.obtainMessage(1, sr));
                    }
                }
                new MyThread().start();
            }
            if (msg.what == 4) {
                final String sr = (String) msg.obj;
                class MyThread extends Thread {
                    @Override
                    public void run() {
                        SharedPreferences sp = getActivity().getSharedPreferences("Set", getActivity().MODE_APPEND);
                        String ID = sp.getString("SessionID", "");
                        SharedPreferences sp1 = getActivity().getSharedPreferences("IP", getActivity().MODE_APPEND);
                        String ip = sp1.getString("Ip", "");
                        JSONObject object = new JSONObject();
                        try {
                            object.put("Cmd", "GetRealTimeAlarmsInfo");
                            object.put("Dev", "app");
                            object.put("SessionID", ID);
                            object.put("StationID", "");
                            object.put("DeviceType", "");
                            object.put("DeviceID", "");
                            object.put("SearchStr", Searchs);
                            object.put("SortField", "DeviceName");
                            object.put("SortType", sr);

                        } catch (JSONException e) {

                            e.printStackTrace();
                        }
                        String sr = Post.sendPost(
                                "http://" + ip + "/SPMSWcfService/SPMSMainHandler.ashx", object);
                        handlers.sendMessage(handlers.obtainMessage(1, sr));
                    }
                }

                if (NetWorking.isNetworkAvailable(getActivity())) {
                    new MyThread().start();
                } else {
                    if (language.equals("zh_cn")) {
                        Toast.makeText(getActivity(), "当前网络不可用", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(getActivity(), "The current network is not available", Toast.LENGTH_SHORT).show();
                    }
                }

            }
            if (msg.what == 5) {
                final String sr = (String) msg.obj;
                class MyThread extends Thread {
                    @Override
                    public void run() {

                        SharedPreferences sp = getActivity().getSharedPreferences("Set", getActivity().MODE_APPEND);
                        String ID = sp.getString("SessionID", "");
                        SharedPreferences sp1 = getActivity().getSharedPreferences("IP", getActivity().MODE_APPEND);
                        String ip = sp1.getString("Ip", "");
                        JSONObject object = new JSONObject();
                        try {
                            object.put("Cmd", "GetRealTimeAlarmsInfo");
                            object.put("Dev", "app");
                            object.put("SessionID", ID);
                            object.put("StationID", "");
                            object.put("DeviceType", "");
                            object.put("DeviceID", "");
                            object.put("SearchStr", Searchs);
                            object.put("SortField", "AlarmTagName");
                            object.put("SortType", sr);

                        } catch (JSONException e) {

                            e.printStackTrace();
                        }
                        String sr = Post.sendPost(
                                "http://" + ip + "/SPMSWcfService/SPMSMainHandler.ashx", object);
                        handlers.sendMessage(handlers.obtainMessage(1, sr));
                    }
                }

                if (NetWorking.isNetworkAvailable(getActivity())) {
                    new MyThread().start();
                } else {
                    if (language.equals("zh_cn")) {
                        Toast.makeText(getActivity(), "当前网络不可用", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(getActivity(), "The current network is not available", Toast.LENGTH_SHORT).show();
                    }
                }

            }
            if (msg.what == 6) {


            }
            if (msg.what == 8) {
                class MyThread extends Thread {
                    @Override
                    public void run() {
                        SharedPreferences sp = getActivity().getSharedPreferences("Set", getActivity().MODE_APPEND);
                        String ID = sp.getString("SessionID", "");
                        SharedPreferences sp1 = getActivity().getSharedPreferences("IP", getActivity().MODE_APPEND);
                        String ip = sp1.getString("Ip", "");
                        JSONObject object = new JSONObject();
                        try {
                            object.put("Cmd", "GetRealTimeAlarmsInfo");
                            object.put("Dev", "app");
                            object.put("SessionID", ID);
                            object.put("StationID", "");
                            object.put("DeviceType", "");
                            object.put("DeviceID", "");
                            object.put("SearchStr", Searchs);
                            object.put("SortField", "AlarmDateTime");
                            object.put("SortType", "desc");

                        } catch (JSONException e) {

                            e.printStackTrace();
                        }
                        String sr = Post.sendPost(
                                "http://" + ip + "/SPMSWcfService/SPMSMainHandler.ashx", object);
                        handlers.sendMessage(handlers.obtainMessage(1, sr));
                    }
                }

                if (NetWorking.isNetworkAvailable(getActivity())) {
                    new MyThread().start();
                } else {
                    if (language.equals("zh_cn")) {
                        Toast.makeText(getActivity(), "当前网络不可用", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(getActivity(), "The current network is not available", Toast.LENGTH_SHORT).show();
                    }
                }


            }
            if (msg.what == 7) {
                final String sr = (String) msg.obj;
                class MyThread extends Thread {
                    @Override
                    public void run() {

                        SharedPreferences sp = getActivity().getSharedPreferences("Set", getActivity().MODE_APPEND);
                        String ID = sp.getString("SessionID", "");
                        SharedPreferences sp1 = getActivity().getSharedPreferences("IP", getActivity().MODE_APPEND);
                        String ip = sp1.getString("Ip", "");
                        JSONObject object = new JSONObject();
                        try {
                            object.put("Cmd", "GetRealTimeAlarmsInfo");
                            object.put("Dev", "app");
                            object.put("SessionID", ID);
                            object.put("StationID", "");
                            object.put("DeviceType", "");
                            object.put("DeviceID", "");
                            object.put("SearchStr", Searchs);
                            object.put("SortField", "AlarmDesc");
                            object.put("SortType", sr);

                        } catch (JSONException e) {

                            e.printStackTrace();
                        }
                        String sr = Post.sendPost(
                                "http://" + ip + "/SPMSWcfService/SPMSMainHandler.ashx", object);
                        handlers.sendMessage(handlers.obtainMessage(1, sr));
                    }
                }

                if (NetWorking.isNetworkAvailable(getActivity())) {
                    new MyThread().start();
                } else {
                    if (language.equals("zh_cn")) {
                        Toast.makeText(getActivity(), "当前网络不可用", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(getActivity(), "The current network is not available", Toast.LENGTH_SHORT).show();
                    }
                }

            }
            super.handleMessage(msg);
        }
    };


    //请求具体报警信息的list
    private class MyThread extends Thread {
        @Override
        public void run() {


            JSONObject object = new JSONObject();
            try {
                object.put("Cmd", "GetRealTimeAlarmsInfo");
                object.put("Dev", "app");
                object.put("SessionID", ID);
                object.put("StationID", StationID);
                object.put("DeviceType", device);
                object.put("DeviceID", DeviceID);
                object.put("SearchStr", "");
                object.put("SortField", "AlarmDateTime");
                object.put("SortType", "desc");

            } catch (JSONException e) {

                e.printStackTrace();
            }
            String sr = Post.sendPost(
                    "http://" + ip + "/SPMSWcfService/SPMSMainHandler.ashx", object);
            Log.d("coming123", sr + ID);

            handler.sendMessage(handler.obtainMessage(1, sr));

            if (level.equals("3")) {

                //查询的线程
                device = "";
                DeviceID = "";

            } else {
                StationID = "";
                device = "";
                DeviceID = "";
            }

        }

    }

    /**
     * //报警页面用的
     */
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                Log.d("12345678", StationID + "sdasdwa" + device + "sadad" + DeviceID);
                String sr = (String) msg.obj;
                Gson gson = new Gson();
                AlertBean bean = gson.fromJson(sr, AlertBean.class);
                try {
                    if (bean.getRet().equals("Success")) {
                        tv_alert_title.setText(bean.getPageTitle());
                        AlertAdapter adapter = new AlertAdapter(getActivity(), bean.getRealTimeAlarmsInfo(), R.layout.item_alert);
                        lv.setAdapter(adapter);
                        dialog.close();
                    } else {
                        dialog.close();
                        if (bean.getErrCode().equals("1")) {
                            if (language.equals("zh_cn")) {
                                Toast.makeText(getActivity(), "您的登录已失效，请重新登录", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getActivity(),LoginActivity.class);
                                startActivity(intent);
                                sendBroadcast();
                            } else {
                                Toast.makeText(getActivity(), "Your login has expired, please log in again", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            if (language.equals("zh_cn")) {
                                Toast.makeText(getActivity(), "连接服务器失败", Toast.LENGTH_SHORT).show();

                            } else {
                                Toast.makeText(getActivity(), "Connection server failed ", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                } catch (Exception e) {

                }
            }
            if (msg.what == 2) {
                sr5 = (String) msg.obj;

                if (NetWorking.isNetworkAvailable(getActivity())) {
                    new MyThread5().start();
                } else {
                    dialog.close();
                    if (language.equals("zh_cn")) {
                        Toast.makeText(getActivity(), "当前网络不可用", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(getActivity(), "The current network is not available", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            if (msg.what == 3) {
                sr3 = (String) msg.obj;

                if (NetWorking.isNetworkAvailable(getActivity())) {
                    new MyThread3().start();
                } else {
                    dialog.close();
                    if (language.equals("zh_cn")) {
                        Toast.makeText(getActivity(), "当前网络不可用", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(getActivity(), "The current network is not available", Toast.LENGTH_SHORT).show();
                    }
                }

            }

            if (msg.what == 4) {
                sr4 = (String) msg.obj;

                if (NetWorking.isNetworkAvailable(getActivity())) {
                    new MyThread4().start();
                } else {
                    dialog.close();
                    if (language.equals("zh_cn")) {
                        Toast.makeText(getActivity(), "当前网络不可用", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(getActivity(), "The current network is not available", Toast.LENGTH_SHORT).show();
                    }
                }

            }
            if (msg.what == 5) {
                sr6 = (String) msg.obj;
                if (NetWorking.isNetworkAvailable(getActivity())) {
                    new MyThread6().start();
                } else {
                    dialog.close();
                    if (language.equals("zh_cn")) {
                        Toast.makeText(getActivity(), "当前网络不可用", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(getActivity(), "The current network is not available", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            if (msg.what == 6) {

                String sr = (String) msg.obj;
                Gson gson = new Gson();
                AlarmBean = gson.fromJson(sr, GetStationDeviceListBean.class);
                Log.d("woxaizxzc", SSID+"@"+DDID);
                try {
                    if (AlarmBean.getRet().equals("Success")) {
                        adapter = new ArrayAdapter<String>(getActivity(), R.layout.simple_spinner_item, R.id.tv_spinner);
                        adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
                        for (int i = 0; i < AlarmBean.getStations().size(); i++) {
                            adapter.add(AlarmBean.getStations().get(i).getStationName());
                        }
                        spinner.setAdapter(adapter);

                        if (SSID.equals("") || SSID.equals("null")) {

                        } else {
                            spinner.setSelection(getpos(SSID, AlarmBean));

                        }
                        Log.d("xxxxzzzzz11", getpos(SSID, AlarmBean) + "xxx" + SSID);
                        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, final int position, long id) {
                                if (position == 0) {
                                    adapter0 = new ArrayAdapter<String>(getActivity(), R.layout.simple_spinner_alert, R.id.tv_spinner);
                                    adapter0.setDropDownViewResource(R.layout.alert_spnner_device);
                                    spinner3.setAdapter(adapter0);
                                    spinner2.setAdapter(adapter0);
                                    StationID = "";
                                    DeviceID = "";
                                    device = "";
                                } else {
                                    StationID = AlarmBean.getStations().get(position).getStationID();
                                    adapter1 = new ArrayAdapter<String>(getActivity(), R.layout.simple_spinner_alert, R.id.tv_spinner);
                                    adapter1.setDropDownViewResource(R.layout.alert_spnner_device);
                                    if (language.equals("zh_cn")) {

                                        adapter1.add("全部");
                                        adapter1.add("逆变器");
                                        adapter1.add("电表");
                                    } else {
                                        adapter1.add("ALL");
                                        adapter1.add("Inverter");
                                        adapter1.add("EleMeter");
                                    }
                                    spinner3.setAdapter(adapter1);

                                    if (spDevice.equals("all") || spDevice.equals("")) {
                                        spinner3.setSelection(0);
                                    } else {
                                        spinner3.setSelection(1);
                                        int m = getpos(SSID, AlarmBean);
                                       Log.d("xxszx333", getpos1(DDID, AlarmBean,m) + "xxx"+SSID+"@"+m);
                                    }

                                    spinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                        @Override
                                        public void onItemSelected(AdapterView<?> parent, View view, int position2, long id) {
                                            if (position2 == 1) {
                                                device = "Dev_Inverter";
                                                adapter3 = new ArrayAdapter<String>(getActivity(), R.layout.simple_spinner_alert, R.id.tv_spinner);
                                                adapter3.setDropDownViewResource(R.layout.alert_spnner_device);
                                                for (int i = 0; i < AlarmBean.getStations().get(position).getInverterDevices().size(); i++) {
                                                    adapter3.add(AlarmBean.getStations().get(position).getInverterDevices().get(i).getDeviceName());
                                                }

                                                //显示电表和逆变器
                                                spinner2.setAdapter(adapter3);
                                                spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                                    @Override
                                                    public void onItemSelected(AdapterView<?> parent, View view, int position2, long id) {
                                                        StationID = AlarmBean.getStations().get(position).getStationID();
                                                        DeviceID = AlarmBean.getStations().get(position).getInverterDevices().get(position2).getDeviceID();
                                                    }

                                                    @Override
                                                    public void onNothingSelected(AdapterView<?> parent) {

                                                    }
                                                });
                                                int m = getpos(SSID, AlarmBean);
                                                if (DDID.equals("") || DDID.equals("null")) {

                                                } else {
                                                    spinner2.setSelection(getpos1(DDID, AlarmBean, m));
                                                    Log.d("zhupiguai", getpos1(DDID, AlarmBean, m) + "xczcx"+m+"@"+DDID);
                                                }
                                            } else if (position2 == 2) {
                                                device = "Dev_EleMeter";
                                                adapter3 = new ArrayAdapter<String>(getActivity(), R.layout.simple_spinner_alert, R.id.tv_spinner);
                                                adapter3.setDropDownViewResource(R.layout.alert_spnner_device);
                                                for (int i = 0; i < AlarmBean.getStations().get(position).getEleMeterDevices().size(); i++) {
                                                    adapter3.add(AlarmBean.getStations().get(position).getEleMeterDevices().get(i).getDeviceName());
                                                }

                                                //显示电表和逆变器
                                                spinner2.setAdapter(adapter3);
                                                int m = getpos(SSID, AlarmBean);
                                                if (DDID.equals("") || DDID.equals("null")) {

                                                } else {
                                                    spinner2.setSelection(getpos1(DDID, AlarmBean, m));
                                                    Log.d("7894poi", getpos1(DDID, AlarmBean, m) + "xczcx"+"@"+m);
                                                }

                                                spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                                    @Override
                                                    public void onItemSelected(AdapterView<?> parent, View view, int position2, long id) {
                                                        StationID = AlarmBean.getStations().get(position).getStationID();
                                                        DeviceID = AlarmBean.getStations().get(position).getEleMeterDevices().get(position2).getDeviceID();
                                                    }

                                                    @Override
                                                    public void onNothingSelected(AdapterView<?> parent) {

                                                    }
                                                });
                                            } else {
                                                device = "";
                                                adapter3 = new ArrayAdapter<String>(getActivity(), R.layout.simple_spinner_alert, R.id.tv_spinner);
                                                adapter3.setDropDownViewResource(R.layout.alert_spnner_device);
                                                //显示电表和逆变器
                                                spinner2.setAdapter(adapter3);
                                            }
                                        }

                                        @Override
                                        public void onNothingSelected(AdapterView<?> parent) {

                                        }
                                    });
                                }


                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });

                    } else {
                        dialog.close();
                        if (AlarmBean.getErrCode().equals("1")) {
                            if (language.equals("zh_cn")) {
                                Toast.makeText(getActivity(), "您的登录已失效，请重新登录", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getActivity(),LoginActivity.class);
                                startActivity(intent);
                                sendBroadcast();
                            } else {
                                Toast.makeText(getActivity(), "Your login has expired, please log in again", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            if (language.equals("zh_cn")) {
                                Toast.makeText(getActivity(), "连接服务器失败", Toast.LENGTH_SHORT).show();

                            } else {
                                Toast.makeText(getActivity(), "Connection server failed ", Toast.LENGTH_SHORT).show();
                            }
                        }

                    }
                } catch (Exception e) {

                }

            }
            if (msg.what == 8) {

//                if (NetWorking.isNetworkAvailable(getActivity())) {
                    new MyThread8().start();
//                    Log.d("231231", StationID + "+++=" + DeviceID + "231" + device);
//                } else {
//                    if (language.equals("zh_cn")) {
//                        Toast.makeText(getActivity(), "当前网络不可用", Toast.LENGTH_SHORT).show();
//
//                    } else {
//                        Toast.makeText(getActivity(), "The current network is not available", Toast.LENGTH_SHORT).show();
//                    }
//                }

            }

            if (msg.what == 7) {
                sr7 = (String) msg.obj;
                if (NetWorking.isNetworkAvailable(getActivity())) {
                    new MyThread7().start();
                } else {
                    dialog.close();
                    if (language.equals("zh_cn")) {
                        Toast.makeText(getActivity(), "当前网络不可用", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(getActivity(), "The current network is not available", Toast.LENGTH_SHORT).show();
                    }
                }

            }
//            if (msg.what == 11) {
//                int a = getpos(StationID, AlarmBean);
//                spinner.setSelection(a);
//                new MyThread().start();
////                adapter7 = new ArrayAdapter<String>(getActivity(), R.layout.simple_spinner_alert, R.id.tv_spinner);
////                if (language.equals("zh_cn")){
////
////                    adapter7.add("全部");
////                    adapter7.add("逆变器");
////                    adapter7.add("电表");
////                }else {
////                    adapter7.add("ALL");
////                    adapter7.add("Inverter");
////                    adapter7.add("EleMeter");
////                }
////                spinner3.setAdapter(adapter7);
////
////                adapter8 = new ArrayAdapter<String>(getActivity(), R.layout.simple_spinner_alert, R.id.tv_spinner);
////                adapter8.setDropDownViewResource(R.layout.alert_spnner_device);
////                for (int i = 0; i < AlarmBean.getStations().get(a).getInverterDevices().size(); i++) {
////                    adapter8.add(AlarmBean.getStations().get(a).getInverterDevices().get(i).getDeviceName());
////                }
//////
//////                //显示电表和逆变器
////                spinner2.setAdapter(adapter8);
//
//                handler2.sendMessage(handler2.obtainMessage(13, "3"));
//                int b = getpos1(DeviceID, AlarmBean, a);
//                spinner2.setSelection(b);
//                Log.e("vbnm", a + "nnnn" + b);
//            }
            super.handleMessage(msg);
        }
    };

//

   //拉取spinner的数据
    private class MyThread2 extends Thread {
        @Override
        public void run() {
            JSONObject object = new JSONObject();
            try {
                object.put("Cmd", "GetStationDeviceList");
                object.put("Dev", "app");
                object.put("SessionID", ID);

            } catch (JSONException e) {

                e.printStackTrace();
            }
            String sr = Post.sendPost(
                    "http://" + ip + "/SPMSWcfService/SPMSMainHandler.ashx", object);
            handler.sendMessage(handler.obtainMessage(6, sr));

            Log.d("32132132wasd", sr);


        }
    }

    //查询5个按钮拉数据用的AlarmDateTime
    class MyThread5 extends Thread {
        @Override
        public void run() {

            SharedPreferences sp = getActivity().getSharedPreferences("Set", getActivity().MODE_APPEND);
            String ID = sp.getString("SessionID", "");
            SharedPreferences sp1 = getActivity().getSharedPreferences("IP", getActivity().MODE_APPEND);
            String ip = sp1.getString("Ip", "");
            JSONObject object = new JSONObject();
            try {
                object.put("Cmd", "GetRealTimeAlarmsInfo");
                object.put("Dev", "app");
                object.put("SessionID", ID);
                object.put("StationID", StationID);
                object.put("DeviceType", device);
                object.put("DeviceID", DeviceID);
                object.put("SearchStr", "");
                object.put("SortField", "AlarmDateTime");
                object.put("SortType", sr5);

            } catch (JSONException e) {

                e.printStackTrace();
            }
            String sr = Post.sendPost(
                    "http://" + ip + "/SPMSWcfService/SPMSMainHandler.ashx", object);
            handler.sendMessage(handler.obtainMessage(1, sr));
            Log.e("2sawxa", String.valueOf(object));
        }
    }
    //查询5个按钮拉数据用的StationName
    class MyThread3 extends Thread {
        @Override
        public void run() {
            SharedPreferences sp = getActivity().getSharedPreferences("Set", getActivity().MODE_APPEND);
            String ID = sp.getString("SessionID", "");
            SharedPreferences sp1 = getActivity().getSharedPreferences("IP", getActivity().MODE_APPEND);
            String ip = sp1.getString("Ip", "");
            JSONObject object = new JSONObject();
            try {
                object.put("Cmd", "GetRealTimeAlarmsInfo");
                object.put("Dev", "app");
                object.put("SessionID", ID);
                object.put("StationID", StationID);
                object.put("DeviceType", device);
                object.put("DeviceID", DeviceID);
                object.put("SearchStr", "");
                object.put("SortField", "StationName");
                object.put("SortType", sr3);

            } catch (JSONException e) {

                e.printStackTrace();
            }
            String sr = Post.sendPost(
                    "http://" + ip + "/SPMSWcfService/SPMSMainHandler.ashx", object);
            handler.sendMessage(handler.obtainMessage(1, sr));
        }
    }
    //查询5个按钮拉数据用的DeviceName
    class MyThread4 extends Thread {
        @Override
        public void run() {

            SharedPreferences sp = getActivity().getSharedPreferences("Set", getActivity().MODE_APPEND);
            String ID = sp.getString("SessionID", "");
            SharedPreferences sp1 = getActivity().getSharedPreferences("IP", getActivity().MODE_APPEND);
            String ip = sp1.getString("Ip", "");
            JSONObject object = new JSONObject();
            try {
                object.put("Cmd", "GetRealTimeAlarmsInfo");
                object.put("Dev", "app");
                object.put("SessionID", ID);
                object.put("StationID", StationID);
                object.put("DeviceType", device);
                object.put("DeviceID", DeviceID);
                object.put("SearchStr", "");
                object.put("SortField", "DeviceName");
                object.put("SortType", sr4);

            } catch (JSONException e) {

                e.printStackTrace();
            }
            String sr = Post.sendPost(
                    "http://" + ip + "/SPMSWcfService/SPMSMainHandler.ashx", object);
            handler.sendMessage(handler.obtainMessage(1, sr));
        }
    }

    //查询5个按钮拉数据用的AlarmTagName
    class MyThread6 extends Thread {
        @Override
        public void run() {

            SharedPreferences sp = getActivity().getSharedPreferences("Set", getActivity().MODE_APPEND);
            String ID = sp.getString("SessionID", "");
            SharedPreferences sp1 = getActivity().getSharedPreferences("IP", getActivity().MODE_APPEND);
            String ip = sp1.getString("Ip", "");
            JSONObject object = new JSONObject();
            try {
                object.put("Cmd", "GetRealTimeAlarmsInfo");
                object.put("Dev", "app");
                object.put("SessionID", ID);
                object.put("StationID", StationID);
                object.put("DeviceType", device);
                object.put("DeviceID", DeviceID);
                object.put("SearchStr", "");
                object.put("SortField", "AlarmTagName");
                object.put("SortType", sr6);

            } catch (JSONException e) {

                e.printStackTrace();
            }
            String sr = Post.sendPost(
                    "http://" + ip + "/SPMSWcfService/SPMSMainHandler.ashx", object);
            handler.sendMessage(handler.obtainMessage(1, sr));
        }
    }

    //查询5个按钮拉数据用的AlarmDateTime
    class MyThread8 extends Thread {
        @Override
        public void run() {
//            SharedPreferences sp = getActivity().getSharedPreferences("Set", getActivity().MODE_APPEND);
//            String ID = sp.getString("SessionID", "");
//            SharedPreferences sp1 = getActivity().getSharedPreferences("IP", getActivity().MODE_APPEND);
//            String ip = sp1.getString("Ip", "");
            JSONObject object = new JSONObject();

            try {
                object.put("Cmd", "GetRealTimeAlarmsInfo");
                object.put("Dev", "app");
                object.put("SessionID", ID);
                object.put("StationID", StationID);
                object.put("DeviceType", device);
                object.put("DeviceID", DeviceID);
                object.put("SearchStr", "");
                object.put("SortField", "");
                object.put("SortType", "");

            } catch (JSONException e) {

                e.printStackTrace();
            }
            String sr = Post.sendPost(
                    "http://" + ip + "/SPMSWcfService/SPMSMainHandler.ashx", object);
            handler.sendMessage(handler.obtainMessage(1, sr));
        }
    }

    //查询5个按钮拉数据用的AlarmDesc
    class MyThread7 extends Thread {
        @Override
        public void run() {
            SharedPreferences sp = getActivity().getSharedPreferences("Set", getActivity().MODE_APPEND);
            String ID = sp.getString("SessionID", "");
            SharedPreferences sp1 = getActivity().getSharedPreferences("IP", getActivity().MODE_APPEND);
            String ip = sp1.getString("Ip", "");
            JSONObject object = new JSONObject();
            try {
                object.put("Cmd", "GetRealTimeAlarmsInfo");
                object.put("Dev", "app");
                object.put("SessionID", ID);
                object.put("StationID", StationID);
                object.put("DeviceType", device);
                object.put("DeviceID", DeviceID);
                object.put("SearchStr", "");
                object.put("SortField", "AlarmDesc");
                object.put("SortType", sr7);

            } catch (JSONException e) {

                e.printStackTrace();
            }
            String sr = Post.sendPost(
                    "http://" + ip + "/SPMSWcfService/SPMSMainHandler.ashx", object);
            handler.sendMessage(handler.obtainMessage(1, sr));
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        getFocus();
    }


    private void getFocus() {
        getView().setFocusable(true);
        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {

            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_BACK) {
                    // 监听到返回按钮点击事件
                    if (num == 1) {
                        iv_menu.setVisibility(VISIBLE);
                        iv_search.setVisibility(VISIBLE);
                        iv_backs.setVisibility(GONE);
                        ll_alert1.setVisibility(VISIBLE);
                        ll_alert2.setVisibility(GONE);
                        et_inputs.setText("");
                        lvs.setVisibility(GONE);
                        num = 0;
                    } else {
                        exit();
                    }
                    return true;// 未处理
                }
                return false;
            }
        });
    }

    //退出时的时间
    private long mExitTime;

    public void exit() {
        if ((System.currentTimeMillis() - mExitTime) > 2000) {
            if (language.equals("zh_cn")) {
                Toast.makeText(getActivity(), "再按一次退出程序", Toast.LENGTH_SHORT).show();

            } else {
                Toast.makeText(getActivity(), "Then click one exit procedure ", Toast.LENGTH_SHORT).show();
            }

            mExitTime = System.currentTimeMillis();
        } else {
            getActivity().finish();
            System.exit(0);
        }
    }

    /**
     * 低级帐户
     */


    Handler handler3 = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0) {
                String sr = (String) msg.obj;
                Gson gson = new Gson();
                final GetStationDeviceListBean bean = gson.fromJson(sr, GetStationDeviceListBean.class);

                StationID = bean.getStations().get(1).getStationID();
                if (bean.getRet().equals("Success")) {
                    adapter = new ArrayAdapter<String>(getActivity(), R.layout.simple_spinner_item, R.id.tv_spinner);
                    adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
                    for (int i = 0; i < bean.getStations().size(); i++) {
                        adapter.add(bean.getStations().get(i).getStationName());
                    }
                    spinner.setAdapter(adapter);
                    spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, final int position, long id) {

                            if (position == 0) {
                                adapter1 = new ArrayAdapter<String>(getActivity(), R.layout.simple_spinner_alert, R.id.tv_spinner);
                                adapter1.setDropDownViewResource(R.layout.alert_spnner_device);
                                spinner3.setAdapter(adapter1);
                                spinner2.setAdapter(adapter1);
                                DeviceID = "";
                                device = "";
                            } else {
                                adapter1 = new ArrayAdapter<String>(getActivity(), R.layout.simple_spinner_alert, R.id.tv_spinner);
                                adapter1.setDropDownViewResource(R.layout.alert_spnner_device);
                                if (language.equals("zh_cn")) {

                                    adapter1.add("全部");
                                    adapter1.add("逆变器");
                                    adapter1.add("电表");
                                } else {
                                    adapter1.add("ALL");
                                    adapter1.add("Inverter");
                                    adapter1.add("EleMeter");
                                }


                                spinner3.setAdapter(adapter1);
                                spinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position2, long id) {
                                        if (position2 == 1) {
                                            device = "Dev_Inverter";
                                            adapter3 = new ArrayAdapter<String>(getActivity(), R.layout.simple_spinner_alert, R.id.tv_spinner);
                                            adapter3.setDropDownViewResource(R.layout.alert_spnner_device);
                                            for (int i = 0; i < bean.getStations().get(position).getInverterDevices().size(); i++) {
                                                adapter3.add(bean.getStations().get(position).getInverterDevices().get(i).getDeviceName());
                                            }

                                            //显示电表和逆变器
                                            spinner2.setAdapter(adapter3);
                                            spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                                @Override
                                                public void onItemSelected(AdapterView<?> parent, View view, int position2, long id) {
                                                    StationID = bean.getStations().get(position).getStationID();
                                                    DeviceID = bean.getStations().get(position).getInverterDevices().get(position2).getDeviceID();
                                                }

                                                @Override
                                                public void onNothingSelected(AdapterView<?> parent) {

                                                }
                                            });
                                        } else if (position2 == 2) {
                                            device = "Dev_EleMeter";
                                            adapter3 = new ArrayAdapter<String>(getActivity(), R.layout.simple_spinner_alert, R.id.tv_spinner);
                                            adapter3.setDropDownViewResource(R.layout.alert_spnner_device);
                                            for (int i = 0; i < bean.getStations().get(position).getEleMeterDevices().size(); i++) {
                                                adapter3.add(bean.getStations().get(position).getEleMeterDevices().get(i).getDeviceName());
                                            }

                                            //显示电表和逆变器
                                            spinner2.setAdapter(adapter3);
                                            spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                                @Override
                                                public void onItemSelected(AdapterView<?> parent, View view, int position2, long id) {
                                                    StationID = bean.getStations().get(position).getStationID();
                                                    DeviceID = bean.getStations().get(position).getEleMeterDevices().get(position2).getDeviceID();
                                                }

                                                @Override
                                                public void onNothingSelected(AdapterView<?> parent) {

                                                }
                                            });
                                        } else {
                                            device = "";
                                            adapter3 = new ArrayAdapter<String>(getActivity(), R.layout.simple_spinner_alert, R.id.tv_spinner);
                                            adapter3.setDropDownViewResource(R.layout.alert_spnner_device);
                                            //显示电表和逆变器
                                            spinner2.setAdapter(adapter3);
                                        }
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {

                                    }
                                });

                            }


                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                    handler3.sendMessage(handler3.obtainMessage(1, sr));
                } else {
                    dialog.close();
                    if (bean.getErrCode().equals("1")) {
                        if (language.equals("zh_cn")) {
                            Toast.makeText(getActivity(), "您的登录已失效，请重新登录", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getActivity(),LoginActivity.class);
                            startActivity(intent);
                            sendBroadcast();
                        } else {
                            Toast.makeText(getActivity(), "Your login has expired, please log in again", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        if (language.equals("zh_cn")) {
                            Toast.makeText(getActivity(), "连接服务器失败", Toast.LENGTH_SHORT).show();

                        } else {
                            Toast.makeText(getActivity(), "Connection server failed ", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

            }
            if (msg.what == 1) {
                new MyThread().start();
            }

            if (msg.what ==33){
                String sr = (String) msg.obj;
                Gson gson = new Gson();

                ButtonBean bean = gson.fromJson(sr,ButtonBean.class);
                btn.setText(bean.getTextInfo().getButtonQuery());

                tv1.setText(bean.getTextInfo().getDateTime());
                tv2.setText(bean.getTextInfo().getPowerStation());
                tv3.setText(bean.getTextInfo().getDevice());
                tv4.setText(bean.getTextInfo().getTagName());
                tv5.setText(bean.getTextInfo().getAlarmDesc());

                tv1_1.setText(bean.getTextInfo().getDateTime());
                tv2_1.setText(bean.getTextInfo().getPowerStation());
                tv3_1.setText(bean.getTextInfo().getDevice());
                tv4_1.setText(bean.getTextInfo().getTagName());
                tv5_1.setText(bean.getTextInfo().getAlarmDesc());



                tv1s.setText(bean.getTextInfo().getDateTime());
                tv2s.setText(bean.getTextInfo().getPowerStation());
                tv3s.setText(bean.getTextInfo().getDevice());
                tv4s.setText(bean.getTextInfo().getTagName());
                tv5s.setText(bean.getTextInfo().getAlarmDesc());

                tv1_1s.setText(bean.getTextInfo().getDateTime());
                tv2_1s.setText(bean.getTextInfo().getPowerStation());
                tv3_1s.setText(bean.getTextInfo().getDevice());
                tv4_1s.setText(bean.getTextInfo().getTagName());
                tv5_1s.setText(bean.getTextInfo().getAlarmDesc());

            }
            super.handleMessage(msg);
        }
    };

    private class MyThread22 extends Thread {
        @Override
        public void run() {
            JSONObject object = new JSONObject();
            try {
                object.put("Cmd", "GetStationDeviceList");
                object.put("Dev", "app");
                object.put("SessionID", ID);

            } catch (JSONException e) {

                e.printStackTrace();
            }
            String sr = Post.sendPost(
                    "http://" + ip + "/SPMSWcfService/SPMSMainHandler.ashx", object);
            handler3.sendMessage(handler3.obtainMessage(0, sr));

            Log.d("32132132wasd", sr);


        }
    }


    private class MyThread33 extends Thread {
        @Override
        public void run() {
            JSONObject object = new JSONObject();
            try {
                object.put("Cmd", "GetRTAlarmStr");
                object.put("Dev", "app");
                object.put("SessionID", ID);

            } catch (JSONException e) {

                e.printStackTrace();
            }
            String sr = Post.sendPost(
                    "http://" + ip + "/SPMSWcfService/SPMSMainHandler.ashx", object);
            handler3.sendMessage(handler3.obtainMessage(33, sr));

            Log.d("zhecishizenmeshende", sr);


        }
    }



    public int getpos(String title, GetStationDeviceListBean data) {
        int a = 0;
        for (int i = 0; i < data.getStations().size(); i++) {
            if (data.getStations().get(i).getStationID().equals(title)) {
                a = i;
                break;
            }
        }
        return a;
    }

    public int getpos1(String title, GetStationDeviceListBean data, int b) {
        int a = 0;
        Log.e("sdaxzz3213zzxxd", data.getStations().get(b).getInverterDevices().get(0).getDeviceID());
        for (int j = 0; j < data.getStations().get(b).getInverterDevices().size(); j++){
            if (data.getStations().get(b).getInverterDevices().get(j).getDeviceID().equals(title)) {
                a = j;
                break;
            }
        }
        return a;
    }

    //发送广播的方法
    public void sendBroadcast() {
        Intent intent1 = new Intent("com.main");
        LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent1);

    }

}
