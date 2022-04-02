package com.example.soccerleague.Service;

import com.example.soccerleague.domain.League;

import java.util.List;
import java.util.Optional;

public interface LeagueService {
    List<League> searchAll();
    Optional<League> searchLeague(Long id);
    void updateSeasonAndRoundSt(int season,int roundSt);
}
