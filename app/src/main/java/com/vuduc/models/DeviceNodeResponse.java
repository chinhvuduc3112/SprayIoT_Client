package com.vuduc.models;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by VuDuc on 8/20/2017.
 */

public class DeviceNodeResponse {

    /**
     * result : [{"_id":"59e71176913a6308905af17d","name":"Temp416303ff","description":"Cảm biến nhiệt độ NodeIoT 1","nodeId":"59e70c39913a6308905af17a","note":"","trash":false,"data":27,"deviceType":{"_id":"599998bbdf986f212c94f273","name":"tempSensor","note":"cảm biến nhiệt độ","trash":false,"__v":0}},{"_id":"59e711a7913a6308905af17e","name":"Hum416303ff","description":"Cảm biến độ ẩm NodeIoT 1","nodeId":"59e70c39913a6308905af17a","note":"","trash":false,"data":50,"deviceType":{"_id":"599997f4df986f212c94f272","name":"humiSensor","note":"cảm biến độ ẩm","trash":false,"__v":0}},{"_id":"59e711e5913a6308905af17f","name":"Shum416303ff","description":"Cảm biến độ ẩm đất NodeIoT 1","nodeId":"59e70c39913a6308905af17a","note":"","trash":false,"data":9,"deviceType":{"_id":"599998dadf986f212c94f274","name":"airHumiSensor","note":"cảm biến độ ẩm không khí","trash":false,"__v":0}},{"_id":"59e71209913a6308905af180","name":"Lux416303ff","description":"Cảm biến ánh sáng NodeIoT 1 ","nodeId":"59e70c39913a6308905af17a","note":"ápnca","trash":false,"data":500,"deviceType":{"_id":"599997d3df986f212c94f271","name":"lightSensor","note":"cảm biến ánh sáng","trash":false,"__v":0}},{"_id":"5a2258ce633ac5119460b041","name":"Rain416303ff","description":"cảm biến mưa NodeIoT 1","nodeId":"59e70c39913a6308905af17a","note":"â","trash":false,"data":0,"deviceType":{"_id":"5a192f594ee59c0e8007a65d","name":"rainSensor","note":"Cảm biến mưa","trash":false,"__v":0}}]
     * status : 1
     */

    @SerializedName("status")
    private int status;
    @SerializedName("result")
    private List<Result> result;

    public static DeviceNodeResponse objectFromData(String str) {

        return new Gson().fromJson(str, DeviceNodeResponse.class);
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
         * _id : 59e71176913a6308905af17d
         * name : Temp416303ff
         * description : Cảm biến nhiệt độ NodeIoT 1
         * nodeId : 59e70c39913a6308905af17a
         * note :
         * trash : false
         * data : 27
         * deviceType : {"_id":"599998bbdf986f212c94f273","name":"tempSensor","note":"cảm biến nhiệt độ","trash":false,"__v":0}
         */

        @SerializedName("_id")
        private String id;
        @SerializedName("name")
        private String name;
        @SerializedName("description")
        private String description;
        @SerializedName("nodeId")
        private String nodeId;
        @SerializedName("note")
        private String note;
        @SerializedName("trash")
        private boolean trash;
        @SerializedName("data")
        private int data;
        @SerializedName("deviceType")
        private DeviceType deviceType;

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

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getNodeId() {
            return nodeId;
        }

        public void setNodeId(String nodeId) {
            this.nodeId = nodeId;
        }

        public String getNote() {
            return note;
        }

        public void setNote(String note) {
            this.note = note;
        }

        public boolean isTrash() {
            return trash;
        }

        public void setTrash(boolean trash) {
            this.trash = trash;
        }

        public int getData() {
            return data;
        }

        public void setData(int data) {
            this.data = data;
        }

        public DeviceType getDeviceType() {
            return deviceType;
        }

        public void setDeviceType(DeviceType deviceType) {
            this.deviceType = deviceType;
        }

        public static class DeviceType {
            /**
             * _id : 599998bbdf986f212c94f273
             * name : tempSensor
             * note : cảm biến nhiệt độ
             * trash : false
             * __v : 0
             */

            @SerializedName("_id")
            private String id;
            @SerializedName("name")
            private String name;
            @SerializedName("note")
            private String note;
            @SerializedName("trash")
            private boolean trash;
            @SerializedName("__v")
            private int v;

            public static DeviceType objectFromData(String str) {

                return new Gson().fromJson(str, DeviceType.class);
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
