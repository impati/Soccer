package com.example.soccerleague.SearchService.LeagueRound.strategy;

import com.example.soccerleague.SearchService.SearchResult;
import com.example.soccerleague.domain.DataTransferObject;

import java.util.List;

public interface ShowDown extends SearchResult {
    List<DataTransferObject> searchList(DataTransferObject dataTransferObject);
}
