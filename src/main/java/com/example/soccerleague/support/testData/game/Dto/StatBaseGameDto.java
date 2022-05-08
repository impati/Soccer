package com.example.soccerleague.support.testData.game.Dto;

import com.example.soccerleague.domain.Player.Position;
import com.example.soccerleague.domain.Player.Stat;
import com.example.soccerleague.support.testData.game.DuoInfo;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class StatBaseGameDto {
    private Long playerId;
    private Long teamId;
    private int oppDefense;
    private int oppGoalKeeperAbility;
    private int oppSuperSave;
    private int passSum;
    private double percent;
    private Stat stat;
    private Position position ;
    private List<DuoInfo> duoResult = new ArrayList<>();

    private int goal;
    private int assist;
    private int pass ;
    private int shooting;
    private int validShooting;
    private int foul;
    private int goodDefense;
    private int grade;

    public StatBaseGameDto(Long playerId, Long teamId, double percent, Stat stat, Position position) {
        this.playerId = playerId;
        this.teamId = teamId;
        this.percent = percent;
        this.stat = stat;
        this.position = position ;
    }
}
