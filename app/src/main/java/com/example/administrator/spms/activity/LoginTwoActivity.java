package com.example.administrator.spms.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.administrator.spms.R;
import com.example.administrator.spms.base.BaseActivity;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * Created by Administrator on 2016/10/28.
 */
@ContentView(R.layout.activity_login_two)
public class LoginTwoActivity extends BaseActivity {

    //绑定是的按钮
    @ViewInject(R.id.btn_login2_yes)
    private Button btn_yes;
   //绑定否，跳过的按钮
    @ViewInject(R.id.btn_login2_no)
    private Button btn_no;

    @ViewInject(R.id.tv_login2_title1)
    private TextView tv_title1;
    private String language;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);

        SharedPreferences sp3 = LoginTwoActivity.this.getSharedPreferences("language", MODE_APPEND);
        language = sp3.getString("language", "");
        if (language.equals("")||language == null||language.equals("null")){
            String z = language();
            if (z.equals("a")){
                //获取系统字为中文时
                tv_title1.setText(R.string.login2_title);
                btn_yes.setText(R.string.login2_button);
                btn_no.setText(R.string.login2_button1);

            }else if(z.equals("b")){
                //获取系统字为繁体中文时
                tv_title1.setText(R.string.login2_TW_title);
                btn_yes.setText(R.string.login2_TW_button);
                btn_no.setText(R.string.login2_TW_button1);

            }else  if(z.equals("c")){
                //获取系统字为英文时
                tv_title1.setText(R.string.login2_English_title);
                btn_yes.setText(R.string.login2_English_button);
                btn_no.setText(R.string.login2_English_button1);

            }else  if(z.equals("d")){
                //获取系统字为英文时
                tv_title1.setText(R.string.login2_English_title);
                btn_yes.setText(R.string.login2_English_button);
                btn_no.setText(R.string.login2_English_button1);

            }else  if(z.equals("e")){
                //获取系统字为其他时
                tv_title1.setText(R.string.login2_English_title);
                btn_yes.setText(R.string.login2_English_button);
                btn_no.setText(R.string.login2_English_button1);

            }
        }else {
            if (language.equals("zh_cn")) {
                tv_title1.setText(R.string.login2_title);
                btn_yes.setText(R.string.login2_button);
                btn_no.setText(R.string.login2_button1);
            } else {
                tv_title1.setText(R.string.login2_English_title);
                btn_yes.setText(R.string.login2_English_button);
                btn_no.setText(R.string.login2_English_button1);

            }
        }





        btn_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences share = LoginTwoActivity.this.getSharedPreferences("Set", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = share.edit();
                editor.putString("automaticLogin", "1");
                editor.apply();
                Intent intent = new Intent(LoginTwoActivity.this,LoginThreeActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btn_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences share = LoginTwoActivity.this.getSharedPreferences("Set", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = share.edit();
                editor.putString("automaticLogin", "0");
                editor.apply();
                Intent intent = new Intent(LoginTwoActivity.this,LoginFourActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
    private String language() {
        boolean a = LoginTwoActivity.this.getResources().getConfiguration().locale.getCountry().equals("CN");
        boolean b = LoginTwoActivity.this.getResources().getConfiguration().locale.getCountry().equals("TW");
        boolean c = LoginTwoActivity.this.getResources().getConfiguration().locale.getCountry().equals("UK");
        boolean d = LoginTwoActivity.this.getResources().getConfiguration().locale.getCountry().equals("US");
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
}
