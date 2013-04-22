/**
 * OntologyServiceImpl.java
 * Copyright 2013 Hao Tong, all rights reserved.
 * PROPRIETARY/CONFIDENTIAL, any form of usage is subject to approval.
 */

package com.ccnt.tcmbio.service.impl;

import java.util.ArrayList;

import com.ccnt.tcmbio.dao.OntologyDAO;
import com.ccnt.tcmbio.data.OntologyData;
import com.ccnt.tcmbio.service.OntologyService;

public class OntologyServiceImpl implements OntologyService{

    private OntologyDAO ontologyDAO;

    public void setOntologyDAO(final OntologyDAO ontologyDAO) {
        this.ontologyDAO = ontologyDAO;
    }

    @Override
    public ArrayList<OntologyData> getAllOntologies() {
        return ontologyDAO.findAllOntologies();
    }

    @Override
    public ArrayList<OntologyData> searchOntologies(final String keyword){
        return ontologyDAO.searchOntologies(keyword);
    }

}
