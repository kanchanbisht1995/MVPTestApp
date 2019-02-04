package com.mvptestapp.core.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginResponseModel extends BaseResponseModel {
    @SerializedName("token")
    @Expose
    private String token;

    public String getUserId() {
        return token;
    }

    public void setUserId(String userId) {
        this.token = userId;
    }
}
