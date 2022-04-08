package com.example.soccerleague.SearchService;

import com.example.soccerleague.domain.DataTransferObject;

import java.util.List;

public interface SearchResult {
    boolean support(DataTransferObject dto);
    List<DataTransferObject> searchResult();
}
