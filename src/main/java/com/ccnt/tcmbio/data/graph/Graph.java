/**
 * Graph.java
 * Copyright 2013 Hao Tong, all rights reserved.
 * PROPRIETARY/CONFIDENTIAL, any form of usage is subject to approval.
 */

package com.ccnt.tcmbio.data.graph;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Graph {

    private ArrayList<Node> node;

    private ArrayList<Edge> edge;

    private String id;

    private String edgedefault;

    public ArrayList<Node> getNode() {
        return node;
    }

    public void addNodes(final ArrayList<Node> nodes) {
        node.addAll(nodes);
    }

    public void setNode(final ArrayList<Node> node) {
        this.node = node;
    }

    public ArrayList<Edge> getEdge() {
        return edge;
    }

    public void addEdges(final ArrayList<Edge> edges) {
        edge.addAll(edges);
    }

    public void setEdge(final ArrayList<Edge> edge) {
        this.edge = edge;
    }

    @XmlAttribute
    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    @XmlAttribute
    public String getEdgedefault() {
        return edgedefault;
    }

    public void setEdgedefault(final String edgedefault) {
        this.edgedefault = edgedefault;
    }

    public Graph(){}

    public Graph(final ArrayList<Node> node, final ArrayList<Edge> edge, final String id, final String edgedefault) {
        super();
        this.node = node;
        this.edge = edge;
        this.id = id;
        this.edgedefault = edgedefault;
    }

}
