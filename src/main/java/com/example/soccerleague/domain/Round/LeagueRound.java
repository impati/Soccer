package com.example.soccerleague.domain.Round;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@Getter
@DiscriminatorValue("League")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LeagueRound extends Round{

    public static LeagueRound createLeagueRound(Long league,Long teamA,Long teamB,int season,int roundSt){
        LeagueRound leagueRound = new LeagueRound();
        leagueRound.setLeagueId(league);
        leagueRound.setRoundSt(roundSt);
        leagueRound.setHomeTeamId(teamA);
        leagueRound.setAwayTeamId(teamB);
        leagueRound.setSeason(season);
        leagueRound.setRoundStatus(RoundStatus.YET);
        return leagueRound;
    }
}
