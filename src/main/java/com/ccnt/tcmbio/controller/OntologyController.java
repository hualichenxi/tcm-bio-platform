/**
 * OntologiesController.java
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

import com.ccnt.tcmbio.data.OntologyData;
import com.ccnt.tcmbio.service.OntologyService;

@Controller
@RequestMapping("/")
public class OntologyController {

    private static final Logger LOGGER = LogManager.getLogger(OntologyController.class.getName());

    @Autowired
    private OntologyService ontologyService;

    public void setOntologyService(final OntologyService ontologyService) {
        this.ontologyService = ontologyService;
    }

    @RequestMapping(value = "/v0.9/ontologies", method = RequestMethod.GET)
    public @ResponseBody String getAllOntologies() throws Exception{

        LOGGER.debug("Reveived GET request: /v0.9/ontologies");

        try {
//            final OntologyDAO ontologyDAO = (OntologyDAO)context.getBean("ontologyDAO");
            final ArrayList<OntologyData> ontologies = ontologyService.getAllOntologies();
            final ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(ontologies);
        } catch (final Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping(value = "/v0.9/ontologies/{keyword}", method = RequestMethod.GET)
    public @ResponseBody String searchOntology(@PathVariable final String keyword) throws Exception{

        LOGGER.debug("Reveived GET request: /v0.9/ontologies/{keyword}");

        try {
            final ArrayList<OntologyData> ontologies = ontologyService.searchOntologies(keyword);
            final ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(ontologies);
        } catch (final Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

//    @RequestMapping(value = "/ontologysearch/index.html", method = RequestMethod.GET)
//    public ModelAndView ontoSePage() throws Exception {
//        try {
//            final ModelAndView mView = new ModelAndView("ontologysearch/index");
//            return mView;
//        } catch (final Exception e) {
//            // TODO: handle exception
//            e.printStackTrace();
//        }
//        return null;
//    }

}
