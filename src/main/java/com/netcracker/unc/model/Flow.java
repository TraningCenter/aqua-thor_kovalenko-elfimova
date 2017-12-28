package com.netcracker.unc.model;

import javax.xml.bind.annotation.XmlEnum;

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
