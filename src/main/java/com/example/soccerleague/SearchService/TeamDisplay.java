package com.example.soccerleague.SearchService;

import com.example.soccerleague.EntityRepository.TeamEntityRepository;
import com.example.soccerleague.Web.newDto.Team.TeamDisplayDto;
import com.example.soccerleague.domain.DataTransferObject;
import com.example.soccerleague.domain.Team;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TeamDisplay implements SearchResult{
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
}
