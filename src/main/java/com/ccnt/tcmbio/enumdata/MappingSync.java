/**
 * MappingSync.java
 * Copyright 2013 Hao Tong, all rights reserved.
 * PROPRIETARY/CONFIDENTIAL, any form of usage is subject to approval.
 */

package com.ccnt.tcmbio.enumdata;

public enum MappingSync {
    INSTANCE;

    private Integer status = 0;

    private Integer ontologyPercent = 0;

    private String ontologyPercentF = "0/0";

    private Integer itemPercent = 0;

    private String itemPercentF = "0/0";

    private String estimateTime = "00:00:00";

    private String passedTime = "00:00:00";

    public Integer getStatus() {
        return status;
    }

    public void setStatus(final Integer status) {
        this.status = status;
    }

    public Integer getOntologyPercent() {
        return ontologyPercent;
    }

    public void setOntologyPercent(final Integer ontologyPercent) {
        this.ontologyPercent = ontologyPercent;
    }

    public String getOntologyPercentF() {
        return ontologyPercentF;
    }

    public void setOntologyPercentF(final String ontologyPercentF) {
        this.ontologyPercentF = ontologyPercentF;
    }

    public String getItemPercentF() {
        return itemPercentF;
    }

    public void setItemPercentF(final String itemPercentF) {
        this.itemPercentF = itemPercentF;
    }

    public Integer getItemPercent() {
        return itemPercent;
    }

    public void setItemPercent(final Integer itemPercent) {
        this.itemPercent = itemPercent;
    }

    public String getEstimateTime() {
        return estimateTime;
    }

    public void setEstimateTime(final String estimateTime) {
        this.estimateTime = estimateTime;
    }

    public String getPassedTime() {
        return passedTime;
    }

    public void setPassedTime(final String passedTime) {
        this.passedTime = passedTime;
    }

}
