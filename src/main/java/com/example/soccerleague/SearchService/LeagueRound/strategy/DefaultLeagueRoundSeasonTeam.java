package com.example.soccerleague.SearchService.LeagueRound.strategy;
import com.example.soccerleague.domain.DataTransferObject;
import com.example.soccerleague.domain.Round.Round;
import com.example.soccerleague.domain.Round.RoundStatus;
import com.example.soccerleague.domain.Team;
import com.example.soccerleague.domain.record.*;
import com.example.soccerleague.springDataJpa.RoundRepository;
import com.example.soccerleague.springDataJpa.TeamLeagueRecordRepository;
import com.example.soccerleague.springDataJpa.TeamRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DefaultLeagueRoundSeasonTeam implements  LeagueRoundSeasonTeam {
    private final TeamLeagueRecordRepository teamLeagueRecordRepository;
    private final RoundRepository roundRepository;
    private final TeamRepository teamRepository;
    @Override
    public boolean supports(DataTransferObject dto) {
        return dto instanceof LeagueRoundSeasonTeamRequest;
    }

    @Override
    public Optional<DataTransferObject> search(DataTransferObject dataTransferObject) {
        LeagueRoundSeasonTeamRequest req = (LeagueRoundSeasonTeamRequest) dataTransferObject;
        Round round = roundRepository.findById(req.getRoundId()).orElse(null);
        Team team = teamRepository.findById(req.getTeamId()).orElse(null);

        LeagueRoundSeasonTeamResponse resp = LeagueRoundSeasonTeamResponse.create(team.getName(),"League");
        List<TeamLeagueRecord> teamRecordList = teamLeagueRecordRepository
                .findBySeasonAndTeam(team.getId(),round.getSeason(), RoundStatus.DONE).stream().map(ele->(TeamLeagueRecord)ele).collect(Collectors.toList());

        int sz = teamRecordList.size();
        int count  = 0;
        for(int i = 0;i < sz ;i++){
            TeamLeagueRecord teamLeagueRecord = teamRecordList.get(i);
            if(teamLeagueRecord.getRound().getRoundSt() < round.getRoundSt()) {
                resp.update(teamLeagueRecord.getMatchResult(), teamLeagueRecord.getScore(), teamLeagueRecord.getOppositeScore(), teamLeagueRecord.getRank());
                if (i > sz - 5) resp.recentUpdate(teamLeagueRecord.getMatchResult());
                count ++;
            }
        }
        resp.updateGainAndLost(count);
        return Optional.ofNullable(resp);
    }
}
