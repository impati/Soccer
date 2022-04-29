package com.example.soccerleague.SearchService.LeagueRound.GameResult;

import com.example.soccerleague.SearchService.SearchResult;
import com.example.soccerleague.domain.DataTransferObject;

import java.util.List;

public interface LeagueRoundGameResult extends SearchResult {
    List<DataTransferObject> searchPlayerResult(DataTransferObject dataTransferObject);
    List<DataTransferObject> searchTeamResult(DataTransferObject dataTransferObject);
}
