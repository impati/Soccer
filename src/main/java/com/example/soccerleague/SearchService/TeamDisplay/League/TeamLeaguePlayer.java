package com.example.soccerleague.SearchService.TeamDisplay.League;

import com.example.soccerleague.SearchService.SearchResult;
import com.example.soccerleague.domain.DataTransferObject;

import java.util.List;

/**
 * 어떤 시즌에 뛴 선수들을 조회하는 기능
 */
public interface TeamLeaguePlayer extends SearchResult {
    List<DataTransferObject> search(DataTransferObject dataTransferObject);
}
