package com.example.soccerleague.support.testData.game.feature;

import com.example.soccerleague.domain.Player.Stat;
import com.example.soccerleague.support.testData.game.Dto.StatBaseGameDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class DefaultDefenseReturn implements DefenseReturn{
    public int defenseValue (StatBaseGameDto req){
        int ret = 0 ;
        Stat stat = req.getStat();
        double percent = req.getPercent();
        ret += stat.getDefense()*3 * percent;
        ret += stat.getTackle() * percent;
        ret += stat.getIntercepting()*2 * percent;
        ret += stat.getSlidingTackle() * percent;
        ret += stat.getPhysicalFight()*percent;
        ret += stat.getActiveness() * percent;
        ret += stat.getSense() / 2 * percent;
        ret += stat.getSpeedReaction() / 2 * percent;
        return ret / 10;
    }
}
