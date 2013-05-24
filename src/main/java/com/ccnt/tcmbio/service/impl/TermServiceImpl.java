/**
 * TermServiceImpl.java
 * Copyright 2013 Hao Tong, all rights reserved.
 * PROPRIETARY/CONFIDENTIAL, any form of usage is subject to approval.
 */

package com.ccnt.tcmbio.service.impl;

import java.util.ArrayList;

import com.ccnt.tcmbio.dao.TermDAO;
import com.ccnt.tcmbio.data.DiseaseData;
import com.ccnt.tcmbio.data.DrugData;
import com.ccnt.tcmbio.data.GeneData;
import com.ccnt.tcmbio.data.ProteinData;
import com.ccnt.tcmbio.data.TcmData;
import com.ccnt.tcmbio.service.TermService;

public class TermServiceImpl implements TermService{

    private TermDAO termDAO;

    public void setTermDAO(final TermDAO termDAO) {
        this.termDAO = termDAO;
    }

    @Override
    public ArrayList<DiseaseData> searchDisease(final String keyword){
        return termDAO.searchDisease(keyword);
    }

    @Override
    public GeneData searchGene(final String keyword){
        return null;
    }

    @Override
    public ArrayList<TcmData> searchTCM(final String keyword){
        return null;
    }

    @Override
    public ArrayList<ProteinData> searchProtein(final String keyword){
        return null;
    }

    @Override
    public ArrayList<DrugData> searchDrug(final String keyword){
        return null;
    }

}
