package com.example.soccerleague.support.testData.game.feature;

import com.example.soccerleague.domain.Player.Position;
import com.example.soccerleague.domain.Player.Stat;
import com.example.soccerleague.support.testData.game.Dto.StatBaseGameDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static org.junit.jupiter.api.Assertions.*;


class DefaultPassReturnTest {

    Map<Long , StatBaseGameDto> mappedPlayer = new ConcurrentHashMap<>();
    @BeforeEach
    void prePost(){
        for(Long i =0L;i<11L;i++){
            int rn = 70;
            Stat stat = Stat.createStat(rn, rn, rn, rn, rn, rn, rn, rn,
                    rn, rn, rn, rn, rn, rn, rn, rn, rn, rn,
                    rn, rn, rn, rn, rn, rn, rn, rn, rn
            );
            StatBaseGameDto req = new StatBaseGameDto(i, 1L, 1.0, stat, Position.CF);
            mappedPlayer.put(i,req);
        }

    }
    @Test
    void passTest(){
        DefaultPassReturn pt = new DefaultPassReturn();
        DefaultFoulAndDefenseReturn fd = new DefaultFoulAndDefenseReturn();

        int count = 100;
        int avg = 0;
        int x = 90;
        int i = 0 ;
        while(i  < count) {
//            int rn = (int) (Math.random() * 50) + 50;
            int rn = x;
            Stat stat = Stat.createStat(0, 0, rn, rn, rn, 0, 0, 0,
                    rn, rn, rn, 0, 0, 0, 0, 0, rn, rn,
                    rn, rn, 0, 0, 0, 0, 0, rn, rn
            );
            StatBaseGameDto req = new StatBaseGameDto(1L, 1L, 1.0, stat, Position.CF);
            req.setOppDefense(0);
            int ret = fd.foul(req);
            System.out.println(" defense : " + req.getGoodDefense() + " " + "  foul :" + req.getFoul() +  "  oppFreeKickChance : " + ret);

            i ++;
        }
    }

    @Test
    void freeTest(){
        DefaultSuperSave ss = new DefaultSuperSave();
        DefaultFreeKickChance fkc = new DefaultFreeKickChance(ss);


        int count = 100;
        int avg = 0;
        int x = 120;
        int i = 0 ;
        int ret = 0;
        while(i  < count) {
//            int rn = (int) (Math.random() * 50) + 50;
            int rn = x;
            Stat stat = Stat.createStat(0, 0, rn, rn, rn, 0, 0, 0,
                    rn, rn, rn, 0, rn, rn, rn, 0, rn, rn,
                    rn, rn, 0, 0, 0, 0, 0, rn, rn
            );
            StatBaseGameDto req = new StatBaseGameDto(1L, 1L, 1.0, stat, Position.CF);
            if(fkc.freeKick(req)) ret +=1;
            i+=1;
        }
        System.out.println("ret: " + ret);
    }

    @Test
    void LongKickTest(){
        DefaultSuperSave ss = new DefaultSuperSave();
        DefaultShootGoal sg = new DefaultShootGoal(ss);

        int count = 100;
        int avg = 0;
        int x = 70;
        int i = 0 ;
        int ret = 0;
        while(i  < count) {
//            int rn = (int) (Math.random() * 50) + 50;
            int rn = x;
            Stat stat = Stat.createStat(0, 0, rn, rn, rn, 0, 0, 0,
                    rn, rn, rn, 0, rn, rn, rn, 0, rn, rn,
                    rn, rn, 0, 0, 0, 0, 0, rn, rn
            );
            StatBaseGameDto req = new StatBaseGameDto(1L, 1L, 1.0, stat, Position.CF);
            req.setPassSum(150);
            req.setOppGoalKeeperAbility(70);
            sg.shootAndGoal(mappedPlayer,req);
            System.out.println(" shooting :" + req.getShooting() +" validShooting : " + req.getValidShooting() + " Shooting : " + req.getDuoResult().size() + " superSave " + req.getOppSuperSave());
            i+=1;
        }




    }


}