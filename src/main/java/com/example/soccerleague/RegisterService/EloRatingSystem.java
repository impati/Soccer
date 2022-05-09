package com.example.soccerleague.RegisterService;

import com.example.soccerleague.domain.record.PlayerLeagueRecord;

import java.util.List;

public interface EloRatingSystem {
    void ratingCalc(List<PlayerLeagueRecord> plrA , List<PlayerLeagueRecord> plrB);
}
