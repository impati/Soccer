package com.example.soccerleague.support.testData.game;


import com.example.soccerleague.RegisterService.LeagueRound.Duo.DuoRecordDto;
import com.example.soccerleague.RegisterService.LeagueRound.Duo.DuoRecordRegister;
import com.example.soccerleague.RegisterService.LeagueRound.Game.LeagueRoundGameDto;
import com.example.soccerleague.RegisterService.LeagueRound.Game.LeagueRoundGameRegister;
import com.example.soccerleague.SearchService.LeagueRound.Game.LeagueRoundGameResponse;
import com.example.soccerleague.domain.Player.Player;
import com.example.soccerleague.domain.Player.Position;
import com.example.soccerleague.domain.Player.Stat;
import com.example.soccerleague.domain.Round.Round;
import com.example.soccerleague.domain.record.MatchResult;
import com.example.soccerleague.domain.record.PlayerLeagueRecord;
import com.example.soccerleague.springDataJpa.PlayerLeagueRecordRepository;
import com.example.soccerleague.springDataJpa.RoundRepository;
import com.example.soccerleague.support.testData.game.Dto.*;
import com.example.soccerleague.support.testData.game.Repository.GameAvgDto;
import com.example.soccerleague.support.testData.game.Repository.GameResultRepository;
import com.example.soccerleague.support.testData.game.feature.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Slf4j
@RequiredArgsConstructor
@Transactional
public class AdvanceStatBaseGameDataPosing implements GameDataPosting{
    private final DuoRecordRegister duoRecordRegister;
    private final LeagueRoundGameRegister leagueRoundGameRegister;
    private final PlayerLeagueRecordRepository playerLeagueRecordEntityRepository;
    private final RoundRepository roundEntityRepository;
    private final DefenseReturn defenseReturn;
    private final PassReturn passReturn;
    private final CornerKickChance cornerKickChance;
    private final FoulAndDefenseReturn foulAndDefenseReturn;
    private final FreeKickChance freeKickChance;
    private final ShootGoal shootGoal;
    private final GameResultRepository gameResultRepository;
    private final GradeDecision gradeDecision;
    @Override
    public void calculation(Long roundId, LeagueRoundGameResponse resp) {
        // 공유 변수

         Map<Long , StatBaseGameDto> mappedPlayerA = new ConcurrentHashMap<>();
         Map<Long , StatBaseGameDto> mappedPlayerB = new ConcurrentHashMap<>();

        int defenseAvgA = 0;
        int defenseAvgB = 0;

        int goalKeeperAbilityA = 0;
        int goalKeeperAbilityB = 0;


        GameAvgDto gameAvgDto = gameResultRepository.findByAvgProjection();


        if(gameAvgDto.getCount() < 30 * 22){
            gameAvgDto.setShooting(2);
            gameAvgDto.setGoodDefense(9);
            gameAvgDto.setPass(25);
        }


        Round round = (Round)roundEntityRepository.findById(roundId).orElse(null);


        // 라인업
        List<PlayerLeagueRecord> plrA = playerLeagueRecordEntityRepository
                .findByRoundAndTeam(roundId,round.getHomeTeamId());
        List<PlayerLeagueRecord> plrB = playerLeagueRecordEntityRepository
                .findByRoundAndTeam(roundId,round.getAwayTeamId());

        // 스탯 정보
        List<Player> playerA = new ArrayList<>();
        plrA.stream().forEach(ele->playerA.add(ele.getPlayer()));
        List<Player> playerB = new ArrayList<>();
        plrB.stream().forEach(ele->playerB.add(ele.getPlayer()));


        // statBaseGameDto 세팅


        mapped(plrA,playerA,mappedPlayerA);
        mapped(plrB,playerB,mappedPlayerB);



        // defenseAvg


        defenseAvgA += defenseCal(mappedPlayerA);
        defenseAvgB += defenseCal(mappedPlayerB);

        for(var s : mappedPlayerA.keySet()){
            mappedPlayerA.get(s).setOppDefense(defenseAvgB);
        }

        for(var s : mappedPlayerB.keySet()){
            mappedPlayerB.get(s).setOppDefense(defenseAvgA);
        }

        // goalKeeperAbility


        goalKeeperAbilityA = goalKeeperCal(mappedPlayerA);
        goalKeeperAbilityB = goalKeeperCal(mappedPlayerB);

        for(var s : mappedPlayerA.keySet()){
            mappedPlayerA.get(s).setOppGoalKeeperAbility(goalKeeperAbilityB);
        }
        for(var s : mappedPlayerB.keySet()){
            mappedPlayerB.get(s).setOppGoalKeeperAbility(goalKeeperAbilityA);
        }


        // pass
        int i = passCal(mappedPlayerA);
        int j = passCal(mappedPlayerB);


        //  점유율

        i = shareCal(mappedPlayerA);
        j = shareCal(mappedPlayerB);
        double share = shareResult(i,j);
        resp.getSharePair().add((int)share);
        resp.getSharePair().add(100 - (int)share);


        // 코너킥

        resp.getCornerKickPair().add(CornerKick(mappedPlayerA));
        resp.getCornerKickPair().add(CornerKick(mappedPlayerB));


        // 파울 and 차단


        int savageFoulA = 0;
        int savageFoulB = 0;

        for(var s : mappedPlayerA.keySet()){
            savageFoulA += foulAndDefenseReturn.foul(mappedPlayerA.get(s));
        }


        for(var s : mappedPlayerB.keySet()){
            savageFoulB += foulAndDefenseReturn.foul(mappedPlayerB.get(s));
        }


        // 프리킥

        resp.getFreeKickPair().add(freeKick(savageFoulB,mappedPlayerA));
        resp.getFreeKickPair().add(freeKick(savageFoulA,mappedPlayerB));


        // 중거리 , 숫

        for(var s : mappedPlayerA.keySet()){
            shootGoal.shootAndGoal(mappedPlayerA, mappedPlayerA.get(s));
        }
        for(var s : mappedPlayerB.keySet()){
            shootGoal.shootAndGoal(mappedPlayerB, mappedPlayerB.get(s));
        }



        // 수퍼 세이브
        int superSaveA = 0,superSaveB =0;
        for(var s: mappedPlayerA.keySet()){
            superSaveB += mappedPlayerA.get(s).getOppSuperSave();
        }
        for(var s: mappedPlayerB.keySet()){
            superSaveA += mappedPlayerB.get(s).getOppSuperSave();
        }

        DuoRecordDto duoRecordDto = DuoRecordDto.create(roundId);

        Map<Long,Integer> as = new ConcurrentHashMap<>();
        playerA.stream().forEach(ele->as.put(ele.getId(),0));
        playerB.stream().forEach(ele->as.put(ele.getId(),0));
        as.put(0L,0);

        for(int k = 0;k<playerA.size();k++){
            StatBaseGameDto element = mappedPlayerA.get(playerA.get(k).getId());
            resp.getPassList().add(element.getPass());
            resp.getShootingList().add(element.getShooting());
            resp.getValidShootingList().add(element.getValidShooting());
            resp.getFoulList().add(element.getFoul());
            resp.getGoalList().add(element.getDuoResult().size());
            if(k == 10) resp.getGoodDefenseList().add(superSaveA);
            else resp.getGoodDefenseList().add(element.getGoodDefense());
            element.getDuoResult().stream().forEach(ele->{
                duoRecordDto.getScorer().add(ele.getGoal());
                duoRecordDto.getAssistant().add(ele.getAssist());
                duoRecordDto.getGoalType().add(ele.getGoalType());


                as.put(ele.getAssist(),as.get(ele.getAssist()) + 1);


            });
        }
        resp.getScorePair().add(duoRecordDto.getScorer().size());
        for(int k = 0;k<playerB.size();k++){
            StatBaseGameDto element = mappedPlayerB.get(playerB.get(k).getId());
            resp.getPassList().add(element.getPass());
            resp.getShootingList().add(element.getShooting());
            resp.getValidShootingList().add(element.getValidShooting());
            resp.getFoulList().add(element.getFoul());
            resp.getGoalList().add(element.getDuoResult().size());
            if(k == 10) resp.getGoodDefenseList().add(superSaveB);
            else resp.getGoodDefenseList().add(element.getGoodDefense());
            element.getDuoResult().stream().forEach(ele->{
                duoRecordDto.getScorer().add(ele.getGoal());
                duoRecordDto.getAssistant().add(ele.getAssist());
                duoRecordDto.getGoalType().add(ele.getGoalType());

                as.put(ele.getAssist(),as.get(ele.getAssist()) + 1);

            });
        }
        resp.getScorePair().add(duoRecordDto.getScorer().size() - resp.getScorePair().get(0));



        for(int k = 0;k<playerA.size();k++){
            StatBaseGameDto element = mappedPlayerA.get(playerA.get(k).getId());
            element.setAssist(as.get(playerA.get(k).getId()));


            if(resp.getScorePair().get(0) > resp.getScorePair().get(1))
                element.setMatchResult(MatchResult.WIN);
            else if(resp.getScorePair().get(0) < resp.getScorePair().get(1))
                element.setMatchResult(MatchResult.LOSE);
            else
                element.setMatchResult(MatchResult.DRAW);

            resp.getGradeList().add(gradeDecision.grade(gameAvgDto,element,superSaveA));

            resp.getAssistList().add(as.get(playerA.get(k).getId()));

        }
        for(int k = 0;k<playerB.size();k++){
            StatBaseGameDto element = mappedPlayerB.get(playerB.get(k).getId());
            element.setAssist(as.get(playerB.get(k).getId()));


            if(resp.getScorePair().get(0) > resp.getScorePair().get(1))
                element.setMatchResult(MatchResult.LOSE);
            else if(resp.getScorePair().get(0) < resp.getScorePair().get(1))
                element.setMatchResult(MatchResult.WIN);
            else
                element.setMatchResult(MatchResult.DRAW);

            resp.getGradeList().add(gradeDecision.grade(gameAvgDto,element,superSaveB));
            resp.getAssistList().add(as.get(playerB.get(k).getId()));
        }











        LeagueRoundGameDto leagueRoundGameDto = LeagueRoundGameDto.of(resp);
        leagueRoundGameDto.setRoundId(roundId);


        leagueRoundGameRegister.register(leagueRoundGameDto);
        duoRecordRegister.register(duoRecordDto);



    }


