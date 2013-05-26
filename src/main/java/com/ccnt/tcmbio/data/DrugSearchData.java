/**
 * DrugSearchData.java
 * Copyright 2013 Hao Tong, all rights reserved.
 * PROPRIETARY/CONFIDENTIAL, any form of usage is subject to approval.
 */

package com.ccnt.tcmbio.data;

import java.util.ArrayList;

public class DrugSearchData {

    private Integer resultCount;

    private String label;

    private ArrayList<DrugData> drugDatas;

    public Integer getResultCount() {
        return resultCount;
    }

    public void setResultCount(final Integer resultCount) {
        this.resultCount = resultCount;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(final String label) {
        this.label = label;
    }

    public ArrayList<DrugData> getDrugDatas() {
        return drugDatas;
    }

    public void setDrugDatas(final ArrayList<DrugData> drugDatas) {
        this.drugDatas = drugDatas;
    }

    public DrugSearchData() {
        super();
        this.label = new String();
        this.resultCount = 0;
        this.drugDatas = new ArrayList<DrugData>();
    }

}
