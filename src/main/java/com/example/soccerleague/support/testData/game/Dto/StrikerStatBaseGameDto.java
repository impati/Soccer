package com.example.soccerleague.support.testData.game.Dto;

import com.example.soccerleague.domain.Player.Position;
import com.example.soccerleague.domain.Player.Stat;

public class StrikerStatBaseGameDto extends StatBaseGameDto{
    public StrikerStatBaseGameDto(Long playerId, Long teamId, double percent, Stat stat, Position position) {
        super(playerId, teamId, percent, stat, position);
    }
}
