package com.example.isk.api;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class UserResponse implements Serializable {
    @SerializedName("status")
    private int status;
    @SerializedName("data")
    private ArrayList<User> data;

    // Getter Methods for arraylist user
    public int getStatus() {
        return status;
    }

    public ArrayList<User> getData() {
        return data;
    }

}