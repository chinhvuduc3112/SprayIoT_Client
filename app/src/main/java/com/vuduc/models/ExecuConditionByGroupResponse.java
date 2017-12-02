package com.vuduc.models;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by VuDuc on 11/25/2017.
 */

public class ExecuConditionByGroupResponse {

    /**
     * result : [{"_id":"5a03305d4d131a08306a0769","name":"dieu kien anh sang","description":"mo ta dieu kien 1","compare":1,"compareValue":80,"trash":false,"groupExecutionCondition":{"_id":"5a032c13cab58f288cac10a8","name":"group dieu kien 1","description":"mo ta 1","functionId":"5a032bcdcab58f288cac10a7","trash":false,"__v":0},"deviceNode":{"_id":"59e71209913a6308905af180","name":"Lux416303ff","description":"Cảm biến ánh sáng NodeIoT 1 ","deviceTypeId":"599997d3df986f212c94f271","nodeId":"59e70c39913a6308905af17a","note":"ápnca","trash":false,"data":60,"__v":0}},{"_id":"5a0330704d131a08306a076a","name":"dieu kien anh sang 2","description":"mo ta dieu kien 2","compare":2,"compareValue":40,"trash":false,"groupExecutionCondition":{"_id":"5a032c13cab58f288cac10a8","name":"group dieu kien 1","description":"mo ta 1","functionId":"5a032bcdcab58f288cac10a7","trash":false,"__v":0},"deviceNode":{"_id":"59e71209913a6308905af180","name":"Lux416303ff","description":"Cảm biến ánh sáng NodeIoT 1 ","deviceTypeId":"599997d3df986f212c94f271","nodeId":"59e70c39913a6308905af17a","note":"ápnca","trash":false,"data":60,"__v":0}}]
     * status : 1
     */

    @SerializedName("status")
    private int status;
    @SerializedName("result")
    private List<ResultBean> result;

    public static ExecuConditionByGroupResponse objectFromData(String str) {

        return new Gson().fromJson(str, ExecuConditionByGroupResponse.class);
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
         * _id : 5a03305d4d131a08306a0769
         * name : dieu kien anh sang
         * description : mo ta dieu kien 1
         * compare : 1
         * compareValue : 80
         * trash : false
         * groupExecutionCondition : {"_id":"5a032c13cab58f288cac10a8","name":"group dieu kien 1","description":"mo ta 1","functionId":"5a032bcdcab58f288cac10a7","trash":false,"__v":0}
         * deviceNode : {"_id":"59e71209913a6308905af180","name":"Lux416303ff","description":"Cảm biến ánh sáng NodeIoT 1 ","deviceTypeId":"599997d3df986f212c94f271","nodeId":"59e70c39913a6308905af17a","note":"ápnca","trash":false,"data":60,"__v":0}
         */

        @SerializedName("_id")
        private String id;
        @SerializedName("name")
        private String name;
        @SerializedName("description")
        private String description;
        @SerializedName("compare")
        private int compare;
        @SerializedName("compareValue")
        private int compareValue;
        @SerializedName("trash")
        private boolean trash;
        @SerializedName("groupExecutionCondition")
        private GroupExecutionConditionBean groupExecutionCondition;
        @SerializedName("deviceNode")
        private DeviceNodeBean deviceNode;

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

        public int getCompare() {
            return compare;
        }

        public void setCompare(int compare) {
            this.compare = compare;
        }

        public int getCompareValue() {
            return compareValue;
        }

        public void setCompareValue(int compareValue) {
            this.compareValue = compareValue;
        }

        public boolean isTrash() {
            return trash;
        }

        public void setTrash(boolean trash) {
            this.trash = trash;
        }

        public GroupExecutionConditionBean getGroupExecutionCondition() {
            return groupExecutionCondition;
        }

        public void setGroupExecutionCondition(GroupExecutionConditionBean groupExecutionCondition) {
            this.groupExecutionCondition = groupExecutionCondition;
        }

        public DeviceNodeBean getDeviceNode() {
            return deviceNode;
        }

        public void setDeviceNode(DeviceNodeBean deviceNode) {
            this.deviceNode = deviceNode;
        }

        public static class GroupExecutionConditionBean {
            /**
             * _id : 5a032c13cab58f288cac10a8
             * name : group dieu kien 1
             * description : mo ta 1
             * functionId : 5a032bcdcab58f288cac10a7
             * trash : false
             * __v : 0
             */

            @SerializedName("_id")
            private String id;
            @SerializedName("name")
            private String name;
            @SerializedName("description")
            private String description;
            @SerializedName("functionId")
            private String functionId;
            @SerializedName("trash")
            private boolean trash;
            @SerializedName("__v")
            private int v;

            public static GroupExecutionConditionBean objectFromData(String str) {

                return new Gson().fromJson(str, GroupExecutionConditionBean.class);
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

            public String getFunctionId() {
                return functionId;
            }

            public void setFunctionId(String functionId) {
                this.functionId = functionId;
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

        public static class DeviceNodeBean {
            /**
             * _id : 59e71209913a6308905af180
             * name : Lux416303ff
             * description : Cảm biến ánh sáng NodeIoT 1
             * deviceTypeId : 599997d3df986f212c94f271
             * nodeId : 59e70c39913a6308905af17a
             * note : ápnca
             * trash : false
             * data : 60
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
            @SerializedName("nodeId")
            private String nodeId;
            @SerializedName("note")
            private String note;
            @SerializedName("trash")
            private boolean trash;
            @SerializedName("data")
            private int data;
            @SerializedName("__v")
            private int v;

            public static DeviceNodeBean objectFromData(String str) {

                return new Gson().fromJson(str, DeviceNodeBean.class);
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

            public int getV() {
                return v;
            }

            public void setV(int v) {
                this.v = v;
            }
        }
    }
}
