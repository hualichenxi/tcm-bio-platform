/**
 * CreateGraphService.java
 * Copyright 2013 Hao Tong, all rights reserved.
 * PROPRIETARY/CONFIDENTIAL, any form of usage is subject to approval.
 */

package com.ccnt.tcmbio.service;

import java.util.ArrayList;

import com.ccnt.tcmbio.data.graph.Graph;

public interface CreateGraphService {

    public Graph createGraph(String root, ArrayList<String> leaves, String rootID,
            Boolean rootExist, String leafStartID, String edgeName, String edgeStartID,
            String graphID, String edgedefault, String rootType, String leafType);

}
