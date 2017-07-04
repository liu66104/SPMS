package com.example.administrator.spms.activity;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.example.administrator.spms.R;
import com.example.administrator.spms.base.Base64ToBitmap;
import com.example.administrator.spms.base.BaseActivity;
import com.example.administrator.spms.base.LoadingDialog;
import com.example.administrator.spms.base.MyBitmap;
import com.example.administrator.spms.base.NetWorking;
import com.example.administrator.spms.base.Post;
import com.example.administrator.spms.model.GetOperatorInfoBean;
import com.example.administrator.spms.model.LoginBean;
import com.google.gson.Gson;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Administrator on 2016/10/28.
 */
@ContentView(R.layout.activity_login)
public class LoginActivity extends BaseActivity {
    private String username;
    private String password;

    private String language;
    private Handler handler8;
    //欢迎进入
    @ViewInject(R.id.tv_login_title1)
    private TextView tv1;
    //研华智能光伏电站
    @ViewInject(R.id.tv_login_title2)
    private TextView tv2;
    //登录图片
    @ViewInject(R.id.iv_login)
    private ImageView iv;
    //用户名
    @ViewInject(R.id.tv_login_username)
    private TextView tv_username;
    //密码
    @ViewInject(R.id.tv_login_title3)
    private TextView tv_password;
    //语言
    @ViewInject(R.id.tv_login_language1)
    private TextView tv_language;
    //登录按钮
    @ViewInject(R.id.btn_login)
    private Button btn;
    //绑定设置按钮
    @ViewInject(R.id.iv_login_set)
    private ImageView iv_set;

    @ViewInject(R.id.sp_login)
    private Spinner sp;
    //用户名输入框
    @ViewInject(R.id.et_username)
    private EditText et_username;
    //密码输入框
    @ViewInject(R.id.et_password)
    private EditText et_password;


//    @ViewInject(R.id.tv_test)
//    private TextView tv_test;

    private LoadingDialog dialog, dialog2,dialog3;

    private ArrayAdapter<String> adapter;

