package com.example.administrator.spms.model;

/**
 * Created by Administrator on 2016/11/10.
 */

public class GetGroupOperationInfo {
    /**
     * PageTitle : 集团运营
     * GroupOperationInfo : {"DayPowerGenerationStr":"今日已发电量","DayPowerGeneration":"0","DayPowerGenerationUnit":"kWh","YearPowerGenerationStr":"年已发电量","YearPowerGeneration":"205.933","YearPowerGenerationUnit":"MWh","YearObjectiveGenerationStr":"年发电量目标","YearObjectiveGeneration":"600","YearObjectiveGenerationUnit":"MWh","WorkingStationNumStr":"运行电站数量","WorkingStationNum":"3","WorkingStationNumUnit":"座","AllStationCapacityStr":"运行电站容量","AllStationCapacity":"2.4","AllStationCapacityUnit":"MW","TotalBenefitStr":"业主累计收益","TotalBenefit":"2386.608","TotalBenefitUnit":"百万元","EmissionsReductionStr":"累计碳减排量","EmissionsReduction":"0","EmissionsReductionUnit":"kg","LastYearCompletionRateStr":"去年发电量完成率","LastYearCompletionRate":"非数字","LastYearCompletionRateUnit":"%"}
     * Cmd : GetGroupOperationInfo
     * Ret : Success
     * ErrCode :
     * Msg :
     */

    private String PageTitle;
    /**
     * DayPowerGenerationStr : 今日已发电量
     * DayPowerGeneration : 0
     * DayPowerGenerationUnit : kWh
     * YearPowerGenerationStr : 年已发电量
     * YearPowerGeneration : 205.933
     * YearPowerGenerationUnit : MWh
     * YearObjectiveGenerationStr : 年发电量目标
     * YearObjectiveGeneration : 600
     * YearObjectiveGenerationUnit : MWh
     * WorkingStationNumStr : 运行电站数量
     * WorkingStationNum : 3
     * WorkingStationNumUnit : 座
     * AllStationCapacityStr : 运行电站容量
     * AllStationCapacity : 2.4
     * AllStationCapacityUnit : MW
     * TotalBenefitStr : 业主累计收益
     * TotalBenefit : 2386.608
     * TotalBenefitUnit : 百万元
     * EmissionsReductionStr : 累计碳减排量
     * EmissionsReduction : 0
     * EmissionsReductionUnit : kg
     * LastYearCompletionRateStr : 去年发电量完成率
     * LastYearCompletionRate : 非数字
     * LastYearCompletionRateUnit : %
     */

    private GroupOperationInfoBean GroupOperationInfo;
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

    public GroupOperationInfoBean getGroupOperationInfo() {
        return GroupOperationInfo;
    }

