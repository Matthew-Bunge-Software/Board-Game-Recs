package org.bunge.bgginfo.bggintegration.model;

import javax.xml.bind.annotation.XmlElement;

public class Ratings {
    @XmlElement(name = "usersrated")
    protected UsersRated usersRated;

    @XmlElement(name = "average")
    protected Average average;

    @XmlElement(name = "bayesaverage")
    protected BayesAverage bayesAverage;

    @XmlElement(name = "averageweight")
    protected AverageWeight averageWeight;
}