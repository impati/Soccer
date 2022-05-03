package com.example.soccerleague.support.testData.LineUp;

import com.example.soccerleague.RegisterService.LeagueRound.LineUp.LeagueRoundLineUpDto;
import com.example.soccerleague.SearchService.LeagueRound.LineUp.LeagueRoundLineUpResponse;

public interface LineUpDataPosting {
    LeagueRoundLineUpDto calculation(Long roundId , LeagueRoundLineUpResponse resp);
}
