package com.example.administrator.spms.model;

/**
 * Created by Administrator on 2016/11/15.
 */

public class GetDeviceAllDataBean {
    /**
     * PageTitle : 逆变器1
     * BaseInfo : {"DeviceID":"1","StationNameStr":"Station","StationName":"Y1B10","DeviceName":"逆变器1","DeviceAlarm":"0","InverterManufacturerStr":"Manufacturer","InverterManufacturer":"","InverterModelNumberStr":"Model","InverterModelNumber":"","InputPowerStr":"DC Input Power\r\n","InputPower":"*","InputPowerUnit":"kW","PowerStr":"AC Output Power\r\n","Power":"*","PowerUnit":"kW","TransferEfficiencyStr":"Conversion Efficiency","TransferEfficiency":"*","TransferEfficiencyUnit":"%","WorkingTemperatureStr":"Device Temperature\r\n","WorkingTemperature":"*","WorkingTemperatureUnit":"℃","DayPowerGenerationStr":"Daily Generation","DayPowerGeneration":"*","DayPowerGenerationUnit":"kWh","TotalPowerGenerationStr":"Accumulated Power Generation\r\n","TotalPowerGeneration":"*","TotalPowerGenerationUnit":"kWh"}
     * Cmd : null
     * Ret : Success
     * ErrCode : null
     * Msg :
     */

    private String PageTitle;
    /**
     * DeviceID : 1
     * StationNameStr : Station
     * StationName : Y1B10
     * DeviceName : 逆变器1
     * DeviceAlarm : 0
     * InverterManufacturerStr : Manufacturer
     * InverterManufacturer :
     * InverterModelNumberStr : Model
     * InverterModelNumber :
     * InputPowerStr : DC Input Power

     * InputPower : *
     * InputPowerUnit : kW
     * PowerStr : AC Output Power

     * Power : *
     * PowerUnit : kW
     * TransferEfficiencyStr : Conversion Efficiency
     * TransferEfficiency : *
     * TransferEfficiencyUnit : %
     * WorkingTemperatureStr : Device Temperature

     * WorkingTemperature : *
     * WorkingTemperatureUnit : ℃
     * DayPowerGenerationStr : Daily Generation
     * DayPowerGeneration : *
     * DayPowerGenerationUnit : kWh
     * TotalPowerGenerationStr : Accumulated Power Generation

     * TotalPowerGeneration : *
     * TotalPowerGenerationUnit : kWh
     */

    private BaseInfoBean BaseInfo;
    private Object Cmd;
    private String Ret;
    private Object ErrCode;
    private String Msg;

    public String getPageTitle() {
        return PageTitle;
    }

    public void setPageTitle(String PageTitle) {
        this.PageTitle = PageTitle;
    }

    public BaseInfoBean getBaseInfo() {
        return BaseInfo;
    }

