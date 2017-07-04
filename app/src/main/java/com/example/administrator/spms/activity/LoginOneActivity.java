package com.example.administrator.spms.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.administrator.spms.R;
import com.example.administrator.spms.base.BaseActivity;
import com.example.administrator.spms.base.LoadingDialog;
import com.example.administrator.spms.base.NetWorking;
import com.example.administrator.spms.fragment.OperateFragment;
import com.example.administrator.spms.model.SPMSIPCheckBean;
import com.google.gson.Gson;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;


/**
 * Created by Administrator on 2016/10/28.
 */
@ContentView(R.layout.activity_login_one)
public class LoginOneActivity extends BaseActivity {
    private String IP;
    private AlertDialog.Builder dialog;
    @ViewInject(R.id.tv_login1_title1)
    private TextView tv_title1;

    @ViewInject(R.id.tv_login1_title2)
    private TextView tv_title2;

    @ViewInject(R.id.tv_login1_title3)
    private TextView tv_title3;

    //绑定下一步的按钮
    @ViewInject(R.id.btn_login1_next)
    private Button btn_next;

    @ViewInject(R.id.et_login_one)
    private EditText et;
    private LoadingDialog dialog2;
    private String language;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        String z = language();
        SharedPreferences sp = LoginOneActivity.this.getSharedPreferences("IP", MODE_PRIVATE);
        String IP1 = sp.getString("Ip", "");

        SharedPreferences sp3 = LoginOneActivity.this.getSharedPreferences("language", MODE_APPEND);
        language = sp3.getString("language", "");
        dialog = new AlertDialog.Builder(LoginOneActivity.this);

        if (IP1 == "" ||IP1 == null || IP1.trim().equals("")){
            et.setText("");
        }else {
            et.setText(IP1);
        }


