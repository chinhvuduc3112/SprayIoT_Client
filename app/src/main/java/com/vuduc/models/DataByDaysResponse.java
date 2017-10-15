package com.vuduc.models;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by VuDuc on 10/15/2017.
 */

public class DataByDaysResponse {

    /**
     * status : 1
     * result : [{"date":1506867218000,"avgData":21.75},{"date":1506953618000,"avgData":-1}]
     */

    @SerializedName("status")
    private int status;
    @SerializedName("result")
    private List<Result> result;

    public static DataByDaysResponse objectFromData(String str) {

        return new Gson().fromJson(str, DataByDaysResponse.class);
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<Result> getResult() {
        return result;
    }

    public void setResult(List<Result> result) {
        this.result = result;
    }

    public static class Result {
        /**
         * date : 1506867218000
         * avgData : 21.75
         */

        @SerializedName("date")
        private long date;
        @SerializedName("avgData")
        private float avgData;

        public static Result objectFromData(String str) {

            return new Gson().fromJson(str, Result.class);
        }

        public long getDate() {
            return date;
        }

        public void setDate(long date) {
            this.date = date;
        }

        public float getAvgData() {
            return avgData;
        }

        public void setAvgData(float avgData) {
            this.avgData = avgData;
        }
    }
}
