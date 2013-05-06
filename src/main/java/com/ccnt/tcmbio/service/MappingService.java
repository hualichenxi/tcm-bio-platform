/**
 * MappingService.java
 * Copyright 2013 Hao Tong, all rights reserved.
 * PROPRIETARY/CONFIDENTIAL, any form of usage is subject to approval.
 */

package com.ccnt.tcmbio.service;

import java.util.ArrayList;

import com.ccnt.tcmbio.data.MappingData;

public interface MappingService {

    public Integer syncMappingGraph();

    public boolean clearMappingGraph();

    public ArrayList<MappingData> getMappings();

    public ArrayList<MappingData> searchMappings(String keyword);

    public ArrayList<MappingData> getMappingDetails(String graphName);

}
