package com.example.soccerleague.SearchService.LeagueRecord.team;

import com.example.soccerleague.domain.DataTransferObject;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LeagueTeamRecordResponse extends DataTransferObject {

    private int rank;
    private String teamName;
    private Long teamId;
    private int gameNumber;
    private int point;
    private int win;
    private int draw;
    private int lose;
    private int gain;
    private int lost;
    private int diff;


    public LeagueTeamRecordResponse(Long teamId,String teamName, int gameNumber,
                                    int win, int draw, int lose,
                                    int gain, int lost) {
        this.teamName = teamName;
        this.gameNumber = gameNumber;
        this.win = win;
        this.draw = draw;
        this.lose = lose;
        this.gain = gain;
        this.lost = lost;
        this.diff = this.gain - this.lost;
        this.point = 3*this.win + this.draw;
    }
}
