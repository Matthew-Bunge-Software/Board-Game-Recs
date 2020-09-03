package org.bunge.bgginfo.bggintegration.model;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.bunge.bgginfo.playerspoll.PlayersPollDAO;

@XmlRootElement(name = "items")
public class BGGPayloadSearchResults {
    @XmlElement(name = "item", required = true)
    protected Item[] item;

    private String termsofuse;

    @XmlAttribute
    public String getTermsofuse() {
        return termsofuse;
    }

    public void setTermsofuse(String termsofuse) {
        this.termsofuse = termsofuse;
    }

    @Override
    public String toString() {
        return item[0].name[0].value;
    }

    public int getId() {
        return item[0].id;
    }

    public String getType() {
        return item[0].type;
    }

    public String getName() {
        return Arrays.stream(item[0].name).filter(n -> n.type.equals("primary")).findFirst().get().value;
    }

    public int getNumRatings() {
        return item[0].statistics.ratings.usersRated.value;
    }

    public double getAverage() {
        return item[0].statistics.ratings.average.value;
    }

    public double getWeight() {
        return item[0].statistics.ratings.averageWeight.value;
    }

    public Set<PlayersPollDAO> getPolls() {
        Set<PlayersPollDAO> s = new HashSet<>();
        Results[] r = Arrays.stream(item[0].poll).filter(p -> p.name.equals("suggested_numplayers")).findFirst().get().results;
        Arrays.stream(r).forEach(rs -> {
            int rec, nRec, best;
            rec = nRec = best = 0;
            for (int i = 0; i < rs.result.length; i++) {
                Result rslt = rs.result[i];
                if (rslt.value.equals("Best"))
                best = Integer.parseInt(rslt.numVotes);
                else if (rslt.value.equals("Recommended"))
                    rec = Integer.parseInt(rslt.numVotes);
                else if (rslt.value.equals("Not Recommended"))
                    nRec = Integer.parseInt(rslt.numVotes);
            }
            s.add(new PlayersPollDAO(getId(), rs.numPlayers, best, rec, nRec));
        });
        return s;
	}
}