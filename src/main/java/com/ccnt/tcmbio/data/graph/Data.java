/**
 * Data.java
 * Copyright 2013 Hao Tong, all rights reserved.
 * PROPRIETARY/CONFIDENTIAL, any form of usage is subject to approval.
 */

package com.ccnt.tcmbio.data.graph;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

@XmlRootElement
public class Data {

    private String key;
    private String value;

    @XmlAttribute
    public String getKey() {
        return key;
    }

    public void setKey(final String key) {
        this.key = key;
    }

    @XmlValue
    public String getValue() {
        return value;
    }

    public void setValue(final String value) {
        this.value = value;
    }

    public Data(final String key, final String value) {
        super();
        this.key = key;
        this.value = value;
    }

    public Data(){}

}
