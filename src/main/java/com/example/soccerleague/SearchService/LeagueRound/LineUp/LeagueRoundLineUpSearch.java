package com.example.soccerleague.SearchService.LeagueRound.LineUp;

import com.example.soccerleague.SearchService.SearchResult;
import com.example.soccerleague.domain.DataTransferObject;

import java.util.Optional;

public interface LeagueRoundLineUpSearch extends SearchResult {
    Optional<DataTransferObject> search(DataTransferObject dataTransferObject);
}
