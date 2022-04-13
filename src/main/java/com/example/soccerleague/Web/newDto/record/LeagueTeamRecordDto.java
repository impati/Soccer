package com.example.soccerleague.Web.newDto.record;

import com.example.soccerleague.domain.DataTransferObject;
import lombok.Data;

@Data
public class LeagueTeamRecordDto extends DataTransferObject {
    //필요한 데이터
    private Long leagueId;
    private Integer season;


    //채워줄 데이터

    private String leagueName;


    //채워줄 List데이터들
    private int rank;
    private String teamName;
    private int gameNumber;
    private int point;
    private int win;
    private int draw;
    private int lose;
    private int gain;
    private int lost;
    private int diff;

    public static LeagueTeamRecordDto create(
            String teamName,int gameNumber,int win,
            int draw,int lose,int gain,int lost){
        LeagueTeamRecordDto obj = new LeagueTeamRecordDto();
        obj.setTeamName(teamName);
        obj.setGameNumber(gameNumber);
        obj.setWin(win);
        obj.setDraw(draw);
        obj.setLose(lose);
        obj.setGain(gain);
        obj.setLost(lost);
        obj.setDiff(gain - lost);
        obj.setPoint(win * 3 + draw);
        return obj;
    }
    public void update(int rank){
        this.setRank(rank);
    }

}
