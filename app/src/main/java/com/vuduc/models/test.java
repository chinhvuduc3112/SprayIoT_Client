package com.vuduc.models;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

/**
 * Created by VuDuc on 10/16/2017.
 */

public class test {

    /**
     * status : 1
     * data : {"a":1,"b":2}
     */

    @SerializedName("status")
    private int status;
    @SerializedName("data")
    private DataBean data;

    public static test objectFromData(String str) {

        return new Gson().fromJson(str, test.class);
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * a : 1
         * b : 2
         */

        @SerializedName("a")
        private int a;
        @SerializedName("b")
        private int b;

        public static DataBean objectFromData(String str) {

            return new Gson().fromJson(str, DataBean.class);
        }

        public int getA() {
            return a;
        }

        public void setA(int a) {
            this.a = a;
        }

        public int getB() {
            return b;
        }

        public void setB(int b) {
            this.b = b;
        }
    }
}
