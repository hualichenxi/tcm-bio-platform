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
import com.ccnt.tcmbio.data.DrugSearchData;
import com.ccnt.tcmbio.data.GeneData;
import com.ccnt.tcmbio.data.GeneSearchData;
import com.ccnt.tcmbio.data.TCMData;
import com.ccnt.tcmbio.data.TCMSearchData;
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
    public GeneSearchData searchGene(final String keyword, final String start, final String offset){
        final ArrayList<GeneData> geneDatas = termDAO.searchGene(keyword, start, offset);
        final Integer count = termDAO.searchGeneCount(keyword);
        final GeneSearchData geneSearchData = new GeneSearchData();
        geneSearchData.setGeneDatas(geneDatas);
        geneSearchData.setLabel("Gene ID");
        geneSearchData.setResultCount(count);
        return geneSearchData;
    }

    @Override
    public TCMSearchData searchTCM(final String keyword, final String start, final String offset){
        final ArrayList<TCMData> tcmDatas = termDAO.searchTCM(keyword, start, offset);
        final Integer count = termDAO.searchTcmCount(keyword);
        final TCMSearchData tcmSearchData = new TCMSearchData();
        tcmSearchData.setLabel("TCM");
        tcmSearchData.setResultCount(count);
        tcmSearchData.setTcmDatas(tcmDatas);
        return tcmSearchData;
    }

    @Override
    public DrugSearchData searchDrug(final String keyword, final String start, final String offset){
        final ArrayList<DrugData> drugDatas = termDAO.searchDrug(keyword, start, offset);
        final Integer count = termDAO.searchDrugCount(keyword);
        final DrugSearchData drugSearchData = new DrugSearchData();
        drugSearchData.setDrugDatas(drugDatas);
        drugSearchData.setLabel("Drug");
        drugSearchData.setResultCount(count);
        return drugSearchData;
    }

}
