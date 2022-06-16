package com.example.soccerleague.SearchService.Round.LineUp;

import com.example.soccerleague.SearchService.SearchResult;
import com.example.soccerleague.domain.DataTransferObject;

import java.util.Optional;

public interface RoundLineUpSearch extends SearchResult {
    Optional<DataTransferObject> search(DataTransferObject dataTransferObject);
}
