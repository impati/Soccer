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
@RequiredArgsConstructor
@Component
public class DefaultShootGoal implements ShootGoal {
    private final SuperSave superSave;

    private Long assistant(Map<Long, StatBaseGameDto> mappedPlayer) {
        List<Long> kicker = new ArrayList<>();
        int count = 0;
        for(var s : mappedPlayer.keySet()){
            StatBaseGameDto element = mappedPlayer.get(s);
            Position position = element.getPosition();
            if(position.equals(Position.LM)
                    || position.equals(Position.RM) || position.equals(Position.CF)
                    || position.equals(Position.ST) || position.equals(Position.RF)
                    || position.equals(Position.LF) || position.equals(Position.AM)
            ) {
                count = 30;
                while(count != 0){
                    kicker.add(element.getPlayerId());
                    count -=1;
                }
            }
            else if(position.equals(Position.DM) || position.equals(Position.CM)){
                count = 5;
                while(count != 0){
                    kicker.add(element.getPlayerId());
                    count -=1;
                }
            }
            else if(position.equals(Position.RWB)
                    || position.equals(Position.LWB)
                    || position.equals(Position.LB)
                    || position.equals(Position.RB)
                    || position.equals(Position.CB)
            ){
                kicker.add(element.getPlayerId());
            }

        }

        if(kicker.size() == 0 )mappedPlayer.keySet().forEach(ele->kicker.add(ele));
        int idx = (int)(Math.random() * kicker.size());



        return kicker.get(idx);
    }
    private GoalType goalTypeDecision(StatBaseGameDto req){
        int a = (req.getStat().getJump() + req.getStat().getHeading())/10;
        int value = (int)(Math.random() * 100);
        if(value < a) return GoalType.HEADING;
        else return GoalType.NOMAL;
    }
    @Override
    public void shootAndGoal(Map<Long, StatBaseGameDto> mappedPlayer,StatBaseGameDto req) {

        Stat stat = req.getStat();
        double percent = req.getPercent();
        Position position = req.getPosition();


        int pass = req.getPassSum() / 10;
        if(position.equals(Position.GK))return ;
        // 중거리슛
        int midStat = (int)(stat.getGoalDetermination() * 3 * percent);
        midStat += stat.getMidRangeShot()*6 * percent;
        midStat += stat.getShootPower()*1*percent;

        int rn = (int)(Math.random() * (pass / 3));

        int possible = 0;

        if(position.equals(Position.ST) || position.equals(Position.RF)
                ||position.equals(Position.LF) || position.equals(Position.CF)
        ){
            possible = 90;
        }
        else if(position.equals(Position.AM) || position.equals(Position.LM)
                ||position.equals(Position.RM) || position.equals(Position.CM)
        ){
            possible = 70;
        }
        else{
            possible = 20;
        }

        while(rn != 0){
            int r = (int)(Math.random()*100);
            if(r < possible) {
                req.setShooting(req.getShooting() + 1);
                int rx = (int) (Math.random() * midStat);
                if (rx > 500 && rx % 3 == 0) {
                    req.setValidShooting(req.getValidShooting() + 1);
                    if (!superSave.save(req)) {
                        Long as = req.getPlayerId();
                        while (as.equals(req.getPlayerId())) {
                            as = assistant(mappedPlayer);
                        }
                        req.getDuoResult().add(new DuoInfo(req.getPlayerId(), as, GoalType.LONGKICK));
                    }
                }
            }

            rn -= 1;
        }



        int shootStat = midStat / 10;
        shootStat += stat.getDribble() * percent;
        shootStat += stat.getAcceleration() *percent;
        shootStat += stat.getSpeed() * percent;
        shootStat += stat.getBalance() *percent / 2;
        shootStat += stat.getBallControl() *percent;
        shootStat += stat.getPositioning() * percent;
        shootStat += stat.getSense() / 2 *percent;
        shootStat += stat.getGoalDetermination() * 3;



        rn = (int)(Math.random() * (pass / 7));

        possible = (int)(Math.random() * (shootStat / 20)) + shootStat / 20;

        if(position.equals(Position.ST) || position.equals(Position.RF)
                ||position.equals(Position.LF) || position.equals(Position.CF)
        ){
            possible *= 1.5;
        }
        else if(position.equals(Position.AM) || position.equals(Position.LM)
                ||position.equals(Position.RM) || position.equals(Position.CM)
        ){
            possible *= 1.3;
        }
        else{
            possible *= 0.2;
        }

        while(rn != 0){
            int r = (int)(Math.random()*(100));
            if(r < possible) {
                req.setShooting(req.getShooting() + 1);
                int rx = (int) (Math.random() * shootStat);
                if (rx > 500 ) {
                    req.setValidShooting(req.getValidShooting() + 1);
                    if (!superSave.save(req)) {
                        Long as = req.getPlayerId();
                        while (as.equals(req.getPlayerId())) {
                            as = assistant(mappedPlayer);
                        }
                        // 헤딩 , 노말,
                        req.getDuoResult().add(new DuoInfo(req.getPlayerId(), as, goalTypeDecision(req)));
                    }
                }
            }

            rn -= 1;
        }




    }
}
