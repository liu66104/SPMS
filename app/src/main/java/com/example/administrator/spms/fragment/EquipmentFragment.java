package com.example.administrator.spms.fragment;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.ColorDrawable;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.spms.R;
import com.example.administrator.spms.activity.AboutActivity;
import com.example.administrator.spms.activity.InfoActivity;
import com.example.administrator.spms.activity.LoginActivity;
import com.example.administrator.spms.activity.LoginOneActivity;
import com.example.administrator.spms.adapter.EquipmentAdapter;
import com.example.administrator.spms.adapter.EquipmentTwoAdapter;
import com.example.administrator.spms.adapter.SearchAdapter;
import com.example.administrator.spms.adapter.SearchEquipmentAdapter;
import com.example.administrator.spms.adapter.SearchListAdapter;
import com.example.administrator.spms.base.BaseFragment;
import com.example.administrator.spms.base.Loading;
import com.example.administrator.spms.base.LoadingDialog;
import com.example.administrator.spms.base.NetWorking;
import com.example.administrator.spms.base.Post;
import com.example.administrator.spms.base.SearchView;
import com.example.administrator.spms.base.SearchViewEquipment;
import com.example.administrator.spms.model.GetDeviceAlarmListBean;
import com.example.administrator.spms.model.GetDeviceAllDataBean;
import com.example.administrator.spms.model.GetDeviceStatusListBean;
import com.example.administrator.spms.model.GetGroupOperationInfo;
import com.example.administrator.spms.model.GetStationBaseInfoBean;
import com.example.administrator.spms.model.GetStationListBean;
import com.example.administrator.spms.model.SearchBean;
import com.example.administrator.spms.refresh.PullToRefreshLayout;
import com.example.administrator.spms.refresh.TwoPullToRefresh;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.awt.font.NumericShaper;
import java.util.ArrayList;
import java.util.List;

import static android.view.View.VISIBLE;

/**
 * Created by Administrator on 2016/10/28.
 * 逆变器状态
 */
@ContentView(R.layout.fragment_equipment_one)
public class EquipmentFragment extends BaseFragment implements SearchView.SearchViewListener, SearchViewEquipment.SearchViewListener2 {
    private List<GetDeviceStatusListBean.DeviceListBean> data;
    private String Station, Device;//记录选中的ID
    private int nums = 0; //用来判断在第几页返回的
    private String sr1;
    private String sr2;
    @ViewInject(R.id.tv_equipment_title)
    private TextView tv_title_top;

    @ViewInject(R.id.tv_equipment_title_search)
    private TextView tv_title_top1;
    //text代替spinner
    @ViewInject(R.id.tv_sp)
    private TextView tv_sp;

    private ArrayAdapter<String> adapter1;
    private ArrayAdapter<String> adapter5;
    private  int s =0;
    //逆变器图片
    @ViewInject(R.id.iv_nibianqi)
    private ImageView iv_tag7;

    //每行text的图片
    @ViewInject(R.id.iv_equipment_tag)
    private ImageView iv_tag;
    //每行text的图片
    @ViewInject(R.id.iv_equipment_tag2)
    private ImageView iv_tag2;
    //每行text的图片
    @ViewInject(R.id.iv_equipment_tag3)
    private ImageView iv_tag3;
    //每行text的图片
    @ViewInject(R.id.iv_equipment_tag4)
    private ImageView iv_tag4;
    //每行text的图片
    @ViewInject(R.id.iv_equipment_tag5)
    private ImageView iv_tag5;
    //每行text的图片
    @ViewInject(R.id.iv_equipment_tag6)
    private ImageView iv_tag6;

    //下拉框
    @ViewInject(R.id.spinner1)
    private Spinner spinner;
    //逆变器列表的top
    @ViewInject(R.id.iv_top_list)
    private ImageView iv_top_list;
    //逆变器状态top
    @ViewInject(R.id.iv_top_equipment)
    private ImageView iv_top;
    //stationID
    private String stationID;

    //逆变器状态
    @ViewInject(R.id.ll_equipment)
    private LinearLayout ll;
    //逆变器列表
    @ViewInject(R.id.ll_equipment_two)
    private LinearLayout ll_two;
    //逆变器详情
    @ViewInject(R.id.ll_equipment_three)
    private LinearLayout ll_three;
    //详情的标题
    @ViewInject(R.id.tv_equipment_title_three)
    private TextView tv_title_three;

    private View view_pop;
    private PopupWindow mPopupwinow = null;

    private EquipmentAdapter adapter;
    private String language;

    @ViewInject(R.id.lv_equipment)
    private ListView listView;

    @ViewInject(R.id.iv_point)
    private ImageView iv_point;

    @ViewInject(R.id.iv_search_equipment)
    private ImageView iv_search;

    @ViewInject(R.id.tv_name_equipment_three)
    private TextView tv_title;

    @ViewInject(R.id.tv_station_equipment_three)
    private TextView tv_equipment_three_station;

    @ViewInject(R.id.tv_equipment_three)
    private TextView tv_equipment_three_device;

    @ViewInject(R.id.tv_equipment_three_number)
    private TextView tv_equipment_three_number;
    //直流输入功率
    @ViewInject(R.id.tv_InputPowerStr)
    private TextView tv_InputPowerStr;
    @ViewInject(R.id.tv_kw_equipment_three)
    private TextView tv_InputPowerUnit;
    @ViewInject(R.id.tv_num_equipment_three)
    private TextView tv_InputPower;
    //交流输出功率
    @ViewInject(R.id.tv_PowerStr)
    private TextView tv_PowerStr;
    @ViewInject(R.id.tv_kw2_equipment_three)
    private TextView tv_PowerUnit;
    @ViewInject(R.id.tv_Power)
    private TextView tv_Power;
    //转换效率
    @ViewInject(R.id.tv_TransferEfficiencyStr)
    private TextView tv_TransferEfficiencyStr;
    @ViewInject(R.id.tv_kw3_equipment_three)
    private TextView tv_TransferEfficiencyUnit;
    @ViewInject(R.id.tv_TransferEfficiency)
    private TextView tv_TransferEfficiency;
    //机内温度
    @ViewInject(R.id.tv_WorkingTemperatureStr)
    private TextView tv_WorkingTemperatureStr;
    @ViewInject(R.id.tv_kw4_equipment_three)
    private TextView tv_WorkingTemperatureUnit;
    @ViewInject(R.id.tv_WorkingTemperature)
    private TextView tv_WorkingTemperature;
    //今日发电量
    @ViewInject(R.id.tv_DayPowerGenerationStr)
    private TextView tv_DayPowerGenerationStr;
    @ViewInject(R.id.tv_kw5_equipment_three)
    private TextView tv_DayPowerGenerationUnit;
    @ViewInject(R.id.textView4)
    private TextView tv_DayPowerGeneration;
    //累计发电量
    @ViewInject(R.id.tv_TotalPowerGenerationStr)
    private TextView tv_TotalPowerGenerationStr;
    @ViewInject(R.id.tv_kw6_equipment_three)
    private TextView tv_TotalPowerGenerationUnit;
    @ViewInject(R.id.textView5)
    private TextView tv_TotalPowerGeneration;
    /**
     * 逆变器列表
     */
    private int a = 0, b = 0;
    private View view_pop2;
    private PopupWindow mPopupwinow2 = null;
    private EquipmentTwoAdapter adapter2;

    @ViewInject(R.id.lv_equipment_two)
    private ListView listView2;

    @ViewInject(R.id.iv_point2)
    private ImageView iv_point2;

    //设备标题文字
    private TextView tv_equ;
    //设备列表
    private TextView tv_equ2;
    //详情页的spnner
//    @ViewInject(R.id.tv_equ_title3)
//    private Spinner sp_title_top3;

    /**
     * 逆变器的详情
     */
    private View view_pop3;
    private PopupWindow mPopupwinow3 = null;

    //判断逆变器状态的图标
    @ViewInject(R.id.iv_red_point)
    private ImageView iv_red_point;

    @ViewInject(R.id.iv_point3)
    private ImageView iv_point3;
    private int eqm;
    /**
     *
     *搜索
     *
     */
    /**
     * 热搜框列表adapter
     */
    private ArrayAdapter<String> hintAdapter;

    /**
     * 自动补全列表adapter
     */
    private ArrayAdapter<String> autoCompleteAdapter;

    /**
     * 搜索结果列表adapter
     */
    private SearchEquipmentAdapter resultAdapter;

    private List<GetDeviceStatusListBean.DeviceListBean> dbData;

    /**
     * 热搜版数据
     */
    private List<String> hintData;

    /**
     * 搜索过程中自动补全数据
     */
    private List<String> autoCompleteData;

    /**
     * 搜索结果的数据
     */
    private List<GetDeviceStatusListBean.DeviceListBean> resultData;

    /**
     * 默认提示框显示项的个数
     */
    private static int DEFAULT_HINT_SIZE = 50;

