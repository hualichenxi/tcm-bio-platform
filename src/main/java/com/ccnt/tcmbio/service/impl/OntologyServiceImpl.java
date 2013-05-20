/**
 * OntologyServiceImpl.java
 * Copyright 2013 Hao Tong, all rights reserved.
 * PROPRIETARY/CONFIDENTIAL, any form of usage is subject to approval.
 */

package com.ccnt.tcmbio.service.impl;

import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ccnt.tcmbio.dao.OntologyDAO;
import com.ccnt.tcmbio.data.OntologyData;
import com.ccnt.tcmbio.service.OntologyService;

public class OntologyServiceImpl implements OntologyService{

    private OntologyDAO ontologyDAO;
    private static final Logger LOGGER = LogManager.getLogger(OntologyServiceImpl.class.getName());
    private static final String ONTOLOGIES = "http://localhost:8890/graph-resource";

    public void setOntologyDAO(final OntologyDAO ontologyDAO) {
        this.ontologyDAO = ontologyDAO;
    }

    @Override
    public ArrayList<OntologyData> getAllOntologies() {

        LOGGER.debug("service: getAllOntologies");

        return ontologyDAO.findAllOntologies();
    }

    @Override
    public ArrayList<OntologyData> searchOntologies(final String keyword){

        LOGGER.debug("service: searchOntologies");

        return ontologyDAO.searchOntologies(keyword);
    }

    @Override
    public ArrayList<OntologyData> getAllCachedOntologies(){
        return ontologyDAO.findAllCachedOntologies(ONTOLOGIES);
    }

    @Override
    public boolean syncOntologiesGraph(){
        try {
            if (ontologyDAO.ifExistMappingGraph(ONTOLOGIES)) {
                ontologyDAO.dropOntolgyGraph(ONTOLOGIES);
            }

            ontologyDAO.newOntologyGraph(ONTOLOGIES);
            final ArrayList<OntologyData> ontologyDatas = ontologyDAO.findAllOntologies();

            for(final OntologyData ontologyData : ontologyDatas){
                ontologyDAO.insertOntology(ONTOLOGIES, ontologyData);
            }
        } catch (final Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return true;
    }

}
