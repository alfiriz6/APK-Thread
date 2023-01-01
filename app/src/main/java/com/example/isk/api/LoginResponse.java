package com.example.isk.api;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class LoginResponse implements Serializable {
    @SerializedName("status")
    private int status;
    @SerializedName("error")
    private String error;
    @SerializedName("message")
    private String message;
    @SerializedName("token")
    private String token;
    @SerializedName("nama")
    private String nama;
    @SerializedName("role")
    private Integer role;

    public LoginResponse(int status, String error, String message, String token, String nama, Integer role) {
        this.status = status;
        this.error = error;
        this.message = message;
        this.token = token;
        this.nama = nama;
        this.role = role;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }
}
