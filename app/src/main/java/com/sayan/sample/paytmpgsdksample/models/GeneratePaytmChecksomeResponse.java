package com.sayan.sample.paytmpgsdksample.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GeneratePaytmChecksomeResponse {
    @SerializedName("checksomeHASH")
    @Expose
    private String checksomeHASH;
    @SerializedName("orderID")
    @Expose
    private String orderID;
    @SerializedName("data")
    @Expose
    private Data data;
    @SerializedName("result")
    @Expose
    private String result;

    /**
     * No args constructor for use in serialization
     *
     */
    public GeneratePaytmChecksomeResponse() {
    }

    /**
     *
     * @param result
     * @param orderID
     * @param checksomeHASH
     * @param data
     */
    public GeneratePaytmChecksomeResponse(String checksomeHASH, String orderID, Data data, String result) {
        super();
        this.checksomeHASH = checksomeHASH;
        this.orderID = orderID;
        this.data = data;
        this.result = result;
    }

    public String getChecksomeHASH() {
        return checksomeHASH;
    }

    public void setChecksomeHASH(String checksomeHASH) {
        this.checksomeHASH = checksomeHASH;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
