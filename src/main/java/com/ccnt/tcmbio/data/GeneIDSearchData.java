/**
 * GeneIDSearchData.java
 * Copyright 2013 Hao Tong, all rights reserved.
 * PROPRIETARY/CONFIDENTIAL, any form of usage is subject to approval.
 */

package com.ccnt.tcmbio.data;

import java.util.ArrayList;

public class GeneIDSearchData {

    private ArrayList<GeneIDData> geneIDDatas;

    private Integer resultCount;

    private String label;

    public ArrayList<GeneIDData> getGeneIDDatas() {
        return geneIDDatas;
    }

    public void setGeneIDDatas(final ArrayList<GeneIDData> geneIDDatas) {
        this.geneIDDatas = geneIDDatas;
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

    public GeneIDSearchData() {
        super();
        this.resultCount = 0;
        this.label = null;
        this.geneIDDatas = new ArrayList<GeneIDData>();
    }

}
