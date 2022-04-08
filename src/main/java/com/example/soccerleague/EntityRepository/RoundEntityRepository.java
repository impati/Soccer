package com.example.soccerleague.EntityRepository;

import com.example.soccerleague.domain.Round.Round;

import java.util.List;

public interface RoundEntityRepository extends EntityRepository{
    List<Round> findByLeagueAndSeasonAndRoundSt(Long leagueId, int season, int roundSt);
    List<Round> findByTeam(Long teamId);
    List<Round> isRemainSeasonAndRoundSt(int season, int roundSt);
    Boolean findByLeagueSeason(int season,Long leagueId);

}
