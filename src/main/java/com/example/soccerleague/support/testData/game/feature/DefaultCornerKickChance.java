package com.example.soccerleague.support.testData.game.feature;

import com.example.soccerleague.domain.Player.Position;
import com.example.soccerleague.domain.Player.Stat;
import com.example.soccerleague.domain.record.GoalType;
import com.example.soccerleague.support.testData.game.Dto.StatBaseGameDto;
import com.example.soccerleague.support.testData.game.DuoInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class DefaultCornerKickChance implements  CornerKickChance{
    private final SuperSave superSave;
    @Override
    public boolean cornerKick(Long k ,int cross , StatBaseGameDto req) {

        Position position = req.getPosition();
        Stat stat = req.getStat();
        double percent = req.getPercent();
        int defense = req.getOppDefense() ;
        int keeper = req.getOppGoalKeeperAbility();
        if(position.equals(Position.GK)) return false;


        int ability = cross ;
        ability += stat.getHeading() * 4 * percent;
        ability += stat.getJump() * 2 * percent;
        ability += stat.getPositioning() * 2 * percent;

        ability -= defense * 2;
        ability -= keeper * 3;

        int rn = (int)(Math.random() * ability / 10) + ability / 10;
        double possible = (double)((int)(Math.random() * (ability / 10)) + rn);
        possible /= 10.0;

        int rx = (int)(Math.random()*100);

        if(rx < possible) {
            req.setShooting(req.getShooting() + 1);
            int heading = (int)(Math.random() * 1000);
            if(heading > 300){
                if(!superSave.save(req)){
                    req.setValidShooting(req.getValidShooting() + 1);
                    req.getDuoResult().add(new DuoInfo(req.getPlayerId(),k, GoalType.HEADING));
                }

            }
            else{
                if(!superSave.save(req)){
                    req.setValidShooting(req.getValidShooting() + 1);
                    req.getDuoResult().add(new DuoInfo(req.getPlayerId(),k, GoalType.NOMAL));
                }
            }
            return true;
        }
        else if(rx %  10 == 0 ) {
            req.setShooting(req.getShooting() + 1);
            return false;
        }
        else return false;
    }


    public Long cornerKicker(Map<Long, StatBaseGameDto> mappedPlayer) {
        List<Long> kicker = new ArrayList<>();
        int count = 0;
        for(var s : mappedPlayer.keySet()){
            StatBaseGameDto element = mappedPlayer.get(s);
            Position position = element.getPosition();
            if(position.equals(Position.LM)
                    || position.equals(Position.RM)
            ) {
                count = 20;
                while(count != 0){
                    kicker.add(element.getPlayerId());
                    count -=1;
                }
            }
            else if(position.equals(Position.RF) || position.equals(Position.LF)){
                count = 5;
                while(count != 0){
                    kicker.add(element.getPlayerId());
                    count -=1;
                }
            }
            else if(position.equals(Position.RWB)
                    || position.equals(Position.LWB)
                    || position.equals(Position.AM)
                    || position.equals(Position.LB)
                    || position.equals(Position.RB)
            ){
                kicker.add(element.getPlayerId());
            }

        }
        if(kicker.size() == 0 )mappedPlayer.keySet().forEach(ele->kicker.add(ele));
        int idx = (int)(Math.random() * kicker.size());

        return kicker.get(idx);
    }


}
