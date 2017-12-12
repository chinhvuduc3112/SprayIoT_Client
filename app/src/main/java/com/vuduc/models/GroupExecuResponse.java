package com.vuduc.models;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by VuDuc on 11/23/2017.
 */

public class GroupExecuResponse {

    /**
     * result : [{"_id":"5a27a1e373328b14301c4201","name":"group dieu kien 1","description":"mo ta 1","trash":false,"autoTime":15,"status":true,"function":{"_id":"5a2129845843b420747a56cd","name":"van 2","actuatorId":"5a042bfb03e2ef31540f30f0","activityDuration":20,"manualTime":15,"description":"mo ta cai gi day","trash":false,"status":true,"__v":0}}]
     * status : 1
     */

    @SerializedName("status")
    private int status;
    @SerializedName("result")
    private List<ResultBean> result;

    public static GroupExecuResponse objectFromData(String str) {

        return new Gson().fromJson(str, GroupExecuResponse.class);
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
         * _id : 5a27a1e373328b14301c4201
         * name : group dieu kien 1
         * description : mo ta 1
         * trash : false
         * autoTime : 15
         * status : true
         * function : {"_id":"5a2129845843b420747a56cd","name":"van 2","actuatorId":"5a042bfb03e2ef31540f30f0","activityDuration":20,"manualTime":15,"description":"mo ta cai gi day","trash":false,"status":true,"__v":0}
         */

        @SerializedName("_id")
        private String id;
        @SerializedName("name")
        private String name;
        @SerializedName("description")
        private String description;
        @SerializedName("trash")
        private boolean trash;
        @SerializedName("autoTime")
        private int autoTime;
        @SerializedName("status")
        private boolean status;
        @SerializedName("function")
        private FunctionBean function;

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

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public boolean isTrash() {
            return trash;
        }

        public void setTrash(boolean trash) {
            this.trash = trash;
        }

        public int getAutoTime() {
            return autoTime;
        }

        public void setAutoTime(int autoTime) {
            this.autoTime = autoTime;
        }

        public boolean isStatus() {
            return status;
        }

        public void setStatus(boolean status) {
            this.status = status;
        }

        public FunctionBean getFunction() {
            return function;
        }

        public void setFunction(FunctionBean function) {
            this.function = function;
        }

        public static class FunctionBean {
            /**
             * _id : 5a2129845843b420747a56cd
             * name : van 2
             * actuatorId : 5a042bfb03e2ef31540f30f0
             * activityDuration : 20
             * manualTime : 15
             * description : mo ta cai gi day
             * trash : false
             * status : true
             * __v : 0
             */

            @SerializedName("_id")
            private String id;
            @SerializedName("name")
            private String name;
            @SerializedName("actuatorId")
            private String actuatorId;
            @SerializedName("activityDuration")
            private int activityDuration;
            @SerializedName("manualTime")
            private int manualTime;
            @SerializedName("description")
            private String description;
            @SerializedName("trash")
            private boolean trash;
            @SerializedName("status")
            private boolean status;
            @SerializedName("__v")
            private int v;

            public static FunctionBean objectFromData(String str) {

                return new Gson().fromJson(str, FunctionBean.class);
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

            public int getManualTime() {
                return manualTime;
            }

            public void setManualTime(int manualTime) {
                this.manualTime = manualTime;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public boolean isTrash() {
                return trash;
            }

            public void setTrash(boolean trash) {
                this.trash = trash;
            }

            public boolean isStatus() {
                return status;
            }

            public void setStatus(boolean status) {
                this.status = status;
            }

            public int getV() {
                return v;
            }

            public void setV(int v) {
                this.v = v;
            }
        }
    }
}
