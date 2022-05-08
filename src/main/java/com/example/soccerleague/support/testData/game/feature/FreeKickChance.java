package com.example.soccerleague.support.testData.game.feature;

import com.example.soccerleague.support.testData.game.Dto.StatBaseGameDto;

import java.util.Map;

public interface FreeKickChance {
    StatBaseGameDto freeKicker(Map<Long,StatBaseGameDto> mappedPlayer);
    boolean freeKick(StatBaseGameDto req);
}
