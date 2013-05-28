/**
 * TcmSearchData.java
 * Copyright 2013 Hao Tong, all rights reserved.
 * PROPRIETARY/CONFIDENTIAL, any form of usage is subject to approval.
 */

package com.ccnt.tcmbio.data;

import java.util.ArrayList;

public class TcmInferSearchData {

    private boolean status;

    private ArrayList<TcmInferData> tcmInferData;

    private ArrayList<String> fuzzymatchTCM;

    private Integer totalNum;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(final boolean status) {
        this.status = status;
    }

    public ArrayList<TcmInferData> getTcmInferData() {
        return tcmInferData;
    }

    public void setTcmInferData(final ArrayList<TcmInferData> tcmInferData) {
        this.tcmInferData = tcmInferData;
    }

    public ArrayList<String> getFuzzymatchTCM() {
        return fuzzymatchTCM;
    }

    public void setFuzzymatchTCM(final ArrayList<String> fuzzymatchTCM) {
        this.fuzzymatchTCM = fuzzymatchTCM;
    }

    public Integer getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(final Integer totalNum) {
        this.totalNum = totalNum;
    }

}
