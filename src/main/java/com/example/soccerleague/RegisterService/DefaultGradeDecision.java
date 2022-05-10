package com.example.soccerleague.RegisterService;

import com.example.soccerleague.EntityRepository.PlayerLeagueRecordEntityRepository;
import com.example.soccerleague.domain.Player.Position;
import com.example.soccerleague.domain.record.MatchResult;
import com.example.soccerleague.domain.record.PlayerLeagueRecord;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
@Slf4j
@Service
@RequiredArgsConstructor
public class DefaultGradeDecision implements GradeDecision{
    private final PlayerLeagueRecordEntityRepository playerLeagueRecordEntityRepository;
    @Override
    public void LeagueGradeDecision(List<PlayerLeagueRecord> plr) {
        AvgDto avg = playerLeagueRecordEntityRepository.findByGameAvg();
        mappedMid(avg.getPassAvg(),avg.getShootingAvg(),avg.getDefenseAvg(),plr);
        mappedDefenser(avg.getPassAvg(),avg.getShootingAvg(),avg.getDefenseAvg(),plr);
        mappedStriker(avg.getPassAvg(),avg.getShootingAvg(),avg.getDefenseAvg(),plr);
        mappedGoalKeeper(plr);

    }

    private void  mappedDefenser(double passAvg,double shootingAvg,double defenseAvg, List<PlayerLeagueRecord> plr){

        plr.stream().filter(ele->ele.getGrade() == 0).forEach(ele-> {

            Position position = ele.getPosition();
            if (position.equals(Position.CB) || position.equals(Position.RB) ||
                    position.equals(Position.LB) || position.equals(Position.LWB) || position.equals(Position.RWB)
            ) {

                int goal = ele.getGoal();
                int assist = ele.getAssist();
                int shooting = ele.getShooting();
                int validShooting = ele.getValidShooting();
                int pass = ele.getPass();
                int defense = ele.getGoodDefense();
                int foul = ele.getFoul();

                double ret = 0;

                int attackPoint = goal + assist ;
                if(attackPoint == 1) ret = 150;
                else if(attackPoint == 2) ret = 180;
                else if(attackPoint == 3) ret = 210;
                else if(attackPoint == 4) ret = 240;
                else if(attackPoint == 5) ret = 270;
                else if(attackPoint > 5) ret = 300;

                double passScore = (((pass / passAvg)) * 200)/2;
                ret += passScore >= 200 ? 200 : passScore;
                double shootingScore = (((shooting / shootingAvg)) * 50) / 2;
                ret += shootingScore > 25 ? 25:shootingScore;
                double defenseScore = (((defense / defenseAvg)) * 400) /2;
                ret += defenseScore >= 400 ? 400 : defenseScore;

                if(validShooting == 1) ret += 5;
                else if(validShooting == 2) ret += 12;
                else if(validShooting == 3) ret += 21;
                else if(validShooting == 4) ret += 32;
                else if(validShooting == 5) ret += 41;
                else if(validShooting > 5) ret += 50;

                if(foul <= 1) ret -= 0;
                else if(foul <= 3) ret -= 10;
                else if(foul <= 5) ret -= 20;
                else if(foul <= 7) ret -= 30;
                else if(foul <= 9) ret -= 40;
                else ret -= 50;

                if(ele.getMathResult().equals(MatchResult.WIN)) ret += 40;
                if(ele.getMathResult().equals(MatchResult.DRAW)) ret += 20;
                ele.setGrade((int)ret / 10);


            }

        });








    }

