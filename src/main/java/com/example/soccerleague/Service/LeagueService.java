package com.example.soccerleague.Service;

import com.example.soccerleague.domain.League;

import java.util.List;

public interface LeagueService {
    List<League> searchAll();
    void updateSeasonAndRoundSt(int season,int roundSt);
}
