package com.vuduc.models;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by VuDuc on 12/18/2017.
 */

public class ManualUpdateFunction {

    /**
     * status : 1
     * result : {"name":"may bom 1","_id":"5a032a2f2f331f2bac34ed29","status":true,"time":5,"functions":[{"_id":"5a21296d5843b420747a56cc","name":"van 1","actuatorId":"5a032a2f2f331f2bac34ed29","description":"mo ta van 1 test 1","trash":false,"__v":0,"manualTime":0,"activityDuration":5,"status":true}]}
     */

    @SerializedName("status")
    private int status;
    @SerializedName("result")
    private ResultBean result;

    public static ManualUpdateFunction objectFromData(String str) {

        return new Gson().fromJson(str, ManualUpdateFunction.class);
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * name : may bom 1
         * _id : 5a032a2f2f331f2bac34ed29
         * status : true
         * time : 5
         * functions : [{"_id":"5a21296d5843b420747a56cc","name":"van 1","actuatorId":"5a032a2f2f331f2bac34ed29","description":"mo ta van 1 test 1","trash":false,"__v":0,"manualTime":0,"activityDuration":5,"status":true}]
         */

        @SerializedName("name")
        private String name;
        @SerializedName("_id")
        private String id;
        @SerializedName("status")
        private boolean status;
        @SerializedName("time")
        private int time;
        @SerializedName("functions")
        private List<FunctionsBean> functions;

        public static ResultBean objectFromData(String str) {

            return new Gson().fromJson(str, ResultBean.class);
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public boolean isStatus() {
            return status;
        }

        public void setStatus(boolean status) {
            this.status = status;
        }

        public int getTime() {
            return time;
        }

        public void setTime(int time) {
            this.time = time;
        }

        public List<FunctionsBean> getFunctions() {
            return functions;
        }

        public void setFunctions(List<FunctionsBean> functions) {
            this.functions = functions;
        }

        public static class FunctionsBean {
            /**
             * _id : 5a21296d5843b420747a56cc
             * name : van 1
             * actuatorId : 5a032a2f2f331f2bac34ed29
             * description : mo ta van 1 test 1
             * trash : false
             * __v : 0
             * manualTime : 0
             * activityDuration : 5
             * status : true
             */

            @SerializedName("_id")
            private String id;
            @SerializedName("name")
            private String name;
            @SerializedName("actuatorId")
            private String actuatorId;
            @SerializedName("description")
            private String description;
            @SerializedName("trash")
            private boolean trash;
            @SerializedName("__v")
            private int v;
            @SerializedName("manualTime")
            private int manualTime;
            @SerializedName("activityDuration")
            private int activityDuration;
            @SerializedName("status")
            private boolean status;

            public static FunctionsBean objectFromData(String str) {

                return new Gson().fromJson(str, FunctionsBean.class);
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

            public int getV() {
                return v;
            }

            public void setV(int v) {
                this.v = v;
            }

            public int getManualTime() {
                return manualTime;
            }

            public void setManualTime(int manualTime) {
                this.manualTime = manualTime;
            }

            public int getActivityDuration() {
                return activityDuration;
            }

            public void setActivityDuration(int activityDuration) {
                this.activityDuration = activityDuration;
            }

            public boolean isStatus() {
                return status;
            }

            public void setStatus(boolean status) {
                this.status = status;
            }
        }
    }
}
