package com.jueme.android.newas.bean;

/**
 * Created by Jueme on 2020/12/22
 **/
public class AlarmList {
    /**
     * signalLevel : 蓝色
     * issueContent : 云南省气象台2020年12月19日17时10分继续发布寒潮蓝色预警：预计未来24小时，昭通、曲靖、文山、红河、玉溪、昆明将维持低温阴冷天气。昭通、曲靖、昆明东部、文山北部、红河北部还将出现雨夹雪或小雪，请注意防范。（预警信息来源：国家预警信息发布中心）
     * province : 云南省
     * issueTime : 2020-12-19 17:12:16
     * signalType : 寒潮
     * city :
     */

    private String signalLevel;
    private String issueContent;
    private String province;
    private String issueTime;
    private String signalType;
    private String city;

    public String getSignalLevel() {
        return signalLevel;
    }

    public void setSignalLevel(String signalLevel) {
        this.signalLevel = signalLevel;
    }

    public String getIssueContent() {
        return issueContent;
    }

    public void setIssueContent(String issueContent) {
        this.issueContent = issueContent;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getIssueTime() {
        return issueTime;
    }

    public void setIssueTime(String issueTime) {
        this.issueTime = issueTime;
    }

    public String getSignalType() {
        return signalType;
    }

    public void setSignalType(String signalType) {
        this.signalType = signalType;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
