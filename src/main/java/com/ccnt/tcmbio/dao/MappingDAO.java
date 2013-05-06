/**
 * MappingDAO.java
 * Copyright 2013 Hao Tong, all rights reserved.
 * PROPRIETARY/CONFIDENTIAL, any form of usage is subject to approval.
 */

package com.ccnt.tcmbio.dao;

import java.util.ArrayList;

import com.ccnt.tcmbio.data.MappingData;


public interface MappingDAO {

    // init or update the mapping graph

    public Integer askIfMapping(String graph1, String graph2);

    public boolean newMappingGraph(String graphName);

    public boolean ifExistMappingGraph(String graphName);

    public boolean dropMappingGraph(final String graphName);

    public boolean insertMappingTriple(String mappingGraphName, String graphName, Integer mappingCount);

    public boolean insertMappingDetailTriple(String mappingGraphName, String graph1, String graph2, Integer mappingCount);

    // get the details of details


    // get mapping relations

    public ArrayList<MappingData> getMappings();

    public ArrayList<MappingData> getMappingDetails(String graphName);

}
