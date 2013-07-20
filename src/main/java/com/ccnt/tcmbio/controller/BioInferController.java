/**
 * BioInferController.java
 * Copyright 2013 Hao Tong, all rights reserved.
 * PROPRIETARY/CONFIDENTIAL, any form of usage is subject to approval.
 */

package com.ccnt.tcmbio.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.ccnt.tcmbio.data.BioInferSearchData;
import com.ccnt.tcmbio.service.BioInferService;
//import com.ccnt.tcmbio.data.graph.Graphml;

@Controller
@RequestMapping("/")
public class BioInferController {

    private static final Logger LOGGER = LogManager.getLogger(BioInferController.class.getName());

    @Autowired
    private BioInferService bioInferService;

    public void setBioInferService(final BioInferService bioInferService) {
        this.bioInferService = bioInferService;
    }

    @RequestMapping(value="/v0.9/bioinfer/kw={bioName}&s={start}&o={offset}", method=RequestMethod.GET)
    public @ResponseBody String getBioInference(@PathVariable final String bioName, @PathVariable final Integer start, @PathVariable final Integer offset) throws Exception{

        LOGGER.debug("Received GET request: /v0.9/bioinfer/kw={}&s={}&o={}", bioName, start, offset);

        try {
            final BioInferSearchData bioSearchDatas = bioInferService.getBioInference(bioName, start, offset);
            final ObjectMapper objectMapper =  new ObjectMapper();
            return objectMapper.writeValueAsString(bioSearchDatas);
        } catch (final Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
}