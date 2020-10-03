package org.bunge.bgginfo.bggintegration.model;

import org.bunge.bgginfo.playerspoll.PlayersPollDAO;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

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
        String[] names = new String[item.length];
        for (int i = 0; i < item.length; ++i) {
            names[i] = item[i].name[0].value;
        }
        return String.join(", ", names);
    }

    public int getItemCount() {
        return item.length;
    }

    public int getId(int idx) {
        return item[idx].id;
    }

    public String getType(int idx) {
        return item[idx].type;
    }

    public String getName(int idx) {
        return Arrays.stream(item[idx].name).filter(n -> n.type.equals("primary")).findFirst().get().value;
    }

    public int getNumRatings(int idx) {
        return item[idx].statistics.ratings.usersRated.value;
    }

    public double getAverage(int idx) {
        return item[idx].statistics.ratings.average.value;
    }

    public double getWeight(int idx) {
        return item[idx].statistics.ratings.averageWeight.value;
    }

    public Set<PlayersPollDAO> getPolls(int idx) {
        Set<PlayersPollDAO> s = new HashSet<>();
        Results[] r = Arrays.stream(item[idx].poll).filter(p -> p.name.equals("suggested_numplayers")).findFirst().get().results;
        Arrays.stream(r).forEach(rs -> {
            if (rs.result != null) {
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
                s.add(new PlayersPollDAO(getId(idx), rs.numPlayers, best, rec, nRec));
            }
        });
        return s;
	}
}