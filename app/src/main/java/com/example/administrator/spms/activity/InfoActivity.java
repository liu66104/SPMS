package com.example.administrator.spms.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.administrator.spms.R;
import com.example.administrator.spms.base.LoadingDialog;
import com.example.administrator.spms.base.Post;
import com.example.administrator.spms.fragment.OperateFragment;
import com.example.administrator.spms.model.GetGroupOperationInfo;
import com.example.administrator.spms.model.LoginBean;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * Created by Administrator on 2016/11/18.
 * 帐户信息界面
 */
@ContentView(R.layout.account_info)
public class InfoActivity extends AppCompatActivity {
    private LoadingDialog dialog;
    //用户名
    @ViewInject(R.id.tv_info_name_tag)
    private TextView tv_nameStr;
    @ViewInject(R.id.tv_info_name)
    private TextView tv_name;
    //用户级别
    @ViewInject(R.id.tv_lv_info)
    private TextView tv_lvStr;
    @ViewInject(R.id.tv_info_lv)
    private TextView tv_lv;
    //用户级别
    @ViewInject(R.id.tv_descStr_info)
    private TextView tv_descStr;
    @ViewInject(R.id.tv_desc_info)
    private TextView tv_desc;

    @ViewInject(R.id.btn_back_info)
    private Button btn_back;

    @ViewInject(R.id.tv_info_title)
    private TextView tv_title;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);

        dialog = new LoadingDialog(InfoActivity.this, "加载中...");
        //显示Dialog
        dialog.show();
        SharedPreferences sp = getSharedPreferences("person", MODE_PRIVATE);
        String str = sp.getString("person", "");
        SharedPreferences sp2 = getSharedPreferences("Set", MODE_APPEND);
        Gson gson = new Gson();
        LoginBean bean = gson.fromJson(str, LoginBean.class);
        tv_title.setText(sp2.getString("SUserMessage", ""));
        tv_nameStr.setText(bean.getUserBaseInfo().getNameStr());
        tv_name.setText(bean.getUserBaseInfo().getName());
        tv_lvStr.setText(bean.getUserBaseInfo().getLevelDescStr());
        tv_lv.setText(bean.getUserBaseInfo().getLevelDesc());
        tv_descStr.setText(bean.getUserBaseInfo().getUserDescStr());
        tv_desc.setText(bean.getUserBaseInfo().getUserDesc());
        btn_back.setText(sp2.getString("SExit", ""));
        dialog.close();
    }

    @Event(R.id.iv_back_info)
    private void backClick(View view) {
        finish();
    }

    //退出登录
    @Event(R.id.btn_back_info)
    private void btnClick(View view) {


        AlertDialog.Builder builder = new AlertDialog.Builder(InfoActivity.this);
        builder.setMessage("确认退出吗？");
        builder.setTitle("提示");
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                SharedPreferences share = InfoActivity.this.getSharedPreferences("Set", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = share.edit();
                editor.putString("automaticLogin", "0");
                editor.apply();
                sendBroadcast();
                Intent intent = new Intent(InfoActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();


    }


    //发送广播的方法
    public void sendBroadcast() {
        Intent intent1 = new Intent("com.main");
        LocalBroadcastManager.getInstance(InfoActivity.this).sendBroadcast(intent1);

    }


}
