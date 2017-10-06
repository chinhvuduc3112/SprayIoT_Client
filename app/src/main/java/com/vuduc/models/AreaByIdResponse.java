package com.vuduc.models;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 10/6/2017.
 */

public class AreaByIdResponse {

    /**
     * result : {"_id":"595c5525ffcb2205acb1a8a5","name":"area2","note":"note2","x":1,"y":2,"__v":0,"trash":false}
     * status : 1
     */

    @SerializedName("result")
    private ResultBean result;
    @SerializedName("status")
    private int status;

    public static AreaByIdResponse objectFromData(String str) {

        return new Gson().fromJson(str, AreaByIdResponse.class);
    }

    public static List<AreaByIdResponse> arrayAreaByIdResponseFromData(String str) {

        Type listType = new TypeToken<ArrayList<AreaByIdResponse>>() {
        }.getType();

        return new Gson().fromJson(str, listType);
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
         * _id : 595c5525ffcb2205acb1a8a5
         * name : area2
         * note : note2
         * x : 1
         * y : 2
         * __v : 0
         * trash : false
         */

        @SerializedName("_id")
        private String id;
        @SerializedName("name")
        private String name;
        @SerializedName("note")
        private String note;
        @SerializedName("x")
        private int x;
        @SerializedName("y")
        private int y;
        @SerializedName("__v")
        private int v;
        @SerializedName("trash")
        private boolean trash;

        public static ResultBean objectFromData(String str) {

            return new Gson().fromJson(str, ResultBean.class);
        }

        public static List<ResultBean> arrayResultBeanFromData(String str) {

            Type listType = new TypeToken<ArrayList<ResultBean>>() {
            }.getType();

            return new Gson().fromJson(str, listType);
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

        public String getNote() {
            return note;
        }

        public void setNote(String note) {
            this.note = note;
        }

        public int getX() {
            return x;
        }

        public void setX(int x) {
            this.x = x;
        }

        public int getY() {
            return y;
        }

        public void setY(int y) {
            this.y = y;
        }

        public int getV() {
            return v;
        }

        public void setV(int v) {
            this.v = v;
        }

        public boolean isTrash() {
            return trash;
        }

        public void setTrash(boolean trash) {
            this.trash = trash;
        }
    }
}
