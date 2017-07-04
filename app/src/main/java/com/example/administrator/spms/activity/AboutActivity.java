package com.example.administrator.spms.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.spms.R;
import com.example.administrator.spms.base.LoadingDialog;
import com.example.administrator.spms.base.NetWorking;
import com.example.administrator.spms.base.Post;
import com.example.administrator.spms.fragment.AlertFragment;
import com.example.administrator.spms.fragment.OperateFragment;
import com.example.administrator.spms.model.AboutBean;
import com.example.administrator.spms.model.GetGroupOperationInfo;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * Created by Administrator on 2016/11/21.
 */

@ContentView(R.layout.activity_about)
public class AboutActivity extends AppCompatActivity {
    private String lan;
    private LoadingDialog dialog;

    @ViewInject(R.id.tv_ben)
    private TextView tv_ben;

    @ViewInject(R.id.tv_ban)
    private TextView textView;

    @ViewInject(R.id.tv_about_title)
    private TextView tv_title;

    @ViewInject(R.id.tv_about1)
    private TextView tv1;

    @ViewInject(R.id.tv_app)
    private TextView tv_app;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);

        SharedPreferences sp1 = getSharedPreferences("language", MODE_APPEND);
        lan = sp1.getString("language", "");

        dialog = new LoadingDialog(AboutActivity.this, "加载中...");
        //显示Dialog
        dialog.show();


        if (NetWorking.isNetworkAvailable(AboutActivity.this)) {

            new MyThread().start();
        } else {
            dialog.close();
            if (lan.equals("zh_cn")){
                Toast.makeText(AboutActivity.this, "当前网络不可用", Toast.LENGTH_SHORT).show();

            }else {
                Toast.makeText(AboutActivity.this, "The current network is not available", Toast.LENGTH_SHORT).show();
            }
        }

    }


    private class MyThread extends Thread {
        @Override
        public void run() {
            // TODO Auto-generated method stub
            SharedPreferences sp1 = getSharedPreferences("IP", MODE_APPEND);
            String ip = sp1.getString("Ip", "");

            JSONObject object = new JSONObject();
            try {
                object.put("Cmd", "GetSPMSServerVersion");
                object.put("Dev", "app");
                object.put("Language", lan);
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
                dialog.close();
                String sr = (String) msg.obj;
                Gson gson = new Gson();
                AboutBean bean = gson.fromJson(sr, AboutBean.class);
                try{
                    String str = bean.getVersion();
                    if (lan.equals("zh_cn")){
                        String a = str.substring(0, 9);
                        String b = str.substring(9);  // or str=str.Substring(i);
                        textView.setText(a);
                        tv_ben.setText(b);
                    }else {
                        String a = str.substring(0, 36);
                        String b = str.substring(36);  // or str=str.Substring(i);
                        textView.setText(a);
                        tv_ben.setText(b);
                        tv1.setText("Copyright  1983-2016 Advantech Co..,Ltd All Rights Reserved");
                        tv_app.setText("Advantech SPMS");
                    }
                    SharedPreferences sp = getSharedPreferences("Set",MODE_APPEND);
                    tv_title.setText(sp.getString("SAbout", ""));
                }catch (Exception e){

                }

            }
            super.handleMessage(msg);
        }
    };


    @Event(R.id.iv_back_about)
    private void backClick(View view) {
        finish();
    }

}
