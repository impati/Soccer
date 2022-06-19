package com.example.soccerleague.support.testData.game;


import com.example.soccerleague.SearchService.Round.Game.RoundGameResponse;

public interface GameDataPosting {
    void calculation(Long roundId, RoundGameResponse resp);
}
