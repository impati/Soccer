package com.example.soccerleague.support.testData.game;

import com.example.soccerleague.EntityRepository.PlayerEntityRepository;
import com.example.soccerleague.EntityRepository.PlayerLeagueRecordEntityRepository;
import com.example.soccerleague.EntityRepository.RoundEntityRepository;
import com.example.soccerleague.RegisterService.LeagueRound.Duo.DuoRecordDto;
import com.example.soccerleague.RegisterService.LeagueRound.Duo.DuoRecordRegister;
import com.example.soccerleague.RegisterService.LeagueRound.Game.LeagueRoundGameDto;
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
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

// condition 0 ~ 100
@Slf4j
@Component
@Transactional
@RequiredArgsConstructor
public class StatBaseGameDataPosting implements GameDataPosting{
    private final DuoRecordRegister duoRecordRegister;
    private final LeagueRoundGameRegister leagueRoundGameRegister;
    private final PlayerEntityRepository playerEntityRepository;
    private final PlayerLeagueRecordEntityRepository playerLeagueRecordEntityRepository;
    private final RoundEntityRepository roundEntityRepository;
    private List<DuoInfo> goalAssist = new ArrayList<>();
    @Override
    public void calculation(Long roundId, LeagueRoundGameResponse resp) {
         int defenseAvgA = 0;
         int defenseAvgB = 0;
         int passSumA = 0;
         int passSumB = 0;
         int goalKeeperAbilityA = 0;
         int goalKeeperAbilityB = 0;
         int superSaveA = 0;
         int superSaveB = 0;

        Map<Long,Integer> passA = new ConcurrentHashMap<>();
         Map<Long,Integer> passB = new ConcurrentHashMap<>();
        Round round = (Round)roundEntityRepository.findById(roundId).orElse(null);
        // **********************컨디션****************************
        double percentA = conditionCalc();
        double percentB = conditionCalc();
        // *******************************************************



        // ********************* PlayerLeagueRecord *********************
        List<PlayerLeagueRecord> plrA = playerLeagueRecordEntityRepository
                .findByRoundAndTeam(roundId,round.getHomeTeamId());
        List<PlayerLeagueRecord> plrB = playerLeagueRecordEntityRepository
                .findByRoundAndTeam(roundId,round.getAwayTeamId());

        List<Player> playerA = new ArrayList<>();
        plrA.stream().forEach(ele->playerA.add(ele.getPlayer()));
        List<Player> playerB = new ArrayList<>();
        plrB.stream().forEach(ele->playerB.add(ele.getPlayer()));
        // *********************++++++++++++*****************************

        // *********************defenseAvg *********************
        defenseAvgA = defenseAvg(playerA,percentA);
        defenseAvgB = defenseAvg(playerB,percentB);
        // *********************++++++++++++********************


        for(var ele : plrA){
            Player player = ele.getPlayer();
            int realPass = passStat(ele.getPosition(),player.getStat().getPass(),defenseAvgB,percentA);
            passA.put(player.getId(),realPass);
            passSumA += realPass;
            resp.getPassList().add(realPass);
        }


        for(var ele : plrB){
            Player player = ele.getPlayer();
            int realPass = passStat(ele.getPosition(),player.getStat().getPass(),defenseAvgA,percentB);
            passB.put(player.getId(),realPass);
            passSumB += realPass;
            resp.getPassList().add(realPass);
        }


        // **********************************************



        //*****************점유율************************
        int i = shareCalculation(playerA,passA,percentA);
        int j = shareCalculation(playerB,passB,percentB);
        double share = shareResult(i,j);
         resp.getSharePair().add((int)share);
         resp.getSharePair().add(100 - (int)share);
        //**********************************************




        //*****************코너킥***********************
        int cornerKick = (int)(Math.random() * 4);
        resp.getCornerKickPair().add(cornerKick);
        while(cornerKick != 0){
            cornerKickChance(plrA,percentA);
            cornerKick-=1;
        }

        cornerKick = (int)(Math.random() * 4);
        resp.getCornerKickPair().add(cornerKick);
        while(cornerKick != 0){
            cornerKickChance(plrB,percentB);
            cornerKick-=1;
        }
        //*********************************************

        //****************프리킥,파울,차단************************
         freeKickChance(playerA,playerB,resp,percentA);
         freeKickChance(playerB,playerA,resp,percentB);

        //*****************************************************
        log.info("*************************************************************************");

        //*********************중거리 슛,노말 슛********************
        for(var ele : playerA){
            superSaveB += Kick(playerA,ele,resp,passSumA,defenseAvgB,goalKeeperAbilityB,percentA);

        }
        for(var ele : playerB){
            superSaveA += Kick(playerB,ele,resp,passSumB,defenseAvgA,goalKeeperAbilityA,percentB);

        }
        //*************************************************


        log.info("*************************************************************************");
        //************************득점과 도움 *************************

        DuoRecordDto duoRecordDto = DuoRecordDto.create(roundId);
        int scoreA = 0, scoreB = 0;
        for(var ele : goalAssist){
            if(ele.getPlayer().getTeam().getId().equals(round.getHomeTeamId())){
                scoreA += 1;
            }
          else{
                scoreB +=1;
            }
            duoRecordDto.getScorer().add(ele.getGoal());
            duoRecordDto.getAssistant().add(ele.getAssist());
            duoRecordDto.getGoalType().add(ele.getGoalType());
        }


        resp.getScorePair().add(scoreA);
        resp.getScorePair().add(scoreB);


        playerA.stream().forEach(ele->{
            int ret1 = 0,ret2=0;
            for(var ga :goalAssist) {
                if(ga.getGoal().equals(ele.getId())) ret1++;
                if(ga.getAssist().equals(ele.getId()))ret2++;
            }
            resp.getGoalList().add(ret1);
            resp.getAssistList().add(ret2);
            resp.getGradeList().add(0);
        });

        playerB.stream().forEach(ele->{
            int ret1 = 0,ret2=0;
            for(var ga :goalAssist) {
                if(ga.getGoal().equals(ele.getId())) ret1++;
                if(ga.getAssist().equals(ele.getId()))ret2++;
            }
            resp.getGoalList().add(ret1);
            resp.getAssistList().add(ret2);
            resp.getGradeList().add(0);
        });


        log.info("***********************************end*************************************");

        int idx = 0;
        for(int o =0;o<playerA.size();o++){
            if(playerA.get(o).getPosition().equals(Position.GK)){
                idx = o;
            }
        }
        resp.getGoodDefenseList().set(idx,superSaveA);
        idx = playerA.size();
        for(int o = 0;o<playerB.size();o++){
            if(playerB.get(o).getPosition().equals(Position.GK)){
                idx += o;
            }
        }
        resp.getGoodDefenseList().set(idx,superSaveB);


        LeagueRoundGameDto leagueRoundGameDto = LeagueRoundGameDto.of(resp);
        leagueRoundGameDto.setRoundId(roundId);

        leagueRoundGameRegister.register(leagueRoundGameDto);
        duoRecordRegister.register(duoRecordDto);


        goalAssist.clear();

    }
    private boolean shooting(Player player,double percent){
        boolean flag = false;
        int ret = 0;
        Stat stat = player.getStat();
        ret += (int)(stat.getBallControl() * percent * 2);
        ret += (int)(stat.getBalance() *percent);
        ret += (int)(stat.getDribble() *percent *2);
        ret += (int)(stat.getAcceleration() * percent*2);
        ret += (int)(stat.getSpeed() *percent);
        ret += (int)(stat.getPhysicalFight() *percent);
        ret += (int)(stat.getPhysicalFight()*percent);
        int rn = (int)(Math.random()*(ret / 2)) + (ret/2);
        if(ret > 600 && ret % 2 == 0) flag = true;
        return flag;
    }
    private int Kick(List<Player>players ,Player player ,LeagueRoundGameResponse resp,int passSum,int defense,int goalKeeperAbility,double percent){
        int shooting = 0;
        int validShooting =0 ;
        int superSave = 0;
        if(!player.getPosition().equals(Position.GK)) {
            int mid = passSum / 100;
            int possibleCount = 1 + mid / 2;
            Stat stat = player.getStat();
            int increase = (int) (stat.getGoalDetermination() * percent)
                    + (int) (3 * stat.getMidRangeShot() * percent) + (int) (stat.getShootPower() * percent);

            int rn = (int) (Math.random() * (increase / 10));
            boolean arr[] = new boolean[500];
            while (rn != 0) {
                int n = (int) (Math.random() * 500);
                arr[n] = true;
                rn -= 1;
            }
            while (possibleCount != 0) {
                if(shooting(player,percent)) {
                    shooting += 1;
                    int v = (int) (Math.random() * 500);
                    if (arr[v]) {
                        validShooting += 1;
                        if (!goalKeeperDefense((int) (goalKeeperAbility * percent))) {
                            goalAssist.add(new DuoInfo(player, player.getId(), AssistMake(players, player.getId()), GoalType.LONGKICK));
                        }
                        else  superSave+=1;
                    }
                }
                possibleCount--;
            }


            mid = passSum / 10 - passSum / 100;
            possibleCount = (int) (Math.random() * (mid / 2));

            increase = (int) (stat.getAcceleration() * percent)
                    + (int) (stat.getSpeed() * percent) + (int) (stat.getPhysicalFight() * percent)
                    + (int) (stat.getGoalDetermination() * 2 * percent)
                    + (int) (stat.getShootPower() * 0.5 * percent)
                    + (int) (stat.getBallControl() * percent) - defense;

            rn = (int) (Math.random() * (increase / 10)) + (increase / 10);
            boolean ar[] = new boolean[1000];
            while (rn != 0) {
                int n = (int) (Math.random() * 1000);
                ar[n] = true;
                rn -= 1;
            }
            while (possibleCount != 0) {

                if(shooting(player,percent)) {
                    shooting += 1;
                    int v = (int) (Math.random() * 1000);
                    if (ar[v]) {
                        validShooting += 1;
                        if (!goalKeeperDefense((int) (goalKeeperAbility * percent))){
                            goalAssist.add(new DuoInfo(player, player.getId(), AssistMake(players, player.getId()), decisionGoalType(player, "normal")));
                        }
                        else  superSave+=1;

                    }
                }
                possibleCount--;
            }

        }
        resp.getShootingList().add(shooting);
        resp.getValidShootingList().add(validShooting);

        return superSave;
    }




