/**
 * MappingServiceImpl.java
 * Copyright 2013 Hao Tong, all rights reserved.
 * PROPRIETARY/CONFIDENTIAL, any form of usage is subject to approval.
 */

package com.ccnt.tcmbio.service.impl;

import java.util.ArrayList;
import java.util.Iterator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ccnt.tcmbio.dao.MappingDAO;
import com.ccnt.tcmbio.dao.OntologyDAO;
import com.ccnt.tcmbio.data.MappingData;
import com.ccnt.tcmbio.data.OntologyData;
import com.ccnt.tcmbio.service.MappingService;

public class MappingServiceImpl implements MappingService{

    private MappingDAO mappingDAO;
    private OntologyDAO ontologyDAO;
    private final String graphName = "http://localhost:8890/graph_mapping_relations";

    private static final Logger LOGGER = LogManager.getLogger(MappingServiceImpl.class.getName());

    public void setMappingDAO(final MappingDAO mappingDAO) {
        this.mappingDAO = mappingDAO;
    }

    public void setOntologyDAO(final OntologyDAO ontologyDAO) {
        this.ontologyDAO = ontologyDAO;
    }

    @Override
    public Integer syncMappingGraph(){

        try {
            if (!mappingDAO.ifExistMappingGraph(graphName)) {
                mappingDAO.newMappingGraph(graphName);
            }
            final ArrayList<OntologyData> ontologyDatas = ontologyDAO.findAllGraphs();
            final Iterator<OntologyData> ontoIterator = ontologyDatas.iterator();
            while (ontoIterator.hasNext()) {
                final OntologyData ontologyData = ontoIterator.next();
                int mappingcount = 0;
                final String graph1 = ontologyData.getName();

                final Iterator<OntologyData> inOntoIterator = ontologyDatas.iterator();
                while (inOntoIterator.hasNext()) {
                    final OntologyData inOntologyData = inOntoIterator.next();
                    final String graph2 = inOntologyData.getName();
                    if (!graph1.equals(graph2)) {

                        LOGGER.debug("mapping test of the two graphs: {} & {}", graph1, graph2);

                        final Integer count = mappingDAO.askIfMapping(graph1, graph2);
                        if (count > 0) {
                            mappingDAO.insertMappingDetailTriple(graphName, graph1, graph2, count);
                            mappingcount++;
                        }
                    }
                }
                mappingDAO.insertMappingTriple(graphName, graph1, mappingcount);
            }
            return 1;
        } catch (final Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return -1;
    }

    @Override
    public boolean clearMappingGraph(){

        LOGGER.debug("Drop the mapping graph");

        return mappingDAO.dropMappingGraph(graphName);
    }

    @Override
    public ArrayList<MappingData> getMappings(){

        LOGGER.debug("Get mapping relation of all graphs");

        if (!mappingDAO.ifExistMappingGraph(graphName)) {
            LOGGER.debug("No mapping graph exits, start the synchonise job");
            syncMappingGraph();
        } else {
            // UNDO
//            mappingDAO.dropMappingGraph(graphName);
//            syncMappingGraph();
        }
        return mappingDAO.getMappings();
    }

    @Override
    public ArrayList<MappingData> searchMappings(final String keyword){

        LOGGER.debug("Search the mapping relations");

        return null;
    }

    @Override
    public ArrayList<MappingData> getMappingDetails(final String graphName){

        LOGGER.debug("Get the mapping details of graph: {}", graphName);

        return mappingDAO.getMappingDetails(graphName);
    }

}
