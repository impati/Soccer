package com.example.soccerleague.support.testData.game.feature;

import com.example.soccerleague.support.testData.game.Dto.StatBaseGameDto;

import java.util.Map;

public interface ShootGoal {
    void shootAndGoal(Map<Long,StatBaseGameDto> mappedPlayer,StatBaseGameDto req);
}
