package com.vuduc.models;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

/**
 * Created by admin on 3/10/2018.
 */

public class AutoableResponse {

    /**
     * status : 1
     * autoable : false
     */

    @SerializedName("status")
    private int status;
    @SerializedName("autoable")
    private boolean autoable;

    public static AutoableResponse objectFromData(String str) {

        return new Gson().fromJson(str, AutoableResponse.class);
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public boolean isAutoable() {
        return autoable;
    }

    public void setAutoable(boolean autoable) {
        this.autoable = autoable;
    }
}
