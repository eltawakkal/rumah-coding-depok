package com.example.neardeal.response;

import com.google.gson.annotations.SerializedName;

public class UserResponse {

    @SerializedName("username")
    String user;
    @SerializedName("password")
    String pass;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
}
