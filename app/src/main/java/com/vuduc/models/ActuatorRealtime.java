package com.vuduc.models;

/**
 * Created by admin on 7/8/2017.
 */

public class ActuatorRealtime {
    String actuatorName;
    Long time;
    Boolean status;

    public ActuatorRealtime() {
    }

    public ActuatorRealtime(String actuatorName, Long time, Boolean status) {
        this.actuatorName = actuatorName;
        this.time = time;
        this.status = status;
    }

    public String getActuatorName() {
        return actuatorName;
    }

    public void setActuatorName(String actuatorName) {
        this.actuatorName = actuatorName;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }
}
