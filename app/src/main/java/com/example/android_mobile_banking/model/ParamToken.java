package com.example.android_mobile_banking.model;

public class ParamToken {
    private int nominal;
    private int id_card_nasabah;
    private String nama_transaksi;
    private String no_pelanggan;
    private int id_voucer;

    public ParamToken() {
    }

    public int getNominal() {
        return nominal;
    }

    public void setNominal(int nominal) {
        this.nominal = nominal;
    }

    public int getId_card_nasabah() {
        return id_card_nasabah;
    }

    public void setId_card_nasabah(int id_card_nasabah) {
        this.id_card_nasabah = id_card_nasabah;
    }

    public String getNama_transaksi() {
        return nama_transaksi;
    }

    public void setNama_transaksi(String nama_transaksi) {
        this.nama_transaksi = nama_transaksi;
    }

    public String getNo_pelanggan() {
        return no_pelanggan;
    }

    public void setNo_pelanggan(String no_pelanggan) {
        this.no_pelanggan = no_pelanggan;
    }

    public int getId_voucer() {
        return id_voucer;
    }

    public void setId_voucer(int id_voucer) {
        this.id_voucer = id_voucer;
    }
}
