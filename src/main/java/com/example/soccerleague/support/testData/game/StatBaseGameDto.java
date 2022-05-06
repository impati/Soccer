package com.example.soccerleague.support.testData.game;

import com.example.soccerleague.domain.Player.Stat;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class StatBaseGameDto {
    private Long playerId;
    private Long teamId;
    private int oppDefense;
    private int oppGoalKeeperAbility;
    private int passSum;
    private double percent;
    private Stat stat;
    private List<DuoInfo> DuoResult = new ArrayList<>();
}
