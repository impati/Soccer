package com.example.soccerleague.SearchService.LeagueRound.strategy;

import com.example.soccerleague.SearchService.SearchResult;
import com.example.soccerleague.domain.DataTransferObject;

import java.util.List;
import java.util.Optional;

public interface LeagueRoundTopPlayer extends SearchResult {
    List<DataTransferObject> search(DataTransferObject dataTransferObject);
}
