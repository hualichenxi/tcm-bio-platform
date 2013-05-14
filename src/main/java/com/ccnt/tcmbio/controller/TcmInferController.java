/**
 * TcmInferController.java
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

}
