package com.example.soccerleague.RegisterService;

import com.example.soccerleague.domain.Round.Round;
import com.example.soccerleague.domain.record.PlayerLeagueRecord;
import com.example.soccerleague.domain.record.PlayerRecord;

import java.util.List;

public interface EloRatingSystem {
    void ratingCalc(Round round , List<PlayerRecord> plrA , List<PlayerRecord> plrB);
    void seasonResultCalc(Round round ,Long leagueId);

}
