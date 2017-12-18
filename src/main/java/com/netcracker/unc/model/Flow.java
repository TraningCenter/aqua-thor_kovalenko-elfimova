package com.netcracker.unc.model;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlEnum
public enum Flow {
    RIGHT,
    LEFT,
    NONE;

    public String value() {
        return name();
    }

    public static Flow fromValue(String v) {
        return valueOf(v);
    }
}
