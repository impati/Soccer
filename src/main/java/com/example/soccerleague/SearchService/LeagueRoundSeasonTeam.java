package com.example.soccerleague.SearchService;

import com.example.soccerleague.EntityRepository.*;
import com.example.soccerleague.Web.dto.League.LeagueRoundSeasonResult;
import com.example.soccerleague.Web.newDto.league.LeagueRoundSeasonTeamDto;
import com.example.soccerleague.domain.DataTransferObject;
import com.example.soccerleague.domain.Player.Player;
import com.example.soccerleague.domain.Round.Round;
import com.example.soccerleague.domain.Round.RoundStatus;
import com.example.soccerleague.domain.Season;
import com.example.soccerleague.domain.Team;
import com.example.soccerleague.domain.record.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LeagueRoundSeasonTeam implements SearchResult{
    private final TeamLeagueRecordEntityRepository teamLeagueRecordEntityRepository;
    private final RoundEntityRepository roundEntityRepository;
    private final TeamEntityRepository teamEntityRepository;
    private final PlayerEntityRepository playerEntityRepository;
    private final PlayerLeagueRecordEntityRepository playerLeagueRecordEntityRepository;
    @Override
    public boolean supports(DataTransferObject dto) {
        return dto instanceof LeagueRoundSeasonTeamDto;
    }

    @Override
    public Optional<DataTransferObject> searchResult(DataTransferObject dto) {
        LeagueRoundSeasonTeamDto seasonTeamDto = (LeagueRoundSeasonTeamDto) dto;
        Round round = (Round)roundEntityRepository.findById(seasonTeamDto.getRoundId()).orElse(null);
        Team team = (Team)teamEntityRepository.findById(seasonTeamDto.getTeamId()).orElse(null);
        return Optional.ofNullable(fillResult(round,team));
    }

    private LeagueRoundSeasonTeamDto fillResult(Round round, Team team){
        LeagueRoundSeasonTeamDto leagueRoundSeasonTeamDto = LeagueRoundSeasonTeamDto.create(team.getName(),"League");
        List<TeamLeagueRecord> teamRecordList = teamLeagueRecordEntityRepository.findBySeasonAndTeam(round.getSeason(), team.getId()).stream().map(ele->(TeamLeagueRecord)ele).collect(Collectors.toList());
        int sz = teamRecordList.size();
        int count  = 0;
        for(int i = 0;i < sz ;i++){
            TeamLeagueRecord teamLeagueRecord = teamRecordList.get(i);
            if(teamLeagueRecord.getRound().getRoundSt() < round.getRoundSt()) {
                leagueRoundSeasonTeamDto.update(teamLeagueRecord.getMathResult(), teamLeagueRecord.getScore(), teamLeagueRecord.getOppositeScore(), teamLeagueRecord.getRank());
                if (i > sz - 5) leagueRoundSeasonTeamDto.recentUpdate(teamLeagueRecord.getMathResult());
                count ++;
            }
        }
        leagueRoundSeasonTeamDto.updateGainAndLost(count);



        return leagueRoundSeasonTeamDto;
    }

}
