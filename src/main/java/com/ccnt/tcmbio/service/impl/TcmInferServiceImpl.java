/**
 * TcmInferServiceImpl.java
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

import com.ccnt.tcmbio.dao.TcmInferDao;
import com.ccnt.tcmbio.data.TcmInferData;
import com.ccnt.tcmbio.data.TcmInferSearchData;
import com.ccnt.tcmbio.data.graph.Graph;
import com.ccnt.tcmbio.data.graph.Graphml;
import com.ccnt.tcmbio.service.CreateGraphService;
import com.ccnt.tcmbio.service.TcmInferService;

public class TcmInferServiceImpl implements TcmInferService{

    private TcmInferDao tcmInferDao;
    private CreateGraphService createGraphService;
    private static final Logger LOGGER = LogManager.getLogger(TcmInferServiceImpl.class.getName());

    public void setTcmInferDao(final TcmInferDao tcmInferDao) {
        this.tcmInferDao = tcmInferDao;
    }

    public void setCreateGraphService(final CreateGraphService createGraphService) {
        this.createGraphService = createGraphService;
    }


    @Override
    public TcmInferSearchData getTcmInference(final String tcmName, final Integer start, final Integer offset){

        LOGGER.debug("get the tcm inference result");
        try {
            final TcmInferSearchData tcmSearchData = new TcmInferSearchData();

            if (!searchTcm(tcmName)) {
                tcmSearchData.setFuzzymatchTCM(fuzzyMatchTcm(tcmName));
                tcmSearchData.setStatus(false);
                tcmSearchData.setTcmInferData(null);
                return tcmSearchData;
            }

            String tcm = tcmName.substring(0, 1).toUpperCase() + tcmName.substring(1).toLowerCase();
            tcm = tcm.replace(" ", "_");
            tcm = TCMGeneDITID + "medicine/" + tcm;
            final ArrayList<TcmInferData> tcmInferData = tcmInferDao.getTcmInference(tcm, start, offset);
            tcmSearchData.setStatus(true);
            tcmSearchData.setTcmInferData(tcmInferData);
            tcmSearchData.setFuzzymatchTCM(null);
            tcmSearchData.setTotalNum(tcmInferDao.getTcmInferCount(tcm));
            return tcmSearchData;
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
            final String url = TCMGeneDITID + "medicine/" + tcmName;
            final ArrayList<String> leaves = tcmInferDao.getDiseaseName("<" + url + ">", start, offset);
            final Graph graph = createGraphService.createGraph(url, leaves, "node#0", false, "node#1", "treatment", "edge#0", "G#0", "directed");
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
            final ArrayList<String> leaves = tcmInferDao.getDiseaseID("<" + url + ">", start, offset);
            final Graph graph = createGraphService.createGraph(url, leaves, "node#0", false, "node#1", "treatment", "edge#0", "G#0", "directed");
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
            final ArrayList<String> leaves = tcmInferDao.getDrugID("<" + url + ">", start, offset);
            final Graph graph = createGraphService.createGraph(url, leaves, "node#0", false, "node#1", "possibleDrug", "edge#0", "G#0", "directed");
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
            final ArrayList<String> leaves = tcmInferDao.getTargetID("<" + url + ">", start, offset);
            final Graph graph = createGraphService.createGraph(url, leaves, "node#0", false, "node#1", "target", "edge#0", "G#0", "directed");
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
            final ArrayList<String> leaves = tcmInferDao.getProtein("<" + url + ">", start, offset);
            final Graph graph = createGraphService.createGraph(url, leaves, "node#0", false, "node#1", "swissprotId", "edge#0", "G#0", "directed");
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
            final ArrayList<String> leaves = tcmInferDao.getGeneID("<" + url + ">", start, offset);
            final Graph graph = createGraphService.createGraph(url, leaves, "node#0", false, "node#1", "classifiedWith", "edge#0", "G#0", "directed");
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
            final ArrayList<String> leaves = tcmInferDao.getGeneProduct("<" + url + ">", start, offset);
            final Graph graph = createGraphService.createGraph(url, leaves, "node#0", false, "node#1", "label", "edge#0", "G#0", "directed");
            return new Graphml(graph);
        } catch (final Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

}
