package com.example.soccerleague.SearchService.Round.Common;

import com.example.soccerleague.RegisterService.Champions.SeasonTable.ChampionsSeasonTable;
import com.example.soccerleague.RegisterService.EloRatingSystem;
import com.example.soccerleague.RegisterService.LeagueRound.LeagueRoundSeasonResult;
import com.example.soccerleague.RegisterService.LeagueSeasonTable;
import com.example.soccerleague.RegisterService.LeagueSeasonTableDto;
import com.example.soccerleague.SearchService.ChampionsRound.ChampionsRoundInfoRequest;
import com.example.soccerleague.domain.DataTransferObject;
import com.example.soccerleague.domain.Player.Player;
import com.example.soccerleague.domain.Round.ChampionsLeagueRound;
import com.example.soccerleague.domain.Round.Round;
import com.example.soccerleague.domain.Round.RoundStatus;
import com.example.soccerleague.domain.Season;
import com.example.soccerleague.domain.Team;
import com.example.soccerleague.domain.record.DirectorRecord;
import com.example.soccerleague.domain.record.MatchResult;
import com.example.soccerleague.domain.record.PlayerLeagueRecord;
import com.example.soccerleague.domain.record.TeamLeagueRecord;
import com.example.soccerleague.springDataJpa.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
@Service
@RequiredArgsConstructor
public class GameResult extends RoundCommon{
    private final TeamRepository teamRepository;
    private final TeamLeagueRecordRepository teamLeagueRecordRepository;
    private final PlayerRepository playerRepository;
    private final PlayerLeagueRecordRepository playerLeagueRecordRepository;
    private final PlayerChampionsRecordRepository playerChampionsRecordRepository;
    private final TeamChampionsRecordRepository teamChampionsRecordRepository;
    private final DirectorRecordRepository directorRecordRepository;
    private final RoundRepository roundRepository;
    private final LeagueRepository leagueRepository;
    private final LeagueSeasonTable leagueSeasonTable;
    private final ChampionsSeasonTable championsSeasonTable;
    private final EloRatingSystem eloRatingSystem;


    @Override
    protected DataTransferObject leagueFeature(Round round ,Object... objects) {
        Long leagueId = (Long)objects[0];
        int season = (int)objects[1];
        int roundSt = (int)objects[2];
        List<Team> teamList = teamRepository.findByLeagueId(leagueId);
        List<LeagueRoundSeasonResult> ret = new ArrayList<>();

        // 현 시즌에 경기가 끝난 결과들을 가져옴.
        for (var team : teamList) {
            List<TeamLeagueRecord> teamRecord = teamLeagueRecordRepository
                    .findBySeasonAndTeam(team.getId(),season, RoundStatus.DONE)
                    .stream().map(ele -> (TeamLeagueRecord) ele).collect(Collectors.toList());
            int win = 0, draw = 0, lose = 0, gain = 0, lost = 0;
            for (var record : teamRecord) {
                if (record.getMatchResult().equals(MatchResult.WIN)) win++;
                if (record.getMatchResult().equals(MatchResult.DRAW)) draw++;
                else lose++;
                gain += record.getScore();
                lost += record.getOppositeScore();
            }
            ret.add(LeagueRoundSeasonResult.create(team, win, draw, lose, gain, lost));
        }

        // 가져온 결과로 순위를 메이킹.
        for(int i = 0 ;i<ret.size();i++){
            int r = 1;
            LeagueRoundSeasonResult cur = ret.get(i);
            for(int k =0;k<ret.size();k++){
                LeagueRoundSeasonResult nxt = ret.get(k);
                if(cur.getPoint() < nxt.getPoint())r++;
                else if(cur.getPoint() == nxt.getPoint() && cur.getDiff() < nxt.getDiff())r++;
            }
            cur.setRank(r);
        }

        for (var element : ret) {
            TeamLeagueRecord tlr =  teamLeagueRecordRepository
                    .findByLastRecord(element.getTeam().getId(),season, PageRequest.of(0,1))
                    .stream().findFirst().orElse(null);

            DirectorRecord directorRecord = directorRecordRepository
                    .findByLastRecord(element.getTeam().getId()).stream().findFirst().orElse(null);

            if(directorRecord == null)continue;
            if (tlr == null) continue;

            directorRecord.setRank(element.getRank());
            tlr.setRank(element.getRank());

            List<Player> playerList = playerRepository.findByTeam(element.getTeam());
            for (var player : playerList) {
                PlayerLeagueRecord plr = playerLeagueRecordRepository.findFirstByLast(player.getId(),season,PageRequest.of(0,1)).stream().findFirst().orElse(null);
                if (plr == null) continue;
                plr.setRank(element.getRank());
            }
        }

        if(roundRepository.currentLeagueRoundIsDone(roundSt).equals(0L)){
            if(Season.CURRENTLEAGUEROUND != Season.LASTLEAGUEROUND) Season.CURRENTLEAGUEROUND += 1;
            isSeasonDone(round);
        }
        return null;
    }
    private void isSeasonDone(Round round){
        if(roundRepository.isSeasonDone(Season.CURRENTSEASON).equals(0L)){
            eloRatingSystem.seasonResultCalc(round);
            Season.CURRENTSEASON += 1;
            Season.CURRENTCHAMPIONSROUND = 16;
            Season.CURRENTLEAGUEROUND = 1;
            //TODO : 유로파
            leagueRepository.findAll().stream().forEach(ele -> {
                leagueSeasonTable.register(new LeagueSeasonTableDto(ele.getId(), Season.CURRENTSEASON));
            });
            championsSeasonTable.register(new ChampionsRoundInfoRequest(Season.CURRENTSEASON ,Season.CURRENTCHAMPIONSROUND));
            //TODO :유로파
        }
        leagueRepository.findAll().stream().forEach(ele -> {
            ele.setCurrentSeason(Season.CURRENTSEASON);
            ele.setCurrentRoundSt(Season.CURRENTLEAGUEROUND);
            ele.setCurrentChampionsRoundSt(Season.CURRENTCHAMPIONSROUND);
        });

    }


    private int getScore(ChampionsLeagueRound championsLeagueRound , Long teamId){
        return  teamChampionsRecordRepository
                .findTeamScoreByRound(championsLeagueRound.getId() - 1, championsLeagueRound.getId(),teamId)
                .stream()
                .mapToInt(ele -> ele.getScore())
                .sum();
    }
    @Override
    protected DataTransferObject championsFeature(Round round, Object... objects) {

        ChampionsLeagueRound championsLeagueRound = (ChampionsLeagueRound) round;
        if(championsLeagueRound.getFirstAndSecond() == 2){
            int scoreA = getScore(championsLeagueRound,championsLeagueRound.getHomeTeamId());
            int scoreB = getScore(championsLeagueRound,championsLeagueRound.getAwayTeamId());
            ChampionsLeagueRound newChampionsRound = new ChampionsLeagueRound();
            if(scoreA <= scoreB){
                if(championsLeagueRound.getRoundSt() != 2){

                }
            }
            else{

            }
        }

        isSeasonDone(round);










        return null;
    }

    @Override
    protected DataTransferObject feature(Round round , Object... objects) {
        return null;
    }




}
