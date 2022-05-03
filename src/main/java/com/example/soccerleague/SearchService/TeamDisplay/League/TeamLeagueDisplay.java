package com.example.soccerleague.SearchService.TeamDisplay.League;

import com.example.soccerleague.SearchService.SearchResult;
import com.example.soccerleague.domain.DataTransferObject;


/**
 * 팀의 시즌 정보와 해당 시즌에서 경기한 선수들을 가져옴.
 */
public interface TeamLeagueDisplay extends SearchResult {
    DataTransferObject search(DataTransferObject dataTransferObject);
}
