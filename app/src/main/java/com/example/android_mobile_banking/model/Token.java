package com.example.android_mobile_banking.model;

public class Token {
    private int price;
    private String voucer;
    private int id_voucer;

    public Token() {
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getVoucer() {
        return voucer;
    }

    public void setVoucer(String voucer) {
        this.voucer = voucer;
    }

    public int getId_voucer() {
        return id_voucer;
    }

    public void setId_voucer(int id_voucer) {
        this.id_voucer = id_voucer;
    }
}
