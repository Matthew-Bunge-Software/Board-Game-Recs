package org.bunge.bgginfo.bggintegration.model;

import javax.xml.bind.annotation.XmlAttribute;

public class Result {
    @XmlAttribute(name = "value")
    protected String value;

    @XmlAttribute(name = "numvotes")
    protected String numVotes;

    @XmlAttribute(name = "level")
    protected String level;
}