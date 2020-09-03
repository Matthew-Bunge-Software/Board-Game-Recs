package org.bunge.bgginfo.playerspoll;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.bunge.bgginfo.game.GameDAO;


@Entity
@Table(name = "PlayersPoll")
public class PlayersPollDAO {
    @EmbeddedId
    private PlayersPollDAOId id;

    @Column(name = "best", nullable = false)
    private int best;

    @Column(name = "recommended", nullable = false)
    private int recommended;

    @Column(name = "notRecommended", nullable = false)
    private int notRecommended;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id", insertable = false, updatable = false)
    private GameDAO game;

    public PlayersPollDAO() {

    }

    public PlayersPollDAO(int id, String numplayers, int best, int recommended, int notRecommended) {
        this.id = new PlayersPollDAOId(id, numplayers);
        this.best = best;
        this.recommended = recommended;
        this.notRecommended = notRecommended;
    }

    public void fixGame(GameDAO game) {
        this.game = game;
    }
}

@Embeddable
class PlayersPollDAOId implements Serializable {
    private static final long serialVersionUID = 1L;

    @Column(name = "id", nullable = false)
    private int id;

    @Column(name = "numPlayers", nullable = false)
    private String numplayers;

    public PlayersPollDAOId() {

    }

    public PlayersPollDAOId(int id, String numplayers) {
        this.id = id;
        this.numplayers = numplayers;
    }
}
