package com.example.soccerleague.SearchService.PlayerDisplay;

import com.example.soccerleague.SearchService.SearchResult;
import com.example.soccerleague.domain.DataTransferObject;

import java.util.Optional;

public interface PlayerDisplay extends SearchResult {
    Optional<DataTransferObject> search(Long id);
    Optional<DataTransferObject> search(DataTransferObject dataTransferObject);
}