    public void setGroupOperationInfo(GroupOperationInfoBean GroupOperationInfo) {
        this.GroupOperationInfo = GroupOperationInfo;
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

    public static class GroupOperationInfoBean {
        private String DayPowerGenerationStr;
        private String DayPowerGeneration;
        private String DayPowerGenerationUnit;
        private String YearPowerGenerationStr;
        private String YearPowerGeneration;
        private String YearPowerGenerationUnit;
        private String YearObjectiveGenerationStr;
        private String YearObjectiveGeneration;
        private String YearObjectiveGenerationUnit;
        private String WorkingStationNumStr;
        private String WorkingStationNum;
        private String WorkingStationNumUnit;
        private String AllStationCapacityStr;
        private String AllStationCapacity;
        private String AllStationCapacityUnit;
        private String TotalBenefitStr;
        private String TotalBenefit;
        private String TotalBenefitUnit;
        private String EmissionsReductionStr;
        private String EmissionsReduction;
        private String EmissionsReductionUnit;
        private String LastYearCompletionRateStr;
        private String LastYearCompletionRate;
        private String LastYearCompletionRateUnit;

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

        public String getYearPowerGenerationStr() {
            return YearPowerGenerationStr;
        }

        public void setYearPowerGenerationStr(String YearPowerGenerationStr) {
            this.YearPowerGenerationStr = YearPowerGenerationStr;
        }

        public String getYearPowerGeneration() {
            return YearPowerGeneration;
        }

        public void setYearPowerGeneration(String YearPowerGeneration) {
            this.YearPowerGeneration = YearPowerGeneration;
        }

        public String getYearPowerGenerationUnit() {
            return YearPowerGenerationUnit;
        }

        public void setYearPowerGenerationUnit(String YearPowerGenerationUnit) {
            this.YearPowerGenerationUnit = YearPowerGenerationUnit;
        }

        public String getYearObjectiveGenerationStr() {
            return YearObjectiveGenerationStr;
        }

        public void setYearObjectiveGenerationStr(String YearObjectiveGenerationStr) {
            this.YearObjectiveGenerationStr = YearObjectiveGenerationStr;
        }

        public String getYearObjectiveGeneration() {
            return YearObjectiveGeneration;
        }

        public void setYearObjectiveGeneration(String YearObjectiveGeneration) {
            this.YearObjectiveGeneration = YearObjectiveGeneration;
        }

        public String getYearObjectiveGenerationUnit() {
            return YearObjectiveGenerationUnit;
        }

        public void setYearObjectiveGenerationUnit(String YearObjectiveGenerationUnit) {
            this.YearObjectiveGenerationUnit = YearObjectiveGenerationUnit;
        }

        public String getWorkingStationNumStr() {
            return WorkingStationNumStr;
        }

        public void setWorkingStationNumStr(String WorkingStationNumStr) {
            this.WorkingStationNumStr = WorkingStationNumStr;
        }

        public String getWorkingStationNum() {
            return WorkingStationNum;
        }

        public void setWorkingStationNum(String WorkingStationNum) {
            this.WorkingStationNum = WorkingStationNum;
        }

        public String getWorkingStationNumUnit() {
            return WorkingStationNumUnit;
        }

        public void setWorkingStationNumUnit(String WorkingStationNumUnit) {
            this.WorkingStationNumUnit = WorkingStationNumUnit;
        }

        public String getAllStationCapacityStr() {
            return AllStationCapacityStr;
        }

        public void setAllStationCapacityStr(String AllStationCapacityStr) {
            this.AllStationCapacityStr = AllStationCapacityStr;
        }

        public String getAllStationCapacity() {
            return AllStationCapacity;
        }

        public void setAllStationCapacity(String AllStationCapacity) {
            this.AllStationCapacity = AllStationCapacity;
        }

        public String getAllStationCapacityUnit() {
            return AllStationCapacityUnit;
        }

        public void setAllStationCapacityUnit(String AllStationCapacityUnit) {
            this.AllStationCapacityUnit = AllStationCapacityUnit;
        }

        public String getTotalBenefitStr() {
            return TotalBenefitStr;
        }

        public void setTotalBenefitStr(String TotalBenefitStr) {
            this.TotalBenefitStr = TotalBenefitStr;
        }

        public String getTotalBenefit() {
            return TotalBenefit;
        }

        public void setTotalBenefit(String TotalBenefit) {
            this.TotalBenefit = TotalBenefit;
        }

        public String getTotalBenefitUnit() {
            return TotalBenefitUnit;
        }

        public void setTotalBenefitUnit(String TotalBenefitUnit) {
            this.TotalBenefitUnit = TotalBenefitUnit;
        }

        public String getEmissionsReductionStr() {
            return EmissionsReductionStr;
        }

        public void setEmissionsReductionStr(String EmissionsReductionStr) {
            this.EmissionsReductionStr = EmissionsReductionStr;
        }

        public String getEmissionsReduction() {
            return EmissionsReduction;
        }

        public void setEmissionsReduction(String EmissionsReduction) {
            this.EmissionsReduction = EmissionsReduction;
        }

        public String getEmissionsReductionUnit() {
            return EmissionsReductionUnit;
        }

        public void setEmissionsReductionUnit(String EmissionsReductionUnit) {
            this.EmissionsReductionUnit = EmissionsReductionUnit;
        }

        public String getLastYearCompletionRateStr() {
            return LastYearCompletionRateStr;
        }

        public void setLastYearCompletionRateStr(String LastYearCompletionRateStr) {
            this.LastYearCompletionRateStr = LastYearCompletionRateStr;
        }

        public String getLastYearCompletionRate() {
            return LastYearCompletionRate;
        }

        public void setLastYearCompletionRate(String LastYearCompletionRate) {
            this.LastYearCompletionRate = LastYearCompletionRate;
        }

        public String getLastYearCompletionRateUnit() {
            return LastYearCompletionRateUnit;
        }

        public void setLastYearCompletionRateUnit(String LastYearCompletionRateUnit) {
            this.LastYearCompletionRateUnit = LastYearCompletionRateUnit;
        }
    }
}
