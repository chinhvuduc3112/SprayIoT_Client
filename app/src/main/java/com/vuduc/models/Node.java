package com.vuduc.models;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by VuDuc on 8/20/2017.
 */

public class Node {

    /**
     * status : 1
     * result : [{"_id":"598f0a61eff2ef23884d7f7a","name":"node test 1 ","description":"đây là node để test","idArea":"595281eb903d4b1f30750703","note":"đây là node để test","__v":0,"trash":false},{"_id":"598f0b7deff2ef23884d7f7c","name":"node test 2","description":"đây là node để test 2","note":"đây là node để test 2","__v":0,"trash":false},{"_id":"598f0e44eff2ef23884d7f7d","name":"node test 3","description":"đây là node để test 3","note":"đây là node để test 3","__v":0,"trash":false}]
     */

    @SerializedName("status")
    private int status;
    @SerializedName("result")
    private List<ResultBean> result;

    public static Node objectFromData(String str) {

        return new Gson().fromJson(str, Node.class);
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
         * _id : 598f0a61eff2ef23884d7f7a
         * name : node test 1
         * description : đây là node để test
         * idArea : 595281eb903d4b1f30750703
         * note : đây là node để test
         * __v : 0
         * trash : false
         */

        @SerializedName("_id")
        private String id;
        @SerializedName("name")
        private String name;
        @SerializedName("description")
        private String description;
        @SerializedName("idArea")
        private String idArea;
        @SerializedName("note")
        private String note;
        @SerializedName("__v")
        private int v;
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

        public String getIdArea() {
            return idArea;
        }

        public void setIdArea(String idArea) {
            this.idArea = idArea;
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

        public boolean isTrash() {
            return trash;
        }

        public void setTrash(boolean trash) {
            this.trash = trash;
        }
    }
}
