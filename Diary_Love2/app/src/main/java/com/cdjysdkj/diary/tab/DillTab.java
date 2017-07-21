package com.cdjysdkj.diary.tab;

import com.j256.ormlite.field.DatabaseField;

import cn.bmob.v3.BmobObject;

public class DillTab extends BmobObject {
    @DatabaseField(generatedId = true)
    private int ID;
    /**
     */
    @DatabaseField
    private String detailtime = "";//详情时间
    /**
     */
    @DatabaseField
    private String time = "";//时间
    /**
     */
    @DatabaseField
    private String accountType = "";//账户类型
    /**
     */
    @DatabaseField
    private String takeType = "";//支持/收入
    /**
     */
    @DatabaseField
    private String type = "";//零花类型
    /**
     */
    @DatabaseField
    private String indexImg = "0";//图标

    /**
     * @return the indexImg
     */
    public String getIndexImg() {
        return indexImg;
    }

    /**
     * @param indexImg the indexImg to set
     */
    public void setIndexImg(String indexImg) {
        this.indexImg = indexImg;
    }

    /**
     */
    @DatabaseField
    private String money = "";//我的总资产
    /**
     *
     */
    @DatabaseField
    private String declare = "";
    /**
     * @return the detailtime
     */
    @DatabaseField
    private String telPhone = "";//手机号

    public String getTelPhone() {
        return telPhone;
    }

    public void setTelPhone(String telPhone) {
        this.telPhone = telPhone;
    }

    public String getDetailtime() {
        return detailtime;
    }

    /**
     * @param detailtime the detailtime to set
     */
    public void setDetailtime(String detailtime) {
        this.detailtime = detailtime;
    }

    /**
     * @return the time
     */
    public String getTime() {
        return time;
    }

    /**
     * @param time the time to set
     */
    public void setTime(String time) {
        this.time = time;
    }

    /**
     * @return the accountType
     */
    public String getAccountType() {
        return accountType;
    }

    /**
     * @param accountType the accountType to set
     */
    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    /**
     * @return the takeType
     */
    public String getTakeType() {
        return takeType;
    }

    /**
     * @param takeType the takeType to set
     */
    public void setTakeType(String takeType) {
        this.takeType = takeType;
    }

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return the money
     */
    public String getMoney() {
        return money;
    }

    /**
     * @param money the money to set
     */
    public void setMoney(String money) {
        this.money = money;
    }

    /**
     * @return the declare
     */
    public String getDeclare() {
        return declare;
    }

    /**
     * @param declare the declare to set
     */
    public void setDeclare(String declare) {
        this.declare = declare;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }
}