    /**
     * 提示框显示项的个数
     */
    private static int hintSize = DEFAULT_HINT_SIZE;
    /**
     * 搜索结果列表view
     */
    @ViewInject(R.id.main_lv_search_results)
    private ListView lvResults;
    /**
     * 搜索view
     */
    @ViewInject(R.id.main_search_layout)
    private SearchView searchView;
    /**
     * 搜索页面返回键
     */
    @ViewInject(R.id.iv_back_search_equipment)
    private ImageView iv_search_back;
    //逆变器转态的整体布局
    @ViewInject(R.id.ll_equipment)
    private LinearLayout ll_equipment;
    //电站搜索的整体布局
    @ViewInject(R.id.ll_equipment_one)
    private LinearLayout ll_search_equipment;


    /**
     *
     *
     * 逆变器列表的搜索
     *
     */
    /**
     * 热搜框列表adapter
     */
    private ArrayAdapter<String> hintAdapter2;

    /**
     * 自动补全列表adapter
     */
    private ArrayAdapter<String> autoCompleteAdapter2;

    /**
     * 搜索结果列表adapter
     */
    private SearchListAdapter resultAdapter2;

    private List<GetDeviceAlarmListBean.DeviceListBean> dbData2;

    /**
     * 热搜版数据
     */
    private List<String> hintData2;

    /**
     * 搜索过程中自动补全数据
     */
    private List<String> autoCompleteData2;

    /**
     * 搜索结果的数据
     */
    private List<GetDeviceAlarmListBean.DeviceListBean> resultData2;

    /**
     * 默认提示框显示项的个数
     */
    private static int DEFAULT_HINT_SIZE2 = 50;

    /**
     * 提示框显示项的个数
     */
    private static int hintSize2 = DEFAULT_HINT_SIZE2;
    /**
     * 搜索结果列表view
     */
    @ViewInject(R.id.main_lv_search_results_two)
    private ListView lvResults2;
    /**
     * 搜索view
     */
    @ViewInject(R.id.main_search_layout_two)
    private SearchViewEquipment searchView2;
    /**
     * 搜索页面返回键
     */
    @ViewInject(R.id.iv_back_search_equipment_two)
    private ImageView iv_search_back_two;

    @ViewInject(R.id.iv_search_equipment_two)
    private ImageView iv_search_two;
    //列表搜索的整体布局
    @ViewInject(R.id.ll_search_equipment_two)
    private LinearLayout ll_search_equipment_two;
    private LoadingDialog dialog, dialog5;

    //上下箭头
    @ViewInject(R.id.iv_eqm_up)
    private ImageView iv_up;
    @ViewInject(R.id.iv_eqm_down)
    private ImageView iv_down;
    @ViewInject(R.id.iv_eqm_up1)
    private ImageView iv_up1;
    @ViewInject(R.id.iv_eqm_down1)
    private ImageView iv_down1;
    //逆变器列表搜索的标题
    @ViewInject(R.id.rv_search_equipment_three)
    private TextView tv_search_title;

    //定义广播管理器，广播接收器 用来接收
    private LocalBroadcastManager broadcastManager3;
    private IntentFilter intentFilter3;
    private BroadcastReceiver mReceiver3;

    private String title;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dialog = new LoadingDialog(getActivity(), Loading.getText(getActivity()));
        //显示Dialog
        dialog.show();

        SharedPreferences sp3 = getActivity().getSharedPreferences("language", getActivity().MODE_APPEND);
        language = sp3.getString("language", "");


        if (NetWorking.isNetworkAvailable(getActivity())) {
            new MyThread().start();
        } else {
            if (language.equals("zh_cn")) {
                Toast.makeText(getActivity(), "当前网络不可用", Toast.LENGTH_SHORT).show();

            } else {
                Toast.makeText(getActivity(), "The current network is not available", Toast.LENGTH_SHORT).show();
            }
        }

        SharedPreferences sp2 = getActivity().getSharedPreferences("title", getActivity().MODE_APPEND);
        title = sp2.getString("title", "");
        tv_title_top.setText(title);
        tv_title_top1.setText(title);
        SharedPreferences sp = getActivity().getSharedPreferences("Set", getActivity().MODE_APPEND);

        view = LayoutInflater.from(getActivity()).inflate(R.layout.head_list, null);
        tv_equ = (TextView) view.findViewById(R.id.tv_equ_title);
        LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.ll_head);
        linearLayout.setClickable(false);
        listView.addHeaderView(view);

        View view2 = LayoutInflater.from(getActivity()).inflate(R.layout.head_two, null);
        tv_equ2 = (TextView) view2.findViewById(R.id.tv_equ_title2);
        listView2.addHeaderView(view2);


        //上拉加载和下拉刷新的监听
        ((TwoPullToRefresh) getActivity().findViewById(R.id.refresh_equipment_one))
                .setOnRefreshListener(new MyListener());


        //上拉加载和下拉刷新的监听
        ((TwoPullToRefresh) getActivity().findViewById(R.id.refresh_equipment_two))
                .setOnRefreshListener(new MyListener2());

        //上拉加载和下拉刷新的监听
        ((TwoPullToRefresh) getActivity().findViewById(R.id.refresh_equipment_three))
                .setOnRefreshListener(new MyListener3());


        //逆变器状态的top按键
        iv_top.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("ssszzzxxx", "我是逆变器状态top");
                listView.setSelection(0);
//                        listView.smoothScrollToPosition(0);
                if (language.equals("zh_cn")) {
                    Toast.makeText(getActivity(), "返回顶部", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(getActivity(), "Return to top ", Toast.LENGTH_SHORT).show();
                }


            }
        });

        //逆变器列表返回顶部按键监听
        iv_top_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listView2.setSelection(0);
                Log.e("ssszzzxxx", "我是逆变器列表top");
                if (language.equals("zh_cn")) {
                    Toast.makeText(getActivity(), "返回顶部", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(getActivity(), "Return to top ", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //右上角弹出的设置窗口
        view_pop = LayoutInflater.from(getActivity()).inflate(
                R.layout.pop_menu, null);


        TextView pop1 = (TextView) view_pop.findViewById(R.id.pop_textView1);
        pop1.setText(sp.getString("SUserMessage", ""));
        TextView pop2 = (TextView) view_pop.findViewById(R.id.pop_textView2);
        pop2.setText(sp.getString("SSet", ""));
        TextView pop3 = (TextView) view_pop.findViewById(R.id.pop_textView3);
        pop3.setText(sp.getString("SAbout", ""));
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
        /**
         *
         *
         * 逆变器列表的代码
         *
         *
         */


        //右上角弹出的设置窗口
        view_pop2 = LayoutInflater.from(getActivity()).inflate(
                R.layout.pop_menu, null);
        TextView pop4 = (TextView) view_pop2.findViewById(R.id.pop_textView1);
        pop4.setText(sp.getString("SUserMessage", ""));
        TextView pop5 = (TextView) view_pop2.findViewById(R.id.pop_textView2);
        pop5.setText(sp.getString("SSet", ""));
        TextView pop6 = (TextView) view_pop2.findViewById(R.id.pop_textView3);
        pop6.setText(sp.getString("SAbout", ""));
        //帐户信息
        LinearLayout ll4 = (LinearLayout) view_pop.findViewById(R.id.ll_pop1);
        ll4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), InfoActivity.class);
                startActivity(intent);
            }
        });
        //登录
        LinearLayout ll5 = (LinearLayout) view_pop.findViewById(R.id.ll_pop2);
        ll5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), LoginOneActivity.class);
                startActivity(intent);
            }
        });
        //关于
        LinearLayout ll6 = (LinearLayout) view_pop.findViewById(R.id.ll_pop3);
        ll6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AboutActivity.class);
                startActivity(intent);
            }
        });
        iv_point2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                if (mPopupwinow2 == null) {
                    mPopupwinow2 = new PopupWindow(view_pop,
                            ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT, true);
                    //设置pop window的背景颜色
                    mPopupwinow2.setBackgroundDrawable(new ColorDrawable(
                            0x00000000));
                }
                //设置pop window的位置
                mPopupwinow2.showAsDropDown(iv_point2, 0, 0);
            }
        });


        /**
         *
         *
         * 逆变器详情
         *
         *
         */
        //右上角弹出的设置窗口
        view_pop3 = LayoutInflater.from(getActivity()).inflate(
                R.layout.pop_menu, null);
        iv_point3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                if (mPopupwinow3 == null) {
                    mPopupwinow3 = new PopupWindow(view_pop,
                            ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT, true);
                    //设置pop window的背景颜色
                    mPopupwinow3.setBackgroundDrawable(new ColorDrawable(
                            0x00000000));
                }
                //设置pop window的位置
                mPopupwinow3.showAsDropDown(iv_point3, 0, 0);
            }
        });


        /**
         *
         *
         * 电站的搜索
         *
         *
         */
        //搜索按键
        iv_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent1 = new Intent("com.search3");
                LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent1);

                iv_top.setVisibility(View.GONE);
                ll_search_equipment.setVisibility(View.VISIBLE);
                ll_equipment.setVisibility(View.GONE);
                nums = 4;
            }
        });
        // initData();
        initViews();
        /**
         *
         *
         * 逆变器列表中的搜索
         *
         *
         */
        iv_search_two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent("com.search6");
                LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent1);
                ll_two.setVisibility(View.GONE);
                ll_search_equipment_two.setVisibility(View.VISIBLE);
                nums = 5;
            }
        });
        // initData2();
        initViews2();


        ////显示哪个页面
        broadcastManager3 = LocalBroadcastManager.getInstance(getActivity());
        intentFilter3 = new IntentFilter();
        intentFilter3.addAction("MainActivity.ViewPager");
        // intentFilter.addAction("com.example.administrator.myapplication.main.main.home.broadcast");
        mReceiver3 = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                ll_two.setVisibility(View.GONE);
                ll.setVisibility(View.VISIBLE);
                tv_title_top.setVisibility(View.VISIBLE);
                spinner.setVisibility(View.GONE);
                iv_top.setVisibility(View.VISIBLE);
                tv_sp.setVisibility(View.GONE);
                ll_three.setVisibility(View.GONE);
                ll_search_equipment_two.setVisibility(View.GONE);
                ll_search_equipment.setVisibility(View.GONE);
            }
        };
        broadcastManager3.registerReceiver(mReceiver3, intentFilter3);


        broadcastManager3 = LocalBroadcastManager.getInstance(getActivity());
        intentFilter3 = new IntentFilter();
        intentFilter3.addAction("com.search4");
        // intentFilter.addAction("com.example.administrator.myapplication.main.main.home.broadcast");
        mReceiver3 = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                lvResults.setVisibility(View.GONE);
                iv_top.setVisibility(View.VISIBLE);
                ll_equipment.setVisibility(View.VISIBLE);
                ll_search_equipment.setVisibility(View.GONE);

                nums = 0;
            }
        };
        broadcastManager3.registerReceiver(mReceiver3, intentFilter3);


        broadcastManager3 = LocalBroadcastManager.getInstance(getActivity());
        intentFilter3 = new IntentFilter();
        intentFilter3.addAction("com.search5");
        // intentFilter.addAction("com.example.administrator.myapplication.main.main.home.broadcast");
        mReceiver3 = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                ll_search_equipment_two.setVisibility(View.GONE);
                ll_two.setVisibility(View.VISIBLE);
                lvResults2.setVisibility(View.GONE);
                ll_search_equipment_two.setVisibility(View.GONE);
                ll_two.setVisibility(View.GONE);
                ll.setVisibility(View.VISIBLE);
                tv_title_top.setVisibility(View.VISIBLE);
                spinner.setVisibility(View.GONE);
                tv_sp.setVisibility(View.GONE);
                Log.d("231231", String.valueOf(dbData2) + "1");
                nums = 0;

            }
        };
        broadcastManager3.registerReceiver(mReceiver3, intentFilter3);


    }


    /**
     * 初始化视图
     */
    private void initViews() {
//        lvResults = (ListView) findViewById(R.id.main_lv_search_results);
//        searchView = (SearchView) findViewById(R.id.main_search_layout);
//        iv_search_back = (ImageView) searchFragment.getView().findViewById(R.id.iv_back_search);
        iv_search_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lvResults.setVisibility(View.GONE);
                iv_top.setVisibility(View.VISIBLE);
                ll_equipment.setVisibility(View.VISIBLE);
                ll_search_equipment.setVisibility(View.GONE);
                nums = 0;

                Intent intent1 = new Intent("Search.Equipment");
                LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent1);

            }
        });

        //设置监听
        searchView.setSearchViewListener(this);
        //设置adapter
        searchView.setTipsHintAdapter(hintAdapter);
        searchView.setAutoCompleteAdapter(autoCompleteAdapter);


    }


    /**
     * 初始化数据
     */
