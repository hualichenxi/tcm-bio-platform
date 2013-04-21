/**
 * BaseController.java
 * Copyright 2013 Hao Tong, all rights reserved.
 * PROPRIETARY/CONFIDENTIAL, any form of usage is subject to approval.
 */

package com.ccnt.tcmbio.controller;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ccnt.tcmbio.dao.OntologyDAO;


@Controller
@RequestMapping("/")
public class BaseController {

//    @RequestMapping(value="welcome", method = RequestMethod.GET)
//    public String welcome(final ModelMap model) {
//
//        model.addAttribute("message", "fdfdfd");
//        return "index";
//
//    }
//
//    @RequestMapping(value="/welcome/{name}", method = RequestMethod.GET)
//    public String welcomeName(@PathVariable final String name, final ModelMap model) {
//
//        model.addAttribute("message", "Maven Web Project + Spring 3 MVC - " + name);
//        return "index";
//
//    }

    @RequestMapping(value="/testjson/hello={hello}", method = RequestMethod.GET)
    public @ResponseBody String testJson(@PathVariable final String hello) throws Exception{

        try {
            final String test2[] = {"a", "b"};
            final String test1 = hello;
            final Jsontest testJsontest = new Jsontest();
            final ObjectMapper mapper =  new ObjectMapper();
            testJsontest.setJsonv1(hello);
            testJsontest.setJsonv2(test2);

            final ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
            final OntologyDAO ontologyDAO = (OntologyDAO)context.getBean("ontologyDAO");
            ontologyDAO.findAllOntologies();

            return mapper.writeValueAsString(testJsontest);
        } catch (final Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }

}
