package com.vuduc.models;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by admin on 11/3/2017.
 */

public class ActuatorsResponse {

    /**
     * result : [{"_id":"59f9756f8620bb2d485d4a62","name":"Máy bơm loại 1","description":"Mô tả","deviceTypeId":"59f974288620bb2d485d4a61","idArea":"59b90cefd81ce726c8bab473","time":0,"status":false,"trash":false,"__v":0},{"_id":"59f975888620bb2d485d4a63","name":"Máy bơm test 1","description":"Mô tả","deviceTypeId":"59f974288620bb2d485d4a61","idArea":null,"time":0,"status":false,"trash":false,"__v":0},{"_id":"59f97fa5e05ece26e44f3787","name":"Máy bơm test 2","description":"Mô tả","deviceTypeId":"59f974288620bb2d485d4a61","idArea":"59b8f37091a7e52e3441a9f8","time":0,"status":false,"trash":false,"__v":0}]
     * status : 1
     */

    @SerializedName("status")
    private int status;
    @SerializedName("result")
    private List<ResultBean> result;

    public static ActuatorsResponse objectFromData(String str) {

        return new Gson().fromJson(str, ActuatorsResponse.class);
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
         * _id : 59f9756f8620bb2d485d4a62
         * name : Máy bơm loại 1
         * description : Mô tả
         * deviceTypeId : 59f974288620bb2d485d4a61
         * idArea : 59b90cefd81ce726c8bab473
         * time : 0
         * status : false
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
