package com.example.soccerleague.SearchService.LeagueRound.strategy;

import com.example.soccerleague.SearchService.SearchResult;
import com.example.soccerleague.domain.DataTransferObject;

import java.util.Optional;

public interface LeagueRoundSeasonTeam extends SearchResult {
    Optional<DataTransferObject > search(DataTransferObject dataTransferObject);
}
