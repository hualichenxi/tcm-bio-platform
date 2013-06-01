/**
 * TermService.java
 * Copyright 2013 Hao Tong, all rights reserved.
 * PROPRIETARY/CONFIDENTIAL, any form of usage is subject to approval.
 */

package com.ccnt.tcmbio.service;

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

public interface TermService {

    // fuzzy search
    public DiseaseSearchData searchDisease(String keyword, String start, String offset);

    public GeneSearchData searchGOID(String keyword, String start, String offset);

    public TCMSearchData searchTCM(String keyword, String start, String offset);

    public DrugSearchData searchDrug(String keyword, String start, String offset);

    public GeneIDSearchData searchGeneID(String keyword, String start, String offset);

    public ProteinSearchData searchProteinAC(String keyword, String start, String offset);

    // full match
    public DiseaseData getDisease(String keyword);

    public GeneData getGOID(String keyword);

    public TCMData getTCM(String keyword);

    public DrugData getDrug(String keyword);

    public GeneIDData getGeneID(String keyword);

    public ProteinData getProteinAC(String keyword);

}
