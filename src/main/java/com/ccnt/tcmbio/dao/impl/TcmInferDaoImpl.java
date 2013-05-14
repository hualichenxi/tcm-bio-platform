/**
 * TcmInferDaoImpl.java
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

import com.ccnt.tcmbio.dao.TcmInferDao;
import com.ccnt.tcmbio.data.TcmInferData;

public class TcmInferDaoImpl extends JdbcDaoSupport implements TcmInferDao{

    private static final Logger LOGGER = LogManager.getLogger(TcmInferDaoImpl.class.getName());

    @Override
    public ArrayList<TcmInferData> getTcmInference(final String tcm, final Integer start, final Integer offset){

        final String sparql = "sparql select * where {"
                + "graph<http://localhost:8890/TCMGeneDIT> {<" + tcm + "> TCMGeneDIT:treatment ?diseaseName} . "
                + "graph<http://localhost:8890/tcm_diseasesome_mapping> {?diseaseName owl:sameAs ?diseaseID} . "
                + "graph<http://linkedlifedata.com/resource/diseasome> {?diseaseID diseasesome:possibleDrug ?drugID} . "
                + "graph<http://linkedlifedata.com/resource/drugbank> {?drugID drugbank:target ?targetID} . "
                + "graph<http://linkedlifedata.com/resource/drugbank> {?targetID drugbank:swissprotId ?proteinAcce} . "
                + "graph<http://uniprot/protein_gene_mapping> {?proteinAcce uniprotGO:classifiedWith ?GOID} . "
                + "graph<http://localhost:8890/gene_ontology> {?GOID rdfs:label ?genePro}} "
                + "limit(" + offset + ") offset(" + start + ")";

        LOGGER.debug("get tcm inference result - query virtuoso: {}", sparql);

        try {
            final ArrayList<TcmInferData> tcmInferDatas = new ArrayList<TcmInferData>();
            final List<Map<String, Object>> rows = getJdbcTemplate().queryForList(sparql);

            for (final Map<String, Object> map : rows) {
                final TcmInferData tcmInferData = new TcmInferData();
                tcmInferData.setTcmName(tcm);
                tcmInferData.setDiseaseName(map.get("diseaseName").toString());
                tcmInferData.setDiseaseID(map.get("diseaseID").toString());
                tcmInferData.setDrugID(map.get("drugID").toString());
                tcmInferData.setTargetID(map.get("targetID").toString());
                tcmInferData.setProteinAcce(map.get("proteinAcce").toString());
                tcmInferData.setGeneGOID(map.get("GOID").toString());
                tcmInferData.setGeneProduct(map.get("genePro").toString());
                tcmInferDatas.add(tcmInferData);
            }
            return tcmInferDatas;
        } catch (final DataAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Integer getTcmInferCount(final String tcm){

        final String sparql = "sparql select count(*) as ?count where {"
                + "graph<http://localhost:8890/TCMGeneDIT> {<" + tcm + "> TCMGeneDIT:treatment ?diseaseName} . "
                + "graph<http://localhost:8890/tcm_diseasesome_mapping> {?diseaseName owl:sameAs ?diseaseID} . "
                + "graph<http://linkedlifedata.com/resource/diseasome> {?diseaseID diseasesome:possibleDrug ?drugID} . "
                + "graph<http://linkedlifedata.com/resource/drugbank> {?drugID drugbank:target ?targetID} . "
                + "graph<http://linkedlifedata.com/resource/drugbank> {?targetID drugbank:swissprotId ?proteinAcce} . "
                + "graph<http://uniprot/protein_gene_mapping> {?proteinAcce uniprotGO:classifiedWith ?GOID} . "
                + "graph<http://localhost:8890/gene_ontology> {?GOID rdfs:label ?genePro}}";

        LOGGER.debug("get tcm inference result - query virtuoso: {}", sparql);

        try {
            return getJdbcTemplate().queryForInt(sparql);
        } catch (final DataAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return 0;

    }

}
