/**
 * ProteinSearchData.java
 * Copyright 2013 Hao Tong, all rights reserved.
 * PROPRIETARY/CONFIDENTIAL, any form of usage is subject to approval.
 */

package com.ccnt.tcmbio.data;

import java.util.ArrayList;

public class ProteinSearchData {

    private Integer resultCount;

    private ArrayList<ProteinData> proteinDatas;

    private String label;

    public Integer getResultCount() {
        return resultCount;
    }

    public void setResultCount(final Integer resultCount) {
        this.resultCount = resultCount;
    }

    public ArrayList<ProteinData> getProteinDatas() {
        return proteinDatas;
    }

    public void setProteinDatas(final ArrayList<ProteinData> proteinDatas) {
        this.proteinDatas = proteinDatas;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(final String label) {
        this.label = label;
    }

    public ProteinSearchData() {
        super();
        this.label = new String();
        this.resultCount = new Integer(0);
        this.proteinDatas = new ArrayList<ProteinData>();
    }

}
