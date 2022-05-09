package com.example.soccerleague.RegisterService;

import com.example.soccerleague.EntityRepository.PlayerEntityRepository;
import com.example.soccerleague.EntityRepository.PlayerLeagueRecordEntityRepository;
import com.example.soccerleague.domain.Player.Player;
import com.example.soccerleague.domain.Player.Position;
import com.example.soccerleague.domain.record.MatchResult;
import com.example.soccerleague.domain.record.PlayerLeagueRecord;
import com.example.soccerleague.support.testData.game.Dto.DefenserStatBaseGameDto;
import com.example.soccerleague.support.testData.game.Dto.GoalKeeperStatBaseGameDto;
import com.example.soccerleague.support.testData.game.Dto.MidFielderStatBaseGameDto;
import com.example.soccerleague.support.testData.game.Dto.StrikerStatBaseGameDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class DefaultEloRatingSystem implements EloRatingSystem {
    private final PlayerEntityRepository playerEntityRepository;
    private final PlayerLeagueRecordEntityRepository playerLeagueRecordEntityRepository;
    private static Integer VI = 400;
    private static Integer K = 20;

    @Override
    public void ratingCalc(List<PlayerLeagueRecord> plrA, List<PlayerLeagueRecord> plrB) {

        double avgGrade = playerLeagueRecordEntityRepository.avgGrade();
        if(avgGrade == 0) avgGrade = 25;
        calc(mappedDefenser(plrA),mappedDefenser(plrB),avgGrade);
        calc(mappedMid(plrA),mappedMid(plrB),avgGrade);
        calc(mappedStriker(plrA),mappedStriker(plrB),avgGrade);
        calc(mappedGoalKeeper(plrA),mappedGoalKeeper(plrB),avgGrade);

    }

    private void calc(List<PlayerLeagueRecord> plrA, List<PlayerLeagueRecord>plrB,double avgGrade){

        List<Player> playerA =  new ArrayList<>();
        List<Integer> gradeA = plrA.stream().map(ele->ele.getGrade()).collect(Collectors.toList());

        plrA.stream().forEach(ele->{
            playerA.add(ele.getPlayer());
        });
        playerA.stream().filter(ele->ele.getRating() == 0).forEach(ele->ele.setRating(1500));
        Double avgRaingA = playerA.stream().mapToDouble(ele->ele.getRating()).average().orElse(0);


        List<Player> playerB =  new ArrayList<>();
        List<Integer> gradeB = plrB.stream().map(ele->ele.getGrade()).collect(Collectors.toList());

        plrB.stream().forEach(ele->{
            playerB.add(ele.getPlayer());
        });

        playerB.stream().filter(ele->ele.getRating() == 0).forEach(ele->ele.setRating(1500));
        Double avgRaingB = playerB.stream().mapToDouble(ele->ele.getRating()).average().orElse(0);


        for(int i =0;i<plrA.size();i++){
            double myRating = playerA.get(i).getRating();
            int myGrade = plrA.get(i).getGrade();
            double w = 0;
            double a = 10;
            double b = (avgRaingB - myRating) / VI;
            double we = 1 / (Math.pow(a,b) + 1);
            MatchResult matchResult = plrA.get(i).getMathResult();

            if(matchResult.equals(MatchResult.WIN)){
                w = 1;
            }
            else if(matchResult.equals(MatchResult.DRAW)){
                w = 0.5;
            }
            double r = myRating + K * (w - we);
            r += 3*((myGrade / avgGrade) - 1);

            playerA.get(i).setRating(r);
        }



        for(int i =0;i<plrB.size();i++){
            double myRating = playerB.get(i).getRating();
            int myGrade = plrB.get(i).getGrade();
            double w = 0;
            double a = 10;
            double b = (avgRaingA - myRating) / VI;
            double we = 1 / (Math.pow(a,b) + 1);
            MatchResult matchResult = plrB.get(i).getMathResult();

            if(matchResult.equals(MatchResult.WIN)){
                w = 1;
            }
            else if(matchResult.equals(MatchResult.DRAW)){
                w = 0.5;
            }
            double r = myRating + K * (w - we);
            r += 3*((myGrade / avgGrade) - 1);
            playerB.get(i).setRating(r);
        }







    }
    private List<PlayerLeagueRecord> mappedDefenser(List<PlayerLeagueRecord> plr){

        List<PlayerLeagueRecord> ret = new ArrayList<>();
        plr.stream().forEach(ele-> {

            Position position = ele.getPosition();
            if (position.equals(Position.CB) || position.equals(Position.RB) ||
                    position.equals(Position.LB) || position.equals(Position.LWB) || position.equals(Position.RWB)
            ) {
                ret.add(ele);
            }

        });
        return ret;
    }

    private List<PlayerLeagueRecord> mappedStriker(List<PlayerLeagueRecord> plr){

        List<PlayerLeagueRecord> ret = new ArrayList<>();
        plr.stream().forEach(ele-> {
            Position position = ele.getPosition();
            if(position.equals(Position.ST )|| position.equals(Position.LF) ||
                    position.equals(Position.RF) || position.equals(Position.CF)
            ) {
                ret.add(ele);

            }
        });
        return ret;
    }

    private List<PlayerLeagueRecord> mappedGoalKeeper(List<PlayerLeagueRecord> plr){

        List<PlayerLeagueRecord> ret = new ArrayList<>();
        plr.stream().forEach(ele-> {
            Position position = ele.getPosition();
            if(position.equals(Position.GK)) {
                ret.add(ele);

            }
        });
        return ret;
    }

    private List<PlayerLeagueRecord> mappedMid(List<PlayerLeagueRecord> plr){

        List<PlayerLeagueRecord> ret = new ArrayList<>();
        plr.stream().forEach(ele-> {
            Position position = ele.getPosition();
            if(position.equals(Position.AM )|| position.equals(Position.LM) ||
                    position.equals(Position.RM) || position.equals(Position.CM) || position.equals(Position.DM)
            ) {
                ret.add(ele);

            }
        });
        return ret;
    }


}
