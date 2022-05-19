package com.example.soccerleague.RegisterService.LeagueRound.Duo;

import com.example.soccerleague.RegisterService.EloRatingSystem;
import com.example.soccerleague.RegisterService.LeagueSeasonTable;
import com.example.soccerleague.RegisterService.LeagueSeasonTableDto;
import com.example.soccerleague.RegisterService.LeagueRound.LeagueRoundSeasonResult;
import com.example.soccerleague.domain.DataTransferObject;
import com.example.soccerleague.domain.League;
import com.example.soccerleague.domain.Player.Player;
import com.example.soccerleague.domain.Round.LeagueRound;
import com.example.soccerleague.domain.Round.Round;
import com.example.soccerleague.domain.Round.RoundStatus;
import com.example.soccerleague.domain.Season;
import com.example.soccerleague.domain.Team;
import com.example.soccerleague.domain.record.*;
import com.example.soccerleague.springDataJpa.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class DefaultDuoRecordRegister implements DuoRecordRegister{
    private final DuoRepository duoRepository;
    private final RoundRepository roundRepository;
    private final LeagueSeasonTable leagueSeasonTable;
    private final LeagueRepository leagueRepository;
    private final TeamLeagueRecordRepository teamLeagueRecordRepository;
    private final PlayerLeagueRecordRepository playerLeagueRecordRepository;
    private final PlayerRepository playerRepository;
    private final TeamRepository teamRepository;
    private final EloRatingSystem eloRatingSystem;
    private final DirectorLeagueRecordRepository directorLeagueRecordRepository;
    @Override
    public boolean supports(DataTransferObject dataTransferObject) {
        return dataTransferObject instanceof DuoRecordDto;
    }

    @Override
    public void register(DataTransferObject dataTransferObject) {

        DuoRecordDto duoRecordDto = (DuoRecordDto)dataTransferObject;
        LeagueRound leagueRound =  (LeagueRound) roundRepository.findById(duoRecordDto.getRoundId()).orElse(null);
        int sz = duoRecordDto.getScorer().size();
        for(int i = 0;i < sz; i++){
            Long scorer = duoRecordDto.getScorer().get(i);
            Long assistant = duoRecordDto.getAssistant().get(i);
            GoalType goalType = duoRecordDto.getGoalType().get(i);
            Duo duo = Duo.create(scorer,assistant,goalType,leagueRound);
            duoRepository.save(duo);
        }
        leagueRound.setRoundStatus(RoundStatus.DONE);
        rankMake(leagueRound);


        if(roundRepository.currentRoundIsDone(leagueRound.getRoundSt()) == 0L){
            Season.CURRENTLEAGUEROUND += 1;
            if(Season.CURRENTLEAGUEROUND > Season.LASTLEAGUEROUND){
                for(Long i = 1L ; i<=4L;i++){
                    eloRatingSystem.LeagueSeasonResultCalc(i);
                }
                Season.CURRENTSEASON += 1;
                Season.CURRENTLEAGUEROUND = 1;
                leagueRepository.findAll().stream().forEach(ele->leagueSeasonTable.register(new LeagueSeasonTableDto(ele.getId(),Season.CURRENTSEASON)));
            }
            leagueRepository.findAll().stream().forEach(ele->{ele.setCurrentSeason(Season.CURRENTSEASON); ele.setCurrentRoundSt(Season.CURRENTLEAGUEROUND);});
        }
    }

    private void rankMake(Round round) {
        // roundst보다 적은 결과만을 가져옴.
        List<Team> teamList = teamRepository.findByLeagueId(round.getLeagueId());
        List<LeagueRoundSeasonResult> ret = new ArrayList<>();

        for (var team : teamList) {
            List<TeamLeagueRecord> teamRecord = teamLeagueRecordRepository.findBySeasonAndTeam(team.getId(),round.getSeason(),RoundStatus.DONE).stream().map(ele -> (TeamLeagueRecord) ele).collect(Collectors.toList());
            int win = 0, draw = 0, lose = 0, gain = 0, lost = 0;
            for (var record : teamRecord) {
                if (record.getRound().getRoundStatus().equals(RoundStatus.DONE)) {
                    if (record.getMatchResult().equals(MatchResult.WIN)) win++;
                    if (record.getMatchResult().equals(MatchResult.DRAW)) draw++;
                    else lose++;
                    gain += record.getScore();
                    lost += record.getOppositeScore();
                }
            }
            ret.add(LeagueRoundSeasonResult.create(team, win, draw, lose, gain, lost));
        }
        for (int i = 0; i < ret.size(); i++) {
            int r = 1;
            LeagueRoundSeasonResult temp = ret.get(i);
            int myPoint = temp.getPoint();
            int myDiff = temp.getDiff();
            for (int k = 0; k < ret.size(); k++) {
                LeagueRoundSeasonResult nxt = ret.get(k);
                int youPoint = nxt.getPoint();
                int youDiff = nxt.getDiff();
                if (myPoint < youPoint) r++;
                else if (myPoint == youPoint && myDiff < youDiff) r++;
            }
            temp.setRank(r);
        }

        for (var element : ret) {
            TeamLeagueRecord tlr =  teamLeagueRecordRepository.findByLastRecord(element.getTeam().getId(),round.getSeason(), PageRequest.of(0,1)).stream().findFirst().orElse(null);
            DirectorLeagueRecord directorLeagueRecord = directorLeagueRecordRepository.findByRoundAndTeam(round.getId(), element.getTeam().getId()).orElse(null);
            if(directorLeagueRecord == null)continue;
            if (tlr == null) continue;
            directorLeagueRecord.setRank(element.getRank());
            tlr.setRank(element.getRank());

            List<Player> playerList = playerRepository.findByTeam(element.getTeam());
            for (var player : playerList) {
                PlayerLeagueRecord plr = playerLeagueRecordRepository.findFirstByLast(player.getId(),round.getSeason(),PageRequest.of(0,1)).stream().findFirst().orElse(null);
                if (plr == null) continue;
                plr.setRank(element.getRank());
            }
        }
    }



}
