package com.example.soccerleague.support.testData.game;

import com.example.soccerleague.EntityRepository.PlayerEntityRepository;
import com.example.soccerleague.EntityRepository.PlayerLeagueRecordEntityRepository;
import com.example.soccerleague.EntityRepository.RoundEntityRepository;
import com.example.soccerleague.RegisterService.LeagueRound.Duo.DuoRecordRegister;
import com.example.soccerleague.RegisterService.LeagueRound.Game.LeagueRoundGameRegister;
import com.example.soccerleague.SearchService.LeagueRound.Game.LeagueRoundGameResponse;
import com.example.soccerleague.domain.Player.Player;
import com.example.soccerleague.domain.Player.Position;
import com.example.soccerleague.domain.Player.Stat;
import com.example.soccerleague.domain.Round.Round;
import com.example.soccerleague.domain.record.GoalType;
import com.example.soccerleague.domain.record.PlayerLeagueRecord;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

// condition 0 ~ 100
@RequiredArgsConstructor
public class StatBaseGameDataPosting implements GameDataPosting{
    private final DuoRecordRegister duoRecordRegister;
    private final LeagueRoundGameRegister leagueRoundGameRegister;
    private final PlayerEntityRepository playerEntityRepository;
    private final PlayerLeagueRecordEntityRepository playerLeagueRecordEntityRepository;
    private final RoundEntityRepository roundEntityRepository;
    private int defenseAvgA;
    private int defenseAvgB;
    private Map<Long,Integer> passA = new ConcurrentHashMap<>();
    private Map<Long,Integer> passB = new ConcurrentHashMap<>();
    private List<DuoInfo> goalAssist = new ArrayList<>();
    @Override
    public void calculation(Long roundId, LeagueRoundGameResponse resp) {
        Round round = (Round)roundEntityRepository.findById(roundId).orElse(null);
        // **********************컨디션****************************
        double percent = conditionCalc();
        // *******************************************************



        // ********************* PlayerLeagueRecord *********************
        List<PlayerLeagueRecord> plrA = playerLeagueRecordEntityRepository
                .findByRoundAndTeam(roundId,round.getHomeTeamId());
        List<PlayerLeagueRecord> plrB = playerLeagueRecordEntityRepository
                .findByRoundAndTeam(roundId,round.getAwayTeamId());

        List<Player> playerA = playerEntityRepository.findByTeamId(round.getHomeTeamId());
        List<Player> playerB = playerEntityRepository.findByTeamId(round.getAwayTeamId());
        // *********************++++++++++++*****************************



        // *********************defenseAvg *********************
        defenseAvgA = defenseAvg(playerA,percent);
        defenseAvgB = defenseAvg(playerB,percent);
        // *********************++++++++++++********************


        // *********************pass*********************

        for(var ele : plrA){
            Player player = (Player)playerEntityRepository.findById(ele.getId()).orElse(null);
            int realPass = passStat(ele.getPosition(),player.getStat().getPass(),defenseAvgB,percent);
            passA.put(player.getId(),realPass);
            resp.getPassList().add(realPass);
        }

        for(var ele : plrB){
            Player player = (Player)playerEntityRepository.findById(ele.getId()).orElse(null);
            int realPass = passStat(ele.getPosition(),player.getStat().getPass(),defenseAvgA,percent);
            passB.put(player.getId(),realPass);
            resp.getPassList().add(realPass);
        }


        // **********************************************



        //*****************점유율************************
         double share = shareResult(shareCalculation(playerA,percent),shareCalculation(playerB,percent));
         resp.getSharePair().add((int)share);
         resp.getSharePair().add(100 - (int)share);
        //**********************************************

        //*****************코너킥***********************
        int cornerKick = (int)(Math.random() * 4);
        resp.getCornerKickPair().add(cornerKick);
        while(cornerKick != 0){
            cornerKickChance(plrA,percent);
            cornerKick-=1;
        }

        cornerKick = (int)(Math.random() * 4);
        resp.getCornerKickPair().add(cornerKick);
        while(cornerKick != 0){
            cornerKickChance(plrB,percent);
            cornerKick-=1;
        }
        //*********************************************


        //****************프리킥,파울,차단************************
         freeKickChance(playerA,playerB,resp,percent);
         freeKickChance(playerB,playerA,resp,percent);

        //*****************************************************




        //*********************중거리 슛********************




        //*************************************************





        //*********************노말 슛********************




        //*************************************************






    }
    private void freeKickChance(List<Player> playerA,List<Player> playerB ,LeagueRoundGameResponse resp,double percent){
        AtomicInteger foulSum = new AtomicInteger();
        playerA.stream()
                .forEach(ele->{
                    if(!ele.getPosition().equals(Position.GK)) {
                        Stat stat = ele.getStat();
                        int increase = stat.getDefense() + stat.getTackle() + stat.getIntercepting() + stat.getSlidingTackle();
                        int rn = (int) (Math.random() * increase);
                        int v;
                        if (rn <= 50) v = (int) (Math.random() * 25);
                        else if (rn <= 150) v = (int) (Math.random() * 15);
                        else if (rn <= 250) v = (int) (Math.random() * 10);
                        else v = (int) (Math.random() * 5);

                        int defense = (int) (Math.random() * rn) + increase / 20;

                        foulSum.addAndGet(v);
                        resp.getFoulList().add(v);
                        resp.getGoodDefenseList().add(defense);
                    }
                });
        int rn = (int)(Math.random()*foulSum.get());
        if(rn % 30 == 0){
            List<Long> kicker = new ArrayList<>();
            playerB.stream().forEach(ele->{
                if(ele.getPosition().equals(Position.ST) || ele.getPosition().equals(Position.CF)
                        || ele.getPosition().equals(Position.RF) || ele.getPosition().equals(Position.LF)){
                }
                kicker.add(ele.getId());
            });
            int rdn = (int)(Math.random()*kicker.size());

            Long kick = kicker.get(rdn);
            Player k = (Player)playerEntityRepository.findById(kick).get();
            int goalDeter = (int)(k.getStat().getGoalDetermination() * percent);
            int mid = (int)(k.getStat().getMidRangeShot() * percent);
            int power = (int)(k.getStat().getShootPower() * percent);

            int result = 2 * goalDeter + mid + power;

            rdn = (int)(Math.random()*result);
            if(rdn % 10 == 0 || rdn > 370){
                goalAssist.add(new DuoInfo(k.getId(),0L,GoalType.FREEKICK));
            }
            else if(rdn % 100 ==0){
                int tf = (int)(Math.random()*10);
                if(tf % 2 == 0 || tf > 5)
                goalAssist.add(new DuoInfo(k.getId(),0L,GoalType.PENALTYKICK));
            }
        }
    }



