/**
 * DiseaseSearchData.java
 * Copyright 2013 Hao Tong, all rights reserved.
 * PROPRIETARY/CONFIDENTIAL, any form of usage is subject to approval.
 */

package com.ccnt.tcmbio.data;

import java.util.ArrayList;

public class DiseaseSearchData {

    private final ArrayList<DiseaseData> diseaseDatas;
    private Integer resultCount;
    private String label;

    public ArrayList<DiseaseData> getDiseaseDatas() {
        return diseaseDatas;
    }

    public void setDiseaseDatas(final ArrayList<DiseaseData> diseaseDatas) {
        this.diseaseDatas.addAll(diseaseDatas);
    }

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

    public DiseaseSearchData() {
        super();
        this.diseaseDatas = new ArrayList<DiseaseData>();
        this.resultCount = 0;
        this.label = null;
    }

}
