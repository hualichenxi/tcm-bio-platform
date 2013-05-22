/**
 * CreateGraphServiceImpl.java
 * Copyright 2013 Hao Tong, all rights reserved.
 * PROPRIETARY/CONFIDENTIAL, any form of usage is subject to approval.
 */

package com.ccnt.tcmbio.service.impl;

import java.util.ArrayList;

import com.ccnt.tcmbio.data.graph.Data;
import com.ccnt.tcmbio.data.graph.Edge;
import com.ccnt.tcmbio.data.graph.Graph;
import com.ccnt.tcmbio.data.graph.Node;
import com.ccnt.tcmbio.service.CreateGraphService;

public class CreateGraphServiceImpl implements CreateGraphService{

    @Override
    public Graph createGraph(final String root, final ArrayList<String> leaves, final String rootID,
            final Boolean rootExist, final String leafStartID, final String edgeName,
            final String edgeStartID, final String graphID, final String edgedefault){

        try {
            final ArrayList<Node> nodes = new ArrayList<Node>();
            final ArrayList<Edge> edges = new ArrayList<Edge>();

            if (!rootExist){
                final Data data = new Data("k-node", root);
                final Node node = new Node(rootID, data);
                nodes.add(node);
            }

            final String leafHeader = leafStartID.split("#")[0];
            Integer leafIDInteger = Integer.valueOf(leafStartID.split("#")[1]);
            final String edgeHeader =  edgeStartID.split("#")[0];
            Integer edgeIDInteger = Integer.valueOf(edgeStartID.split("#")[1]);
            for (final String leaf : leaves){

                final String leafID = leafHeader + '#' + leafIDInteger.toString();
                final String edgeID = edgeHeader + '#' + edgeIDInteger.toString();

                final Data nodeData = new Data("k-node", leaf);
                final Node node = new Node(leafID, nodeData);
                nodes.add(node);

                final Data edgeData =  new Data("k-edge", edgeName);
                final Edge edge = new Edge(rootID, leafID, edgeID, edgeData);
                edges.add(edge);

                leafIDInteger++;
                edgeIDInteger++;
            }

            final Graph graph = new Graph(nodes, edges, graphID, edgedefault);
            return graph;
        } catch (final NumberFormatException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return null;
    }

}
