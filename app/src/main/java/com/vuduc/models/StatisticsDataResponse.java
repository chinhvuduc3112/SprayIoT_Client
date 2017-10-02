package com.vuduc.models;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 10/2/2017.
 */

public class StatisticsDataResponse {

    /**
     * status : 1
     * result : [[],[],[],[],[],[],[],[],[],[],[],[],[],[],[],[],[],[],[],[],[],[{"_id":"59999aa1df986f212c94f275","avgData":21.75}],[],[]]
     */

    @SerializedName("status")
    private int status;
    @SerializedName("result")
    private List<List<?>> result;

    public static StatisticsDataResponse objectFromData(String str) {

        return new Gson().fromJson(str, StatisticsDataResponse.class);
    }

    public static List<StatisticsDataResponse> arrayStatisticsDataResponseFromData(String str) {

        Type listType = new TypeToken<ArrayList<StatisticsDataResponse>>() {
        }.getType();

        return new Gson().fromJson(str, listType);
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<List<?>> getResult() {
        return result;
    }

    public void setResult(List<List<?>> result) {
        this.result = result;
    }
}
