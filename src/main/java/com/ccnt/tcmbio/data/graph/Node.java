/**
 * Node.java
 * Copyright 2013 Hao Tong, all rights reserved.
 * PROPRIETARY/CONFIDENTIAL, any form of usage is subject to approval.
 */

package com.ccnt.tcmbio.data.graph;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Node {

    private String id;
    private Data data;

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

    public Node(final String id, final Data data){
        this.id = id;
        this.data = data;
    }

    public Node(){}

}
