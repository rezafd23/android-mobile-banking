package com.example.android_mobile_banking.model;

public class Mutasi {
    private String no_rekening;
    private String nama_transaksi;
    private int nominal;
    private String status_transaksi;
    private String card_no;
    private String tgl_transaksi;

    public Mutasi() {
    }

    public String getNo_rekening() {
        return no_rekening;
    }

    public void setNo_rekening(String no_rekening) {
        this.no_rekening = no_rekening;
    }

    public String getNama_transaksi() {
        return nama_transaksi;
    }

    public void setNama_transaksi(String nama_transaksi) {
        this.nama_transaksi = nama_transaksi;
    }

    public int getNominal() {
        return nominal;
    }

    public void setNominal(int nominal) {
        this.nominal = nominal;
    }

    public String getStatus_transaksi() {
        return status_transaksi;
    }

    public void setStatus_transaksi(String status_transaksi) {
        this.status_transaksi = status_transaksi;
    }

    public String getCard_no() {
        return card_no;
    }

    public void setCard_no(String card_no) {
        this.card_no = card_no;
    }

    public String getTgl_transaksi() {
        return tgl_transaksi;
    }

    public void setTgl_transaksi(String tgl_transaksi) {
        this.tgl_transaksi = tgl_transaksi;
    }
}
