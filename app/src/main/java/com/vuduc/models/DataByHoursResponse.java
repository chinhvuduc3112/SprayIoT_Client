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
     * result : [{"_id":null,"avgData":null},{"_id":null,"avgData":null},{"_id":null,"avgData":null},{"_id":null,"avgData":null},{"_id":null,"avgData":null},{"_id":null,"avgData":null},{"_id":null,"avgData":null},{"_id":null,"avgData":null},{"_id":null,"avgData":null},{"_id":null,"avgData":null},{"_id":null,"avgData":null},{"_id":null,"avgData":null},{"_id":null,"avgData":null},{"_id":null,"avgData":null},{"_id":null,"avgData":null},{"_id":null,"avgData":null},{"_id":null,"avgData":null},{"_id":null,"avgData":null},{"_id":null,"avgData":null},{"_id":null,"avgData":null},{"_id":null,"avgData":null},{"_id":"59999aa1df986f212c94f275","avgData":21.75},{"_id":null,"avgData":null},{"_id":null,"avgData":null}]
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
         * avgData : null
         */

        @SerializedName("_id")
        private Object id;
        @SerializedName("avgData")
        private Object avgData;

        public static ResultBean objectFromData(String str) {

            return new Gson().fromJson(str, ResultBean.class);
        }

        public Object getId() {
            return id;
        }

        public void setId(Object id) {
            this.id = id;
        }

        public Object getAvgData() {
            return avgData;
        }

        public void setAvgData(Object avgData) {
            this.avgData = avgData;
        }
    }
}