    private void cornerKickChance(List<PlayerLeagueRecord> players ,double percent){
        List<Long> kicker = new ArrayList<>();
        players.stream().forEach(ele->{
            if(ele.getPosition().equals(Position.LM)
                    || ele.getPosition().equals(Position.RM)
                    || ele.getPosition().equals(Position.LB)
                    || ele.getPosition().equals(Position.RB)
                    || ele.getPosition().equals(Position.RWB)
                    || ele.getPosition().equals(Position.LWB)
                    || ele.getPosition().equals(Position.AM)
            ) {
                kicker.add(ele.getPlayer().getId());
            }
        });
        if(kicker.size() == 0) kicker.add(players.get(0).getId());
        int rn = (int)(Math.random()*kicker.size());

        Long kick = kicker.get(rn);
        Player k = (Player)playerEntityRepository.findById(kick).get();
        int cross = k.getStat().getCrosses();
        players.stream()
                .map(ele->ele.getPlayer())
                .forEach(ele->{
                    Stat stat = ele.getStat();
                    if(!ele.getId().equals(kick)) {
                        int positioning = (int) (stat.getPositioning()*percent);
                        int visualRange = (int) (stat.getVisualRange()*percent);
                        int heading = (int)(int) (stat.getHeading()*percent);
                        int increase = positioning + visualRange + heading + cross;
                        int possible = (int) (Math.random() * increase);
                        if (possible <= 200 && possible % 100 == 0) {
                            goalAssist.add(new DuoInfo(ele.getId(),kick,GoalType.NOMAL));
                        }
                        else if(possible < 300 && possible % 70 == 0){
                                goalAssist.add(new DuoInfo(ele.getId(),kick,GoalType.NOMAL));
                        }
                        else{
                            if(possible % 50 == 0){
                                goalAssist.add(new DuoInfo(ele.getId(),kick,GoalType.NOMAL));
                            }
                        }

                    }
        });











    }



    private int shareCalculation(List<Player> plr,double percent){
        AtomicInteger ret = new AtomicInteger();

        plr.stream().forEach(ele->{
            ret.addAndGet(passA.get(ele.getId()));
            int stamina = ele.getStat().getStamina();
            int activeness = ele.getStat().getActiveness();
            int psy = ele.getStat().getPhysicalFight();

            int rn = (int)(Math.random()*(stamina/3)) + stamina / 3;
            ret.addAndGet(rn);

            rn = (int)(Math.random()*(activeness)/3) + activeness / 3;
            ret.addAndGet(rn);

            rn = (int)(Math.random()*(psy / 3)) + psy/3;
            ret.addAndGet(rn);


        });

        return ret.get();
    }

    private double shareResult(int a,int b){
        double ret = a / (a + b) * 100;
        return ret;
    }


    private int defenseAvg(List<Player> players,double percent){
        AtomicInteger ret = new AtomicInteger();
        players.stream()
                .map(ele->ele.getStat())
                .forEach(ele-> {
                    ret.addAndGet(ele.getDefense());
                    ret.addAndGet(ele.getTackle());
                    ret.addAndGet(ele.getIntercepting());
                    ret.addAndGet(ele.getSlidingTackle());
        });
        return ret.get() / (4 * players.size());
    }


    private int passStat(Position position ,int passStat, int defenseAvg, double percent){

        passStat =  (int)(passStat * percent);

        int s, i;
        if(position.equals(Position.AM) ||
                position.equals(Position.CM) ||
                position.equals(Position.RM) ||
                position.equals(Position.LM) ||
                position.equals(Position.DM)
          ){
            s = passStat / 3;
            i = (2 * passStat / 3);
        }
        else{
            s = passStat / 5;
            i = (3 * passStat / 5);
        }

        int start = (int)(Math.random() * s);
        int increase = (int)(Math.random() * s) + i;
        int pass = (int)(Math.random() * increase) + start;
        int realPass = 0;

        boolean possibleArr [] =  new boolean[100];
        defenseAvg = defenseAvg / 2;
        while(defenseAvg != 0) {
            int trueIdx = (int)(Math.random() * 100);
            possibleArr[trueIdx] = true; // 실패
            defenseAvg-=1;
        }
        while(pass != 0){
            int falseIdx = (int)(Math.random()*100);
            if(!possibleArr[falseIdx]) realPass += 1;
            pass -= 1;
        }


        return realPass;
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

    @Data
    @AllArgsConstructor
    static class DuoInfo{
        Long goal;
        Long assist;
        GoalType goalType;
    }

}
