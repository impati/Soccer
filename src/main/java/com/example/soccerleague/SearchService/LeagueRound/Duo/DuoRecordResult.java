package com.example.soccerleague.SearchService.LeagueRound.Duo;

import com.example.soccerleague.SearchService.SearchResult;
import com.example.soccerleague.domain.DataTransferObject;

import java.util.List;

public interface DuoRecordResult extends SearchResult {
    List<DataTransferObject> searchList(DataTransferObject dataTransferObject);
}
