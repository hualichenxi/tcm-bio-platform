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
import com.ccnt.tcmbio.data.GeneIDData;
import com.ccnt.tcmbio.data.GeneIDSearchData;
import com.ccnt.tcmbio.data.GeneSearchData;
import com.ccnt.tcmbio.data.ProteinData;
import com.ccnt.tcmbio.data.ProteinSearchData;
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
        final ArrayList<DiseaseData> datas = termDAO.searchDisease(keyword, start, offset, 0);
        final Integer count = termDAO.searchDiseaseCount(keyword.replace(" ", "_"));
        final DiseaseSearchData diseaseSearchData = new DiseaseSearchData();
        diseaseSearchData.setDiseaseDatas(datas);
        diseaseSearchData.setResultCount(count);
        diseaseSearchData.setLabel("Disease");
        return diseaseSearchData;
    }

    @Override
    public GeneSearchData searchGOID(final String keyword, final String start, final String offset){
        final ArrayList<GeneData> geneDatas = termDAO.searchGOID(keyword, start, offset, 0);
        Integer count = new Integer(0);
        if (geneDatas.size()==1) {
            count = 1;
        } else {
            count = termDAO.searchGOIDCount(keyword);
        }

        final GeneSearchData geneSearchData = new GeneSearchData();
        geneSearchData.setGeneDatas(geneDatas);
        geneSearchData.setLabel("GO ID");
        geneSearchData.setResultCount(count);
        return geneSearchData;
    }

    @Override
    public TCMSearchData searchTCM(final String keyword, final String start, final String offset){
        final ArrayList<TCMData> tcmDatas = termDAO.searchTCM(keyword, start, offset, 0);
        final Integer count = termDAO.searchTcmCount(keyword);
        final TCMSearchData tcmSearchData = new TCMSearchData();
        tcmSearchData.setLabel("TCM");
        tcmSearchData.setResultCount(count);
        tcmSearchData.setTcmDatas(tcmDatas);
        return tcmSearchData;
    }

    @Override
    public DrugSearchData searchDrug(final String keyword, final String start, final String offset){
        final ArrayList<DrugData> drugDatas = termDAO.searchDrug(keyword, start, offset, 0);
        final Integer count = termDAO.searchDrugCount(keyword);
        final DrugSearchData drugSearchData = new DrugSearchData();
        drugSearchData.setDrugDatas(drugDatas);
        drugSearchData.setLabel("Drug");
        drugSearchData.setResultCount(count);
        return drugSearchData;
    }

    @Override
    public GeneIDSearchData searchGeneID(final String keyword, final String start, final String offset){
        final ArrayList<GeneIDData> geneIDDatas = termDAO.searchGeneID(keyword, start, offset, 0);
        Integer count = 0;
        if (geneIDDatas.size() == 1) {
            count = 1;
        } else {
            count = termDAO.searchGeneIDCount(keyword);
        }
        final GeneIDSearchData geneIDSearchData = new GeneIDSearchData();
        geneIDSearchData.setGeneIDDatas(geneIDDatas);
        geneIDSearchData.setResultCount(count);
        geneIDSearchData.setLabel("Gene ID");
        return geneIDSearchData;
    }

    @Override
    public ProteinSearchData searchProteinAC(final String keyword, final String start, final String offset){
        final ArrayList<ProteinData> proteinDatas = termDAO.searchProtein(keyword, start, offset, 0);
        Integer count = 0;
        if (proteinDatas.size() == 1) {
            count = 1;
        } else {
            count = termDAO.searchProteinCount(keyword);
        }
        final ProteinSearchData proteinSearchData = new ProteinSearchData();
        proteinSearchData.setLabel("Protein");
        proteinSearchData.setProteinDatas(proteinDatas);
        proteinSearchData.setResultCount(count);
        return proteinSearchData;
    }

    @Override
    public DiseaseData getDisease(final String keyword){
        final ArrayList<DiseaseData> diseaseDatas = termDAO.searchDisease(keyword.replace(" ", "_"), "", "", 1);
        if (diseaseDatas.isEmpty()) {
            return null;
        } else {
            return diseaseDatas.get(0);
        }
    }

    @Override
    public GeneData getGOID(final String keyword){
        final ArrayList<GeneData> geneDatas = termDAO.searchGOID(keyword.replace("GO_", ""), "", "", 1);
        if (geneDatas.isEmpty()) {
            return null;
        } else {
            return geneDatas.get(0);
        }
    }

    @Override
    public TCMData getTCM(final String keyword){
        final ArrayList<TCMData> tcmDatas = termDAO.searchTCM(keyword, "", "", 1);
        if (tcmDatas.isEmpty()) {
            return null;
        } else {
            return tcmDatas.get(0);
        }
    }

    @Override
    public DrugData getDrug(final String keyword){
        final ArrayList<DrugData> drugDatas = termDAO.searchDrug(keyword, "", "", 1);
        if (drugDatas.isEmpty()) {
            return null;
        } else {
            return drugDatas.get(0);
        }
    }

    @Override
    public GeneIDData getGeneID(final String keyword){
        final ArrayList<GeneIDData> geneIDDatas = termDAO.searchGeneID(keyword, "", "", 1);
        if (geneIDDatas.isEmpty()) {
            return null;
        } else {
            return geneIDDatas.get(0);
        }
    }

    @Override
    public ProteinData getProteinAC(final String keyword){
        final ArrayList<ProteinData> proteinDatas = termDAO.searchProtein(keyword, "", "", 1);
        if (proteinDatas.isEmpty()) {
            return null;
        } else {
            return proteinDatas.get(0);
        }
    }

}
