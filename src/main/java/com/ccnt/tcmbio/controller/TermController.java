/**
 * TermController.java
 * Copyright 2013 Hao Tong, all rights reserved.
 * PROPRIETARY/CONFIDENTIAL, any form of usage is subject to approval.
 */

package com.ccnt.tcmbio.controller;

import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ccnt.tcmbio.data.DiseaseData;
import com.ccnt.tcmbio.data.DrugData;
import com.ccnt.tcmbio.data.GeneData;
import com.ccnt.tcmbio.data.ProteinData;
import com.ccnt.tcmbio.data.TcmData;
import com.ccnt.tcmbio.service.TermService;

@Controller
@RequestMapping("/")
public class TermController {

    private static final Logger LOGGER = LogManager.getLogger(MappingController.class.getName());

    @Autowired
    private TermService termService;

    public void setTermService(final TermService termService) {
        this.termService = termService;
    }

    @RequestMapping(value = "/v0.9/searchterm?kw={keyword}", method = RequestMethod.GET)
    public @ResponseBody String searchTerm(@PathVariable final String keyword) throws Exception{
        return null;
    }

    @RequestMapping(value = "/v0.9/term/searchdisease/kw={keyword}", method = RequestMethod.GET)
    public @ResponseBody ArrayList<DiseaseData> searchDisease(@PathVariable final String keyword) throws Exception{

        LOGGER.debug("Reveived GET request: ../v0.9/term/searchdisease/kw={}", keyword);

        try {
            return termService.searchDisease(keyword);
        } catch (final Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping(value = "/v0.9/term/searchgene?kw={keyword}", method = RequestMethod.GET)
    public @ResponseBody GeneData searchGene(@PathVariable final String keyword) throws Exception{
        return null;
    }

    @RequestMapping(value = "/v0.9/term/searchtcm?kw={keyword}", method = RequestMethod.GET)
    public @ResponseBody TcmData searchTCM(@PathVariable final String keyword) throws Exception{
        return null;
    }

    @RequestMapping(value = "/v0.9/term/searchprotein?kw={keyword}", method = RequestMethod.GET)
    public @ResponseBody ProteinData searchProtein(@PathVariable final String keyword) throws Exception{
        return null;
    }

    @RequestMapping(value = "/v0.9/term/searchdrug?kw={keyword}", method = RequestMethod.GET)
    public @ResponseBody DrugData searchDrug(@PathVariable final String keyword) throws Exception{
        return null;
    }

}
