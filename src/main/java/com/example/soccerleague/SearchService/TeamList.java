package com.example.soccerleague.SearchService;

import com.example.soccerleague.EntityRepository.TeamEntityRepository;
import com.example.soccerleague.SearchService.SearchResult;
import com.example.soccerleague.Web.newDto.Team.TeamListDto;
import com.example.soccerleague.domain.DataTransferObject;
import com.example.soccerleague.domain.Team;
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
public class TeamList implements SearchResult {
    private final TeamEntityRepository teamEntityRepository;
    @Override
    public boolean supports(DataTransferObject dto) {
        return dto instanceof TeamListDto;
    }

    @Override
    public List<DataTransferObject> searchResultList(DataTransferObject dto) {
        TeamListDto teamListDto = (TeamListDto) dto;
        List<DataTransferObject> ret = new ArrayList<>();
        List<Team> teams = teamEntityRepository.findByLeagueId(teamListDto.getLeagueId());
        for(var team:teams){
            TeamListDto obj = TeamListDto.create(team.getId(),team.getName(),team.getRating());
            ret.add(obj);
        }
        return ret;
    }
}
