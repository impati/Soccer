package com.example.soccerleague.support.testData.game;

import com.example.soccerleague.RegisterService.LeagueRound.Game.LeagueRoundGameDto;
import com.example.soccerleague.SearchService.LeagueRound.Game.LeagueRoundGameResponse;

public interface GameDataPosting {
    void calculation(Long roundId,LeagueRoundGameResponse resp);
}
