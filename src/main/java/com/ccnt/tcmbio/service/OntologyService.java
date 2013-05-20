/**
 * Ontology.java
 * Copyright 2013 Hao Tong, all rights reserved.
 * PROPRIETARY/CONFIDENTIAL, any form of usage is subject to approval.
 */

package com.ccnt.tcmbio.service;

import java.util.ArrayList;

import com.ccnt.tcmbio.data.OntologyData;

public interface OntologyService {

    public ArrayList<OntologyData> getAllOntologies();

    public ArrayList<OntologyData> searchOntologies(String keyword);

    public ArrayList<OntologyData> getAllCachedOntologies();

    public boolean syncOntologiesGraph();

}
