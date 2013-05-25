/**
 * TermService.java
 * Copyright 2013 Hao Tong, all rights reserved.
 * PROPRIETARY/CONFIDENTIAL, any form of usage is subject to approval.
 */

package com.ccnt.tcmbio.service;

import java.util.ArrayList;

import com.ccnt.tcmbio.data.DiseaseSearchData;
import com.ccnt.tcmbio.data.DrugData;
import com.ccnt.tcmbio.data.GeneData;
import com.ccnt.tcmbio.data.ProteinData;
import com.ccnt.tcmbio.data.TcmData;

public interface TermService {

    public DiseaseSearchData searchDisease(String keyword, String start, String offset);

    public GeneData searchGene(String keyword);

    public ArrayList<TcmData> searchTCM(String keyword);

    public ArrayList<ProteinData> searchProtein(String keyword);

    public ArrayList<DrugData> searchDrug(String keyword);

}
