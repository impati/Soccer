package com.example.soccerleague.Service;

import com.example.soccerleague.Repository.LeagueRepository;
import com.example.soccerleague.domain.League;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LeagueServiceImpl implements LeagueService{
    private final LeagueRepository leagueRepository;
    @Override
    public List<League> searchAll() {
        return leagueRepository.findAll();
    }

    @Override
    public Optional<League> searchLeague(Long id) {
        return leagueRepository.findById(id);
    }

    @Override
    @Transactional
    public void updateSeasonAndRoundSt(int season, int roundSt) {
        List<League> leagues = leagueRepository.findAll();
        for(var ele : leagues){
            ele.setCurrentSeason(season);
            ele.setCurrentRoundSt(roundSt);
        }
    }


}
