/**
 * GeneSearchData.java
 * Copyright 2013 Hao Tong, all rights reserved.
 * PROPRIETARY/CONFIDENTIAL, any form of usage is subject to approval.
 */

package com.ccnt.tcmbio.data;

import java.util.ArrayList;

public class GeneSearchData {

    private ArrayList<GeneData> geneDatas;

    private Integer resultCount;

    private String label;

    public ArrayList<GeneData> getGeneDatas() {
        return geneDatas;
    }

    public void setGeneDatas(final ArrayList<GeneData> geneDatas) {
        this.geneDatas = geneDatas;
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

    public GeneSearchData() {
        super();
        this.resultCount = 0;
        this.label = null;
        this.geneDatas = new ArrayList<GeneData>();
    }

}
