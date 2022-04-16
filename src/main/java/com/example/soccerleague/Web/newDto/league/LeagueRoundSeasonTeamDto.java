package com.example.soccerleague.Web.newDto.league;

import com.example.soccerleague.domain.DataTransferObject;
import lombok.Data;

@Data
public class LeagueRoundSeasonTeamDto extends DataTransferObject {

    //필요한 데이터
    private Long roundId;



    private String name;
    private String type;
    private int rank;


    private int TotalWin;
    private int TotalDraw;
    private int TotalLose;
    private String total;

    private int recentWin;
    private int recentDraw;
    private int recentLose;
    private String recent;


    private double avgGain;
    private double avgLost;
}
