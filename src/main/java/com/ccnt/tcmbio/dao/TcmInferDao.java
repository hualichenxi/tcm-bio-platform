/**
 * TcmInferDao.java
 * Copyright 2013 Hao Tong, all rights reserved.
 * PROPRIETARY/CONFIDENTIAL, any form of usage is subject to approval.
 */

package com.ccnt.tcmbio.dao;

import java.util.ArrayList;

import com.ccnt.tcmbio.data.TcmInferData;

public interface TcmInferDao {

    public ArrayList<TcmInferData> getTcmInference(String tcm, Integer start, Integer offset);

    public Integer getTcmInferCount(String tcm);

}
