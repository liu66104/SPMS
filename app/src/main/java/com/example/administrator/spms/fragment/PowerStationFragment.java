package com.example.administrator.spms.fragment;

import android.annotation.TargetApi;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
import com.example.administrator.spms.adapter.GridViewAdapter;
import com.example.administrator.spms.adapter.SearchAdapter;
import com.example.administrator.spms.base.BaseFragment;
import com.example.administrator.spms.base.Loading;
import com.example.administrator.spms.base.LoadingDialog;
import com.example.administrator.spms.base.NetWorking;
import com.example.administrator.spms.base.Post;
import com.example.administrator.spms.base.SearchView;
import com.example.administrator.spms.model.GetDeviceStatusListBean;
import com.example.administrator.spms.model.GetStationListBean;
import com.example.administrator.spms.refresh.PullToRefreshLayout;
import com.example.administrator.spms.refresh.PullableGridView;
import com.example.administrator.spms.refresh.TwoPullToRefresh;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

import static android.view.View.VISIBLE;

/**
 * Created by Administrator on 2016/10/24.
 * 电站列表
 */

@ContentView(R.layout.fragment_power)
public class PowerStationFragment extends BaseFragment implements SearchView.SearchViewListener {
    private int num_tag = 0;

    @ViewInject(R.id.rl_station_try)
    private RelativeLayout rl_station_try;

    private View mViewObserved;//被监听的视图
    private int usableHeightPrevious;//视图变化前的可用高度
    private List<GetStationListBean.StationsBean> datas;

    private int nums = 0; //判断返回
    //电站的top按键
    private ArrayAdapter<String> adapter1;
    @ViewInject(R.id.iv_top_station)
    private ImageView iv_top;
    //记录stationid
    private String stationID;
    private View view_pop;
    private PopupWindow mPopupwinow = null;
    private ImageView mPreSelectedBt;
    private GridViewAdapter gridViewAdapter;
    private List<String> data;
    private int a = 0;
    private Handler handler8;
    //整体布局
    @ViewInject(R.id.ll_station)
    private LinearLayout ll_station;
    //下拉框
    @ViewInject(R.id.spinner1)
    private Spinner spinner;
    @ViewInject(R.id.vp_power)
    private ViewPager viewPager;
    //点的group布局
    @ViewInject(R.id.pointGroup)
    private LinearLayout pointGroup;

    //标题
    @ViewInject(R.id.tv_list_power)
    private TextView tv_list_power;

    @ViewInject(R.id.gv_power)
    private PullableGridView gridView;
    //菜单按键
    @ViewInject(R.id.iv_point)
    private ImageView iv_point;
    //viewpager的linearLayout布局
    @ViewInject(R.id.ll_power)
    private LinearLayout linearLayout;
    //文字电站列表的布局
    @ViewInject(R.id.rl_power)
    private RelativeLayout relativeLayout;
    //返回键
    @ViewInject(R.id.iv_back_power)
    private ImageView imageView_back;
    //搜索键
    @ViewInject(R.id.iv_search_station)
    private ImageView iv_search;
    //fragment的线性布局
    @ViewInject(R.id.ll_fm_station)
    private LinearLayout ll_fm_station;

    @ViewInject(R.id.tv_power_title)
    private TextView tv_title;
    @ViewInject(R.id.tv_power_title_search)
    private TextView tv_title1;


    private String title_main;

    //记录viewpager位置
    private int num;

    private   GetStationListBean bean;


//    @ViewInject(R.id.tv_power_title1)
//    private TextView tv_title1;

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
    private SearchAdapter resultAdapter;

    private List<GetStationListBean.StationsBean> dbData;

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
    private List<GetStationListBean.StationsBean> resultData;

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
    @ViewInject(R.id.iv_back_search)
    private ImageView iv_search_back;
    private LoadingDialog dialog;


    //定义广播管理器，广播接收器 用来接收
    private LocalBroadcastManager broadcastManager3;
    private IntentFilter intentFilter3;
    private BroadcastReceiver mReceiver3;

    private String language;

    /**
     * 设置提示框显示项的个数
     *
     * @param hintSize 提示框显示个数
     */

    private WindowManager mWm;

