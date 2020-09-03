package org.bunge.bgginfo.bggintegration.service;

import java.util.List;

import org.bunge.bgginfo.bggintegration.model.BGGPayloadSearchResults;
import org.bunge.bgginfo.game.GameDAO;
import org.bunge.bgginfo.game.GameDTO;
import org.bunge.bgginfo.game.GameRepo;
import org.bunge.bgginfo.playerspoll.PlayersPollRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class DefaultBGGService implements BGGService {

    @Autowired
    GameRepo gameRepo;

    @Autowired
    PlayersPollRepo playersPollRepo;

    @Override
    public String getRandomGameTerms(int i) {
        BGGPayloadSearchResults s = null;
        try {
            s = WebClient.create().get().uri("https://www.boardgamegeek.com/xmlapi2/thing?id=" + i + "&stats=1")
                    .exchange().block().bodyToMono(BGGPayloadSearchResults.class).block();
            if (s.getType().equals("boardgame")) {
                GameDAO g = new GameDAO(s);
                gameRepo.save(g);
            }
        } catch (NullPointerException e) {
            GameDAO g = new GameDAO(i, true);
            gameRepo.save(g);
        }
        return s == null ? "" : s.toString();
    }

	@Override
	public List<GameDTO> getMasterList(int average) {
        double perc = average * .01;
        return gameRepo.findByAverage(perc);
	}

}