/**
 * TCMSearchData.java
 * Copyright 2013 Hao Tong, all rights reserved.
 * PROPRIETARY/CONFIDENTIAL, any form of usage is subject to approval.
 */

package com.ccnt.tcmbio.data;

import java.util.ArrayList;

public class TCMSearchData {

    private Integer resultCount;

    private ArrayList<TCMData> tcmDatas;

    private String label;

    public Integer getResultCount() {
        return resultCount;
    }

    public void setResultCount(final Integer resultCount) {
        this.resultCount = resultCount;
    }

    public ArrayList<TCMData> getTcmDatas() {
        return tcmDatas;
    }

    public void setTcmDatas(final ArrayList<TCMData> tcmDatas) {
        this.tcmDatas = tcmDatas;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(final String label) {
        this.label = label;
    }

    public TCMSearchData() {
        super();
        this.resultCount = 0;
        this.label = null;
        this.tcmDatas = new ArrayList<TCMData>();
    }

}
