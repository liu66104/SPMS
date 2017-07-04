package com.example.administrator.spms.model;

/**
 * Created by Administrator on 2016/11/30.
 */

public class TextBean {


    /**
     * ButtonRun : 运营
     * ButtonPowerStation : 电站
     * ButtonEquipment : 设备
     * ButtonAlarm : 报警
     * UserMessage : 账户信息
     * Set : 设置
     * About : 关于
     * Exit : 退出
     */

    private TextInfoBean TextInfo;
    /**
     * TextInfo : {"ButtonRun":"运营","ButtonPowerStation":"电站","ButtonEquipment":"设备","ButtonAlarm":"报警","UserMessage":"账户信息","Set":"设置","About":"关于","Exit":"退出"}
     * Cmd : GetFunctionStr
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
        private String ButtonRun;
        private String ButtonPowerStation;
        private String ButtonEquipment;
        private String ButtonAlarm;
        private String UserMessage;
        private String Set;
        private String About;
        private String Exit;

        public String getButtonRun() {
            return ButtonRun;
        }

        public void setButtonRun(String ButtonRun) {
            this.ButtonRun = ButtonRun;
        }

        public String getButtonPowerStation() {
            return ButtonPowerStation;
        }

        public void setButtonPowerStation(String ButtonPowerStation) {
            this.ButtonPowerStation = ButtonPowerStation;
        }

        public String getButtonEquipment() {
            return ButtonEquipment;
        }

        public void setButtonEquipment(String ButtonEquipment) {
            this.ButtonEquipment = ButtonEquipment;
        }

        public String getButtonAlarm() {
            return ButtonAlarm;
        }

        public void setButtonAlarm(String ButtonAlarm) {
            this.ButtonAlarm = ButtonAlarm;
        }

        public String getUserMessage() {
            return UserMessage;
        }

        public void setUserMessage(String UserMessage) {
            this.UserMessage = UserMessage;
        }

        public String getSet() {
            return Set;
        }

        public void setSet(String Set) {
            this.Set = Set;
        }

        public String getAbout() {
            return About;
        }

        public void setAbout(String About) {
            this.About = About;
        }

        public String getExit() {
            return Exit;
        }

        public void setExit(String Exit) {
            this.Exit = Exit;
        }
    }
}
