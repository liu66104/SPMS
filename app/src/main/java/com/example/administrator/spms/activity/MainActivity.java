package com.example.administrator.spms.activity;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.Toast;
import com.example.administrator.spms.R;
import com.example.administrator.spms.adapter.MainAdapter;
import com.example.administrator.spms.base.BaseActivity;
import com.example.administrator.spms.base.Loading;
import com.example.administrator.spms.base.LoadingDialog;
import com.example.administrator.spms.base.Post;
import com.example.administrator.spms.fragment.AlertFragment;
import com.example.administrator.spms.fragment.EquipmentFragment;
import com.example.administrator.spms.fragment.OperateFragment;
import com.example.administrator.spms.fragment.PowerStationFragment;
import com.example.administrator.spms.model.TextBean;
import com.google.gson.Gson;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;
import java.util.ArrayList;
import java.util.List;


@ContentView(R.layout.activity_main)
public class MainActivity extends BaseActivity {
    private ArrayList<Fragment> fragmentList;
    private MainAdapter adapter;
    private String s;
    //文字
    private List<String> titles;
    @ViewInject(R.id.tl)
    private TabLayout tabLayout;

    @ViewInject(R.id.vp)
    private ViewPager viewPager;

    private LoadingDialog dialog;

    //定义广播管理器，广播接收器 用来接收
    private LocalBroadcastManager broadcastManager, broadcastManager2, broadcastManager3;
    private IntentFilter intentFilter, intentFilter2, intentFilter3;
    private BroadcastReceiver mReceiver, mReceiver2, mReceiver3;
    private int a = 0;
    private String language;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);

        SharedPreferences sp3 = getSharedPreferences("language", MODE_APPEND);
        language = sp3.getString("language", "");

        fragmentList = new ArrayList<>();
        fragmentList.add(new OperateFragment());
        fragmentList.add(new PowerStationFragment());
        fragmentList.add(new EquipmentFragment());
        fragmentList.add(new AlertFragment());

        dialog = new LoadingDialog(MainActivity.this, Loading.getText(MainActivity.this));
        //显示Dialog
        dialog.show();

        titles = new ArrayList<>();
        new MyThread().start();

        //如此简单的接收广播停止播放 注册广播接收器 此种方法不用再manifest中写任何东东
        broadcastManager = LocalBroadcastManager.getInstance(MainActivity.this);
        intentFilter = new IntentFilter();
        intentFilter.addAction("MainActivity.broadcast");
        // intentFilter.addAction("com.example.administrator.myapplication.main.main.home.broadcast");
        mReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                viewPager.setCurrentItem(3);
            }
        };
        broadcastManager.registerReceiver(mReceiver, intentFilter);
        //如此简单的接收广播停止播放 注册广播接收器 此种方法不用再manifest中写任何东东
        broadcastManager2 = LocalBroadcastManager.getInstance(MainActivity.this);
        intentFilter2 = new IntentFilter();
        intentFilter2.addAction("com.main");
        // intentFilter.addAction("com.example.administrator.myapplication.main.main.home.broadcast");
        mReceiver2 = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                Log.e("qweqweqwe", "wwwwwww");
                finish();

            }
        };
        broadcastManager2.registerReceiver(mReceiver2, intentFilter2);
        ////显示哪个页面
        broadcastManager3 = LocalBroadcastManager.getInstance(MainActivity.this);
        intentFilter3 = new IntentFilter();
        intentFilter3.addAction("com.search");
        // intentFilter.addAction("com.example.administrator.myapplication.main.main.home.broadcast");
        mReceiver3 = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                a = 1;
            }
        };
        broadcastManager3.registerReceiver(mReceiver3, intentFilter3);


        ////显示哪个页面
        broadcastManager3 = LocalBroadcastManager.getInstance(MainActivity.this);
        intentFilter3 = new IntentFilter();
        intentFilter3.addAction("com.search3");
        // intentFilter.addAction("com.example.administrator.myapplication.main.main.home.broadcast");
        mReceiver3 = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                a = 2;
            }
        };
        broadcastManager3.registerReceiver(mReceiver3, intentFilter3);

        ////显示哪个页面
        broadcastManager3 = LocalBroadcastManager.getInstance(MainActivity.this);
        intentFilter3 = new IntentFilter();
        intentFilter3.addAction("com.search6");
        // intentFilter.addAction("com.example.administrator.myapplication.main.main.home.broadcast");
        mReceiver3 = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                a = 3;
            }
        };
        broadcastManager3.registerReceiver(mReceiver3, intentFilter3);


        ////显示哪个页面
        broadcastManager3 = LocalBroadcastManager.getInstance(MainActivity.this);
        intentFilter3 = new IntentFilter();
        intentFilter3.addAction("com.search8");
        // intentFilter.addAction("com.example.administrator.myapplication.main.main.home.broadcast");
        mReceiver3 = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                a = 4;
            }
        };
        broadcastManager3.registerReceiver(mReceiver3, intentFilter3);

    }


    private class MyThread extends Thread {
        @Override
        public void run() {
            // TODO Auto-generated method stub
            SharedPreferences sp = getSharedPreferences("Set", MODE_APPEND);
            String ID = sp.getString("SessionID", "");
            SharedPreferences sp1 = getSharedPreferences("IP", MODE_APPEND);
            String ip = sp1.getString("Ip", "");

            JSONObject object = new JSONObject();
            try {
                object.put("Cmd", "GetFunctionStr");
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


    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            if (msg.what == 1) {
                s = (String) msg.obj;
                Gson gson = new Gson();
                TextBean textBean = gson.fromJson(s, TextBean.class);
                try {
                    if (textBean.getRet().equals("Success")) {
                        titles.add(textBean.getTextInfo().getButtonRun());
                        titles.add(textBean.getTextInfo().getButtonPowerStation());
                        titles.add(textBean.getTextInfo().getButtonEquipment());
                        titles.add(textBean.getTextInfo().getButtonAlarm());
                        SharedPreferences share = MainActivity.this.getSharedPreferences("Set", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = share.edit();
                        editor.putString("SUserMessage", textBean.getTextInfo().getUserMessage());
                        editor.putString("SSet", textBean.getTextInfo().getSet());
                        editor.putString("SAbout", textBean.getTextInfo().getAbout());
                        editor.putString("SExit", textBean.getTextInfo().getExit());
                        editor.apply();
                        adapter = new MainAdapter(getSupportFragmentManager(), fragmentList, titles, MainActivity.this);
                        viewPager.setAdapter(adapter);
                        viewPager.setOffscreenPageLimit(3);//设置缓存页数
//                tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
//                    @Override
//                    public void onTabSelected(TabLayout.Tab tab) {
//                        Log.e("tab", "dddddd");
//                        if (tab.getPosition() == 1 || tab.getPosition() == 2){
//                            Intent intent2 = new Intent("MainActivity.ViewPager");
//                            LocalBroadcastManager.getInstance(MainActivity.this).sendBroadcast(intent2);
//                        }
//
//                    }
//
//                    @Override
//                    public void onTabUnselected(TabLayout.Tab tab) {
//
//                    }
//
//                    @Override
//                    public void onTabReselected(TabLayout.Tab tab) {
//                        Log.e("tablayout", "wwwww");
//                        if (tab.getPosition() == 1 || tab.getPosition() == 2){
//                            Intent intent2 = new Intent("MainActivity.ViewPager");
//                            LocalBroadcastManager.getInstance(MainActivity.this).sendBroadcast(intent2);
//                        }
//                    }
//                });


                        tabLayout.setupWithViewPager(viewPager);
                        for (int i = 0; i < 4; i++) {
                            TabLayout.Tab tab = tabLayout.getTabAt(i);
                            if (tab != null) {
                                tab.setCustomView(adapter.getTabView(i));
                            }
                        }

                        dialog.close();

                    } else {
                        if (textBean.getErrCode().equals("1")) {
                            if (language.equals("zh_cn")) {
                                Toast.makeText(MainActivity.this, "您的登录已失效，请重新登录", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(MainActivity.this,LoginActivity.class);
                                startActivity(intent);
                                finish();

                              } else {
                                Toast.makeText(MainActivity.this, "Your login has expired, please log in again", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            if (language.equals("zh_cn")) {
                                Toast.makeText(MainActivity.this, "连接服务器失败", Toast.LENGTH_SHORT).show();

                            } else {
                                Toast.makeText(MainActivity.this, "Connection server failed ", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                } catch (Exception e) {

                }


            }


            super.handleMessage(msg);
        }
    };


    /**
     * 02.     * 返回监听
     * 03.
     */
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO 监听返回键，相当于点击home键
        PackageManager pm = getPackageManager();
        ResolveInfo homeInfo = pm.resolveActivity(
                new Intent(Intent.ACTION_MAIN)
                        .addCategory(Intent.CATEGORY_HOME), 0);
        if (keyCode == KeyEvent.KEYCODE_BACK) {
//            ActivityInfo ai = homeInfo.activityInfo;
//            Intent startIntent = new Intent(Intent.ACTION_MAIN);
//            startIntent.addCategory(Intent.CATEGORY_LAUNCHER);
//            startIntent.setComponent(new ComponentName(ai.packageName, ai.name));
//            startActivitySafely(startIntent);
            if (a == 1) {
                Intent intent1 = new Intent("com.search2");
                LocalBroadcastManager.getInstance(this).sendBroadcast(intent1);
                exit();
            } else if (a == 2) {
                Intent intent1 = new Intent("com.search4");
                LocalBroadcastManager.getInstance(this).sendBroadcast(intent1);
                exit();
            } else if (a == 3) {
                Intent intent1 = new Intent("com.search5");
                LocalBroadcastManager.getInstance(this).sendBroadcast(intent1);
                exit();
            } else if (a == 4) {
                Intent intent1 = new Intent("com.search9");
                LocalBroadcastManager.getInstance(this).sendBroadcast(intent1);
                exit();
            }

            return true;
        } else
            return super.onKeyDown(keyCode, event);
    }


    //退出时的时间
    private long mExitTime;

    public void exit() {
        if ((System.currentTimeMillis() - mExitTime) > 2000) {
//            Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            mExitTime = System.currentTimeMillis();
        } else {
            finish();
            System.exit(0);
        }
    }

}
