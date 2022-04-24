package com.example.soccerleague.SearchService.TeamDisplay;

import com.example.soccerleague.EntityRepository.TeamEntityRepository;
import com.example.soccerleague.SearchService.SearchResult;
import com.example.soccerleague.SearchService.TeamDisplay.League.TeamLeagueDisplayResponse;
import com.example.soccerleague.Web.newDto.Team.TeamDisplayDto;
import com.example.soccerleague.domain.DataTransferObject;
import com.example.soccerleague.domain.Team;
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
public class DefaultTeamDisplay implements TeamDisplay {
    private final TeamEntityRepository teamEntityRepository;
    @Override
    public boolean supports(DataTransferObject dto) {
        return dto instanceof  TeamDisplayDto;
    }

    @Override
    public Optional<DataTransferObject> searchResult(DataTransferObject dto) {
        TeamDisplayDto displayDto = (TeamDisplayDto) dto;
        Team team = (Team)teamEntityRepository.findById(displayDto.getTeamId()).orElse(null);
        displayDto.fillData(team.getName(),team.getLeague().getName(),team.getRating());

        return Optional.ofNullable(displayDto);
    }

    @Override
    public List<DataTransferObject> search(DataTransferObject dataTransferObject) {
        TeamDisplayRequest req  = (TeamDisplayRequest) dataTransferObject;
        List<DataTransferObject> ret = new ArrayList<>();
        teamEntityRepository.findByLeagueId(req.getLeagueId())
                .stream()
                .map(ele->(Team)ele)
                .forEach(ele -> {
                    ret.add(new TeamDisplayResponse(ele.getId(),ele.getLeague().getName(),ele.getName(),ele.getRating()));
                });
        return ret;
    }

    @Override
    public DataTransferObject searchOne(Long teamId) {
        Team team = (Team)teamEntityRepository.findById(teamId).orElse(null);
        return new TeamDisplayResponse(team.getId(),team.getLeague().getName(),team.getName(),team.getRating());
    }
}
