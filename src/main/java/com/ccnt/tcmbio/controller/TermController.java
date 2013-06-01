/**
 * TermController.java
 * Copyright 2013 Hao Tong, all rights reserved.
 * PROPRIETARY/CONFIDENTIAL, any form of usage is subject to approval.
 */

package com.ccnt.tcmbio.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ccnt.tcmbio.data.DiseaseData;
import com.ccnt.tcmbio.data.DiseaseSearchData;
import com.ccnt.tcmbio.data.DrugData;
import com.ccnt.tcmbio.data.DrugSearchData;
import com.ccnt.tcmbio.data.GeneData;
import com.ccnt.tcmbio.data.GeneIDData;
import com.ccnt.tcmbio.data.GeneIDSearchData;
import com.ccnt.tcmbio.data.GeneSearchData;
import com.ccnt.tcmbio.data.ProteinData;
import com.ccnt.tcmbio.data.ProteinSearchData;
import com.ccnt.tcmbio.data.TCMData;
import com.ccnt.tcmbio.data.TCMSearchData;
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

    @RequestMapping(value = "/v0.9/term/searchdisease/kw={keyword}&s={start}&o={offset}", method = RequestMethod.GET)
    public @ResponseBody DiseaseSearchData searchDisease(@PathVariable final String keyword,
            @PathVariable final String start, @PathVariable final String offset) throws Exception{

        LOGGER.debug("Reveived GET request: ../v0.9/term/searchdisease/kw={}&s={}&o={}", keyword, start, offset);

        try {
            return termService.searchDisease(keyword,start, offset);
        } catch (final Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping(value = "/v0.9/term/searchgoid/kw={keyword}&s={start}&o={offset}", method = RequestMethod.GET)
    public @ResponseBody GeneSearchData searchGOID(@PathVariable final String keyword,
            @PathVariable final String start, @PathVariable final String offset) throws Exception{

        LOGGER.debug("Reveived GET request: ../v0.9/term/searchgoid/kw={}&s={}&o={}", keyword, start, offset);

        try {
            return termService.searchGOID(keyword, start, offset);
        } catch (final Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping(value = "/v0.9/term/searchtcm/kw={keyword}&s={start}&o={offset}", method = RequestMethod.GET)
    public @ResponseBody TCMSearchData searchTCM(@PathVariable final String keyword,
            @PathVariable final String start, @PathVariable final String offset) throws Exception{

        LOGGER.debug("Reveived GET request: ../v0.9/term/searchtcm/kw={}&s={}&o={}", keyword, start, offset);

        try {
            return termService.searchTCM(keyword, start, offset);
        } catch (final Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping(value = "/v0.9/term/searchdrug/kw={keyword}&s={start}&o={offset}", method = RequestMethod.GET)
    public @ResponseBody DrugSearchData searchDrug(@PathVariable final String keyword,
            @PathVariable final String start, @PathVariable final String offset) throws Exception{

        LOGGER.debug("Reveived GET request: ../v0.9/term/searchtcm/kw={}&s={}&o={}", keyword, start, offset);

        try {
            return termService.searchDrug(keyword, start, offset);
        } catch (final Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return null;
    }

    @RequestMapping(value = "/v0.9/term/searchgeneid/kw={keyword}&s={start}&o={offset}", method = RequestMethod.GET)
    public @ResponseBody GeneIDSearchData searchGeneID(@PathVariable final String keyword,
            @PathVariable final String start, @PathVariable final String offset) throws Exception{

        LOGGER.debug("Reveived GET request: ../v0.9/term/searchgeneid/kw={}&s={}&o={}", keyword, start, offset);

        try {
            return termService.searchGeneID(keyword, start, offset);
        } catch (final Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping(value = "/v0.9/term/searchprotein/kw={keyword}&s={start}&o={offset}", method = RequestMethod.GET)
    public @ResponseBody ProteinSearchData searchProtin(@PathVariable final String keyword,
            @PathVariable final String start, @PathVariable final String offset) throws Exception{

        LOGGER.debug("Reveived GET request: ../v0.9/term/searchprotein/kw={}&s={}&o={}", keyword, start, offset);

        try {
            return termService.searchProteinAC(keyword, start, offset);
        } catch (final Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping(value = "/v0.9/term/getdisease/kw={keyword}", method = RequestMethod.GET)
    public @ResponseBody DiseaseData getDisease(@PathVariable final String keyword) throws Exception{

        LOGGER.debug("Reveived GET request: ../v0.9/term/getdisease/kw={}", keyword);

        try {
            return termService.getDisease(keyword);
        } catch (final Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping(value = "/v0.9/term/getgoid/kw={keyword}", method = RequestMethod.GET)
    public @ResponseBody GeneData getGOID(@PathVariable final String keyword) throws Exception{

        LOGGER.debug("Reveived GET request: ../v0.9/term/getgoid/kw={}", keyword);

        try {
            return termService.getGOID(keyword);
        } catch (final Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping(value = "/v0.9/term/getgeneid/kw={keyword}", method = RequestMethod.GET)
    public @ResponseBody GeneIDData getGeneID(@PathVariable final String keyword) throws Exception{

        LOGGER.debug("Reveived GET request: ../v0.9/term/getgeneid/kw={}", keyword);

        try {
            return termService.getGeneID(keyword);
        } catch (final Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping(value = "/v0.9/term/gettcm/kw={keyword}", method = RequestMethod.GET)
    public @ResponseBody TCMData getTCM(@PathVariable final String keyword) throws Exception{

        LOGGER.debug("Reveived GET request: ../v0.9/term/gettcm/kw={}", keyword);

        try {
            return termService.getTCM(keyword);
        } catch (final Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping(value = "/v0.9/term/getdrug/kw={keyword}", method = RequestMethod.GET)
    public @ResponseBody DrugData getDrug(@PathVariable final String keyword) throws Exception{

        LOGGER.debug("Reveived GET request: ../v0.9/term/getdrug/kw={}", keyword);

        try {
            return termService.getDrug(keyword);
        } catch (final Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping(value = "/v0.9/term/getprotein/kw={keyword}", method = RequestMethod.GET)
    public @ResponseBody ProteinData getProtein(@PathVariable final String keyword) throws Exception{

        LOGGER.debug("Reveived GET request: ../v0.9/term/getprotein/kw={}", keyword);

        try {
            return termService.getProteinAC(keyword);
        } catch (final Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

}