    private boolean goalKeeperDefense(int goalKeeperAbility){
        int rn = (int)(Math.random()*goalKeeperAbility);
        boolean arr[] = new boolean[100];
        while(rn !=0){
            arr[(int)(Math.random()*100)] = true;
            rn-=1;
        }
        if(arr[(int)(Math.random()*100)]){

            return true;
        }
        else return false;
    }

    private Long AssistMake(List<Player> players,Long goal){
        long arr[] = new long[100];
        AtomicInteger sz = new AtomicInteger();
        players.stream().forEach(ele->{
            if(!ele.getId().equals(goal)){
                if(ele.getPosition().equals(Position.ST) ||
                    ele.getPosition().equals(Position.CF) ||
                        ele.getPosition().equals(Position.RF) ||
                        ele.getPosition().equals(Position.LF) ||
                        ele.getPosition().equals(Position.RM) ||
                        ele.getPosition().equals(Position.AM) ||
                        ele.getPosition().equals(Position.CM) ||
                        ele.getPosition().equals(Position.RM) ||
                        ele.getPosition().equals(Position.LM) ||
                        ele.getPosition().equals(Position.DM)
                ){
                    for(int i = 0;i<10;i++){
                        arr[sz.incrementAndGet()] = ele.getId();
                    }

                }
                else{
                    for(int i = 0;i<3;i++){
                        arr[sz.incrementAndGet()] = ele.getId();
                    }
                }
            }

        });

        int rn = (int)(Math.random()*sz.get());
        return arr[rn];
    }

