/**
 * TermDAOImpl.java
 * Copyright 2013 Hao Tong, all rights reserved.
 * PROPRIETARY/CONFIDENTIAL, any form of usage is subject to approval.
 */

package com.ccnt.tcmbio.dao.impl;

import static com.ccnt.tcmbio.data.GraphNames.DiseaseNameIDMapping;
import static com.ccnt.tcmbio.data.GraphNames.DiseaseOntology;
import static com.ccnt.tcmbio.data.GraphNames.Diseasome;
import static com.ccnt.tcmbio.data.GraphNames.DrugBank;
import static com.ccnt.tcmbio.data.GraphNames.FunDO;
import static com.ccnt.tcmbio.data.GraphNames.Gene_Ontology;
import static com.ccnt.tcmbio.data.GraphNames.TCMGeneDIT;
import static com.ccnt.tcmbio.data.GraphNames.Tcm_Diseasesome_Mapping;
import static com.ccnt.tcmbio.data.Namespaces.DrugBankDiseaseIDPrefix;
import static com.ccnt.tcmbio.data.Namespaces.FunDODisease;
import static com.ccnt.tcmbio.data.Namespaces.GeneOntologyGeneIDPrefix;
import static com.ccnt.tcmbio.data.Namespaces.TCMGeneDITID;
import static com.ccnt.tcmbio.data.PredictNames.DiseaseOntologyPrefix;
import static com.ccnt.tcmbio.data.PredictNames.GODefinition;
import static com.ccnt.tcmbio.data.PredictNames.GOID;
import static com.ccnt.tcmbio.data.PredictNames.GONamespace;
import static com.ccnt.tcmbio.data.PredictNames.GOProduct;
import static com.ccnt.tcmbio.data.PredictNames.GOSynonym;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.ccnt.tcmbio.controller.MappingController;
import com.ccnt.tcmbio.dao.TermDAO;
import com.ccnt.tcmbio.data.DiseaseData;
import com.ccnt.tcmbio.data.DrugData;
import com.ccnt.tcmbio.data.GeneData;
import com.ccnt.tcmbio.data.ProteinData;
import com.ccnt.tcmbio.data.TcmData;
//import static com.ccnt.tcmbio.data.GraphNames.Protein_Gene_Mapping;

public class TermDAOImpl extends JdbcDaoSupport implements TermDAO{

    private static final Logger LOGGER = LogManager.getLogger(MappingController.class.getName());