    private String getLanguage;
    private String ip1;
    private String num3;
    private String language1;
    private String loginCmd;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);

        SharedPreferences sp2 = LoginActivity.this.getSharedPreferences("IP", MODE_PRIVATE);
        String ip = sp2.getString("Ip", "");
        SharedPreferences sp3 = LoginActivity.this.getSharedPreferences("Set", MODE_PRIVATE);
         ip1 = sp3.getString("automaticLogin", "");

        SharedPreferences sp4 = LoginActivity.this.getSharedPreferences("language", MODE_APPEND);
        language1 = sp4.getString("language", "");


        //选择语言的下拉框
        adapter = new ArrayAdapter<String>(LoginActivity.this, R.layout.simple_spinner_item1, R.id.tv_spinner);
        adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
        adapter.add("简体中文");
        adapter.add("English");
        sp.setAdapter(adapter);


        if (language1.equals("")||language1 == null||language1.equals("null")){
            String num = language();
            if (num.equals("a")) {
                getLanguage = "zh_cn";
                sp.setSelection(0);
            } else {
                getLanguage = "en";
                sp.setSelection(1);
            }
        }else {
            if (language1.equals("zh_cn")){
                getLanguage = "zh_cn";
                sp.setSelection(0);
            }else {
                getLanguage = "en";
                sp.setSelection(1);
            }
        }

        if (getLanguage.equals("zh_cn")){
            dialog2 = new LoadingDialog(LoginActivity.this,"加载中...");
            //显示Dialog
            dialog2.show();
        }else {
            dialog2 = new LoadingDialog(LoginActivity.this,"Loading...");
            //显示Dialog
            dialog2.show();
        }

        if (NetWorking.isNetworkAvailable(this)) {
            Log.d("wangluobukeyongle", "onCreate: NetWorking.isNetworkAvailable(this)");
            //是否设置了IP
            if (ip == null || ip.trim().equals("")) {
                Intent intent = new Intent(LoginActivity.this, LoginOneActivity.class);
                startActivity(intent);
                finish();
            } else {
                //是否设置了自动登录
                //设置自动登录
                if (ip1.equals("1")) {
                           //设置成功之后进入的时候
                          //拉取中文等页面数据
                    if (getLanguage.equals("zh_cn")){
                        SharedPreferences share = LoginActivity.this.getSharedPreferences("Set", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = share.edit();
                        editor.putString("automaticLogin", "2");
                        editor.apply();
                        String usernameGet = sp3.getString("username", "");
                        String passwordGet = sp3.getString("password", "");
                        et_username.setText(usernameGet+"");
                        et_password.setText(passwordGet+"");
                       // Log.d("xaz2cx", usernameGet+passwordGet);
                       // new MyThreadLogin().start();
                        sp.setSelection(0);

                    } else {
                        SharedPreferences share = LoginActivity.this.getSharedPreferences("Set", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = share.edit();
                        editor.putString("automaticLogin", "2");
                        editor.apply();
                        String usernameGet = sp3.getString("username", "");
                        String passwordGet = sp3.getString("password", "");
                        et_username.setText(usernameGet+"");
                        et_password.setText(passwordGet+"");
                        //拉取英文页面数据
//                        dialog2.close();
//                        dialog = new LoadingDialog(LoginActivity.this, "Loading...");
//                        //显示Dialog
//                        dialog.show();
                     //   new MyThread2().start();
                        sp.setSelection(1);
                    }
                } else if(ip1.equals("2")){

                    dialog2.close();


                    if (getLanguage.equals("zh_cn")){
                        dialog = new LoadingDialog(LoginActivity.this, "自动登录中...");
                        //显示Dialog
                        dialog.show();
                        new MyThread2().start();
                    }else {
                        dialog = new LoadingDialog(LoginActivity.this, "Loading ...");
                        //显示Dialog
                        dialog.show();
                        new MyThread3().start();
                    }


                }else {
                    //第一次设置自动登录
                    //将保存过的用户名和密码取出来
                    SharedPreferences sp1 = LoginActivity.this.getSharedPreferences("Set", MODE_PRIVATE);
                    String username = sp1.getString("saveUsername", "");
                    String password = sp1.getString("savePassword", "");
                    et_username.setText(username + "");
                    et_password.setText(password + "");
                   //  new MyThread().run();
                    if (language1.equals("")||language1 == null||language1.equals("null")){
                        String num = language();
                        if (num.equals("a")) {
                            getLanguage = "zh_cn";
                            sp.setSelection(0);
                        } else {
                            getLanguage = "en";
                            sp.setSelection(1);
                        }
                    }else {
                        if (language1.equals("zh_cn")){
                            getLanguage = "zh_cn";
                            sp.setSelection(0);
                        }else {
                            getLanguage = "en";
                            sp.setSelection(1);
                        }
                    }
                }
            }
            //



            sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    if (position == 0){
                        dialog2.close();
                        if (getLanguage.equals("zh_cn")){

                            if(ip1.equals("2")){

                            }else {
                                dialog3= new LoadingDialog(LoginActivity.this,"加载中...");
                                //显示Dialog
                                dialog3.show();
                            }

                        }else {
                            if(ip1.equals("2")){

                            }else {
                                dialog3 = new LoadingDialog(LoginActivity.this, "Loading...");
                                //显示Dialog
                                dialog3.show();
                            }
                        }
                        num3 = "a";
                        new MyThreadLoginChina().start();

                        //保存语言
                        SharedPreferences share_about = LoginActivity.this.getSharedPreferences("language", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor_about = share_about.edit();
                        editor_about.putString("language", "zh_cn");
                        editor_about.apply();


                    }else {
                        dialog2.close();
                        if (getLanguage.equals("zh_cn")){
                            if(ip1.equals("2")){

                            }else {
                                dialog3 = new LoadingDialog(LoginActivity.this, "加载中...");
                                //显示Dialog
                                dialog3.show();
                            }
                        }else {
                            if(ip1.equals("2")){

                            }else {
                                dialog3 = new LoadingDialog(LoginActivity.this,"Loading...");
                                //显示Dialog
                                dialog3.show();
                            }

                        }
                        num3 = "b";
                        new MyThreadLoginEnglish().start();

                        //保存语言
                        SharedPreferences share_about = LoginActivity.this.getSharedPreferences("language", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor_about = share_about.edit();
                        editor_about.putString("language", "en");
                        editor_about.apply();

                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });


            //设置按钮的监听
            iv_set.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(LoginActivity.this, LoginOneActivity.class);
                    startActivity(intent);
                    finish();
                }
            });

            //登录按钮的监听
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

//                    if (loginCmd.equals("Error")){
//                        Toast.makeText(LoginActivity.this, "当前网络不可用", Toast.LENGTH_SHORT).show();
//                    }else {
                        Log.d("wodeshengqingruzai",  language+"111");
                        new MyThread1().start();
//                    }



                }
            });
        } else {
            dialog2.close();
            if (getLanguage.equals("zh_cn")){
                Toast.makeText(this, "当前网络不可用", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(this, "The current network is not available", Toast.LENGTH_SHORT).show();
            }


        }


    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {


                 if (ip1.equals("2")){

                 }else {
                     dialog3.close();
                 }


                String sr = (String) msg.obj;

                Log.d("handleMessage123szxc", "handleMessage: "+sr);

                Gson gson = new Gson();
                GetOperatorInfoBean bean = gson.fromJson(sr, GetOperatorInfoBean.class);

                loginCmd = bean.getRet();

             //   tv_test.setText(bean.getCmd()+"/"+bean.getErrCode()+"/"+bean.getMsg()+"/"+bean.getRet()+"/"+bean.getOperatorInfo().getTitle()+"/"+bean.getOperatorInfo().getWelcome()+"/"+bean.getOperatorInfo().getLogoImage().substring(0,10));


                try {
                    if (bean.getRet().equals("Success")) {

                        if (bean.getOperatorInfo().getImage().equals("")||bean.getOperatorInfo().getImage() == null )
                        {

                        }else {
                            //  设置页面图片
                            iv.setImageBitmap(MyBitmap.zoomImage(Base64ToBitmap.stringtoBitmap(bean.getOperatorInfo().getImage()), 600, 400));
                        }

                        String str = (String) sp.getSelectedItem();

                        try{
                            if (str.equals("简体中文")) {
                                language = "zh_cn";
                            } else {
                                language = "en";
                            }
                        }catch (Exception e){

                        }
                        if (language.equals("zh_cn")){
                            tv1.setText(bean.getOperatorInfo().getWelcome());
                            tv2.setText(bean.getOperatorInfo().getTitle());
                            //保存title
                            SharedPreferences share_about = LoginActivity.this.getSharedPreferences("title", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor_about = share_about.edit();
                            editor_about.putString("title", bean.getOperatorInfo().getTitle());
                            editor_about.apply();

                            tv_username.setText(bean.getPageStr().getUserName());
                            btn.setText(bean.getPageStr().getLogin());
                            tv_password.setText("密    码");
                            tv_language.setText("语    言");
                        }else {
                            tv1.setText(bean.getOperatorInfo().getWelcome());
                            tv2.setText(bean.getOperatorInfo().getTitle());

                            //保存title
                            SharedPreferences share_about = LoginActivity.this.getSharedPreferences("title", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor_about = share_about.edit();
                            editor_about.putString("title", bean.getOperatorInfo().getTitle());
                            editor_about.apply();

                            tv_username.setText(bean.getPageStr().getUserName());
                            tv_password.setText(bean.getPageStr().getUserPwd());
                            tv_language.setText(bean.getPageStr().getLanguage());
                            btn.setText(bean.getPageStr().getLogin());
                        }


//                        if (num3.equals("a")) {
//
//                        } else {
//
//                        }
                    } else {
                        SharedPreferences share_about = LoginActivity.this.getSharedPreferences("title", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor_about = share_about.edit();
                        editor_about.putString("title", "");
                        editor_about.apply();
                        if (getLanguage.equals("zh_cn")){
                            Toast.makeText(LoginActivity.this, "连接服务器失败", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(LoginActivity.this, "Connection server failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                } catch (Exception e) {

                }
            }
            if (msg.what == 2) {

                String sr = (String) msg.obj;


                Gson gson = new Gson();



                LoginBean bean = gson.fromJson(sr, LoginBean.class);

             //   tv_test.setText(bean.getCmd()+"/"+bean.getErrCode()+"/"+bean.getMsg()+"/"+bean.getRet()+"/"+bean.getSessionID()+"/"+bean.getUser());



                if (language.equals("zh_cn")) {
                    Toast.makeText(LoginActivity.this, bean.getMsg(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(LoginActivity.this, "ERROR Incorrect username or password", Toast.LENGTH_SHORT).show();
                }



            }
            if (msg.what == 10){
                if (getLanguage.equals("zh_cn")){
                    Toast.makeText(LoginActivity.this, "当前网络不可用", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(LoginActivity.this, "The current network is not available", Toast.LENGTH_SHORT).show();
                }
            }
            if(msg.what == 6){
               dialog.close();
            }
            if(msg.what == 22){
                if (getLanguage.equals("zh_cn")){
                    Toast.makeText(LoginActivity.this, "当前网络不可用", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(LoginActivity.this, "The current network is not available", Toast.LENGTH_SHORT).show();
                }
                dialog.close();
            }

            super.handleMessage(msg);
        }
    };

    private class MyThreadLogin extends Thread {
        @Override
        public void run() {
            String num = language();
            if (num.equals("a")) {
                language = "zh_cn";
            } else {
                language = "en";
            }
            JSONObject object = new JSONObject();
            SharedPreferences sp1 = LoginActivity.this.getSharedPreferences("IP", MODE_PRIVATE);
            String ip = sp1.getString("Ip", "");
            try {
                object.put("Cmd", "GetOperatorInfo");
                object.put("Dev", "app");
                object.put("Language", language);
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            //SharedPreferences sp = LoginActivity.this.getSharedPreferences("IP", MODE_PRIVATE);
            // String IP = sp.getString("Ip","");

            String sr =
                    Post.sendPost(
                            "http://" + ip + "/SPMSWcfService/SPMSMainHandler.ashx", object);
            num3 = language();
            handler.sendMessage(handler.obtainMessage(1, sr));




        }
    }

    private class MyThread1 extends Thread {
        @Override
        public void run() {
            // TODO Auto-generated method stub
            username = et_username.getText().toString();
            password = et_password.getText().toString();
            String MD5 = getMD5(password);

            String str = (String) sp.getSelectedItem();

            try{
                if (str.equals("简体中文")) {
                    language = "zh_cn";
                } else {
                    language = "en";
                }
            }catch (Exception e){

            }

            //保存语言
            SharedPreferences share_about = LoginActivity.this.getSharedPreferences("language", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor_about = share_about.edit();
            editor_about.putString("language", language);
            editor_about.apply();


            Log.d("wodeshengqingruzai",  language+"111");
            JSONObject object = new JSONObject();
            try {
                object.put("Cmd", "Login");
                object.put("User", username);
                object.put("Pwd", MD5);
                object.put("Dev", "app");
                object.put("Language", language);
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            SharedPreferences sp = LoginActivity.this.getSharedPreferences("IP", MODE_PRIVATE);
            String IP = sp.getString("Ip", "");
            String sr = Post.sendPost(
                    "http://" + IP + "/SPMSWcfService/SPMSMainHandler.ashx", object);
            //保存字符串给帐户信息用
            SharedPreferences share_person = LoginActivity.this.getSharedPreferences("person", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor_person = share_person.edit();
            editor_person.putString("person", sr);
            editor_person.apply();

            Gson gson = new Gson();
            LoginBean bean = gson.fromJson(sr, LoginBean.class);

            try {
                if (!sr.equals("") || sr != null) {
                    if (bean.getRet().equals("Success")) {
                        //保存title
                        SharedPreferences share = LoginActivity.this.getSharedPreferences("Set", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = share.edit();
                        editor.putString("SessionID", bean.getUserBaseInfo().getSessionID());
                        editor.putString("Level", bean.getUserBaseInfo().getLevel());
                        editor.apply();
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();


                    } else {

                        handler.sendMessage(handler.obtainMessage(2, sr));
                    }
                } else {

                }
            } catch (Exception e) {
                handler.sendMessage(handler.obtainMessage(10, sr));
            }

        }
    }

    private class MyThread2 extends Thread {

        @Override
        public void run() {
            Log.d("xxwesda", "run: 23123");
            // TODO Auto-generated method stub
            SharedPreferences sp3 = LoginActivity.this.getSharedPreferences("Set", MODE_PRIVATE);
            String username1 = sp3.getString("username", "");
            String password1 = sp3.getString("password", "");
            String MD5 = getMD5(password1);

            JSONObject object = new JSONObject();
            try {
                object.put("Cmd", "Login");
                object.put("User", username1);
                object.put("Pwd", MD5);
                object.put("Dev", "app");
                object.put("Language", "zh_cn");
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            String num = language();
            if (num.equals("a")) {
                language = "zh_cn";
            } else {
                language = "en";
            }
            JSONObject object1 = new JSONObject();
            SharedPreferences sp1 = LoginActivity.this.getSharedPreferences("IP", MODE_PRIVATE);
            String ip = sp1.getString("Ip", "");
            try {
                object1.put("Cmd", "GetOperatorInfo");
                object1.put("Dev", "app");
                object1.put("Language", language);
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            //SharedPreferences sp = LoginActivity.this.getSharedPreferences("IP", MODE_PRIVATE);
            // String IP = sp.getString("Ip","");

            String sr1 =
                    Post.sendPost(
                            "http://" + ip + "/SPMSWcfService/SPMSMainHandler.ashx", object1);
            Log.d("xxwesda", sr1);





            Gson gson1 = new Gson();
            GetOperatorInfoBean bean1 = gson1.fromJson(sr1, GetOperatorInfoBean.class);
                if (bean1.getRet().equals("Error")){
                    //保存title
                    SharedPreferences share_about = LoginActivity.this.getSharedPreferences("title", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor_about = share_about.edit();
                    editor_about.putString("title", "");
                    editor_about.apply();
                }else {
                    //保存title
                    SharedPreferences share_about = LoginActivity.this.getSharedPreferences("title", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor_about = share_about.edit();
                    editor_about.putString("title", bean1.getOperatorInfo().getTitle());
                    editor_about.apply();
                }



                SharedPreferences sp = LoginActivity.this.getSharedPreferences("IP", MODE_PRIVATE);
                String IP = sp.getString("Ip", "");
                String sr = Post.sendPost(
                        "http://" + IP + "/SPMSWcfService/SPMSMainHandler.ashx", object);



                //保存字符串给帐户信息用
                SharedPreferences share_person = LoginActivity.this.getSharedPreferences("person", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor_person = share_person.edit();
                editor_person.putString("person", sr);
                editor_person.apply();


                Gson gson = new Gson();
                LoginBean bean = gson.fromJson(sr, LoginBean.class);

                try {
                    if (bean.getRet().equals("Success")) {

                        SharedPreferences share = LoginActivity.this.getSharedPreferences("Set", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = share.edit();
                        editor.putString("SessionID", bean.getUserBaseInfo().getSessionID());
                        editor.putString("Level", bean.getUserBaseInfo().getLevel());
                        editor.apply();
                        handler.sendMessage(handler.obtainMessage(6, sr));

                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();


                    } else {
                        dialog.close();
                        handler.sendMessage(handler.obtainMessage(2, sr));
                    }
                } catch (Exception e) {

                }


        }
    }



    private class MyThread3 extends Thread {

        @Override
        public void run() {
            Log.d("xxwesda", "run: 23123");
            // TODO Auto-generated method stub
            SharedPreferences sp3 = LoginActivity.this.getSharedPreferences("Set", MODE_PRIVATE);
            String username1 = sp3.getString("username", "");
            String password1 = sp3.getString("password", "");
            String MD5 = getMD5(password1);

            JSONObject object = new JSONObject();
            try {
                object.put("Cmd", "Login");
                object.put("User", username1);
                object.put("Pwd", MD5);
                object.put("Dev", "app");
                object.put("Language", "en");
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            String num = language();
            if (num.equals("a")) {
                language = "zh_cn";
            } else {
                language = "en";
            }
            JSONObject object1 = new JSONObject();
            SharedPreferences sp1 = LoginActivity.this.getSharedPreferences("IP", MODE_PRIVATE);
            String ip = sp1.getString("Ip", "");
            try {
                object1.put("Cmd", "GetOperatorInfo");
                object1.put("Dev", "app");
                object1.put("Language", language);
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            //SharedPreferences sp = LoginActivity.this.getSharedPreferences("IP", MODE_PRIVATE);
            // String IP = sp.getString("Ip","");

            String sr1 =
                    Post.sendPost(
                            "http://" + ip + "/SPMSWcfService/SPMSMainHandler.ashx", object1);
            Log.d("xxwesda", sr1);



            Gson gson1 = new Gson();
            GetOperatorInfoBean bean1 = gson1.fromJson(sr1, GetOperatorInfoBean.class);
            //保存title
            SharedPreferences share_about = LoginActivity.this.getSharedPreferences("title", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor_about = share_about.edit();
            editor_about.putString("title", bean1.getOperatorInfo().getTitle());
            editor_about.apply();

            SharedPreferences sp = LoginActivity.this.getSharedPreferences("IP", MODE_PRIVATE);
            String IP = sp.getString("Ip", "");
            String sr = Post.sendPost(
                    "http://" + IP + "/SPMSWcfService/SPMSMainHandler.ashx", object);

            //保存字符串给帐户信息用
            SharedPreferences share_person = LoginActivity.this.getSharedPreferences("person", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor_person = share_person.edit();
            editor_person.putString("person", sr);
            editor_person.apply();


            Gson gson = new Gson();
            LoginBean bean = gson.fromJson(sr, LoginBean.class);

            try {
                if (bean.getRet().equals("Success")) {

                    SharedPreferences share = LoginActivity.this.getSharedPreferences("Set", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = share.edit();
                    editor.putString("SessionID", bean.getUserBaseInfo().getSessionID());
                    editor.putString("Level", bean.getUserBaseInfo().getLevel());
                    editor.apply();
                    handler.sendMessage(handler.obtainMessage(6, sr));

                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();


                } else {
                    dialog.close();
                    handler.sendMessage(handler.obtainMessage(2, sr));
                }
            } catch (Exception e) {

            }

        }
    }


    public String getMD5(String info) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(info.getBytes("UTF-8"));
            byte[] encryption = md5.digest();

            StringBuffer strBuf = new StringBuffer();
            for (int i = 0; i < encryption.length; i++) {
                if (Integer.toHexString(0xff & encryption[i]).length() == 1) {
                    strBuf.append("0").append(Integer.toHexString(0xff & encryption[i]));
                } else {
                    strBuf.append(Integer.toHexString(0xff & encryption[i]));
                }
            }

            return strBuf.toString();
        } catch (NoSuchAlgorithmException e) {
            return "";
        } catch (UnsupportedEncodingException e) {
            return "";
        }
    }


    private class MyThreadLoginEnglish extends Thread {
        @Override
        public void run() {
            JSONObject object = new JSONObject();
            SharedPreferences sp1 = LoginActivity.this.getSharedPreferences("IP", MODE_PRIVATE);
            String ip = sp1.getString("Ip", "");
            try {
                object.put("Cmd", "GetOperatorInfo");
                object.put("Dev", "app");
                object.put("Language", "en");
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            //SharedPreferences sp = LoginActivity.this.getSharedPreferences("IP", MODE_PRIVATE);
            // String IP = sp.getString("Ip","");

            String sr =
                    Post.sendPost(
                            "http://" + ip + "/SPMSWcfService/SPMSMainHandler.ashx", object);

            handler.sendMessage(handler.obtainMessage(1, sr));
            Log.d("asxzz123123", sr);


        }
    }

    private class MyThreadLoginChina extends Thread {
        @Override
        public void run() {
            JSONObject object = new JSONObject();
            SharedPreferences sp1 = LoginActivity.this.getSharedPreferences("IP", MODE_PRIVATE);
            String ip = sp1.getString("Ip", "");
            try {
                object.put("Cmd", "GetOperatorInfo");
                object.put("Dev", "app");
                object.put("Language","zh_cn");
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            //SharedPreferences sp = LoginActivity.this.getSharedPreferences("IP", MODE_PRIVATE);
            // String IP = sp.getString("Ip","");

            String sr =
                    Post.sendPost(
                            "http://" + ip + "/SPMSWcfService/SPMSMainHandler.ashx", object);

            handler.sendMessage(handler.obtainMessage(1, sr));

        }
    }

    private String language() {
        boolean a = LoginActivity.this.getResources().getConfiguration().locale.getCountry().equals("CN");
        boolean b = LoginActivity.this.getResources().getConfiguration().locale.getCountry().equals("TW");
        boolean c = LoginActivity.this.getResources().getConfiguration().locale.getCountry().equals("UK");
        boolean d = LoginActivity.this.getResources().getConfiguration().locale.getCountry().equals("US");
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


}