    public static void setHintSize(int hintSize) {
        PowerStationFragment.hintSize = hintSize;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mViewObserved = rl_station_try;


        SharedPreferences sp3 = getActivity().getSharedPreferences("language", getActivity().MODE_APPEND);
        language = sp3.getString("language", "");

        dialog = new LoadingDialog(getActivity(), Loading.getText(getActivity()));
        //显示Dialog
        dialog.show();

        SharedPreferences sp2 = getActivity().getSharedPreferences("title", getActivity().MODE_APPEND);
        title_main = sp2.getString("title", "");


        if (NetWorking.isNetworkAvailable(getActivity())) {
            new MyThread().start();
        } else {
            if (language.equals("zh_cn")) {
                Toast.makeText(getActivity(), "当前网络不可用", Toast.LENGTH_SHORT).show();

            } else {
                Toast.makeText(getActivity(), "The current network is not available", Toast.LENGTH_SHORT).show();
            }
        }

        //上拉加载和下拉刷新的监听
        ((TwoPullToRefresh) getActivity().findViewById(R.id.refresh_power_station))
                .setOnRefreshListener(new MyListener());
        SharedPreferences sp = getActivity().getSharedPreferences("Set", getActivity().MODE_APPEND);

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


        //
//        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        for (int i = 0; i < 3; i++) {
            ImageView bt = new ImageView(getActivity());
//            bt.setLayoutParams(new ViewGroup.LayoutParams(bitmap.getWidth(), bitmap.getHeight()));
            bt.setBackgroundResource(R.mipmap.bullet_white);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            params.rightMargin = 20;
            bt.setLayoutParams(params);
            pointGroup.addView(bt);
        }


        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (mPreSelectedBt != null) {
                    mPreSelectedBt.setBackgroundResource(R.mipmap.bullet_white);
                }
                ImageView currentBt = (ImageView) pointGroup.getChildAt(position);
                currentBt.setBackgroundResource(R.drawable.point_selector);
                mPreSelectedBt = currentBt;
                num = position;
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
//        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
//        viewPager.setAdapter(adapter);

        //电站的top按键
        iv_top.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gridView.setSelection(0);
                if (language.equals("zh_cn")) {
                    Toast.makeText(getActivity(), "返回顶部", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(getActivity(), "Return to top ", Toast.LENGTH_SHORT).show();
                }

            }
        });


        ////显示哪个页面
        broadcastManager3 = LocalBroadcastManager.getInstance(getActivity());
        intentFilter3 = new IntentFilter();
        intentFilter3.addAction("MainActivity.ViewPager");
        // intentFilter.addAction("com.example.administrator.myapplication.main.main.home.broadcast");
        mReceiver3 = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
//                relativeLayout.setVisibility(View.VISIBLE);
//                ll_fm_station.setVisibility(View.GONE);
//                linearLayout.setVisibility(View.GONE);

                tv_title.setVisibility(View.VISIBLE);
                relativeLayout.setVisibility(View.VISIBLE);
                iv_search.setVisibility(View.VISIBLE);
                spinner.setVisibility(View.GONE);
                linearLayout.setVisibility(View.GONE);
                imageView_back.setVisibility(View.GONE);
                ll_fm_station.setVisibility(View.GONE);
                ll_station.setVisibility(View.VISIBLE);


            }
        };
        broadcastManager3.registerReceiver(mReceiver3, intentFilter3);


        ////显示哪个页面
        broadcastManager3 = LocalBroadcastManager.getInstance(getActivity());
        intentFilter3 = new IntentFilter();
        intentFilter3.addAction("com.search2");
        // intentFilter.addAction("com.example.administrator.myapplication.main.main.home.broadcast");
        mReceiver3 = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                lvResults.setVisibility(View.GONE);
                ll_station.setVisibility(View.VISIBLE);
                ll_fm_station.setVisibility(View.GONE);
                tv_title.setVisibility(View.VISIBLE);
                spinner.setVisibility(View.GONE);
                nums = 0;
            }
        };
        broadcastManager3.registerReceiver(mReceiver3, intentFilter3);


//        rl_station_try.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//
//            @Override
//            public void onGlobalLayout() {
//
//                if (resetLayoutByUsableHeight(computeUsableHeight())) {
//                    Log.e("sdazxc", "iiiiiii");
//                } else {
//                    Log.e("sdazxc", "uuuuuu");
//                    mWm = (WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE);
//                    Log.e("num", num_tag+"");
//
//                    if (datas.size() > 0){
//                        if (hasSoftKeys(mWm)) {
//                            Log.e("ttttqqqq", "wwwwwww");
//                            gridViewAdapter = new GridViewAdapter(getActivity(), datas, R.layout.item_stationtwo);
//                        } else {
//                            Log.e("ttttqqqq", "qqqqqqq");
//                            gridViewAdapter = new GridViewAdapter(getActivity(), datas, R.layout.item_station);
//                        }
//                        gridView.setAdapter(gridViewAdapter);
//                    }
//
//                }
//            }
//        });


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
                ll_station.setVisibility(View.VISIBLE);
                tv_title.setVisibility(View.VISIBLE);
                lvResults.setVisibility(View.GONE);
                ll_fm_station.setVisibility(View.GONE);
                spinner.setVisibility(View.GONE);
                iv_search.setVisibility(View.VISIBLE);
                iv_point.setVisibility(VISIBLE);
                nums = 0;

                relativeLayout.setVisibility(View.VISIBLE);


