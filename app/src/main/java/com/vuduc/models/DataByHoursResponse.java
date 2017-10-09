package com.vuduc.models;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by admin on 10/2/2017.
 */

public class DataByHoursResponse {

    /**
     * status : 1
     * result : [{"_id":null,"avgData":-1},{"_id":null,"avgData":-1},{"_id":null,"avgData":-1},{"_id":null,"avgData":-1},{"_id":null,"avgData":-1},{"_id":null,"avgData":-1},{"_id":null,"avgData":-1},{"_id":null,"avgData":-1},{"_id":null,"avgData":-1},{"_id":null,"avgData":-1},{"_id":null,"avgData":-1},{"_id":null,"avgData":-1},{"_id":null,"avgData":-1},{"_id":null,"avgData":-1},{"_id":null,"avgData":-1},{"_id":null,"avgData":-1},{"_id":null,"avgData":-1},{"_id":null,"avgData":-1},{"_id":null,"avgData":-1},{"_id":null,"avgData":-1},{"_id":null,"avgData":-1},{"_id":"59999aa1df986f212c94f275","avgData":21.75},{"_id":null,"avgData":-1},{"_id":null,"avgData":-1}]
     */

    @SerializedName("status")
    private int status;
    @SerializedName("result")
    private List<ResultBean> result;

    public static DataByHoursResponse objectFromData(String str) {

        return new Gson().fromJson(str, DataByHoursResponse.class);
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * _id : null
         * avgData : -1
         */

        @SerializedName("_id")
        private String id;
        @SerializedName("avgData")
        private float avgData;

        public static ResultBean objectFromData(String str) {

            return new Gson().fromJson(str, ResultBean.class);
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public float getAvgData() {
            return avgData;
        }

        public void setAvgData(float avgData) {
            this.avgData = avgData;
        }
    }
}