//    private void initData() {
//        //从数据库获取数据
//        getDbData();
//        //初始化热搜版数据
//      //  getHintData();
//        //初始化自动补全数据
//      //  getAutoCompleteData(null);
//        //初始化搜索结果数据
//        getResultData(null);
//    }

    /**
     * 获取db 数据
     */
    private void getDbData(List<GetDeviceStatusListBean.DeviceListBean> data) {
        int size = data.size();
        dbData = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            dbData.add(data.get(i));
        }
    }

    /**
     * 获取自动补全data 和adapter
     */
    private void getAutoCompleteData(String text) {
        if (autoCompleteData == null) {
            //初始化
            autoCompleteData = new ArrayList<>(hintSize);
        } else {
            // 根据text 获取auto data
            autoCompleteData.clear();
            for (int i = 0, count = 0; i < dbData.size()
                    && count < hintSize; i++) {
                Log.e("ssswww", dbData.get(i).getStationName() + "");
                if (dbData.get(i).getStationName().contains(text.trim())) {
                    autoCompleteData.add(dbData.get(i).getStationName());
                    count++;
                }
            }
        }
        if (autoCompleteAdapter == null) {
            autoCompleteAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, autoCompleteData);
        } else {
            autoCompleteAdapter.notifyDataSetChanged();
        }
    }

    /**
     * 获取搜索结果data和adapter
     */
    private void getResultData(String text) {
        if (resultData == null) {
            // 初始化
            resultData = new ArrayList<>();
        } else {
            resultData.clear();
            for (int i = 0; i < dbData.size(); i++) {
                if (dbData.get(i).getStationName().toLowerCase().contains(text.trim().toLowerCase()) || dbData.get(i).getStationName().toUpperCase().contains(text.trim().toUpperCase()) || dbData.get(i).getStationName().contains(text.trim())) {
                    resultData.add(dbData.get(i));
                }
            }
        }
        if (resultAdapter == null) {
            resultAdapter = new SearchEquipmentAdapter(getActivity(), resultData, R.layout.search_layout_equipment);
        } else {
            resultAdapter.notifyDataSetChanged();
        }
    }

    /**
     * 当搜索框 文本改变时 触发的回调 ,更新自动补全数据
     *
     * @param text
     */
    public void onRefreshAutoComplete(String text) {
        //更新数据
        getAutoCompleteData(text);
    }

    /**
     * 点击搜索键时edit text触发的回调
     *
     * @param text
     */
    public void onSearch(String text) {

        if (text.equals("") || text.equals("null")) {
            lvResults.setVisibility(View.GONE);
        } else {
            if (NetWorking.isNetworkAvailable(getActivity())) {
                //更新result数据
                getResultData(text);
                lvResults.setVisibility(View.VISIBLE);
                //第一次获取结果 还未配置适配器
                if (lvResults.getAdapter() == null) {
                    //获取搜索数据 设置适配器
                    lvResults.setAdapter(resultAdapter);
                } else {
                    //更新搜索数据
                    resultAdapter.notifyDataSetChanged();
                }
                if (language.equals("zh_cn")) {
                    Toast.makeText(getActivity(), "完成搜索", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(getActivity(), "Complete search ", Toast.LENGTH_SHORT).show();
                }

            } else {
                if (language.equals("zh_cn")) {
                    Toast.makeText(getActivity(), "当前网络不可用", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(getActivity(), "The current network is not available", Toast.LENGTH_SHORT).show();
                }
            }

        }
    }


    /**
     * 初始化视图
     */
    private void initViews2() {
//        lvResults = (ListView) findViewById(R.id.main_lv_search_results);
//        searchView = (SearchView) findViewById(R.id.main_search_layout);
//        iv_search_back = (ImageView) searchFragment.getView().findViewById(R.id.iv_back_search);
        iv_search_back_two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ll_search_equipment_two.setVisibility(View.GONE);
                ll_two.setVisibility(View.VISIBLE);
                lvResults2.setVisibility(View.GONE);
                nums = 1;

                Intent intent1 = new Intent("Search.Equipment");
                LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent1);

            }
        });

        //设置监听
        searchView2.setSearchViewListener2(this);
        //设置adapter
        searchView2.setTipsHintAdapter(hintAdapter2);
        searchView2.setAutoCompleteAdapter(autoCompleteAdapter2);


    }


