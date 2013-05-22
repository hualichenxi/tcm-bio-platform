/**
 * TcmInferService.java
 * Copyright 2013 Hao Tong, all rights reserved.
 * PROPRIETARY/CONFIDENTIAL, any form of usage is subject to approval.
 */

package com.ccnt.tcmbio.service;

import java.util.ArrayList;

import com.ccnt.tcmbio.data.TcmInferData;
import com.ccnt.tcmbio.data.TcmSearchData;
import com.ccnt.tcmbio.data.graph.Graphml;

public interface TcmInferService {

    public TcmSearchData getTcmInference(String tcmName, Integer start, Integer offset);

    public ArrayList<TcmInferData> getAndCacheTcmInference(String tcmName);

    public boolean searchTcm(String tcmName);

    public ArrayList<String> fuzzyMatchTcm(String tcmName);

    public Graphml getDiseaseName(String tcmName, Integer start, Integer offset);

    public Graphml getDiseaseID(String diseaseName, Integer start, Integer offset);

    public Graphml getDrugID(String diseaseID, Integer start, Integer offset);

    public Graphml getTargetID(String drugID, Integer start, Integer offset);

    public Graphml getProtein(String targetID, Integer start, Integer offset);

    public Graphml getGeneID(String protein, Integer start, Integer offset);

    public Graphml getGeneProduct(String geneID, Integer start, Integer offset);

}