    private void freeKickChance(List<Player> playerA,List<Player> playerB ,LeagueRoundGameResponse resp,double percent){
        AtomicInteger foulSum = new AtomicInteger();
        playerA.stream()
                .forEach(ele->{
                    if(!ele.getPosition().equals(Position.GK)) {
                        Stat stat = ele.getStat();
                        int increase = (int)(stat.getDefense() * percent) +
                                (int)(stat.getTackle()*percent) +
                                (int)(stat.getIntercepting()*percent) +
                                (int)(stat.getSlidingTackle()*percent);


                        int rn = (int) (Math.random() * increase);
                        int v;
                        if (rn <= 50) v = (int) (Math.random() * 20);
                        else if (rn <= 150) v = (int) (Math.random() * 13);
                        else if (rn <= 250) v = (int) (Math.random() * 7);
                        else v = (int) (Math.random() * 3);

                        int defense = (int) (Math.random() * increase / 20) + increase / 20;

                        foulSum.addAndGet(v);
                        resp.getFoulList().add(v);
                        resp.getGoodDefenseList().add(defense);
                    }
                    else{
                        resp.getFoulList().add(0);
                        resp.getGoodDefenseList().add(0);
                    }
                });
        int rn = (int)(Math.random()*foulSum.get());
        if(rn % 30 == 0){
            List<Long> kicker = new ArrayList<>();
            playerB.stream().forEach(ele->{
                if(ele.getPosition().equals(Position.ST) || ele.getPosition().equals(Position.CF)
                        || ele.getPosition().equals(Position.RF) || ele.getPosition().equals(Position.LF)){

                    kicker.add(ele.getId());
                }
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
                goalAssist.add(new DuoInfo(k,k.getId(),0L,GoalType.FREEKICK));
            }
            else if(rdn % 100 ==0){
                int tf = (int)(Math.random()*10);
                if(tf % 2 == 0 || tf > 5)
                goalAssist.add(new DuoInfo(k,k.getId(),0L,GoalType.PENALTYKICK));
            }

            resp.getFreeKickPair().add(1);
        }
        else resp.getFreeKickPair().add(0);
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
                            goalAssist.add(new DuoInfo(ele,ele.getId(),kick,decisionGoalType(ele,"corner")));
                        }
                        else if(possible < 300 && possible % 70 == 0){
                                goalAssist.add(new DuoInfo(ele,ele.getId(),kick,decisionGoalType(ele,"corner")));
                        }
                        else{
                            if(possible % 50 == 0){
                                goalAssist.add(new DuoInfo(ele,ele.getId(),kick,decisionGoalType(ele,"corner")));
                            }
                        }

                    }
        });


    }



    private int shareCalculation(List<Player> p,Map<Long,Integer> pass,double percent){
        AtomicInteger ret = new AtomicInteger();

        p.stream().forEach(ele->{
            if(!pass.containsKey(ele.getId()))
            ret.addAndGet(pass.get(ele.getId()));
            int stamina = (int)(ele.getStat().getStamina() * percent);
            int activeness =(int)(ele.getStat().getActiveness() *percent);
            int psy = (int)(ele.getStat().getPhysicalFight()*percent);

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
        double ret = ((a * 100)/(a + b));
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
    private GoalType decisionGoalType(Player player,String status){
        int h = player.getStat().getHeading();
        if(status.equals("corner")){
            h += 100;
        }
        int rn = (int)(Math.random() * (h / 2));
        boolean arr[] = new boolean[100];
        while(rn !=0){
            arr[(int)(Math.random()*100)] = true;
            rn-=1;

        }
        int d = (int)(Math.random()*100);
        if(arr[d]) return GoalType.NOMAL;
        else return GoalType.HEADING;
    }


}
