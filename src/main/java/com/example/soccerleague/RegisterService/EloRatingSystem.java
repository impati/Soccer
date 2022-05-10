package com.example.soccerleague.RegisterService;

import com.example.soccerleague.domain.record.PlayerLeagueRecord;

import java.util.List;

public interface EloRatingSystem {
    void LeagueRatingCalc(List<PlayerLeagueRecord> plrA , List<PlayerLeagueRecord> plrB);
    void LeagueSeasonResultCalc(Long leagueId);
}
