package com.example.administrator.spms.model;

/**
 * Created by Administrator on 2017/1/16.
 */

public class ButtonBean {

    /**
     * ButtonQuery : Query
     * DateTime : DateTime
     * PowerStation : Station
     * Device : Device
     * TagName : TagName
     * AlarmDesc : AlarmDesc
     */

    private TextInfoBean TextInfo;
    /**
     * TextInfo : {"ButtonQuery":"Query","DateTime":"DateTime","PowerStation":"Station","Device":"Device","TagName":"TagName","AlarmDesc":"AlarmDesc"}
     * Cmd : GetRTAlarmStr
     * Ret : Success
     * ErrCode :
     * Msg :
     */

    private String Cmd;
    private String Ret;
    private String ErrCode;
    private String Msg;

    public TextInfoBean getTextInfo() {
        return TextInfo;
    }

    public void setTextInfo(TextInfoBean TextInfo) {
        this.TextInfo = TextInfo;
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

    public static class TextInfoBean {
        private String ButtonQuery;
        private String DateTime;
        private String PowerStation;
        private String Device;
        private String TagName;
        private String AlarmDesc;

        public String getButtonQuery() {
            return ButtonQuery;
        }

        public void setButtonQuery(String ButtonQuery) {
            this.ButtonQuery = ButtonQuery;
        }

        public String getDateTime() {
            return DateTime;
        }

        public void setDateTime(String DateTime) {
            this.DateTime = DateTime;
        }

        public String getPowerStation() {
            return PowerStation;
        }

        public void setPowerStation(String PowerStation) {
            this.PowerStation = PowerStation;
        }

        public String getDevice() {
            return Device;
        }

        public void setDevice(String Device) {
            this.Device = Device;
        }

        public String getTagName() {
            return TagName;
        }

        public void setTagName(String TagName) {
            this.TagName = TagName;
        }

        public String getAlarmDesc() {
            return AlarmDesc;
        }

        public void setAlarmDesc(String AlarmDesc) {
            this.AlarmDesc = AlarmDesc;
        }
    }
}
