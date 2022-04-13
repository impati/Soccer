package com.example.soccerleague.Web.newDto.league;

import lombok.AllArgsConstructor;
import lombok.Data;
@Data
public class LeagueRoundInfo{
    private Long roundId;
    private boolean isDone;
    private String teamAName;
    private String teamBName;
    private int scoreA;
    private int scoreB;

    public static LeagueRoundInfo create(Long roundId,String teamAName,String teamBName){
        LeagueRoundInfo leagueRoundInfo = new LeagueRoundInfo();
        leagueRoundInfo.setRoundId(roundId);
        leagueRoundInfo.setDone(false);
        leagueRoundInfo.setTeamAName(teamAName);
        leagueRoundInfo.setTeamBName(teamBName);
        return leagueRoundInfo;
    }
    public static LeagueRoundInfo create(Long roundId,String teamAName,String teamBName,int scoreA,int scoreB){
        LeagueRoundInfo leagueRoundInfo = new LeagueRoundInfo();
        leagueRoundInfo.setRoundId(roundId);
        leagueRoundInfo.setDone(true);
        leagueRoundInfo.setTeamAName(teamAName);
        leagueRoundInfo.setTeamBName(teamBName);
        leagueRoundInfo.setScoreA(scoreA);
        leagueRoundInfo.setScoreB(scoreB);
        return leagueRoundInfo;
    }

}