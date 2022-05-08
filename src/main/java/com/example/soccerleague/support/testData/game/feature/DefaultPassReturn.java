package com.example.soccerleague.support.testData.game.feature;

import com.example.soccerleague.domain.Player.Position;
import com.example.soccerleague.domain.Player.Stat;
import com.example.soccerleague.support.testData.game.Dto.StatBaseGameDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class DefaultPassReturn implements PassReturn {
    @Override
    public int pass(StatBaseGameDto req) {
        int pass = 0;

        Stat stat = req.getStat();
        double percent = req.getPercent();
        Position position = req.getPosition();
        int defenseAvg = req.getOppDefense();


        int passStat = (int) (stat.getPass() * percent * 4);
        passStat += (int) (stat.getLongPass() * percent * 3);
        passStat += (int)(stat.getCrosses());
        passStat += (int)(stat.getVisualRange()*percent);
        passStat += (int)(stat.getSense()*percent);
        passStat -= (defenseAvg);
        int s, i;
        if(position.equals(Position.AM) ||
                position.equals(Position.CM) ||
                position.equals(Position.RM) ||
                position.equals(Position.LM) ||
                position.equals(Position.DM)
        ){
            s = passStat / 25;
            i = (2 * passStat / 25);
        }
        else{
            s = passStat / 45;
            i = (3 * passStat / 45);
        }


        int start = (int)(Math.random() * s);
        int increase = (int)(Math.random() * s) + i;
        int rn = (int)(Math.random() * increase) + start;
        int count = rn;
        while(count != 0){
            int rx = (int)(Math.random()*rn);
            if(rx > rn * 0.3) pass+=1;
            count -= 1;
        }

        return pass;
    }
}
