package com.example.soccerleague.SearchService.LeagueRecord.Player;

import com.example.soccerleague.SearchService.SearchResult;
import com.example.soccerleague.domain.DataTransferObject;

import java.util.List;

public interface LeaguePlayerRecord extends SearchResult {
    List<DataTransferObject> searchList(DataTransferObject dataTransferObject);
}
