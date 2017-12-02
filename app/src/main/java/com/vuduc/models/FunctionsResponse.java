package com.vuduc.models;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by VuDuc on 11/22/2017.
 */

public class FunctionsResponse {

    /**
     * result : [{"_id":"5a032bcdcab58f288cac10a7","name":"func may bom 1","actuatorId":"5a032a2f2f331f2bac34ed29","activityDuration":10,"trash":false,"__v":0,"status":false},{"_id":"5a158b47455fbb1014952e75","name":"func may bom 2","actuatorId":"5a032a2f2f331f2bac34ed29","activityDuration":0,"trash":false,"__v":0,"status":false}]
     * status : 1
     */

    @SerializedName("status")
    private int status;
    @SerializedName("result")
    private List<ResultBean> result;

    public static FunctionsResponse objectFromData(String str) {

        return new Gson().fromJson(str, FunctionsResponse.class);
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
         * _id : 5a032bcdcab58f288cac10a7
         * name : func may bom 1
         * actuatorId : 5a032a2f2f331f2bac34ed29
         * activityDuration : 10
         * trash : false
         * __v : 0
         * status : false
         */

        @SerializedName("_id")
        private String id;
        @SerializedName("name")
        private String name;
        @SerializedName("actuatorId")
        private String actuatorId;
        @SerializedName("activityDuration")
        private int activityDuration;
        @SerializedName("trash")
        private boolean trash;
        @SerializedName("__v")
        private int v;
        @SerializedName("status")
        private boolean status;

        public static ResultBean objectFromData(String str) {

            return new Gson().fromJson(str, ResultBean.class);
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getActuatorId() {
            return actuatorId;
        }

        public void setActuatorId(String actuatorId) {
            this.actuatorId = actuatorId;
        }

        public int getActivityDuration() {
            return activityDuration;
        }

        public void setActivityDuration(int activityDuration) {
            this.activityDuration = activityDuration;
        }

        public boolean isTrash() {
            return trash;
        }

        public void setTrash(boolean trash) {
            this.trash = trash;
        }

        public int getV() {
            return v;
        }

        public void setV(int v) {
            this.v = v;
        }

        public boolean isStatus() {
            return status;
        }

        public void setStatus(boolean status) {
            this.status = status;
        }
    }
}
