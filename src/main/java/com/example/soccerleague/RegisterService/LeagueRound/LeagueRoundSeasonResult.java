package com.example.soccerleague.RegisterService.LeagueRound;

import com.example.soccerleague.domain.DataTransferObject;
import com.example.soccerleague.domain.Team;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LeagueRoundSeasonResult extends DataTransferObject {
    private int rank;
    private Team team;

    private int win;
    private int draw;
    private int lose;

    private int point;

    private int gain;
    private int lost;
    private int diff;
    public static LeagueRoundSeasonResult create(Team team,int win,int draw ,int lose,int gain,int lost){
        LeagueRoundSeasonResult obj = new LeagueRoundSeasonResult();
        obj.setWin(win);
        obj.setDraw(draw);
        obj.setLose(lose);
        obj.setTeam(team);
        obj.setPoint(win*3 + draw);
        obj.setGain(gain);
        obj.setLost(lost);
        obj.setDiff(gain - lost);
        return obj;
    }


}
