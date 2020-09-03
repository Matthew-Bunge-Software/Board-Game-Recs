package org.bunge.bgginfo.bggintegration.model;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

public class Poll {
    @XmlElement(name = "results")
    protected Results[] results;

    @XmlAttribute(name = "name")
    protected String name;

    @XmlAttribute(name = "title")
    protected String title;

    @XmlAttribute(name = "totalvotes")
    protected Integer totalVotes;
}