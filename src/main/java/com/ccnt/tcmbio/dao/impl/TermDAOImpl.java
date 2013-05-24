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
import static com.ccnt.tcmbio.data.GraphNames.TCMGeneDIT;
import static com.ccnt.tcmbio.data.GraphNames.Tcm_Diseasesome_Mapping;
import static com.ccnt.tcmbio.data.Namespaces.DrugBankDiseaseIDPrefix;
import static com.ccnt.tcmbio.data.Namespaces.FunDODisease;
import static com.ccnt.tcmbio.data.Namespaces.TCMGeneDITID;
import static com.ccnt.tcmbio.data.PredictNames.DiseaseOntologyPrefix;

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
//import static com.ccnt.tcmbio.data.GraphNames.Gene_Ontology;
//import static com.ccnt.tcmbio.data.GraphNames.Protein_Gene_Mapping;

public class TermDAOImpl extends JdbcDaoSupport implements TermDAO{

    private static final Logger LOGGER = LogManager.getLogger(MappingController.class.getName());

    @Override
    public ArrayList<DiseaseData> searchDisease(final String keyword){
        try {
            final String sparql1 = "sparql select * where {"
                    + "graph<" + Tcm_Diseasesome_Mapping + "> {?diseaseName owl:sameAs ?diseaseID " +
                    		"FILTER regex(?diseaseName, \"" + TCMGeneDITID + "disease/.*" + keyword + "\", \"i\")} . " +
                    " optional { graph<" + Diseasome + "> {?diseaseID diseasesome:possibleDrug ?drugID }} ." +
                    "optional { graph<" + DrugBank + "> {?drugID rdfs:label ?drugName}} ." +
                    "optional { graph<" + TCMGeneDIT + "> {?tcmName TCMGeneDIT:treatment ?diseaseName " +
                    		"FILTER regex(?diseaseName, \"" + TCMGeneDITID + "disease/.*" + keyword + "\", \"i\")}}} order by ?diseaseName";

            LOGGER.debug("search disease query virtuoso: {}", sparql1);

            final List<Map<String, Object>> list1 = getJdbcTemplate().queryForList(sparql1);

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

            int flag = 0;
            String lastDisease = new String();

            for (final Map<String, Object> row : list1) {

                final String diseaseNameString = row.get("diseaseName").toString();
                if (!diseaseNameSet.isEmpty() && !diseaseNameSet.contains(diseaseNameString) || row == list1.get(list1.size()-1)) {

                    diseaseData.setDefinition(definitionSet);
                    diseaseData.setDiseaseID(diseaseIDSet);
                    diseaseData.setDiseaseIDInDrugBank(diseaseIDInDrugBankSet);
                    diseaseData.setDiseaseName(lastDisease);
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

                lastDisease = diseaseNameString;
                diseaseNameSet.add(diseaseNameString);
                if (row.get("diseaseID") != null) {
                    diseaseIDSet.add(row.get("diseaseID").toString());
                }

                if (row.get("drugID") != null) {
                    drugIDSet.add(row.get("drugID").toString());
                }
                if (row.get("drugName") != null) {
                    drugNameSet.add(row.get("drugName").toString());
                }
                if (row.get("tcmName") != null) {
                    tcmNameSet.add(row.get("tcmName").toString());
                }

                if (flag == 0) {
                    final String[] diseaseNameStrings = diseaseNameString.split("/");
                    final String sparql2 = "sparql select * where {" +
                            "graph<" + FunDO + "> {?diseaseName <http://purl.org/net/tcm/tcm.lifescience.ntu.edu.tw/association> ?geneID " +
                                    "FILTER regex(?diseaseName, \"" + FunDODisease + ".*" + diseaseNameStrings[diseaseNameStrings.length-1] + "\", \"i\")}}";

                    LOGGER.debug("search disease query virtuoso: {}", sparql2);

                    final List<Map<String, Object>> list2 = getJdbcTemplate().queryForList(sparql2);
                    for (final Map<String, Object> row2 : list2) {
                        geneIDSet.add(row2.get("geneID").toString());
                    }

                    final String sparql4 = "sparql select * where {graph<" + DiseaseNameIDMapping + "> { <" +
                    		diseaseNameString + "> owl:sameAs ?diseaseID}}";

                    LOGGER.debug("search disease query virtuoso: {}", sparql4);

                    final List<Map<String, Object>> list4 = getJdbcTemplate().queryForList(sparql4);
                    for (final Map<String, Object> row4 : list4) {
//                        final String sparql3 = "sparql select * where {" +
//                                "graph<" + DiseaseOntology + "> {?diseaseID <http://www.w3.org/2004/02/skos/core#prefLabel> ?diseaseName " +
//                                        "FILTER regex(?diseaseName, \"" + diseaseNameStrings[diseaseNameStrings.length-1] + "\", \"i\"). " +
//                                "?diseaseID <" + DiseaseOntologyPrefix + "notation> ?xrefs." +
//                                "?diseaseID <" + DiseaseOntologyPrefix + "definition> ?definition." +
//                                "?diseaseID <" + DiseaseOntologyPrefix + "relatedSynonym> ?rSynonym." +
//                                "}}";
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
            return diseaseDatas;

        } catch (final DataAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public GeneData searchGene(final String keyword){
        return null;
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