    private void mappedStriker(double passAvg,double shootingAvg,double defenseAvg,List<PlayerLeagueRecord> plr){

        plr.stream().filter(ele->ele.getGrade() == 0).forEach(ele-> {
            Position position = ele.getPosition();
            if(position.equals(Position.ST )|| position.equals(Position.LF) ||
                    position.equals(Position.RF) || position.equals(Position.CF)
            ) {
                int goal = ele.getGoal();
                int assist = ele.getAssist();
                int shooting = ele.getShooting();
                int validShooting = ele.getValidShooting();
                int pass = ele.getPass();
                int defense = ele.getGoodDefense();
                int foul = ele.getFoul();

                double ret = 0;

                int attackPoint = goal + assist ;
                if(attackPoint == 1) ret = 200;
                else if(attackPoint == 2) ret = 250;
                else if(attackPoint == 3) ret = 270;
                else if(attackPoint == 4) ret = 300;
                else if(attackPoint == 5) ret = 350;
                else if(attackPoint > 5) ret = 400;




                double passScore = (((pass / passAvg))  * 150)/2;
                ret += passScore >= 100 ? 100 : passScore;
                double shootingScore = (((shooting / shootingAvg)) * 200)/2;
                ret += shootingScore > 150 ? 150:shootingScore;
                double defenseScore = (((defense / defenseAvg)) * 100)/2;
                ret += defenseScore >= 50 ? 50 : defenseScore;




                if(validShooting == 1) ret += 60;
                else if(validShooting == 2) ret += 80;
                else if(validShooting == 3) ret += 100;
                else if(validShooting == 4) ret += 110;
                else if(validShooting == 5) ret += 130;
                else if(validShooting > 5) ret += 150;

                if(foul <= 1) ret -= 0;
                else if(foul <= 3) ret -= 10;
                else if(foul <= 5) ret -= 20;
                else if(foul <= 7) ret -= 30;
                else if(foul <= 9) ret -= 40;
                else ret -= 50;


                if(ele.getMathResult().equals(MatchResult.WIN)) ret += 40;
                if(ele.getMathResult().equals(MatchResult.DRAW)) ret += 20;
                ele.setGrade((int)(ret / 10));

            }
        });
    }

    private void mappedGoalKeeper(List<PlayerLeagueRecord> plr){


        plr.stream().filter(ele->ele.getGrade() == 0).forEach(ele-> {
            Position position = ele.getPosition();
            if(position.equals(Position.GK)) {
                int superSave = ele.getGoodDefense();
                int ret = 0;
                if(ele.getMathResult().equals(MatchResult.WIN)) ret = 40;
                if(ele.getMathResult().equals(MatchResult.DRAW)) ret = 20;

                if(superSave == 1)ret += 30;
                else if(superSave == 2)ret += 35;
                else if(superSave == 3)ret += 40;
                else if(superSave == 4) ret += 45;
                else if(superSave == 5) ret += 50;
                else if(superSave > 4) ret += 60;

                ele.setGrade(ret);
            }
        });

    }

    private void mappedMid(double passAvg,double shootingAvg,double defenseAvg,List<PlayerLeagueRecord> plr){


        plr.stream().filter(ele->ele.getGrade() == 0).forEach(ele-> {
            Position position = ele.getPosition();
            if(position.equals(Position.AM )|| position.equals(Position.LM) ||
                    position.equals(Position.RM) || position.equals(Position.CM) || position.equals(Position.DM)
            ) {

                int goal = ele.getGoal();
                int assist = ele.getAssist();
                int shooting = ele.getShooting();
                int validShooting = ele.getValidShooting();
                int pass = ele.getPass();
                int defense = ele.getGoodDefense();
                int foul = ele.getFoul();

                double ret = 0;

                int attackPoint = goal + assist ;
                if(attackPoint == 1) ret = 200;
                else if(attackPoint == 2) ret = 250;
                else if(attackPoint == 3) ret = 270;
                else if(attackPoint == 4) ret = 300;
                else if(attackPoint == 5) ret = 350;
                else if(attackPoint > 5) ret = 400;


                double passScore = (((pass / passAvg))  * 200)/2;
                ret += passScore >= 200 ? 200 : passScore;
                double shootingScore = (((shooting / shootingAvg)) * 150) / 2;
                ret += shootingScore > 100 ? 100:shootingScore;
                double defenseScore = (((defense / defenseAvg)) * 200) / 2;
                ret += defenseScore >= 150 ? 150 : defenseScore;

                if(validShooting == 1) ret += 20;
                else if(validShooting == 2) ret += 30;
                else if(validShooting == 3) ret += 40;
                else if(validShooting == 4) ret += 60;
                else if(validShooting == 5) ret += 80;
                else if(validShooting > 5) ret += 100;

                if(foul <= 1) ret -= 0;
                else if(foul <= 3) ret -= 10;
                else if(foul <= 5) ret -= 20;
                else if(foul <= 7) ret -= 30;
                else if(foul <= 9) ret -= 40;
                else ret -= 50;

                if(ele.getMathResult().equals(MatchResult.WIN)) ret += 40;
                if(ele.getMathResult().equals(MatchResult.DRAW)) ret += 20;
                ele.setGrade((int)(ret / 10));
            }
        });

    }
}
