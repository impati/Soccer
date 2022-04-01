package com.example.soccerleague.Service;

import com.example.soccerleague.Repository.TeamRepository;
import com.example.soccerleague.domain.Team;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TeamServiceImpl implements TeamService{
    private final TeamRepository teamRepository;

    @Override
    public Team searchTeam(Long id) {
        return teamRepository.findById(id);
    }

    @Override
    public List<Team> searchAll() {
        return teamRepository.findAll();
    }

    @Override
    public List<Team> searchByLeague(Long leagueId) {
        return teamRepository.findByLeagueId(leagueId);
    }
}
