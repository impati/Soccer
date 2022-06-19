package com.example.soccerleague.SearchService.Round.Game;

import com.example.soccerleague.SearchService.SearchResult;
import com.example.soccerleague.domain.DataTransferObject;

import java.util.Optional;

public interface RoundGameSearch extends SearchResult {
    Optional<DataTransferObject> search(DataTransferObject dataTransferObject);
}
