package com.example.soccerleague.SearchService.playerEdit;

import com.example.soccerleague.SearchService.SearchResult;
import com.example.soccerleague.domain.DataTransferObject;

import java.util.Optional;

public interface PlayerEditSearch extends SearchResult {
    Optional<DataTransferObject> search(Long id);
    Optional<DataTransferObject> search(DataTransferObject dataTransferObject);
}
