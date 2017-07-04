package com.example.administrator.spms.model;

import java.util.List;

/**
 * Created by Administrator on 2016/11/14.
 */

public class GetDeviceStatusListBean {
    /**
     * PageTitle : 逆变器状态
     * ListNum : 2
     * DeviceList : [{"StationID":"100010004","StationName":"Y1B10","TotalStr":"总数","Total":"2","NormalStr":"正常","Normal":"0","FaultStr":"故障","Fault":"2"},{"StationID":"100010005","StationName":"Y2B10","TotalStr":"总数","Total":"2","NormalStr":"正常","Normal":"0","FaultStr":"故障","Fault":"2"}]
     * Cmd : GetDeviceStatusList
     * Ret : Success
     * ErrCode :
     * Msg :
     */

    private String PageTitle;
    private int ListNum;
    private String Cmd;
    private String Ret;
    private String ErrCode;
    private String Msg;
    /**
     * StationID : 100010004
     * StationName : Y1B10
     * TotalStr : 总数
     * Total : 2
     * NormalStr : 正常
     * Normal : 0
     * FaultStr : 故障
     * Fault : 2
     */

    private List<DeviceListBean> DeviceList;

    public String getPageTitle() {
        return PageTitle;
    }

    public void setPageTitle(String PageTitle) {
        this.PageTitle = PageTitle;
    }

    public int getListNum() {
        return ListNum;
    }

    public void setListNum(int ListNum) {
        this.ListNum = ListNum;
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
        private String StationID;
        private String StationName;
        private String TotalStr;
        private String Total;
        private String NormalStr;
        private String Normal;
        private String FaultStr;
        private String Fault;

        public String getStationID() {
            return StationID;
        }

        public void setStationID(String StationID) {
            this.StationID = StationID;
        }

        public String getStationName() {
            return StationName;
        }

        public void setStationName(String StationName) {
            this.StationName = StationName;
        }

        public String getTotalStr() {
            return TotalStr;
        }

        public void setTotalStr(String TotalStr) {
            this.TotalStr = TotalStr;
        }

        public String getTotal() {
            return Total;
        }

        public void setTotal(String Total) {
            this.Total = Total;
        }

        public String getNormalStr() {
            return NormalStr;
        }

        public void setNormalStr(String NormalStr) {
            this.NormalStr = NormalStr;
        }

        public String getNormal() {
            return Normal;
        }

        public void setNormal(String Normal) {
            this.Normal = Normal;
        }

        public String getFaultStr() {
            return FaultStr;
        }

        public void setFaultStr(String FaultStr) {
            this.FaultStr = FaultStr;
        }

        public String getFault() {
            return Fault;
        }

        public void setFault(String Fault) {
            this.Fault = Fault;
        }
    }
}
