package com.example.administrator.spms.model;

/**
 * Created by Administrator on 2016/11/21.
 */

public class AboutBean {


    /**
     * Version : 研华光伏电站服务器版本 3.0.609c
     * Cmd : GetSPMSServerVersion
     * Ret : Success
     * ErrCode :
     * Msg :
     */

    private String Version;
    private String Cmd;
    private String Ret;
    private String ErrCode;
    private String Msg;

    public String getVersion() {
        return Version;
    }

    public void setVersion(String Version) {
        this.Version = Version;
    }

    public String getCmd() {
        return Cmd;
    }

    public void setCmd(String Cmd) {
        this.Cmd = Cmd;
    }

    public String getRet() {
        return Ret;
    }

    public void setRet(String Ret) {
        this.Ret = Ret;
    }

    public String getErrCode() {
        return ErrCode;
    }

    public void setErrCode(String ErrCode) {
        this.ErrCode = ErrCode;
    }

    public String getMsg() {
        return Msg;
    }

    public void setMsg(String Msg) {
        this.Msg = Msg;
    }
}