//    private void initData2() {
//        //从数据库获取数据
//        getDbData2();
//        //初始化热搜版数据
//      //  getHintData2();
//        //初始化自动补全数据
//     //   getAutoCompleteData2(null);
//        //初始化搜索结果数据
//        getResultData2(null);
//    }


    /**
     * 获取db 数据
     */
    private void getDbData2(List<GetDeviceAlarmListBean.DeviceListBean> data) {
        int size = data.size();
        dbData2 = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            dbData2.add(data.get(i));

        }
    }

    /**
     * 获取热搜版data 和adapter
     */
    private void getHintData2() {
        hintData2 = new ArrayList<>();
        for (int i = 1; i <= hintSize2; i++) {
            hintData2.add("热搜版" + i + "：Android自定义View");
        }
        hintAdapter2 = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, hintData2);
        Log.e("dfdfz", hintSize2 + "");
    }


    /**
     * 获取自动补全data 和adapter
     */
    private void getAutoCompleteData2(String text) {
        if (NetWorking.isNetworkAvailable(getActivity())) {
            if (autoCompleteData2 == null) {
                //初始化
                autoCompleteData2 = new ArrayList<>();
            } else {
                // 根据text 获取auto data
                autoCompleteData2.clear();

                Log.e("dfddsadf", "sadsadad");
                for (int i = 0, count = 0; i < dbData2.size()
                        && count < hintSize2; i++) {
                    Log.e("qwesad", dbData2.get(i).getDeviceName() + "");
                    if (dbData2.get(i).getDeviceName().contains(text.trim())) {
                        autoCompleteData2.add(dbData2.get(i).getDeviceName());
                        count++;
                    }
                }
            }
            if (autoCompleteAdapter2 == null) {
                autoCompleteAdapter2 = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, autoCompleteData2);
            } else {
                autoCompleteAdapter2.notifyDataSetChanged();
            }
        } else {
            if (language.equals("zh_cn")) {
                Toast.makeText(getActivity(), "当前网络不可用", Toast.LENGTH_SHORT).show();

            } else {
                Toast.makeText(getActivity(), "The current network is not available", Toast.LENGTH_SHORT).show();
            }
        }


    }


    /**
     * 获取搜索结果data和adapter
     */
    private void getResultData2(String text) {
        if (NetWorking.isNetworkAvailable(getActivity())) {
            if (resultData2 == null) {
                // 初始化
                resultData2 = new ArrayList<>();
            } else {
                resultData2.clear();
                if(s > 0){
                    for (int i = 0; i <resultData2.size() ; i++) {
                        resultData2.remove(i);
                    }
                }

                for (int i = 0; i < dbData2.size(); i++) {

                    if (dbData2.get(i).getDeviceName().toLowerCase().contains(text.trim().toLowerCase()) || dbData2.get(i).getDeviceName().toUpperCase().contains(text.trim().toUpperCase()) || dbData2.get(i).getDeviceName().contains(text.trim())) {
                        resultData2.add(dbData2.get(i));
                    }
                }

                s++;


            }
            if (resultAdapter2 == null) {
                Log.e("dzxf", resultData2.size() + "");
                Log.e("32132wxzasds", Station);
                resultAdapter2 = new SearchListAdapter(handler, getActivity(), resultData2, R.layout.item_equipment_two);
            } else {
                Log.e("32132wxzasds11", Station);
//                resultAdapter2 = new SearchListAdapter(Station, getActivity(), resultData2, R.layout.item_equipment_two);
                resultAdapter2.notifyDataSetChanged();
            }
        } else {
            if (language.equals("zh_cn")) {
                Toast.makeText(getActivity(), "当前网络不可用", Toast.LENGTH_SHORT).show();

            } else {
                Toast.makeText(getActivity(), "The current network is not available", Toast.LENGTH_SHORT).show();
            }
        }


    }


    @Override
    public void onRefreshAutoComplete2(String text) {
        //更新数据
        getAutoCompleteData2(text);
    }

    @Override
    public void onSearch2(String text) {

        if (text.equals("") || text.equals("null")) {
            lvResults2.setVisibility(View.GONE);
        } else {

            if (NetWorking.isNetworkAvailable(getActivity())) {
                //更新result数据
                getResultData2(text);
                lvResults2.setVisibility(View.VISIBLE);
                //第一次获取结果 还未配置适配器
                if (lvResults2.getAdapter() == null) {
                    //获取搜索数据 设置适配器
                    lvResults2.setAdapter(resultAdapter2);
                } else {
                    //更新搜索数据
                    resultAdapter2.notifyDataSetChanged();
                }
                if (language.equals("zh_cn")) {
                    Toast.makeText(getActivity(), "完成搜索", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(getActivity(), "Complete search", Toast.LENGTH_SHORT).show();
                }

            } else {
                if (language.equals("zh_cn")) {
                    Toast.makeText(getActivity(), "当前网络不可用", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(getActivity(), "The current network is not available", Toast.LENGTH_SHORT).show();
                }
            }

        }
    }


    //逆变器列表的返回键监听
    @Event(R.id.iv_back_equipment)
    private void imaClick(View view) {
        if (nums == 9){
            ll_two.setVisibility(View.GONE);
            spinner.setVisibility(View.GONE);
            tv_sp.setVisibility(View.GONE);
            iv_top.setVisibility(View.GONE);
            tv_title_top.setVisibility(View.VISIBLE);
            ll_search_equipment.setVisibility(View.VISIBLE);
            ll_equipment.setVisibility(View.GONE);
            nums = 4;
        }else {
            ll_two.setVisibility(View.GONE);
            ll.setVisibility(View.VISIBLE);
//        for (int i = 0; i < dbData2.size(); i++) {
//            dbData2.remove(i);
//        }
            tv_title_top.setVisibility(View.VISIBLE);
            spinner.setVisibility(View.GONE);
            iv_top.setVisibility(View.VISIBLE);
            tv_sp.setVisibility(View.GONE);
            Log.d("231231", String.valueOf(dbData2) + "1");
            nums = 0;
        }
    }

    //逆变器详情的返回键监听
    @Event(R.id.iv_back_equipment_three)
    private void ima2Click(View view) {
        if (nums == 8){
            ll_two.setVisibility(View.GONE);
            ll_search_equipment_two.setVisibility(View.VISIBLE);
            ll_three.setVisibility(View.GONE);
            spinner.setVisibility(View.VISIBLE);
            nums = 5;
        }else {
            ll_two.setVisibility(View.VISIBLE);
            ll_three.setVisibility(View.GONE);
            spinner.setVisibility(View.VISIBLE);
            nums = 1;
        }
    }

//    @Override
//    public void onResume() {
//        super.onResume();
//        getFocus();
//    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(final Message msg) {
            if (msg.what == 1) {
                final String sr = (String) msg.obj;
                Gson gson = new Gson();
                final GetDeviceStatusListBean bean = gson.fromJson(sr, GetDeviceStatusListBean.class);

                try {
                    if (bean.getRet().equals("Success")) {
                        tv_equ.setText(bean.getPageTitle());
                        data = new ArrayList<>();
                        for (int i = 0; i < bean.getDeviceList().size(); i++) {
                            data.add(bean.getDeviceList().get(i));
                        }
                        adapter = new EquipmentAdapter(getActivity(), data, R.layout.item_equipment);
                        listView.setAdapter(adapter);

                        //spenner添加数据
                        adapter1 = new ArrayAdapter<String>(getActivity(), R.layout.simple_spinner_item2, R.id.tv_spinner);
                        adapter1.setDropDownViewResource(R.layout.simple_spinner_dropdown_item2);


                        if (bean.getDeviceList().size() == 1) {
                            tv_sp.setText(bean.getDeviceList().get(0).getStationName());
                            Log.e("dferyu", bean.getDeviceList().get(0).getStationName() + "ddd");
                        } else {
                            for (int i = 0; i < bean.getDeviceList().size(); i++) {
                                adapter1.add(bean.getDeviceList().get(i).getStationName());
                            }
                        }
                        Log.e("qwerasd", bean.getDeviceList().size() + "");
                        //逆变器列表的
                        spinner.setAdapter(adapter1);
                        //item监听跳转到逆变器列表
                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                                if (position == 0) {

                                } else {
                                    //逆变器列表搜索的标题
                                    tv_search_title.setText(bean.getDeviceList().get((int) parent.getItemIdAtPosition(position)).getStationName());
                                    //详情标题添加数据
                                    tv_title_three.setText(bean.getDeviceList().get((int) parent.getItemIdAtPosition(position)).getStationName());
                                    //记录stationID
                                    Station = bean.getDeviceList().get((int) parent.getItemIdAtPosition(position)).getStationID();
                                    nums = 1;
                                    iv_top.setVisibility(View.GONE);
                                    ll_two.setVisibility(View.VISIBLE);
                                    ll.setVisibility(View.GONE);
                                    handler.sendMessage(handler.obtainMessage(2, bean.getDeviceList().get((int) parent.getItemIdAtPosition(position)).getStationID()));
                                    tv_title_top.setVisibility(View.GONE);
                                    if (bean.getDeviceList().size() == 1) {
                                        tv_sp.setVisibility(View.VISIBLE);
                                    } else {
                                        spinner.setVisibility(View.VISIBLE);
                                    }
                                    spinner.setSelection((int) parent.getItemIdAtPosition(position));
                                    Log.d("dsadsdwadw", (int) parent.getItemIdAtPosition(position) + "");
                                    dialog = new LoadingDialog(getActivity(), Loading.getText(getActivity()));
                                    //显示Dialog
                                    dialog.show();

                                }

                            }
                        });
                        Log.e("ssszzzxxx", "ddddddddddd");
                        getDbData(bean.getDeviceList());
                        //初始化热搜版数据
                        //  getHintData();
                        //初始化自动补全数据
                        //  getAutoCompleteData(null);
                        //初始化搜索结果数据

                        if (b == 0) {
                            getResultData(null);
                        }
                        b++;
                        lvResults.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                                nums = 9;
                                //记录StationID
                                Station = bean.getDeviceList().get(position).getStationID();
                                tv_title_three.setText(bean.getDeviceList().get(position).getStationName());
                                ll_equipment.setVisibility(View.VISIBLE);
                                ll_search_equipment.setVisibility(View.GONE);
                                ll_two.setVisibility(View.VISIBLE);
                                ll.setVisibility(View.GONE);
                                tv_title_top.setVisibility(View.GONE);
                                spinner.setVisibility(View.VISIBLE);
//                                lvResults.setVisibility(View.GONE);
                                Log.e("gbzcv", getpos(bean.getDeviceList().get(position).getStationName(), sr)+"");
                                Log.d("gbzcv1",bean.getDeviceList().get(position).getStationName());
                                spinner.setSelection(getpos(resultData.get(position).getStationName(), sr));
                                handler.sendMessage(handler.obtainMessage(2, resultData.get(position).getStationID()));
                                Intent intent1 = new Intent("Search.Equipment");
                                LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent1);
                                dialog = new LoadingDialog(getActivity(), Loading.getText(getActivity()));
                                //显示Dialog
                                dialog.show();
                            }
                        });

                        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

