/**
 * GeneIDData.java
 * Copyright 2013 Hao Tong, all rights reserved.
 * PROPRIETARY/CONFIDENTIAL, any form of usage is subject to approval.
 */

package com.ccnt.tcmbio.data;

import java.util.HashSet;
import java.util.Set;

public class GeneIDData {

    private String geneID;

    private Set<String> goID;

    private Set<String> relatedProteinSet;

    private Set<String> relatedTCMSet;

    private Set<String> relatedDiseaseNameSet;

    public String getGeneID() {
        return geneID;
    }

    public void setGeneID(final String geneID) {
        this.geneID = geneID;
    }

    public Set<String> getGoID() {
        return goID;
    }

    public void setGoID(final Set<String> goID) {
        this.goID = goID;
    }

    public Set<String> getRelatedProteinSet() {
        return relatedProteinSet;
    }

    public void setRelatedProteinSet(final Set<String> relatedProteinSet) {
        this.relatedProteinSet = relatedProteinSet;
    }

    public Set<String> getRelatedTCMSet() {
        return relatedTCMSet;
    }

    public void setRelatedTCMSet(final Set<String> relatedTCMSet) {
        this.relatedTCMSet = relatedTCMSet;
    }

    public Set<String> getRelatedDiseaseNameSet() {
        return relatedDiseaseNameSet;
    }

    public void setRelatedDiseaseNameSet(final Set<String> relatedDiseaseNameSet) {
        this.relatedDiseaseNameSet = relatedDiseaseNameSet;
    }

    public GeneIDData() {
        super();
        this.geneID = new String();
        this.goID = new HashSet<String>();
        this.relatedDiseaseNameSet = new HashSet<String>();
        this.relatedProteinSet = new HashSet<String>();
        this.relatedTCMSet = new HashSet<String>();
    }

}
