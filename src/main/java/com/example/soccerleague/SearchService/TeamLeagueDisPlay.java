package com.example.soccerleague.SearchService;

import com.example.soccerleague.Web.newDto.Team.TeamLeagueDisplayDto;
import com.example.soccerleague.domain.DataTransferObject;

public interface TeamLeagueDisPlay extends SearchResult{
    @Override
    default boolean supports(DataTransferObject dto){
        return dto instanceof TeamLeagueDisplayDto;
    }

}
