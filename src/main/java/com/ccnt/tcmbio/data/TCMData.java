/**
 * TCMData.java
 * Copyright 2013 Hao Tong, all rights reserved.
 * PROPRIETARY/CONFIDENTIAL, any form of usage is subject to approval.
 */

package com.ccnt.tcmbio.data;

import java.util.HashSet;
import java.util.Set;

public class TCMData {

    private String tcmName;

    private Set<String> effect;

    private Set<String> ingredient;

    private Set<String> treatment;

    private Set<String> relatedGene;

    public String getTcmName() {
        return tcmName;
    }

    public void setTcmName(final String tcmName) {
        this.tcmName = tcmName;
    }

    public Set<String> getEffect() {
        return effect;
    }

    public void setEffect(final Set<String> effect) {
        this.effect = effect;
    }

    public Set<String> getIngredient() {
        return ingredient;
    }

    public void setIngredient(final Set<String> ingredient) {
        this.ingredient = ingredient;
    }

    public Set<String> getTreatment() {
        return treatment;
    }

    public void setTreatment(final Set<String> treatment) {
        this.treatment = treatment;
    }

    public Set<String> getRelatedGene() {
        return relatedGene;
    }

    public void setRelatedGene(final Set<String> relatedGene) {
        this.relatedGene = relatedGene;
    }

    public TCMData() {
        super();
        this.tcmName = null;
        this.effect = new HashSet<String>();
        this.ingredient = new HashSet<String>();
        this.treatment = new HashSet<String>();
        this.relatedGene = new HashSet<String>();
    }

}
