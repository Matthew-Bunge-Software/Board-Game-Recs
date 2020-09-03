package org.bunge.bgginfo.bggintegration.model;

import javax.xml.bind.annotation.XmlElement;

public class Statistics {
    @XmlElement(name = "ratings")
    protected Ratings ratings;
}