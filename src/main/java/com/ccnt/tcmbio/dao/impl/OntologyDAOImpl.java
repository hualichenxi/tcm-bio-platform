/**
 * JdbcOntologyDAO.java
 * Copyright 2013 Hao Tong, all rights reserved.
 * PROPRIETARY/CONFIDENTIAL, any form of usage is subject to approval.
 */

package com.ccnt.tcmbio.dao.impl;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

    @Override
    public ArrayList<OntologyData> findAllOntologies() {
        final String getAllGraphSparql = "SPARQL SELECT DISTINCT ?g as ?name WHERE { GRAPH ?g {?s ?p ?o} }";
        final ArrayList<OntologyData> ontologies = new ArrayList<OntologyData>();

        try {

            final List<Map<String, Object>> rows = getJdbcTemplate().queryForList(getAllGraphSparql);

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
    public ArrayList<OntologyData> searchOntology(final String keyword){
        return null;
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