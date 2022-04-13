package com.example.soccerleague.Web.newDto.record;

import com.example.soccerleague.domain.DataTransferObject;
import com.example.soccerleague.domain.Direction;
import com.example.soccerleague.domain.SortType;
import lombok.Data;

@Data
public class LeaguePlayerRecordDto extends DataTransferObject {

    //필요한 데이터
    private Integer season;
    private Long leagueId;
    private SortType sortType;
    private Direction direction;
    //채워줄 단일 데이터`

    private String leagueName;

    //채워줄 데이터
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


    public static LeaguePlayerRecordDto create(String name,String team,SortType sortType,Direction direction){
        LeaguePlayerRecordDto obj = new LeaguePlayerRecordDto();
        obj.setName(name);
        obj.setTeam(team);
        obj.setSortType(sortType);
        obj.setDirection(direction);
        return obj;
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
