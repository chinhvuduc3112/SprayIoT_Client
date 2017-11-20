package com.vuduc.models;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by VuDuc on 11/6/2017.
 */

public class ActuatorInfosResponse {

    /**
     * result : [{"_id":"5a003e440f889f2bbc066759","name":"ahsicbo","description":"aioscboiasbc","time":0,"status":false,"trash":false,"deviceType":{"_id":"599997d3df986f212c94f271","name":"lightSensor","note":"cảm biến ánh sáng","trash":false,"__v":0},"area":{"_id":"59b8f37091a7e52e3441a9f8","name":"area test 1","note":"day la node","x":1,"y":2,"trash":false,"__v":0}}]
     * status : 1
     */

    @SerializedName("status")
    private int status;
    @SerializedName("result")
    private List<Result> result;

    public static ActuatorInfosResponse objectFromData(String str) {

        return new Gson().fromJson(str, ActuatorInfosResponse.class);
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
         * _id : 5a003e440f889f2bbc066759
         * name : ahsicbo
         * description : aioscboiasbc
         * time : 0
         * status : false
         * trash : false
         * deviceType : {"_id":"599997d3df986f212c94f271","name":"lightSensor","note":"cảm biến ánh sáng","trash":false,"__v":0}
         * area : {"_id":"59b8f37091a7e52e3441a9f8","name":"area test 1","note":"day la node","x":1,"y":2,"trash":false,"__v":0}
         */

        @SerializedName("_id")
        private String id;
        @SerializedName("name")
        private String name;
        @SerializedName("description")
        private String description;
        @SerializedName("time")
        private Long time;
        @SerializedName("status")
        private boolean status;
        @SerializedName("trash")
        private boolean trash;
        @SerializedName("deviceType")
        private DeviceType deviceType;
        @SerializedName("area")
        private Area area;

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

        //TODO: Cast INT to LONG
        public long getTime() {
            return time;
        }
        //TODO: Cast INT to LONG
        public void setTime(long time) {
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

        public DeviceType getDeviceType() {
            return deviceType;
        }

        public void setDeviceType(DeviceType deviceType) {
            this.deviceType = deviceType;
        }

        public Area getArea() {
            return area;
        }

        public void setArea(Area area) {
            this.area = area;
        }

        public static class DeviceType {
            /**
             * _id : 599997d3df986f212c94f271
             * name : lightSensor
             * note : cảm biến ánh sáng
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

        public static class Area {
            /**
             * _id : 59b8f37091a7e52e3441a9f8
             * name : area test 1
             * note : day la node
             * x : 1
             * y : 2
             * trash : false
             * __v : 0
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
            @SerializedName("trash")
            private boolean trash;
            @SerializedName("__v")
            private int v;

            public static Area objectFromData(String str) {

                return new Gson().fromJson(str, Area.class);
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
