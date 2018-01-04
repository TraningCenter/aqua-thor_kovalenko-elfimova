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

    public static Flow fromIndex(int i) {
        if (values().length <= i) {
            return null;
        }
        return values()[i];
    }

    public static int size() {
        return values().length;
    }
}
