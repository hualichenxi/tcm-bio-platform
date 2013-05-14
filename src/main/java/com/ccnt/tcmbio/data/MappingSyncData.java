/**
 * MappingSync.java
 * Copyright 2013 Hao Tong, all rights reserved.
 * PROPRIETARY/CONFIDENTIAL, any form of usage is subject to approval.
 */

package com.ccnt.tcmbio.data;

public class MappingSyncData {

    private Integer status;

    private Integer ontologyPercent;

    private String ontologyPercentF;

    private Integer itemPercent;

    private String itemPercentF;

    private String estimateTime;

    private String passedTime;

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

    public Integer getItemPercent() {
        return itemPercent;
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