//                              dialog5 = new LoadingDialog(getActivity(), Loading.getText(getActivity()));
//                              dialog5.show();
                                tv_title_three.setText(bean.getDeviceList().get(position).getStationName());
                                //逆变器列表搜索的标题
                                tv_search_title.setText(bean.getDeviceList().get((int) parent.getItemIdAtPosition(position)).getStationName());
                                Station = bean.getDeviceList().get(position).getStationID();
                                Log.d("woxaxzwz", Station+"wode");
                                ll_two.setVisibility(View.VISIBLE);
                                ll.setVisibility(View.GONE);
                                handler.sendMessage(handler.obtainMessage(2, bean.getDeviceList().get(position).getStationID()));
                                Log.d("eeeeee", bean.getDeviceList().get(position).getStationID() + "000");
                                tv_title_top.setVisibility(View.GONE);
                                spinner.setVisibility(View.VISIBLE);

                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });
                        dialog.close();
                    } else {
                        dialog.close();
                        if (bean.getErrCode().equals("1")){
                            if (language.equals("zh_cn")){
                                Toast.makeText(getActivity(), "您的登录已失效，请重新登录", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getActivity(),LoginActivity.class);
                                startActivity(intent);
                                sendBroadcast1();
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
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
            if (msg.what == 2) {
                sr2 = (String) msg.obj;
                Log.d("eeeeee", sr2 + "000");
                stationID = sr2;
                SharedPreferences share = getActivity().getSharedPreferences("Set", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = share.edit();
                editor.putString("StationID", sr2);
                editor.apply();

                if (NetWorking.isNetworkAvailable(getActivity())) {
                    new MyThread1().start();
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
//                dialog5.close();
                final String sr = (String) msg.obj;
                Gson gson = new Gson();
                final GetDeviceAlarmListBean bean = gson.fromJson(sr, GetDeviceAlarmListBean.class);
                try {
                    if (bean.getRet().equals("Success")) {

                        //逆变器详情里面的spnner
                        //逆变器详情spenner添加数据
                        adapter5 = new ArrayAdapter<String>(getActivity(), R.layout.simple_spinner_item2, R.id.tv_spinner);
                        adapter5.setDropDownViewResource(R.layout.simple_spinner_dropdown_item2);

                        for (int i = 0; i < bean.getDeviceList().size(); i++) {
                            adapter5.add(bean.getDeviceList().get(i).getDeviceName());
                        }

                        Log.e("qweras321321d", bean.getDeviceList().size() + "");

                        //逆变器详情的
//                    sp_title_top3.setAdapter(adapter5);

//                    sp_title_top3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                        @Override
//                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//
//                            ll_two.setVisibility(View.GONE);
//                            ll_three.setVisibility(View.VISIBLE);
//                            dialog = new LoadingDialog(getActivity(), Loading.getText(getActivity()));
//                            //显示Dialog
//                            dialog.show();
//                            handler.sendMessage(handler.obtainMessage(4, bean.getDeviceList().get(position).getDeviceID()));
//
//
//                            tv_title_top.setVisibility(View.VISIBLE);
//                            spinner.setVisibility(View.GONE);
//
//                        }
//
//                        @Override
//                        public void onNothingSelected(AdapterView<?> parent) {
//
//                        }
//                    });

                        tv_equ2.setText(bean.getPageTitle());
                        final List<GetDeviceAlarmListBean.DeviceListBean> data2;
                        data2 = new ArrayList<>();


                        for (int i = 0; i < bean.getDeviceList().size(); i++) {
                            data2.add(bean.getDeviceList().get(i));
                        }
                        adapter2 = new EquipmentTwoAdapter(stationID, getActivity(), data2, R.layout.item_equipment_two);
                        listView2.setAdapter(adapter2);
                        //点击item跳转到逆变器详情
                        listView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                                if (position == 0) {

                                } else {
                                    //记录DeviceID
                                    Device = bean.getDeviceList().get((int) parent.getItemIdAtPosition(position)).getDeviceID();
                                    nums = 2;
                                    ll_two.setVisibility(View.GONE);
                                    ll_three.setVisibility(View.VISIBLE);
                                dialog = new LoadingDialog(getActivity(), Loading.getText(getActivity()));
//                                //显示Dialog
                                dialog.show();
                                    handler.sendMessage(handler.obtainMessage(4, bean.getDeviceList().get((int) parent.getItemIdAtPosition(position)).getDeviceID()));
//                                sp_title_top3.setSelection((int) parent.getItemIdAtPosition(position));
//                                dialog = new LoadingDialog(getActivity(), Loading.getText(getActivity()));
//                                //显示Dialog
//                                dialog.show();
                                    eqm = ((int) parent.getItemIdAtPosition(position));
                                    tv_title_top.setVisibility(View.VISIBLE);
                                    spinner.setVisibility(View.GONE);
                                    Log.d("1321sa", eqm + "/");
                                    if (eqm == 0) {
                                        iv_up.setVisibility(View.GONE);
                                        iv_up1.setVisibility(View.VISIBLE);
                                        iv_down.setVisibility(View.VISIBLE);
                                        iv_down1.setVisibility(View.GONE);
                                        iv_up.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                if (eqm > 0) {
                                                    dialog = new LoadingDialog(getActivity(), Loading.getText(getActivity()));
//                                //显示Dialog
                                                    dialog.show();
                                                    handler.sendMessage(handler.obtainMessage(4, bean.getDeviceList().get(eqm - 1).getDeviceID()));
                                                    eqm = eqm - 1;
                                                    if (eqm == 0) {
                                                        iv_up.setVisibility(View.GONE);
                                                        iv_up1.setVisibility(View.VISIBLE);
                                                        iv_down.setVisibility(View.VISIBLE);
                                                        iv_down1.setVisibility(View.GONE);
                                                    } else if (eqm == bean.getDeviceList().size() - 1) {
                                                        iv_up.setVisibility(View.VISIBLE);
                                                        iv_up1.setVisibility(View.GONE);
                                                        iv_down.setVisibility(View.GONE);
                                                        iv_down1.setVisibility(View.VISIBLE);
                                                    } else {
                                                        iv_up.setVisibility(View.VISIBLE);
                                                        iv_up1.setVisibility(View.GONE);
                                                        iv_down.setVisibility(View.VISIBLE);
                                                        iv_down1.setVisibility(View.GONE);
                                                    }

                                                } else {


                                                }

                                            }
                                        });
                                        iv_down.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                if (eqm < bean.getDeviceList().size() - 1) {
                                                    dialog = new LoadingDialog(getActivity(), Loading.getText(getActivity()));
//                                //显示Dialog
                                                    dialog.show();
                                                    handler.sendMessage(handler.obtainMessage(4, bean.getDeviceList().get(eqm + 1).getDeviceID()));
                                                    eqm = eqm + 1;
                                                    if (eqm == 0) {
                                                        iv_up.setVisibility(View.GONE);
                                                        iv_up1.setVisibility(View.VISIBLE);
                                                        iv_down.setVisibility(View.VISIBLE);
                                                        iv_down1.setVisibility(View.GONE);
                                                    } else if (eqm == bean.getDeviceList().size() - 1) {
                                                        iv_up.setVisibility(View.VISIBLE);
                                                        iv_up1.setVisibility(View.GONE);
                                                        iv_down.setVisibility(View.GONE);
                                                        iv_down1.setVisibility(View.VISIBLE);
                                                    } else {
                                                        iv_up.setVisibility(View.VISIBLE);
                                                        iv_up1.setVisibility(View.GONE);
                                                        iv_down.setVisibility(View.VISIBLE);
                                                        iv_down1.setVisibility(View.GONE);
                                                    }

                                                } else {

                                                }
                                            }
                                        });
                                    } else if (eqm == bean.getDeviceList().size() - 1) {
                                        iv_up.setVisibility(View.VISIBLE);
                                        iv_up1.setVisibility(View.GONE);
                                        iv_down.setVisibility(View.GONE);
                                        iv_down1.setVisibility(View.VISIBLE);
                                        iv_up.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                if (eqm > 0) {
                                                    dialog = new LoadingDialog(getActivity(), Loading.getText(getActivity()));
//                                //显示Dialog
                                                    dialog.show();
                                                    handler.sendMessage(handler.obtainMessage(4, bean.getDeviceList().get(eqm - 1).getDeviceID()));
                                                    eqm = eqm - 1;
                                                    if (eqm == 0) {
                                                        iv_up.setVisibility(View.GONE);
                                                        iv_up1.setVisibility(View.VISIBLE);
                                                        iv_down.setVisibility(View.VISIBLE);
                                                        iv_down1.setVisibility(View.GONE);
                                                    } else if (eqm == bean.getDeviceList().size() - 1) {
                                                        iv_up.setVisibility(View.VISIBLE);
                                                        iv_up1.setVisibility(View.GONE);
                                                        iv_down.setVisibility(View.GONE);
                                                        iv_down1.setVisibility(View.VISIBLE);
                                                    } else {
                                                        iv_up.setVisibility(View.VISIBLE);
                                                        iv_up1.setVisibility(View.GONE);
                                                        iv_down.setVisibility(View.VISIBLE);
                                                        iv_down1.setVisibility(View.GONE);
                                                    }
                                                } else {

                                                }

                                            }
                                        });
                                        iv_down.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                if (eqm < bean.getDeviceList().size() - 1) {
                                                    dialog = new LoadingDialog(getActivity(), Loading.getText(getActivity()));
//                                //显示Dialog
                                                    dialog.show();
                                                    handler.sendMessage(handler.obtainMessage(4, bean.getDeviceList().get(eqm + 1).getDeviceID()));
                                                    eqm = eqm + 1;
                                                    if (eqm == 0) {
                                                        iv_up.setVisibility(View.GONE);
                                                        iv_up1.setVisibility(View.VISIBLE);
                                                        iv_down.setVisibility(View.VISIBLE);
                                                        iv_down1.setVisibility(View.GONE);
                                                    } else if (eqm == bean.getDeviceList().size() - 1) {
                                                        iv_up.setVisibility(View.VISIBLE);
                                                        iv_up1.setVisibility(View.GONE);
                                                        iv_down.setVisibility(View.GONE);
                                                        iv_down1.setVisibility(View.VISIBLE);
                                                    } else {
                                                        iv_up.setVisibility(View.VISIBLE);
                                                        iv_up1.setVisibility(View.GONE);
                                                        iv_down.setVisibility(View.VISIBLE);
                                                        iv_down1.setVisibility(View.GONE);
                                                    }
                                                } else {

                                                }
                                            }
                                        });
                                    } else {
                                        iv_up.setVisibility(View.VISIBLE);
                                        iv_up1.setVisibility(View.GONE);
                                        iv_down.setVisibility(View.VISIBLE);
                                        iv_down1.setVisibility(View.GONE);
                                        iv_up.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                if (eqm > 0) {
                                                    dialog = new LoadingDialog(getActivity(), Loading.getText(getActivity()));
//                                //显示Dialog
                                                    dialog.show();
                                                    handler.sendMessage(handler.obtainMessage(4, bean.getDeviceList().get(eqm - 1).getDeviceID()));
                                                    eqm = eqm - 1;
                                                    if (eqm == 0) {
                                                        iv_up.setVisibility(View.GONE);
                                                        iv_up1.setVisibility(View.VISIBLE);
                                                        iv_down.setVisibility(View.VISIBLE);
                                                        iv_down1.setVisibility(View.GONE);
                                                    } else if (eqm == bean.getDeviceList().size() - 1) {
                                                        iv_up.setVisibility(View.VISIBLE);
                                                        iv_up1.setVisibility(View.GONE);
                                                        iv_down.setVisibility(View.GONE);
                                                        iv_down1.setVisibility(View.VISIBLE);
                                                    } else {
                                                        iv_up.setVisibility(View.VISIBLE);
                                                        iv_up1.setVisibility(View.GONE);
                                                        iv_down.setVisibility(View.VISIBLE);
                                                        iv_down1.setVisibility(View.GONE);
                                                    }
                                                } else {

                                                }

                                            }
                                        });
                                        iv_down.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                if (eqm < bean.getDeviceList().size() - 1) {
                                                    dialog = new LoadingDialog(getActivity(), Loading.getText(getActivity()));
//                                //显示Dialog
                                                    dialog.show();
                                                    handler.sendMessage(handler.obtainMessage(4, bean.getDeviceList().get(eqm + 1).getDeviceID()));
                                                    eqm = eqm + 1;
                                                    if (eqm == 0) {
                                                        iv_up.setVisibility(View.GONE);
                                                        iv_up1.setVisibility(View.VISIBLE);
                                                        iv_down.setVisibility(View.VISIBLE);
                                                        iv_down1.setVisibility(View.GONE);
                                                    } else if (eqm == bean.getDeviceList().size() - 1) {
                                                        iv_up.setVisibility(View.VISIBLE);
                                                        iv_up1.setVisibility(View.GONE);
                                                        iv_down.setVisibility(View.GONE);
                                                        iv_down1.setVisibility(View.VISIBLE);
                                                    } else {
                                                        iv_up.setVisibility(View.VISIBLE);
                                                        iv_up1.setVisibility(View.GONE);
                                                        iv_down.setVisibility(View.VISIBLE);
                                                        iv_down1.setVisibility(View.GONE);
                                                    }
                                                } else {

                                                }
                                            }
                                        });
                                    }

                                }

                            }
                        });

                        getDbData2(bean.getDeviceList());
                        //初始化热搜版数据
                        //  getHintData2();
                        //初始化自动补全数据
                        //   getAutoCompleteData2(null);
                        //初始化搜索结果数据
                        if (a == 0) {
                            getResultData2(null);
                        }
                        a++;
                        //逆变器列表搜索的item监听
                        lvResults2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                                nums = 8;
                                eqm =  getpos1(resultData2.get(position).getDeviceName(),data2);
                                //记录DeviceID
                                Device = resultData2.get(position).getDeviceID();

                                Log.d("wodejiazainali", eqm+"....");
                                if (eqm == 0) {



                                    iv_up.setVisibility(View.GONE);
                                    iv_up1.setVisibility(View.VISIBLE);
                                    iv_down.setVisibility(View.VISIBLE);
                                    iv_down1.setVisibility(View.GONE);
                                    iv_up.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            if (eqm > 0) {
                                                dialog = new LoadingDialog(getActivity(), Loading.getText(getActivity()));
//                                //显示Dialog
                                                dialog.show();
                                                handler.sendMessage(handler.obtainMessage(4, bean.getDeviceList().get(eqm - 1).getDeviceID()));
                                                eqm = eqm - 1;
                                                if (eqm == 0) {
                                                    iv_up.setVisibility(View.GONE);
                                                    iv_up1.setVisibility(View.VISIBLE);
                                                    iv_down.setVisibility(View.VISIBLE);
                                                    iv_down1.setVisibility(View.GONE);
                                                } else if (eqm == bean.getDeviceList().size() - 1) {
                                                    iv_up.setVisibility(View.VISIBLE);
                                                    iv_up1.setVisibility(View.GONE);
                                                    iv_down.setVisibility(View.GONE);
                                                    iv_down1.setVisibility(View.VISIBLE);
                                                } else {
                                                    iv_up.setVisibility(View.VISIBLE);
                                                    iv_up1.setVisibility(View.GONE);
                                                    iv_down.setVisibility(View.VISIBLE);
                                                    iv_down1.setVisibility(View.GONE);
                                                }

                                            } else {


                                            }

                                        }
                                    });
                                    iv_down.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            if (eqm < bean.getDeviceList().size() - 1) {
                                                dialog = new LoadingDialog(getActivity(), Loading.getText(getActivity()));
//                                //显示Dialog
                                                dialog.show();
                                                handler.sendMessage(handler.obtainMessage(4, bean.getDeviceList().get(eqm + 1).getDeviceID()));
                                                eqm = eqm + 1;
                                                if (eqm == 0) {
                                                    iv_up.setVisibility(View.GONE);
                                                    iv_up1.setVisibility(View.VISIBLE);
                                                    iv_down.setVisibility(View.VISIBLE);
                                                    iv_down1.setVisibility(View.GONE);
                                                } else if (eqm == bean.getDeviceList().size() - 1) {
                                                    iv_up.setVisibility(View.VISIBLE);
                                                    iv_up1.setVisibility(View.GONE);
                                                    iv_down.setVisibility(View.GONE);
                                                    iv_down1.setVisibility(View.VISIBLE);
                                                } else {
                                                    iv_up.setVisibility(View.VISIBLE);
                                                    iv_up1.setVisibility(View.GONE);
                                                    iv_down.setVisibility(View.VISIBLE);
                                                    iv_down1.setVisibility(View.GONE);
                                                }

                                            } else {

                                            }
                                        }
                                    });
                                } else if (eqm == bean.getDeviceList().size() - 1) {
                                    iv_up.setVisibility(View.VISIBLE);
                                    iv_up1.setVisibility(View.GONE);
                                    iv_down.setVisibility(View.GONE);
                                    iv_down1.setVisibility(View.VISIBLE);
                                    iv_up.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            if (eqm > 0) {
                                                dialog = new LoadingDialog(getActivity(), Loading.getText(getActivity()));
//                                //显示Dialog
                                                dialog.show();
                                                handler.sendMessage(handler.obtainMessage(4, bean.getDeviceList().get(eqm - 1).getDeviceID()));
                                                eqm = eqm - 1;
                                                if (eqm == 0) {
                                                    iv_up.setVisibility(View.GONE);
                                                    iv_up1.setVisibility(View.VISIBLE);
                                                    iv_down.setVisibility(View.VISIBLE);
                                                    iv_down1.setVisibility(View.GONE);
                                                } else if (eqm == bean.getDeviceList().size() - 1) {
                                                    iv_up.setVisibility(View.VISIBLE);
                                                    iv_up1.setVisibility(View.GONE);
                                                    iv_down.setVisibility(View.GONE);
                                                    iv_down1.setVisibility(View.VISIBLE);
                                                } else {
                                                    iv_up.setVisibility(View.VISIBLE);
                                                    iv_up1.setVisibility(View.GONE);
                                                    iv_down.setVisibility(View.VISIBLE);
                                                    iv_down1.setVisibility(View.GONE);
                                                }
                                            } else {

                                            }

                                        }
                                    });
                                    iv_down.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            if (eqm < bean.getDeviceList().size() - 1) {
                                                dialog = new LoadingDialog(getActivity(), Loading.getText(getActivity()));
//                                //显示Dialog
                                                dialog.show();
                                                handler.sendMessage(handler.obtainMessage(4, bean.getDeviceList().get(eqm + 1).getDeviceID()));
                                                eqm = eqm + 1;
                                                if (eqm == 0) {
                                                    iv_up.setVisibility(View.GONE);
                                                    iv_up1.setVisibility(View.VISIBLE);
                                                    iv_down.setVisibility(View.VISIBLE);
                                                    iv_down1.setVisibility(View.GONE);
                                                } else if (eqm == bean.getDeviceList().size() - 1) {
                                                    iv_up.setVisibility(View.VISIBLE);
                                                    iv_up1.setVisibility(View.GONE);
                                                    iv_down.setVisibility(View.GONE);
                                                    iv_down1.setVisibility(View.VISIBLE);
                                                } else {
                                                    iv_up.setVisibility(View.VISIBLE);
                                                    iv_up1.setVisibility(View.GONE);
                                                    iv_down.setVisibility(View.VISIBLE);
                                                    iv_down1.setVisibility(View.GONE);
                                                }
                                            } else {

                                            }
                                        }
                                    });
                                } else {
                                    iv_up.setVisibility(View.VISIBLE);
                                    iv_up1.setVisibility(View.GONE);
                                    iv_down.setVisibility(View.VISIBLE);
                                    iv_down1.setVisibility(View.GONE);
                                    iv_up.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            if (eqm > 0) {
                                                dialog = new LoadingDialog(getActivity(), Loading.getText(getActivity()));
//                                //显示Dialog
                                                dialog.show();
                                                handler.sendMessage(handler.obtainMessage(4, bean.getDeviceList().get(eqm - 1).getDeviceID()));
                                                eqm = eqm - 1;
                                                if (eqm == 0) {
                                                    iv_up.setVisibility(View.GONE);
                                                    iv_up1.setVisibility(View.VISIBLE);
                                                    iv_down.setVisibility(View.VISIBLE);
                                                    iv_down1.setVisibility(View.GONE);
                                                } else if (eqm == bean.getDeviceList().size() - 1) {
                                                    iv_up.setVisibility(View.VISIBLE);
                                                    iv_up1.setVisibility(View.GONE);
                                                    iv_down.setVisibility(View.GONE);
                                                    iv_down1.setVisibility(View.VISIBLE);
                                                } else {
                                                    iv_up.setVisibility(View.VISIBLE);
                                                    iv_up1.setVisibility(View.GONE);
                                                    iv_down.setVisibility(View.VISIBLE);
                                                    iv_down1.setVisibility(View.GONE);
                                                }
                                            } else {

                                            }

                                        }
                                    });
                                    iv_down.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            if (eqm < bean.getDeviceList().size() - 1) {
                                                dialog = new LoadingDialog(getActivity(), Loading.getText(getActivity()));
//                                //显示Dialog
                                                dialog.show();
                                                handler.sendMessage(handler.obtainMessage(4, bean.getDeviceList().get(eqm + 1).getDeviceID()));
                                                eqm = eqm + 1;
                                                if (eqm == 0) {
                                                    iv_up.setVisibility(View.GONE);
                                                    iv_up1.setVisibility(View.VISIBLE);
                                                    iv_down.setVisibility(View.VISIBLE);
                                                    iv_down1.setVisibility(View.GONE);
                                                } else if (eqm == bean.getDeviceList().size() - 1) {
                                                    iv_up.setVisibility(View.VISIBLE);
                                                    iv_up1.setVisibility(View.GONE);
                                                    iv_down.setVisibility(View.GONE);
                                                    iv_down1.setVisibility(View.VISIBLE);
                                                } else {
                                                    iv_up.setVisibility(View.VISIBLE);
                                                    iv_up1.setVisibility(View.GONE);
                                                    iv_down.setVisibility(View.VISIBLE);
                                                    iv_down1.setVisibility(View.GONE);
                                                }
                                            } else {

                                            }
                                        }
                                    });
                                }
                                ll_search_equipment_two.setVisibility(View.GONE);
                                ll_two.setVisibility(View.VISIBLE);
                                ll_two.setVisibility(View.GONE);
                                ll_three.setVisibility(View.VISIBLE);
                                tv_title_top.setVisibility(View.VISIBLE);
                                spinner.setVisibility(View.GONE);
                                dialog = new LoadingDialog(getActivity(), Loading.getText(getActivity()));
