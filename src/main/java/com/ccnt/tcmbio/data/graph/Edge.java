/**
 * Edge.java
 * Copyright 2013 Hao Tong, all rights reserved.
 * PROPRIETARY/CONFIDENTIAL, any form of usage is subject to approval.
 */

package com.ccnt.tcmbio.data.graph;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Edge {

    private String source;

    private String target;

    private String id;

    private Data data;

    @XmlAttribute
    public String getSource() {
        return source;
    }

    public void setSource(final String source) {
        this.source = source;
    }

    @XmlAttribute
    public String getTarget() {
        return target;
    }

    public void setTarget(final String target) {
        this.target = target;
    }

    @XmlAttribute
    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public Data getData() {
        return data;
    }

    public void setData(final Data data) {
        this.data = data;
    }

    public Edge(final String source, final String target, final String id, final Data data) {
        this.source = source;
        this.target = target;
        this.id = id;
        this.data = data;
    }

    public Edge(){}

}
