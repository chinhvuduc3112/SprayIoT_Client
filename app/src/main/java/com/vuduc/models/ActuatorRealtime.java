package com.vuduc.models;

/**
 * Created by admin on 7/8/2017.
 */

public class ActuatorRealtime {
    String actuatorName;
    Boolean status;

    public ActuatorRealtime() {
    }

    public ActuatorRealtime(String actuatorName, Boolean status) {
        this.actuatorName = actuatorName;
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
}
