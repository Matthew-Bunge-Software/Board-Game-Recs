package org.bunge.bgginfo.bggintegration.model;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

public class Results {
    @XmlElement(name = "result")
    protected Result[] result;

    @XmlAttribute(name = "numplayers")
    protected String numPlayers;
}