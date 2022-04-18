package com.example.soccerleague.Web.newDto.league;

import com.example.soccerleague.domain.DataTransferObject;
import com.example.soccerleague.domain.Round.Round;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LeagueRoundTopPlayerDto extends DataTransferObject {
    private Long TeamId;
    private Round round;
    //채워줄 정보
    private String name;
    private int goal;
    private int assist;


    public static LeagueRoundTopPlayerDto create(Long teamId,Round round){
        LeagueRoundTopPlayerDto obj = new LeagueRoundTopPlayerDto();
        obj.setTeamId(teamId);
        obj.setRound(round);
        return obj;
    }
    public LeagueRoundTopPlayerDto(String name, int goal, int assist) {
        this.name = name;
        this.goal = goal;
        this.assist = assist;
    }

    public LeagueRoundTopPlayerDto(String name) {
        this.name = name;
    }

    public void update(int goal, int assist){
        this.setGoal(this.getGoal() + goal);
        this.setAssist(this.getAssist() + assist);
    }
}
