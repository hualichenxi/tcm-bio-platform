/**
 * JdbcOntologyDAO.java
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

import com.ccnt.tcmbio.dao.OntologyDAO;
import com.ccnt.tcmbio.data.OntologyData;

public class OntologyDAOImpl extends JdbcDaoSupport implements OntologyDAO{

//    private DataSource dataSource;
//
//    @Override
//    public void setDataSource(final DataSource dataSource) {
//        this.dataSource = dataSource;
//    }

    private static final Logger LOGGER = LogManager.getLogger(OntologyDAOImpl.class.getName());

    @Override
    public ArrayList<OntologyData> findAllGraphs(){
        final String sparql = "SPARQL SELECT distinct ?g as ?name WHERE { GRAPH ?g {?s ?p ?o} }";
        final ArrayList<OntologyData> ontologies = new ArrayList<OntologyData>();

        LOGGER.debug("findAllGraphs - query virtuoso: {}", sparql);

        try {

            final List<Map<String, Object>> rows = getJdbcTemplate().queryForList(sparql);

            for (final Map<String, Object> row : rows) {
                final OntologyData ontology = new OntologyData();

                final String nameString = row.get("name").toString();
                ontology.setName(nameString);
                ontology.setItemnum(0);
                ontology.setDescription("no description");

                ontologies.add(ontology);
            }

            return ontologies;

        } catch (final Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public ArrayList<OntologyData> findAllOntologies() {
        final String sparql = "SPARQL SELECT distinct ?g as ?name WHERE { GRAPH ?g {?s ?p ?o} }";
        final ArrayList<OntologyData> ontologies = new ArrayList<OntologyData>();

        LOGGER.debug("findAllOntologies - query virtuoso: {}", sparql);

        try {

            final List<Map<String, Object>> rows = getJdbcTemplate().queryForList(sparql);

            for (final Map<String, Object> row : rows) {
                final OntologyData ontology = new OntologyData();
                final String nameString = row.get("name").toString();
                ontology.setName(nameString);

                final String getTripleCountSparql = "SPARQL select (count(*) as ?count) from <"
                        + nameString
                        + "> where {?s ?p ?o}";
                final List<Map<String, Object>> inrows = getJdbcTemplate().queryForList(getTripleCountSparql);

                if (!inrows.isEmpty()) {
                    ontology.setItemnum(Integer.parseInt((inrows.get(0).get("count")).toString()));
                } else {
                    ontology.setItemnum(0);
                }

                ontology.setDescription("no description");
                ontologies.add(ontology);
            }

            return ontologies;

        } catch (final Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ArrayList<OntologyData> findAllOntologiesv1_0() {
        final String sparql = "SPARQL SELECT ?g as ?name (count(*) as ?count) WHERE { GRAPH ?g {?s ?p ?o} } group by ?g";
        final ArrayList<OntologyData> ontologies = new ArrayList<OntologyData>();

        LOGGER.debug("findAllOntologiesv1_0 - query virtuoso: {}", sparql);

        try {

            final List<Map<String, Object>> rows = getJdbcTemplate().queryForList(sparql);

            for (final Map<String, Object> row : rows) {
                final OntologyData ontology = new OntologyData();

                final String nameString = row.get("name").toString();
                ontology.setName(nameString);

                final Integer itemnum = Integer.parseInt((row.get("count")).toString());
                ontology.setItemnum(itemnum);

                ontology.setDescription("no description");

                ontologies.add(ontology);
            }

            return ontologies;

        } catch (final Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ArrayList<OntologyData> searchOntologies(final String keyword){
        final String sparql = "SPARQL SELECT ?g as ?name (count(*) as ?count) WHERE { GRAPH ?g {?s ?p ?o} . filter regex(?g, \""
                                                            + keyword +"\", \"i\")}";
        final ArrayList<OntologyData> ontologies = new ArrayList<OntologyData>();

        LOGGER.debug("searchOntologies - query virtuoso: {}", sparql);

        try {

            final List<Map<String, Object>> rows = getJdbcTemplate().queryForList(sparql);

            for (final Map<String, Object> row : rows) {
                final OntologyData ontology = new OntologyData();

                final String nameString = row.get("name").toString();
                ontology.setName(nameString);

                final Integer itemnum = Integer.parseInt((row.get("count")).toString());
                ontology.setItemnum(itemnum);

                ontology.setDescription("no description");

                ontologies.add(ontology);
            }

            return ontologies;

        } catch (final Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ArrayList<OntologyData> findAllCachedOntologies(final String graphName){
        final String sparql = "sparql select * from <" + graphName + "> where {?graphname tcmbio:ontologycount ?itemcount}";
        final ArrayList<OntologyData> ontologies = new ArrayList<OntologyData>();

        LOGGER.debug("find all cached Ontologies - query virtuoso: {}", sparql);

        try {

            final List<Map<String, Object>> rows = getJdbcTemplate().queryForList(sparql);

            for (final Map<String, Object> row : rows) {
                final OntologyData ontology = new OntologyData();

                final String nameString = row.get("graphname").toString();
                ontology.setName(nameString);

                final Integer itemnum = Integer.parseInt((row.get("itemcount")).toString());
                ontology.setItemnum(itemnum);

                ontology.setDescription("no description");

                ontologies.add(ontology);
            }

            return ontologies;

        } catch (final Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean newOntologyGraph(final String graphName){
        final String sparql = "sparql create graph<" + graphName + ">";

        LOGGER.debug("newOntologyGraph - query virtuoso: {}", sparql);

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
    public boolean insertOntology(final String graphName, final OntologyData ontologyData){
        final String sparql = "sparql insert in graph<" + graphName + "> {<" + ontologyData.getName()
                + "> tcmbio:ontologycount " + ontologyData.getItemnum() + " }";

        LOGGER.debug("insertMappingTriple - query virtuoso: {}", sparql);

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
    public boolean dropOntolgyGraph(final String graphName){
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

/*    public ArrayList<OntologyData> findAllOntologies() {
        Connection connection = null;
        final String getAllGraphSparql = "SPARQL SELECT DISTINCT ?g as ?name WHERE { GRAPH ?g {?s ?p ?o} }";
        final ArrayList<OntologyData> ontologies = new ArrayList<OntologyData>();

        try {
            connection = dataSource.getConnection();
            final Statement st = connection.createStatement();
            final ResultSet graphRs = st.executeQuery(getAllGraphSparql);

            while (graphRs.next()) {
                final OntologyData ontology = new OntologyData();
                ontology.setName(graphRs.getString("name"));

                final String getTripleCountSparql = "SPARQL select (count(*) as ?count) from <"
                        + graphRs.getString("name")
                        + "> where {?s ?p ?o}";
                final Statement countST = connection.createStatement();
                final ResultSet countRs = countST.executeQuery(getTripleCountSparql);
                if (countRs.next()) {
                    ontology.setItemnum(countRs.getInt("count"));
                } else {
                    ontology.setItemnum(0);
                }
                ontology.setDescription("no description");
                ontologies.add(ontology);
                countRs.close();
            }

            graphRs.close();
            st.close();
            return ontologies;

        } catch (final Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (final Exception e2) {
                    // TODO: handle exception
                    e2.printStackTrace();
                }
            }
        }
        return null;
    }
*/
}