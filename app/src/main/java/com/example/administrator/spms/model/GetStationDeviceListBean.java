package com.example.administrator.spms.model;

import java.util.List;

/**
 * Created by Administrator on 2016/11/22.
 */

public class GetStationDeviceListBean {
    /**
     * DeviceType : '',逆变器,电表
     * StationsNum : 11
     * Stations : [{"StationID":"","StationName":"全部","InverterDevicesNum":0,"InverterDevices":[],"EleMeterDevicesNum":0,"EleMeterDevices":[]},{"StationID":"100010004","StationName":"Y1B10第一个电站第一个电站第一个电站","InverterDevicesNum":12,"InverterDevices":[{"DeviceID":"","DeviceName":"全部"},{"DeviceID":"2","DeviceName":"逆变器1逆变器1逆变器1逆变器1逆变器1"},{"DeviceID":"3","DeviceName":"逆变器2逆变器2逆变器2逆变器2逆变器2"},{"DeviceID":"6","DeviceName":"Huawei001"},{"DeviceID":"7","DeviceName":"逆变器3逆变器3逆变器3逆变器3逆变器3"},{"DeviceID":"8","DeviceName":"逆变器4逆变器4逆变器4逆变器4逆变器4"},{"DeviceID":"9","DeviceName":"逆变器5逆变器5逆变器5逆变器5逆变器5"},{"DeviceID":"10","DeviceName":"逆变器6"},{"DeviceID":"11","DeviceName":"逆变器7"},{"DeviceID":"12","DeviceName":"逆变器8"},{"DeviceID":"13","DeviceName":"逆变器9"},{"DeviceID":"14","DeviceName":"逆变器10"}],"EleMeterDevicesNum":2,"EleMeterDevices":[{"DeviceID":"","DeviceName":"全部"},{"DeviceID":"1","DeviceName":"电表1"}]},{"StationID":"100010005","StationName":"Y2B10第二个电站第二个电站第二个电站","InverterDevicesNum":3,"InverterDevices":[{"DeviceID":"","DeviceName":"全部"},{"DeviceID":"4","DeviceName":"逆变器1逆变器1逆变器1逆变器1逆变器1"},{"DeviceID":"5","DeviceName":"逆变器2逆变器2逆变器2逆变器2逆变器2"}],"EleMeterDevicesNum":2,"EleMeterDevices":[{"DeviceID":"","DeviceName":"全部"},{"DeviceID":"2","DeviceName":"电表1"}]},{"StationID":"100010006","StationName":"Y3B10第三 个电站第三个电站第三个电站","InverterDevicesNum":1,"InverterDevices":[{"DeviceID":"","DeviceName":"全部"}],"EleMeterDevicesNum":1,"EleMeterDevices":[{"DeviceID":"","DeviceName":"全部"}]},{"StationID":"100010007","StationName":"Y4B10第四个电站第四个电站第四个电站","InverterDevicesNum":1,"InverterDevices":[{"DeviceID":"","DeviceName":"全部"}],"EleMeterDevicesNum":1,"EleMeterDevices":[{"DeviceID":"","DeviceName":"全部"}]},{"StationID":"100010008","StationName":"Y5B10第五个电站第五个电站第五个电站","InverterDevicesNum":1,"InverterDevices":[{"DeviceID":"","DeviceName":"全部"}],"EleMeterDevicesNum":1,"EleMeterDevices":[{"DeviceID":"","DeviceName":"全部"}]},{"StationID":"100010009","StationName":"Y6B10第六个电站第六个电站第六个电站","InverterDevicesNum":1,"InverterDevices":[{"DeviceID":"","DeviceName":"全部"}],"EleMeterDevicesNum":1,"EleMeterDevices":[{"DeviceID":"","DeviceName":"全部"}]},{"StationID":"100010010","StationName":"Y7B10","InverterDevicesNum":1,"InverterDevices":[{"DeviceID":"","DeviceName":"全部"}],"EleMeterDevicesNum":1,"EleMeterDevices":[{"DeviceID":"","DeviceName":"全部"}]},{"StationID":"100010011","StationName":"Y8B10","InverterDevicesNum":1,"InverterDevices":[{"DeviceID":"","DeviceName":"全部"}],"EleMeterDevicesNum":1,"EleMeterDevices":[{"DeviceID":"","DeviceName":"全部"}]},{"StationID":"100010012","StationName":"Y9B10","InverterDevicesNum":1,"InverterDevices":[{"DeviceID":"","DeviceName":"全部"}],"EleMeterDevicesNum":1,"EleMeterDevices":[{"DeviceID":"","DeviceName":"全部"}]},{"StationID":"100010013","StationName":"Y10B10","InverterDevicesNum":1,"InverterDevices":[{"DeviceID":"","DeviceName":"全部"}],"EleMeterDevicesNum":1,"EleMeterDevices":[{"DeviceID":"","DeviceName":"全部"}]}]
     * Cmd : GetStationDeviceList
     * Ret : Success
     * ErrCode :
     * Msg :
     */

