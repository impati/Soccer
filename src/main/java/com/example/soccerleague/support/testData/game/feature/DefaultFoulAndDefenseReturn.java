package com.example.soccerleague.support.testData.game.feature;

import com.example.soccerleague.domain.Player.Position;
import com.example.soccerleague.domain.Player.Stat;
import com.example.soccerleague.support.testData.game.Dto.StatBaseGameDto;
import org.springframework.stereotype.Component;

@Component
public class DefaultFoulAndDefenseReturn implements FoulAndDefenseReturn {
    // 심한 파울 수를 리턴.
    @Override
    public int foul(StatBaseGameDto req) {

        Stat stat = req.getStat();
        double percent = req.getPercent();
        Position position = req.getPosition();
        if(position.equals(Position.GK)) return 0;
        int defenseStat  = (int)(stat.getDefense() * percent * 2);
        defenseStat += stat.getTackle() * percent;
        defenseStat += stat.getIntercepting() * percent * 2;
        defenseStat += stat.getSlidingTackle() * percent;
        defenseStat += stat.getPhysicalFight() * percent;
        defenseStat += stat.getActiveness() * percent;
        defenseStat += stat.getSense() * percent;
        defenseStat += stat.getStamina() * percent;

        // defense


        int s, i;
        if(position.equals(Position.AM) ||
                position.equals(Position.CM) ||
                position.equals(Position.DM) ||
                position.equals(Position.LWB) ||
                position.equals(Position.RWB) ||
                position.equals(Position.CB) ||
                position.equals(Position.LB) ||
                position.equals(Position.RB)
        ){
            s = defenseStat / 25;
            i = (2 * defenseStat / 25);
        }
        else{
            s = defenseStat / 45;
            i = (3 * defenseStat / 45);
        }

        int defense = 0;
        int start = (int)(Math.random() * s);
        int increase = (int)(Math.random() * s) + i;
        int rn = (int)(Math.random() * increase) + start;
        int count = rn;
        while(count != 0){
            int rx = (int)(Math.random()*rn);
            if(rx > rn * 0.4) defense+=1;
            count -= 1;
        }

        req.setGoodDefense(defense / 2);



        // foul
        int freeKick = 0;
        int foul = 0;
        foul = (int)(Math.random()*(defenseStat/(defenseStat / 7)));
        req.setFoul(foul);

        rn = (int)(Math.random()*100);
        while(foul !=0){
            int rx = (int)(Math.random() * 100);
            if(rx > 95){
                // 상대편 프리킥 찬스
                freeKick += 1;
            }
            foul -= 1;
        }


        return freeKick;

    }
}
