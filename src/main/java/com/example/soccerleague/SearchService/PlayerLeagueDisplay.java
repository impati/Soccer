package com.example.soccerleague.SearchService;


import com.example.soccerleague.Web.newDto.Player.PlayerLeagueDisplayDto;
import com.example.soccerleague.domain.DataTransferObject;

public interface PlayerLeagueDisplay extends SearchResult{
    @Override
    default boolean supports(DataTransferObject dto){
        return dto instanceof PlayerLeagueDisplayDto;
    }

}
