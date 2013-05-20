/**
 * TcmInferDaoImpl.java
 * Copyright 2013 Hao Tong, all rights reserved.
 * PROPRIETARY/CONFIDENTIAL, any form of usage is subject to approval.
 */

package com.ccnt.tcmbio.dao.impl;

import static com.ccnt.tcmbio.data.GraphNames.Diseasome;
import static com.ccnt.tcmbio.data.GraphNames.DrugBank;
import static com.ccnt.tcmbio.data.GraphNames.Gene_Ontology;
import static com.ccnt.tcmbio.data.GraphNames.Protein_Gene_Mapping;
import static com.ccnt.tcmbio.data.GraphNames.TCMGeneDIT;
import static com.ccnt.tcmbio.data.GraphNames.Tcm_Diseasesome_Mapping;
import static com.ccnt.tcmbio.data.PredictNames.Diseasesome_PossibleDrug;
import static com.ccnt.tcmbio.data.PredictNames.Drugbank_SwissprotId;
import static com.ccnt.tcmbio.data.PredictNames.Drugbank_Target;
import static com.ccnt.tcmbio.data.PredictNames.OWL_SameAs;
import static com.ccnt.tcmbio.data.PredictNames.Rdfs_Label;
import static com.ccnt.tcmbio.data.PredictNames.TCMGeneDIT_Treatment;
import static com.ccnt.tcmbio.data.PredictNames.UniprotGO_ClassifiedWith;

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

    @Override
    public ArrayList<String> getDiseaseName(final String tcm, final Integer start, final Integer offset){

        final String sparql = "sparql select (*) from <" + TCMGeneDIT + "> where {"
                + tcm + " " + TCMGeneDIT_Treatment + " ?diseaseName}";

        LOGGER.debug("getDiseaseName - query virtuoso: {}", sparql);

        try {
            final ArrayList<String> diseaseNames = new ArrayList<String>();
            final List<Map<String, Object>> rows = getJdbcTemplate().queryForList(sparql);

            for(final Map<String, Object> row : rows){
                diseaseNames.add(row.get("diseaseName").toString());
            }

            return diseaseNames;
        } catch (final DataAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public ArrayList<String> getDiseaseID(final String diseaseName, final Integer start, final Integer offset){

        final String sparql = "sparql select (*) from <" + Tcm_Diseasesome_Mapping + "> where {"
                + diseaseName + " " + OWL_SameAs + " ?diseaseID}";

        LOGGER.debug("getDiseaseID - query virtuoso: {}", sparql);

        try {
            final ArrayList<String> diseaseIDs = new ArrayList<String>();
            final List<Map<String, Object>> rows = getJdbcTemplate().queryForList(sparql);

            for(final Map<String, Object> row : rows){
                diseaseIDs.add(row.get("diseaseID").toString());
            }

            return diseaseIDs;
        } catch (final DataAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public ArrayList<String> getDrugID(final String diseaseID, final Integer start, final Integer offset){

        final String sparql = "sparql select (*) from <" + Diseasome + "> where {"
                + diseaseID + " " + Diseasesome_PossibleDrug + " ?drugID}";

        LOGGER.debug("getDrugID - query virtuoso: {}", sparql);

        try {
            final ArrayList<String> drugIDs = new ArrayList<String>();
            final List<Map<String, Object>> rows = getJdbcTemplate().queryForList(sparql);

            for(final Map<String, Object> row : rows){
                drugIDs.add(row.get("drugID").toString());
            }

            return drugIDs;
        } catch (final DataAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public ArrayList<String> getTargetID(final String drugID, final Integer start, final Integer offset){

        final String sparql = "sparql select (*) from <" + DrugBank + "> where {"
                + drugID + " " + Drugbank_Target + " ?targetID}";

        LOGGER.debug("getTargetID - query virtuoso: {}", sparql);

        try {
            final ArrayList<String> targetIDs = new ArrayList<String>();
            final List<Map<String, Object>> rows = getJdbcTemplate().queryForList(sparql);

            for(final Map<String, Object> row : rows){
                targetIDs.add(row.get("targetID").toString());
            }

            return targetIDs;
        } catch (final DataAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public ArrayList<String> getProtein(final String targetID, final Integer start, final Integer offset){

        final String sparql = "sparql select (*) from <" + DrugBank + "> where {"
                + targetID + " " + Drugbank_SwissprotId + " ?proteinAcce}";

        LOGGER.debug("getProtein - query virtuoso: {}", sparql);

        try {
            final ArrayList<String> proteinAcces = new ArrayList<String>();
            final List<Map<String, Object>> rows = getJdbcTemplate().queryForList(sparql);

            for(final Map<String, Object> row : rows){
                proteinAcces.add(row.get("proteinAcce").toString());
            }

            return proteinAcces;
        } catch (final DataAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public ArrayList<String> getGeneID(final String protein, final Integer start, final Integer offset){

        final String sparql = "sparql select (*) from <" + Protein_Gene_Mapping + "> where {"
                + protein + " " + UniprotGO_ClassifiedWith + " ?geneID}";

        LOGGER.debug("getGeneID - query virtuoso: {}", sparql);

        try {
            final ArrayList<String> geneIDs = new ArrayList<String>();
            final List<Map<String, Object>> rows = getJdbcTemplate().queryForList(sparql);

            for(final Map<String, Object> row : rows){
                geneIDs.add(row.get("geneID").toString());
            }

            return geneIDs;
        } catch (final DataAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public ArrayList<String> getGeneProduct(final String geneID, final Integer start, final Integer offset){

        final String sparql = "sparql select (*) from <" + Gene_Ontology + "> where {"
                + geneID + " " + Rdfs_Label + " ?geneProduct}";

        LOGGER.debug("getGeneProduct - query virtuoso: {}", sparql);

        try {
            final ArrayList<String> geneProducts = new ArrayList<String>();
            final List<Map<String, Object>> rows = getJdbcTemplate().queryForList(sparql);

            for(final Map<String, Object> row : rows){
                geneProducts.add(row.get("geneProduct").toString());
            }

            return geneProducts;
        } catch (final DataAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return null;
    }

}
