package org.bunge.bgginfo.bggintegration.model;

import javax.xml.bind.annotation.XmlAttribute;

public class Name {
    @XmlAttribute(name = "type")
    protected String type;

    @XmlAttribute(name = "value")
    protected String value;
}