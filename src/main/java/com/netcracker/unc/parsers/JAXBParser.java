package com.netcracker.unc.parsers;

import com.netcracker.unc.model.OceanConfig;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

public class JAXBParser implements IXMLParser {

    @Override
    public OceanConfig read(InputStream config) {
        try {
            JAXBContext jc = JAXBContext.newInstance(OceanConfig.class);
            Unmarshaller unmarshaller = jc.createUnmarshaller();
            return (OceanConfig) unmarshaller.unmarshal(config);

        } catch (JAXBException ex) {
            System.err.println(ex);
            return null;
        }
    }

    @Override
    public void write() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
