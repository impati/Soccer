package com.example.soccerleague.SearchService.LeagueRound.strategy;

import com.example.soccerleague.Web.newDto.league.ShowDownDto;
import com.example.soccerleague.domain.DataTransferObject;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class ShowDownResponse extends DataTransferObject {
    private String teamA;
    private String teamB;

    private int scoreA;
    private int scoreB;

    private int season;
    private int roundSt;

    //리그? 챔피언스리그? 유로파?
    private String type;

    /**
     * 리그 타입 생성
     * @param teamA
     * @param teamB
     * @param scoreA
     * @param scoreB
     * @return
     */
    public static ShowDownResponse create(String teamA, String teamB, int scoreA, int scoreB, int season, int roundSt){
        ShowDownResponse obj = new ShowDownResponse();
        obj.setTeamA(teamA);
        obj.setTeamB(teamB);
        obj.setScoreA(scoreA);
        obj.setScoreB(scoreB);
        obj.setSeason(season);
        obj.setRoundSt(roundSt);
        obj.setType("League");
        return obj;
    }
}
