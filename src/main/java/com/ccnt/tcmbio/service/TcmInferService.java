/**
 * TcmInferService.java
 * Copyright 2013 Hao Tong, all rights reserved.
 * PROPRIETARY/CONFIDENTIAL, any form of usage is subject to approval.
 */

package com.ccnt.tcmbio.service;

import java.util.ArrayList;

import com.ccnt.tcmbio.data.TcmInferData;
import com.ccnt.tcmbio.data.TcmSearchData;

public interface TcmInferService {

    public TcmSearchData getTcmInference(String tcmName, Integer start, Integer offset);

    public ArrayList<TcmInferData> getAndCacheTcmInference(String tcmName);

    public boolean searchTcm(String tcmName);

    public ArrayList<String> fuzzyMatchTcm(String tcmName);

}
