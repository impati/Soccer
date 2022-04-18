package com.example.soccerleague.SearchService;

import com.example.soccerleague.EntityRepository.RoundEntityRepository;
import com.example.soccerleague.EntityRepository.TeamEntityRepository;
import com.example.soccerleague.EntityRepository.TeamLeagueRecordEntityRepository;
import com.example.soccerleague.Web.newDto.league.LeagueRoundSeasonTeamDto;
import com.example.soccerleague.domain.DataTransferObject;
import com.example.soccerleague.domain.Round.Round;
import com.example.soccerleague.domain.Team;
import com.example.soccerleague.domain.record.MatchResult;
import com.example.soccerleague.domain.record.Record;
import com.example.soccerleague.domain.record.TeamLeagueRecord;
import lombok.Data;
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
@Transactional(readOnly = true)
public class LeagueRoundSeasonTeam implements SearchResult{
    private final TeamLeagueRecordEntityRepository teamLeagueRecordEntityRepository;
    private final RoundEntityRepository roundEntityRepository;
    private final TeamEntityRepository teamEntityRepository;
    @Override
    public boolean supports(DataTransferObject dto) {
        return dto instanceof LeagueRoundSeasonTeamDto;
    }

    @Override
    public Optional<DataTransferObject> searchResult(DataTransferObject dto) {
        LeagueRoundSeasonTeamDto seasonTeamDto = (LeagueRoundSeasonTeamDto) dto;
        Round round = (Round)roundEntityRepository.findById(seasonTeamDto.getRoundId()).orElse(null);
        Team team = (Team)teamEntityRepository.findById(seasonTeamDto.getTeamId()).orElse(null);
        fillResult(round,team);
        return Optional.ofNullable(seasonTeamDto);
    }

    private LeagueRoundSeasonTeamDto fillResult(Round round, Team team){
        LeagueRoundSeasonTeamDto leagueRoundSeasonTeamDto = LeagueRoundSeasonTeamDto.create(team.getName(),"League");
        List<TeamLeagueRecord> teamRecordList = teamLeagueRecordEntityRepository.findBySeasonAndTeam(round.getSeason(), round.getRoundSt(), round.getHomeTeamId());
        int sz = teamRecordList.size();
        for(int i = 0;i < sz ;i++){
            TeamLeagueRecord teamLeagueRecord = teamRecordList.get(i);
            leagueRoundSeasonTeamDto.update(teamLeagueRecord.getMathResult(),teamLeagueRecord.getScore(),teamLeagueRecord.getOppositeScore(),teamLeagueRecord.getRank());
            if(i > sz - 5) leagueRoundSeasonTeamDto.recentUpdate(teamLeagueRecord.getMathResult());
        }
        leagueRoundSeasonTeamDto.updateGainAndLost(sz);


        return leagueRoundSeasonTeamDto;
    }
}
