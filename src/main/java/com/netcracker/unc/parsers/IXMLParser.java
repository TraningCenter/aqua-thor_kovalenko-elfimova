package com.netcracker.unc.parsers;

import com.netcracker.unc.model.OceanConfig;
import java.io.InputStream;

public interface IXMLParser {

    OceanConfig read(InputStream config);

    void write();
}
