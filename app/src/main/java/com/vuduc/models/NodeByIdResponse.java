package com.vuduc.models;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

/**
 * Created by admin on 10/16/2017.
 */

public class NodeByIdResponse {

    /**
     * result : {"_id":"598f0b7deff2ef23884d7f7c","name":"post man 1","description":"mo ta 1","note":"ghi chu 1","__v":0,"idArea":"595c5525ffcb2205acb1a8a5","trash":false}
     * status : 1
     */

    @SerializedName("result")
    private ResultBean result;
    @SerializedName("status")
    private int status;

    public static NodeByIdResponse objectFromData(String str) {

        return new Gson().fromJson(str, NodeByIdResponse.class);
    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public static class ResultBean {
        /**
         * _id : 598f0b7deff2ef23884d7f7c
         * name : post man 1
         * description : mo ta 1
         * note : ghi chu 1
         * __v : 0
         * idArea : 595c5525ffcb2205acb1a8a5
         * trash : false
         */

        @SerializedName("_id")
        private String id;
        @SerializedName("name")
        private String name;
        @SerializedName("description")
        private String description;
        @SerializedName("note")
        private String note;
        @SerializedName("__v")
        private int v;
        @SerializedName("idArea")
        private String idArea;
        @SerializedName("trash")
        private boolean trash;

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

        public String getNote() {
            return note;
        }

        public void setNote(String note) {
            this.note = note;
        }

        public int getV() {
            return v;
        }

        public void setV(int v) {
            this.v = v;
        }

        public String getIdArea() {
            return idArea;
        }

        public void setIdArea(String idArea) {
            this.idArea = idArea;
        }

        public boolean isTrash() {
            return trash;
        }

        public void setTrash(boolean trash) {
            this.trash = trash;
        }
    }
}
