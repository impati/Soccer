package com.example.soccerleague.Web.dto.League;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LeagueRoundSearchDto {
    private int season;
    private int roundSt;
    private int currentSeason;
    private int lastLeagueRound;

    public static LeagueRoundSearchDto create(int season,int roundSt,int currentSeason,int lastLeagueRound){
        LeagueRoundSearchDto leagueRoundSearchDto = new LeagueRoundSearchDto();
        leagueRoundSearchDto.setSeason(season);
        leagueRoundSearchDto.setRoundSt(roundSt);
        leagueRoundSearchDto.setLastLeagueRound(lastLeagueRound);
        leagueRoundSearchDto.setCurrentSeason(currentSeason);
        return leagueRoundSearchDto;
    }
}
