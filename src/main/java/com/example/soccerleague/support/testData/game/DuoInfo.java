package com.example.soccerleague.support.testData.game;

import com.example.soccerleague.domain.Player.Player;
import com.example.soccerleague.domain.record.GoalType;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DuoInfo{
    Player player;
    Long goal;
    Long assist;
    GoalType goalType;

    public DuoInfo(Long goal, Long assist, GoalType goalType) {
        this.goal = goal;
        this.assist = assist;
        this.goalType = goalType;
    }
}