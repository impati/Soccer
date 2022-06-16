package com.example.soccerleague.support.testData.LineUp;

import com.example.soccerleague.RegisterService.LeagueRound.LineUp.RoundLineUpDto;
import com.example.soccerleague.SearchService.Round.LineUp.RoundLineUpResponse;

public interface LineUpDataPosting {
    RoundLineUpDto calculation(Long roundId , RoundLineUpResponse resp);
}
