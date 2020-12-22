package com.example.android_mobile_banking.model;

import java.io.Serializable;

public class ElementModel   implements Serializable {

    public int getAppType() {
        return appType;
    }

    public void setAppType(int appType) {
        this.appType = appType;
    }

    private int appType;

    private byte[] data1;
    private String tag1;
    private byte[] data2;

    public byte[] getData1() {
        return data1;
    }

    public void setData1(byte[] data1) {
        this.data1 = data1;
    }



    public String getTag1() {
        return tag1;
    }

    public void setTag1(String tag1) {
        this.tag1 = tag1;
    }

    public byte[] getData2() {
        return data2;
    }

    public void setData2(byte[] data2) {
        this.data2 = data2;
    }

    public String getTag2() {
        return tag2;
    }

    public void setTag2(String tag2) {
        this.tag2 = tag2;
    }

    private String tag2;



}