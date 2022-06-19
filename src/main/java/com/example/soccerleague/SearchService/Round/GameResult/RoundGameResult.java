package com.example.soccerleague.SearchService.Round.GameResult;

import com.example.soccerleague.SearchService.SearchResult;
import com.example.soccerleague.domain.DataTransferObject;

import java.util.List;

public interface RoundGameResult extends SearchResult {
    List<DataTransferObject> searchPlayerResult(DataTransferObject dataTransferObject);
    List<DataTransferObject> searchTeamResult(DataTransferObject dataTransferObject);
}
