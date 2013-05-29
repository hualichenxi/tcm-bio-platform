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
import static com.ccnt.tcmbio.data.GraphNames.Gene2GO;
import static com.ccnt.tcmbio.data.GraphNames.GeneNameToIDMapping;
import static com.ccnt.tcmbio.data.GraphNames.GeneOntology;
import static com.ccnt.tcmbio.data.GraphNames.TCMGeneDIT;
import static com.ccnt.tcmbio.data.GraphNames.Tcm_Diseasesome_Mapping;
import static com.ccnt.tcmbio.data.GraphNames.Uniprot_Protein_Entrez_ID;
import static com.ccnt.tcmbio.data.GraphNames.Uniprot_Protein_GO;
import static com.ccnt.tcmbio.data.Namespaces.DrugBankDiseaseIDPrefix;
import static com.ccnt.tcmbio.data.Namespaces.FunDODisease;
import static com.ccnt.tcmbio.data.Namespaces.GeneIDPrefix;
import static com.ccnt.tcmbio.data.Namespaces.GeneOntologyGeneIDPrefix;
import static com.ccnt.tcmbio.data.Namespaces.TCMGeneDITID;
import static com.ccnt.tcmbio.data.PredictNames.DiseaseOntologyPrefix;
import static com.ccnt.tcmbio.data.PredictNames.GODefinition;
import static com.ccnt.tcmbio.data.PredictNames.GOID;
import static com.ccnt.tcmbio.data.PredictNames.GONamespace;
import static com.ccnt.tcmbio.data.PredictNames.GOProduct;
import static com.ccnt.tcmbio.data.PredictNames.GOSynonym;
import static com.ccnt.tcmbio.data.PredictNames.TCMGeneDITPrefix;
import static com.ccnt.tcmbio.data.PredictNames.UniprotGO_ClassifiedWith;

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
import com.ccnt.tcmbio.data.GeneIDData;
import com.ccnt.tcmbio.data.TCMData;
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
    public ArrayList<GeneData> searchGOID(final String keyword, final String start, final String offset){
        try {
            final String sparql0 = "sparql select distinct ?GOID where {graph<" + GeneOntology + "> {?GOID ?p ?o " +
            				"filter regex(?GOID, \"" + GeneOntologyGeneIDPrefix + ".*" + keyword + "\", \"i\")}} " +
            						"limit(" + offset + ") offset(" + start + ")";

            LOGGER.debug("query for GOID: {}", sparql0);
            final List<Map<String, Object>> rows0 = getJdbcTemplate().queryForList(sparql0);

            final ArrayList<GeneData> geneDatas = new ArrayList<GeneData>();

            for (final Map<String, Object> row0 : rows0){
                final String goID = row0.get("GOID").toString();

                final String sparql1 = "sparql select * where {graph<" + GeneOntology + "> {" +
                		"optional {<" + goID + "> <" + GODefinition + "> ?definition} . " +
                		"optional {<" + goID + "> <" + GOID + "> ?GOID} . " +
                		"optional {<" + goID + "> <" + GOSynonym + "> ?synonym} . " +
                		"optional {<" + goID + "> <" + GONamespace + "> ?namespace} . " +
                		"optional {<" + goID + "> <" + GOProduct + "> ?product}}}";

                LOGGER.debug("query for gene detail: {}", sparql1);
                final List<Map<String, Object>> rows1 = getJdbcTemplate().queryForList(sparql1);

                final GeneData geneData = new GeneData();
                final Set<String> synonymSet = new HashSet<String>();
                boolean flag = true;
                for (final Map<String, Object> row1 : rows1){
                    if (flag) {
                        if(row1.get("definition") != null){
                            geneData.setDefinition(row1.get("definition").toString());
                        }
                        if(row1.get("GOID") != null){
                            geneData.setGeneID(row1.get("GOID").toString());
                        }
                        if(row1.get("product") != null){
                            geneData.setGeneProduct(row1.get("product").toString());
                        }
                        if(row1.get("namespace") != null){
                            geneData.setOntology(row1.get("namespace").toString());
                        }
                        flag = false;
                    }
                    if(row1.get("synonym") != null){
                        synonymSet.add(row1.get("synonym").toString());
                    }
                }
                geneData.setSynonym(synonymSet);

                final String sparql2 = "sparql select * where {graph<" + Uniprot_Protein_GO + "> {" +
                		"?proteinID <" + UniprotGO_ClassifiedWith + "> <" + goID + ">}}";

                LOGGER.debug("query for protein: {}", sparql2);
                final List<Map<String, Object>> rows2 = getJdbcTemplate().queryForList(sparql2);

                final Set<String> proteinIDSet = new HashSet<String>();
                for (final Map<String, Object> row2 : rows2){
                    if(row2.get("proteinID")!=null){
                        proteinIDSet.add(row2.get("proteinID").toString());
                    }
                }
                geneData.setRelatedProteinSet(proteinIDSet);

                final String sparql3 = "sparql select ?tcmName ?diseaseName where {" +
                		"graph<" + Gene2GO + ">{?geneID TCMGeneDIT:association <" + goID + ">} . " +
                        "optional {graph<" + FunDO + ">{?diseaseName TCMGeneDIT:association ?geneID}} . " +
                		"optional {graph<" + GeneNameToIDMapping + ">{?geneName owl:sameAs ?geneID}} . " +
                		"optional {graph<" + TCMGeneDIT + "> {?tcmName TCMGeneDIT:association ?geneName}} }";

                LOGGER.debug("query for tcm: {}", sparql3);
                final List<Map<String, Object>> rows3 = getJdbcTemplate().queryForList(sparql3);

                final Set<String> tcmSet = new HashSet<String>();
                final Set<String> diseaseNameSet = new HashSet<String>();
                for (final Map<String, Object> row3 : rows3){
                    if (row3.get("tcmName")!=null) {
                        tcmSet.add(row3.get("tcmName").toString());
                    }
                    if (row3.get("diseaseName")!=null) {
                        diseaseNameSet.add(row3.get("diseaseName").toString());
                    }
                }
                geneData.setRelatedTCMSet(tcmSet);
                geneData.setRelatedDiseaseNameSet(diseaseNameSet);

                geneDatas.add(geneData);
            }
            return geneDatas;
        } catch (final DataAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Integer searchGOIDCount(final String keyword){
        try {
            final String sparql = "sparql select count(distinct ?GOID) where {graph<" + GeneOntology + "> {?GOID ?p ?o " +
                    "filter regex(?GOID, \"" + GeneOntologyGeneIDPrefix + ".*" + keyword + "\", \"i\")}} ";

            LOGGER.debug("query for fuzzy GOID count: {}", sparql);
            return getJdbcTemplate().queryForInt(sparql);
        } catch (final DataAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public ArrayList<TCMData> searchTCM(final String keyword, final String start, final String offset){

        try {

            final String sparql = "sparql select distinct ?tcmName where {graph<" + TCMGeneDIT + "> " +
                    "{?tcmName ?p ?o " +
                    "filter regex(?tcmName, \"" + TCMGeneDITID + "medicine/.*" + keyword.replace(" ", "_") + ".*\", \"i\")}} " +
                    		"limit(" + offset + ") offset(" + start + ")";

            LOGGER.debug("query for tcm: {}", sparql);
            List<Map<String, Object>> rows0 = getJdbcTemplate().queryForList(sparql);
            if (rows0.size()==0) {
                String sparql0 = "sparql select distinct ?tcmName where {graph<" + TCMGeneDIT + "> " +
                        "{?tcmName ?p ?o " +
                        "filter regex(?tcmName, \"" + TCMGeneDITID + "medicine/.*(";

                if(keyword.contains(" ")){
                    final String[] kws = keyword.split(" ");
                    for (final String kw : kws){
                        sparql0 +=  kw;
                        if(kw != kws[kws.length-1]){
                            sparql0 += "|";
                        }
                    }
                } else {
                    sparql0 += keyword;
                }
                sparql0 += ").*\", \"i\")}} limit(" + offset + ") offset(" + start + ")";

                LOGGER.debug("query for tcm: {}", sparql0);
                rows0 = getJdbcTemplate().queryForList(sparql0);
            }

            final ArrayList<TCMData> tcmDatas = new ArrayList<TCMData>();

            for (final Map<String, Object> row0 : rows0){
                final String tcmName = row0.get("tcmName").toString();

                final String sparql1 = "sparql select * where {graph<" + TCMGeneDIT + ">" +
                		"{ <" + tcmName + "> <" + TCMGeneDITPrefix + "effect> ?effect }} ";
                final String sparql2 = "sparql select * where {graph<" + TCMGeneDIT + "> " +
                        "{ <" + tcmName + "> <" + TCMGeneDITPrefix + "treatment> ?disease }}";
                final String sparql3 = "sparql select * where {graph<" + TCMGeneDIT + "> " +
                        "{ <" + tcmName + "> <" + TCMGeneDITPrefix + "ingredient> ?ingredient }}";
                final String sparql4 = "sparql select * where {graph<" + TCMGeneDIT + "> " +
                        "{<" + tcmName + "> <" + TCMGeneDITPrefix + "association> ?gene }}";

                final TCMData tcmData = new TCMData();
                tcmData.setTcmName(tcmName);

                final Set<String> effectSet = new HashSet<String>();
                final Set<String> ingredientSet = new HashSet<String>();
                final Set<String> diseaSet = new HashSet<String>();
                final Set<String> geneSet = new HashSet<String>();

                LOGGER.debug("query for tcm detail info: {}", sparql1);
                final List<Map<String, Object>> rows1 = getJdbcTemplate().queryForList(sparql1);
                for (final Map<String, Object> row1 : rows1){
                    if (row1.get("effect") != null) {
                        effectSet.add(row1.get("effect").toString());
                    }
                }

                LOGGER.debug("query for tcm detail info: {}", sparql2);
                final List<Map<String, Object>> rows2 = getJdbcTemplate().queryForList(sparql2);
                for (final Map<String, Object> row : rows2){
                    if (row.get("disease") != null) {
                        diseaSet.add(row.get("disease").toString());
                    }
                }

                LOGGER.debug("query for tcm detail info: {}", sparql3);
                final List<Map<String, Object>> rows3 = getJdbcTemplate().queryForList(sparql3);
                for (final Map<String, Object> row : rows3){
                    if (row.get("ingredient") != null) {
                        ingredientSet.add(row.get("ingredient").toString());
                    }
                }

                LOGGER.debug("query for tcm detail info: {}", sparql4);
                final List<Map<String, Object>> rows4 = getJdbcTemplate().queryForList(sparql4);
                for (final Map<String, Object> row : rows4){
                    if (row.get("gene") != null) {
                        geneSet.add(row.get("gene").toString());
                    }
                }

                tcmData.setEffect(effectSet);
                tcmData.setIngredient(ingredientSet);
                tcmData.setRelatedGene(diseaSet);
                tcmData.setTreatment(diseaSet);
                tcmDatas.add(tcmData);
            }

            return tcmDatas;
        } catch (final DataAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Integer searchTcmCount(final String keyword){

        try {
            Integer count = 0;

            final String sparql0 = "sparql select count(distinct ?tcmName) as ?count where {graph<" + TCMGeneDIT + "> " +
                    "{?tcmName ?p ?o " +
                    "filter regex(?tcmName, \"" + TCMGeneDITID + "medicine/.*" + keyword + ".*\", \"i\")}}";
            count = getJdbcTemplate().queryForInt(sparql0);

            if (count==0) {
                String sparql = "sparql select count(distinct ?tcmName) as ?count where {graph<" + TCMGeneDIT + "> " +
                        "{?tcmName ?p ?o " +
                        "filter regex(?tcmName, \"" + TCMGeneDITID + "medicine/.*(";

                if(keyword.contains(" ")){
                    final String[] kws = keyword.split(" ");
                    for (final String kw : kws){
                        sparql += kw;
                        if(kw != kws[kws.length-1]){
                            sparql += "|";
                        }
                    }
                } else {
                    sparql += keyword;
                }
                sparql += ").*\", \"i\")}}";

                LOGGER.debug("query for tcm fuzzy count: {}", sparql);
                count = getJdbcTemplate().queryForInt(sparql);
            }

            return count;
        } catch (final DataAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return 0;
    }

    @Override
    public ArrayList<DrugData> searchDrug(final String keyword, final String start, final String offset){

        try {

            final String sparql = "sparql select distinct ?drugName ?drugID where {graph<" + DrugBank + ">" +
                    "{?drugID rdfs:label ?drugName " +
                    "filter regex(?drugName, \".*" + keyword + ".*\", \"i\")}} limit(" + offset + ") offset(" + start + ")";

            LOGGER.debug("query for drug: {}", sparql);
            List<Map<String, Object>> rows0 = getJdbcTemplate().queryForList(sparql);
            if (rows0.size()==0) {
                String sparql0 = "sparql select distinct ?drugName ?drugID where {graph<" + DrugBank + ">" +
                        "{?drugID rdfs:label ?drugName " +
                        "filter regex(?drugName, \".*(";

                if(keyword.contains(" ")){
                    final String[] kws = keyword.split(" ");
                    for (final String kw : kws){
                        sparql0 += kw + "|";
                    }
                    sparql0 += keyword.replace(" ", ".*");
                } else {
                    sparql0 += keyword;
                }
                sparql0 += ").*\", \"i\")}} limit(" + offset + ") offset(" + start + ")";

                LOGGER.debug("query for drug: {}", sparql0);
                rows0 = getJdbcTemplate().queryForList(sparql0);
            }

            final ArrayList<DrugData> drugDatas = new ArrayList<DrugData>();

            for (final Map<String, Object> row0 : rows0){
                final String drugID = row0.get("drugID").toString();
                final String drugName = row0.get("drugName").toString();

                final DrugData drugData = new DrugData();
                drugData.setDrugID(drugID);
                drugData.setDrugName(drugName);

                final Set<String> brandName = new HashSet<String>();
                final Set<String> drugCategory = new HashSet<String>();
                final Set<String> pages = new HashSet<String>();
                final Set<String> diseaseTarget = new HashSet<String>();

                final String sparql1 = "sparql select * where {graph<" + DrugBank + ">{" +
                		"<" + drugID + "> drugbank:state ?state}}";
                final String sparql2 = "sparql select * where {graph<" + DrugBank + ">{" +
                        "<" + drugID + "> drugbank:brandName ?brandName}}";
                final String sparql3 = "sparql select * where {graph<" + DrugBank + ">{" +
                        "<" + drugID + "> drugbank:description ?description}}";
                final String sparql4 = "sparql select * where {graph<" + DrugBank + ">{" +
                        "<" + drugID + "> drugbank:drugCategory ?drugCategory}}";
                final String sparql5 = "sparql select * where {graph<" + DrugBank + ">{" +
                        "<" + drugID + "> drugbank:mechanismOfAction  ?mechanismOfAction}}";
                final String sparql6 = "sparql select * where {graph<" + DrugBank + ">{" +
                        "<" + drugID + "> foaf:page ?pages}}";
                final String sparql7 = "sparql select * where {graph<" + DrugBank + ">{" +
                        "<" + drugID + "> drugbank:affectedOrganism ?affectedPrganism}}";
                final String sparql8 = "sparql select * where {graph<" + DrugBank + ">{" +
                        "<" + drugID + "> drugbank:possibleDiseaseTarget ?diseaseTarget}}";

                LOGGER.debug("query for drug detail info: {}", sparql1);
                final List<Map<String, Object>> rows1 = getJdbcTemplate().queryForList(sparql1);
                for (final Map<String, Object> row : rows1){
                    if(row.get("state")!=null){
                        drugData.setState(row.get("state").toString());
                    }
                }

                LOGGER.debug("query for drug detail info: {}", sparql2);
                final List<Map<String, Object>> rows2 = getJdbcTemplate().queryForList(sparql2);
                for (final Map<String, Object> row : rows2){
                    if(row.get("brandName")!=null){
                        brandName.add(row.get("brandName").toString());
                    }
                }
                drugData.setBrandName(brandName);

                LOGGER.debug("query for drug detail info: {}", sparql3);
                final List<Map<String, Object>> rows3 = getJdbcTemplate().queryForList(sparql3);
                for (final Map<String, Object> row : rows3){
                    if(row.get("description")!=null){
                        drugData.setDescription(row.get("description").toString());
                    }
                }

                LOGGER.debug("query for drug detail info: {}", sparql4);
                final List<Map<String, Object>> rows4 = getJdbcTemplate().queryForList(sparql4);
                for (final Map<String, Object> row : rows4){
                    if(row.get("drugCategory")!=null){
                        drugCategory.add(row.get("drugCategory").toString());
                    }
                }
                drugData.setDrugCategory(drugCategory);

                LOGGER.debug("query for drug detail info: {}", sparql5);
                final List<Map<String, Object>> rows5 = getJdbcTemplate().queryForList(sparql5);
                for (final Map<String, Object> row : rows5){
                    if(row.get("mechanismOfAction")!=null){
                        drugData.setMechanismOfAction(row.get("mechanismOfAction").toString());
                    }
                }

                LOGGER.debug("query for drug detail info: {}", sparql6);
                final List<Map<String, Object>> rows6 = getJdbcTemplate().queryForList(sparql6);
                for (final Map<String, Object> row : rows6){
                    if(row.get("pages")!=null){
                        pages.add(row.get("pages").toString());
                    }
                }
                drugData.setPages(pages);

                LOGGER.debug("query for drug detail info: {}", sparql7);
                final List<Map<String, Object>> rows7 = getJdbcTemplate().queryForList(sparql7);
                for (final Map<String, Object> row : rows7){
                    if(row.get("affectedPrganism")!=null){
                        drugData.setAffectedPrganism(row.get("affectedPrganism").toString());
                    }
                }

                LOGGER.debug("query for drug detail info: {}", sparql8);
                final List<Map<String, Object>> rows8 = getJdbcTemplate().queryForList(sparql8);
                for (final Map<String, Object> row : rows8){
                    if(row.get("diseaseTarget")!=null){
                        diseaseTarget.add(row.get("diseaseTarget").toString());
                    }
                }
                drugData.setDiseaseTarget(diseaseTarget);

                drugDatas.add(drugData);
            }
            return drugDatas;
        } catch (final DataAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Integer searchDrugCount(final String keyword){

        try {
            Integer count = 0;

            final String sparql = "sparql select count(distinct ?drugID) where {graph<" + DrugBank + ">" +
                    "{?drugID rdfs:label ?drugName " +
                    "filter regex(?drugName, \".*" + keyword + ".*\", \"i\")}} ";

            LOGGER.debug("query for drug: {}", sparql);
            count = getJdbcTemplate().queryForInt(sparql);
            if (count==0) {
                String sparql0 = "sparql select count(distinct ?drugID) where {graph<" + DrugBank + ">" +
                        "{?drugID rdfs:label ?drugName " +
                        "filter regex(?drugName, \".*(";

                if(keyword.contains(" ")){
                    final String[] kws = keyword.split(" ");
                    for (final String kw : kws){
                        sparql0 += kw + "|";
                    }
                    sparql0 += keyword.replace(" ", ".*");
                } else {
                    sparql0 += keyword;
                }
                sparql0 += ").*\", \"i\")}} ";

                LOGGER.debug("query for drug: {}", sparql0);
                count = getJdbcTemplate().queryForInt(sparql0);
            }

            return count;
        } catch (final DataAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return 0;
    }

    @Override
    public ArrayList<GeneIDData> searchGeneID(final String keyword, final String start, final String offset){
        try {
            final String sparql0 = "sparql select distinct ?geneID where {graph<" + Gene2GO + "> {?geneID ?p ?o " +
                            "filter regex(?geneID, \"" + GeneIDPrefix + ".*" + keyword + "\", \"i\")}} " +
                                    "limit(" + offset + ") offset(" + start + ")";

            LOGGER.debug("query for geneID: {}", sparql0);
            final List<Map<String, Object>> rows0 = getJdbcTemplate().queryForList(sparql0);

            final ArrayList<GeneIDData> geneIDDatas = new ArrayList<GeneIDData>();

            for (final Map<String, Object> row0 : rows0){
                final String geneID = row0.get("geneID").toString();

                final String sparql1 = "sparql select ?GOID where {graph<" + Gene2GO + "> {" +
                        "<" + geneID + "> <" + TCMGeneDITPrefix + "association> ?GOID}}";

                LOGGER.debug("query for related GOID: {}", sparql1);
                final List<Map<String, Object>> rows1 = getJdbcTemplate().queryForList(sparql1);

                final GeneIDData geneIDData = new GeneIDData();
                geneIDData.setGeneID(geneID);
                final Set<String> goIDSet = new HashSet<String>();

                for (final Map<String, Object> row1 : rows1){
                    if(row1.get("GOID") != null){
                        goIDSet.add(row1.get("GOID").toString());
                    }
                }
                geneIDData.setGoID(goIDSet);

                final String sparql2 = "sparql select * where {graph<" + Uniprot_Protein_Entrez_ID + "> {" +
                        "?proteinID <" + UniprotGO_ClassifiedWith + "> <" + geneID + ">}}";

                LOGGER.debug("query for protein: {}", sparql2);
                final List<Map<String, Object>> rows2 = getJdbcTemplate().queryForList(sparql2);

                final Set<String> proteinIDSet = new HashSet<String>();
                for (final Map<String, Object> row2 : rows2){
                    if(row2.get("proteinID")!=null){
                        proteinIDSet.add(row2.get("proteinID").toString());
                    }
                }
                geneIDData.setRelatedProteinSet(proteinIDSet);

                final String sparql3 = "sparql select ?tcmName ?diseaseName where {" +
                        "optional {graph<" + FunDO + ">{?diseaseName TCMGeneDIT:association <" + geneID + ">}} . " +
                        "optional {graph<" + GeneNameToIDMapping + ">{?geneName owl:sameAs <" + geneID + ">}} . " +
                        "optional {graph<" + TCMGeneDIT + "> {?tcmName TCMGeneDIT:association ?geneName}} }";

                LOGGER.debug("query for tcm and diseaseName: {}", sparql3);
                final List<Map<String, Object>> rows3 = getJdbcTemplate().queryForList(sparql3);

                final Set<String> tcmSet = new HashSet<String>();
                final Set<String> diseaseNameSet = new HashSet<String>();
                for (final Map<String, Object> row3 : rows3){
                    if (row3.get("tcmName")!=null) {
                        tcmSet.add(row3.get("tcmName").toString());
                    }
                    if (row3.get("diseaseName")!=null) {
                        diseaseNameSet.add(row3.get("diseaseName").toString());
                    }
                }
                geneIDData.setRelatedTCMSet(tcmSet);
                geneIDData.setRelatedDiseaseNameSet(diseaseNameSet);

                geneIDDatas.add(geneIDData);
            }
            return geneIDDatas;
        } catch (final DataAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Integer searchGeneIDCount(final String keyword){
        try {
            final String sparql0 = "sparql select count(distinct ?geneID) where {graph<" + Gene2GO + "> {?geneID ?p ?o " +
                    "filter regex(?geneID, \"" + GeneIDPrefix + ".*" + keyword + "\", \"i\")}}";

            LOGGER.debug("query for geneID count: {}", sparql0);
            return getJdbcTemplate().queryForInt(sparql0);
        } catch (final DataAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return 0;
    }

}
