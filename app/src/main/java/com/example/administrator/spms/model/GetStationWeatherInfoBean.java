package com.example.administrator.spms.model;

/**
 * Created by Administrator on 2016/11/11.
 */

public class GetStationWeatherInfoBean {

    /**
     * PageTitle : 气象信息
     * WeatherInfo : {"StationID":"100010004","WetherInfo":"晴 10℃","WeatherCode":"32","WetherStationStatusStr":"气象站状态","WetherStationStatus":"0","DayTotalRadiationStr":"今日累计辐射量","DayTotalRadiation":"250.055","DayTotalRadiationUnit":"Wh/m²","MonthTotalRadiationStr":"本月累计辐射量","MonthTotalRadiation":"250.055","MonthTotalRadiationUnit":"Wh/m²","YearTotalRadiationStr":"本年累计辐射量","YearTotalRadiation":"2.608","YearTotalRadiationUnit":"MWh/m²","MonthPeakSunshineHourStr":"本月峰值日照小时数","MonthPeakSunshineHour":"0","MonthPeakSunshineHourUnit":"h","YearPeakSunshineHourStr":"本年峰值日照小时数","YearPeakSunshineHour":"-6.492","YearPeakSunshineHourUnit":"h","EnvironmentTemperatureStr":"环境温度","EnvironmentTemperature":"30","EnvironmentTemperatureUnit":"℃","ModuleTemperatureStr":"组件温度","ModuleTemperature":"29","ModuleTemperatureUnit":"℃"}
     * Cmd : GetStationWeatherInfo
     * Ret : Success
     * ErrCode :
     * Msg :
     */

    private String PageTitle;
    /**
     * StationID : 100010004
     * WetherInfo : 晴 10℃
     * WeatherCode : 32
     * WetherStationStatusStr : 气象站状态
     * WetherStationStatus : 0
     * DayTotalRadiationStr : 今日累计辐射量
     * DayTotalRadiation : 250.055
     * DayTotalRadiationUnit : Wh/m²
     * MonthTotalRadiationStr : 本月累计辐射量
     * MonthTotalRadiation : 250.055
     * MonthTotalRadiationUnit : Wh/m²
     * YearTotalRadiationStr : 本年累计辐射量
     * YearTotalRadiation : 2.608
     * YearTotalRadiationUnit : MWh/m²
     * MonthPeakSunshineHourStr : 本月峰值日照小时数
     * MonthPeakSunshineHour : 0
     * MonthPeakSunshineHourUnit : h
     * YearPeakSunshineHourStr : 本年峰值日照小时数
     * YearPeakSunshineHour : -6.492
     * YearPeakSunshineHourUnit : h
     * EnvironmentTemperatureStr : 环境温度
     * EnvironmentTemperature : 30
     * EnvironmentTemperatureUnit : ℃
     * ModuleTemperatureStr : 组件温度
     * ModuleTemperature : 29
     * ModuleTemperatureUnit : ℃
     */

    private WeatherInfoBean WeatherInfo;
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

    public WeatherInfoBean getWeatherInfo() {
        return WeatherInfo;
    }

