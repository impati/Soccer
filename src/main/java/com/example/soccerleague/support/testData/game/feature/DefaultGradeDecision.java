package com.example.soccerleague.support.testData.game.feature;

import com.example.soccerleague.domain.record.MatchResult;
import com.example.soccerleague.support.testData.game.Dto.DefenserStatBaseGameDto;
import com.example.soccerleague.support.testData.game.Dto.MidFielderStatBaseGameDto;
import com.example.soccerleague.support.testData.game.Dto.StatBaseGameDto;
import com.example.soccerleague.support.testData.game.Dto.StrikerStatBaseGameDto;
import com.example.soccerleague.support.testData.game.Repository.GameAvgDto;
import com.example.soccerleague.support.testData.game.Repository.GameResultRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class DefaultGradeDecision implements  GradeDecision{

    @Override
    public int grade(GameAvgDto avgDto , StatBaseGameDto req ,int superSave) {

        double passAvg = avgDto.getPass() + 1;
        double shootingAvg = avgDto.getShooting() + 1;
        double defenseAvg = avgDto.getGoodDefense() + 1;

        if(req instanceof StrikerStatBaseGameDto)return striker(passAvg,shootingAvg,defenseAvg,req);
        else if(req instanceof MidFielderStatBaseGameDto)return mid(passAvg,shootingAvg,defenseAvg,req);
        else if(req instanceof DefenserStatBaseGameDto)return defense(passAvg,shootingAvg,defenseAvg,req);
        else return goalKeeper(superSave,req);

    }


    private int striker(double passAvg , double shootingAvg , double defenseAvg ,StatBaseGameDto req){
        int goal = req.getDuoResult().size();
        int assist = req.getAssist();
        int shooting = req.getShooting();
        int validShooting = req.getValidShooting();
        int pass = req.getPass();
        int defense = req.getGoodDefense();
        int foul = req.getFoul();

        double ret = 0;

        if(goal == 1)ret = 100;
        else if(goal == 2) ret = 140;
        else if(goal == 3) ret = 170;
        else if(goal > 3) ret = 200;

        if(assist == 1) ret += 40;
        else if(assist == 2) ret += 80;
        else if(assist == 3) ret += 120;
        else if(assist == 4) ret += 150;
        else if(assist > 4) ret += 200;



        double passScore = (((pass / passAvg))  * 100)/2;
        ret += passScore >= 100 ? 100 : passScore;
        double shootingScore = (((shooting / shootingAvg)) * 150)/2;
        ret += shootingScore > 150 ? 150:shootingScore;
        double defenseScore = (((defense / defenseAvg)) * 50)/2;
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


        if(req.getMatchResult().equals(MatchResult.WIN)) ret += 40;
        if(req.getMatchResult().equals(MatchResult.DRAW)) ret += 20;

        return (int) (ret / 10);
    }
    private int mid(double passAvg , double shootingAvg , double defenseAvg ,StatBaseGameDto req){
        int goal = req.getDuoResult().size();
        int assist = req.getAssist();
        int shooting = req.getShooting();
        int validShooting = req.getValidShooting();
        int pass = req.getPass();
        int defense = req.getGoodDefense();
        int foul = req.getFoul();

        double ret = 0;

        if(goal == 1)ret = 100;
        else if(goal == 2) ret = 140;
        else if(goal == 3) ret = 170;
        else if(goal > 3) ret = 200;

        if(assist == 1) ret += 40;
        else if(assist == 2) ret += 80;
        else if(assist == 3) ret += 120;
        else if(assist == 4) ret += 150;
        else if(assist > 4) ret += 200;


        double passScore = (((pass / passAvg))  * 200)/2;
        ret += passScore >= 200 ? 200 : passScore;
        double shootingScore = (((shooting / shootingAvg)) * 100) / 2;
        ret += shootingScore > 100 ? 100:shootingScore;
        double defenseScore = (((defense / defenseAvg)) * 150) / 2;
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

        if(req.getMatchResult().equals(MatchResult.WIN)) ret += 40;
        if(req.getMatchResult().equals(MatchResult.DRAW)) ret += 20;

        return (int) (ret / 10);
    }
    private int defense(double passAvg , double shootingAvg , double defenseAvg ,StatBaseGameDto req){
        int goal = req.getDuoResult().size();
        int assist = req.getAssist();
        int shooting = req.getShooting();
        int validShooting = req.getValidShooting();
        int pass = req.getPass();
        int defense = req.getGoodDefense();
        int foul = req.getFoul();

        double ret = 0;

        if(goal == 1)ret = 40;
        else if(goal == 2) ret = 50;
        else if(goal == 3) ret = 70;
        else if(goal > 3) ret = 100;

        if(assist == 1) ret += 40;
        else if(assist == 2) ret += 50;
        else if(assist == 3) ret += 60;
        else if(assist == 4) ret += 80;
        else if(assist > 4) ret += 100;

        double passScore = (((pass / passAvg)) * 200)/2;
        ret += passScore >= 200 ? 200 : passScore;
        double shootingScore = (((shooting / shootingAvg)) * 25) / 2;
        ret += shootingScore > 25 ? 25:shootingScore;
        double defenseScore = (((defense / defenseAvg)) * 400) /2;
        ret += defenseScore >= 400 ? 400 : defenseScore;

        if(validShooting == 1) ret += 3;
        else if(validShooting == 2) ret += 6;
        else if(validShooting == 3) ret += 9;
        else if(validShooting == 4) ret += 15;
        else if(validShooting == 5) ret += 20;
        else if(validShooting > 5) ret += 25;

        if(foul <= 1) ret -= 0;
        else if(foul <= 3) ret -= 10;
        else if(foul <= 5) ret -= 20;
        else if(foul <= 7) ret -= 30;
        else if(foul <= 9) ret -= 40;
        else ret -= 50;

        if(req.getMatchResult().equals(MatchResult.WIN)) ret += 40;
        if(req.getMatchResult().equals(MatchResult.DRAW)) ret += 20;

        return (int) (ret / 10);

    }
    private int goalKeeper(int superSave,StatBaseGameDto req) {
        int ret = 0;
        if(req.getMatchResult().equals(MatchResult.WIN)) ret = 40;
        if(req.getMatchResult().equals(MatchResult.DRAW)) ret = 20;

        if(superSave == 1)ret += 30;
        else if(superSave == 2)ret += 35;
        else if(superSave == 3)ret += 40;
        else if(superSave == 4) ret += 45;
        else if(superSave == 5) ret += 50;
        else if(superSave > 4) ret += 60;

        return ret;
    }

}