    @Override
    public ArrayList<DiseaseData> searchDisease(final String keyword, final String start, final String offset){
        try {
            final String sparql0 = "sparql select distinct ?diseaseName where { graph<" +
            		Tcm_Diseasesome_Mapping + "> {?diseaseName ?p ?o " +
                            "FILTER regex(?diseaseName, \"" + TCMGeneDITID + "disease/.*" + keyword + "\", \"i\")}} " +
                            		"limit(" + offset + ") offset(" + start + ")";

            LOGGER.debug("search disease query virtuoso: {}", sparql0);

            final List<Map<String, Object>> list0 = getJdbcTemplate().queryForList(sparql0);

            final ArrayList<DiseaseData> diseaseDatas = new ArrayList<DiseaseData>();
            DiseaseData diseaseData = new DiseaseData();

            final Set<String> diseaseNameSet = new HashSet<String>();
            final Set<String> diseaseIDSet = new HashSet<String>();
            final Set<String> diseaseIDInDrugBankSet = new HashSet<String>();
            final Set<String> drugIDSet = new HashSet<String>();
            final Set<String> drugNameSet = new HashSet<String>();
            final Set<String> tcmNameSet = new HashSet<String>();
            final Set<String> geneIDSet = new HashSet<String>();
            final Set<String> xRefsSet = new HashSet<String>();
            final Set<String> definitionSet = new HashSet<String>();
            final Set<String> synonymSet = new HashSet<String>();

            for (final Map<String, Object> row0 : list0) {
                final String diseaseSearchNameString = row0.get("diseaseName").toString();
                final String sparql1 = "sparql select * where {"
                        + "graph<" + Tcm_Diseasesome_Mapping + "> {<" + diseaseSearchNameString + "> owl:sameAs ?diseaseID }." +
                        " optional { graph<" + Diseasome + "> {?diseaseID diseasesome:possibleDrug ?drugID }} ." +
                        "optional { graph<" + DrugBank + "> {?drugID rdfs:label ?drugName}} ." +
                        "optional { graph<" + TCMGeneDIT + "> {?tcmName TCMGeneDIT:treatment <"+ diseaseSearchNameString +
                                ">}}} order by ?diseaseName";

                LOGGER.debug("search diseasfinal List<Map<String, Object>> list1 = getJdbcTemplate().queryForList(sparql1);e query virtuoso: {}", sparql1);

                final List<Map<String, Object>> list1 = getJdbcTemplate().queryForList(sparql1);
                int flag = 0;

                for (final Map<String, Object> row1 : list1) {

                    diseaseNameSet.add(diseaseSearchNameString);
                    if (row1.get("diseaseID") != null) {
                        diseaseIDSet.add(row1.get("diseaseID").toString());
                    }
                    if (row1.get("drugID") != null) {
                        drugIDSet.add(row1.get("drugID").toString());
                    }
                    if (row1.get("drugName") != null) {
                        drugNameSet.add(row1.get("drugName").toString());
                    }
                    if (row1.get("tcmName") != null) {
                        tcmNameSet.add(row1.get("tcmName").toString());
                    }

                    if (flag == 0) {
                        final String[] diseaseNameStrings = diseaseSearchNameString.split("/");
                        final String sparql2 = "sparql select * where {" +
                                "graph<" + FunDO + "> {?diseaseName <http://purl.org/net/tcm/tcm.lifescience.ntu.edu.tw/association> ?geneID " +
                                        "FILTER regex(?diseaseName, \"" + FunDODisease + ".*" + diseaseNameStrings[diseaseNameStrings.length-1] + "\", \"i\")}}";

                        LOGGER.debug("search disease query virtuoso: {}", sparql2);

                        final List<Map<String, Object>> list2 = getJdbcTemplate().queryForList(sparql2);
                        for (final Map<String, Object> row2 : list2) {
                            geneIDSet.add(row2.get("geneID").toString());
                        }

                        final String sparql4 = "sparql select * where {graph<" + DiseaseNameIDMapping + "> { <" +
                                diseaseSearchNameString + "> owl:sameAs ?diseaseID}}";

                        LOGGER.debug("search disease query virtuoso: {}", sparql4);

                        final List<Map<String, Object>> list4 = getJdbcTemplate().queryForList(sparql4);
                        for (final Map<String, Object> row4 : list4) {
                            final String diseaseIDInDrugBank = DrugBankDiseaseIDPrefix + row4.get("diseaseID").toString().split("_")[1];
                            final String sparql3 = "sparql select * where { graph<" + DiseaseOntology + "> { " +
                                    "<" + diseaseIDInDrugBank + "> <" + DiseaseOntologyPrefix + "notation> ?xrefs. " +
                                    "optional {<" + diseaseIDInDrugBank  + "> <" + DiseaseOntologyPrefix + "definition> ?definition}. " +
                                    "optional {<" + diseaseIDInDrugBank + "> <" + DiseaseOntologyPrefix + "relatedSynonym> ?rSynonym}" +
                                    "}}";

                            LOGGER.debug("search disease query virtuoso: {}", sparql3);

                            final List<Map<String, Object>> list3 = getJdbcTemplate().queryForList(sparql3);
                            for (final Map<String, Object> row3 : list3) {

                                diseaseIDInDrugBankSet.add(diseaseIDInDrugBank);

                                if (row3.get("xrefs") != null) {
                                    xRefsSet.add(row3.get("xrefs").toString());
                                }
                                if (row3.get("definition") != null) {
                                    definitionSet.add(row3.get("definition").toString());
                                }
                                if (row3.get("rSynonym") != null) {
                                    synonymSet.add(row3.get("rSynonym").toString());
                                }
                            }
                        }
                        flag = 1;
                    }
                }

                diseaseData.setDefinition(definitionSet);
                diseaseData.setDiseaseID(diseaseIDSet);
                diseaseData.setDiseaseIDInDrugBank(diseaseIDInDrugBankSet);
                diseaseData.setDiseaseName(diseaseSearchNameString);
                diseaseData.setRelatedDrugID(drugIDSet);
                diseaseData.setRelatedDrugName(drugNameSet);
                diseaseData.setRelatedGene(geneIDSet);
                diseaseData.setRelatedSynonym(synonymSet);
                diseaseData.setRelatedTCM(tcmNameSet);
                diseaseData.setXrefs(xRefsSet);
                diseaseDatas.add(diseaseData);

                // clear all
                diseaseData = new DiseaseData();
                diseaseNameSet.clear();
                diseaseIDSet.clear();
                diseaseIDInDrugBankSet.clear();
                drugIDSet.clear();
                drugNameSet.clear();
                tcmNameSet.clear();
                geneIDSet.clear();
                xRefsSet.clear();
                definitionSet.clear();
                synonymSet.clear();
                flag = 0;
            }

            return diseaseDatas;

        } catch (final DataAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Integer searchDiseaseCount(final String keyword){
        try {
            final String sparql = "sparql select count(distinct ?diseaseName) as ?count where { graph<" +
                    Tcm_Diseasesome_Mapping + "> {?diseaseName ?p ?o " +
                            "FILTER regex(?diseaseName, \"" + TCMGeneDITID + "disease/.*" + keyword + "\", \"i\")}}";

            LOGGER.debug("search disease query virtuoso: {}", sparql);

            return getJdbcTemplate().queryForInt(sparql);

        } catch (final DataAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public ArrayList<GeneData> searchGene(final String keyword, final String start, final String offset){
        final String sparql0 = "sparql select distinct ?geneID where {graph<" + Gene_Ontology + "> {" +
        		GeneOntologyGeneIDPrefix + keyword + " ?p ?o}} limit(" + offset + ") offset(" + start + ")";

        LOGGER.debug("search gene query virtuoso: {}", sparql0);

        final List<Map<String, Object>> rows0 = getJdbcTemplate().queryForList(sparql0);
        final ArrayList<GeneData> geneDatas = new ArrayList<GeneData>();

        for (final Map<String, Object> row : rows0){
            final String geneID = row.get("geneID").toString();

            final String sparql1 = "sparql select * where {graph<" + Gene_Ontology + "> {" +
            		"<" + geneID + "> " + GODefinition + " ?definition . " +
            		"<" + geneID + "> " + GOID + " ?geneID . " +
            		"<" + geneID + "> " + GOSynonym + " ?synonym . " +
            		"<" + geneID + "> " + GONamespace + " ?namespace . " +
            		"<" + geneID + "> " + GOProduct + " ?product}}";

            final List<Map<String, Object>> rows2 = getJdbcTemplate().queryForList(sparql1);
            final GeneData geneData = new GeneData();
            final Set<String> synonymSet = new HashSet<String>();
            final boolean flag = true;
            for (final Map<String, Object> row2 : rows2){
                if (flag) {
                    geneData.setDefinition(row2.get("definition").toString());
                    geneData.setGeneID(row2.get("geneID").toString());
                    geneData.setGeneProduct(row2.get("product").toString());
                    geneData.setOntology(row2.get("namespace").toString());
                }
                synonymSet.add(row2.get("synonym").toString());
            }
            geneData.setSynonym(synonymSet);

            // todo
        }

        return null;
    }

    @Override
    public Integer searchGeneCount(final String keyword){
        return 0;
    }

    @Override
    public ArrayList<TcmData> searchTCM(final String keyword){
        return null;
    }

    @Override
    public ArrayList<ProteinData> searchProtein(final String keyword){
        return null;
    }

    @Override
    public ArrayList<DrugData> searchDrug(final String keyword){
        return null;
    }

}
