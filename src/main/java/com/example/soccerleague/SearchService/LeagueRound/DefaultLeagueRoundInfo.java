package com.example.soccerleague.SearchService.LeagueRound;

import com.example.soccerleague.EntityRepository.LeagueEntityRepository;
import com.example.soccerleague.EntityRepository.RoundEntityRepository;
import com.example.soccerleague.EntityRepository.TeamEntityRepository;
import com.example.soccerleague.EntityRepository.TeamLeagueRecordEntityRepository;
import com.example.soccerleague.Web.newDto.league.LeagueRoundInfoDto;
import com.example.soccerleague.Web.newDto.league.LeagueRoundSearchDto;
import com.example.soccerleague.domain.DataTransferObject;
import com.example.soccerleague.domain.League;
import com.example.soccerleague.domain.Round.Round;
import com.example.soccerleague.domain.Round.RoundStatus;
import com.example.soccerleague.domain.Team;
import com.example.soccerleague.domain.record.TeamLeagueRecord;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DefaultLeagueRoundInfo implements LeagueRoundInfo {
    private final RoundEntityRepository roundEntityRepository;
    private final TeamEntityRepository teamEntityRepository;
    private final TeamLeagueRecordEntityRepository teamLeagueRecordEntityRepository;
    private final LeagueEntityRepository leagueEntityRepository;
    @Override
    public boolean supports(DataTransferObject dto) {
        return dto instanceof LeagueRoundSearchDto;
    }

    @Override
    public List<DataTransferObject> searchResultList(DataTransferObject dto) {
        LeagueRoundSearchDto leagueRoundSearchDto = (LeagueRoundSearchDto)dto;
        int season = leagueRoundSearchDto.getSeason();
        int roundSt = leagueRoundSearchDto.getRoundSt();
        List<DataTransferObject> ret = new ArrayList<>();
        for(Long i = 1L; i<=4L;i++){
            List<Round> roundList = roundEntityRepository
                    .findByLeagueAndSeasonAndRoundSt(i,season, roundSt);

            LeagueRoundSearchDto element = new LeagueRoundSearchDto();

            for (var round : roundList) {
                Team teamA  = (Team)teamEntityRepository.findById(round.getHomeTeamId()).orElse(null);
                Team teamB  = (Team)teamEntityRepository.findById(round.getAwayTeamId()).orElse(null);
                LeagueRoundInfoDto info = null;
                if(round.getRoundStatus() == RoundStatus.DONE) {
                    TeamLeagueRecord record = (TeamLeagueRecord)teamLeagueRecordEntityRepository.findByRoundId(round.getId()).stream().findFirst().orElse(null);
                    if(record.getTeam().getId().equals(teamA.getId())) info = LeagueRoundInfoDto.create(round.getId(),teamA.getName(),teamB.getName(),record.getScore(),record.getOppositeScore());
                    else info = LeagueRoundInfoDto.create(round.getId(),teamA.getName(),teamB.getName(),record.getOppositeScore(),record.getScore());
                }
                else{
                    info = LeagueRoundInfoDto.create(round.getId(),teamA.getName(),teamB.getName());
                }
                element.setLeagueName(teamA.getLeague().getName());
                element.getLeagueRoundInfoList().add(info);

            }
            ret.add(element);


        }
        return ret;
    }

    @Override
    public List<DataTransferObject> searchList(DataTransferObject dataTransferObject) {
        LeagueRoundInfoRequest req = (LeagueRoundInfoRequest) dataTransferObject;
        List<DataTransferObject> resp = new ArrayList<>();
        League league = (League)leagueEntityRepository.findById(req.getLeagueId()).orElse(null);
        roundEntityRepository
                .findByLeagueAndSeasonAndRoundSt(req.getLeagueId(),req.getSeason(),req.getRoundSt())
                .stream().forEach(ele->{

                    Team teamA  = (Team)teamEntityRepository.findById(ele.getHomeTeamId()).orElse(null);
                    Team teamB  = (Team)teamEntityRepository.findById(ele.getAwayTeamId()).orElse(null);
                    LeagueRoundInfoResponse element = null;
                    if(ele.getRoundStatus().equals(RoundStatus.DONE)) {
                        TeamLeagueRecord record = (TeamLeagueRecord) teamLeagueRecordEntityRepository.findByRoundId(ele.getId()).stream().findFirst().orElse(null);
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
