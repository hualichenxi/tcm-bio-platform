/**
 * BioInferServiceImpl.java
 * Copyright 2013 Hao Tong, all rights reserved.
 * PROPRIETARY/CONFIDENTIAL, any form of usage is subject to approval.
 */

package com.ccnt.tcmbio.service.impl;

import static com.ccnt.tcmbio.data.Namespaces.DiseaseSome;
import static com.ccnt.tcmbio.data.Namespaces.DrugBank;
import static com.ccnt.tcmbio.data.Namespaces.GeneOntology;
import static com.ccnt.tcmbio.data.Namespaces.TCMGeneDITID;
import static com.ccnt.tcmbio.data.Namespaces.UNIPROT;
import java.util.ArrayList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.ccnt.tcmbio.dao.BioInferDao;
import com.ccnt.tcmbio.data.BioInferData;
import com.ccnt.tcmbio.data.BioInferSearchData;
import com.ccnt.tcmbio.data.TcmInferData;
import com.ccnt.tcmbio.data.graph.Graph;
import com.ccnt.tcmbio.data.graph.Graphml;
import com.ccnt.tcmbio.service.BioInferService;
import com.ccnt.tcmbio.service.CreateGraphService;

public class BioInferServiceImpl implements BioInferService{

    private BioInferDao bioInferDao;
    private CreateGraphService createGraphService;
    private static final Logger LOGGER = LogManager.getLogger(BioInferServiceImpl.class.getName());

    public void setBioInferDao(final BioInferDao bioInferDao) {
        this.bioInferDao = bioInferDao;
    }

    public void setCreateGraphService(final CreateGraphService createGraphService) {
        this.createGraphService = createGraphService;
    }


    @Override
    public BioInferSearchData getBioInference(final String bioName, final Integer start, final Integer offset){

        LOGGER.debug("get the bio inference result");
        try {
            final BioInferSearchData bioSearchData = new BioInferSearchData();

            if (!searchTcm(bioName)) {
                bioSearchData.setFuzzymatchTCM(fuzzyMatchTcm(bioName));
                bioSearchData.setStatus(false);
                bioSearchData.setBioInferData(null);
                return bioSearchData;
            }

            String bio = bioName.substring(0, 1).toUpperCase() + bioName.substring(1).toLowerCase();
            bio = bio.replace(" ", "_");
//            bio = DrugBank + "drugs/" + bio;
            final ArrayList<BioInferData> bioInferData = bioInferDao.getBioInference(bio, start, offset);
            bioSearchData.setStatus(true);
            bioSearchData.setBioInferData(bioInferData);
            bioSearchData.setFuzzymatchTCM(null);
            bioSearchData.setTotalNum(bioInferDao.getBioInferCount(bio));
            return bioSearchData;
        } catch (final Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ArrayList<TcmInferData> getAndCacheTcmInference(final String tcmName){
        return null;
    }

    @Override
    public boolean searchTcm(final String tcmName){
        return true;
    }

    @Override
    public ArrayList<String> fuzzyMatchTcm(final String tcmName){
        return null;
    }


    @Override
    public Graphml getDiseaseName(final String tcmName, final Integer start, final Integer offset){
        try {
        	String tcm = tcmName.substring(0, 1).toUpperCase() + tcmName.substring(1).toLowerCase();
            tcm = tcm.replace(" ", "_");
            final String url = TCMGeneDITID + "medicine/" + tcm;
            final ArrayList<String> leaves = bioInferDao.getDiseaseName("<" + url + ">", start, offset);
            final Graph graph = createGraphService.createGraph(url, leaves, "node#0", false, "node#1", "treatment", "edge#0", "G#0", "directed", "0", "1");
            return new Graphml(graph);
        } catch (final Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Graphml getDiseaseID(final String diseaseName, final Integer start, final Integer offset){
        try {
            final String url = TCMGeneDITID + "disease/" + diseaseName;
            final ArrayList<String> leaves = bioInferDao.getDiseaseID("<" + url + ">", start, offset);
            final Graph graph = createGraphService.createGraph(url, leaves, "node#0", false, "node#1", "treatment", "edge#0", "G#0", "directed" ,"1", "2");
            return new Graphml(graph);
        } catch (final Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Graphml getDrugID(final String diseaseID, final Integer start, final Integer offset){
        try {
            final String url = DiseaseSome + "diseases/" + diseaseID;
            final ArrayList<String> leaves = bioInferDao.getDrugID("<" + url + ">", start, offset);
            final Graph graph = createGraphService.createGraph(url, leaves, "node#0", false, "node#1", "possibleDrug", "edge#0", "G#0", "directed" ,"2", "3");
            return new Graphml(graph);
        } catch (final Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Graphml getTargetID(final String drugID, final Integer start, final Integer offset){
        try {
            final String url = DrugBank + "drugs/" + drugID;
            final ArrayList<String> leaves = bioInferDao.getTargetID("<" + url + ">", start, offset);
            final Graph graph = createGraphService.createGraph(url, leaves, "node#0", false, "node#1", "target", "edge#0", "G#0", "directed", "3", "4");
            return new Graphml(graph);
        } catch (final Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Graphml getProtein(final String targetID, final Integer start, final Integer offset){
        try {
            final String url = DrugBank + "targets/" + targetID;
            final ArrayList<String> leaves = bioInferDao.getProtein("<" + url + ">", start, offset);
            final Graph graph = createGraphService.createGraph(url, leaves, "node#0", false, "node#1", "swissprotId", "edge#0", "G#0", "directed", "4", "5");
            return new Graphml(graph);
        } catch (final Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Graphml getGeneID(final String protein, final Integer start, final Integer offset){
        try {
            final String url = UNIPROT + protein;
            final ArrayList<String> leaves = bioInferDao.getGeneID("<" + url + ">", start, offset);
            final Graph graph = createGraphService.createGraph(url, leaves, "node#0", false, "node#1", "classifiedWith", "edge#0", "G#0", "directed" ,"5", "6");
            return new Graphml(graph);
        } catch (final Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Graphml getGeneProduct(final String geneID, final Integer start, final Integer offset){
        try {
            final String url = GeneOntology + geneID;
            final ArrayList<String> leaves = bioInferDao.getGeneProduct("<" + url + ">", start, offset);
            final Graph graph = createGraphService.createGraph(url, leaves, "node#0", false, "node#1", "label", "edge#0", "G#0", "directed" ,"6", "7");
            return new Graphml(graph);
        } catch (final Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

}