        if (language.equals("")||language == null||language.equals("null")){
            if (z.equals("a")) {
                //获取系统字为中文时
                tv_title1.setText(R.string.login1_title);
                tv_title2.setText(R.string.login1_title1);
                tv_title3.setText(R.string.login1_title2);
                btn_next.setText(R.string.login1_button);
            } else if (z.equals("b")) {
                //获取系统字为繁体中文时
                tv_title1.setText(R.string.login1_TW_title);
                tv_title2.setText(R.string.login1_TW_title1);
                tv_title3.setText(R.string.login1_TW_title2);
                btn_next.setText(R.string.login1_TW_button);
            } else if (z.equals("c")) {
                //获取系统字为英文时
                tv_title1.setText(R.string.login1_English_title);
                tv_title2.setText(R.string.login1_English_title1);
                tv_title3.setText(R.string.login1_English_title2);
                btn_next.setText(R.string.login1_English_button);
            } else if (z.equals("d")) {
                //获取系统字为英文时
                tv_title1.setText(R.string.login1_English_title);
                tv_title2.setText(R.string.login1_English_title1);
                tv_title3.setText(R.string.login1_English_title2);
                btn_next.setText(R.string.login1_English_button);
            } else if (z.equals("e")) {
                //获取系统字为其他时
                tv_title1.setText(R.string.login1_English_title);
                tv_title2.setText(R.string.login1_English_title1);
                tv_title3.setText(R.string.login1_English_title2);
                btn_next.setText(R.string.login1_English_button);
            }
        }else {
            if (language.equals("zh_cn")) {
                //获取系统字为中文时
                tv_title1.setText(R.string.login1_title);
                tv_title2.setText(R.string.login1_title1);
                tv_title3.setText(R.string.login1_title2);
                btn_next.setText(R.string.login1_button);
            } else {
                //获取系统字为其他时
                tv_title1.setText(R.string.login1_English_title);
                tv_title2.setText(R.string.login1_English_title1);
                tv_title3.setText(R.string.login1_English_title2);
                btn_next.setText(R.string.login1_English_button);
            }
        }
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String z = language();
                //输入IP点击按钮后判断当前网络是否可用
                if (NetWorking.isNetworkAvailable(LoginOneActivity.this)) {
                    if (language.equals("")||language == null||language.equals("null")){
                        if (z.equals("a")) {
                            //获取系统字为中文时
                            dialog2 = new LoadingDialog(LoginOneActivity.this, "正在检测IP地址...");
                        } else {
                            dialog2 = new LoadingDialog(LoginOneActivity.this, "Detecting IP address...");
                        }
                    }else {
                        if (language.equals("zh_cn")) {
                            //获取系统字为中文时
                            dialog2 = new LoadingDialog(LoginOneActivity.this, "正在检测IP地址...");
                        } else {
                            //获取系统字为其他时
                            dialog2 = new LoadingDialog(LoginOneActivity.this, "Detecting IP address...");
                        }
                    }
                    //显示Dialog
                    IP = et.getText().toString();
                    if (IP == null || IP == "" || IP == " " || IP.equals("")) {
                        if (language.equals("")||language == null||language.equals("null")){
                            if (z.equals("a")) {
                                //获取系统字为中文时
                                Toast.makeText(LoginOneActivity.this, "输入的IP地址不能为空", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(LoginOneActivity.this, "Enter the IP address can not be empty", Toast.LENGTH_SHORT).show();
                            }
                        }else {
                            if (language.equals("zh_cn")) {
                                //获取系统字为中文时
                                Toast.makeText(LoginOneActivity.this, "输入的IP地址不能为空", Toast.LENGTH_SHORT).show();
                            } else {
                                //获取系统字为其他时
                                Toast.makeText(LoginOneActivity.this, "Enter the IP address can not be empty", Toast.LENGTH_SHORT).show();
                            }
                        }
                        Toast.makeText(LoginOneActivity.this, "输入的IP地址不能为空", Toast.LENGTH_SHORT).show();
                    } else {
                        dialog2.show();
                        new MyThread().start();
                    }
                } else {
                    if (language.equals("zh_cn")){
                        Toast.makeText(LoginOneActivity.this, "当前网络不可用", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(LoginOneActivity.this, "The current network is not available", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    public class MyThread extends Thread {
        @Override
        public void run() {
            // TODO Auto-generated method stub
            JSONObject object = new JSONObject();
            try {
                object.put("Cmd", "SPMSIPCheck");
                object.put("Dev", "app");
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            IP = et.getText().toString();
            String sr = sendPost(
                    "http://" + IP + "/SPMSWcfService/SPMSMainHandler.ashx",
                    object);
//            Log.d("ewqew", "http://" + IP + "/SPMSWcfService/SPMSMainHandler.ashx");
            Log.d("ewqew1", sr);



//            dialog2.close();
            if (sr == "" || sr == null || sr == " ") {
                String z = language();

                if (language.equals("")||language == null||language.equals("null")){
                    if (z.equals("a")) {
                        //获取系统字为中文时
                        dialog.setTitle("提示");
                        dialog.setMessage("输入的IP地址有误");
                        dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        Looper.prepare();
                        dialog.show();
                        Looper.myLooper().loop();

                    } else {

                        dialog.setTitle("Prompt");
                        dialog.setMessage("The IP address entered is incorrect");
                        dialog.setPositiveButton("Determine", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        Looper.prepare();
                        dialog.show();
                        Looper.myLooper().loop();
                    }
                }else {
                    if (language.equals("zh_cn")) {
                        //获取系统字为中文时
                        dialog.setTitle("提示");
                        dialog.setMessage("输入的IP地址有误");
                        dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        Looper.prepare();
                        dialog.show();
                        Looper.myLooper().loop();
                    } else {
                        //获取系统字为其他时

                        dialog.setTitle("Prompt");
                        dialog.setMessage("The IP address entered is incorrect");
                        dialog.setPositiveButton("Determine", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        Looper.prepare();
                        dialog.show();
                        Looper.myLooper().loop();
                    }
                }



            } else {
                Gson gson = new Gson();
                SPMSIPCheckBean bean = gson.fromJson(sr, SPMSIPCheckBean.class);
                if (bean.getRet().equals("Success")) {
                    SharedPreferences share = LoginOneActivity.this.getSharedPreferences("IP", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = share.edit();
                    editor.putString("Ip", IP);
                    editor.apply();
                    Intent intent = new Intent(LoginOneActivity.this, LoginTwoActivity.class);
                    startActivity(intent);
                    finish();

                } else {
                    dialog.setTitle("提示");
                    dialog.setMessage("输入的IP地址有误");
                    dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    Looper.prepare();
                    dialog.show();
                    Looper.myLooper().loop();
                }

            }
        }
    }


    private String language() {
        boolean a = LoginOneActivity.this.getResources().getConfiguration().locale.getCountry().equals("CN");
        boolean b = LoginOneActivity.this.getResources().getConfiguration().locale.getCountry().equals("TW");
        boolean c = LoginOneActivity.this.getResources().getConfiguration().locale.getCountry().equals("UK");
        boolean d = LoginOneActivity.this.getResources().getConfiguration().locale.getCountry().equals("US");
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


    public String sendPost(String url, JSONObject param) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            conn.setConnectTimeout(5000);
            // // 设置通用的请求属性
            // conn.setRequestProperty("accept", "*/*");
            // conn.setRequestProperty("connection", "Keep-Alive");
            // conn.setRequestProperty("user-agent",
            // "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输出流、输入流
        finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        handler.sendEmptyMessage(2);
        return result;
    }


    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 2){
                dialog2.close();
            }

            super.handleMessage(msg);
        }
    };

}
