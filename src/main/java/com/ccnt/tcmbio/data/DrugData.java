/**
 * DrugData.java
 * Copyright 2013 Hao Tong, all rights reserved.
 * PROPRIETARY/CONFIDENTIAL, any form of usage is subject to approval.
 */

package com.ccnt.tcmbio.data;

import java.util.HashSet;
import java.util.Set;

public class DrugData {

    private String drugName;

    private String drugID;

    private String state;

    private Set<String> brandName;

    private String description;

    private Set<String> drugCategory;

    private String mechanismOfAction;

    private Set<String> pages;

    private String affectedPrganism;

    private Set<String> diseaseTarget;

    public String getDrugName() {
        return drugName;
    }

    public void setDrugName(final String drugName) {
        this.drugName = drugName;
    }

    public String getDrugID() {
        return drugID;
    }

    public void setDrugID(final String drugID) {
        this.drugID = drugID;
    }

    public String getState() {
        return state;
    }

    public void setState(final String state) {
        this.state = state;
    }

    public Set<String> getBrandName() {
        return brandName;
    }

    public void setBrandName(final Set<String> brandName) {
        this.brandName = brandName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public Set<String> getDrugCategory() {
        return drugCategory;
    }

    public void setDrugCategory(final Set<String> drugCategory) {
        this.drugCategory = drugCategory;
    }

    public String getMechanismOfAction() {
        return mechanismOfAction;
    }

    public void setMechanismOfAction(final String mechanismOfAction) {
        this.mechanismOfAction = mechanismOfAction;
    }

    public Set<String> getPages() {
        return pages;
    }

    public void setPages(final Set<String> pages) {
        this.pages = pages;
    }

    public String getAffectedPrganism() {
        return affectedPrganism;
    }

    public void setAffectedPrganism(final String affectedPrganism) {
        this.affectedPrganism = affectedPrganism;
    }

    public Set<String> getDiseaseTarget() {
        return diseaseTarget;
    }

    public void setDiseaseTarget(final Set<String> diseaseTarget) {
        this.diseaseTarget = diseaseTarget;
    }

    public DrugData() {
        super();
        this.drugName = new String();
        this.affectedPrganism = new String();
        this.brandName = new HashSet<String>();
        this.description = new String();
        this.diseaseTarget = new HashSet<String>();
        this.drugCategory = new HashSet<String>();
        this.drugID = new String();
        this.mechanismOfAction = new String();
        this.pages = new HashSet<String>();
        this.state = new String();
    }

}
