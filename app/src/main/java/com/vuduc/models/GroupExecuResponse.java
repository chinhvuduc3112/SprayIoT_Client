package com.vuduc.models;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by VuDuc on 11/23/2017.
 */

public class GroupExecuResponse {

    /**
     * result : [{"_id":"5a032c13cab58f288cac10a8","name":"group dieu kien 1","description":"mo ta 1","trash":false,"function":{"_id":"5a032bcdcab58f288cac10a7","name":"van 1","actuatorId":"5a032a2f2f331f2bac34ed29","activityDuration":10,"trash":false,"status":false,"__v":0}}]
     * status : 1
     */

    @SerializedName("status")
    private int status;
    @SerializedName("result")
    private List<ResultBean> result;

    public static GroupExecuResponse objectFromData(String str) {

        return new Gson().fromJson(str, GroupExecuResponse.class);
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
         * _id : 5a032c13cab58f288cac10a8
         * name : group dieu kien 1
         * description : mo ta 1
         * trash : false
         * function : {"_id":"5a032bcdcab58f288cac10a7","name":"van 1","actuatorId":"5a032a2f2f331f2bac34ed29","activityDuration":10,"trash":false,"status":false,"__v":0}
         */

        @SerializedName("_id")
        private String id;
        @SerializedName("name")
        private String name;
        @SerializedName("description")
        private String description;
        @SerializedName("trash")
        private boolean trash;
        @SerializedName("function")
        private FunctionBean function;

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

        public boolean isTrash() {
            return trash;
        }

        public void setTrash(boolean trash) {
            this.trash = trash;
        }

        public FunctionBean getFunction() {
            return function;
        }

        public void setFunction(FunctionBean function) {
            this.function = function;
        }

        public static class FunctionBean {
            /**
             * _id : 5a032bcdcab58f288cac10a7
             * name : van 1
             * actuatorId : 5a032a2f2f331f2bac34ed29
             * activityDuration : 10
             * trash : false
             * status : false
             * __v : 0
             */

            @SerializedName("_id")
            private String id;
            @SerializedName("name")
            private String name;
            @SerializedName("actuatorId")
            private String actuatorId;
            @SerializedName("activityDuration")
            private int activityDuration;
            @SerializedName("trash")
            private boolean trash;
            @SerializedName("status")
            private boolean status;
            @SerializedName("__v")
            private int v;

            public static FunctionBean objectFromData(String str) {

                return new Gson().fromJson(str, FunctionBean.class);
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

            public int getActivityDuration() {
                return activityDuration;
            }

            public void setActivityDuration(int activityDuration) {
                this.activityDuration = activityDuration;
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

            public int getV() {
                return v;
            }

            public void setV(int v) {
                this.v = v;
            }
        }
    }
}
