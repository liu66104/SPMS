package com.example.administrator.spms.model;

import java.util.List;

/**
 * Created by Administrator on 2016/11/17.
 */

public class AlertBean {


    /**
     * PageTitle : RealTimeAlarm
     * AlarmsCount : 1
     * RealTimeAlarmsInfo : [{"SearchStr":"","AlarmDateTime":"16/11/17 09:33:00","StationName":"Y1B10","DeviceName":"Inverter 1","AlarmTagName":"NBQ001:LD-NBQZT","AlarmDesc":"Inverter block for test ::inverter status Alarm","AckStatus":"1"}]
     * Cmd : null
     * Ret : Success
     * ErrCode : null
     * Msg :
     */

    private String PageTitle;
    private int AlarmsCount;
    private Object Cmd;
    private String Ret;
    private Object ErrCode;
    private String Msg;
    /**
     * SearchStr :
     * AlarmDateTime : 16/11/17 09:33:00
     * StationName : Y1B10
     * DeviceName : Inverter 1
     * AlarmTagName : NBQ001:LD-NBQZT
     * AlarmDesc : Inverter block for test ::inverter status Alarm
     * AckStatus : 1
     */

    private List<RealTimeAlarmsInfoBean> RealTimeAlarmsInfo;

    public String getPageTitle() {
        return PageTitle;
    }

    public void setPageTitle(String PageTitle) {
        this.PageTitle = PageTitle;
    }

    public int getAlarmsCount() {
        return AlarmsCount;
    }

    public void setAlarmsCount(int AlarmsCount) {
        this.AlarmsCount = AlarmsCount;
    }

    public Object getCmd() {
        return Cmd;
    }

    public void setCmd(Object Cmd) {
        this.Cmd = Cmd;
    }

    public String getRet() {
        return Ret;
    }

    public void setRet(String Ret) {
        this.Ret = Ret;
    }

    public Object getErrCode() {
        return ErrCode;
    }

    public void setErrCode(Object ErrCode) {
        this.ErrCode = ErrCode;
    }

    public String getMsg() {
        return Msg;
    }

    public void setMsg(String Msg) {
        this.Msg = Msg;
    }

    public List<RealTimeAlarmsInfoBean> getRealTimeAlarmsInfo() {
        return RealTimeAlarmsInfo;
    }

    public void setRealTimeAlarmsInfo(List<RealTimeAlarmsInfoBean> RealTimeAlarmsInfo) {
        this.RealTimeAlarmsInfo = RealTimeAlarmsInfo;
    }

    public static class RealTimeAlarmsInfoBean {
        private String SearchStr;
        private String AlarmDateTime;
        private String StationName;
        private String DeviceName;
        private String AlarmTagName;
        private String AlarmDesc;
        private String AckStatus;

        public String getSearchStr() {
            return SearchStr;
        }

        public void setSearchStr(String SearchStr) {
            this.SearchStr = SearchStr;
        }

        public String getAlarmDateTime() {
            return AlarmDateTime;
        }

        public void setAlarmDateTime(String AlarmDateTime) {
            this.AlarmDateTime = AlarmDateTime;
        }

        public String getStationName() {
            return StationName;
        }

        public void setStationName(String StationName) {
            this.StationName = StationName;
        }

        public String getDeviceName() {
            return DeviceName;
        }

        public void setDeviceName(String DeviceName) {
            this.DeviceName = DeviceName;
        }

        public String getAlarmTagName() {
            return AlarmTagName;
        }

        public void setAlarmTagName(String AlarmTagName) {
            this.AlarmTagName = AlarmTagName;
        }

        public String getAlarmDesc() {
            return AlarmDesc;
        }

        public void setAlarmDesc(String AlarmDesc) {
            this.AlarmDesc = AlarmDesc;
        }

        public String getAckStatus() {
            return AckStatus;
        }

        public void setAckStatus(String AckStatus) {
            this.AckStatus = AckStatus;
        }
    }
}
