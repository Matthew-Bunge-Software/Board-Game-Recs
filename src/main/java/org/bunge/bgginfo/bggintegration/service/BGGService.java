package org.bunge.bgginfo.bggintegration.service;

import java.util.List;

import org.bunge.bgginfo.game.GameDTO;

public interface BGGService {
    
    public String getRandomGameTerms(int i);

    public List<GameDTO> getMasterList(int average);
}