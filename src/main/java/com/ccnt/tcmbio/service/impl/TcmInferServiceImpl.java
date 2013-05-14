/**
 * TcmInferServiceImpl.java
 * Copyright 2013 Hao Tong, all rights reserved.
 * PROPRIETARY/CONFIDENTIAL, any form of usage is subject to approval.
 */

package com.ccnt.tcmbio.service.impl;

import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ccnt.tcmbio.dao.TcmInferDao;
import com.ccnt.tcmbio.data.TcmInferData;
import com.ccnt.tcmbio.data.TcmSearchData;
import com.ccnt.tcmbio.service.TcmInferService;

public class TcmInferServiceImpl implements TcmInferService{

    private TcmInferDao tcmInferDao;
    private static final Logger LOGGER = LogManager.getLogger(TcmInferServiceImpl.class.getName());
    private final String NAMESPACE = "http://purl.org/net/tcm/tcm.lifescience.ntu.edu.tw/id/medicine/";

    public void setTcmInferDao(final TcmInferDao tcmInferDao) {
        this.tcmInferDao = tcmInferDao;
    }

    @Override
    public TcmSearchData getTcmInference(final String tcmName, final Integer start, final Integer offset){

        LOGGER.debug("get the tcm inference result");
        try {
            final TcmSearchData tcmSearchData = new TcmSearchData();

            if (!searchTcm(tcmName)) {
                tcmSearchData.setFuzzymatchTCM(fuzzyMatchTcm(tcmName));
                tcmSearchData.setStatus(false);
                tcmSearchData.setTcmInferData(null);
                return tcmSearchData;
            }

            String tcm = tcmName.substring(0, 1).toUpperCase() + tcmName.substring(1).toLowerCase();
            tcm = tcm.replace(" ", "_");
            tcm = NAMESPACE + tcm;
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

}
