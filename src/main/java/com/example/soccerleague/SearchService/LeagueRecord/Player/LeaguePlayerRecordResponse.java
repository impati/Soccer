package com.example.soccerleague.SearchService.LeagueRecord.Player;

import com.example.soccerleague.domain.DataTransferObject;
import com.example.soccerleague.domain.Direction;
import com.example.soccerleague.domain.SortType;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LeaguePlayerRecordResponse extends DataTransferObject {

    private SortType sortType;
    private Direction direction;
    private String name;
    private String team;
    private int goal;
    private int assist;
    private int attackPoint;
    private int shooting;
    private int validShooting;
    private int foul;
    private int pass;
    private int defense;

    public LeaguePlayerRecordResponse(SortType sortType, Direction direction, String name, String team) {
        this.sortType = sortType;
        this.direction = direction;
        this.name = name;
        this.team = team;
    }

    public void update(
            int goal,int assist,int shooting,
            int validShooting,int foul,int pass,int defense
    ){
        this.setAssist(this.getAssist() + assist);
        this.setGoal(this.getGoal() + goal);
        this.setShooting(this.getShooting() + shooting);
        this.setValidShooting(this.getValidShooting() + validShooting);
        this.setFoul(this.getFoul() + foul);
        this.setPass(this.getPass() + pass);
        this.setDefense(this.getDefense() + defense);
        this.setAttackPoint(this.getAttackPoint() + assist + assist);
    }
}
