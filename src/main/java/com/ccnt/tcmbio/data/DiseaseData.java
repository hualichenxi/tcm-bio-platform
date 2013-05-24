/**
 * DiseaseData.java
 * Copyright 2013 Hao Tong, all rights reserved.
 * PROPRIETARY/CONFIDENTIAL, any form of usage is subject to approval.
 */

package com.ccnt.tcmbio.data;

import java.util.HashSet;
import java.util.Set;

public class DiseaseData {

    private String diseaseName;
    private final Set<String> diseaseID;
    private final Set<String> diseaseIDInDrugBank;
    private final Set<String> definition;
    private final Set<String> xrefs;
    private final Set<String> relatedSynonym;
    private final Set<String> relatedDrugID;
    private final Set<String> relatedDrugName;
    private final Set<String> relatedTCM;
    private final Set<String> relatedGene;

    public String getDiseaseName() {
        return diseaseName;
    }

    public void setDiseaseName(final String diseaseName) {
        this.diseaseName = diseaseName;
    }

    public Set<String> getDiseaseID() {
        return diseaseID;
    }

    public void setDiseaseID(final Set<String> diseaseIDInDrugBank) {
        this.diseaseID.addAll(diseaseIDInDrugBank);
    }

    public Set<String> getDiseaseIDInDrugBank() {
        return diseaseIDInDrugBank;
    }

    public void setDiseaseIDInDrugBank(final Set<String> diseaseIDInDrugBank) {
        this.diseaseIDInDrugBank.addAll(diseaseIDInDrugBank);
    }

    public Set<String> getDefinition() {
        return definition;
    }

    public void setDefinition(final Set<String> definition) {
        this.definition.addAll(definition);
    }

    public Set<String> getXrefs() {
        return xrefs;
    }

    public void setXrefs(final Set<String> xrefs) {
        this.xrefs.addAll(xrefs);
    }

    public Set<String> getRelatedSynonym() {
        return relatedSynonym;
    }

    public void setRelatedSynonym(final Set<String> relatedSynonym) {
        this.relatedSynonym.addAll(relatedSynonym);
    }

    public Set<String> getRelatedDrugID() {
        return relatedDrugID;
    }

    public void setRelatedDrugID(final Set<String> relatedDrugID) {
        this.relatedDrugID.addAll(relatedDrugID);
    }

    public Set<String> getRelatedDrugName() {
        return relatedDrugName;
    }

    public void setRelatedDrugName(final Set<String> relatedDrugName) {
        this.relatedDrugName.addAll(relatedDrugName);
    }

    public Set<String> getRelatedTCM() {
        return relatedTCM;
    }

    public void setRelatedTCM(final Set<String> relatedTCM) {
        this.relatedTCM.addAll(relatedTCM);
    }

    public Set<String> getRelatedGene() {
        return relatedGene;
    }

    public void setRelatedGene(final Set<String> relatedGene) {
        this.relatedGene.addAll(relatedGene);
    }

    public DiseaseData() {
        this.diseaseName = new String();
        this.diseaseID = new HashSet<String>();
        this.diseaseIDInDrugBank = new HashSet<String>();
        this.definition = new HashSet<String>();
        this.xrefs = new HashSet<String>();
        this.relatedSynonym = new HashSet<String>();
        this.relatedDrugID = new HashSet<String>();
        this.relatedDrugName = new HashSet<String>();
        this.relatedTCM = new HashSet<String>();
        this.relatedGene = new HashSet<String>();
    }

}
