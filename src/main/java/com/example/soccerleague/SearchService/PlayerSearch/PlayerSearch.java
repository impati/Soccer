package com.example.soccerleague.SearchService.PlayerSearch;

import com.example.soccerleague.SearchService.SearchResult;
import com.example.soccerleague.domain.DataTransferObject;

import java.util.List;
import java.util.Optional;

public interface PlayerSearch  extends SearchResult {
    default boolean supports(DataTransferObject dto) {return dto instanceof PlayerSearchRequest;}
    List<DataTransferObject> searchList(DataTransferObject playerSearchRequest);

}
