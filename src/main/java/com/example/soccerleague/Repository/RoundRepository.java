package com.example.soccerleague.Repository;

import com.example.soccerleague.domain.Round.Round;

import java.util.List;

public interface RoundRepository {
    void save(Round round);
    Round findById(Long id);
    List<Round> findByLeagueAndSeasonAndRoundSt(Long leagueId,int season,int roundSt);
    List<Round> findByTeam(Long teamId);
    List<Round>  isRemainSeasonAndRoundSt(int season,int roundSt);
    Boolean findByLeagueSeason(Long leagueId,int season);

}