    private int freeKick(int savageFoul,Map<Long ,StatBaseGameDto> mappedPlayer){
         int ret =0;
        StatBaseGameDto freeKicker = freeKickChance.freeKicker(mappedPlayer);
        while(savageFoul!=0){
            int rx = (int)(Math.random()* 100);
            if(rx % 2 == 0){
                ret +=1;
                freeKickChance.freeKick(freeKicker);
            }
            savageFoul = 0;
        }
        return ret;
    }

    private int CornerKick(Map<Long ,StatBaseGameDto> mappedPlayer){
        int cornerKick = (int)(Math.random() * 4);
        int ret = cornerKick;
        Long k = cornerKickChance.cornerKicker(mappedPlayer);
        int cross = mappedPlayer.get(k).getStat().getCrosses();
        while(cornerKick != 0){
            for(var s : mappedPlayer.keySet()){
                StatBaseGameDto element = mappedPlayer.get(s);
                if(cornerKickChance.cornerKick(k,cross,element)) break;
            }

            cornerKick-=1;
        }
        return ret;
    }

    private int shareCal(Map<Long ,StatBaseGameDto> mappedPlayer){
        int ret =0 ;
        for(var s : mappedPlayer.keySet()){
            Stat stat = mappedPlayer.get(s).getStat();
            double percent = mappedPlayer.get(s).getPercent();
            ret +=  stat.getPhysicalFight() * percent;
            ret +=  stat.getStamina() * percent;
            ret += stat.getActiveness();
        }
        return ret;
    }
    private int passCal( Map<Long ,StatBaseGameDto> mappedPlayer){
        int passSum = 0;
        for(var s : mappedPlayer.keySet()){
            StatBaseGameDto element = mappedPlayer.get(s);

            if(element.getPosition().equals(Position.GK))continue;

            int pass = passReturn.pass(element);
            element.setPass(pass);
            passSum += pass;
        }
        for(var s : mappedPlayer.keySet()){
            StatBaseGameDto element = mappedPlayer.get(s);
            element.setPassSum(passSum);
        }
        return passSum;
    }
    private int defenseCal( Map<Long ,StatBaseGameDto> mappedPlayer){
        int defense = 0;
        for(var s : mappedPlayer.keySet()){
            defense += defenseReturn.defenseValue(mappedPlayer.get(s));
        }
        defense /=  mappedPlayer.size();

        return defense;
    }
    private int goalKeeperCal( Map<Long ,StatBaseGameDto> mappedPlayer){
        int ret = 0;
        for(var s : mappedPlayer.keySet()){
            StatBaseGameDto element = mappedPlayer.get(s);
            if(element.getPosition().equals(Position.GK)) {
                Stat stat = element.getStat();
                double percent = element.getPercent();
                ret += stat.getDiving() * percent;
                ret += stat.getHandling() *percent;
                ret += stat.getGoalKick() * percent;
                ret += stat.getSpeedReaction() *2 *percent;
            }
        }
        return ret / 5;
    }

