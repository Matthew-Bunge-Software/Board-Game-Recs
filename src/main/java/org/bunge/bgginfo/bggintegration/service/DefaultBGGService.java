package org.bunge.bgginfo.bggintegration.service;

import org.bunge.bgginfo.bggintegration.model.BGGPayloadSearchResults;
import org.bunge.bgginfo.game.GameDAO;
import org.bunge.bgginfo.game.GameDTO;
import org.bunge.bgginfo.game.GameRepo;
import org.bunge.bgginfo.playerspoll.PlayersPollRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class DefaultBGGService implements BGGService {
    private static final Logger log = LoggerFactory.getLogger(DefaultBGGService.class);
    private static final String BGG_URL_TEMPLATE = "https://www.boardgamegeek.com/xmlapi2";
    private static final int BGG_BATCH_SIZE = 20;
    private static final int BGG_MAX_GAME_ID = 5000000;

    @Autowired
    GameRepo gameRepo;

    @Autowired
    PlayersPollRepo playersPollRepo;

    private WebClient webClient;
    private Integer nextGameId = 1;

    @PostConstruct
    public void init() {
        // Init web client
        webClient = WebClient.builder()
                .baseUrl(BGG_URL_TEMPLATE)
                .build();
    }

    @Override
    public String getRandomGameTerms(int i) {
        BGGPayloadSearchResults s = null;
        try {
            s = WebClient.create().get().uri("https://www.boardgamegeek.com/xmlapi2/thing?id=" + i + "&stats=1")
                    .exchange().block().bodyToMono(BGGPayloadSearchResults.class).block();
            if (s.getType(0).equals("boardgame")) {
                GameDAO g = new GameDAO(s, 0);
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

    @Override
    public void getNextGameTerms() {
        String gameIdsParam = gameIdRangeToQueryParam();
        log.info("Fetching game IDs: {}", gameIdsParam);

        BGGPayloadSearchResults gameData = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/thing")
                        .queryParam("stats", 1)
                        .queryParam("type", "boardgame")
                        .queryParam("id", gameIdsParam)
                        .build())
                .retrieve()
                .bodyToMono(BGGPayloadSearchResults.class)
                .block();

        if (gameData == null) {
            log.warn("Received empty response from BGG");

        } else {
            for (int i = 0; i < gameData.getItemCount(); ++i) {
                GameDAO dao = new GameDAO(gameData, i);
                log.info("Saving game {} - \"{}\"", gameData.getId(i), gameData.getName(i));
                gameRepo.save(dao);
            }
        }
    }

    private String gameIdRangeToQueryParam() {
        final int MAX_ID_LENGTH = 8;
        StringBuilder queryParam = new StringBuilder(BGG_BATCH_SIZE * MAX_ID_LENGTH);

        int count = 0;
        boolean first = true;

        while (count < BGG_BATCH_SIZE) {
            // if we reached the max game ID, bail out.
            if (nextGameId > BGG_MAX_GAME_ID) {
                nextGameId = 1;
            }

            // every subsequent ID, except the first, needs a comma after it.
            if (first) {
                first = false;
            } else {
                queryParam.append(",");
            }

            // Add ID to string, and increment counters.
            queryParam.append(nextGameId);
            nextGameId++;
            count++;
        }

        return queryParam.toString();
    }
}