    public void setBaseInfo(BaseInfoBean BaseInfo) {
        this.BaseInfo = BaseInfo;
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

    public static class BaseInfoBean {
        private String DeviceID;
        private String StationNameStr;
        private String StationName;
        private String DeviceName;
        private String DeviceAlarm;
        private String InverterManufacturerStr;
        private String InverterManufacturer;
        private String InverterModelNumberStr;
        private String InverterModelNumber;
        private String InputPowerStr;
        private String InputPower;
        private String InputPowerUnit;
        private String PowerStr;
        private String Power;
        private String PowerUnit;
        private String TransferEfficiencyStr;
        private String TransferEfficiency;
        private String TransferEfficiencyUnit;
        private String WorkingTemperatureStr;
        private String WorkingTemperature;
        private String WorkingTemperatureUnit;
        private String DayPowerGenerationStr;
        private String DayPowerGeneration;
        private String DayPowerGenerationUnit;
        private String TotalPowerGenerationStr;
        private String TotalPowerGeneration;
        private String TotalPowerGenerationUnit;

        public String getDeviceID() {
            return DeviceID;
        }

        public void setDeviceID(String DeviceID) {
            this.DeviceID = DeviceID;
        }

        public String getStationNameStr() {
            return StationNameStr;
        }

        public void setStationNameStr(String StationNameStr) {
            this.StationNameStr = StationNameStr;
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

        public String getDeviceAlarm() {
            return DeviceAlarm;
        }

        public void setDeviceAlarm(String DeviceAlarm) {
            this.DeviceAlarm = DeviceAlarm;
        }

        public String getInverterManufacturerStr() {
            return InverterManufacturerStr;
        }

        public void setInverterManufacturerStr(String InverterManufacturerStr) {
            this.InverterManufacturerStr = InverterManufacturerStr;
        }

        public String getInverterManufacturer() {
            return InverterManufacturer;
        }

        public void setInverterManufacturer(String InverterManufacturer) {
            this.InverterManufacturer = InverterManufacturer;
        }

        public String getInverterModelNumberStr() {
            return InverterModelNumberStr;
        }

        public void setInverterModelNumberStr(String InverterModelNumberStr) {
            this.InverterModelNumberStr = InverterModelNumberStr;
        }

        public String getInverterModelNumber() {
            return InverterModelNumber;
        }

        public void setInverterModelNumber(String InverterModelNumber) {
            this.InverterModelNumber = InverterModelNumber;
        }

        public String getInputPowerStr() {
            return InputPowerStr;
        }

        public void setInputPowerStr(String InputPowerStr) {
            this.InputPowerStr = InputPowerStr;
        }

        public String getInputPower() {
            return InputPower;
        }

        public void setInputPower(String InputPower) {
            this.InputPower = InputPower;
        }

        public String getInputPowerUnit() {
            return InputPowerUnit;
        }

        public void setInputPowerUnit(String InputPowerUnit) {
            this.InputPowerUnit = InputPowerUnit;
        }

        public String getPowerStr() {
            return PowerStr;
        }

        public void setPowerStr(String PowerStr) {
            this.PowerStr = PowerStr;
        }

        public String getPower() {
            return Power;
        }

        public void setPower(String Power) {
            this.Power = Power;
        }

        public String getPowerUnit() {
            return PowerUnit;
        }

        public void setPowerUnit(String PowerUnit) {
            this.PowerUnit = PowerUnit;
        }

        public String getTransferEfficiencyStr() {
            return TransferEfficiencyStr;
        }

        public void setTransferEfficiencyStr(String TransferEfficiencyStr) {
            this.TransferEfficiencyStr = TransferEfficiencyStr;
        }

        public String getTransferEfficiency() {
            return TransferEfficiency;
        }

        public void setTransferEfficiency(String TransferEfficiency) {
            this.TransferEfficiency = TransferEfficiency;
        }

        public String getTransferEfficiencyUnit() {
            return TransferEfficiencyUnit;
        }

        public void setTransferEfficiencyUnit(String TransferEfficiencyUnit) {
            this.TransferEfficiencyUnit = TransferEfficiencyUnit;
        }

        public String getWorkingTemperatureStr() {
            return WorkingTemperatureStr;
        }

        public void setWorkingTemperatureStr(String WorkingTemperatureStr) {
            this.WorkingTemperatureStr = WorkingTemperatureStr;
        }

        public String getWorkingTemperature() {
            return WorkingTemperature;
        }

        public void setWorkingTemperature(String WorkingTemperature) {
            this.WorkingTemperature = WorkingTemperature;
        }

        public String getWorkingTemperatureUnit() {
            return WorkingTemperatureUnit;
        }

        public void setWorkingTemperatureUnit(String WorkingTemperatureUnit) {
            this.WorkingTemperatureUnit = WorkingTemperatureUnit;
        }

        public String getDayPowerGenerationStr() {
            return DayPowerGenerationStr;
        }

        public void setDayPowerGenerationStr(String DayPowerGenerationStr) {
            this.DayPowerGenerationStr = DayPowerGenerationStr;
        }

        public String getDayPowerGeneration() {
            return DayPowerGeneration;
        }

        public void setDayPowerGeneration(String DayPowerGeneration) {
            this.DayPowerGeneration = DayPowerGeneration;
        }

        public String getDayPowerGenerationUnit() {
            return DayPowerGenerationUnit;
        }

        public void setDayPowerGenerationUnit(String DayPowerGenerationUnit) {
            this.DayPowerGenerationUnit = DayPowerGenerationUnit;
        }

        public String getTotalPowerGenerationStr() {
            return TotalPowerGenerationStr;
        }

        public void setTotalPowerGenerationStr(String TotalPowerGenerationStr) {
            this.TotalPowerGenerationStr = TotalPowerGenerationStr;
        }

        public String getTotalPowerGeneration() {
            return TotalPowerGeneration;
        }

        public void setTotalPowerGeneration(String TotalPowerGeneration) {
            this.TotalPowerGeneration = TotalPowerGeneration;
        }

        public String getTotalPowerGenerationUnit() {
            return TotalPowerGenerationUnit;
        }

        public void setTotalPowerGenerationUnit(String TotalPowerGenerationUnit) {
            this.TotalPowerGenerationUnit = TotalPowerGenerationUnit;
        }
    }
}
