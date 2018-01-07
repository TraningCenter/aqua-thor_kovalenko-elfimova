package com.netcracker.unc.parsers;

import com.netcracker.unc.metric.MetricsWriter;
import com.netcracker.unc.model.OceanConfig;
import com.netcracker.unc.utils.CommonUtils;
import java.io.InputStream;
import java.io.OutputStream;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.ValidationEvent;

/**
 * JAXB parser
 */
public class JAXBParserXML implements IXMLParser {

    /**
     * read config from input stream
     *
     * @param config input stream
     * @return ocean configuration
     */
    @Override
    public OceanConfig read(InputStream config) {
        try {
            JAXBContext jc = JAXBContext.newInstance(OceanConfig.class);
            Unmarshaller unmarshaller = jc.createUnmarshaller();
            unmarshaller.setEventHandler((ValidationEvent event) -> false);
            OceanConfig oceanConfig = (OceanConfig) unmarshaller.unmarshal(config);
            if (CommonUtils.checkEmpty(oceanConfig.getSharks()) && CommonUtils.checkEmpty(oceanConfig.getSmallFishes())) {
                return null;
            }
            return oceanConfig;
        } catch (JAXBException ex) {
            return null;
        }
    }

    /**
     * write snapshots to xml file
     *
     * @param metricsWriter contains snapshots
     * @param outputStream output stream
     */
    @Override
    public void write(MetricsWriter metricsWriter, OutputStream outputStream) {
        try {
            JAXBContext jc = JAXBContext.newInstance(MetricsWriter.class);
            Marshaller marshaller = jc.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.marshal(metricsWriter, outputStream);
        } catch (JAXBException ex) {
        }
    }
}
