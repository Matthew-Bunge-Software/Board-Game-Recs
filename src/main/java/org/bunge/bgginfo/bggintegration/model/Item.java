package org.bunge.bgginfo.bggintegration.model;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

public class Item {
    @XmlElement(name = "thumbnail")
    protected String thumbnail;

    @XmlElement(name = "poll")
    protected Poll[] poll;

    @XmlElement(name = "minplayers")
    protected MinPlayers minplayers;

    @XmlElement(name = "maxplayers")
    protected MaxPlayers maxplayers;

    @XmlElement(name = "name")
    protected Name[] name;

    @XmlElement(name = "statistics")
    protected Statistics statistics;

    @XmlAttribute(name = "id")
    protected Integer id;

    @XmlAttribute(name = "type")
    protected String type;
}