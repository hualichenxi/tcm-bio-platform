/**
 * Graphml.java
 * Copyright 2013 Hao Tong, all rights reserved.
 * PROPRIETARY/CONFIDENTIAL, any form of usage is subject to approval.
 */

package com.ccnt.tcmbio.data.graph;

import java.util.ArrayList;
import java.util.Arrays;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Graphml {

    private static ArrayList<Key> key = new ArrayList<Key>(Arrays.asList(
    		new Key("label",  "all", "label", "string"),
            new Key("k-node", "node", "node-name", "string"),
            new Key("k-edge", "edge", "edge-name", "string")));

    private Graph graph;

    @XmlElement(name="key")
    public ArrayList<Key> getKey() {
        return key;
    }

    public Graph getGraph() {
        return graph;
    }

    public void setGraph(final Graph graph) {
        this.graph = graph;
    }

    public Graphml(){}

    public Graphml(final Graph graph) {
        super();
        this.graph = graph;
    }

}
