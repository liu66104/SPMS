package com.example.administrator.spms.model;

import java.util.List;

/**
 * Created by Administrator on 2016/11/14.
 */

public class GetDeviceAlarmListBean {
    /**
     * PageTitle : 逆变器列表
     * DeviceNum : 2
     * DeviceList : [{"DeviceID":"1","DeviceName":"逆变器1","DeviceAlarm":"0"},{"DeviceID":"2","DeviceName":"逆变器2","DeviceAlarm":"0"}]
     * Cmd : GetDeviceAlarmList
     * Ret : Success
     * ErrCode :
     * Msg :
     */

    private String PageTitle;
    private int DeviceNum;
    private String Cmd;
    private String Ret;
    private String ErrCode;
    private String Msg;
    /**
     * DeviceID : 1
     * DeviceName : 逆变器1
     * DeviceAlarm : 0
     */

    private List<DeviceListBean> DeviceList;

    public String getPageTitle() {
        return PageTitle;
    }

    public void setPageTitle(String PageTitle) {
        this.PageTitle = PageTitle;
    }

    public int getDeviceNum() {
        return DeviceNum;
    }

    public void setDeviceNum(int DeviceNum) {
        this.DeviceNum = DeviceNum;
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

    public List<DeviceListBean> getDeviceList() {
        return DeviceList;
    }

    public void setDeviceList(List<DeviceListBean> DeviceList) {
        this.DeviceList = DeviceList;
    }

    public static class DeviceListBean {
        private String DeviceID;
        private String DeviceName;
        private String DeviceAlarm;

        public String getDeviceID() {
            return DeviceID;
        }

        public void setDeviceID(String DeviceID) {
            this.DeviceID = DeviceID;
        }

        public String getDeviceName() {
            return DeviceName;
        }

        public void setDeviceName(String DeviceName) {
            this.DeviceName = DeviceName;
        }

        public String getDeviceAlarm() {
            return DeviceAlarm;
        }

        public void setDeviceAlarm(String DeviceAlarm) {
            this.DeviceAlarm = DeviceAlarm;
        }
    }
}
