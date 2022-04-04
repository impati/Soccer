package com.example.soccerleague.Web.dto.record.league;

import com.example.soccerleague.domain.DataTransferObject;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RecordPlayerLeagueDto extends DataTransferObject {



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


    public static RecordPlayerLeagueDto create(String name){
        RecordPlayerLeagueDto obj = new RecordPlayerLeagueDto();
        obj.setName(name);
        return obj;
    }
    public void update(
            String teamName,
            int goal,int assist,int shooting,
            int validShooting,int foul,int pass,int defense
    ){
        this.setTeam(teamName);
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
