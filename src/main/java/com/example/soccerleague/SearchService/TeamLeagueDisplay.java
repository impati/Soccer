package com.example.soccerleague.SearchService;

import com.example.soccerleague.EntityRepository.TeamLeagueRecordEntityRepository;
import com.example.soccerleague.Web.dto.Team.TeamLeagueDisplayDto;
import com.example.soccerleague.domain.DataTransferObject;
import com.example.soccerleague.domain.Season;
import com.example.soccerleague.domain.record.TeamLeagueRecord;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TeamLeagueDisplay implements SearchResult {
    private final TeamLeagueRecordEntityRepository teamLeagueRecordEntityRepository;
    @Override
    public boolean supports(DataTransferObject dto) {
        return dto instanceof TeamLeagueDisplayDto;
    }

    @Override
    public Optional<DataTransferObject> searchResult(DataTransferObject dto) {
        TeamLeagueDisplayDto displayDto = (TeamLeagueDisplayDto)dto;
        List<TeamLeagueRecord> teamLeagueRecordList = teamLeagueRecordEntityRepository.
                findBySeasonAndTeam(displayDto.getSeason(), Season.LASTLEAGUEROUND, displayDto.getTeamId());
        teamLeagueRecordList.stream().forEach(ele->displayDto.update(ele));
        return Optional.ofNullable(displayDto);
    }
}
