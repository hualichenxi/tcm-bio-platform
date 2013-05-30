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
            final String edgeStartID, final String graphID, final String edgedefault, String rootType, String leafType){

        try {
            final ArrayList<Node> nodes = new ArrayList<Node>();
            final ArrayList<Edge> edges = new ArrayList<Edge>();

            if (!rootExist){
                final Data[] data = new Data[3];
                data[0]=new Data("k-node", root);
                data[1]=new Data("label",root.substring(root.lastIndexOf("/")+1));
                data[2]=new Data("node-type",rootType);
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

                final Data[] nodeData = new Data[3];
                nodeData[0] = new Data("k-node", leaf);
                nodeData[1] = new Data("label",leaf.substring(leaf.lastIndexOf("/")+1));
                nodeData[2]=new Data("node-type",leafType);
                final Node node = new Node(leafID, nodeData);
                nodes.add(node);
                
                final Data[] edgeData= new Data[2];
                edgeData[0] = new Data("k-edge", edgeName);
                edgeData[1] = new Data("label",edgeName.substring(edgeName.lastIndexOf("/")+1));
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
