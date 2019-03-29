package com.sayan.sample.paytmpgsdksample.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {
    @SerializedName("MID")
    @Expose
    private String mID;
    @SerializedName("ORDER_ID")
    @Expose
    private String orderID;
    @SerializedName("MOBILE_NO")
    @Expose
    private String mobileNo;
    @SerializedName("EMAIL")
    @Expose
    private String email;
    @SerializedName("CUST_ID")
    @Expose
    private String custID;
    @SerializedName("INDUSTRY_TYPE_ID")
    @Expose
    private String industryTypeID;
    @SerializedName("CHANNEL_ID")
    @Expose
    private String channelID;
    @SerializedName("TXN_AMOUNT")
    @Expose
    private String txnAmount;
    @SerializedName("WEBSITE")
    @Expose
    private String website;
    @SerializedName("CALLBACK_URL")
    @Expose
    private String callbackURL;

    /**
     * No args constructor for use in serialization
     *
     */
    public Data() {
    }

    /**
     *
     * @param txnAmount
     * @param website
     * @param channelID
     * @param custID
     * @param callbackURL
     * @param mID
     * @param orderID
     * @param industryTypeID
     * @param email
     * @param mobileNo
     */
    public Data(String mID, String orderID, String mobileNo, String email, String custID, String industryTypeID, String channelID, String txnAmount, String website, String callbackURL) {
        super();
        this.mID = mID;
        this.orderID = orderID;
        this.mobileNo = mobileNo;
        this.email = email;
        this.custID = custID;
        this.industryTypeID = industryTypeID;
        this.channelID = channelID;
        this.txnAmount = txnAmount;
        this.website = website;
        this.callbackURL = callbackURL;
    }

    public String getMID() {
        return mID;
    }

    public void setMID(String mID) {
        this.mID = mID;
    }

    public String getORDERID() {
        return orderID;
    }

    public void setORDERID(String oRDERID) {
        this.orderID = oRDERID;
    }

    public String getMOBILENO() {
        return mobileNo;
    }

    public void setMOBILENO(String mOBILENO) {
        this.mobileNo = mOBILENO;
    }

    public String getEMAIL() {
        return email;
    }

    public void setEMAIL(String eMAIL) {
        this.email = eMAIL;
    }

    public String getCUSTID() {
        return custID;
    }

    public void setCUSTID(String cUSTID) {
        this.custID = cUSTID;
    }

    public String getINDUSTRYTYPEID() {
        return industryTypeID;
    }

    public void setINDUSTRYTYPEID(String iNDUSTRYTYPEID) {
        this.industryTypeID = iNDUSTRYTYPEID;
    }

    public String getCHANNELID() {
        return channelID;
    }

    public void setCHANNELID(String cHANNELID) {
        this.channelID = cHANNELID;
    }

    public String getTXNAMOUNT() {
        return txnAmount;
    }

    public void setTXNAMOUNT(String tXNAMOUNT) {
        this.txnAmount = tXNAMOUNT;
    }

    public String getWEBSITE() {
        return website;
    }

    public void setWEBSITE(String wEBSITE) {
        this.website = wEBSITE;
    }

    public String getCALLBACKURL() {
        return callbackURL;
    }

    public void setCALLBACKURL(String cALLBACKURL) {
        this.callbackURL = cALLBACKURL;
    }

}
