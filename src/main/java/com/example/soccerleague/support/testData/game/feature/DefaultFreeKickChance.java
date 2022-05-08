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
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Component
@RequiredArgsConstructor
public class DefaultFreeKickChance implements FreeKickChance{
    private final SuperSave superSave;

    @Override
    public StatBaseGameDto freeKicker(Map<Long, StatBaseGameDto> mappedPlayer) {
        List<StatBaseGameDto> kicker = new ArrayList<>();
        mappedPlayer.keySet().stream().map(ele->mappedPlayer.get(ele)).forEach(ele->{
            if(ele.getPosition().equals(Position.ST) || ele.getPosition().equals(Position.CF)
                    || ele.getPosition().equals(Position.RF) || ele.getPosition().equals(Position.LF)){

                kicker.add(ele);
            }
        });
        int idx = 0;
        int ret = -1;
        for(int i =0;i<kicker.size();i++){
            Stat stat = kicker.get(i).getStat();
            int  value = stat.getGoalDetermination() * 4;
            value += stat.getMidRangeShot() *3;
            value += stat.getShootPower() * 3;
            if(ret< value){
                ret = value;
                idx = i;
            }
        }
        return kicker.get(idx);
    }

    @Override
    public boolean freeKick(StatBaseGameDto req) {

        Stat stat = req.getStat();
        double percent = req.getPercent();


        req.setShooting(req.getShooting()  +1);

        int  kickStat =(int)(stat.getGoalDetermination() * 4 * percent);
        kickStat += stat.getMidRangeShot() *3 * percent;
        kickStat += stat.getShootPower() * 3 * percent;


       int rn = (int)(Math.random() * (kickStat / 100) + (kickStat / 100));
       double possible = kickStat * rn / 1000;
       while(rn !=0 ){
           double rx = (Math.random() *  1000);
           if(rx < possible) {
               req.setValidShooting(req.getValidShooting() + 1);
               if(!superSave.save(req)){
                   req.getDuoResult().add(new DuoInfo(req.getPlayerId(),0L, GoalType.FREEKICK));
               }
               return true;
           }

           rn-=1;
       }

        return false;
    }
}
