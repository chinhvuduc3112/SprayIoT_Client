package com.vuduc.models;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by VuDuc on 10/23/2017.
 */

public class DeviceTypeResponse {

    /**
     * status : 1
     * result : [{"_id":"599997d3df986f212c94f271","name":"lightSensor","note":"cảm biến ánh sáng","__v":0,"trash":false},{"_id":"599997f4df986f212c94f272","name":"humiSensor","note":"cảm biến độ ẩm","__v":0,"trash":false},{"_id":"599998bbdf986f212c94f273","name":"tempSensor","note":"cảm biến nhiệt độ","__v":0,"trash":false},{"_id":"599998dadf986f212c94f274","name":"airHumiSensor","note":"cảm biến độ ẩm không khí","__v":0,"trash":false}]
     */

    @SerializedName("status")
    private int status;
    @SerializedName("result")
    private List<ResultBean> result;

    public static DeviceTypeResponse objectFromData(String str) {

        return new Gson().fromJson(str, DeviceTypeResponse.class);
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
         * _id : 599997d3df986f212c94f271
         * name : lightSensor
         * note : cảm biến ánh sáng
         * __v : 0
         * trash : false
         */

        @SerializedName("_id")
        private String id;
        @SerializedName("name")
        private String name;
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
