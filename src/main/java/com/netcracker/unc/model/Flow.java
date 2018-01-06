package com.netcracker.unc.model;

import javax.xml.bind.annotation.XmlEnum;

/**
 * Flow enum
 */
@XmlEnum
public enum Flow {
    RIGHT,
    LEFT,
    NONE;

    /**
     * get flow by string value
     *
     * @param v string name of flow
     * @return Flow (RIGHT/LEFT/NONE)
     */
    public static Flow fromValue(String v) {
        return valueOf(v);
    }

    /**
     * get flow by index
     *
     * @param i index
     * @return Flow (RIGHT/LEFT/NONE)
     */
    public static Flow fromIndex(int i) {
        if (values().length <= i) {
            return null;
        }
        return values()[i];
    }

    /**
     * get size
     *
     * @return count of flow variants
     */
    public static int size() {
        return values().length;
    }
}
