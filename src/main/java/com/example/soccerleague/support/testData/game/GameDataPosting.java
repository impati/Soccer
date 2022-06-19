package com.example.soccerleague.support.testData.game;

import com.example.soccerleague.SearchService.LeagueRound.Game.RoundGameResponse;

public interface GameDataPosting {
    void calculation(Long roundId, RoundGameResponse resp);
}