//                tv_title.setVisibility(View.VISIBLE);
//                spinner.setVisibility(View.GONE);
//                relativeLayout.setVisibility(View.VISIBLE);
//                linearLayout.setVisibility(View.GONE);
//                iv_search.setVisibility(View.VISIBLE);
//                imageView_back.setVisibility(View.GONE);


                Intent intent2 = new Intent("Search.Equipment");
                LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent2);

            }
        });

        //设置监听
        searchView.setSearchViewListener(this);
        //设置adapter
        searchView.setTipsHintAdapter(hintAdapter);
        searchView.setAutoCompleteAdapter(autoCompleteAdapter);

//        lvResults.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
//                Toast.makeText(getActivity(), position + "", Toast.LENGTH_SHORT).show();
//            }
//        });
    }

    /**
     * 初始化数据
     */
//    private void initData() {
//        //从数据库获取数据
//        getDbData();
////        //初始化热搜版数据
////        getHintData();
//        //初始化自动补全数据
////        getAutoCompleteData(null);
//        //初始化搜索结果数据
//        getResultData(null);
//    }

    /**
     * 获取db 数据
     */
    private void getDbData(List<GetStationListBean.StationsBean> data) {
        int size = data.size();
        dbData = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            dbData.add(data.get(i));
        }
    }

    /**
     * 获取热搜版data 和adapter
     */
    private void getHintData() {
        hintData = new ArrayList<>(hintSize);
        for (int i = 1; i <= hintSize; i++) {
            hintData.add("热搜版" + i + "：Android自定义View");
        }
        hintAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, hintData);
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
            resultAdapter = new SearchAdapter(getActivity(), resultData, R.layout.item_bean_list);
        } else {
            resultAdapter.notifyDataSetChanged();
        }
    }

    /**
     * 当搜索框 文本改变时 触发的回调 ,更新自动补全数据
     *
     * @param text
     */
    @Override
    public void onRefreshAutoComplete(String text) {
        //更新数据
        getAutoCompleteData(text);
    }

    /**
     * 点击搜索键时edit text触发的回调
     *
     * @param text
     */
    @Override
    public void onSearch(String text) {
        //更新result数据

        if (text.equals("") || text.equals("null")) {
            lvResults.setVisibility(View.GONE);
        } else {
            if (NetWorking.isNetworkAvailable(getActivity())) {
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


    public class ViewPagerAdapter extends FragmentStatePagerAdapter {
        private List<Fragment> datas;

        //构造方法
        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
            datas = new ArrayList<>();
            datas.add(new PowerOneFragment());
            datas.add(new PowerTwoFragment());
            datas.add(new PowerThreeFragment());
        }

        @Override
        public Fragment getItem(int position) {
            return datas.get(position);
        }

        @Override
        public int getCount() {
            return datas == null ? 0 : datas.size();
        }
    }

    private class MyThread extends Thread {
        @Override
        public void run() {
            SharedPreferences sp = getActivity().getSharedPreferences("Set", getActivity().MODE_APPEND);
            String ID = sp.getString("SessionID", "");
            String level = sp.getString("Level", "");
            SharedPreferences sp1 = getActivity().getSharedPreferences("IP", getActivity().MODE_APPEND);
            String ip = sp1.getString("Ip", "");
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("Cmd", "GetStationList");
                jsonObject.put("SessionID", ID);
                jsonObject.put("Dev", "app");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            ;
            String sr = Post.sendPost(
                    "http://" + ip + "/SPMSWcfService/SPMSMainHandler.ashx", jsonObject);
            Log.d("coming", sr);
            if (level.equals("1")) {
                handler.sendMessage(handler.obtainMessage(1, sr));
            } else {
                handler.sendMessage(handler.obtainMessage(1, sr));
            }


        }

        Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                //第一次拉数据
                if (msg.what == 1) {
                    datas = new ArrayList<>();
                    String sr = (String) msg.obj;
                    Gson gson = new Gson();
                    bean = gson.fromJson(sr, GetStationListBean.class);
                    dialog.close();
                    try {
                        if (bean.getRet().equals("Success")) {
                            tv_title.setText(title_main);
                            tv_title1.setText(title_main);
                            tv_list_power.setText(bean.getPageTitle());


//                            datas = new ArrayList<>();
                            for (int i = 0; i < bean.getStations().size(); i++) {
                                datas.add(bean.getStations().get(i));
                            }

                            //spinner加数据
                            adapter1 = new ArrayAdapter<String>(getActivity(), R.layout.simple_spinner_item2, R.id.tv_spinner);
                            adapter1.setDropDownViewResource(R.layout.simple_spinner_dropdown_item2);
                            for (int i = 0; i < bean.getStations().size(); i++) {
                                adapter1.add(bean.getStations().get(i).getStationName());
                            }
                            //TODO
                            mWm = (WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE);
                            if (hasSoftKeys(mWm)) {
                                gridViewAdapter = new GridViewAdapter(getActivity(), datas, R.layout.item_stationtwo ,handler);
                            } else {
                                gridViewAdapter = new GridViewAdapter(getActivity(), datas, R.layout.item_station,handler);
                            }

//                            gridViewAdapter = new GridViewAdapter(getActivity(), data, R.layout.item_station);
                            gridView.setAdapter(gridViewAdapter);

//                            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                                @Override
//                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
////                                    nums = 1;
////                                    SharedPreferences share = getActivity().getSharedPreferences("Set", Context.MODE_PRIVATE);
////                                    SharedPreferences.Editor editor = share.edit();
////                                    editor.putString("StationID", bean.getStations().get(position).getStationID());
////                                    editor.apply();
////                                    relativeLayout.setVisibility(View.GONE);
////                                    linearLayout.setVisibility(View.VISIBLE);
////                                    iv_search.setVisibility(View.GONE);
////                                    ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
////                                    viewPager.setAdapter(adapter);
////                                    tv_title.setVisibility(View.GONE);
////                                    spinner.setVisibility(View.VISIBLE);
////                                    spinner.setSelection(position);
////                                    imageView_back.setVisibility(View.VISIBLE);
//
//
//                                }
//                            });

                            //详情页面返回键
                            imageView_back.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if (nums == 5) {
                                        nums = 2;
                                        ll_fm_station.setVisibility(View.VISIBLE);
                                        ll_station.setVisibility(View.GONE);
                                        iv_point.setVisibility(View.GONE);

                                        iv_search.setVisibility(View.GONE);
                                        spinner.setVisibility(View.GONE);
                                        linearLayout.setVisibility(View.GONE);
                                        imageView_back.setVisibility(View.GONE);
                                    } else {
                                        nums = 0;
                                        tv_title.setVisibility(View.VISIBLE);
                                        spinner.setVisibility(View.GONE);
                                        relativeLayout.setVisibility(View.VISIBLE);
                                        linearLayout.setVisibility(View.GONE);
                                        iv_search.setVisibility(View.VISIBLE);
                                        imageView_back.setVisibility(View.GONE);
                                    }


                                }
                            });

                            //点击搜索按键
                            iv_search.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent1 = new Intent("com.search");
                                    LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent1);
                                    ll_station.setVisibility(View.GONE);
                                    ll_fm_station.setVisibility(View.VISIBLE);
                                    nums = 2;
                                }
                            });

                            getDbData(bean.getStations());