//                                //显示Dialog
                                dialog.show();
                                handler.sendMessage(handler.obtainMessage(4, resultData2.get(position).getDeviceID()));
                                Intent intent1 = new Intent("Search.Equipment");
                                LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent1);
                            }
                        });

                        dialog.close();
                    } else {
                        dialog.close();
                        if (bean.getErrCode().equals("1")){
                            if (language.equals("zh_cn")){
                                Toast.makeText(getActivity(), "您的登录已失效，请重新登录", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getActivity(),LoginActivity.class);
                                startActivity(intent);
                                sendBroadcast1();
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
                sr1 = (String) msg.obj;

                if (NetWorking.isNetworkAvailable(getActivity())) {
                    new MyThread2().start();

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
                dialog.close();

                String sr = (String) msg.obj;
                Gson gson = new Gson();
                final GetDeviceAllDataBean bean = gson.fromJson(sr, GetDeviceAllDataBean.class);

                try {
                    if (bean.getRet().equals("Success")) {
                        if (bean.getBaseInfo().getDeviceAlarm().equals("0")) {
                            iv_red_point.setImageResource(R.drawable.text_shape_green);
                        } else {
                            iv_red_point.setImageResource(R.drawable.text_shape_red);
                        }
                        //点击红点进入警报界面
                        iv_red_point.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                if (bean.getBaseInfo().getDeviceAlarm().equals("0")) {

                                } else {
                                    Device = bean.getBaseInfo().getDeviceID();
                                    sendBroadcast();
                                    Intent intent2 = new Intent("MainActivity.broadcast.alert");
                                    intent2.putExtra("se", Station);
                                    Log.e("ttttt", Station + "qqq" + Device);
                                    intent2.putExtra("id", Device);
                                    intent2.putExtra("device","device");
                                    LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent2);
                                }


                            }
                        });

                        tv_title.setText(bean.getBaseInfo().getDeviceName());
                        tv_equipment_three_station.setText(bean.getBaseInfo().getStationNameStr() + ": " + bean.getBaseInfo().getStationName());
                        tv_equipment_three_device.setText(bean.getBaseInfo().getInverterManufacturerStr() + ": " + bean.getBaseInfo().getInverterManufacturer());
                        tv_equipment_three_number.setText(bean.getBaseInfo().getInverterModelNumberStr() + ": " + bean.getBaseInfo().getInverterModelNumber());
                        tv_InputPowerStr.setText(bean.getBaseInfo().getInputPowerStr() + "");
                        tv_InputPower.setText(bean.getBaseInfo().getInputPower() + "");
                        tv_InputPowerUnit.setText(bean.getBaseInfo().getInputPowerUnit() + "");
                        tv_PowerStr.setText(bean.getBaseInfo().getPowerStr());
                        tv_Power.setText(bean.getBaseInfo().getPower());
                        tv_PowerUnit.setText(bean.getBaseInfo().getPowerUnit());
                        tv_TransferEfficiencyStr.setText(bean.getBaseInfo().getTransferEfficiencyStr());
                        tv_TransferEfficiency.setText(bean.getBaseInfo().getTransferEfficiency());
                        tv_TransferEfficiencyUnit.setText(bean.getBaseInfo().getTransferEfficiencyUnit());
                        tv_WorkingTemperatureStr.setText(bean.getBaseInfo().getWorkingTemperatureStr());
                        tv_WorkingTemperature.setText(bean.getBaseInfo().getWorkingTemperature());
                        tv_WorkingTemperatureUnit.setText(bean.getBaseInfo().getWorkingTemperatureUnit());
                        tv_DayPowerGenerationStr.setText(bean.getBaseInfo().getDayPowerGenerationStr());
                        tv_DayPowerGeneration.setText(bean.getBaseInfo().getDayPowerGeneration());
                        tv_DayPowerGenerationUnit.setText(bean.getBaseInfo().getDayPowerGenerationUnit());
                        tv_TotalPowerGeneration.setText(bean.getBaseInfo().getTotalPowerGeneration());
                        tv_TotalPowerGenerationStr.setText(bean.getBaseInfo().getTotalPowerGenerationStr());
                        tv_TotalPowerGenerationUnit.setText(bean.getBaseInfo().getTotalPowerGenerationUnit());
                        iv_tag.setVisibility(VISIBLE);
                        iv_tag2.setVisibility(VISIBLE);
                        iv_tag3.setVisibility(VISIBLE);
                        iv_tag4.setVisibility(VISIBLE);
                        iv_tag5.setVisibility(VISIBLE);
                        iv_tag6.setVisibility(VISIBLE);
                        iv_tag7.setVisibility(VISIBLE);
                        iv_red_point.setVisibility(VISIBLE);

                    } else {
                        dialog.close();
                        if (bean.getErrCode().equals("1")){
                            if (language.equals("zh_cn")){
                                Toast.makeText(getActivity(), "您的登录已失效，请重新登录", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getActivity(),LoginActivity.class);
                                startActivity(intent);
                                sendBroadcast1();
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
            if (msg.what ==11){
                String c = (String) msg.obj;
                sendBroadcast();
                Intent intent2 = new Intent("MainActivity.broadcast.alert");
                intent2.putExtra("se", stationID);
                intent2.putExtra("id", c);
                intent2.putExtra("device","device");
                Log.d("wodajdsmzxcz", c+"@@@"+ stationID);
                LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent2);
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
                object.put("Cmd", "GetDeviceStatusList");
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


    class MyThread1 extends Thread {
        @Override
        public void run() {
            // TODO Auto-generated method stub

            SharedPreferences sp = getActivity().getSharedPreferences("Set", getActivity().MODE_APPEND);
            String ID = sp.getString("SessionID", "");
            SharedPreferences sp1 = getActivity().getSharedPreferences("IP", getActivity().MODE_APPEND);
            String ip = sp1.getString("Ip", "");
            JSONObject object = new JSONObject();
            try {
                object.put("Cmd", "GetDeviceAlarmList");
                object.put("Dev", "app");
                object.put("SessionID", ID);
                object.put("StationID", sr2);
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            String sr = Post.sendPost(
                    "http://" + ip + "/SPMSWcfService/SPMSMainHandler.ashx", object);
            Log.d("coming", sr);
            handler.sendMessage(handler.obtainMessage(3, sr));
        }
    }


    class MyThread2 extends Thread {
        @Override
        public void run() {
            // TODO Auto-generated method stub
            SharedPreferences sp = getActivity().getSharedPreferences("Set", getActivity().MODE_APPEND);
            String ID = sp.getString("SessionID", "");
            String ID1 = sp.getString("StationID", "");
            SharedPreferences sp1 = getActivity().getSharedPreferences("IP", getActivity().MODE_APPEND);
            String ip = sp1.getString("Ip", "");
            JSONObject object = new JSONObject();
            try {
                object.put("Cmd", "GetDeviceAllData");
                object.put("Dev", "app");
                object.put("DeviceType", "Dev_Inverter");
                object.put("SessionID", ID);
                object.put("StationID", ID1);
                object.put("DeviceID", sr1);
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            String sr = Post.sendPost(
                    "http://" + ip + "/SPMSWcfService/SPMSMainHandler.ashx", object);
            Log.d("coming", sr);
            handler.sendMessage(handler.obtainMessage(5, sr));
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
                    Log.e("aaa", "点击了返回键");
                    if (nums == 1) {
                        ll_two.setVisibility(View.GONE);
                        ll.setVisibility(View.VISIBLE);
                        tv_title_top.setVisibility(View.VISIBLE);
                        spinner.setVisibility(View.GONE);
                        iv_top.setVisibility(View.VISIBLE);
                        tv_sp.setVisibility(View.GONE);
                        Log.d("231231", String.valueOf(dbData2) + "1");
                        lvResults.setVisibility(View.GONE);
                        nums = 0;
                    } else if (nums == 2) {
                        lvResults.setVisibility(View.GONE);
                        ll_three.setVisibility(View.GONE);
                        ll_two.setVisibility(View.GONE);
                        ll.setVisibility(View.VISIBLE);
                        tv_title_top.setVisibility(View.VISIBLE);
                        spinner.setVisibility(View.GONE);
                        iv_top.setVisibility(View.VISIBLE);
                        tv_sp.setVisibility(View.GONE);
                        Log.d("231231", String.valueOf(dbData2) + "1");
                        nums = 0;
                    } else if (nums == 4) {
                        lvResults.setVisibility(View.GONE);
                        iv_top.setVisibility(View.VISIBLE);
                        ll_equipment.setVisibility(View.VISIBLE);
                        ll_search_equipment.setVisibility(View.GONE);
                        nums = 0;
                    } else if (nums == 5) {
                        ll_search_equipment_two.setVisibility(View.GONE);
                        ll_two.setVisibility(View.GONE);
                        ll.setVisibility(View.VISIBLE);
                        tv_title_top.setVisibility(View.VISIBLE);
                        spinner.setVisibility(View.GONE);
                        iv_top.setVisibility(View.VISIBLE);
                        tv_sp.setVisibility(View.GONE);
                        Log.d("231231", String.valueOf(dbData2) + "1");
                        lvResults.setVisibility(View.GONE);
                        nums = 0;
                    } else if (nums == 9){
                        ll_two.setVisibility(View.GONE);
                        ll.setVisibility(View.VISIBLE);
                        tv_title_top.setVisibility(View.VISIBLE);
                        spinner.setVisibility(View.GONE);
                        iv_top.setVisibility(View.VISIBLE);
                        tv_sp.setVisibility(View.GONE);
                        lvResults.setVisibility(View.GONE);
                        Log.d("231231", String.valueOf(dbData2) + "1");
                        nums = 0;
                    }else if (nums == 8){
                        ll_three.setVisibility(View.GONE);
                        ll_two.setVisibility(View.GONE);
                        ll.setVisibility(View.VISIBLE);
                        tv_title_top.setVisibility(View.VISIBLE);
                        spinner.setVisibility(View.GONE);
                        iv_top.setVisibility(View.VISIBLE);
                        tv_sp.setVisibility(View.GONE);
                        Log.d("231231", String.valueOf(dbData2) + "1");
                        nums = 0;
                        lvResults.setVisibility(View.GONE);
                        lvResults2.setVisibility(View.GONE);

                    }else {
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
                Toast.makeText(getActivity(), "Then click one exit procedure", Toast.LENGTH_SHORT).show();
            }

            mExitTime = System.currentTimeMillis();
        } else {
            getActivity().finish();
            System.exit(0);
        }

    }


    public class MyListener implements TwoPullToRefresh.OnRefreshListenerTwo {
        @Override
        public void onRefresh(final TwoPullToRefresh pullToRefreshLayout) {

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
        public void onLoadMore(final TwoPullToRefresh pullToRefreshLayout) {
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


    public class MyListener2 implements TwoPullToRefresh.OnRefreshListenerTwo {

        @Override
        public void onRefresh(final TwoPullToRefresh pullToRefreshLayout) {

            // 下拉刷新操作
            new Handler() {
                @Override
                public void handleMessage(Message msg) {

                    dialog = new LoadingDialog(getActivity(), Loading.getText(getActivity()));
                    //显示Dialog
                    dialog.show();

                    if (NetWorking.isNetworkAvailable(getActivity())) {
                        new MyThread1().start();
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
        public void onLoadMore(final TwoPullToRefresh pullToRefreshLayout) {


            // 加载操作
            new Handler() {
                @Override
                public void handleMessage(Message msg) {

                    dialog = new LoadingDialog(getActivity(), Loading.getText(getActivity()));
                    //显示Dialog
                    dialog.show();
                    if (NetWorking.isNetworkAvailable(getActivity())) {
                        new MyThread1().start();
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


    public class MyListener3 implements TwoPullToRefresh.OnRefreshListenerTwo {

        @Override
        public void onRefresh(final TwoPullToRefresh pullToRefreshLayout) {

            // 下拉刷新操作
            new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    dialog = new LoadingDialog(getActivity(), Loading.getText(getActivity()));
                    //显示Dialog
                    dialog.show();
                    if (NetWorking.isNetworkAvailable(getActivity())) {
                        new MyThread2().start();
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
        public void onLoadMore(final TwoPullToRefresh pullToRefreshLayout) {


            // 加载操作
            new Handler() {
                @Override
                public void handleMessage(Message msg) {

                    dialog = new LoadingDialog(getActivity(), Loading.getText(getActivity()));
                    //显示Dialog
                    dialog.show();
                    if (NetWorking.isNetworkAvailable(getActivity())) {
                        new MyThread2().start();
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


    //发送广播的方法
    public void sendBroadcast1() {
        Intent intent1 = new Intent("com.main");
        LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent1);

    }

    //
    public int getpos(String title, String data){
        int a = 0;
        Gson gson = new Gson();
        final GetDeviceStatusListBean bean = gson.fromJson(data, GetDeviceStatusListBean.class);
        for (int i = 0; i < bean.getDeviceList().size(); i++) {
            if (bean.getDeviceList().get(i).getStationName().equals(title)){
                a = i;
                break;
            }
        }
        return a;
    }

    public int getpos1(String title,  List<GetDeviceAlarmListBean.DeviceListBean> data){
        int a = 0;
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).getDeviceName().equals(title)){
                a = i;
                break;
            }
        }
        return a;
    }



}
