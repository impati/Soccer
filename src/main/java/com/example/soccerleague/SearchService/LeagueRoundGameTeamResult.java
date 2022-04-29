package com.example.soccerleague.SearchService;

import com.example.soccerleague.EntityRepository.RoundEntityRepository;
import com.example.soccerleague.EntityRepository.TeamLeagueRecordEntityRepository;
import com.example.soccerleague.SearchService.SearchResult;
import com.example.soccerleague.Web.newDto.league.LeagueRoundGameTeamResultDto;
import com.example.soccerleague.domain.DataTransferObject;
import com.example.soccerleague.domain.Round.LeagueRound;
import com.example.soccerleague.domain.record.Record;
import com.example.soccerleague.domain.record.TeamLeagueRecord;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class LeagueRoundGameTeamResult implements SearchResult {
    private final TeamLeagueRecordEntityRepository teamLeagueRecordEntityRepository;
    @Override
    public boolean supports(DataTransferObject dto) {
        return dto instanceof LeagueRoundGameTeamResultDto;
    }

    @Override
    public List<DataTransferObject> searchResultList(DataTransferObject dto) {
        LeagueRoundGameTeamResultDto teamResultDto = (LeagueRoundGameTeamResultDto)dto;
        List<DataTransferObject> ret = new ArrayList<>();
        teamLeagueRecordEntityRepository.findByRoundId(teamResultDto.getRoundId())
                .stream()
                .map(ele->(TeamLeagueRecord)ele)
                .forEach(ele->ret.add(LeagueRoundGameTeamResultDto.create(
                        ele.getTeam().getName(),ele.getTeam().getId(),ele.getScore(),ele.getShare(),ele.getCornerKick(),
                        ele.getFreeKick())));
        return ret;
    }
}
