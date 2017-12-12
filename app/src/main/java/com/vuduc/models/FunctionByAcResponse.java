package com.vuduc.models;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by VuDuc on 11/21/2017.
 */

public class FunctionByAcResponse {

    /**
     * result : [{"_id":"5a21296d5843b420747a56cc","name":"van 1","activityDuration":10,"manualTime":20,"description":"mo ta van 1 test 1","trash":false,"status":true,"actuator":{"_id":"5a032a2f2f331f2bac34ed29","name":"may bom 1","description":"mo ta","deviceTypeId":"59f6ee05978a7d2f4cdb63b1","idArea":"59b8f37091a7e52e3441a9f8","time":10,"status":true,"trash":false,"__v":0}},{"_id":"5a2129845843b420747a56cd","name":"van 2","activityDuration":0,"manualTime":15,"description":"mo ta cai gi day","trash":false,"status":true,"actuator":{"_id":"5a032a2f2f331f2bac34ed29","name":"may bom 1","description":"mo ta","deviceTypeId":"59f6ee05978a7d2f4cdb63b1","idArea":"59b8f37091a7e52e3441a9f8","time":10,"status":true,"trash":false,"__v":0}}]
     * status : 1
     */

    @SerializedName("status")
    private int status;
    @SerializedName("result")
    private List<Result> result;

    public static FunctionByAcResponse objectFromData(String str) {

        return new Gson().fromJson(str, FunctionByAcResponse.class);
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
         * _id : 5a21296d5843b420747a56cc
         * name : van 1
         * activityDuration : 10
         * manualTime : 20
         * description : mo ta van 1 test 1
         * trash : false
         * status : true
         * actuator : {"_id":"5a032a2f2f331f2bac34ed29","name":"may bom 1","description":"mo ta","deviceTypeId":"59f6ee05978a7d2f4cdb63b1","idArea":"59b8f37091a7e52e3441a9f8","time":10,"status":true,"trash":false,"__v":0}
         */

        @SerializedName("_id")
        private String id;
        @SerializedName("name")
        private String name;
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
        @SerializedName("actuator")
        private Actuator actuator;

        public static Result objectFromData(String str) {

            return new Gson().fromJson(str, Result.class);
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

        public Actuator getActuator() {
            return actuator;
        }

        public void setActuator(Actuator actuator) {
            this.actuator = actuator;
        }

        public static class Actuator {
            /**
             * _id : 5a032a2f2f331f2bac34ed29
             * name : may bom 1
             * description : mo ta
             * deviceTypeId : 59f6ee05978a7d2f4cdb63b1
             * idArea : 59b8f37091a7e52e3441a9f8
             * time : 10
             * status : true
             * trash : false
             * __v : 0
             */

            @SerializedName("_id")
            private String id;
            @SerializedName("name")
            private String name;
            @SerializedName("description")
            private String description;
            @SerializedName("deviceTypeId")
            private String deviceTypeId;
            @SerializedName("idArea")
            private String idArea;
            @SerializedName("time")
            private int time;
            @SerializedName("status")
            private boolean status;
            @SerializedName("trash")
            private boolean trash;
            @SerializedName("__v")
            private int v;

            public static Actuator objectFromData(String str) {

                return new Gson().fromJson(str, Actuator.class);
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

            public String getDeviceTypeId() {
                return deviceTypeId;
            }

            public void setDeviceTypeId(String deviceTypeId) {
                this.deviceTypeId = deviceTypeId;
            }

            public String getIdArea() {
                return idArea;
            }

            public void setIdArea(String idArea) {
                this.idArea = idArea;
            }

            public int getTime() {
                return time;
            }

            public void setTime(int time) {
                this.time = time;
            }

            public boolean isStatus() {
                return status;
            }

            public void setStatus(boolean status) {
                this.status = status;
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
        }
    }
}
