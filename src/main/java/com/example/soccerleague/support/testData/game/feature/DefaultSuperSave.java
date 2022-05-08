package com.example.soccerleague.support.testData.game.feature;

import com.example.soccerleague.support.testData.game.Dto.StatBaseGameDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;

@Slf4j
@Component
public class DefaultSuperSave implements  SuperSave{
    @Override
    public boolean save(StatBaseGameDto req) {
        int oppGoalKeeperAbility = req.getOppGoalKeeperAbility();
        int possible = (int)(Math.random() * (oppGoalKeeperAbility / 10)) +  (oppGoalKeeperAbility / 10);
        int rn = (int)(Math.random() * 100);
        if(rn  < possible) {
            req.setOppSuperSave(req.getOppSuperSave() + 1);
            return true;
        }
        else return false;
    }
}
