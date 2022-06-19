package com.example.soccerleague.springDataJpa;

import com.example.soccerleague.RegisterService.GameAvgDto;
import com.example.soccerleague.SearchService.LeagueRecord.Player.LeaguePlayerRecordResponse;
import com.example.soccerleague.SearchService.TeamDisplay.TeamPlayerDto;
import com.example.soccerleague.domain.SortType;
import com.example.soccerleague.domain.record.PlayerLeagueRecord;
import com.example.soccerleague.domain.record.PlayerRecord;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

import java.util.List;

public interface PlayerLeagueRecordRepository extends  PlayerRecordRepository , PlayerLeagueRepositoryQuerydsl{

    //가장 최근의 경기 하나만을 가져온다.
    @Query("select plr from PlayerLeagueRecord plr join plr.player p on p.id = :playerId " +
            " where plr.season = :season and plr.mathResult is not null " +
            " order by plr.id desc ")
    List<PlayerLeagueRecord> findFirstByLast(@Param("playerId") Long playerId, @Param("season") int season, Pageable pageable);



    @Query(" select plr from PlayerLeagueRecord plr" +
            " join  plr.player p on p.id =:playerId " +
            " join  plr.round lr on lr.roundSt < :roundSt where plr.season = :season ")
    List<PlayerLeagueRecord> findBySeasonAndPlayer(@Param("season") int season,@Param("roundSt") int roundSt,@Param("playerId") Long playerId);


    @Query( "select new com.example.soccerleague.SearchService.TeamDisplay.TeamPlayerDto(" +
            "p.name ,count(plr.id) , p.rating, p.position) from PlayerLeagueRecord  plr " +
            " join plr.player p " +
            " join plr.team t " +
            " where t.id =:teamId and plr.season = :season " +
            " group by p.name ")
    List<TeamPlayerDto> findSeasonAndTeamPlayer(@Param("teamId") Long teamId , @Param("season")int season);



    @Query("select avg(plr.grade) from  PlayerLeagueRecord plr")
    Double avgGrade();



    @Query(" Select plr from PlayerLeagueRecord plr " +
            " join plr.player p on p.id = :playerId " +
            " join plr.round lr on lr.roundSt < :roundSt where plr.season = :season")
    List<PlayerLeagueRecord> findBySeasonAndPlayer(@Param("playerId") Long playerId ,@Param("season") int season,@Param("roundSt") int roundSt);


    @Query("Select plr from PlayerLeagueRecord plr join plr.round lr on lr.season = :season join plr.player p on p.id = :playerId")
    List<PlayerLeagueRecord> findBySeasonAndPlayer(@Param("playerId") Long playerId,@Param("season") int season);






    // query 로 선수 기록 결과를 한꺼번에 조회 + 페이징.
    @Query(" select new com.example.soccerleague.SearchService.LeagueRecord.Player.LeaguePlayerRecordResponse(p.name ,t.name , sum(plr.goal), " +
            " sum(plr.assist) ,sum(plr.shooting),sum(plr.validShooting),sum(plr.foul), sum(plr.pass) , sum(plr.goodDefense)) " +
            " from PlayerLeagueRecord plr " +
            " join plr.player p " +
            " join plr.team t on t.league.id = :leagueId" +
            " where plr.season = :season " +
            " group by p.id " +
            " order by sum(plr.goal) desc")
    List<LeaguePlayerRecordResponse> playerLeagueRecordQuery(@Param("leagueId") Long leagueId , @Param("season") int season);







}

