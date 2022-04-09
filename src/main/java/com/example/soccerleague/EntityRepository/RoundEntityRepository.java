package com.example.soccerleague.EntityRepository;

import com.example.soccerleague.domain.Round.Round;

import java.util.List;

/**
 * TODO: league,챔피언스리그,유로파 등등 라운드의 공통적인 기능만 모으고
 * 구체적인 기능은 이를 상속받아 새로 만들 것.
 */
public interface RoundEntityRepository extends EntityRepository{
    List<Round> findByLeagueAndSeasonAndRoundSt(Long leagueId, int season, int roundSt);
    List<Round> findByTeam(Long teamId);
    List<Round> isRemainSeasonAndRoundSt(int season, int roundSt);
    Boolean findByLeagueSeason(int season,Long leagueId);

}
