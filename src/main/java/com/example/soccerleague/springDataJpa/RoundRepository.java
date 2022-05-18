package com.example.soccerleague.springDataJpa;

import com.example.soccerleague.domain.Round.Round;
import com.example.soccerleague.domain.Round.RoundStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * TODO: league,챔피언스리그,유로파 등등 라운드의 공통적인 기능만 모으고
 * 구체적인 기능은 이를 상속받아 새로 만들 것.
 */

public interface RoundRepository extends JpaRepository<Round,Long> {

    @Query("select r from Round r where r.roundSt = :roundSt and r.leagueId = :leagueId and r.season =:season")
    List<Round> findByLeagueAndSeasonAndRoundSt(
            @Param("leagueId") Long leagueId, @Param("season") int season,@Param("roundSt") int roundSt);

    @Query(value = "select count(r) from Round r where r.roundSt = :roundSt and r.roundStatus <> com.example.soccerleague.domain.Round.RoundStatus.DONE")
    Long currentRoundIsDone(@Param("roundSt") int roundSt);

    @Query(value = "select count(r) from Round r where r.leagueId =:leagueId and r.season = :season")
    Long findByLeagueSeason(@Param("leagueId") Long leagueId ,@Param("season") int season);

    @Query(value ="select r from Round r where r.season = :season and r.roundSt = :roundSt and r.roundStatus <> com.example.soccerleague.domain.Round.RoundStatus.DONE")
    List<Round> findNotDoneGame (@Param("season")int season,@Param("roundSt") int roundSt);


}
