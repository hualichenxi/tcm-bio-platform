/**
 * TermService.java
 * Copyright 2013 Hao Tong, all rights reserved.
 * PROPRIETARY/CONFIDENTIAL, any form of usage is subject to approval.
 */

package com.ccnt.tcmbio.service;

import com.ccnt.tcmbio.data.DiseaseSearchData;
import com.ccnt.tcmbio.data.DrugSearchData;
import com.ccnt.tcmbio.data.GeneIDSearchData;
import com.ccnt.tcmbio.data.GeneSearchData;
import com.ccnt.tcmbio.data.TCMSearchData;

public interface TermService {

    public DiseaseSearchData searchDisease(String keyword, String start, String offset);

    public GeneSearchData searchGOID(String keyword, String start, String offset);

    public TCMSearchData searchTCM(String keyword, String start, String offset);

    public DrugSearchData searchDrug(String keyword, String start, String offset);

    public GeneIDSearchData searchGeneID(String keyword, String start, String offset);

}
