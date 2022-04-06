package com.example.soccerleague.Service;

import com.example.soccerleague.Repository.*;
import com.example.soccerleague.Web.dto.Cmp.LeagueRound.LeagueRoundTopPlayerCmpByAttackPoint;
import com.example.soccerleague.Web.dto.League.*;
import com.example.soccerleague.domain.DataTransferObject;
import com.example.soccerleague.domain.League;
import com.example.soccerleague.domain.Player.Player;
import com.example.soccerleague.domain.Round.LeagueRound;
import com.example.soccerleague.domain.Round.Round;
import com.example.soccerleague.domain.Round.RoundStatus;
import com.example.soccerleague.domain.Season;
import com.example.soccerleague.domain.Team;
import com.example.soccerleague.domain.record.MatchResult;
import com.example.soccerleague.domain.record.PlayerLeagueRecord;
import com.example.soccerleague.domain.record.TeamLeagueRecord;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class RoundServiceImpl implements RoundService {


    private final RoundRepository roundRepository;
    private final TeamRepository teamRepository;
    private final LeagueRepository leagueRepository;
    private final TeamLeagueRecordRepository teamLeagueRecordRepository;
    private final PlayerRepository playerRepository;
    private final PlayerLeagueRecordRepository playerLeagueRecordRepository;
    /**
     * 각 리그의 시즌의 전체적인 경기
     * when 시즌 시작시 반드시 한번만 호출.
     *
     * @param leagueId
     * @param season
     */
    @Override
    public void leagueRoundTable(Long leagueId, int season) {
        if(roundRepository.findByLeagueSeason(leagueId, season))return;
        List<Team> ret = teamRepository.findByLeagueTop16(leagueId);
        int arr[] = new int[17];
        int vec[][] = new int[17][17];
        boolean posible[][] = new boolean[17][17];
        for(int j =0;j<15;j++){
            boolean visited[] = new boolean[17];
            for(int i =0; i <16;i++){
                for(int k =0;k<16;k++){
                    if(i != k && !visited[k]){
                        if((arr[i]&(1<< k)) == 0){
                            vec[i][j] = k;
                            visited[k]=true;
                            arr[i] |= (1<<k);
                            break;
                        }
                    }
                }
            }
        }

        for(int i =0;i<16;i++){
            for(int k =0;k<15;k++){
                int opposite = vec[i][k];
                if(posible[i][opposite] || posible[opposite][i])continue;
                posible[i][opposite] = true;
                posible[opposite][i] = true;
                LeagueRound round = LeagueRound.createLeagueRound(leagueId,ret.get(i).getId(),ret.get(opposite).getId(),season,k+1);
                roundRepository.save(round);
            }
        }
    }

    /**
     *
     * 리그 정보 ,시즌 + 라운드St 정보 round 정보를 가져온다
     * @param leagueId
     * @param season
     * @param roundSt
     * @return
     */
    @Override
    public List<Round> searchLeagueAndSeasonAndRoundSt(Long leagueId, int season, int roundSt) {
        return roundRepository.findByLeagueAndSeasonAndRoundSt(leagueId,season,roundSt);
    }

    /**
     * 모든 리그에 대해서
     * 시즌 + 라운드St 정보가 모두 ROUNDSTATUS.DONE으로 검색되면 false
     * 경기가 아직 남아 있다면 true
     *
     * @param season
     * @param roundSt
     * @return
     */
    @Override
    public Boolean isLeagueRoundStRemain(int season, int roundSt) {
        boolean flag = false;
        List<Round> remainSeasonAndRoundSt = roundRepository.isRemainSeasonAndRoundSt(season, roundSt);
        for(var ele:remainSeasonAndRoundSt){
            if(ele.getRoundStatus() != RoundStatus.DONE) flag = true;
        }
        return flag;
    }

    /**
     *
     * 리그 정보 ,시즌 + 라운드St 정보 round 정보를 가져온 후 league/round 페이지에 데이터를 내려주기 위한 작업.
     * roundStatus.DONE -> teamLeagueRecord를 조회해서 경기결과를 조회한 후에 스코어 정보를 넣고 올려줌.
     * 그렇지않으면 단순히 라운드 정보만을 넣어서 올려줌.
     * @param leagueId
     * @param season
     * @param roundSt
     * @return
     */
    @Override
    public List<DataTransferObject> searchLeagueAndSeasonAndRoundStDisplayDto(Long leagueId,int season, int roundSt) {
            League league = leagueRepository.findById(leagueId).orElse(null);
            List<DataTransferObject> ret = new ArrayList<>();
            List<Round> rounds = searchLeagueAndSeasonAndRoundSt(leagueId, season, roundSt);
            for(var r : rounds){
                Team teamA = teamRepository.findById(r.getHomeTeamId());
                Team teamB = teamRepository.findById(r.getAwayTeamId());
                TeamLeagueRecord teamLeagueRecord = teamLeagueRecordRepository.findByRoundId(r.getId()).stream().findFirst().orElse(null);
                LeagueRoundDisplayDto dto = null;
                if(r.getRoundStatus() == RoundStatus.DONE){
                    int scoreA = 0;
                    int scoreB = 0;
                    if(teamLeagueRecord.getTeam().getId() == teamA.getId()){
                        scoreA = teamLeagueRecord.getScore();
                        scoreB = teamLeagueRecord.getOppositeScore();
                    }
                    else{
                        scoreA = teamLeagueRecord.getOppositeScore();
                        scoreB = teamLeagueRecord.getScore();
                    }

//                    StringBuilder teamAName = new StringBuilder(teamA.getName());
//                    teamAName.append("   ");
//                    teamAName.append(String.valueOf(scoreA));
//                    teamAName.append("   ");
//
//                    StringBuilder teamBName = new StringBuilder(String.valueOf(scoreB));
//                    teamBName.append("   ");
//                    teamBName.append(teamB.getName());
//                    teamBName.append("   ");
//                    log.info("teamA {} ,teamB {}",teamAName,teamBName);
                    dto = LeagueRoundDisplayDto.create(
                            r.getId(), teamA.getName(),teamB.getName(),scoreA,scoreB,league.getName());
                }
                else{
                    dto = LeagueRoundDisplayDto.create(r.getId(),teamA.getName(),teamB.getName(),league.getName());
                }
                ret.add(dto);
            }
            return ret;

    }

    /**
     *
     *  roundId 를 받아서 round정보를 가져온후 두 팀을가져오고 , 선수들모두 가져와서 세팅한 후 올려준다.
     *  roundStatus -> YET 상태일때는 rare한 정보를 가져옴
     *  roundStatus -> YET 가 아닐때는 ->line-up이 저장이된 상태 ->playerLeagueRecord를 찾아서 세팅을 해줘야함.
     * @param roundId
     * @return
     */


    @Override
    public DataTransferObject getLineUp(Long roundId) {
        Round round = roundRepository.findById(roundId);
        Team teamA = teamRepository.findById(round.getHomeTeamId());
        Team teamB = teamRepository.findById(round.getAwayTeamId());

        LeagueRoundLineUp leagueRoundLineUp = new LeagueRoundLineUp();
        leagueRoundLineUp.setTeamA(teamA.getName());
        leagueRoundLineUp.setTeamB(teamB.getName());


        if(round.getRoundStatus() == RoundStatus.YET) {

            List<Player> playerListA = playerRepository.findByTeam(teamA);
            List<Player> playerListB = playerRepository.findByTeam(teamB);
            for (var ele : playerListA) {
                LineUpPlayer lineUpPlayer = LineUpPlayer.create(ele.getId(), ele.getName(), ele.getPosition());
                leagueRoundLineUp.getPlayerListA().add(lineUpPlayer);
                leagueRoundLineUp.getJoinPlayer().add(ele.getId());
            }
            for (var ele : playerListB) {
                LineUpPlayer lineUpPlayer = LineUpPlayer.create(ele.getId(), ele.getName(), ele.getPosition());
                leagueRoundLineUp.getPlayerListB().add(lineUpPlayer);
                leagueRoundLineUp.getJoinPlayer().add(ele.getId());
            }
        }
        else{
            List<PlayerLeagueRecord> byRoundId = playerLeagueRecordRepository.findByRoundId(roundId);
            for(var ele : byRoundId) {
                if(ele.getTeam().getId() == teamA.getId()){
                    Player player = ele.getPlayer();
                    LineUpPlayer lineUpPlayer = LineUpPlayer.create(player.getId(),player.getName(),ele.getPosition());
                    leagueRoundLineUp.getPlayerListA().add(lineUpPlayer);
                }
                else{
                    Player player = ele.getPlayer();
                    LineUpPlayer lineUpPlayer = LineUpPlayer.create(player.getId(),player.getName(),ele.getPosition());
                    leagueRoundLineUp.getPlayerListB().add(lineUpPlayer);
                }
            }
        }
        leagueRoundLineUp.setRoundId(roundId);
        leagueRoundLineUp.setRoundStatus(round.getRoundStatus());
        return leagueRoundLineUp;
    }



    /**
     * 선택한 데이터를 받아서 -> round 조회 -> 순서대로 선수가 선택된 데이터라면 순서에 맞게 position ,playerId  로 playerLeagueRecord를 생성
     *
     * ROUNDSTATUS.ING로 바꿈.
     *
     * @param roundId
     * @param dto
     */
    @Override
    public void lineUpSave(Long roundId, DataTransferObject dto) {
        Round round = roundRepository.findById(roundId);
        Team teamA = teamRepository.findById(round.getHomeTeamId());
        Team teamB = teamRepository.findById(round.getAwayTeamId());

        TeamLeagueRecord teamLeagueRecord = TeamLeagueRecord.create(round,teamA);
        teamLeagueRecordRepository.save(teamLeagueRecord);

        teamLeagueRecord = TeamLeagueRecord.create(round,teamB);
        teamLeagueRecordRepository.save(teamLeagueRecord);



        List<Player> playerListA = playerRepository.findByTeam(teamA);
        List<Player> playerListB = playerRepository.findByTeam(teamB);

        LeagueRoundLineUp leagueRoundLineUp = (LeagueRoundLineUp) dto;

        int szA = playerListA.size();
        int szB = playerListB.size();

        // playerListA의 순서와 dto.joinPosition순서가 동일함.
        for (int i = 0; i<szA;i++) {
            Player ele = playerListA.get(i);
            Long pid = ele.getId();
            boolean flag = false;

            for(var joinPlayerId:leagueRoundLineUp.getJoinPlayer()){
                if(joinPlayerId.equals(pid))flag=true;
            }

            if(flag){
                PlayerLeagueRecord playerLeagueRecord = PlayerLeagueRecord.create(ele,leagueRoundLineUp.getJoinPosition().get(i),teamA,(LeagueRound)round);
                playerLeagueRecordRepository.save(playerLeagueRecord);
            }
        }
        for (int i = 0; i < szB;i++) {
            Player ele = playerListB.get(i);
            Long pid = ele.getId();
            boolean flag = false;
            for(var joinPlayerId:leagueRoundLineUp.getJoinPlayer()){
                if(joinPlayerId.equals(pid))flag=true;
            }
            if(flag){
                PlayerLeagueRecord playerLeagueRecord = PlayerLeagueRecord.create(ele,leagueRoundLineUp.getJoinPosition().get(szA + i),teamB,(LeagueRound)round);
                playerLeagueRecordRepository.save(playerLeagueRecord);
            }
        }

        round.setRoundStatus(RoundStatus.ING);

    }



    /**
     * roundId로 ROund정보를 가져와 올림
     * @param roundId
     * @return
     */
    @Override
    public Round searchRound(Long roundId) {
        return roundRepository.findById(roundId);
    }


    /**
     *  roundId 를 이용해서 teamRecord , playerRecord를 가져와 DTO에 넣어주고 올린다.
     *
     *
     * @param roundId
     * @return
     */
    @Override
    public DataTransferObject getGameForm(Long roundId) {
        Round round = roundRepository.findById(roundId);

        List<TeamLeagueRecord> teamLeagueRecordList = teamLeagueRecordRepository.findByRoundId(roundId);

        LeagueRoundGameDto leagueRoundGameDto = new LeagueRoundGameDto();
        leagueRoundGameDto.setRoundStatus(RoundStatus.ING);
        leagueRoundGameDto.setTeamA(teamLeagueRecordList.get(0).getTeam().getName());
        leagueRoundGameDto.setTeamB(teamLeagueRecordList.get(1).getTeam().getName());

        List<PlayerLeagueRecord> byRoundId = playerLeagueRecordRepository.findByRoundId(roundId);
        for(var ele:byRoundId){
            Player player = ele.getPlayer();
            LineUpPlayer lineUpPlayer = LineUpPlayer.create(player.getId(),player.getName(),ele.getPosition());

            if(teamLeagueRecordList.get(0).getTeam().getId() == ele.getTeam().getId())
                leagueRoundGameDto.getPlayerListA().add(lineUpPlayer);
            else
                leagueRoundGameDto.getPlayerListB().add(lineUpPlayer);
        }


        return leagueRoundGameDto;
    }

    /**
     *
     * 결과를 받아서 최종적으로 teamLeaugeRecord ,playerLeagueRecord를 수정해준다.
     *
     * @param roundId
     * @param dto
     */
    @Override
    public void gameResultSave(Long roundId, DataTransferObject dto) {
        Round round = roundRepository.findById(roundId);


        List<PlayerLeagueRecord> byRoundId = playerLeagueRecordRepository.findByRoundId(roundId);



        LeagueRoundGameDto res = (LeagueRoundGameDto)dto;

        //best player 처리 로직.
        int bestGrade = -1;
        for(int i = 0  ; i < res.getGradeList().size() ; i++){
            if(bestGrade < res.getGradeList().get(i)){
                bestGrade = res.getGradeList().get(i);
            }
        }
        //matchResult  처리.
        MatchResult matchResultA = null;
        MatchResult matchResultB = null;
        if(res.getScorePair().get(0) > res.getScorePair().get(1)){
            matchResultA = MatchResult.WIN;
            matchResultB = MatchResult.LOSE;
        }
        else if(res.getScorePair().get(0) == res.getScorePair().get(1)){
            matchResultA = MatchResult.DRAW;
            matchResultB = MatchResult.DRAW;
        }
        else{
            matchResultA = MatchResult.LOSE;
            matchResultB = MatchResult.WIN;
        }
        List<TeamLeagueRecord> teams = teamLeagueRecordRepository.findByRoundId(roundId);

        recordSave(0,matchResultA,byRoundId,dto,teams.get(0),bestGrade);
        recordSave(1,matchResultB,byRoundId,dto,teams.get(1),bestGrade);

        round.setRoundStatus(RoundStatus.DONE);
        rankMake(round);
        //SEASON AND ROUND 가 변하는 유일한 구간.
        endRoundOrEndSeason();

    }


    /**
     * 랭크 관리 매경기가 끝난 후 호출해주어야함.
     */
    private void rankMake(Round round){
        // roundst보다 적은 결과만을 가져옴.
        List<Team> teamList = teamRepository.findByLeagueId(round.getLeagueId());
        List<LeagueRoundSeasonResult> ret = new ArrayList<>();
        for(var team:teamList){
            LeagueRoundSeasonResult dto = (LeagueRoundSeasonResult)teamSeasonResult(round.getSeason(), round.getRoundSt() + 1, team);
            ret.add(dto);
        }

        for(int i =0;i<ret.size();i++){
            int r = 1;
            LeagueRoundSeasonResult temp = ret.get(i);
            int myPoint = temp.getPoint();
            int myDiff =  temp.getDiff();
            for(int k =0;k<ret.size();k++){
                LeagueRoundSeasonResult nxt = ret.get(k);
                int youPoint = nxt.getPoint();
                int youDiff = nxt.getDiff();
                if(myPoint < youPoint)r++;
                else if(myPoint == youPoint && myDiff < youDiff)r++;
            }
            temp.setRank(r);
        }


        for(var element : ret){
            TeamLeagueRecord tlr = teamLeagueRecordRepository.findByLastRecord(round.getSeason(), element.getTeam().getId()).orElse(null);
            if(tlr == null)continue;

            tlr.setRank(element.getRank());

            List<Player> playerList = playerRepository.findByTeam(element.getTeam());
            for(var player:playerList){
                PlayerLeagueRecord plr = playerLeagueRecordRepository.findByLast(round.getSeason(), player.getId()).orElse(null);
                if(plr == null) continue;
                plr.setRank(element.getRank());
            }
        }



    }

    /**
     * 매경기가 끝날때 마다 호출되며 현재라운드 경기가끝났는지 확인하고
     * 모두 끝났다면 현재시즌을 ++해준다
     *
     * 현재시즌이 마지막 라운드보다 크다면 시즌라운드테이블을 채운후
     * 현재 라운드를 1로설정한다.
     */
    private void endRoundOrEndSeason(){
        List<Round> roundList = roundRepository.isRemainSeasonAndRoundSt(Season.CURRENTSEASON,Season.CURRENTLEAGUEROUND);
        Round round = roundList.stream().filter(ele -> !ele.getRoundStatus().equals(RoundStatus.DONE)).findFirst().orElse(null);
        if(round == null){
            List<League> all = leagueRepository.findAll();
            Season.CURRENTLEAGUEROUND += 1;
            for(var ele:all){
                ele.setCurrentRoundSt(Season.CURRENTLEAGUEROUND);
            }
            if(Season.CURRENTLEAGUEROUND > Season.LASTLEAGUEROUND){
                Season.CURRENTSEASON += 1;
                Season.CURRENTLEAGUEROUND = 1;
                for(var ele : all){
                    leagueRoundTable(ele.getId(),Season.CURRENTSEASON);
                    ele.setCurrentSeason(Season.CURRENTSEASON);
                    ele.setCurrentRoundSt(Season.CURRENTLEAGUEROUND);
                }

            }
        }

    }


    /**
     * gameResultSave 를 도와주는 메서드
     *
     */
    private void recordSave(int idx,MatchResult matchResult,List<PlayerLeagueRecord> byRoundId,
                            DataTransferObject dto,TeamLeagueRecord teamLeagueRecord,int bestGrade)
    {
        LeagueRoundGameDto res = (LeagueRoundGameDto)dto;
        int count = 0;
        int sumPass = 0;
        int sumShooting = 0;
        int sumValidShooting = 0;
        int sumFoul = 0;
        int sumGoodDefense = 0;
        int avgGrade = 0;
        for(int i = 0;i<byRoundId.size();i++){
            PlayerLeagueRecord playerLeagueRecord = byRoundId.get(i);
            if(playerLeagueRecord.getTeam().getId()== teamLeagueRecord.getTeam().getId()) {
                int goal = res.getGoalList().get(i);
                int assist = res.getAssistList().get(i);
                int pass = res.getPassList().get(i);
                int shooting = res.getShootingList().get(i);
                int validShooting = res.getValidShootingList().get(i);
                int foul = res.getFoulList().get(i);
                int goodDefense = res.getGoodDefenseList().get(i);
                int grade = res.getGradeList().get(i);
                int rating = playerLeagueRecord.getPlayer().getRating();
                boolean isBest = grade == bestGrade ? true : false;
                count +=1;
                sumPass += pass;
                sumShooting += shooting;
                sumValidShooting += validShooting;
                sumFoul += foul;
                sumGoodDefense += goodDefense;
                avgGrade += grade;
                playerLeagueRecord.update(goal, assist, pass, shooting, validShooting, foul, goodDefense, grade, matchResult, isBest,rating);
            }
        }

        avgGrade /= count;
        teamLeagueRecord.update(
                res.getScorePair().get(idx),res.getScorePair().get(idx ^ 1),
                res.getSharePair().get(idx),res.getCornerKickPair().get(idx),
                res.getFreeKickPair().get(idx),sumPass,sumShooting,sumValidShooting,sumFoul,sumGoodDefense,avgGrade,matchResult,
                teamLeagueRecord.getTeam().getRating()
        );


    }


    /**
     *
     * 팀의 경기결과를 가져와 dto 에넣고 올려준다.
     * @param roundId
     * @return
     */
    @Override
    public List<DataTransferObject> gameTeamResult(Long roundId) {
        List<TeamLeagueRecord> teams = teamLeagueRecordRepository.findByRoundId(roundId);
        TeamLeagueRecord teamA = teams.get(0);
        TeamLeagueRecord teamB = teams.get(1);
        LeagueRoundGameResultTeamDto retA = LeagueRoundGameResultTeamDto.create(
                teamA.getTeam().getName(),teamA.getTeam().getId(),teamA.getScore(),teamA.getShare(),teamA.getCornerKick(),teamA.getFreeKick());
        LeagueRoundGameResultTeamDto retB = LeagueRoundGameResultTeamDto.create(
                teamB.getTeam().getName(),teamB.getTeam().getId(),teamB.getScore(),teamB.getShare(),teamB.getCornerKick(),teamB.getFreeKick());
        List<DataTransferObject> ret = new ArrayList<>();
        ret.add(retA);
        ret.add(retB);
        return ret;
    }
    /**
     *
     * 선수의 경기결과를 가져와 dto 에넣고 올려준다.
     * @param roundId
     * @return
     */
    @Override
    public List<DataTransferObject> gamePlayerResult(Long roundId) {
        List<PlayerLeagueRecord> players = playerLeagueRecordRepository.findByRoundId(roundId);
        List<DataTransferObject> ret = new ArrayList<>();
        for(var ele : players){
            LeagueRoundGameResultPlayerDto tmp = LeagueRoundGameResultPlayerDto.create(
                   ele.getTeam().getId(), ele.getPosition(),ele.getPlayer().getName(),ele.getGoal(), ele.getAssist(),
                    ele.getPass(),ele.getShooting(), ele.getValidShooting(),ele.getFoul(),ele.getGoodDefense(),ele.getGrade()
            );
            ret.add(tmp);
        }
        return ret;
    }

    /**
     *
     *
     * LeagueRoundStrategyDto 를 사용.
     * Round를 통해 시즌 승무패 ,최근5경기 승무패, 평균득점 평균실점을 계산 후 넘겨준다.
     *
     * @param roundId
     * @return
     */
    @Override
    public List<DataTransferObject> seasonTeamGameResultWithStrategy(Long roundId) {
        Round round = roundRepository.findById(roundId);

        Team teamA = teamRepository.findById(round.getHomeTeamId());
        Team teamB = teamRepository.findById(round.getAwayTeamId());
        List<DataTransferObject> ret = new ArrayList<>();

        ret.add(teamSeasonResultWithStrategy(round,teamA));
        ret.add(teamSeasonResultWithStrategy(round,teamB));



        return ret;
    }

    /**
     * 전력페이지용
     * LeagueRoundStrategyDto
     * 현재 시즌 리그 경기결과를  반환
     *
     */
    private DataTransferObject teamSeasonResultWithStrategy(Round round,Team team){
        List<TeamLeagueRecord> teamAResult = teamLeagueRecordRepository.findBySeasonAndTeamRecent5(round.getSeason(),round.getRoundSt(), team);
        LeagueRoundStrategyDto dto = new LeagueRoundStrategyDto();

        dto.setName(team.getName());
        dto.setType("League");

        int sz = 0,win =0,draw = 0,lose = 0;
        double gain = 0,lost = 0;
        for(var ele : teamAResult) {
            if (ele.getMathResult().equals(MatchResult.WIN)) win += 1;
            else if (ele.getMathResult().equals(MatchResult.DRAW)) draw += 1;
            else lose += 1;
            gain += ele.getScore();
            lost += ele.getOppositeScore();
            sz += 1;
        }
        if(sz > 0){
            dto.setAvgGain(gain / sz);
            dto.setAvgLost(lost / sz);
        }
        dto.setRecentWin(win);
        dto.setRecentDraw(draw);
        dto.setRecentLose(lose);


        //LeagueRoundSeasonResult정보를 가져와서 LeagueRoundStrategyDto 결과를 채움
        List<DataTransferObject> dataTransferObjects = leagueSeasonResult(round.getSeason(),round.getRoundSt(),team.getLeague().getId());
        for(var ele : dataTransferObjects){
            LeagueRoundSeasonResult temp = (LeagueRoundSeasonResult)ele;
            if(temp.getTeam().equals(team)) {
                dto.setTotalWin(temp.getWin());
                dto.setTotalDraw(temp.getDraw());
                dto.setTotalLose(temp.getLose());
                dto.setRank(temp.getRank());
                break;
            }
        }
        dto.setTotal(dto.getRank() +"위 "+dto.getTotalWin() + "승 " + dto.getTotalDraw() + "무 " + dto.getTotalLose() + "패");
        dto.setRecent(dto.getRecentWin() + "승 "  + dto.getRecentDraw() + "무 " + dto.getRecentLose() + "패");
        return dto;
    }

    /**
     * LeagueRoundSeasonResult
     * 현재 팀의 시즌경기 결과를 반환
     * @return
     */
    private DataTransferObject teamSeasonResult(int season,int roundSt,Team team){
        List<TeamLeagueRecord> teamAResult = teamLeagueRecordRepository.findBySeasonAndTeam(season,roundSt, team);
        int win = 0;
        int draw = 0;
        int lose = 0;
        int gain = 0,lost =0;
        for(var ele : teamAResult){
            if(ele.getMathResult() == null)continue;
            if(ele.getMathResult().equals(MatchResult.WIN)) win+=1;
            else if(ele.getMathResult().equals(MatchResult.DRAW)) draw+=1;
            else
                lose+=1;
            gain += ele.getScore();
            lost += ele.getOppositeScore();
        }
        LeagueRoundSeasonResult ret = LeagueRoundSeasonResult.create(team,win,draw,lose,gain,lost);
        return ret;
    }


    /**
     * LeagueRoundSeasonResult
     * 현재 시즌 리그 순위를 매겨서 리턴해줌.
     * @param season
     * @param leagueId
     * @return
     */
    public List<DataTransferObject> leagueSeasonResult(int season,int roundSt,Long leagueId){
        List<DataTransferObject> ret = new ArrayList<>();

        List<Team> teamList = teamRepository.findByLeagueId(leagueId);

        for(var ele: teamList){
            LeagueRoundSeasonResult data = (LeagueRoundSeasonResult)teamSeasonResult(season,roundSt, ele);
            ret.add(data);
        }
        for(int i =0;i<ret.size();i++){
            int r = 1;
            LeagueRoundSeasonResult temp = (LeagueRoundSeasonResult)ret.get(i);
            int myPoint = temp.getPoint();
            int myDiff =  temp.getDiff();
            for(int k =0;k<ret.size();k++){
                LeagueRoundSeasonResult nxt = (LeagueRoundSeasonResult)ret.get(k);
                int youPoint = nxt.getPoint();
                int youDiff = nxt.getDiff();
                if(myPoint < youPoint)r++;
                else if(myPoint == youPoint && myDiff < youDiff)r++;
            }
            temp.setRank(r);
        }
        for(var ele :ret){
            LeagueRoundSeasonResult temp = (LeagueRoundSeasonResult)ele;
        }

        return ret;
    }

    /**
     *
     *  라운드 정보 -> 팀정보 -> 5경기 맞대결 teamLeagueRecord가져오기.
     *  호출한 시점에서 부터 과거의 결과만을 가져옴
     * @param roundId
     * @return
     */
    @Override
    public List<DataTransferObject> RecentShowDownWithStrategy(Long roundId) {
        Round round = roundRepository.findById(roundId);

        Team teamA = teamRepository.findById(round.getHomeTeamId());
        Team teamB = teamRepository.findById(round.getAwayTeamId());

        List<TeamLeagueRecord> teamLeagueRecordList = teamLeagueRecordRepository.findByShowDown(round,teamA, teamB);
        List<DataTransferObject> ret = new ArrayList<>();
        int count = 0;
        for(int i = 1;i< teamLeagueRecordList.size(); i+=2){
            TeamLeagueRecord temp = teamLeagueRecordList.get(i);
            if(temp.getRound().getSeason() == round.getSeason() && temp.getRound().getRoundSt() >= round.getRoundSt())continue;
            count ++;
            RecentShowDown element = RecentShowDown.create(teamA.getName(),teamB.getName(),temp.getScore(),temp.getOppositeScore(),temp.getSeason(),temp.getRound().getRoundSt());
            ret.add(element);
            if(count == 5)break;
        }

        return ret;
    }

    /**
     *  전력페이지에 season 일때 , roundst보다 적은 playerLeageuRecord 정보를 가져와서
     *  공격포인순으로 정렬 후 올려줌.
     * @param roundId
     * @return
     */
    @Override
    public List<DataTransferObject> seasonTopPlayerWithStrategy(Long roundId,String HomeOrAway) {
        Round round = roundRepository.findById(roundId);

        Team teamA = teamRepository.findById(round.getHomeTeamId());
        Team teamB = teamRepository.findById(round.getAwayTeamId());
        List<DataTransferObject> ret = new ArrayList<>();
        List<Object[]> objects = null;
        if(HomeOrAway.equals("home") || HomeOrAway.equals("A") || HomeOrAway.equals("Home")  || HomeOrAway.equals("HOME") || HomeOrAway.equals("a")){
            objects = playerLeagueRecordRepository.TopPlayerSeasonAndRoundStWithStrategy(round, teamA);
        }
        else{
            objects = playerLeagueRecordRepository.TopPlayerSeasonAndRoundStWithStrategy(round, teamB);
        }
        List<LeagueRoundTopPlayer> temp =  new ArrayList<>();

        for(var ele : objects){
            String name = (String)ele[0];
            int goal = Integer.valueOf(String.valueOf(ele[1]));
            int assist =Integer.valueOf(String.valueOf(ele[2]));
            LeagueRoundTopPlayer leagueRoundTopPlayer = new LeagueRoundTopPlayer(name,goal,assist);
            temp.add(leagueRoundTopPlayer);
        }
        temp.sort(new LeagueRoundTopPlayerCmpByAttackPoint());
        for(int i =0;i<5;i++){
            ret.add(temp.get(i));
        }
        return ret;
    }



}
