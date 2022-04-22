package com.example.soccerleague.SearchService;

import com.example.soccerleague.EntityRepository.TeamRecordEntityRepository;
import com.example.soccerleague.Web.newDto.Team.TeamTotalRecordDto;
import com.example.soccerleague.domain.DataTransferObject;
import com.example.soccerleague.domain.Season;
import com.example.soccerleague.domain.record.TeamRecord;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Slf4j
@Transactional(readOnly = true)
public class TeamTotalDisplay implements SearchResult{
    private final List<TeamRecordEntityRepository> teamRecordEntityRepository;
    @Override
    public boolean supports(DataTransferObject dto) {
        return dto instanceof TeamTotalRecordDto;
    }

    @Override
    public Optional<DataTransferObject> searchResult(DataTransferObject dto) {
        TeamTotalRecordDto teamTotalRecordDto = (TeamTotalRecordDto) dto;
        for(var record: teamRecordEntityRepository){
            for(int i = 0 ;i<=Season.CURRENTSEASON;i++){
                List<TeamRecord> teamRecordList = record.findBySeasonAndTeam(i, teamTotalRecordDto.getTeamId());
                teamRecordList.stream().forEach(ele->{teamTotalRecordDto.update(ele);});

            }
        }
        return Optional.ofNullable(teamTotalRecordDto);
    }

}
