package com.example.soccerleague.Web.dto.League;

import com.example.soccerleague.domain.DataTransferObject;
import lombok.Data;
/**
 * TODO: 전체 전적도 만들기.
 */

/**
 * 최근 5경기 양팀 맞대결
 */
@Data
public class RecentShowDown extends DataTransferObject {
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
    public static RecentShowDown create(String teamA,String teamB,int scoreA, int scoreB,int season,int roundSt){
        RecentShowDown obj = new RecentShowDown();
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
