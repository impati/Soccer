package com.example.soccerleague.SearchService.PlayerDisplay.Total;

import com.example.soccerleague.SearchService.SearchResult;
import com.example.soccerleague.domain.DataTransferObject;

import java.util.Optional;

public interface PlayerTotal extends SearchResult {
    Optional<DataTransferObject> search(DataTransferObject dataTransferObject);
}
