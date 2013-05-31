/**
 * MappingDAOImpl.java
 * Copyright 2013 Hao Tong, all rights reserved.
 * PROPRIETARY/CONFIDENTIAL, any form of usage is subject to approval.
 */

package com.ccnt.tcmbio.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.ccnt.tcmbio.dao.MappingDAO;
import com.ccnt.tcmbio.data.MappingData;

public class MappingDAOImpl extends JdbcDaoSupport implements MappingDAO{

    private static final Logger LOGGER = LogManager.getLogger(MappingDAOImpl.class.getName());
    // to generated the new mapping graph

    @Override
    public Integer askIfMapping(final String graph1, final String graph2){
        final String sparql = "sparql select (count(*) as ?count) where {{{graph <" + graph1
                                + "> {?s1 ?p1 ?o}} . {graph <" + graph2 + "> {?s2 ?p2 ?o}}} union "
                                + "{{graph <" + graph1 + "> {?s ?p1 ?o1}} . {graph <" + graph2 + "> {?s ?p2 ?o2}}}}";

        LOGGER.debug("askIfMapping - query virtuoso: {}", sparql);

        try {
            final Integer countInteger = getJdbcTemplate().queryForInt(sparql);
            return countInteger;
        } catch (final Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return -1;
    }

    @Override
    public boolean newMappingGraph(final String graphName){
        final String sparql = "sparql create graph<" + graphName + ">";

        LOGGER.debug("newMappingGraph - query virtuoso: {}", sparql);

        try {
            getJdbcTemplate().update(sparql);
            return true;
        } catch (final DataAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean ifExistMappingGraph(final String graphName){
        final String sparql = "sparql select count(*) as ?count {graph <" + graphName + "> {?s ?p ?o}}";

        LOGGER.debug("ifExistMappingGraph - query virtuoso: {}", sparql);

        try {
            if(getJdbcTemplate().queryForInt(sparql) !=0 ){
                return true;
            } else {
                return false;
            }
        } catch (final DataAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean dropMappingGraph(final String graphName){
        final String sparql = "sparql drop silent graph<" + graphName + ">";

        LOGGER.debug("dropMappingGraph - query virtuoso: {}", sparql);

        try {
            getJdbcTemplate().update(sparql);
            return true;
        } catch (final DataAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return false;
    }


    @Override
    public boolean insertMappingTriple(final String mappingGraphName, final String graphName, final Integer mappingCount){

        try {
            final String sparql0 = "sparql select * where{ graph<" + mappingGraphName + "> {<" + graphName
                    + "> tcmbio:mappingcount ?mappingCount}}";

            LOGGER.debug("check if mapping triple exist - query virtuoso: {}", sparql0);

            final List<Map<String, Object>> rows = getJdbcTemplate().queryForList(sparql0);

            if (!rows.isEmpty()) {
                for(final Map<String, Object> row : rows){
                    final String count = row.get("mappingCount").toString();
                    final String sparql1 = "sparql delete from <" + mappingGraphName + "> {<" + graphName
                            + "> tcmbio:mappingcount " + count + "}";
                    LOGGER.debug("delete mapping triple - query virtuoso: {}", sparql1);
                    getJdbcTemplate().update(sparql1);
                }
            }

            final String sparql = "sparql insert in graph<" + mappingGraphName + "> {<" + graphName
                    + "> tcmbio:mappingcount " + mappingCount + " }";

            LOGGER.debug("insertMappingTriple - query virtuoso: {}", sparql);

            getJdbcTemplate().update(sparql);
            return true;
        } catch (final DataAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean insertMappingDetailTriple(final String mappingGraphName, final String graph1, final String graph2, final Integer mappingCount){

        final String sparql0 = "sparql select * where{ graph<" + mappingGraphName + "> {<" + graph1
                + "> tcmbio:mapping <" + graph2 + "> }}";

        LOGGER.debug("check if mapping triple exist - query virtuoso: {}", sparql0);

        final List<Map<String, Object>> rows = getJdbcTemplate().queryForList(sparql0);

        if (!rows.isEmpty()) {
            final String sparql1 = "sparql delete from <" + mappingGraphName + "> {<" + graph1
                    + "> tcmbio:mapping <" + graph2 + "> }";

            LOGGER.debug("delete mapping triple  - query virtuoso: {}", sparql1);

            getJdbcTemplate().update(sparql1);
        }

        final String sparql = "sparql insert in graph<" + mappingGraphName + "> {<" + graph1
                + "> tcmbio:mapping <" + graph2 + "> }";

        LOGGER.debug("insertMappingDetailTriple - query virtuoso: {}", sparql);

        try {
            getJdbcTemplate().update(sparql);
            return true;
        } catch (final DataAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return false;
    }

    // for details of the details

    //queries for the generated mapping graph

    @Override
    public ArrayList<MappingData> getMappings(){
        final String sparql = "sparql select ?gn ?count where {graph <http://localhost:8890/graph_mapping_relations> {?gn tcmbio:mappingcount ?count}}";

        LOGGER.debug("getMappings - query virtuoso: {}", sparql);

        try {
            final List<Map<String, Object>> rows = getJdbcTemplate().queryForList(sparql);
            final ArrayList<MappingData> mappingDatas = new ArrayList<MappingData>();
            for(final Map<String, Object> row : rows){
                final MappingData mappingData = new MappingData();
                mappingData.setOntoName(row.get("gn").toString());
                mappingData.setTotalNum(Integer.parseInt(row.get("count").toString()));
                mappingDatas.add(mappingData);
            }
            return mappingDatas;
        } catch (final DataAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (final NumberFormatException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public ArrayList<MappingData> getMappingDetails(final String graphName){
        final String sparql = "sparql select ?graph where {graph <http://localhost:8890/graph_mapping_relations> {<"
                                + graphName +"> tcmbio:mapping ?graph}}";

        LOGGER.debug("getMappingDetails - query virtuoso: {}", sparql);

        try {
            final List<Map<String, Object>> rows = getJdbcTemplate().queryForList(sparql);
            final ArrayList<MappingData> mappingDatas = new ArrayList<MappingData>();
            for(final Map<String, Object> row : rows){
                final MappingData mappingData = new MappingData();
                mappingData.setOntoName(row.get("graph").toString());
                mappingData.setTotalNum(askIfMapping(graphName, row.get("graph").toString()));
                mappingDatas.add(mappingData);
            }
            return mappingDatas;
        } catch (final DataAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (final NumberFormatException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
    
    @Override
    public ArrayList<String> getRelativeGraph(final String graphName){
        final String sparql = "sparql select ?graph where {graph <http://localhost:8890/graph_mapping_relations> {<"
                                + graphName +"> tcmbio:mapping ?graph}}";

        LOGGER.debug("getRelativeGraph - query virtuoso: {}", sparql);

        try {
            final List<Map<String, Object>> rows = getJdbcTemplate().queryForList(sparql);
            final ArrayList<String> relativeGraphs = new ArrayList<String>();
            for(final Map<String, Object> row : rows){
            	relativeGraphs.add(row.get("graph").toString());
            }
            return relativeGraphs;
        } catch (final DataAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (final NumberFormatException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

}
