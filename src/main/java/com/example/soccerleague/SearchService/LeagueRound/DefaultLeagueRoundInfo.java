package com.example.soccerleague.SearchService.LeagueRound;

import com.example.soccerleague.domain.DataTransferObject;
import com.example.soccerleague.domain.League;
import com.example.soccerleague.domain.Round.RoundStatus;
import com.example.soccerleague.domain.Team;
import com.example.soccerleague.domain.record.TeamLeagueRecord;
import com.example.soccerleague.springDataJpa.LeagueRepository;
import com.example.soccerleague.springDataJpa.RoundRepository;
import com.example.soccerleague.springDataJpa.TeamLeagueRecordRepository;
import com.example.soccerleague.springDataJpa.TeamRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * roundPage
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DefaultLeagueRoundInfo implements LeagueRoundInfo {
    private final RoundRepository roundRepository;
    private final TeamRepository teamRepository;
    private final TeamLeagueRecordRepository teamLeagueRecordRepository;
    private final LeagueRepository leagueRepository;
    @Override
    public boolean supports(DataTransferObject dto) {
        return dto instanceof LeagueRoundInfoRequest;
    }



    @Override
    public List<DataTransferObject> searchList(DataTransferObject dataTransferObject) {
        LeagueRoundInfoRequest req = (LeagueRoundInfoRequest) dataTransferObject;
        List<DataTransferObject> resp = new ArrayList<>();
        League league = leagueRepository.findById(req.getLeagueId()).orElse(null);
        roundRepository
                .findByLeagueAndSeasonAndRoundSt(req.getLeagueId(),req.getSeason(),req.getRoundSt())
                .stream().forEach(ele->{

                    Team teamA  = teamRepository.findById(ele.getHomeTeamId()).orElse(null);
                    Team teamB  = teamRepository.findById(ele.getAwayTeamId()).orElse(null);
                    LeagueRoundInfoResponse element = null;
                    if(ele.getRoundStatus().equals(RoundStatus.DONE)) {
                        TeamLeagueRecord record = teamLeagueRecordRepository.findByRoundId(ele.getId()).stream().findFirst().orElse(null);
                        if(record.getTeam().getId().equals(teamA.getId())) {
                             element = new LeagueRoundInfoResponse(ele.getId(), league.getName(),
                                teamA.getName(), teamB.getName(), record.getScore(),record.getOppositeScore());
                        }
                        else{
                             element = new LeagueRoundInfoResponse(ele.getId(), league.getName(),
                                    teamA.getName(), teamB.getName(), record.getOppositeScore(),record.getScore());
                        }
                    }
                    else{
                        element = new LeagueRoundInfoResponse(ele.getId(),league.getName(),teamA.getName(),teamB.getName());
                    }
                    resp.add(element);

                });


        return resp;
    }
}
