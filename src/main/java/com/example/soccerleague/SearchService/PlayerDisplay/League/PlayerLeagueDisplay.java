package com.example.soccerleague.SearchService.PlayerDisplay.League;


import com.example.soccerleague.SearchService.SearchResult;
import com.example.soccerleague.domain.DataTransferObject;

import java.util.Optional;

public interface PlayerLeagueDisplay extends SearchResult {
    @Override
    default boolean supports(DataTransferObject dto){
        return dto instanceof PlayerLeagueDisplayDto;
    }
    Optional<DataTransferObject> search(DataTransferObject playerLeagueDisplayRequest);
}
