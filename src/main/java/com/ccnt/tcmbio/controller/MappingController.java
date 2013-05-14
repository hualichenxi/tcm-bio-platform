/**
 * MappingController.java
 * Copyright 2013 Hao Tong, all rights reserved.
 * PROPRIETARY/CONFIDENTIAL, any form of usage is subject to approval.
 */

package com.ccnt.tcmbio.controller;

import java.util.ArrayList;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ccnt.tcmbio.data.MappingData;
import com.ccnt.tcmbio.data.MappingSyncData;
import com.ccnt.tcmbio.service.MappingService;

@Controller
@RequestMapping("/")
public class MappingController {

    private static final Logger LOGGER = LogManager.getLogger(MappingController.class.getName());

    @Autowired
    private MappingService mappingService;

    public void setMappingService(final MappingService mappingService) {
        this.mappingService = mappingService;
    }

    @RequestMapping(value="/v0.9/getsyncprogress", method=RequestMethod.GET)
    public @ResponseBody String getSyncProgress() throws Exception{

        LOGGER.debug("Reveived GET request: /v0.9/getsyncprogress");

        try {
            final MappingSyncData mappingSyncData = mappingService.syncMappingProgress();
            final ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(mappingSyncData);
        } catch (final Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping(value="/v0.9/mapping", method=RequestMethod.GET)
    public @ResponseBody String getMappings() throws Exception{

        LOGGER.debug("Reveived GET request: /v0.9/mapping");

        try {
            final ArrayList<MappingData> mappingDatas = mappingService.getMappings();
            final ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(mappingDatas);
        } catch (final Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping(value="/v0.9/mapping/ks/", method=RequestMethod.POST)
    public @ResponseBody String searchMappings(@RequestBody final Map<String, String> body) throws Exception{

        LOGGER.debug("Reveived POST request: /v0.9/mapping/ks/");

        try {
            final ArrayList<MappingData> mappingDatas = mappingService.searchMappings(body.get("keyword"));
            final ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(mappingDatas);
        } catch (final Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping(value="/v0.9/mapping/ds/", method=RequestMethod.POST)
    public @ResponseBody String getMappingDetails(@RequestBody final Map<String, String> body) throws Exception{

        LOGGER.debug("Reveived POST request: /v0.9/mapping/ds/");

        try {
            final ArrayList<MappingData> mappingDatas = mappingService.getMappingDetails(body.get("graphname"));
            final ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(mappingDatas);
        } catch (final Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

}
