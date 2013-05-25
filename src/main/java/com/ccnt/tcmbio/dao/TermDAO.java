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
import com.ccnt.tcmbio.data.ProteinData;
import com.ccnt.tcmbio.data.TcmData;

public interface TermDAO {

    public ArrayList<DiseaseData> searchDisease(String keyword, final String start, final String offset);

    public Integer searchDiseaseCount(String keyword);

    public ArrayList<GeneData> searchGene(String keyword, final String start, final String offset);

    public Integer searchGeneCount(String keyword);

    public ArrayList<TcmData> searchTCM(String keyword);

    public ArrayList<ProteinData> searchProtein(String keyword);

    public ArrayList<DrugData> searchDrug(String keyword);

}
