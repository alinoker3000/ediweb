package org.example.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.StringReader;

@Service
@RequiredArgsConstructor
public class XmlValidationService {

    public static void validate(String xml, String xsd) {
        try {
            SchemaFactory factory =
                    SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);

            Schema schema = factory.newSchema(new StreamSource(new StringReader(xsd)));
            Validator validator = schema.newValidator();

            validator.validate(new StreamSource(new StringReader(xml)));

        } catch (Exception e) {
            throw new RuntimeException("XML validation error: " + e.getMessage(), e);
        }
    }
}