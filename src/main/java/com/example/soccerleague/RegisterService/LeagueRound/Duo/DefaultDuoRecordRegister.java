package com.example.soccerleague.RegisterService.LeagueRound.Duo;

import com.example.soccerleague.EntityRepository.*;
import com.example.soccerleague.RegisterService.LeagueSeasonTable;
import com.example.soccerleague.Web.dto.League.LeagueRoundSeasonResult;
import com.example.soccerleague.Web.newDto.league.LeagueSeasonTableDto;
import com.example.soccerleague.domain.DataTransferObject;
import com.example.soccerleague.domain.League;
import com.example.soccerleague.domain.Player.Player;
import com.example.soccerleague.domain.Round.LeagueRound;
import com.example.soccerleague.domain.Round.Round;
import com.example.soccerleague.domain.Round.RoundStatus;
import com.example.soccerleague.domain.Season;
import com.example.soccerleague.domain.Team;
import com.example.soccerleague.domain.record.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    private final DuoEntityRepository duoEntityRepository;
    private final RoundEntityRepository roundEntityRepository;
    private final LeagueSeasonTable leagueSeasonTable;
    private final LeagueEntityRepository leagueEntityRepository;
    private final TeamLeagueRecordEntityRepository teamLeagueRecordEntityRepository;
    private final PlayerLeagueRecordEntityRepository playerLeagueRecordEntityRepository;
    private final PlayerEntityRepository playerEntityRepository;
    private final TeamEntityRepository teamEntityRepository;
    @Override
    public boolean supports(DataTransferObject dataTransferObject) {
        return dataTransferObject instanceof DuoRecordDto;
    }

    @Override
    public void register(DataTransferObject dataTransferObject) {

        DuoRecordDto duoRecordDto = (DuoRecordDto)dataTransferObject;
        LeagueRound leagueRound =  (LeagueRound) roundEntityRepository.findById(duoRecordDto.getRoundId()).orElse(null);
        int sz = duoRecordDto.getScorer().size();
        for(int i = 0;i < sz; i++){
            Long scorer = duoRecordDto.getScorer().get(i);
            Long assistant = duoRecordDto.getAssistant().get(i);
            GoalType goalType = duoRecordDto.getGoalType().get(i);
            Duo duo = Duo.create(scorer,assistant,goalType,leagueRound);
            duoEntityRepository.save(duo);
        }
        leagueRound.setRoundStatus(RoundStatus.DONE);
        rankMake(leagueRound);
        if(roundEntityRepository.currentRoundIsDone(leagueRound)){
            Season.CURRENTLEAGUEROUND += 1;
            if(Season.CURRENTLEAGUEROUND > Season.LASTLEAGUEROUND){
                Season.CURRENTSEASON += 1;
                Season.CURRENTLEAGUEROUND = 1;
                leagueEntityRepository.findAll().stream().map(ele->(League)ele).forEach(ele->leagueSeasonTable.register(new LeagueSeasonTableDto(ele.getId(),Season.CURRENTSEASON)));
            }
            leagueEntityRepository.findAll().stream().map(ele->(League)ele).forEach(ele->{ele.setCurrentSeason(Season.CURRENTSEASON); ele.setCurrentRoundSt(Season.CURRENTLEAGUEROUND);});
        }


    }

    private void rankMake(Round round) {
        // roundst보다 적은 결과만을 가져옴.
        List<Team> teamList = teamEntityRepository.findByLeagueId(round.getLeagueId());
        List<LeagueRoundSeasonResult> ret = new ArrayList<>();

        for (var team : teamList) {
            List<TeamLeagueRecord> teamRecord = teamLeagueRecordEntityRepository.findBySeasonAndTeam(round.getSeason(), team.getId()).stream().map(ele -> (TeamLeagueRecord) ele).collect(Collectors.toList());
            int win = 0, draw = 0, lose = 0, gain = 0, lost = 0;
            for (var record : teamRecord) {
                if (record.getRound().getRoundStatus().equals(RoundStatus.DONE)) {
                    if (record.getMathResult().equals(MatchResult.WIN)) win++;
                    if (record.getMathResult().equals(MatchResult.DRAW)) draw++;
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
            TeamLeagueRecord tlr = teamLeagueRecordEntityRepository.findByLastRecord(round.getSeason(), element.getTeam().getId()).orElse(null);
            if (tlr == null) continue;

            tlr.setRank(element.getRank());

            List<Player> playerList = playerEntityRepository.findByTeam(element.getTeam());
            for (var player : playerList) {
                PlayerLeagueRecord plr = playerLeagueRecordEntityRepository.findByLast(round.getSeason(), player.getId()).orElse(null);
                if (plr == null) continue;
                plr.setRank(element.getRank());
            }
        }
    }



}