    public void setWeatherInfo(WeatherInfoBean WeatherInfo) {
        this.WeatherInfo = WeatherInfo;
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

    public static class WeatherInfoBean {
        private String StationID;
        private String WetherInfo;
        private String WeatherCode;
        private String WetherStationStatusStr;
        private String WetherStationStatus;
        private String DayTotalRadiationStr;
        private String DayTotalRadiation;
        private String DayTotalRadiationUnit;
        private String MonthTotalRadiationStr;
        private String MonthTotalRadiation;
        private String MonthTotalRadiationUnit;
        private String YearTotalRadiationStr;
        private String YearTotalRadiation;
        private String YearTotalRadiationUnit;
        private String MonthPeakSunshineHourStr;
        private String MonthPeakSunshineHour;
        private String MonthPeakSunshineHourUnit;
        private String YearPeakSunshineHourStr;
        private String YearPeakSunshineHour;
        private String YearPeakSunshineHourUnit;
        private String EnvironmentTemperatureStr;
        private String EnvironmentTemperature;
        private String EnvironmentTemperatureUnit;
        private String ModuleTemperatureStr;
        private String ModuleTemperature;
        private String ModuleTemperatureUnit;

        public String getStationID() {
            return StationID;
        }

        public void setStationID(String StationID) {
            this.StationID = StationID;
        }

        public String getWetherInfo() {
            return WetherInfo;
        }

        public void setWetherInfo(String WetherInfo) {
            this.WetherInfo = WetherInfo;
        }

        public String getWeatherCode() {
            return WeatherCode;
        }

        public void setWeatherCode(String WeatherCode) {
            this.WeatherCode = WeatherCode;
        }

        public String getWetherStationStatusStr() {
            return WetherStationStatusStr;
        }

        public void setWetherStationStatusStr(String WetherStationStatusStr) {
            this.WetherStationStatusStr = WetherStationStatusStr;
        }

        public String getWetherStationStatus() {
            return WetherStationStatus;
        }

        public void setWetherStationStatus(String WetherStationStatus) {
            this.WetherStationStatus = WetherStationStatus;
        }

        public String getDayTotalRadiationStr() {
            return DayTotalRadiationStr;
        }

        public void setDayTotalRadiationStr(String DayTotalRadiationStr) {
            this.DayTotalRadiationStr = DayTotalRadiationStr;
        }

        public String getDayTotalRadiation() {
            return DayTotalRadiation;
        }

        public void setDayTotalRadiation(String DayTotalRadiation) {
            this.DayTotalRadiation = DayTotalRadiation;
        }

        public String getDayTotalRadiationUnit() {
            return DayTotalRadiationUnit;
        }

        public void setDayTotalRadiationUnit(String DayTotalRadiationUnit) {
            this.DayTotalRadiationUnit = DayTotalRadiationUnit;
        }

        public String getMonthTotalRadiationStr() {
            return MonthTotalRadiationStr;
        }

        public void setMonthTotalRadiationStr(String MonthTotalRadiationStr) {
            this.MonthTotalRadiationStr = MonthTotalRadiationStr;
        }

        public String getMonthTotalRadiation() {
            return MonthTotalRadiation;
        }

        public void setMonthTotalRadiation(String MonthTotalRadiation) {
            this.MonthTotalRadiation = MonthTotalRadiation;
        }

        public String getMonthTotalRadiationUnit() {
            return MonthTotalRadiationUnit;
        }

        public void setMonthTotalRadiationUnit(String MonthTotalRadiationUnit) {
            this.MonthTotalRadiationUnit = MonthTotalRadiationUnit;
        }

        public String getYearTotalRadiationStr() {
            return YearTotalRadiationStr;
        }

        public void setYearTotalRadiationStr(String YearTotalRadiationStr) {
            this.YearTotalRadiationStr = YearTotalRadiationStr;
        }

        public String getYearTotalRadiation() {
            return YearTotalRadiation;
        }

        public void setYearTotalRadiation(String YearTotalRadiation) {
            this.YearTotalRadiation = YearTotalRadiation;
        }

        public String getYearTotalRadiationUnit() {
            return YearTotalRadiationUnit;
        }

        public void setYearTotalRadiationUnit(String YearTotalRadiationUnit) {
            this.YearTotalRadiationUnit = YearTotalRadiationUnit;
        }

        public String getMonthPeakSunshineHourStr() {
            return MonthPeakSunshineHourStr;
        }

        public void setMonthPeakSunshineHourStr(String MonthPeakSunshineHourStr) {
            this.MonthPeakSunshineHourStr = MonthPeakSunshineHourStr;
        }

        public String getMonthPeakSunshineHour() {
            return MonthPeakSunshineHour;
        }

        public void setMonthPeakSunshineHour(String MonthPeakSunshineHour) {
            this.MonthPeakSunshineHour = MonthPeakSunshineHour;
        }

        public String getMonthPeakSunshineHourUnit() {
            return MonthPeakSunshineHourUnit;
        }

        public void setMonthPeakSunshineHourUnit(String MonthPeakSunshineHourUnit) {
            this.MonthPeakSunshineHourUnit = MonthPeakSunshineHourUnit;
        }

        public String getYearPeakSunshineHourStr() {
            return YearPeakSunshineHourStr;
        }

        public void setYearPeakSunshineHourStr(String YearPeakSunshineHourStr) {
            this.YearPeakSunshineHourStr = YearPeakSunshineHourStr;
        }

        public String getYearPeakSunshineHour() {
            return YearPeakSunshineHour;
        }

        public void setYearPeakSunshineHour(String YearPeakSunshineHour) {
            this.YearPeakSunshineHour = YearPeakSunshineHour;
        }

        public String getYearPeakSunshineHourUnit() {
            return YearPeakSunshineHourUnit;
        }

        public void setYearPeakSunshineHourUnit(String YearPeakSunshineHourUnit) {
            this.YearPeakSunshineHourUnit = YearPeakSunshineHourUnit;
        }

        public String getEnvironmentTemperatureStr() {
            return EnvironmentTemperatureStr;
        }

        public void setEnvironmentTemperatureStr(String EnvironmentTemperatureStr) {
            this.EnvironmentTemperatureStr = EnvironmentTemperatureStr;
        }

        public String getEnvironmentTemperature() {
            return EnvironmentTemperature;
        }

        public void setEnvironmentTemperature(String EnvironmentTemperature) {
            this.EnvironmentTemperature = EnvironmentTemperature;
        }

        public String getEnvironmentTemperatureUnit() {
            return EnvironmentTemperatureUnit;
        }

        public void setEnvironmentTemperatureUnit(String EnvironmentTemperatureUnit) {
            this.EnvironmentTemperatureUnit = EnvironmentTemperatureUnit;
        }

        public String getModuleTemperatureStr() {
            return ModuleTemperatureStr;
        }

        public void setModuleTemperatureStr(String ModuleTemperatureStr) {
            this.ModuleTemperatureStr = ModuleTemperatureStr;
        }

        public String getModuleTemperature() {
            return ModuleTemperature;
        }

        public void setModuleTemperature(String ModuleTemperature) {
            this.ModuleTemperature = ModuleTemperature;
        }

        public String getModuleTemperatureUnit() {
            return ModuleTemperatureUnit;
        }

        public void setModuleTemperatureUnit(String ModuleTemperatureUnit) {
            this.ModuleTemperatureUnit = ModuleTemperatureUnit;
        }
    }
}
