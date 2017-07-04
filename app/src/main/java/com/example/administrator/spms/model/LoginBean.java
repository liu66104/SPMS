package com.example.administrator.spms.model;

/**
 * Created by Administrator on 2016/11/9.
 */

public class LoginBean {


    /**
     * SessionID : 1805746240
     * User : Admin
     * UserBaseInfo : {"SessionID":"1805746240","NameStr":"用户名","Name":"Admin","Level":"1","LevelDescStr":"用户级别","LevelDesc":"运营商管理员","UserDescStr":"描述","UserDesc":"Default User. Super Administrator. Unique Operator Administrator"}
     * Cmd : Login
     * Ret : Success
     * ErrCode :
     * Msg :
     */

    private String SessionID;
    private String User;
    /**
     * SessionID : 1805746240
     * NameStr : 用户名
     * Name : Admin
     * Level : 1
     * LevelDescStr : 用户级别
     * LevelDesc : 运营商管理员
     * UserDescStr : 描述
     * UserDesc : Default User. Super Administrator. Unique Operator Administrator
     */

    private UserBaseInfoBean UserBaseInfo;
    private String Cmd;
    private String Ret;
    private String ErrCode;
    private String Msg;

    public String getSessionID() {
        return SessionID;
    }

    public void setSessionID(String SessionID) {
        this.SessionID = SessionID;
    }

    public String getUser() {
        return User;
    }

    public void setUser(String User) {
        this.User = User;
    }

    public UserBaseInfoBean getUserBaseInfo() {
        return UserBaseInfo;
    }

    public void setUserBaseInfo(UserBaseInfoBean UserBaseInfo) {
        this.UserBaseInfo = UserBaseInfo;
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

    public static class UserBaseInfoBean {
        private String SessionID;
        private String NameStr;
        private String Name;
        private String Level;
        private String LevelDescStr;
        private String LevelDesc;
        private String UserDescStr;
        private String UserDesc;

        public String getSessionID() {
            return SessionID;
        }

        public void setSessionID(String SessionID) {
            this.SessionID = SessionID;
        }

        public String getNameStr() {
            return NameStr;
        }

        public void setNameStr(String NameStr) {
            this.NameStr = NameStr;
        }

        public String getName() {
            return Name;
        }

        public void setName(String Name) {
            this.Name = Name;
        }

        public String getLevel() {
            return Level;
        }

        public void setLevel(String Level) {
            this.Level = Level;
        }

        public String getLevelDescStr() {
            return LevelDescStr;
        }

        public void setLevelDescStr(String LevelDescStr) {
            this.LevelDescStr = LevelDescStr;
        }

        public String getLevelDesc() {
            return LevelDesc;
        }

        public void setLevelDesc(String LevelDesc) {
            this.LevelDesc = LevelDesc;
        }

        public String getUserDescStr() {
            return UserDescStr;
        }

        public void setUserDescStr(String UserDescStr) {
            this.UserDescStr = UserDescStr;
        }

        public String getUserDesc() {
            return UserDesc;
        }

        public void setUserDesc(String UserDesc) {
            this.UserDesc = UserDesc;
        }
    }
}
