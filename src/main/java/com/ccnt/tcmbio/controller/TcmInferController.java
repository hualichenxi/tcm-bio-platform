/**
 * TcmInferController.java
 * Copyright 2013 Hao Tong, all rights reserved.
 * PROPRIETARY/CONFIDENTIAL, any form of usage is subject to approval.
 */

package com.ccnt.tcmbio.controller;

import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ccnt.tcmbio.data.TcmSearchData;
import com.ccnt.tcmbio.service.TcmInferService;

@Controller
@RequestMapping("/")
public class TcmInferController {

    private static final Logger LOGGER = LogManager.getLogger(TcmInferController.class.getName());

    @Autowired
    private TcmInferService tcmInferService;

    public void setTcmInferService(final TcmInferService tcmInferService) {
        this.tcmInferService = tcmInferService;
    }

    @RequestMapping(value="/v0.9/tcminfer/kw={tcmName}&s={start}&o={offset}", method=RequestMethod.GET)
    public @ResponseBody String getTcmInference(@PathVariable final String tcmName, @PathVariable final Integer start, @PathVariable final Integer offset) throws Exception{

        LOGGER.debug("Received GET request: /v0.9/tcminfer/kw={}&s={}&o={}", tcmName, start, offset);

        try {
            final TcmSearchData tcmSearchDatas = tcmInferService.getTcmInference(tcmName, start, offset);
            final ObjectMapper objectMapper =  new ObjectMapper();
            return objectMapper.writeValueAsString(tcmSearchDatas);
        } catch (final Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    //todo

    @RequestMapping(value="/v0.9/tcminfer/tcm2disease/kw={tcmName}&s={start}&o={offset}", method=RequestMethod.GET)
    public @ResponseBody ArrayList<String> getDisease(@PathVariable final String tcmName, @PathVariable final Integer start, @PathVariable final Integer offset) throws Exception{

        LOGGER.debug("Received GET request: /v0.9/tcminfer/tcm2disease/kw={}&s={}&o={}", tcmName, start, offset);

        try {
            return tcmInferService.getDiseaseName(tcmName, start, offset);
        } catch (final Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping(value="/v0.9/tcminfer/disname2disid/kw={diseaseName}&s={start}&o={offset}", method=RequestMethod.GET)
    public @ResponseBody String getDiseaseID(@PathVariable final String diseaseName, @PathVariable final Integer start, @PathVariable final Integer offset) throws Exception{

        LOGGER.debug("Received GET request: /v0.9/tcminfer/disname2disid/kw={}&s={}&o={}", diseaseName, start, offset);

        try {
            final TcmSearchData tcmSearchDatas = tcmInferService.getTcmInference(diseaseName, start, offset);
            final ObjectMapper objectMapper =  new ObjectMapper();
            return objectMapper.writeValueAsString(tcmSearchDatas);
        } catch (final Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping(value="/v0.9/tcminfer/disid2drugid/kw={diseaseID}&s={start}&o={offset}", method=RequestMethod.GET)
    public @ResponseBody String getDrugID(@PathVariable final String diseaseID, @PathVariable final Integer start, @PathVariable final Integer offset) throws Exception{

        LOGGER.debug("Received GET request: /v0.9/tcminfer/disid2drugid/kw={}&s={}&o={}", diseaseID, start, offset);

        try {
            final TcmSearchData tcmSearchDatas = tcmInferService.getTcmInference(diseaseID, start, offset);
            final ObjectMapper objectMapper =  new ObjectMapper();
            return objectMapper.writeValueAsString(tcmSearchDatas);
        } catch (final Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping(value="/v0.9/tcminfer/drugid2targetid/kw={drugID}&s={start}&o={offset}", method=RequestMethod.GET)
    public @ResponseBody String getTargetID(@PathVariable final String drugID, @PathVariable final Integer start, @PathVariable final Integer offset) throws Exception{

        LOGGER.debug("Received GET request: /v0.9/tcminfer/drugid2targetid/kw={}&s={}&o={}", drugID, start, offset);

        try {
            final TcmSearchData tcmSearchDatas = tcmInferService.getTcmInference(drugID, start, offset);
            final ObjectMapper objectMapper =  new ObjectMapper();
            return objectMapper.writeValueAsString(tcmSearchDatas);
        } catch (final Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping(value="/v0.9/tcminfer/target2protein/kw={targetID}&s={start}&o={offset}", method=RequestMethod.GET)
    public @ResponseBody String getProtein(@PathVariable final String targetID, @PathVariable final Integer start, @PathVariable final Integer offset) throws Exception{

        LOGGER.debug("Received GET request: /v0.9/tcminfer/target2protein/kw={}&s={}&o={}", targetID, start, offset);

        try {
            final TcmSearchData tcmSearchDatas = tcmInferService.getTcmInference(targetID, start, offset);
            final ObjectMapper objectMapper =  new ObjectMapper();
            return objectMapper.writeValueAsString(tcmSearchDatas);
        } catch (final Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping(value="/v0.9/tcminfer/protein2geneid/kw={protein}&s={start}&o={offset}", method=RequestMethod.GET)
    public @ResponseBody String getGeneID(@PathVariable final String protein, @PathVariable final Integer start, @PathVariable final Integer offset) throws Exception{

        LOGGER.debug("Received GET request: /v0.9/tcminfer/protein2geneid/kw={}&s={}&o={}", protein, start, offset);

        try {
            final TcmSearchData tcmSearchDatas = tcmInferService.getTcmInference(protein, start, offset);
            final ObjectMapper objectMapper =  new ObjectMapper();
            return objectMapper.writeValueAsString(tcmSearchDatas);
        } catch (final Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping(value="/v0.9/tcminfer/geneid2geneprod/kw={targetID}&s={start}&o={offset}", method=RequestMethod.GET)
    public @ResponseBody String getGeneProduct(@PathVariable final String geneID, @PathVariable final Integer start, @PathVariable final Integer offset) throws Exception{

        LOGGER.debug("Received GET request: /v0.9/tcminfer/geneid2geneprod/kw={}&s={}&o={}", geneID, start, offset);

        try {
            final TcmSearchData tcmSearchDatas = tcmInferService.getTcmInference(geneID, start, offset);
            final ObjectMapper objectMapper =  new ObjectMapper();
            return objectMapper.writeValueAsString(tcmSearchDatas);
        } catch (final Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }


}