//        //初始化热搜版数据
//        getHintData();
                            //初始化自动补全数据
//        getAutoCompleteData(null);
                            //初始化搜索结果数据

                            if (a == 0) {
                                getResultData(null);
                            }
                            a++;
                            initViews();

                            //搜索item的点击
                            lvResults.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                                    nums = 5;
                                    SharedPreferences share = getActivity().getSharedPreferences("Set", Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = share.edit();
                                    editor.putString("StationID", bean.getStations().get(getpos(resultData.get(position).getStationName(), datas)).getStationID());
                                    editor.apply();
                                    ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
                                    viewPager.setAdapter(adapter);
                                    Log.d("321323", bean.getStations().get(position).getStationID());
                                    ll_station.setVisibility(View.VISIBLE);
                                    linearLayout.setVisibility(View.VISIBLE);
                                    ll_fm_station.setVisibility(View.GONE);
                                    iv_search.setVisibility(View.GONE);
                                    tv_title.setVisibility(View.GONE);
                                    spinner.setVisibility(View.VISIBLE);
                                    spinner.setSelection(getpos(resultData.get(position).getStationName(), datas));
                                    imageView_back.setVisibility(View.VISIBLE);
                                    Intent intent2 = new Intent("Search.Equipment");
                                    LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent2);
                                }
                            });

                            spinner.setAdapter(adapter1);
                            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    SharedPreferences share = getActivity().getSharedPreferences("Set", Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = share.edit();
                                    editor.putString("StationID", bean.getStations().get(position).getStationID());
                                    editor.apply();
                                    relativeLayout.setVisibility(View.GONE);
                                    linearLayout.setVisibility(View.VISIBLE);
                                    iv_search.setVisibility(View.GONE);
                                    ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
                                    viewPager.setAdapter(adapter);
                                    viewPager.setCurrentItem(num);
                                    tv_title.setVisibility(View.GONE);
                                    spinner.setVisibility(View.VISIBLE);
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });
                        } else {
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
                    String sr = (String) msg.obj;
                    Gson gson = new Gson();
                    final GetStationListBean bean = gson.fromJson(sr, GetStationListBean.class);

                    dialog.close();
                    try {
                        if (bean.getRet().equals("Success")) {
                            tv_list_power.setText(bean.getPageTitle());
                            List<GetStationListBean.StationsBean> data;
                            data = new ArrayList<>();
                            for (int i = 0; i < bean.getStations().size(); i++) {
                                data.add(bean.getStations().get(i));
                            }
                            //spinner加数据
                            adapter1 = new ArrayAdapter<String>(getActivity(), R.layout.simple_spinner_item2, R.id.tv_spinner);
                            adapter1.setDropDownViewResource(R.layout.simple_spinner_dropdown_item2);
                            for (int i = 0; i < bean.getStations().size(); i++) {
                                adapter1.add(bean.getStations().get(i).getStationName());
                            }
                            gridViewAdapter = new GridViewAdapter(getActivity(), datas, R.layout.item_station ,handler);
                            gridView.setAdapter(gridViewAdapter);
                            SharedPreferences share = getActivity().getSharedPreferences("Set", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = share.edit();
                            editor.putString("StationID", bean.getStations().get(0).getStationID());
                            editor.apply();
                            relativeLayout.setVisibility(View.GONE);
                            linearLayout.setVisibility(View.VISIBLE);
                            iv_search.setVisibility(View.GONE);
                            ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
                            viewPager.setAdapter(adapter);
                            tv_title.setVisibility(View.GONE);
                            spinner.setVisibility(View.VISIBLE);
                            spinner.setSelection(0);
//                        //点击搜索按键
//                        iv_search.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                ll_station.setVisibility(View.GONE);
//                                ll_fm_station.setVisibility(View.VISIBLE);
//
//
//                            }
//                        });
                            getDbData(bean.getStations());
                            if (a == 0) {
                                getResultData(null);
                            }
                            a++;
                            initViews();

                            ll_station.setVisibility(View.VISIBLE);
                            linearLayout.setVisibility(View.VISIBLE);
                            ll_fm_station.setVisibility(View.GONE);
                            tv_title.setVisibility(View.GONE);


                            spinner.setAdapter(adapter1);
                            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    SharedPreferences share = getActivity().getSharedPreferences("Set", Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = share.edit();
                                    editor.putString("StationID", bean.getStations().get(position).getStationID());
                                    editor.apply();
                                    relativeLayout.setVisibility(View.GONE);
                                    linearLayout.setVisibility(View.VISIBLE);
                                    iv_search.setVisibility(View.GONE);
                                    ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
                                    viewPager.setAdapter(adapter);
                                    tv_title.setVisibility(View.GONE);
                                    spinner.setVisibility(View.VISIBLE);
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });
                        } else {
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
                if (msg.what == 6){


                    String a = (String) msg.obj;
                    nums = 1;

                    int position = 0;
                    for (int i = 0; i <bean.getStations().size() ; i++) {
                        if (bean.getStations().get(i).getStationID() == a){
                            position = i;
                        }
                    }

                    SharedPreferences share = getActivity().getSharedPreferences("Set", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = share.edit();
                    editor.putString("StationID", a);
                    editor.apply();
                    relativeLayout.setVisibility(View.GONE);
                    linearLayout.setVisibility(View.VISIBLE);
                    iv_search.setVisibility(View.GONE);
                    ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
                    viewPager.setAdapter(adapter);
                    tv_title.setVisibility(View.GONE);
                    spinner.setVisibility(View.VISIBLE);


                    spinner.setSelection(position);
                    imageView_back.setVisibility(View.VISIBLE);


                }



                super.handleMessage(msg);
            }
        };

    }


    @Override
    public void onResume() {
        super.onResume();
        getFocus();
//        onKeyDown(KeyEvent.KEYCODE_BACK, event);
//        onBackPressed();
    }

//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
//            if (nums == 1) {
//                tv_title.setVisibility(View.VISIBLE);
//                spinner.setVisibility(View.GONE);
//                relativeLayout.setVisibility(View.VISIBLE);
//                linearLayout.setVisibility(View.GONE);
//                iv_search.setVisibility(View.VISIBLE);
//                imageView_back.setVisibility(View.GONE);
//                nums = 0;
//            } else if (nums == 2) {
//                ll_station.setVisibility(View.VISIBLE);
//                ll_fm_station.setVisibility(View.GONE);
//                tv_title.setVisibility(View.VISIBLE);
//                spinner.setVisibility(View.GONE);
//                nums = 0;
//
//            } else {
//                exit();
//
//            }
//            return false;
//        }else {
//            return super.getActivity().onKeyDown(keyCode, event);
//        }
//
//    }

    //


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
                        tv_title.setVisibility(View.VISIBLE);
                        spinner.setVisibility(View.GONE);
                        relativeLayout.setVisibility(View.VISIBLE);
                        linearLayout.setVisibility(View.GONE);
                        iv_search.setVisibility(View.VISIBLE);
                        imageView_back.setVisibility(View.GONE);
                        lvResults.setVisibility(View.GONE);
                        nums = 0;
                    } else if (nums == 2) {
                        ll_station.setVisibility(View.VISIBLE);
                        ll_fm_station.setVisibility(View.GONE);
                        tv_title.setVisibility(View.VISIBLE);
                        spinner.setVisibility(View.GONE);
                        iv_point.setVisibility(VISIBLE);
                        relativeLayout.setVisibility(View.VISIBLE);
                        iv_search.setVisibility(View.VISIBLE);
                        lvResults.setVisibility(View.GONE);
                        imageView_back.setVisibility(View.GONE);
                        nums = 0;

                    } else if (nums == 5) {
                        ll_station.setVisibility(VISIBLE);
                        tv_title.setVisibility(View.VISIBLE);
                        spinner.setVisibility(View.GONE);
                        relativeLayout.setVisibility(View.VISIBLE);
                        linearLayout.setVisibility(View.GONE);
                        iv_search.setVisibility(View.VISIBLE);
                        imageView_back.setVisibility(View.GONE);
                        lvResults.setVisibility(View.GONE);
                        iv_point.setVisibility(VISIBLE);
                        nums = 0;
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


    public int getpos(String title, List<GetStationListBean.StationsBean> data) {
        int a = 0;
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).getStationName().equals(title)) {
                a = i;
                break;
            }
        }
        return a;
    }


    /**
     * 判断底部navigator是否已经显示
     *
     * @param windowManager
     * @return
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private boolean hasSoftKeys(WindowManager windowManager) {
        Display d = windowManager.getDefaultDisplay();
        DisplayMetrics realDisplayMetrics = new DisplayMetrics();
        d.getRealMetrics(realDisplayMetrics);
        int realHeight = realDisplayMetrics.heightPixels;
        int realWidth = realDisplayMetrics.widthPixels;
        DisplayMetrics displayMetrics = new DisplayMetrics();
        d.getMetrics(displayMetrics);
        int displayHeight = displayMetrics.heightPixels;
        int displayWidth = displayMetrics.widthPixels;
        return (realWidth - displayWidth) > 0 || (realHeight - displayHeight) > 0;
    }


    private int computeUsableHeight() {
        Rect r = new Rect();
        mViewObserved.getWindowVisibleDisplayFrame(r);
        return (r.bottom - r.top);
    }

    private boolean resetLayoutByUsableHeight(int usableHeightNow) {
        //比较布局变化前后的View的可用高度
        if (usableHeightNow != usableHeightPrevious) {
            //如果两次高度不一致
            usableHeightPrevious = usableHeightNow;
            return false;
        } else {
            return true;
        }


    }
    //发送广播的方法
    public void sendBroadcast() {
        Intent intent1 = new Intent("com.main");
        LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent1);

    }

}








