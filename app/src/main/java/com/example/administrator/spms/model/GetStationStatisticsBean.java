package com.example.administrator.spms.model;

/**
 * Created by Administrator on 2016/11/18.
 */

public class GetStationStatisticsBean {

    /**
     * PageTitle : Power
     * StaisticsInfo : {"StationID":"100010004","Data":"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"}
     * Cmd : GetStationStatistics
     * Ret : Success
     * ErrCode :
     * Msg :
     */

    private String PageTitle;
    /**
     * StationID : 100010004
     * Data : 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
     */

    private StaisticsInfoBean StaisticsInfo;
    private String Cmd;
    private String Ret;
    private String ErrCode;
    private String Msg;

    public String getPageTitle() {
        return PageTitle;
    }

    public void setPageTitle(String PageTitle) {
        this.PageTitle = PageTitle;
    }

    public StaisticsInfoBean getStaisticsInfo() {
        return StaisticsInfo;
    }

    public void setStaisticsInfo(StaisticsInfoBean StaisticsInfo) {
        this.StaisticsInfo = StaisticsInfo;
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

    public static class StaisticsInfoBean {
        private String StationID;
        private String Data;

        public String getStationID() {
            return StationID;
        }

        public void setStationID(String StationID) {
            this.StationID = StationID;
        }

        public String getData() {
            return Data;
        }

        public void setData(String Data) {
            this.Data = Data;
        }
    }
}
