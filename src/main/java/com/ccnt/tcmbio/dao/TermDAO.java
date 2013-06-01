/**
 * TermDAO.java
 * Copyright 2013 Hao Tong, all rights reserved.
 * PROPRIETARY/CONFIDENTIAL, any form of usage is subject to approval.
 */

package com.ccnt.tcmbio.dao;

import java.util.ArrayList;

import com.ccnt.tcmbio.data.DiseaseData;
import com.ccnt.tcmbio.data.DrugData;
import com.ccnt.tcmbio.data.GeneData;
import com.ccnt.tcmbio.data.GeneIDData;
import com.ccnt.tcmbio.data.ProteinData;
import com.ccnt.tcmbio.data.TCMData;

public interface TermDAO {

    /**
     *
     * @param keyword
     * @param start
     * @param offset
     * @param type search type: 0 for fuzzy&exact search; 1 for exact search; 2 for fuzzy search
     * @return
     */
    public ArrayList<DiseaseData> searchDisease(String keyword, final String start, final String offset, final int type);

    public Integer searchDiseaseCount(String keyword);

    public ArrayList<GeneData> searchGOID(String keyword, final String start, final String offset, final int type);

    public Integer searchGOIDCount(String keyword);

    public ArrayList<TCMData> searchTCM(String keyword, final String start, final String offset, final int type);

    public Integer searchTcmCount(String keyword);

    public ArrayList<DrugData> searchDrug(String keyword, final String start, final String offset, final int type);

    public Integer searchDrugCount(String keyword);

    public ArrayList<GeneIDData> searchGeneID(String keyword, final String start, final String offset, final int type);

    public Integer searchGeneIDCount(String keyword);

    public ArrayList<ProteinData> searchProtein(String keyword, final String start, final String offset, final int type);

    public Integer searchProteinCount(String keyword);

}
