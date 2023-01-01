package com.example.isk.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Thread {
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("judul")
    private String judul;
    @SerializedName("gambar")
    private String gambar;
    @SerializedName("isi")
    private String isi;
    @SerializedName("tanggal")
    private String tanggal;
    @SerializedName("status")
    private int status;

    public Thread() {
    }

    public Thread(int id, String judul, String gambar, String isi) {
        this.id = id;
        this.judul = judul;
        this.gambar = gambar;
        this.isi = isi;
    }

    public Thread(String judul, String gambar, String isi) {
        this.judul = judul;
        this.gambar = gambar;
        this.isi = isi;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getGambar() {
        return gambar;
    }

    public void setGambar(String gambar) {
        this.gambar = gambar;
    }

    public String getIsi() {
        return isi;
    }

    public void setIsi(String isi) {
        this.isi = isi;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
