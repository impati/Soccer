package com.example.soccerleague.support.testData.game.feature;

import com.example.soccerleague.support.testData.game.Dto.StatBaseGameDto;

import java.util.Map;

public interface CornerKickChance {
    boolean  cornerKick(Long k ,int cross , StatBaseGameDto req);
    Long cornerKicker(Map<Long, StatBaseGameDto> mappedPlayer);
}
