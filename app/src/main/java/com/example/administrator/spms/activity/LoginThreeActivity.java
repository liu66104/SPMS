package com.example.administrator.spms.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.spms.R;
import com.example.administrator.spms.base.BaseActivity;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * Created by Administrator on 2016/10/28.
 */
@ContentView(R.layout.activity_login_three)
public class LoginThreeActivity extends BaseActivity {

    @ViewInject(R.id.et_login_three)
    private EditText et_username;


    @ViewInject(R.id.et_two_login_three)
    private EditText et_password;

    //绑定确认的按钮
    @ViewInject(R.id.btn_login3_yes)
    private Button btn_yes;
    //绑定取消的按钮
    @ViewInject(R.id.btn_login3_no)
    private Button btn_no;

    @ViewInject(R.id.tv_login3_title1)
    private TextView tv_title;

    @ViewInject(R.id.tv_login3_username)
    private TextView tv_username;

    @ViewInject(R.id.tv_login_title3)
    private TextView tv_password;

    private String language;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        sendBroadcast();
        SharedPreferences sp3 = LoginThreeActivity.this.getSharedPreferences("language", MODE_APPEND);
        language = sp3.getString("language", "");
        if (language.equals("")||language == null||language.equals("null")){
            String z = language();
            if (z.equals("a")){
                //获取系统字为中文时
                tv_title.setText(R.string.login3_title);
                btn_yes.setText(R.string.login3_button);
                btn_no.setText(R.string.login3_button1);

            }else if(z.equals("b")){
                //获取系统字为繁体中文时
                tv_title.setText(R.string.login3_TW_title);
                btn_yes.setText(R.string.login3_TW_button);
                btn_no.setText(R.string.login3_TW_button1);

            }else  if(z.equals("c")){
                //获取系统字为英文时
                tv_title.setText(R.string.login3_English_title);
                btn_yes.setText(R.string.login3_English_button);
                btn_no.setText(R.string.login3_English_button1);
                tv_username.setText("UserName:");
                tv_password.setText("Password:");

            }else  if(z.equals("d")){
                //获取系统字为英文时
                tv_title.setText(R.string.login3_English_title);
                btn_yes.setText(R.string.login3_English_button);
                btn_no.setText(R.string.login3_English_button1);
                tv_username.setText("UserName:");
                tv_password.setText("Password:");

            }else  if(z.equals("e")){
                //获取系统字为其他时
                tv_title.setText(R.string.login3_English_title);
                btn_yes.setText(R.string.login3_English_button);
                btn_no.setText(R.string.login3_English_button1);
                tv_username.setText("UserName:");
                tv_password.setText("Password:");
            }

        }else {
            if (language.equals("zh_cn")) {
                //获取系统字为中文时
                tv_title.setText(R.string.login3_title);
                btn_yes.setText(R.string.login3_button);
                btn_no.setText(R.string.login3_button1);
            } else {

                //获取系统字为英文时
                tv_title.setText(R.string.login3_English_title);
                btn_yes.setText(R.string.login3_English_button);
                btn_no.setText(R.string.login3_English_button1);
                tv_username.setText("UserName:");
                tv_password.setText("Password:");
            }
        }





        btn_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = et_username.getText().toString();
                String password = et_password.getText().toString();
                Log.d("dsadsad1", username);


             if (username.replace(" ", "").equals("")||password.replace(" ", "").equals("")|| username.equals("")|| password.equals("")||username ==null||password==null){
                 String z = language();
                 if (language.equals("")||language == null||language.equals("null")){
                     if (z.equals("a")) {
                         //获取系统字为中文时
                         Toast.makeText(LoginThreeActivity.this, "输入的用户名或密码不能为空", Toast.LENGTH_SHORT).show();

                     } else {

                         Toast.makeText(LoginThreeActivity.this, "Enter the user name or password can not be empty", Toast.LENGTH_SHORT).show();
                     }
                 }else {
                     if (language.equals("zh_cn")) {
                         //获取系统字为中文时
                         Toast.makeText(LoginThreeActivity.this, "输入的用户名或密码不能为空", Toast.LENGTH_SHORT).show();
                     } else {
                         //获取系统字为其他时
                         Toast.makeText(LoginThreeActivity.this, "Enter the user name or password can not be empty", Toast.LENGTH_SHORT).show();
                     }
                 }

             }else {
                 SharedPreferences share = LoginThreeActivity.this.getSharedPreferences("Set", Context.MODE_PRIVATE);
                 SharedPreferences.Editor editor = share.edit();
                 editor.putString("username", username);
                 editor.putString("password", password);
                 editor.apply();
                 Intent intent = new Intent(LoginThreeActivity.this,LoginActivity.class);
                 startActivity(intent);
                 finish();
              }
            }
        });

        btn_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences share = getSharedPreferences("Set", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = share.edit();
                editor.putString("automaticLogin", "0");
                editor.apply();
                Intent intent = new Intent(LoginThreeActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
    private String language() {
        boolean a = LoginThreeActivity.this.getResources().getConfiguration().locale.getCountry().equals("CN");
        boolean b = LoginThreeActivity.this.getResources().getConfiguration().locale.getCountry().equals("TW");
        boolean c = LoginThreeActivity.this.getResources().getConfiguration().locale.getCountry().equals("UK");
        boolean d = LoginThreeActivity.this.getResources().getConfiguration().locale.getCountry().equals("US");
        if (a == true){
            return "a";
        }else if(b== true){
            return "b";
        }else if(c==true){
            return "c";
        }else if(d == true){
            return "d";
        }else {
            return "e";
        }
    }


    //发送广播的方法
    public void sendBroadcast() {
        Intent intent1 = new Intent("com.main");
        LocalBroadcastManager.getInstance(LoginThreeActivity.this).sendBroadcast(intent1);

    }

}
