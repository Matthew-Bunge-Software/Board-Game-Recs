package org.bunge.bgginfo.game;

import org.bunge.bgginfo.bggintegration.service.BGGService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GameController {

    @Autowired
    BGGService bggService;

    @GetMapping("/terms")
    String getGame() throws Exception {
        return bggService.getRandomGameTerms(1);
    }
}