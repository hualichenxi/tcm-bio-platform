/**
 * TermServiceImpl.java
 * Copyright 2013 Hao Tong, all rights reserved.
 * PROPRIETARY/CONFIDENTIAL, any form of usage is subject to approval.
 */

package com.ccnt.tcmbio.service.impl;

import java.util.ArrayList;

import com.ccnt.tcmbio.dao.TermDAO;
import com.ccnt.tcmbio.data.DiseaseData;
import com.ccnt.tcmbio.data.DiseaseSearchData;
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
    public DiseaseSearchData searchDisease(final String keyword, final String start, final String offset){
        final ArrayList<DiseaseData> datas = termDAO.searchDisease(keyword, start, offset);
        final Integer count = termDAO.searchDiseaseCount(keyword);
        final DiseaseSearchData diseaseSearchData = new DiseaseSearchData();
        diseaseSearchData.setDiseaseDatas(datas);
        diseaseSearchData.setResultCount(count);
        diseaseSearchData.setLabel("Disease");
        return diseaseSearchData;
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
