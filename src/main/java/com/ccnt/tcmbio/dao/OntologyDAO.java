/**
 * OntologyDAO.java
 * Copyright 2013 Hao Tong, all rights reserved.
 * PROPRIETARY/CONFIDENTIAL, any form of usage is subject to approval.
 */

package com.ccnt.tcmbio.dao;

import java.util.ArrayList;

import com.ccnt.tcmbio.data.OntologyData;

public interface OntologyDAO {

    public ArrayList<OntologyData> findAllOntologies();

    public ArrayList<OntologyData> findAllGraphs();

    public ArrayList<OntologyData> findAllOntologiesv1_0();

    public ArrayList<OntologyData> findAllCachedOntologies(String graphName);

    public ArrayList<OntologyData> searchOntologies(final String keyword);

    public boolean newOntologyGraph(String graphName);

    public boolean insertOntology(String graphName, OntologyData ontologyData);

    public boolean dropOntolgyGraph(String graphName);

    public boolean ifExistMappingGraph(final String graphName);

}
