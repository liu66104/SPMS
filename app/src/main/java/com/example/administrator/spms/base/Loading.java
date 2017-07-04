package com.example.administrator.spms.base;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Administrator on 2016/11/24.
 */

public class Loading {
    private Context context;


    public static String getText(Context context){
        SharedPreferences sp = context.getSharedPreferences("language", context.MODE_APPEND);
        String ID = sp.getString("language","");
        String value;
        if (ID.equals("en")){
            value = "Loading...";
        }else {
            value = "加载中...";
        }

        return value;
    }

}
