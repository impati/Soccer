package com.example.soccerleague.SearchService.LeagueRound.Game;

import com.example.soccerleague.SearchService.SearchResult;
import com.example.soccerleague.domain.DataTransferObject;

import java.util.Optional;

public interface LeagueRoundGameSearch extends SearchResult {
    Optional<DataTransferObject> search(DataTransferObject dataTransferObject);
}
