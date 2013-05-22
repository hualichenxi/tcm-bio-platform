/**
 * Key.java
 * Copyright 2013 Hao Tong, all rights reserved.
 * PROPRIETARY/CONFIDENTIAL, any form of usage is subject to approval.
 */

package com.ccnt.tcmbio.data.graph;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Key {

    private String id;

    private String forNorE;

    private String attrName;

    private String attrType;

    @XmlAttribute
    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    @XmlAttribute(name="for")
    public String getForNorE() {
        return forNorE;
    }

    public void setForNorE(final String forNorE) {
        this.forNorE = forNorE;
    }

    @XmlAttribute(name="attr.name")
    public String getAttrName() {
        return attrName;
    }

    public void setAttrName(final String attrName) {
        this.attrName = attrName;
    }

    @XmlAttribute(name="attr.type")
    public String getAttrType() {
        return attrType;
    }

    public void setAttrType(final String attrType) {
        this.attrType = attrType;
    }

    public Key(final String id, final String forNorE, final String attrName, final String attrType) {
        super();
        this.id = id;
        this.forNorE = forNorE;
        this.attrName = attrName;
        this.attrType = attrType;
    }

    public Key(){}

}
