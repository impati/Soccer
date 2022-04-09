package com.example.soccerleague.SearchService;

import com.example.soccerleague.domain.DataTransferObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface SearchResult {
    boolean supports(DataTransferObject dto);
    default List<DataTransferObject> searchResultList(DataTransferObject dto) {
        return new ArrayList<>();
    }
    default Optional<DataTransferObject> searchResult(DataTransferObject dto) {
        return Optional.empty();
    }
}