    private void mapped (List<PlayerLeagueRecord> plr , List<Player> players , Map<Long ,StatBaseGameDto> mappedPlayer){
        for(int i =0 ; i <players.size();i++){
            Player player =  players.get(i);
            Position position  = plr.get(i).getPosition();
            StatBaseGameDto element  = null;
            if(position.equals(Position.GK)){
                element = new GoalKeeperStatBaseGameDto(player.getId(),
                        player.getTeam().getId(),conditionCalc(),player.getStat(),position
                );
            }
            else if(position.equals(Position.CB) || position.equals(Position.RB) ||
                    position.equals(Position.LB) || position.equals(Position.LWB) || position.equals(Position.RWB)
            ){
                element = new DefenserStatBaseGameDto(player.getId(),
                        player.getTeam().getId(),conditionCalc(),player.getStat(),position);
            }
            else if(position.equals(Position.ST )|| position.equals(Position.LF) ||
                    position.equals(Position.RF) || position.equals(Position.CF)
            ) {
                element = new StrikerStatBaseGameDto(player.getId(),
                        player.getTeam().getId(),conditionCalc(),player.getStat(),position);

            }
            else{
                element = new MidFielderStatBaseGameDto(player.getId(),
                        player.getTeam().getId(),conditionCalc(),player.getStat(),position);
            }

            mappedPlayer.put(player.getId(),element);
        }


    }
    private double conditionCalc() {
        int condition = (int) (Math.random() * 101);
        double percent = 0;
        if (condition % 2 == 0) percent = 1.0;
        else {
            if (condition < 15) percent = 0.5;
            else if (condition < 25) percent = 0.6;
            else if (condition < 35) percent = 0.7;
            else if (condition < 45) percent = 0.8;
            else if (condition < 55) percent = 0.9;
            else if (condition < 65) percent = 1.1;
            else if (condition < 75) percent = 1.2;
            else if (condition < 85) percent = 1.3;
            else if (condition < 95) percent = 1.4;
            else percent = 1.5;
        }
        return percent;
    }
    private double shareResult(int a,int b){
        double ret = ((a * 100)/(a + b));
        return ret;
    }
}
