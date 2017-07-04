package com.example.administrator.spms.base;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Administrator on 2016/09/02.
 */
public class MD5WS {

    public static String getMD5(String str) throws NoSuchAlgorithmException {
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        md5.update(str.getBytes());
        byte[] m = md5.digest(); //加密
        return getString(m);
    }
    private static String getString(byte[] b){
        StringBuffer sb = new StringBuffer();
        for(int i=0; i<b.length; i++){
            sb.append(b[i]);
        }
        return sb.toString();
    }

}
