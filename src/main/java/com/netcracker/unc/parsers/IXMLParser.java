package com.netcracker.unc.parsers;

import com.netcracker.unc.metric.MetricsWriter;
import com.netcracker.unc.model.OceanConfig;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Parser interface
 */
public interface IXMLParser {

    OceanConfig read(InputStream config);

    void write(MetricsWriter metricsWriter, OutputStream outputStream);
}
