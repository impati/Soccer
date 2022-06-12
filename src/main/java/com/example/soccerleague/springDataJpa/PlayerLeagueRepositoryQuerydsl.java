package com.example.soccerleague.springDataJpa;

import com.example.soccerleague.SearchService.LeagueRecord.Player.LeaguePlayerRecordRequest;
import com.example.soccerleague.SearchService.LeagueRecord.Player.LeaguePlayerRecordResponse;
import com.example.soccerleague.domain.Direction;
import com.example.soccerleague.domain.SortType;

import java.util.List;

public interface PlayerLeagueRepositoryQuerydsl {
    List<LeaguePlayerRecordResponse> playerLeagueRecordQuery (LeaguePlayerRecordRequest request);
    Long totalCount(LeaguePlayerRecordRequest request);
}
