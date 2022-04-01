package com.example.soccerleague.Web.dto.League;

import com.example.soccerleague.domain.DataTransferObject;
import lombok.Data;

//현재 시즌 리그 경기결과 가져오기.
@Data
public class LeagueRoundStrategyDto extends DataTransferObject {
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
