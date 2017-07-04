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

@ContentView(R.layout.activity_login_four)
public class LoginFourActivity extends BaseActivity {


    //绑定用户名输入
    @ViewInject(R.id.et_username_login4)
    private EditText et_username;

    @ViewInject(R.id.btn_login4_yes)
    private Button btn_yes;

    @ViewInject(R.id.btn_login4_no)
    private Button btn_no;

    @ViewInject(R.id.tv_login4_title)
    private TextView tv_title;

    @ViewInject(R.id.tv_login4_title1)
    private TextView tv_title1;

    private String language;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        SharedPreferences sp3 = LoginFourActivity.this.getSharedPreferences("language", MODE_APPEND);
        language = sp3.getString("language", "");
        if (language.equals("")||language == null||language.equals("null")){
            String z = language();
            if (z.equals("a")) {
                //获取系统字为中文时
                tv_title.setText(R.string.login4_title);
                tv_title1.setText(R.string.login_loginName);
                btn_yes.setText(R.string.login2_button);
                btn_no.setText(R.string.login2_button1);

            } else if (z.equals("b")) {
                //获取系统字为繁体中文时
                tv_title.setText(R.string.login4_TW_title);
                tv_title1.setText(R.string.login_TW_loginName);
                btn_yes.setText(R.string.login2_TW_button);
                btn_no.setText(R.string.login2_TW_button1);

            } else if (z.equals("c")) {
                //获取系统字为英文时
                tv_title.setText(R.string.login4_English_title);
                tv_title1.setText(R.string.login_English_loginName);
                btn_yes.setText(R.string.login2_English_button);
                btn_no.setText(R.string.login2_English_button1);

            } else if (z.equals("d")) {
                //获取系统字为英文时
                tv_title.setText(R.string.login4_English_title);
                tv_title1.setText(R.string.login_English_loginName);
                btn_yes.setText(R.string.login2_English_button);
                btn_no.setText(R.string.login2_English_button1);

            } else if (z.equals("e")) {
                //获取系统字为其他时
                tv_title.setText(R.string.login4_English_title);
                tv_title1.setText(R.string.login_English_loginName);
                btn_yes.setText(R.string.login2_English_button);
                btn_no.setText(R.string.login2_English_button1);

            }
        }else {
            if (language.equals("zh_cn")) {
                //获取系统字为中文时
                tv_title.setText(R.string.login4_title);
                tv_title1.setText(R.string.login_loginName);
                btn_yes.setText(R.string.login2_button);
                btn_no.setText(R.string.login2_button1);
            } else {
                //获取系统字为其他时
                tv_title.setText(R.string.login4_English_title);
                tv_title1.setText(R.string.login_English_loginName);
                btn_yes.setText(R.string.login2_English_button);
                btn_no.setText(R.string.login2_English_button1);

            }

        }



        btn_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = et_username.getText().toString();

                if (username.replace(" ", "").equals("") || username == null || username.equals("")) {

                    String z = language();
                    if (language.equals("")||language == null||language.equals("null")){
                        if (z.equals("a")) {
                            //获取系统字为中文时
                            Toast.makeText(LoginFourActivity.this, "输入的用户名不能为空", Toast.LENGTH_SHORT).show();

                        } else {

                            Toast.makeText(LoginFourActivity.this, "Enter user name cannot be empty", Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        if (language.equals("zh_cn")) {
                            //获取系统字为中文时
                            Toast.makeText(LoginFourActivity.this, "输入的用户名不能为空", Toast.LENGTH_SHORT).show();
                        } else {
                            //获取系统字为其他时
                            Toast.makeText(LoginFourActivity.this, "Enter user name cannot be empty", Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    SharedPreferences share = LoginFourActivity.this.getSharedPreferences("Set", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = share.edit();
                    editor.putString("saveUsername", username);
                    editor.apply();
                    Intent intent = new Intent(LoginFourActivity.this, LoginFiveActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

        btn_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendBroadcast();

                SharedPreferences share = LoginFourActivity.this.getSharedPreferences("Set", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = share.edit();
                editor.putString("saveUsername", "");
                editor.putString("savePassword", "");
                editor.apply();

                Intent intent = new Intent(LoginFourActivity.this, LoginActivity.class);
                startActivity(intent);



                finish();
            }
        });

    }

    private String language() {
        boolean a = LoginFourActivity.this.getResources().getConfiguration().locale.getCountry().equals("CN");
        boolean b = LoginFourActivity.this.getResources().getConfiguration().locale.getCountry().equals("TW");
        boolean c = LoginFourActivity.this.getResources().getConfiguration().locale.getCountry().equals("UK");
        boolean d = LoginFourActivity.this.getResources().getConfiguration().locale.getCountry().equals("US");
        if (a == true) {
            return "a";
        } else if (b == true) {
            return "b";
        } else if (c == true) {
            return "c";
        } else if (d == true) {
            return "d";
        } else {
            return "e";
        }
    }

    //发送广播的方法
    public void sendBroadcast() {
        Intent intent1 = new Intent("com.main");
        LocalBroadcastManager.getInstance(LoginFourActivity.this).sendBroadcast(intent1);

    }
}
