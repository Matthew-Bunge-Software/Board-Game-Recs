package org.bunge.bgginfo.bggintegration.service;

import org.bunge.bgginfo.game.GameDTO;

import java.util.List;

public interface BGGService {
    
    String getRandomGameTerms(int i);

    void getNextGameTerms();

    List<GameDTO> getMasterList(int average);
}