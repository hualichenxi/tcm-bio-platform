/**
 * ProteinData.java
 * Copyright 2013 Hao Tong, all rights reserved.
 * PROPRIETARY/CONFIDENTIAL, any form of usage is subject to approval.
 */

package com.ccnt.tcmbio.data;

import java.util.HashSet;
import java.util.Set;

public class ProteinData {

    private String proteinAC;

    private Set<String> relatedGOID;

    private Set<String> relatedGeneID;

    public String getProteinAC() {
        return proteinAC;
    }

    public void setProteinAC(final String proteinAC) {
        this.proteinAC = proteinAC;
    }

    public Set<String> getRelatedGOID() {
        return relatedGOID;
    }

    public void setRelatedGOID(final Set<String> relatedGOID) {
        this.relatedGOID = relatedGOID;
    }

    public Set<String> getRelatedGeneID() {
        return relatedGeneID;
    }

    public void setRelatedGeneID(final Set<String> relatedGeneID) {
        this.relatedGeneID = relatedGeneID;
    }

    public ProteinData() {
        super();
        this.proteinAC = new String();
        this.relatedGeneID = new HashSet<String>();
        this.relatedGOID = new HashSet<String>();
    }
}
