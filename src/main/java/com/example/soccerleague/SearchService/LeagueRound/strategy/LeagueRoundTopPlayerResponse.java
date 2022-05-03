package com.example.soccerleague.SearchService.LeagueRound.strategy;

import com.example.soccerleague.domain.DataTransferObject;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LeagueRoundTopPlayerResponse extends DataTransferObject {
    private String name;
    private int goal;
    private int assist;

    public LeagueRoundTopPlayerResponse(String name) {
        this.name = name;
    }
    public void update(int goal, int assist){
        this.setGoal(this.getGoal() + goal);
        this.setAssist(this.getAssist() + assist);
    }
}
