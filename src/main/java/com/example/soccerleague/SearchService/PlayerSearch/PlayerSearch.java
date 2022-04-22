package com.example.soccerleague.SearchService.PlayerSearch;

import com.example.soccerleague.SearchService.SearchResult;
import com.example.soccerleague.Web.newDto.Player.PlayerSearchDto;
import com.example.soccerleague.domain.DataTransferObject;
import com.example.soccerleague.domain.Player.Position;

import java.util.HashSet;
import java.util.List;

public interface PlayerSearch  extends SearchResult {
    default boolean supports(DataTransferObject dto) {return dto instanceof PlayerSearchDto;}
    DataTransferObject search(String name, Long leagueId, Long teamId, List<Position> positions);
}
