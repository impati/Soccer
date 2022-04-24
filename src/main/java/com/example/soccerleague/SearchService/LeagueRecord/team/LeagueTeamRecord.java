package com.example.soccerleague.SearchService.LeagueRecord.team;

import com.example.soccerleague.SearchService.SearchResult;
import com.example.soccerleague.domain.DataTransferObject;
import lombok.Data;

import java.util.List;

public interface LeagueTeamRecord extends SearchResult {
    List<DataTransferObject>  searchList(DataTransferObject dataTransferObject);
}