    private String DeviceType;
    private int StationsNum;
    private String Cmd;
    private String Ret;
    private String ErrCode;
    private String Msg;
    /**
     * StationID :
     * StationName : 全部
     * InverterDevicesNum : 0
     * InverterDevices : []
     * EleMeterDevicesNum : 0
     * EleMeterDevices : []
     */

    private List<StationsBean> Stations;

    public String getDeviceType() {
        return DeviceType;
    }

    public void setDeviceType(String DeviceType) {
        this.DeviceType = DeviceType;
    }

    public int getStationsNum() {
        return StationsNum;
    }

    public void setStationsNum(int StationsNum) {
        this.StationsNum = StationsNum;
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

    public List<StationsBean> getStations() {
        return Stations;
    }

    public void setStations(List<StationsBean> Stations) {
        this.Stations = Stations;
    }

    public static class StationsBean {
        private String StationID;
        private String StationName;
        private int InverterDevicesNum;
        private int EleMeterDevicesNum;
        private List<InverterDevicesBean> InverterDevices;
        private List<EleMeterDevicesBean> EleMeterDevices;

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

        public int getInverterDevicesNum() {
            return InverterDevicesNum;
        }

        public void setInverterDevicesNum(int InverterDevicesNum) {
            this.InverterDevicesNum = InverterDevicesNum;
        }

        public int getEleMeterDevicesNum() {
            return EleMeterDevicesNum;
        }

        public void setEleMeterDevicesNum(int EleMeterDevicesNum) {
            this.EleMeterDevicesNum = EleMeterDevicesNum;
        }

        public List<InverterDevicesBean> getInverterDevices() {
            return InverterDevices;
        }

        public void setInverterDevices(List<InverterDevicesBean> InverterDevices) {
            this.InverterDevices = InverterDevices;
        }

        public List<EleMeterDevicesBean> getEleMeterDevices() {
            return EleMeterDevices;
        }

        public void setEleMeterDevices(List<EleMeterDevicesBean> EleMeterDevices) {
            this.EleMeterDevices = EleMeterDevices;
        }
    }
    public static class InverterDevicesBean {
        public String getDeviceID() {
            return DeviceID;
        }

        public void setDeviceID(String deviceID) {
            DeviceID = deviceID;
        }

        private String DeviceID ;

        public String getDeviceName() {
            return DeviceName;
        }

        public void setDeviceName(String deviceName) {
            DeviceName = deviceName;
        }

        private String DeviceName ;

    }
    public static class EleMeterDevicesBean {
        public String getDeviceID() {
            return DeviceID;
        }

        public void setDeviceID(String deviceID) {
            DeviceID = deviceID;
        }

        private String DeviceID ;

        public String getDeviceName() {
            return DeviceName;
        }

        public void setDeviceName(String deviceName) {
            DeviceName = deviceName;
        }

        private String DeviceName ;

    }
}
