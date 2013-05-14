/**
 * MappingService.java
 * Copyright 2013 Hao Tong, all rights reserved.
 * PROPRIETARY/CONFIDENTIAL, any form of usage is subject to approval.
 */

package com.ccnt.tcmbio.service;

import java.util.ArrayList;

import com.ccnt.tcmbio.data.MappingData;
import com.ccnt.tcmbio.data.MappingSyncData;

public interface MappingService {

    public Integer syncMappingGraph();

    public MappingSyncData syncMappingProgress();

    public boolean clearMappingGraph();

    public ArrayList<MappingData> getMappings();

    public ArrayList<MappingData> searchMappings(String keyword);

    public ArrayList<MappingData> getMappingDetails(String graphName);

    public String elapasedTimeToString(long elapasedTIme);

}
