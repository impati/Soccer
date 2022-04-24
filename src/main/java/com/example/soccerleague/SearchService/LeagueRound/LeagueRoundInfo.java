package com.example.soccerleague.SearchService.LeagueRound;

import com.example.soccerleague.SearchService.SearchResult;
import com.example.soccerleague.domain.DataTransferObject;

import java.util.List;

public interface LeagueRoundInfo extends SearchResult {
    List<DataTransferObject> searchList(DataTransferObject dataTransferObject);
}
