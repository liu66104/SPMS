package com.example.administrator.spms.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
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
@ContentView(R.layout.activity_login_five)
public class LoginFiveActivity extends BaseActivity {

    @ViewInject(R.id.tv_login_title3)
    private TextView tv_title2;


    @ViewInject(R.id.tv_login5_title1)
    private TextView tv_title1;
    @ViewInject(R.id.textView2)
    private TextView tv_title;
    //绑定是的按钮
    @ViewInject(R.id.btn_login5_yes)
    private Button btn_yes;
    //绑定否，跳过的按钮
    @ViewInject(R.id.btn_login5_no)
    private Button btn_no;
    //绑定重新输入用户名
    @ViewInject(R.id.btn_login5_rewrite)
    private Button btn_rewrite;

    //绑定显示密码的按钮
    @ViewInject(R.id.radioButton1)
    private CheckBox ck;

    //绑定密码输入框
    @ViewInject(R.id.et_login5_password)
    private EditText et_password;

    private String language;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);

        SharedPreferences sp3 = LoginFiveActivity.this.getSharedPreferences("language", MODE_APPEND);
        language = sp3.getString("language", "");
        if (language.equals("")||language == null||language.equals("null")){
            String z = language();
            if (z.equals("a")) {
                //获取系统字为中文时
                tv_title.setText(R.string.login5_title);
                btn_yes.setText(R.string.login2_button);
                btn_no.setText(R.string.login2_button1);
                btn_rewrite.setText(R.string.login5_button);
                tv_title1.setText(R.string.login5_title1);
                tv_title2.setText(R.string.login_password1);


            } else if (z.equals("b")) {
                //获取系统字为繁体中文时
                tv_title.setText(R.string.login5_TW_title);
                btn_yes.setText(R.string.login2_TW_button);
                btn_no.setText(R.string.login2_TW_button1);
                btn_rewrite.setText(R.string.login5_TW_button);
                tv_title1.setText(R.string.login5_TW_title1);
                tv_title2.setText(R.string.login_TW_password1);


            } else if (z.equals("c")) {
                //获取系统字为英文时
                tv_title.setText(R.string.login5_English_title);
                btn_yes.setText(R.string.login2_English_button);
                btn_no.setText(R.string.login2_English_button1);
                btn_rewrite.setText(R.string.login5_English_button);
                tv_title1.setText(R.string.login5_English_title1);
                tv_title2.setText(R.string.login_English_password1);


            } else if (z.equals("d")) {
                //获取系统字为英文时
                tv_title.setText(R.string.login5_English_title);
                btn_yes.setText(R.string.login2_English_button);
                btn_no.setText(R.string.login2_English_button1);
                btn_rewrite.setText(R.string.login5_English_button);
                tv_title1.setText(R.string.login5_English_title1);
                tv_title2.setText(R.string.login_English_password1);

            } else if (z.equals("e")) {
                //获取系统字为其他时
                tv_title.setText(R.string.login5_English_title);
                btn_yes.setText(R.string.login2_English_button);
                btn_no.setText(R.string.login2_English_button1);
                btn_rewrite.setText(R.string.login5_English_button);
                tv_title1.setText(R.string.login5_English_title1);
                tv_title2.setText(R.string.login_English_password1);

            }
        }else {

            if (language.equals("zh_cn")) {
                //获取系统字为中文时
                tv_title.setText(R.string.login5_title);
                btn_yes.setText(R.string.login2_button);
                btn_no.setText(R.string.login2_button1);
                btn_rewrite.setText(R.string.login5_button);
                tv_title1.setText(R.string.login5_title1);
                tv_title2.setText(R.string.login_password1);
            } else {
                //获取系统字为其他时
                tv_title.setText(R.string.login5_English_title);
                btn_yes.setText(R.string.login2_English_button);
                btn_no.setText(R.string.login2_English_button1);
                btn_rewrite.setText(R.string.login5_English_button);
                tv_title1.setText(R.string.login5_English_title1);
                tv_title2.setText(R.string.login_English_password1);
            }
        }




        btn_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String password = et_password.getText().toString();
                if (password.replace(" ", "").equals("")||password ==null||password.equals("")){
                    if (language.equals("")||language == null||language.equals("null")){
                        String z = language();
                        if (z.equals("a")) {
                            //获取系统字为中文时
                            Toast.makeText(LoginFiveActivity.this, "输入的密码不能为空", Toast.LENGTH_SHORT).show();

                        } else {

                            Toast.makeText(LoginFiveActivity.this, "Enter the password can not be empty", Toast.LENGTH_SHORT).show();
                        }

                    }else {
                        if (language.equals("zh_cn")) {
                            Toast.makeText(LoginFiveActivity.this, "输入的密码不能为空", Toast.LENGTH_SHORT).show();
                        } else {

                            Toast.makeText(LoginFiveActivity.this, "Enter the password can not be empty", Toast.LENGTH_SHORT).show();

                        }


                    }

                }else {

                    SharedPreferences share = LoginFiveActivity.this.getSharedPreferences("Set", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = share.edit();
                    editor.putString("savePassword", password);
                    editor.apply();
                    sendBroadcast();
                    Intent intent = new Intent(LoginFiveActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }

            }
        });
        btn_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendBroadcast();
                Intent intent = new Intent(LoginFiveActivity.this, LoginActivity.class);

                SharedPreferences share = LoginFiveActivity.this.getSharedPreferences("Set", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = share.edit();
                editor.putString("savePassword", "");
                editor.apply();

                startActivity(intent);
                finish();
            }
        });
        btn_rewrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginFiveActivity.this, LoginFourActivity.class);
                startActivity(intent);
                finish();
            }
        });

        ck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    et_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    et_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }

        });

    }

    private String language() {
        boolean a = LoginFiveActivity.this.getResources().getConfiguration().locale.getCountry().equals("CN");
        boolean b = LoginFiveActivity.this.getResources().getConfiguration().locale.getCountry().equals("TW");
        boolean c = LoginFiveActivity.this.getResources().getConfiguration().locale.getCountry().equals("UK");
        boolean d = LoginFiveActivity.this.getResources().getConfiguration().locale.getCountry().equals("US");
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
        LocalBroadcastManager.getInstance(LoginFiveActivity.this).sendBroadcast(intent1);

    }

}
