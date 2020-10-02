package org.bunge.bgginfo.game;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.bunge.bgginfo.bggintegration.model.BGGPayloadSearchResults;
import org.bunge.bgginfo.playerspoll.PlayersPollDAO;

@Entity
@Table(name = "Game")
public class GameDAO {
    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "name", nullable = true, length = 500)
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

    public GameDAO(BGGPayloadSearchResults r) {
        this.id = r.getId();
        this.name = r.getName();
        this.error = false;
        this.polls = r.getPolls();
        this.numRatings = r.getNumRatings();
        this.average = r.getAverage();
        this.weight = r.getWeight();
    }

    public GameDAO(int id, boolean failed) {
        this.id = id;
        this.error = true;
    }

    public Set<PlayersPollDAO> getPolls() {
        return polls;
    }

    public void setPolls(BGGPayloadSearchResults r) {
        this.polls = r.getPolls();
    }
}