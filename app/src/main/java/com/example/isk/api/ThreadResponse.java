package com.example.isk.api;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ThreadResponse {

    @SerializedName("status")
    private int status;
    @SerializedName("data")
    private ArrayList<Thread> data;

    // Getter Methods for arraylist user
    public int getStatus() {
        return status;
    }

    public ArrayList<Thread> getData() {
        return data;
    }

}
