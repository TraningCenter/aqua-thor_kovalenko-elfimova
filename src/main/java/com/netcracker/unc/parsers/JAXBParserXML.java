package com.netcracker.unc.parsers;

import com.netcracker.unc.model.OceanConfig;
import com.netcracker.unc.utils.CommonUtils;
import java.io.InputStream;
import java.io.OutputStream;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.ValidationEvent;

public class JAXBParserXML implements IXMLParser {

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

    @Override
    public void write(OceanConfig oceanConfig, OutputStream outputStream) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
