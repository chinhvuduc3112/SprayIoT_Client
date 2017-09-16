package com.vuduc.models;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by VuDuc on 8/28/2017.
 */

public class AreaResponse {

    /**
     * result : [{"_id":"595c5525ffcb2205acb1a8a5","name":"area2","note":"note2","x":1,"y":2,"__v":0,"trash":false}]
     * status : 1
     */

    @SerializedName("status")
    private int status;
    @SerializedName("result")
    private List<Result> result;

    public static AreaResponse objectFromData(String str) {

        return new Gson().fromJson(str, AreaResponse.class);
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
