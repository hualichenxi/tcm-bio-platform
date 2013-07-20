/**
 * TcmSearchData.java
 * Copyright 2013 Hao Tong, all rights reserved.
 * PROPRIETARY/CONFIDENTIAL, any form of usage is subject to approval.
 */

package com.ccnt.tcmbio.data;

import java.util.ArrayList;

public class BioInferSearchData {

    private boolean status;

    private ArrayList<BioInferData> bioInferData;

    private ArrayList<String> fuzzymatchTCM;

    private Integer totalNum;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(final boolean status) {
        this.status = status;
    }

    public ArrayList<BioInferData> getbioInferData() {
        return bioInferData;
    }

    public void setBioInferData(final ArrayList<BioInferData> bioInferData) {
        this.bioInferData = bioInferData;
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
