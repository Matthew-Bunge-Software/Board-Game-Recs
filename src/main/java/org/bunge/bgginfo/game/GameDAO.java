package org.bunge.bgginfo.game;

import org.bunge.bgginfo.bggintegration.model.BGGPayloadSearchResults;
import org.bunge.bgginfo.playerspoll.PlayersPollDAO;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "Game")
public class GameDAO {
    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "name", nullable = true, length = 100)
    private String name;

    @Column(name = "numRatings")
    private Integer numRatings;

    @Column(name = "average")
    private Double average;

    @Column(name = "error", nullable = false)
    private boolean error;

    @Column(name = "weight")
    private Double weight;

    @OneToMany(mappedBy = "game", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<PlayersPollDAO> polls;

    public GameDAO() {

    }

    public GameDAO(BGGPayloadSearchResults r, int index) {
        this.id = r.getId(index);
        this.name = r.getName(index);
        this.error = false;
        this.polls = r.getPolls(index);
        this.numRatings = r.getNumRatings(index);
        this.average = r.getAverage(index);
        this.weight = r.getWeight(index);
    }

    public GameDAO(int id, boolean failed) {
        this.id = id;
        this.error = true;
    }

    public Set<PlayersPollDAO> getPolls() {
        return polls;
    }

    public void setPolls(BGGPayloadSearchResults r, int index) {
        this.polls = r.getPolls(index);
    }
}