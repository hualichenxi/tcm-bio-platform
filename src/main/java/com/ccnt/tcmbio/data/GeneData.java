/**
 * GeneData.java
 * Copyright 2013 Hao Tong, all rights reserved.
 * PROPRIETARY/CONFIDENTIAL, any form of usage is subject to approval.
 */

package com.ccnt.tcmbio.data;

import java.util.HashSet;
import java.util.Set;

public class GeneData {

    private String geneID;

    private String geneProduct;

    private String definition;

    private Set<String> synonymSet;

    private String ontology;

    private Set<String> relatedProteinSet;

    private Set<String> relatedTCMSet;

    private Set<String> relatedDiseaseNameSet;

    public String getGeneID() {
        return geneID;
    }

    public void setGeneID(final String geneID) {
        this.geneID = geneID;
    }

    public String getGeneProduct() {
        return geneProduct;
    }

    public void setGeneProduct(final String geneProduct) {
        this.geneProduct = geneProduct;
    }

    public String getDefinition() {
        return definition;
    }

    public void setDefinition(final String definition) {
        this.definition = definition;
    }

    public Set<String> getSynonym() {
        return synonymSet;
    }

    public void setSynonym(final Set<String> synonymSet) {
        this.synonymSet = synonymSet;
    }

    public String getOntology() {
        return ontology;
    }

    public void setOntology(final String ontology) {
        this.ontology = ontology;
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

    public GeneData() {
        super();
        this.geneID = null;
        this.geneProduct = null;
        this.definition = null;
        this.synonymSet = new HashSet<String>();
        this.relatedProteinSet = new HashSet<String>();
        this.relatedTCMSet = new HashSet<String>();
        this.relatedDiseaseNameSet = new HashSet<String>();
    }

}
